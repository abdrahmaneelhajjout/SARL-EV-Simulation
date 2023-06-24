package SARL.agents;

import SARL.agents.TrafficSignalAgent;
import SARL.agents.capacities.TrafficSignalManagementCapacity;
import SARL.agents.utils.TrafficLight;
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
public class TrafficSignalSkill extends Skill implements TrafficSignalManagementCapacity {
  private TrafficSignalAgent owner;
  
  public void install() {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        Agent _owner = TrafficSignalSkill.this.getOwner();
        this.$$result = (_owner != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    Agent _owner = this.getOwner();
    this.owner = ((TrafficSignalAgent) _owner);
  }
  
  @Override
  public void changeColor(final TrafficLight newColor) {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        this.$$result = (TrafficSignalSkill.this.owner != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    this.owner.setTrafficLightStatus(newColor);
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
  public TrafficSignalSkill() {
    super();
  }
  
  @SyntheticMember
  public TrafficSignalSkill(final Agent agent) {
    super(agent);
  }
}
