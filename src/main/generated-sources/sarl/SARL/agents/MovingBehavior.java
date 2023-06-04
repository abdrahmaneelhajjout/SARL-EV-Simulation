package SARL.agents;

import SARL.agents.Status;
import SARL.agents.TimeStep;
import SARL.agents.VehiculeAgent;
import SARL.agents.capacities.BatteryCapacity;
import SARL.agents.capacities.MovingCapacity;
import SARL.agents.geolocation.mapbox.NodeUtils;
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
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class MovingBehavior extends Behavior {
  private final VehiculeAgent owner;
  
  private int currentNodeIndex = 0;
  
  private int currentPathIndex = 0;
  
  private final double distanceBetweenNodes = 0.1;
  
  public MovingBehavior(final VehiculeAgent owner) {
    super(owner);
    this.owner = owner;
  }
  
  private void $behaviorUnit$TimeStep$0(final TimeStep occurrence) {
    BatteryCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER();
    double _checkBatteryStatus = _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER.checkBatteryStatus();
    if ((_checkBatteryStatus < 20)) {
      BatteryCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER_1.chargeBattery();
    } else {
      if (((this.currentNodeIndex >= this.owner.getSubPath().size()) && (this.currentPathIndex < (this.owner.getFullPath().size() - 1)))) {
        this.owner.setSubPath(NodeUtils.getNodesBetween(this.owner.getFullPath().get(this.currentPathIndex), this.owner.getFullPath().get((this.currentPathIndex + 1)), 
          this.distanceBetweenNodes));
        this.currentNodeIndex = 0;
        this.currentPathIndex++;
      }
      int _size = this.owner.getSubPath().size();
      if ((this.currentNodeIndex < _size)) {
        this.owner.setStatus(Status.moving);
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER.move(this.owner.getSubPath().get(this.currentNodeIndex));
        this.currentNodeIndex++;
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent has reached its destination.");
        MovingCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1 = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER();
        _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_MOVINGCAPACITY$CALLER_1.stop();
      }
    }
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
  @ImportedCapacityFeature(BatteryCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY;
  
  @SyntheticMember
  @Pure
  private BatteryCapacity $CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY == null || this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY.get() == null) {
      this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY = $getSkill(BatteryCapacity.class);
    }
    return $castSkill(BatteryCapacity.class, this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_BATTERYCAPACITY);
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TimeStep(final TimeStep occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TimeStep$0(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(TimeStep.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (TimeStep.class.isAssignableFrom(event)) {
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
    MovingBehavior other = (MovingBehavior) obj;
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
