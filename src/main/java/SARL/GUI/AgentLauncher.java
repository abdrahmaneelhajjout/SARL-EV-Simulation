package SARL.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentLauncher extends JFrame {
    private JComboBox<String> agentTypes;
    private JPanel formPanel;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JTable agentTable;
    private DefaultTableModel tableModel;

    private AgentForm vehicleAgentForm;
    private AgentForm environmentAgentForm;
    private AgentForm chargeStationAgentForm;
    private AgentForm trafficSignalAgentForm;

    public AgentLauncher() {
        setTitle("Agent Launcher");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Initialize the agentTypes dropdown
        agentTypes = new JComboBox<>(new String[]{"VehicleAgent", "EnvironmentAgent", "ChargeStationAgent", "TrafficSignalAgent"});

        // Initialize the formPanel
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());

        // Initialize the buttonPanel
        buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        // Initialize the tablePanel
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Agent Type");
        tableModel.addColumn("Attributes");
        agentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(agentTable);
        agentTable.setFillsViewportHeight(true);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Initialize the different agent forms
        vehicleAgentForm = new VehicleAgentForm();
        environmentAgentForm = new EnvironmentAgentForm();
        chargeStationAgentForm = new ChargeStationAgentForm();
        trafficSignalAgentForm = new TrafficSignalAgentForm();

        // ActionListener for the JComboBox
        agentTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formPanel.removeAll();
                switch (agentTypes.getSelectedItem().toString()) {
                    case "VehicleAgent":
                        formPanel.add(vehicleAgentForm, BorderLayout.CENTER);
                        break;
                    case "EnvironmentAgent":
                        formPanel.add(environmentAgentForm, BorderLayout.CENTER);
                        break;
                    case "ChargeStationAgent":
                        formPanel.add(chargeStationAgentForm, BorderLayout.CENTER);
                        break;
                    case "TrafficSignalAgent":
                        formPanel.add(trafficSignalAgentForm, BorderLayout.CENTER);
                        break;
                }
                formPanel.revalidate();
                formPanel.repaint();
            }
        });

        // Add components to the frame
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(agentTypes, BorderLayout.NORTH);
        leftPanel.add(formPanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.EAST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AgentLauncher();
    }
}
