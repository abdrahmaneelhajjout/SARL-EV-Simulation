package SARL;

import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;
import SARL.map.JXMapViewerExample;
import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SarlSpecification("0.12")
@SarlElementType(10)
@SuppressWarnings("all")
public class StartUp {
  public static void main(final String... arguments) {
    try {
      SREBootstrap bootstrap = SRE.getBootstrap();
      Node startNode = GeoLocationService.getNearestNode().convertNode();
      Node endNode = GeoLocationService.getRandomNodeFromWayWithinRadius(1000).convertNode();
      JXMapViewerExample frame = new JXMapViewerExample();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  public StartUp() {
    super();
  }
}
