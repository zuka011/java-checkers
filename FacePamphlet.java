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
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		/** Initializes face pamphlet classes */
		add(canvas = new FacePamphletCanvas());

		data = new FacePamphletDatabase();

		/** JObjects on the NORTH */
		add(new JLabel("Name "), NORTH);

		add(topTextField = new JTextField(TEXT_FIELD_SIZE), NORTH);
		add(addButton = new JButton("Add"), NORTH);
		add(deleteButton = new JButton("Delete"), NORTH);
		add(lookUpButton = new JButton("Look Up"), NORTH);

		/** JObjects on the WEST */
		// Field for updating status of the user
		add(statusTextField = new JTextField(TEXT_FIELD_SIZE), WEST);
		add(status = new JButton("Change Status"), WEST);
		statusTextField.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		// Field for updating profile picture of the user
		add(imageTextField = new JTextField(TEXT_FIELD_SIZE), WEST);
		add(image = new JButton("Change Image"), WEST);
		imageTextField.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		// Field for updating friend list of the user
		add(addFriendTextField = new JTextField(TEXT_FIELD_SIZE), WEST);
		add(addFriend = new JButton("Add Friend"), WEST);
		addFriendTextField.addActionListener(this);
		addActionListeners(this);

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj.equals(addFriend) || obj.equals(addFriendTextField)) {
			addFriend(addFriendTextField.getText());
			addFriendTextField.setText("");
		} else if (obj.equals(status) || obj.equals(statusTextField)) {
			setStatus(statusTextField.getText());
			statusTextField.setText("");
		} else if (obj.equals(image) || obj.equals(imageTextField)) {
			setImage(imageTextField.getText());
			imageTextField.setText("");
		} else if (obj.equals(addButton)) {
			addProfile(topTextField.getText());
			topTextField.setText("");
		} else if (obj.equals(deleteButton)) {
			deleteProfile(topTextField.getText());
			topTextField.setText("");
		} else if (obj.equals(lookUpButton)) {
			lookUp(topTextField.getText());
			topTextField.setText("");
		}
	}

	/** Updates friend list of the current user and inputed profile */
	private void addFriend(String name) {
		if (user != null) {
			if (name.contentEquals(user.getName())) {
				canvas.showMessage("You can't add yourself to your friend list");
			} else if (!name.equals("")) {
				if (user.isFriendsWith(name)) {
					canvas.showMessage("<" + user.getName() + "> already has <" + name + "> as a friend.");
				} else if (data.getProfile(name) != null) {
					user.addFriend(name);
					data.getProfile(name).addFriend(user.getName());

					canvas.displayProfile(user);
					canvas.showMessage("<" + name + "> added as a friend");
				} else {
					canvas.showMessage("<" + name + "> does not exist.");
				}
			}
		} else {
			canvas.showMessage("Please select profile to add a friend");
		}
	}

	/** Updates status for current user */
	private void setStatus(String text) {
		if (user != null) {
			if (!text.equals("")) {
				user.setStatus(text);
				canvas.displayProfile(user);
				canvas.showMessage("Status updated to " + text);
			}
		} else {
			canvas.showMessage("Please select a profile to change status");
		}
	}

	/** Updates profile picture for current user */
	private void setImage(String text) {
		if (user != null) {
			if (!text.equals("")) {
				GImage image = null;

				try {

					image = new GImage("images/" + text);

					user.setImage(image);

					canvas.displayProfile(user);

					canvas.showMessage("Picture updated");

				} catch (ErrorException ex) {
					canvas.displayProfile(user);

					canvas.showMessage("Unable to open image file: <" + text + ">");
				}
			}
		} else {
			canvas.showMessage("Please select a profile to change image");
		}
	}

	/**
	 * Adds new profile to the data if this profile already exists, it just selects
	 * as the current user
	 */
	private void addProfile(String name) {
		if (!name.equals("")) {
			if (!data.containsProfile(name)) {
				user = new FacePamphletProfile(name);
				data.addProfile(user);

				canvas.displayProfile(user);
				canvas.showMessage("New profile created");

			} else {
				user = data.getProfile(name);
				canvas.displayProfile(user);
				canvas.showMessage("A profile with the name <" + name + "> already exists");
			}
		}
	}

	/** Deletes selected profile */
	private void deleteProfile(String name) {
		if (!name.equals("")) {
			if (data.containsProfile(name)) {
				data.deleteProfile(name);
				canvas.removeAll();
				canvas.showMessage("Profile of <" + name + "> deleted ");
			} else {
				canvas.showMessage("A profile with the name <" + name + "> does not exists");
			}
		}
	}

	/** Searches for the inputed profile in the data */
	private void lookUp(String name) {
		if (!name.equals("")) {
			if (data.getProfile(name) != null) {
				user = data.getProfile(name);

				canvas.displayProfile(user);
				canvas.showMessage("Displaying <" + name + ">");
			} else {
				canvas.showMessage("A profile with the name <" + name + "> does not exists");
			}
		}
	}

	/** Canvas for the Face Pamphlet */
	private FacePamphletCanvas canvas;

	/** Data containing all the profiles */
	private FacePamphletDatabase data;

	/** JObjects on the NORTH */

	// Buttons
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookUpButton;

	// TextFields
	private JTextField topTextField;

	/** JObjects on the WEST */

	// Buttons
	private JButton addFriend;
	private JButton image;
	private JButton status;

	// TextFields
	private JTextField statusTextField;
	private JTextField imageTextField;
	private JTextField addFriendTextField;

	/** Selected Profile */
	private FacePamphletProfile user;
}
