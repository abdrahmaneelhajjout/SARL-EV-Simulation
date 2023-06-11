package SARL.agents.capacities;

import SARL.agents.GetLocationSkill;
import SARL.agents.geolocation.mapbox.Node;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.core.AgentTrait;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.DefaultSkill;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

@DefaultSkill(GetLocationSkill.class)
@SarlSpecification("0.12")
@SarlElementType(20)
@SuppressWarnings("all")
public interface GeoLocationCapacity extends Capacity {
  @Pure
  Node getCurrentLocation();
  
  @Pure
  List<Node> getRouteToDestination(final Node source, final Node destination);
  
  @Pure
  List<Node> getRouteToDestination(final Node source, final Node viaNode, final Node destination);
  
  /**
   * @ExcludeFromApidoc
   */
  class ContextAwareCapacityWrapper<C extends GeoLocationCapacity> extends Capacity.ContextAwareCapacityWrapper<C> implements GeoLocationCapacity {
    public ContextAwareCapacityWrapper(final C capacity, final AgentTrait caller) {
      super(capacity, caller);
    }
    
    public Node getCurrentLocation() {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getCurrentLocation();
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public List<Node> getRouteToDestination(final Node source, final Node destination) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getRouteToDestination(source, destination);
      } finally {
        resetCallerInLocalThread();
      }
    }
    
    public List<Node> getRouteToDestination(final Node source, final Node viaNode, final Node destination) {
      try {
        ensureCallerInLocalThread();
        return this.capacity.getRouteToDestination(source, viaNode, destination);
      } finally {
        resetCallerInLocalThread();
      }
    }
  }
}
