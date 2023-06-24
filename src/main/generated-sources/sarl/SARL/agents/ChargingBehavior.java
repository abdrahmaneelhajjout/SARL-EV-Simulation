package SARL.agents;

import SARL.agents.ChargeCompletedEvent;
import SARL.agents.TimeStep;
import SARL.agents.VehicleAgent;
import SARL.agents.capacities.MovingCapacity;
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
import java.util.Set;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(21)
@SuppressWarnings("all")
public class ChargingBehavior extends Behavior {
  private final VehicleAgent owner;
  
  private long chargeNeeded;
  
  public ChargingBehavior(final VehicleAgent owner, final long chargeNeeded) {
    super(owner);
    this.owner = owner;
    this.chargeNeeded = chargeNeeded;
  }
  
  private void $behaviorUnit$TimeStep$0(final TimeStep occurrence) {
    long _batteryChargeCapacity = this.owner.getBatteryChargeCapacity();
    long chargeToAdd = Math.min((occurrence.step * _batteryChargeCapacity), this.chargeNeeded);
    long _batteryLevel = this.owner.getBatteryLevel();
    this.owner.setBatteryLevel((_batteryLevel + chargeToAdd));
    this.chargeNeeded = (this.chargeNeeded - chargeToAdd);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    long _batteryLevel_1 = this.owner.getBatteryLevel();
    long _batteryCapacity = this.owner.getBatteryCapacity();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("Charging: " + Long.valueOf(((_batteryLevel_1 * 100) / _batteryCapacity))) + "%"));
    if (((this.chargeNeeded <= 0) || (this.owner.getBatteryLevel() >= this.owner.getBatteryCapacity()))) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
      String _agentName = this.owner.getAgentName();
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("Charging complete for vehicle " + _agentName) + "!"));
      this.owner.setFullPath(this.owner.getFromSourceToDestinationViaStationPair().getValue());
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      ChargeCompletedEvent _chargeCompletedEvent = new ChargeCompletedEvent();
      class $SerializableClosureProxy implements Scope<Address> {
        
        private final UUID $_iD_1;
        
        public $SerializableClosureProxy(final UUID $_iD_1) {
          this.$_iD_1 = $_iD_1;
        }
        
        @Override
        public boolean matches(final Address it) {
          UUID _iD = it.getID();
          return Objects.equal(_iD, $_iD_1);
        }
      }
      final Scope<Address> _function = new Scope<Address>() {
        @Override
        public boolean matches(final Address it) {
          UUID _iD = it.getID();
          UUID _iD_1 = ChargingBehavior.this.owner.getID();
          return Objects.equal(_iD, _iD_1);
        }
        private Object writeReplace() throws ObjectStreamException {
          return new SerializableProxy($SerializableClosureProxy.class, ChargingBehavior.this.owner.getID());
        }
      };
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_chargeCompletedEvent, _function);
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.unregisterBehavior(this);
    }
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
    ChargingBehavior other = (ChargingBehavior) obj;
    if (other.chargeNeeded != this.chargeNeeded)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.chargeNeeded);
    return result;
  }
}
