package SARL.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class CarWaypointRenderer implements WaypointRenderer<CarWaypoint>{
    
	@Override
    public void paintWaypoint(Graphics2D g, JXMapViewer viewer, CarWaypoint w) {
        Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(), viewer.getZoom());
        int x = (int) point.getX();
        int y = (int) point.getY();
        
        ImageIcon icon = w.getIcon();
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        
        g.drawImage(icon.getImage(), x, y, null);

        String name = w.getName();
        g.setColor(Color.BLACK);
        g.drawString(name, x, y); // Adjust these values to position the text correctly on your icon
    }
}
