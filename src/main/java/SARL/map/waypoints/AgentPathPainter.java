package SARL.map.waypoints;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;

public class AgentPathPainter implements Painter<JXMapViewer> {
    private final Map<String, List<GeoPosition>> agentPaths;
    private final Map<String, Color> agentColors;

    public AgentPathPainter(Map<String, List<GeoPosition>> agentPaths, Map<String, Color> agentColors) {
        this.agentPaths = agentPaths;
        this.agentColors = agentColors;
    }
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();
        //convert from viewport to world bitmap
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);
        
        g.setStroke(new BasicStroke(3)); // Set the line width to 3

        for (String agentName : agentPaths.keySet()) {
            g.setColor(agentColors.get(agentName));
            List<GeoPosition> path = agentPaths.get(agentName);
            int lastX = -1, lastY = -1;
            for (GeoPosition gp : path) {
                Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
                if (lastX != -1 && lastY != -1) {
                    g.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
                }
                lastX = (int) pt.getX();
                lastY = (int) pt.getY();
            }
        }

        g.dispose();
    }

}
