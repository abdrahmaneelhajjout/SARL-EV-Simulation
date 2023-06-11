package SARL.GUI;

import javax.swing.*;

public class EnvironmentAgentForm extends AgentForm {
    // Add fields specific to Environment Agent form
    private JTextField environmentField;

    public EnvironmentAgentForm() {
        super();
        environmentField = new JTextField(20);
        add(new JLabel("Environment Field: "));
        add(environmentField);
    }

    public String getEnvironmentText() {
        return environmentField.getText();
    }

    public Object[] getFormData() {
        String agentName = getAgentName();
        String environment = getEnvironmentText();
        return new Object[]{agentName, environment};
    }

    public String getFormDataAsString() {
        String agentName = getAgentName();
        String environment = getEnvironmentText();
        return "Agent Name: " + agentName + ", Environment: " + environment;
    }
}
