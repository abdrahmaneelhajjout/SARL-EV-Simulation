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

import SARL.agents.TrafficLightStatus;
import SARL.agents.geolocation.GeoLocationService;
import SARL.agents.geolocation.mapbox.Node;
import SARL.map.waypoints.AgentPathPainter;
import SARL.map.waypoints.CarWaypoint;
import SARL.map.waypoints.CarWaypointRenderer;
import SARL.map.waypoints.ChargeStationWaypoint;
import SARL.map.waypoints.ChargeStationWaypointRenderer;
import SARL.map.waypoints.TrafficSignalWaypoint;
import SARL.map.waypoints.TrafficSignalWaypointRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JXMapViewerFrame extends JFrame {

	private JXMapViewer mapViewer;

	private Set<CarWaypoint> carWaypoints = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private WaypointPainter<CarWaypoint> carWaypointPainter = new WaypointPainter<>();

	private Set<TrafficSignalWaypoint> trafficSignalWaypoints = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private WaypointPainter<TrafficSignalWaypoint> trafficSignalWaypointPainter = new WaypointPainter<>();

	private Set<ChargeStationWaypoint> chargeStationWaypoints = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private WaypointPainter<ChargeStationWaypoint> chargeStationWaypointPainter = new WaypointPainter<>();

	public Map<Node, String> chargeStationNodes = new HashMap<>();

	// maping each car waypoint to the name of the agent it belongs
	private Map<String, CarWaypoint> CarAgentWaypoints = new ConcurrentHashMap<>();
	// maping each traffic signal waypoint to the name of the agent it belongs
	private Map<String, TrafficSignalWaypoint> trafficAgentWaypoints = new ConcurrentHashMap<>();

	private Map<String, List<GeoPosition>> agentPaths = new ConcurrentHashMap<>();

	private List<Color> availableColors = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN,
			Color.MAGENTA, Color.YELLOW, Color.GRAY);
	private Map<String, Color> agentColors = new ConcurrentHashMap<>();

	private AtomicInteger colorIndex = new AtomicInteger();
	AgentPathPainter agentPathPainter = new AgentPathPainter(agentPaths, agentColors);

	public JXMapViewerFrame(Node location) {
		super("Simulation");

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(12);
		// create a mapViewer
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(tileFactory);
		GeoPosition currentPosition = new GeoPosition(location.getLatitude(), location.getLongitude());

		// init waypointers
		initCarWaypoints();
		initTrafficSignalWaypoints();
		initChargeStationWaypoints();
		CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(carWaypointPainter,
				chargeStationWaypointPainter, agentPathPainter, trafficSignalWaypointPainter);
		mapViewer.setOverlayPainter(compoundPainter);
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

	private void initCarWaypoints() {
		// Set the initial waypoint
		Set<CarWaypoint> copy = new HashSet<>(carWaypoints);
		carWaypointPainter.setWaypoints(copy);
		carWaypointPainter.setRenderer(new CarWaypointRenderer());
	}

	private void initChargeStationWaypoints() {
		// Set the initial waypoint
		Set<ChargeStationWaypoint> copy = new HashSet<>(chargeStationWaypoints);
		chargeStationWaypointPainter.setWaypoints(copy);
		chargeStationWaypointPainter.setRenderer(new ChargeStationWaypointRenderer());
	}

	private void initTrafficSignalWaypoints() {
		// Set the initial waypoint
		Set<TrafficSignalWaypoint> copy = new HashSet<>(trafficSignalWaypoints);
		trafficSignalWaypointPainter.setWaypoints(copy);
		trafficSignalWaypointPainter.setRenderer(new TrafficSignalWaypointRenderer());
	}

	private void addInteractions() {
		// Add interactions
		mapViewer.addMouseListener(new PanMouseInputListener(mapViewer));
		mapViewer.addMouseMotionListener(new PanMouseInputListener(mapViewer));
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
	}

	public void onFullPathUpdate(String agentName, List<Node> newPath) {
		List<GeoPosition> geoPath = newPath.stream()
				.map(node -> new GeoPosition(node.getLatitude(), node.getLongitude())).collect(Collectors.toList());
		agentPaths.put(agentName, geoPath);
		mapViewer.repaint();
	}

	public void onTrafficSignalChanged(String agentName, String newIconPath) {
		// If this traffic signal agent already has a waypoint, update it
		try {
			TrafficSignalWaypoint waypoint = trafficAgentWaypoints.get(agentName);
			waypoint.changeIcon(newIconPath);
			trafficSignalWaypointPainter.setWaypoints(trafficSignalWaypoints);
			mapViewer.repaint();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void onChargeStationIniti(Node location, String agentName) {

		GeoPosition newPosition = new GeoPosition(location.getLatitude(), location.getLongitude());
		// Create a new waypoint and add it to the set
		ChargeStationWaypoint newWaypoint = new ChargeStationWaypoint(newPosition, agentName);
		chargeStationNodes.put(location, agentName);
		chargeStationWaypoints.add(newWaypoint);
		// Create a copy of the trafficSignalWaypoints set
		Set<ChargeStationWaypoint> copy = new HashSet<>(chargeStationWaypoints);
		chargeStationWaypointPainter.setWaypoints(copy);

		mapViewer.repaint();
	}

	public void onAgentUpdate(Node newLocation, String agentName) {
		GeoPosition newPosition = new GeoPosition(newLocation.getLatitude(), newLocation.getLongitude());

		// If this agent already has a waypoint, remove it from the set
		if (CarAgentWaypoints.containsKey(agentName)) {
			carWaypoints.remove(CarAgentWaypoints.get(agentName));
		}

		// If agent doesn't have a color assigned yet, assign it one.
		if (!agentColors.containsKey(agentName)) {
			Color agentColor = availableColors.get(colorIndex.getAndIncrement() % availableColors.size());
			agentColors.put(agentName, agentColor);
		}

		// Create a new waypoint and add it to the set
		CarWaypoint newWaypoint = new CarWaypoint(newPosition, agentName);
		carWaypoints.add(newWaypoint);

		// Update the mapping
		CarAgentWaypoints.put(agentName, newWaypoint);

		// Only update path if it is not set by the onFullPathUpdate method.
		if (!agentPaths.containsKey(agentName)) {
			// If the agent already has a path, get it. Otherwise, create a new one.
			List<GeoPosition> path = new ArrayList<>();
			path.add(newPosition);
			agentPaths.put(agentName, path);
		}

		// Create a copy of the carWaypoints set
		Set<CarWaypoint> copy = new HashSet<>(carWaypoints);
		carWaypointPainter.setWaypoints(copy);

		mapViewer.repaint();
	}

	// when agent is spawn or initialized it triggers this method to set itself on
	// the map
	public void onTrafficAgentInit(Node location, String agentName, TrafficLightStatus status) {
		GeoPosition newPosition = new GeoPosition(location.getLatitude(), location.getLongitude());
		// Create a new waypoint and add it to the set
		TrafficSignalWaypoint newWaypoint = new TrafficSignalWaypoint(newPosition, agentName, status);
		trafficAgentWaypoints.put(agentName, newWaypoint); // Add this line
		trafficSignalWaypoints.add(newWaypoint);
		// Create a copy of the trafficSignalWaypoints set
		Set<TrafficSignalWaypoint> copy = new HashSet<>(trafficSignalWaypoints);
		trafficSignalWaypointPainter.setWaypoints(copy);
		mapViewer.repaint();
	}

	public void setTrafficSignalPosition(Node current_location_node, String agentName) {
		double latitude = current_location_node.getLatitude();
		double longitude = current_location_node.getLongitude();
	}



}
