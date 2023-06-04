package SARL.agents;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;

/**
 * @author ELHAJJOUT
 */
@SarlSpecification("0.12")
@SarlElementType(12)
@SuppressWarnings("all")
public enum Status {
  moving,
  
  stopped,
  
  waiting;
}
