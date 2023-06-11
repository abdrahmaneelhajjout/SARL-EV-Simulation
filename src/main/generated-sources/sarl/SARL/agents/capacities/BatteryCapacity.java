package SARL.agents.capacities;

import SARL.agents.BatterySkill;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;
import org.eclipse.xtext.xbase.lib.Pure;

@DefaultSkill(BatterySkill.class)
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface BatteryCapacity extends Capacity {
  @Pure
  int getBatteryLevel();
  
  void setBatteryLevel(final int level);
  
  @Pure
  int getBatteryCapacity();
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends BatteryCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements BatteryCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public int getBatteryLevel() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getBatteryLevel();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void setBatteryLevel(final int level) {
      try {
        ensureCallerInLocalThread();
        this.capacity.setBatteryLevel(level);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public int getBatteryCapacity() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getBatteryCapacity();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
