package SARL.map;

import javafx.util.Pair;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.WaypointPainter;

import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class JXMapViewerExample extends JFrame {

	private JXMapViewer mapViewer;
	
	private Set<CarWaypoint> waypoints = new HashSet<>();
	private WaypointPainter<CarWaypoint> waypointPainter = new WaypointPainter<>();

	    public JXMapViewerExample() {
        
        super("JXMapViewer Example");

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);
        // create a mapViewer
        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        Pair<Double, Double> currentLocation = GeoLocationService.getCurrentLocationAsPair().get();
        GeoPosition currentPosition = new GeoPosition(currentLocation.getKey(), currentLocation.getValue());

        // Set the initial waypoint
        waypoints.add(new CarWaypoint(currentPosition, "agent"));
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new CarWaypointRenderer());

        mapViewer.setOverlayPainter(waypointPainter);

        // Add interactions
        addInteractions();

        // Set the focus
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(currentPosition);

        // Display the viewer in a JFrame
        this.setLayout(new BorderLayout());
        this.add(mapViewer, BorderLayout.CENTER);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
	private void addInteractions() {
		// Add interactions
		mapViewer.addMouseListener(new PanMouseInputListener(mapViewer));
		//mapViewer.addMouseMotionListener(new PanMouseInputListener(mapViewer));
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
	}

	public void onAgentUpdate(Pair<Double, Double> newLocation) {
		waypoints.clear();
		System.err.println(newLocation.getKey()+"--"+ newLocation.getValue());
		GeoPosition newPosition = new GeoPosition(newLocation.getKey(), newLocation.getValue());
		waypoints.add(new CarWaypoint(newPosition, "name"));
		waypointPainter.setWaypoints(waypoints);
		mapViewer.repaint();
	}

	// Simulates agent movement. Replace this with actual agent update in your
	// application
	public void changeWaypointMovement(Node current_location_node) {
		double latitude = current_location_node.getLatitude();
		double longitude = current_location_node.getLongitude();
		onAgentUpdate(new Pair<>(latitude, longitude));
	}

	public static void main(String[] args) {
		try {
	        SwingUtilities.invokeLater(JXMapViewerExample::new);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
