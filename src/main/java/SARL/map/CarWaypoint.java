package SARL.map;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class CarWaypoint extends ParentWaypoint {

	    public CarWaypoint(GeoPosition coord, String name) {
	        super(coord, name, new ImageIcon(CarWaypoint.class.getClassLoader().getResource("car_waypointer.png")));

	    }

}
