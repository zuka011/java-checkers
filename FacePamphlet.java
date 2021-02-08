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

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

/**
 * This method has the responsibility for initializing the 
 * interactors in the application, and taking care of any other 
 * initialization that needs to be performed.
 */
	public void init() {
		addTopBar();
		addSideBar();
		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
		addActionListeners();
    }
    
  
/**
* 	This method is responsible for detecting when the buttons are
* 	clicked or interactors are used.
*/
    public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		checkTopButtons(cmd);
		checkSideButtons(cmd);
	}
    
/*
 * 	Checks if the button clicked is any button from the top bar
 */
    private void checkTopButtons(String cmd) {
    	String name = nameField.getText();
    	if (cmd.equals("Add")) {
    		addProfile(name);
    	} else if (cmd.equals("Delete")) {
    		deleteProfile(name);
    	} else if (cmd.equals("Lookup")) {
    		lookupProfile(name);
    	}
    }
    
/*
 * 	Checks if the button clicked is any button from the side bar
 */
    private void checkSideButtons(String cmd) {
    	if (cmd.equals("Change Status")) {
    		changeStatus();
    	} else if (cmd.equals("Change Picture")) {
    		changePicture();
    	} else if (cmd.equals("Add Friend")) {
    		addFriend();
    	}
    }
    
/*
 * 	If it's a new profile name, method adds it to the database and displays it on the
 * 	screen with the proper notification. Otherwise, existing profile with this name is
 * 	displayed on the screen.
 */
    private void addProfile(String name) {
    	if (database.containsProfile(name)) {
    		currentProfile = database.getProfile(name);
    		canvas.displayProfile(currentProfile);
			canvas.showMessage("A profile with the name " + name + " already exists");
		} else {
			currentProfile = new FacePamphletProfile(name);
			database.addProfile(currentProfile);
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile created");
		}
    }
    
/*
 * 	Deletes profile if one exists
 */
    private void deleteProfile(String name) {
    	if (database.containsProfile(name)) {
			database.deleteProfile(name);
			canvas.removeAll();
			canvas.showMessage("Profile of " + name + " deleted");
		} else {
			canvas.removeAll();
			canvas.showMessage("A profile with the name " + name + " does not exist");
		}
    	currentProfile = null;
    }
    
/*
 * 	Looks up profile in the database and displays it if one exists
 */
    private void lookupProfile(String name) {
    	if (database.containsProfile(name)) {
    		currentProfile = database.getProfile(name);
			canvas.displayProfile(currentProfile);
			canvas.showMessage("Displaying " + name);
		} else {
			canvas.removeAll();
			currentProfile = null;
			canvas.showMessage("A profile with the name " + name + " does not exist");
		}
    }
    
/*
 * 	Changes status of the profile
 */
    private void changeStatus() {
    	if (currentProfile != null) {
    		String status = statusField.getText();
    		currentProfile.setStatus(status);
    		canvas.displayProfile(currentProfile);
    		canvas.showMessage("Status updated to " + status);
    	} else {
    		canvas.showMessage("Please select a profile to change status");
    	}
    }
    
/*
 * 	Changes picture of the profile
 */
    private void changePicture() {
    	if (currentProfile != null) {
    		GImage image = null;
    		try {
    			image = new GImage(pictureField.getText());
    			if (image != null) {
        			currentProfile.setImage(image);
        			canvas.displayProfile(currentProfile);
        			canvas.showMessage("Picture updated");
        		}
    		} catch (ErrorException ex) {
    			canvas.showMessage("Unable to open image file: " + pictureField.getText());
    		}
    	} else {
    		canvas.showMessage("Please select a profile to change picture");
    	}
    }
    
/*
 * 	Adds friend to the current profile
 */
    private void addFriend() {
    	if (currentProfile != null) {
    		String name = friendField.getText();
    		if (database.containsProfile(name) && !name.equals(currentProfile.getName())) {
    			if (currentProfile.addFriend(name)) {
    				database.getProfile(name).addFriend(currentProfile.getName());
    				canvas.displayProfile(currentProfile);
    				canvas.showMessage(name + " added as a friend");
    			} else {
    				canvas.showMessage(currentProfile.getName() + " already has " + name + " as a friend");
    			}
    		} else if (name.equals(currentProfile.getName())) {
    			canvas.showMessage("You can't add yourself as your friend");
    		} else {
    			canvas.showMessage(name + " does not exist");
    		}
    	} else {
    		canvas.showMessage("Please select a profile to add a friend");
    	}
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
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;
}