package SARL.map;

import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class ParentWaypoint extends DefaultWaypoint {
	 private String name;
	  private ImageIcon icon;



	
    public ParentWaypoint(GeoPosition coord, String name, ImageIcon icon) {
		super(coord);
		this.name = name;
		this.icon = icon;
	}

	public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

}
