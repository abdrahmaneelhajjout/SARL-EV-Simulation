package SARL.agents;

import SARL.agents.utils.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class FullPathUpdateEvent extends Event {
  public final String agentName;
  
  public final List<Node> newPath;
  
  public FullPathUpdateEvent(final String agentName, final List<Node> newPath) {
    this.agentName = agentName;
    this.newPath = newPath;
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
    FullPathUpdateEvent other = (FullPathUpdateEvent) obj;
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
   * Returns a String representation of the FullPathUpdateEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("agentName", this.agentName);
    builder.add("newPath", this.newPath);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -120522498L;
}
