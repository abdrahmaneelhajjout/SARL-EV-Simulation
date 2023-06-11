package SARL.GUI;

import javax.swing.*;

public class TrafficSignalAgentForm extends AgentForm {
    // Add fields specific to Traffic Signal Agent form
    private JTextField colorField;

    public TrafficSignalAgentForm() {
        super();
        colorField = new JTextField(20);
        add(new JLabel("Color: "));
        add(colorField);
    }

    public String getColorText() {
        return colorField.getText();
    }

    public Object[] getFormData() {
        String agentName = getAgentName();
        String color = getColorText();
        return new Object[]{agentName, color};
    }

    public String getFormDataAsString() {
        String agentName = getAgentName();
        String color = getColorText();
        return "Agent Name: " + agentName + ", Color: " + color;
    }
}
