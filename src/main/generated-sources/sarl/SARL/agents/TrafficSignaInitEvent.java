package SARL.agents;

import SARL.agents.TrafficLightStatus;
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
public class TrafficSignaInitEvent extends Event {
  public final Node location;
  
  public final String agentName;
  
  public final TrafficLightStatus color;
  
  public TrafficSignaInitEvent(final Node location, final String agentName, final TrafficLightStatus color) {
    this.location = location;
    this.agentName = agentName;
    this.color = color;
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
    TrafficSignaInitEvent other = (TrafficSignaInitEvent) obj;
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
   * Returns a String representation of the TrafficSignaInitEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("location", this.location);
    builder.add("agentName", this.agentName);
    builder.add("color", this.color);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1022185591L;
}
