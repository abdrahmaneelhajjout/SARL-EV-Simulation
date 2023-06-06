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
public class AgentUpdateEvent extends Event {
  public final Node current_location_node;
  
  public final String agentName;
  
  public AgentUpdateEvent(final Node current_location_node, final String agentName) {
    this.current_location_node = current_location_node;
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
    AgentUpdateEvent other = (AgentUpdateEvent) obj;
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
   * Returns a String representation of the AgentUpdateEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("current_location_node", this.current_location_node);
    builder.add("agentName", this.agentName);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 1933074071L;
}
