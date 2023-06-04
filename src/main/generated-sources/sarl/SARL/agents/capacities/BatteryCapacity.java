package SARL.agents.capacities;

import SARL.agents.BatterySkill;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;

@DefaultSkill(BatterySkill.class)
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface BatteryCapacity extends Capacity {
  void chargeBattery();
  
  double checkBatteryStatus();
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends BatteryCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements BatteryCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void chargeBattery() {
      try {
        ensureCallerInLocalThread();
        this.capacity.chargeBattery();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public double checkBatteryStatus() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.checkBatteryStatus();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
