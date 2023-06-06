package SARL.agents;

import SARL.agents.TrafficSignalAgent;
import SARL.agents.capacities.TrafficSignalManagementCapacity;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import java.util.Collection;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class TrafficSignalBehavior extends Behavior {
  private final TrafficSignalAgent owner;
  
  public TrafficSignalBehavior(final TrafficSignalAgent owner) {
    super(owner);
    this.owner = owner;
  }
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    long time = this.owner.getTrafficLightStatus().getLightStatus().getDuration();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      TrafficSignalManagementCapacity _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY$CALLER = this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY$CALLER();
      _$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY$CALLER.changeColor(this.owner.getTrafficLightStatus().getNext());
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.every(time, _function);
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
  @ImportedCapacityFeature(TrafficSignalManagementCapacity.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY;
  
  @SyntheticMember
  @Pure
  private TrafficSignalManagementCapacity $CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY$CALLER() {
    if (this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY == null || this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY.get() == null) {
      this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY = $getSkill(TrafficSignalManagementCapacity.class);
    }
    return $castSkill(TrafficSignalManagementCapacity.class, this.$CAPACITY_USE$SARL_AGENTS_CAPACITIES_TRAFFICSIGNALMANAGEMENTCAPACITY);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(Initialize.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (Initialize.class.isAssignableFrom(event)) {
      return true;
    }
    return false;
  }
  
  @SyntheticMember
  @Override
  public void $evaluateBehaviorGuards(final Object event, final Collection<Runnable> callbacks) {
    super.$evaluateBehaviorGuards(event, callbacks);
    if (event instanceof Initialize) {
      final Initialize occurrence = (Initialize) event;
      $guardEvaluator$Initialize(occurrence, callbacks);
    }
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
}
