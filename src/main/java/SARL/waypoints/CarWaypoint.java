package SARL.waypoints;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class CarWaypoint extends DefaultWaypoint{
	
	private String name;
	private JButton button;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}
	public CarWaypoint(String name, GeoPosition coord) {
		super(coord);
		this.name = name;
		this.button = button;
	}
	
	public CarWaypoint() {

	}
	
	private void initButton() {
		this.button = new ButtonWayPoint();
		this.button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked : " +  name);
				
			}
		});
	}
	

}
