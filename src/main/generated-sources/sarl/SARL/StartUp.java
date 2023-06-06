package SARL;

import SARL.agents.EnvironmentAgent;
import SARL.agents.TrafficLightStatus;
import SARL.agents.TrafficSignalAgent;
import SARL.agents.VehiculeAgent;
import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;
import SARL.agents.geolocation.mapbox.NodeUtils;
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
      SREBootstrap bootstrap = SRE.getBootstrap();
      Pair<Node, Node> location_pair1 = GeoLocationService.getRandomStartAndDestination(10);
      Pair<Node, Node> location_pair2 = GeoLocationService.getRandomStartAndDestination(10);
      Pair<Node, Node> location_pair3 = GeoLocationService.getRandomStartAndDestination(10);
      List<Node> lightsNode = GeoLocationService.getTrafficSignsFromOverpass(1);
      lightsNode = NodeUtils.<Node>randomSubList(lightsNode, 3);
      Long _long = new Long(1000);
      bootstrap.startAgent(EnvironmentAgent.class, _long);
      Double _double = new Double(100);
      bootstrap.startAgent(VehiculeAgent.class, "agent1", location_pair1, _double);
      Double _double_1 = new Double(40);
      bootstrap.startAgent(VehiculeAgent.class, "agent2", location_pair2, _double_1);
      Map<TrafficLightStatus, Long> trafficMap = new HashMap<TrafficLightStatus, Long>();
      Long _long_1 = new Long(5000);
      trafficMap.put(TrafficLightStatus.GREEN, _long_1);
      Long _long_2 = new Long(3000);
      trafficMap.put(TrafficLightStatus.RED, _long_2);
      Long _long_3 = new Long(2);
      trafficMap.put(TrafficLightStatus.ORANGE, _long_3);
      int i = 1;
      for (final Node node : lightsNode) {
        {
          bootstrap.startAgent(TrafficSignalAgent.class, ("TrafficAgent" + Integer.valueOf(i)), node, trafficMap);
          break;
        }
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
