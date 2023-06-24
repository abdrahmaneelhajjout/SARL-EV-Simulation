package SARL.agents;

import SARL.agents.VehicleAgent;
import SARL.agents.VehicleAgentUpdateEvent;
import SARL.agents.VehicleStatus;
import SARL.agents.capacities.MovingCapacity;
import SARL.agents.utils.geolocation.mapbox.Node;
import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.Skill;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(22)
@SuppressWarnings("all")
public class MovingSkill extends Skill implements MovingCapacity {
  private VehicleAgent owner;
  
  public void install() {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        Agent _owner = MovingSkill.this.getOwner();
        this.$$result = (_owner != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    Agent _owner = this.getOwner();
    this.owner = ((VehicleAgent) _owner);
  }
  
  @Override
  public void move(final Node currentNode) {
    VehicleStatus _status = this.owner.getStatus();
    boolean _equals = Objects.equal(_status, VehicleStatus.moving);
    if (_equals) {
      this.owner.setCurrentLocation(currentNode);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      String _agentName = this.owner.getAgentName();
      VehicleAgentUpdateEvent _vehicleAgentUpdateEvent = new VehicleAgentUpdateEvent(currentNode, _agentName);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_vehicleAgentUpdateEvent);
    }
  }
  
  @Override
  public void stop() {
    this.owner.setStatus(VehicleStatus.stopped);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("stopped");
  }
  
  @Override
  public void waiting(final String reason) {
    this.owner.setStatus(VehicleStatus.waiting);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info((("waiting for " + reason) + " reason"));
  }
  
  @Override
  public void charging() {
    this.owner.setStatus(VehicleStatus.charging);
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
  
  @SyntheticMember
  public MovingSkill() {
    super();
  }
  
  @SyntheticMember
  public MovingSkill(final Agent agent) {
    super(agent);
  }
}
