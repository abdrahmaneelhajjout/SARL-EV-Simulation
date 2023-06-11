package SARL.agents;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class ChargeRequestEvent extends Event {
  public UUID vehicleId;
  
  public int currentBatteryLevel;
  
  public int ChargeBatteryCapacity;
  
  @SyntheticMember
  public ChargeRequestEvent() {
    super();
  }
  
  @SyntheticMember
  public ChargeRequestEvent(final Address source) {
    super(source);
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
    if (!Objects.equals(this.vehicleId, other.vehicleId))
      return false;
    if (other.currentBatteryLevel != this.currentBatteryLevel)
      return false;
    if (other.ChargeBatteryCapacity != this.ChargeBatteryCapacity)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.vehicleId);
    result = prime * result + Integer.hashCode(this.currentBatteryLevel);
    result = prime * result + Integer.hashCode(this.ChargeBatteryCapacity);
    return result;
  }
  
  /**
   * Returns a String representation of the ChargeRequestEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("vehicleId", this.vehicleId);
    builder.add("currentBatteryLevel", this.currentBatteryLevel);
    builder.add("ChargeBatteryCapacity", this.ChargeBatteryCapacity);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -522360079L;
}
