package SARL.agents;

import SARL.agents.VehiculeAgent;
import SARL.agents.capacities.BatteryCapacity;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Skill;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.12")
@SarlElementType(22)
@SuppressWarnings("all")
public class BatterySkill extends Skill implements BatteryCapacity {
  private VehiculeAgent owner;
  
  public void install() {
    class $AssertEvaluator$ {
      final boolean $$result;
      $AssertEvaluator$() {
        Agent _owner = BatterySkill.this.getOwner();
        this.$$result = (_owner != null);
      }
    }
    assert new $AssertEvaluator$().$$result;
    Agent _owner = this.getOwner();
    this.owner = ((VehiculeAgent) _owner);
  }
  
  @Override
  public void chargeBattery() {
    this.owner.setBatteryLevel(100);
  }
  
  @Override
  public double checkBatteryStatus() {
    return this.owner.getBatteryLevel();
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
  public BatterySkill() {
    super();
  }
  
  @SyntheticMember
  public BatterySkill(final Agent agent) {
    super(agent);
  }
}
