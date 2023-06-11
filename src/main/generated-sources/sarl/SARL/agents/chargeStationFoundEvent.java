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
public class chargeStationFoundEvent extends Event {
  public final Node closestStation;
  
  public final String stationAgentName;
  
  public chargeStationFoundEvent(final Node closestStation, final String stationAgentName) {
    this.closestStation = closestStation;
    this.stationAgentName = stationAgentName;
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
    chargeStationFoundEvent other = (chargeStationFoundEvent) obj;
    if (!Objects.equals(this.stationAgentName, other.stationAgentName))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.stationAgentName);
    return result;
  }
  
  /**
   * Returns a String representation of the chargeStationFoundEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("closestStation", this.closestStation);
    builder.add("stationAgentName", this.stationAgentName);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 7617274968L;
}
