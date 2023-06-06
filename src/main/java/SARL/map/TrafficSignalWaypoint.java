package SARL.map;

import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class TrafficSignalWaypoint  extends ParentWaypoint {

	    public TrafficSignalWaypoint(GeoPosition coord, String name) {
	        super(coord, name, new ImageIcon(CarWaypoint.class.getClassLoader().getResource("traffic_light_pointer.png")));

	    }


}
