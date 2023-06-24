package SARL.agents;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class ChargeRequestEvent extends Event {
  public final long currentBatteryLevel;
  
  public final long batteryCapacity;
  
  public final long BatteryChargeCapacity;
  
  public ChargeRequestEvent(final long currentBatteryLevel, final long batteryCapacity, final long ChargeBatteryCapacity) {
    this.currentBatteryLevel = currentBatteryLevel;
    this.batteryCapacity = batteryCapacity;
    this.BatteryChargeCapacity = ChargeBatteryCapacity;
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
    ChargeRequestEvent other = (ChargeRequestEvent) obj;
    if (other.currentBatteryLevel != this.currentBatteryLevel)
      return false;
    if (other.batteryCapacity != this.batteryCapacity)
      return false;
    if (other.BatteryChargeCapacity != this.BatteryChargeCapacity)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.currentBatteryLevel);
    result = prime * result + Long.hashCode(this.batteryCapacity);
    result = prime * result + Long.hashCode(this.BatteryChargeCapacity);
    return result;
  }
  
  /**
   * Returns a String representation of the ChargeRequestEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("currentBatteryLevel", this.currentBatteryLevel);
    builder.add("batteryCapacity", this.batteryCapacity);
    builder.add("BatteryChargeCapacity", this.BatteryChargeCapacity);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 572569775L;
}
