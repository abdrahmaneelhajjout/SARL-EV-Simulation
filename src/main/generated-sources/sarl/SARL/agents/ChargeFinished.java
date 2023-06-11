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
public class ChargeFinished extends Event {
  public UUID vehicleId;
  
  @SyntheticMember
  public ChargeFinished() {
    super();
  }
  
  @SyntheticMember
  public ChargeFinished(final Address source) {
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
    ChargeFinished other = (ChargeFinished) obj;
    if (!Objects.equals(this.vehicleId, other.vehicleId))
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
    return result;
  }
  
  /**
   * Returns a String representation of the ChargeFinished event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("vehicleId", this.vehicleId);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1329698544L;
}
