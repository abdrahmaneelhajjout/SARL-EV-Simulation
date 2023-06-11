package SARL.map.waypoints;

import java.util.Random;

import javax.swing.ImageIcon;
import javafx.util.Pair;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;


import SARL.agents.TrafficLightStatus;

public class TrafficSignalWaypoint  extends DefaultWaypoint {
	 private String name;
	  private ImageIcon icon;

	    public TrafficSignalWaypoint(GeoPosition coord, String name, TrafficLightStatus lightStatus) {
	        super(coord);
	    	ImageIcon traffic_png = new ImageIcon(getClass().getClassLoader().getResource(lightStatus.getImageIconName()));
	        this.name = name;
	        this.icon = traffic_png;
	    }

	    public String getName() {
	        return name;
	    }

	    public ImageIcon getIcon() {
	        return icon;
	    }
	    public void setIcon(ImageIcon icon) {
	        this.icon = icon;
	    }
	    public void changeIcon(String path) {
	        this.icon = new ImageIcon(getClass().getClassLoader().getResource(path));
	    }
	    
}
