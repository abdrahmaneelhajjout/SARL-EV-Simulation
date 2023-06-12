package SARL.map;

import javafx.util.Pair;
import scala.collection.mutable.SynchronizedSet;

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

import SARL.agents.TimeStepChangedEvent;
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
import io.sarl.bootstrap.SRE;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Space;
import io.sarl.sre.boot.internal.skills.BuiltinCapacityModule;
import io.sarl.sre.services.context.DefaultContextFactory;
import io.sarl.sre.skills.bic.DefaultContextInteractionsSkill;
import io.sarl.sre.skills.bic.ExternalContextAccessSkill;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JXMapViewerFrame extends JFrame {

	private JXMapViewer mapViewer;
	private AgentContext agentContext;

	private JTextField textField;
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
	private long simulationTimeStep;
	private AtomicInteger colorIndex = new AtomicInteger();
	AgentPathPainter agentPathPainter = new AgentPathPainter(agentPaths, agentColors);
	private UUID agentID;
	private JButton pauseButton;
	private JButton resumeButton;

	public JXMapViewerFrame(AgentContext agentContext, UUID agentID, Node location, long simulationTimeStep) {
		super("Simulation");
		this.agentContext = agentContext;
		this.agentID = agentID;
		this.simulationTimeStep = simulationTimeStep;
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
		// Create an empty border for horizontal spacing
		Border horizontalSpacing = BorderFactory.createEmptyBorder(0, 10, 0, 10);

		// Create a panel to hold the slider and text field
		JPanel controlPanel = new JPanel(new GridBagLayout());
		controlPanel.setBorder(horizontalSpacing);

		// Create a label for the text field
		JLabel label = new JLabel("Timestamp:");
		label.setHorizontalAlignment(SwingConstants.LEFT); // Align the label at the left
		label.setBorder(new EmptyBorder(10, 0, 5, 0)); // Add vertical spacing to the label

		// Create a small text field
		textField = new JTextField(5);
		textField.setMaximumSize(new Dimension(70, textField.getPreferredSize().height));

		
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				try {
					 ((JXMapViewerFrame)e.getSource()).simulationTimeStep = Integer.parseInt(text) >= 100 ? Integer.parseInt(text) : 100;
					textField.setText(Long.toString(simulationTimeStep));
					agentContext.getDefaultSpace().emit(agentID, new TimeStepChangedEvent(Long.valueOf(simulationTimeStep)));
					;
				} catch (NumberFormatException ex) {
					// Handle invalid input
				}
			}
		});

		// Create the pause button
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(e -> {
			agentContext.getDefaultSpace().emit(agentID, new TimeStepChangedEvent(Long.valueOf(0)));
			textField.setText(Integer.toString(0));

		});

		// Create the resume button
		resumeButton = new JButton("Resume");
		resumeButton.addActionListener(e -> {
			agentContext.getDefaultSpace().emit(agentID, new TimeStepChangedEvent(Long.valueOf(simulationTimeStep)));
			textField.setText(Long.toString(simulationTimeStep));
		});
		Dimension buttonSize = new Dimension(100, 30); // Set the buttons preferred size

        pauseButton.setPreferredSize(buttonSize);
        resumeButton.setPreferredSize(buttonSize);

		// Add components to the control panel using GridBagLayout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(10, 0, 5, 0);
		controlPanel.add(pauseButton, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 5, 0);
		controlPanel.add(resumeButton, gbc);

		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 5, 0);
		controlPanel.add(label, gbc);

		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 5, 0);
		controlPanel.add(textField, gbc);

		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 0, 0);

		// Create a panel to hold the map and control panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(horizontalSpacing);

		mainPanel.add(mapViewer, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.LINE_START); // Align the control panel at the left

		// Add the main panel to the frame
		add(mainPanel);

		// Set frame properties
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
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

	public void OnVehicleAgentInit(Node location, String agentName, List<Node> initialPath) {
		GeoPosition newPosition = new GeoPosition(location.getLatitude(), location.getLongitude());
		CarWaypoint newWaypoint = new CarWaypoint(newPosition, agentName);
		carWaypoints.add(newWaypoint);
		// Update the mapping
		CarAgentWaypoints.put(agentName, newWaypoint);
	    Set<CarWaypoint> copy = new HashSet<>(carWaypoints);
	    carWaypointPainter.setWaypoints(copy);

		Color agentColor = availableColors.get(colorIndex.getAndIncrement() % availableColors.size());
		agentColors.put(agentName, agentColor);
		onFullPathUpdate(agentName, initialPath);
	    mapViewer.repaint();


	}

	public void onVehicleAgentUpdate(Node newLocation, String agentName) {
		GeoPosition newPosition = new GeoPosition(newLocation.getLatitude(), newLocation.getLongitude());
		carWaypoints.remove(CarAgentWaypoints.get(agentName));
		mapViewer.repaint();
		// Create a new waypoint and add it to the set
		CarWaypoint newWaypoint = new CarWaypoint(newPosition, agentName);
		carWaypoints.add(newWaypoint);

		// Update the mapping
		CarAgentWaypoints.put(agentName, newWaypoint);
		carWaypointPainter.setWaypoints(carWaypoints);
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
