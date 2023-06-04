package SARL.capacities;

import SARL.agents.LightStatus;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author ELHAJJOUT
 */
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface LightManagementCapacity extends Capacity {
  void changeColor(final LightStatus newColor);
  
  @Pure
  LightStatus getStatus();
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends LightManagementCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements LightManagementCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void changeColor(final LightStatus newColor) {
      try {
        ensureCallerInLocalThread();
        this.capacity.changeColor(newColor);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public LightStatus getStatus() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getStatus();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
