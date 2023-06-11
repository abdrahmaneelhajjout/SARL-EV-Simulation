package SARL.GUI;

import javax.swing.*;

public class ChargeStationAgentForm extends AgentForm {
    // Add fields specific to Charge Station Agent form
    private JTextField locationField;

    public ChargeStationAgentForm() {
        super();
        locationField = new JTextField(20);
        add(new JLabel("Location: "));
        add(locationField);
    }

    public String getLocationText() {
        return locationField.getText();
    }

    public Object[] getFormData() {
        String agentName = getAgentName();
        String location = getLocationText();
        return new Object[]{agentName, location};
    }

    public String getFormDataAsString() {
        String agentName = getAgentName();
        String location = getLocationText();
        return "Agent Name: " + agentName + ", Location: " + location;
    }
}
