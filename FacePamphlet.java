
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {

		addButtons();
		DataBase = new FacePamphletDatabase();
		Profile = new FacePamphletProfile(name);
		canvas = new FacePamphletCanvas();
		add(canvas);

	}

	private void addButtons() {
		// This method add buttons

		JLabel Name = new JLabel("Name");
		add(Name, NORTH);
		namesField = new JTextField(TEXT_FIELD_SIZE);
		add(namesField, NORTH);
		add = new JButton("Add");
		add(add, NORTH);
		Delete = new JButton("Delete");
		add(Delete, NORTH);
		LookUp = new JButton("Lookup");
		add(LookUp, NORTH);
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		statusField.addActionListener(this);
		ChangeStatus = new JButton("Change Status");
		add(ChangeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		pictureField = new JTextField(TEXT_FIELD_SIZE);
		add(pictureField, WEST);
		pictureField.addActionListener(this);
		ChangePicture = new JButton("Change Picture");
		add(ChangePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		add(addFriendField, WEST);
		addFriendField.addActionListener(this);
		AddFriend = new JButton("Add Friend");
		add(AddFriend, WEST);

		addActionListeners();
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {

		Object sourse = e.getSource();
		name = namesField.getText();
		status = statusField.getText();
		filename = pictureField.getText();
		friendName = addFriendField.getText();
		if (sourse == add) {
			addNewProfile();
		}
		if (sourse == Delete) {
			DeteleProfile();
		}
		if (sourse == LookUp) {
			ProfileLookUp();
		}
		if (sourse == ChangeStatus || sourse == statusField) {
			addStatus();
		}
		if (sourse == ChangePicture || sourse == pictureField) {
			changePicture();
		}
		if (sourse == AddFriend || sourse == addFriendField) {
			addFriends();
		}
	}

	private void addFriends() {
		// this method adds friends to the selected profile's friendlist

		Profile = DataBase.getProfile(name);
		FacePamphletProfile friendProfile = DataBase.getProfile(friendName);
		if (Profile != null) {
			if (name.equals(friendName)) {
				canvas.showMessage("Your own profile can't be added as friend");
			} else if (DataBase.containsProfile(friendName) && Profile.addFriend(friendName)) {
				Profile.addFriend(friendName);
				friendProfile.addFriend(name);
				canvas.displayProfile(Profile);
				canvas.showMessage("Profile " + friendName + " added as friend");
			} else if (!DataBase.containsProfile(friendName)) {
				canvas.showMessage("Profile " + friendName + " does not exist");
			} else
				canvas.showMessage("This profile " + friendName + " is already added as friend");
		} else {
			canvas.showMessage("Please select a profile to add friends");
		}
	}

	private void changePicture() {
		// this method changes picture on profile

		Profile = DataBase.getProfile(name);
		GImage image = null;
		try {
			image = new GImage(filename);
		} catch (ErrorException ex) {
			// Code that is executed if the filename cannot be opened.
		}
		if (Profile != null) {
			Profile.setImage(image);
			canvas.displayProfile(Profile);
			canvas.showMessage("Picture updated");
		} else {
			canvas.showMessage("Please select a profile to change picture");
		}

	}

	private void addStatus() {
		// This method adds status on profile

		Profile = DataBase.getProfile(name);
		if (Profile != null) {
			Profile.setStatus(status);
			canvas.displayProfile(Profile);
			canvas.showMessage("Status updated to " + status);
		} else {
			canvas.showMessage("Please select a profile to change status");
		}
	}

	private void ProfileLookUp() {
		// This method looks up searched profile. If it exists program shows this
		// profile.

		if (DataBase.containsProfile(name)) {
			Profile = DataBase.getProfile(name);
			canvas.displayProfile(Profile);
			canvas.showMessage("Displaying " + name);
		} else {
			canvas.showMessage("This Profile does not exist");
		}
	}

	private void DeteleProfile() {
		// This method deletes profile

		canvas.removeAll();
		if (DataBase.containsProfile(name)) {
			DataBase.deleteProfile(name);
			canvas.showMessage("Profile " + name + " deleted");
		} else {
			canvas.showMessage("This Profile does not exist");
		}
	}

	private void addNewProfile() {
		// this method adds new profile

		if (!DataBase.containsProfile(name)) {
			Profile = new FacePamphletProfile(name);
			DataBase.addProfile(Profile);
			canvas.displayProfile(Profile);
			canvas.showMessage("New Profile Created");
		} else {
			Profile = DataBase.getProfile(name);
			canvas.displayProfile(Profile);
			canvas.showMessage("A Profile with name " + name + " already exists");
		}
	}

	private JTextField namesField;
	private JButton add;
	private JButton Delete;
	private JButton LookUp;
	private JButton ChangeStatus;
	private JButton ChangePicture;
	private JButton AddFriend;
	private JTextField statusField;
	private JTextField pictureField;
	private JTextField addFriendField;
	private String name;
	private String status;
	private String filename;
	private String friendName;

	private FacePamphletDatabase DataBase;
	private FacePamphletProfile Profile;
	private FacePamphletCanvas canvas;
	

}
