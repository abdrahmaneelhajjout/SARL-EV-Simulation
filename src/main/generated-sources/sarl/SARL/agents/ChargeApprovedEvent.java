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
public class ChargeApprovedEvent extends Event {
  public final double totalPrice;
  
  public final long chargeNeeded;
  
  public ChargeApprovedEvent(final double totalPrice, final long chargeNeeded) {
    this.totalPrice = totalPrice;
    this.chargeNeeded = chargeNeeded;
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
    ChargeApprovedEvent other = (ChargeApprovedEvent) obj;
    if (Double.doubleToLongBits(other.totalPrice) != Double.doubleToLongBits(this.totalPrice))
      return false;
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
    result = prime * result + Double.hashCode(this.totalPrice);
    result = prime * result + Long.hashCode(this.chargeNeeded);
    return result;
  }
  
  /**
   * Returns a String representation of the ChargeApprovedEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("totalPrice", this.totalPrice);
    builder.add("chargeNeeded", this.chargeNeeded);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1119530515L;
}
