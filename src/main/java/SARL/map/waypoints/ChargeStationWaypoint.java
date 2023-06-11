package SARL.map.waypoints;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class ChargeStationWaypoint extends DefaultWaypoint {
	 private String name;
	  private ImageIcon icon;

	    public ChargeStationWaypoint(GeoPosition coord, String name) {
	        super(coord);
	    	ImageIcon charge_png = new ImageIcon(getClass().getClassLoader().getResource("charge_station_pointer.png"));
	        this.name = name;
	        this.icon = charge_png;
	    }

	    public String getName() {
	        return name;
	    }

	    public ImageIcon getIcon() {
	        return icon;
	    }
}
