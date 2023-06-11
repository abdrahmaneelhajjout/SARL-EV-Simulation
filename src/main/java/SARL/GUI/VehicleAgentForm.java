package SARL.GUI;

import javax.swing.*;

public class VehicleAgentForm extends AgentForm {
    private JTextField startNodeField;
    private JTextField endNodeField;
    private JTextField speedField;

    public VehicleAgentForm() {
        super();
        startNodeField = new JTextField(20);
        endNodeField = new JTextField(20);
        speedField = new JTextField(20);
        add(new JLabel("Start Node: "));
        add(startNodeField);
        add(new JLabel("End Node: "));
        add(endNodeField);
        add(new JLabel("Speed (km/h): "));
        add(speedField);
    }

    public String getStartNodeText() {
        return startNodeField.getText();
    }

    public String getEndNodeText() {
        return endNodeField.getText();
    }

    public String getSpeedText() {
        return speedField.getText();
    }

    public Object[] getFormData() {
        String agentName = getAgentName();
        String startNode = getStartNodeText();
        String endNode = getEndNodeText();
        String speed = getSpeedText();
        return new Object[]{agentName, startNode, endNode, speed};
    }

    public String getFormDataAsString() {
        String agentName = getAgentName();
        String startNode = getStartNodeText();
        String endNode = getEndNodeText();
        String speed = getSpeedText();
        return "Agent Name: " + agentName + ", Start Node: " + startNode + ", End Node: " + endNode + ", Speed: " + speed;
    }
}
