package SARL.map;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class CarWaypoint extends DefaultWaypoint {
	 private String name;
	    private ImageIcon icon;

	    public CarWaypoint(GeoPosition coord, String name) {
	        super(coord);
	    	ImageIcon car_png = new ImageIcon(getClass().getClassLoader().getResource("car_waypointer.png"));
	        this.name = name;
	        this.icon = car_png;
	    }

	    public String getName() {
	        return name;
	    }

	    public ImageIcon getIcon() {
	        return icon;
	    }
}
