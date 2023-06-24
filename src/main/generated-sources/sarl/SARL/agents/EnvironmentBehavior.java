package SARL.agents;

import SARL.agents.ChargeStationInitEvent;
import SARL.agents.EnvironmentAgent;
import SARL.agents.FindChargeStationEvent;
import SARL.agents.FullPathUpdateEvent;
import SARL.agents.TimeStep;
import SARL.agents.TimeStepChangedEvent;
import SARL.agents.TrafficColorChangedEvent;
import SARL.agents.TrafficSignaInitEvent;
import SARL.agents.VehicleAgentInitEvent;
import SARL.agents.VehicleAgentUpdateEvent;
import SARL.agents.chargeStationFoundEvent;
import SARL.agents.utils.geolocation.mapbox.Node;
import SARL.agents.utils.geolocation.mapbox.NodeUtils;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Behavior;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Set;
import javafx.util.Pair;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class EnvironmentBehavior extends Behavior {
  private final EnvironmentAgent owner;
  
  public EnvironmentBehavior(final EnvironmentAgent owner) {
    super(owner);
    this.owner = owner;
  }
  
  private void $behaviorUnit$VehicleAgentInitEvent$0(final VehicleAgentInitEvent occurrence) {
    this.owner.getMap().OnVehicleAgentInit(occurrence.initial_location_node, occurrence.agentName, occurrence.path);
  }
  
  private void $behaviorUnit$VehicleAgentUpdateEvent$1(final VehicleAgentUpdateEvent occurrence) {
    this.owner.getMap().onVehicleAgentUpdate(occurrence.current_location_node, occurrence.agentName);
  }
  
  private void $behaviorUnit$TimeStepChangedEvent$2(final TimeStepChangedEvent occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("changed time step");
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.cancel(this.owner.getTimeStepTask());
    this.owner.setTimeStep(((occurrence.timeStep) == null ? 0 : (occurrence.timeStep).longValue()));
    long _timeStep = this.owner.getTimeStep();
    if ((_timeStep > 0)) {
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
      final Procedure1<Agent> _function = (Agent it) -> {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        long _currentTimeStep = this.owner.getCurrentTimeStep();
        TimeStep _timeStep_1 = new TimeStep(_currentTimeStep);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_timeStep_1);
      };
      this.owner.setTimeStepTask(_$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.every(this.owner.getTimeStep(), _function));
    }
  }
  
  private void $behaviorUnit$TrafficSignaInitEvent$3(final TrafficSignaInitEvent occurrence) {
    this.owner.getMap().onTrafficAgentInit(occurrence.location, occurrence.agentName, occurrence.color);
  }
  
  private void $behaviorUnit$TrafficColorChangedEvent$4(final TrafficColorChangedEvent occurrence) {
    this.owner.getMap().onTrafficSignalChanged(occurrence.agentName, occurrence.colorIcon);
  }
  
  private void $behaviorUnit$ChargeStationInitEvent$5(final ChargeStationInitEvent occurrence) {
    this.owner.getChargeStationNodes().add(occurrence.location);
    this.owner.getMap().onChargeStationIniti(occurrence.location, occurrence.agentName, occurrence.getSource());
  }
  
  private void $behaviorUnit$FullPathUpdateEvent$6(final FullPathUpdateEvent occurrence) {
    this.owner.getMap().onFullPathUpdate(occurrence.agentName, occurrence.newPath);
  }
  
  private void $behaviorUnit$FindChargeStationEvent$7(final FindChargeStationEvent occurrence) {
    while ((this.owner.getChargeStationNodes().size() < 1)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("waiting ");
    }
    Node location = occurrence.currentLocation;
    Node closestNode = NodeUtils.findClosestNode(this.owner.getChargeStationNodes(), location);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
    Pair<Address, String> _get = this.owner.getMap().chargeStationNodes.get(closestNode);
    chargeStationFoundEvent _chargeStationFoundEvent = new chargeStationFoundEvent(closestNode, _get);
    class $SerializableClosureProxy implements Scope<Address> {
      
      private final Address $_source;
      
      public $SerializableClosureProxy(final Address $_source) {
        this.$_source = $_source;
      }
      
      @Override
      public boolean matches(final Address it) {
        return Objects.equal(it, $_source);
      }
    }
    final Scope<Address> _function = new Scope<Address>() {
      @Override
      public boolean matches(final Address it) {
        Address _source = occurrence.getSource();
        return Objects.equal(it, _source);
      }
      private Object writeReplace() throws ObjectStreamException {
        return new SerializableProxy($SerializableClosureProxy.class, occurrence.getSource());
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_chargeStationFoundEvent, _function);
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
  private void $guardEvaluator$FindChargeStationEvent(final FindChargeStationEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$FindChargeStationEvent$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$VehicleAgentUpdateEvent(final VehicleAgentUpdateEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$VehicleAgentUpdateEvent$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TimeStepChangedEvent(final TimeStepChangedEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TimeStepChangedEvent$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TrafficSignaInitEvent(final TrafficSignaInitEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrafficSignaInitEvent$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$VehicleAgentInitEvent(final VehicleAgentInitEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$VehicleAgentInitEvent$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ChargeStationInitEvent(final ChargeStationInitEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ChargeStationInitEvent$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$FullPathUpdateEvent(final FullPathUpdateEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$FullPathUpdateEvent$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TrafficColorChangedEvent(final TrafficColorChangedEvent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TrafficColorChangedEvent$4(occurrence));
  }
  
  @SyntheticMember
  @Override
  public void $getSupportedEvents(final Set<Class<? extends Event>> toBeFilled) {
    super.$getSupportedEvents(toBeFilled);
    toBeFilled.add(ChargeStationInitEvent.class);
    toBeFilled.add(FindChargeStationEvent.class);
    toBeFilled.add(FullPathUpdateEvent.class);
    toBeFilled.add(TimeStepChangedEvent.class);
    toBeFilled.add(TrafficColorChangedEvent.class);
    toBeFilled.add(TrafficSignaInitEvent.class);
    toBeFilled.add(VehicleAgentInitEvent.class);
    toBeFilled.add(VehicleAgentUpdateEvent.class);
  }
  
  @SyntheticMember
  @Override
  public boolean $isSupportedEvent(final Class<? extends Event> event) {
    if (ChargeStationInitEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (FindChargeStationEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (FullPathUpdateEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (TimeStepChangedEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (TrafficColorChangedEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (TrafficSignaInitEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (VehicleAgentInitEvent.class.isAssignableFrom(event)) {
      return true;
    }
    if (VehicleAgentUpdateEvent.class.isAssignableFrom(event)) {
      return true;
    }
    return false;
  }
  
  @SyntheticMember
  @Override
  public void $evaluateBehaviorGuards(final Object event, final Collection<Runnable> callbacks) {
    super.$evaluateBehaviorGuards(event, callbacks);
    if (event instanceof ChargeStationInitEvent) {
      final ChargeStationInitEvent occurrence = (ChargeStationInitEvent) event;
      $guardEvaluator$ChargeStationInitEvent(occurrence, callbacks);
    }
    if (event instanceof FindChargeStationEvent) {
      final FindChargeStationEvent occurrence = (FindChargeStationEvent) event;
      $guardEvaluator$FindChargeStationEvent(occurrence, callbacks);
    }
    if (event instanceof FullPathUpdateEvent) {
      final FullPathUpdateEvent occurrence = (FullPathUpdateEvent) event;
      $guardEvaluator$FullPathUpdateEvent(occurrence, callbacks);
    }
    if (event instanceof TimeStepChangedEvent) {
      final TimeStepChangedEvent occurrence = (TimeStepChangedEvent) event;
      $guardEvaluator$TimeStepChangedEvent(occurrence, callbacks);
    }
    if (event instanceof TrafficColorChangedEvent) {
      final TrafficColorChangedEvent occurrence = (TrafficColorChangedEvent) event;
      $guardEvaluator$TrafficColorChangedEvent(occurrence, callbacks);
    }
    if (event instanceof TrafficSignaInitEvent) {
      final TrafficSignaInitEvent occurrence = (TrafficSignaInitEvent) event;
      $guardEvaluator$TrafficSignaInitEvent(occurrence, callbacks);
    }
    if (event instanceof VehicleAgentInitEvent) {
      final VehicleAgentInitEvent occurrence = (VehicleAgentInitEvent) event;
      $guardEvaluator$VehicleAgentInitEvent(occurrence, callbacks);
    }
    if (event instanceof VehicleAgentUpdateEvent) {
      final VehicleAgentUpdateEvent occurrence = (VehicleAgentUpdateEvent) event;
      $guardEvaluator$VehicleAgentUpdateEvent(occurrence, callbacks);
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
