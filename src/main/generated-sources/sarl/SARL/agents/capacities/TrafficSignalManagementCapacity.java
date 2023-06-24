package SARL.agents.capacities;

import SARL.agents.TrafficSignalSkill;
import SARL.agents.utils.TrafficLight;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;

@DefaultSkill(TrafficSignalSkill.class)
@FunctionalInterface
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface TrafficSignalManagementCapacity extends Capacity {
  void changeColor(final TrafficLight newColor);
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends TrafficSignalManagementCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements TrafficSignalManagementCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void changeColor(final TrafficLight newColor) {
      try {
        ensureCallerInLocalThread();
        this.capacity.changeColor(newColor);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
