package SARL.agents;

import SARL.agents.ChargeApprovedEvent;
import SARL.agents.ChargeCompletedEvent;
import SARL.agents.ChargeRequestEvent;
import SARL.agents.ChargingBehavior;
import SARL.agents.FullPathUpdateEvent;
import SARL.agents.TimeStep;
import SARL.agents.VehicleAgent;
import SARL.agents.VehicleStatus;
import SARL.agents.capacities.ChargingCapacity;
import SARL.agents.capacities.MovingCapacity;
import SARL.agents.chargeStationFoundEvent;
import SARL.agents.utils.geolocation.mapbox.Node;
import SARL.agents.utils.geolocation.mapbox.NodeUtils;
import com.google.common.base.Objects;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class VehicleAgentBehavior extends Behavior {
  private long timeStep;
  
  private long chargeNeeded;
  
  private Address closestStationID;
  
  private final VehicleAgent owner;
  
  private int currentNodeIndex = 0;
  
  private int currentPathIndex = 0;
  
  private double distanceBetweenNodes;
  
  private boolean lookingForCharging = false;
  
  public VehicleAgentBehavior(final VehicleAgent owner) {
    super(owner);
    this.owner = owner;
  }
  
  private void $behaviorUnit$TimeStep$0(final TimeStep occurrence) {
    double _speedKmPerHour = this.owner.getSpeedKmPerHour();
    this.distanceBetweenNodes = (occurrence.step * ((_speedKmPerHour * 1000) / 3600));
    ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
    long _batteryLevel = _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER.getBatteryLevel();
    if ((_batteryLevel < 20)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("charging ...");
      ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_1.updateBatteryLevel(50);
      ChargingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_2 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_CHARGINGCAPACITY$CALLER_2.goCharge();
      this.lookingForCharging = true;
      List<Node> fullPathToPaint = Stream.<Node>concat(this.owner.getFromSourceToDestinationViaStationPair().getKey().stream(), 
        this.owner.getFromSourceToDestinationViaStationPair().getValue().stream()).collect(Collectors.<Node>toList());
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      String _agentName = this.owner.getAgentName();
      FullPathUpdateEvent _fullPathUpdateEvent = new FullPathUpdateEvent(_agentName, fullPathToPaint);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_fullPathUpdateEvent);
    }
    if (((this.currentNodeIndex >= this.owner.getSubPath().size()) && (this.currentPathIndex < (this.owner.getFullPath().size() - 1)))) {
      this.owner.setSubPath(NodeUtils.getNodesBetween(this.owner.getFullPath().get(this.currentPathIndex), 
        this.owner.getFullPath().get((this.currentPathIndex + 1)), this.distanceBetweenNodes));
      this.currentNodeIndex = 0;
      this.currentPathIndex++;
    }
    int _size = this.owner.getSubPath().size();
    if ((this.currentNodeIndex < _size)) {
      this.owner.setStatus(VehicleStatus.moving);
      MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER.move(this.owner.getSubPath().get(this.currentNodeIndex));
      this.currentNodeIndex++;
    } else {
      if (this.lookingForCharging) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("The agent has reached the charging Station.");
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1.waiting("charging");
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        long _batteryLevel_1 = this.owner.getBatteryLevel();
        long _batteryCapacity = this.owner.getBatteryCapacity();
        long _batteryChargeCapacity = this.owner.getBatteryChargeCapacity();
        ChargeRequestEvent _chargeRequestEvent = new ChargeRequestEvent(_batteryLevel_1, _batteryCapacity, _batteryChargeCapacity);
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final Address $_closestStationID;
          
          public $SerializableClosureProxy(final Address $_closestStationID) {
            this.$_closestStationID = $_closestStationID;
          }
          
          @Override
          public boolean matches(final Address it) {
            return Objects.equal(it, $_closestStationID);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            return Objects.equal(it, VehicleAgentBehavior.this.closestStationID);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, VehicleAgentBehavior.this.closestStationID);
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_chargeRequestEvent, _function);
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("The agent has reached its destination.");
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_2 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_2.stop();
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.unregisterBehavior(this);
      }
    }
  }
  
  private void $behaviorUnit$ChargeCompletedEvent$1(final ChargeCompletedEvent occurrence) {
    this.currentNodeIndex = 0;
    this.currentPathIndex = 0;
    this.lookingForCharging = false;
  }
  
  private void $behaviorUnit$ChargeApprovedEvent$2(final ChargeApprovedEvent occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((((("Charge approved for " + Long.valueOf(occurrence.chargeNeeded)) + " kWh. Total price: ") + Double.valueOf(occurrence.totalPrice)) + " Dh."));
    this.chargeNeeded = occurrence.chargeNeeded;
    MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
    _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER.charging();
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
    ChargingBehavior _chargingBehavior = new ChargingBehavior(this.owner, occurrence.chargeNeeded);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.registerBehavior(_chargingBehavior);
  }
  
  private void $behaviorUnit$chargeStationFoundEvent$3(final chargeStationFoundEvent occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    String _value = occurrence.stationAdressAndNamePair.getValue();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("updated closest " + occurrence.closestStation) + " - ") + _value));
    this.closestStationID = occurrence.stationAdressAndNamePair.getKey();
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
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ChargeCompletedEvent(final ChargeCompletedEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ChargeCompletedEvent$1(occurrence));
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$chargeStationFoundEvent$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ChargeApprovedEvent(final ChargeApprovedEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ChargeApprovedEvent$2(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(ChargeApprovedEvent.class);
    toBeFilled.add(ChargeCompletedEvent.class);
    toBeFilled.add(TimeStep.class);
    toBeFilled.add(chargeStationFoundEvent.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (ChargeApprovedEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (ChargeCompletedEvent.class.isAssignableFrom(event)) {
      return true;
    }
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
    if (event instanceof ChargeApprovedEvent) {
      final ChargeApprovedEvent occurrence = (ChargeApprovedEvent) event;
      $guardEvaluator$ChargeApprovedEvent(occurrence, callbacks);
    }
    if (event instanceof ChargeCompletedEvent) {
      final ChargeCompletedEvent occurrence = (ChargeCompletedEvent) event;
      $guardEvaluator$ChargeCompletedEvent(occurrence, callbacks);
    }
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
    if (other.timeStep != this.timeStep)
      return false;
    if (other.chargeNeeded != this.chargeNeeded)
      return false;
    if (other.currentNodeIndex != this.currentNodeIndex)
      return false;
    if (other.currentPathIndex != this.currentPathIndex)
      return false;
    if (Double.doubleToLongBits(other.distanceBetweenNodes) != Double.doubleToLongBits(this.distanceBetweenNodes))
      return false;
    if (other.lookingForCharging != this.lookingForCharging)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.timeStep);
    result = prime * result + Long.hashCode(this.chargeNeeded);
    result = prime * result + Integer.hashCode(this.currentNodeIndex);
    result = prime * result + Integer.hashCode(this.currentPathIndex);
    result = prime * result + Double.hashCode(this.distanceBetweenNodes);
    result = prime * result + Boolean.hashCode(this.lookingForCharging);
    return result;
  }
}
