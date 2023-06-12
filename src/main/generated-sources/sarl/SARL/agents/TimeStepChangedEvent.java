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
public class TimeStepChangedEvent extends Event {
  public final Long timeStep;
  
  public TimeStepChangedEvent(final Long timeStep) {
    this.timeStep = timeStep;
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
    TimeStepChangedEvent other = (TimeStepChangedEvent) obj;
    if (other.timeStep == null) {
      if (this.timeStep != null)
        return false;
    } else if (this.timeStep == null)
      return false;
    if (other.timeStep != null && other.timeStep.longValue() != this.timeStep.longValue())
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.timeStep);
    return result;
  }
  
  /**
   * Returns a String representation of the TimeStepChangedEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("timeStep", this.timeStep);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1612468258L;
}
