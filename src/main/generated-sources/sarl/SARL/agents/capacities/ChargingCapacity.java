package SARL.agents.capacities;

import SARL.agents.ChargingCapacitySkill;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;
import org.eclipse.xtext.xbase.lib.Pure;

@DefaultSkill(ChargingCapacitySkill.class)
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface ChargingCapacity extends Capacity {
  @Pure
  int getBatteryLevel();
  
  void goCharge();
  
  @Pure
  int getBatteryCapacity();
  
  @Pure
  int getBatteryChargeCapacity();
  
  void updateBatteryLevel(final int amount);
  
  void setBatteryLevel(final int amount);
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends ChargingCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements ChargingCapacity {
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
    
    public void goCharge() {
      try {
        ensureCallerInLocalThread();
        this.capacity.goCharge();
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
    
    public int getBatteryChargeCapacity() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getBatteryChargeCapacity();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void updateBatteryLevel(final int amount) {
      try {
        ensureCallerInLocalThread();
        this.capacity.updateBatteryLevel(amount);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void setBatteryLevel(final int amount) {
      try {
        ensureCallerInLocalThread();
        this.capacity.setBatteryLevel(amount);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
