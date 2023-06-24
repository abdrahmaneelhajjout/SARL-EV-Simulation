package SARL;

import SARL.agents.ChargeStationAgent;
import SARL.agents.EnvironmentAgent;
import SARL.agents.VehicleAgent;
import SARL.agents.utils.TrafficLightStatus;
import SARL.agents.utils.geolocation.GeoLocationService;
import SARL.agents.utils.geolocation.mapbox.Node;
import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SarlSpecification("0.12")
@SarlElementType(10)
@SuppressWarnings("all")
public class StartUp {
  public static void main(final String... arguments) {
    try {
      Node marrakech_node = new Node(31.648711, (-8.013689));
      double radiusInKm = 1.5;
      double priceInDhPerKwh = 1.5d;
      SREBootstrap bootstrap = SRE.getBootstrap();
      Pair<Node, Node> location_pair1 = GeoLocationService.getRandomStartAndDestination(marrakech_node, radiusInKm);
      Pair<Node, Node> location_pair2 = GeoLocationService.getRandomStartAndDestination(marrakech_node, radiusInKm);
      Pair<Node, Node> location_pair3 = GeoLocationService.getRandomStartAndDestination(marrakech_node, radiusInKm);
      List<Node> lightsNode = GeoLocationService.getTrafficSignsFromOverpass(marrakech_node, 1);
      Long _long = new Long(1000);
      bootstrap.startAgent(EnvironmentAgent.class, _long, marrakech_node);
      Node randomNode = GeoLocationService.getRandomNodeFromWayWithinRadius(marrakech_node, radiusInKm).convertNode();
      Node randomNode2 = GeoLocationService.getRandomNodeFromWayWithinRadius(marrakech_node, radiusInKm).convertNode();
      Node randomNode3 = GeoLocationService.getRandomNodeFromWayWithinRadius(marrakech_node, radiusInKm).convertNode();
      Node randomNode4 = GeoLocationService.getRandomNodeFromWayWithinRadius(marrakech_node, radiusInKm).convertNode();
      bootstrap.startAgent(ChargeStationAgent.class, "station1", randomNode, Double.valueOf(priceInDhPerKwh));
      Double _double = new Double(40);
      bootstrap.startAgent(VehicleAgent.class, "agent1", location_pair1, _double);
      Map<TrafficLightStatus, Long> trafficMap = new HashMap<TrafficLightStatus, Long>();
      Long _long_1 = new Long(3000);
      trafficMap.put(TrafficLightStatus.GREEN, _long_1);
      Long _long_2 = new Long(3000);
      trafficMap.put(TrafficLightStatus.RED, _long_2);
      Long _long_3 = new Long(3000);
      trafficMap.put(TrafficLightStatus.ORANGE, _long_3);
      int i = 1;
      for (final Node node : lightsNode) {
        i++;
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  public StartUp() {
    super();
  }
}
