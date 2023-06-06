package SARL.map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class ParentWaypointRenderer implements WaypointRenderer<ParentWaypoint>{
    
	@Override
    public void paintWaypoint(Graphics2D g, JXMapViewer viewer, ParentWaypoint w) {
        Point2D point = viewer.getTileFactory().geoToPixel(w.getPosition(), viewer.getZoom());
        int x = (int) point.getX();
        int y = (int) point.getY();
        
        ImageIcon icon = w.getIcon();
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        int imgHeight = icon.getIconHeight();
        int imgWidth = icon.getIconWidth();
        
        
     // Draw the image such that its bottom is at the waypoint location, rather than the center
        g.drawImage(icon.getImage(), x - imgWidth / 2, y - imgHeight, null);

        String name = w.getName();
        g.setColor(Color.BLACK);
        g.drawString(name, x - (imgWidth / 2), y - imgHeight); // Adjust these values to position the text correctly on your icon
    }
}
