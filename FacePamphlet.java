/* 
 * File: FacePamphlet.java
 * -----------------------
 * This program implements a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.javax.swing.JLabel;

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends ConsoleProgram 
					implements FacePamphletConstants {

/**
 * This method has the responsibility for initializing the 
 * interactors in the application, and taking care of any other 
 * initialization that needs to be performed.
 */
	public void init() {
		addTopBar();
		addSideBar();
		addActionListeners();
		database = new FacePamphletDatabase();
    }
    
  
/**
* This class is responsible for detecting when the buttons are
* clicked or interactors are used, so you will have to add code
* to respond to these actions.
*/
    public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Add")) println("Add: " + nameField.getText());
		if (cmd.equals("Delete")) println("Delete: " + nameField.getText());
		if (cmd.equals("Lookup")) println("Lookup: " + nameField.getText());
		if (cmd.equals("Change Status")) println("Change Status: " + statusField.getText());
		if (cmd.equals("Change Picture")) println("Change Picture: " + pictureField.getText());
		if (cmd.equals("Add Friend")) println("Add Friend: " + friendField.getText());
	}
    
/*
 * 	Adds top bar to the window.
 */
    private void addTopBar() {
    	nameField = new JTextField(TEXT_FIELD_SIZE);
    	add(new JLabel("Name"), NORTH);
    	add(nameField, NORTH);
    	add(new JButton("Add"), NORTH);
    	add(new JButton("Delete"), NORTH);
    	add(new JButton("Lookup"), NORTH);
    }
    
/*
 * 	Adds side bar to the window.
 */
    private void addSideBar() {
    	statusField = new JTextField(TEXT_FIELD_SIZE);
    	statusField.setActionCommand("Change Status");
    	statusField.addActionListener(this);
    	pictureField = new JTextField(TEXT_FIELD_SIZE);
    	pictureField.setActionCommand("Change Picture");
    	pictureField.addActionListener(this);
    	friendField = new JTextField(TEXT_FIELD_SIZE);
    	friendField.setActionCommand("Add Friend");
    	friendField.addActionListener(this);
    	add(statusField, WEST);
    	add(new JButton("Change Status"), WEST);
    	add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    	add(pictureField, WEST);
    	add(new JButton("Change Picture"), WEST);
    	add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    	add(friendField, WEST);
    	add(new JButton("Add Friend"), WEST);
    	add(new JLabel(EMPTY_LABEL_TEXT), WEST);
    }

 /*	Private instance variables */
    private JTextField statusField;
    private JTextField pictureField;
    private JTextField friendField;
    private JTextField nameField;
    private FacePamphletDatabase database;
}