package SARL.agents;

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
public class TrafficColorChangedEvent extends Event {
  public final String agentName;
  
  public final String colorIcon;
  
  public TrafficColorChangedEvent(final String colorIcon, final String agentName) {
    this.colorIcon = colorIcon;
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
    TrafficColorChangedEvent other = (TrafficColorChangedEvent) obj;
    if (!Objects.equals(this.agentName, other.agentName))
      return false;
    if (!Objects.equals(this.colorIcon, other.colorIcon))
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
    result = prime * result + Objects.hashCode(this.colorIcon);
    return result;
  }
  
  /**
   * Returns a String representation of the TrafficColorChangedEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("agentName", this.agentName);
    builder.add("colorIcon", this.colorIcon);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 3544717593L;
}
