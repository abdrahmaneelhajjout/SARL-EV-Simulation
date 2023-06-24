package SARL.agents;

import SARL.agents.VehicleAgent;
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
  private VehicleAgent owner;
  
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
    this.owner = ((VehicleAgent) _owner);
  }
  
  @Pure
  public long getBatteryLevel() {
    return this.owner.getBatteryLevel();
  }
  
  public void setBatteryLevel(final long level) {
    long _batteryLevel = this.owner.getBatteryLevel();
    if ((level <= _batteryLevel)) {
      this.owner.setBatteryLevel(level);
    }
  }
  
  @Pure
  public long getBatteryCapacity() {
    return this.owner.getBatteryCapacity();
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
