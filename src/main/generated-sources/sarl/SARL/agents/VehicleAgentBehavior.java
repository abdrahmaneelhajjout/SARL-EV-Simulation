package SARL.agents;

import SARL.agents.FullPathUpdateEvent;
import SARL.agents.TimeStep;
import SARL.agents.VehicleAgent;
import SARL.agents.VehiculeStatus;
import SARL.agents.capacities.ChargingCapacity;
import SARL.agents.capacities.MovingCapacity;
import SARL.agents.chargeStationFoundEvent;
import SARL.agents.geolocation.mapbox.Node;
import SARL.agents.geolocation.mapbox.NodeUtils;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class VehicleAgentBehavior extends Behavior {
  private final VehicleAgent owner;
  
  private int currentNodeIndex = 0;
  
  private int currentPathIndex = 0;
  
  private double distanceBetweenNodes;
  
  public VehicleAgentBehavior(final VehicleAgent owner) {
    super(owner);
    this.owner = owner;
  }
  
  private void $behaviorUnit$TimeStep$0(final TimeStep occurrence) {
    double _speedKmPerHour = this.owner.getSpeedKmPerHour();
    this.distanceBetweenNodes = (occurrence.step * ((_speedKmPerHour * 1000) / 3600));
    ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
    int _batteryLevel = _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER.getBatteryLevel();
    if ((_batteryLevel < 20)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("charging ...");
      ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_1.updateBatteryLevel(50);
      ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_2 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_2.goCharge();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      String _agentName = this.owner.getAgentName();
      List<Node> _fullPath = this.owner.getFullPath();
      FullPathUpdateEvent _fullPathUpdateEvent = new FullPathUpdateEvent(_agentName, _fullPath);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_fullPathUpdateEvent);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("charged !");
    } else {
      if (((this.currentNodeIndex >= this.owner.getSubPath().size()) && (this.currentPathIndex < (this.owner.getFullPath().size() - 1)))) {
        this.owner.setSubPath(NodeUtils.getNodesBetween(this.owner.getFullPath().get(this.currentPathIndex), 
          this.owner.getFullPath().get((this.currentPathIndex + 1)), this.distanceBetweenNodes));
        this.currentNodeIndex = 0;
        this.currentPathIndex++;
      }
      int _size = this.owner.getSubPath().size();
      if ((this.currentNodeIndex < _size)) {
        this.owner.setStatus(VehiculeStatus.moving);
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER.move(this.owner.getSubPath().get(this.currentNodeIndex));
        this.currentNodeIndex++;
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("The agent has reached its destination.");
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1.stop();
      }
    }
  }
  
  private void $behaviorUnit$chargeStationFoundEvent$1(final chargeStationFoundEvent occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("updated closest " + occurrence.closestStation) + " - ") + occurrence.stationAgentName));
    this.owner.setChargeStationNode(occurrence.closestStation);
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(MovingCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY;
  
  @SyntheticMember
  @Pure
  private MovingCapacity $CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY == null || this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY.get() == null) {
      this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY = $getSkill(MovingCapacity.class);
    }
    return $castSkill(MovingCapacity.class, this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(ChargingCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY;
  
  @SyntheticMember
  @Pure
  private ChargingCapacity $CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY == null || this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY.get() == null) {
      this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY = $getSkill(ChargingCapacity.class);
    }
    return $castSkill(ChargingCapacity.class, this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TimeStep(final TimeStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TimeStep$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$chargeStationFoundEvent(final chargeStationFoundEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$chargeStationFoundEvent$1(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(TimeStep.class);
    toBeFilled.add(chargeStationFoundEvent.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (TimeStep.class.isAssignableFrom(event)) {
      return true;
    }
    if (chargeStationFoundEvent.class.isAssignableFrom(event)) {
      return true;
    }
    return false;
  }
  
  @SyntheticMember
  @Override
  public void $evaluateBehaviorGuards(final Object event, final Collection<Runnable> callbacks) {
    super.$evaluateBehaviorGuards(event, callbacks);
    if (event instanceof TimeStep) {
      final TimeStep occurrence = (TimeStep) event;
      $guardEvaluator$TimeStep(occurrence, callbacks);
    }
    if (event instanceof chargeStationFoundEvent) {
      final chargeStationFoundEvent occurrence = (chargeStationFoundEvent) event;
      $guardEvaluator$chargeStationFoundEvent(occurrence, callbacks);
    }
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VehicleAgentBehavior other = (VehicleAgentBehavior) obj;
    if (other.currentNodeIndex != this.currentNodeIndex)
      return false;
    if (other.currentPathIndex != this.currentPathIndex)
      return false;
    if (Double.doubleToLongBits(other.distanceBetweenNodes) != Double.doubleToLongBits(this.distanceBetweenNodes))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.currentNodeIndex);
    result = prime * result + Integer.hashCode(this.currentPathIndex);
    result = prime * result + Double.hashCode(this.distanceBetweenNodes);
    return result;
  }
}
