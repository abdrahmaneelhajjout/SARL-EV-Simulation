package SARL.agents;

import SARL.agents.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class ChargeStationInitEvent extends Event {
  public final String agentName;
  
  public final Node location;
  
  public ChargeStationInitEvent(final Node location, final String agentName) {
    this.location = location;
    this.agentName = agentName;
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
    ChargeStationInitEvent other = (ChargeStationInitEvent) obj;
    if (!Objects.equals(this.agentName, other.agentName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.agentName);
    return result;
  }
  
  /**
   * Returns a String representation of the ChargeStationInitEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("agentName", this.agentName);
    builder.add("location", this.location);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 5037401670L;
}
