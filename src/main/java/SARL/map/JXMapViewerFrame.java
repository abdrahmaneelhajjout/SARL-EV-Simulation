package SARL.map;

import javafx.util.Pair;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.WaypointPainter;

import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JXMapViewerFrame extends JFrame {

	private JXMapViewer mapViewer;

	private Set<ParentWaypoint> parentWaypoints = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private WaypointPainter<ParentWaypoint> parentWaypointPainter = new WaypointPainter<>();

	// maping each car waypoint to the name of the agent it belongs
	private Map<String, CarWaypoint> agentWaypoints = new ConcurrentHashMap<>();

	public JXMapViewerFrame() {
		super("Simulation");

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(12);
		// create a mapViewer
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(tileFactory);
		Pair<Double, Double> currentLocation = GeoLocationService.getCurrentLocationAsPair().get();
		GeoPosition currentPosition = new GeoPosition(currentLocation.getKey(), currentLocation.getValue());

		// init waypointers
		initWaypoints();

		mapViewer.setOverlayPainter(parentWaypointPainter);
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
		this.setLocationRelativeTo(null);
	}

	private void initWaypoints() {
		// Set the initial waypoint
	    Set<ParentWaypoint> copy = new HashSet<>(parentWaypoints);
	    parentWaypointPainter.setWaypoints(copy);
	    parentWaypointPainter.setRenderer(new ParentWaypointRenderer());
	}



	private void addInteractions() {
		// Add interactions
		mapViewer.addMouseListener(new PanMouseInputListener(mapViewer));
		mapViewer.addMouseMotionListener(new PanMouseInputListener(mapViewer));
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
	}

	public void onAgentUpdate(Node newLocation, String agentName) {

		GeoPosition newPosition = new GeoPosition(newLocation.getLatitude(), newLocation.getLongitude());

		// If this agent already has a waypoint, remove it from the set
		if (agentWaypoints.containsKey(agentName)) {
			parentWaypoints.remove(agentWaypoints.get(agentName));
		}

		// Create a new waypoint and add it to the set
		CarWaypoint newWaypoint = new CarWaypoint(newPosition, agentName);
		parentWaypoints.add(newWaypoint);

		// Update the mapping
		agentWaypoints.put(agentName, newWaypoint);

		// Create a copy of the carWaypoints set
	    Set<ParentWaypoint> copy = new HashSet<>(parentWaypoints);
	    parentWaypointPainter.setWaypoints(copy);

	    mapViewer.repaint();
	}

	// when agent is spawn or initialized it triggers this method to set itself on
	// the map
	public void onTrafficAgentInit(Node location, String agentName) {
		System.out.println("init traffic " + location);
		GeoPosition newPosition = new GeoPosition(location.getLatitude(), location.getLongitude());

		// Create a new waypoint and add it to the set
		TrafficSignalWaypoint newWaypoint = new TrafficSignalWaypoint(newPosition, agentName);

		parentWaypoints.add(newWaypoint);
	    // Create a copy of the trafficSignalWaypoints set
	    Set<ParentWaypoint> copy = new HashSet<>(parentWaypoints);
	    parentWaypointPainter.setWaypoints(copy);

	    mapViewer.repaint();
	}

	public void setTrafficSignalPosition(Node current_location_node, String agentName) {
		double latitude = current_location_node.getLatitude();
		double longitude = current_location_node.getLongitude();
		// onAgentUpdate(new Pair<>(latitude, longitude), agentName);
	}

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(JXMapViewerFrame::new);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
