package SARL.agents;

import SARL.agents.capacities.GetLocation;
import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;
import SARL.agents.geolocation.mapbox.RoutingService;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.Skill;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import javafx.util.Pair;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SarlSpecification("0.12")
@SarlElementType(22)
@SuppressWarnings("all")
public class GetLocationSkill extends Skill implements GetLocation {
  @Override
  public Node getCurrentLocation() {
    try {
      Optional<Pair<Double, Double>> optionalLocation = GeoLocationService.getCurrentLocationAsPair();
      final Supplier<RuntimeException> _function = () -> {
        return new RuntimeException("Can\'t get the IP Address, check your connection");
      };
      Pair<Double, Double> locationPair = optionalLocation.<RuntimeException>orElseThrow(_function);
      Double _key = locationPair.getKey();
      Double _value = locationPair.getValue();
      Node current_location_node = new Node(((_key) == null ? 0 : (_key).doubleValue()), ((_value) == null ? 0 : (_value).doubleValue()));
      return current_location_node;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public List<Node> getRouteToDestination(final Node source, final Node destination) {
    try {
      List<Node> path = RoutingService.getRoute(source, destination);
      return path;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  public GetLocationSkill() {
    super();
  }
  
  @SyntheticMember
  public GetLocationSkill(final Agent agent) {
    super(agent);
  }
}