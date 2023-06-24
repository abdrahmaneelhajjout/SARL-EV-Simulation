package SARL.agents.capacities;

import SARL.agents.MovingSkill;
import SARL.agents.utils.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;

/**
 * @author ELHAJJOUT
 */
@DefaultSkill(MovingSkill.class)
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface MovingCapacity extends Capacity {
  void move(final Node destination);
  
  void stop();
  
  void waiting(final String reason);
  
  void charging();
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends MovingCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements MovingCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public void move(final Node destination) {
      try {
        ensureCallerInLocalThread();
        this.capacity.move(destination);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void stop() {
      try {
        ensureCallerInLocalThread();
        this.capacity.stop();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void waiting(final String reason) {
      try {
        ensureCallerInLocalThread();
        this.capacity.waiting(reason);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public void charging() {
      try {
        ensureCallerInLocalThread();
        this.capacity.charging();
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
