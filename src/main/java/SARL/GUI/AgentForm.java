package SARL.GUI;

import javax.swing.*;

public abstract class AgentForm extends JPanel {
    protected JTextField agentNameField;

    public AgentForm() {
        agentNameField = new JTextField(20);
        add(new JLabel("Agent Name: "));
        add(agentNameField);
    }

    public String getAgentName() {
        return agentNameField.getText();
    }

    public abstract Object[] getFormData();
    public abstract String getFormDataAsString();

}
