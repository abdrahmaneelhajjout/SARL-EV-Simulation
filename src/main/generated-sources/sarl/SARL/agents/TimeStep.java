package SARL.agents;

import io.sarl.lang.annotation.DefaultValue;
import io.sarl.lang.annotation.DefaultValueSource;
import io.sarl.lang.annotation.DefaultValueUse;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSourceCode;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.12")
@SarlElementType(15)
@SuppressWarnings("all")
public class TimeStep extends Event {
  public final long step;
  
  @DefaultValueSource
  public TimeStep(@DefaultValue("SARL.agents.TimeStep#NEW_0") final long step) {
    this.step = step;
  }
  
  /**
   * Default value for the parameter step
   */
  @Pure
  @SyntheticMember
  @SarlSourceCode("1")
  private static long $DEFAULT_VALUE$NEW_0() {
    return 1;
  }
  
  @DefaultValueUse("long")
  @SyntheticMember
  public TimeStep() {
    this($DEFAULT_VALUE$NEW_0());
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
    TimeStep other = (TimeStep) obj;
    if (other.step != this.step)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Long.hashCode(this.step);
    return result;
  }
  
  /**
   * Returns a String representation of the TimeStep event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("step", this.step);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = 698395889L;
}
