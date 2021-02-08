
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
		JLabel name = new JLabel("Name: ");
		add(name, NORTH);

		Name = new JTextField(TEXT_FIELD_SIZE);
		add(Name, NORTH);

		add = new JButton("Add");
		add(add, NORTH);

		delete = new JButton("Delete");
		add(delete, NORTH);

		lookUp = new JButton("Lookup");
		add(lookUp, NORTH);

		Status = new JTextField(TEXT_FIELD_SIZE);
		Status.addActionListener(this);
		add(Status, WEST);

		ChangeStatus = new JButton("Change Status");
		add(ChangeStatus, WEST);

		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		Picture = new JTextField(TEXT_FIELD_SIZE);
		Picture.addActionListener(this);
		add(Picture, WEST);

		ChangePicture = new JButton("Change Picture");
		add(ChangePicture, WEST);

		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		Friend = new JTextField(TEXT_FIELD_SIZE);
		Friend.addActionListener(this);
		add(Friend, WEST);

		AddFriend = new JButton("Add Friend");
		add(AddFriend, WEST);

		canvas = new FacePamphletCanvas();
		add(canvas);

		addActionListeners();

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String name = Name.getText();
		String status = Status.getText();
		String picture = Picture.getText();
		String friend = Friend.getText();
		if (!name.equals("")) {

			// this part is responsible to add profile
			if (e.getSource() == add) {
				if (!dataBase.containsProfile(name)) {
					currProfile = new FacePamphletProfile(name);
					dataBase.addProfile(currProfile);
					canvas.displayProfile(currProfile);
					canvas.showMessage("New Profile Created");
				} else {
					currProfile = dataBase.getProfile(name);
					canvas.displayProfile(currProfile);
					canvas.showMessage("A profile with the name " + name + " already exists");
				}

				// this part is responsible to delete profile
			} else if (e.getSource() == delete) {
				if (dataBase.containsProfile(name)) {
					dataBase.deleteProfile(name);
					currProfile = null;
					canvas.removeAll();
					canvas.showMessage("Profile of " + name + " deleted");
				} else {
					currProfile = null;
					canvas.removeAll();
					canvas.showMessage("A profile with the name " + name + " doesn't exist");
				}

				// this part is responsible to look up to profile
			} else if (e.getSource() == lookUp) {
				if (dataBase.containsProfile(name)) {
					currProfile = dataBase.getProfile(name);
					canvas.displayProfile(currProfile);
					canvas.showMessage("Displaying " + name);
				} else {
					currProfile = null;
					canvas.removeAll();
					canvas.showMessage("A profile with the name " + name + " doesn't exist");
				}
			}
		}

		// this part is responsible to change status to the current profile
		if (!status.equals("")) {
			if (e.getSource() == ChangeStatus || e.getSource() == Status) {
				if (currProfile != null) {
					currProfile.setStatus(status);
					canvas.displayProfile(currProfile);
					canvas.showMessage("Status updated to " + name);
					Status.setText("");
				} else {
					canvas.showMessage("Please select profile to change status");
				}
			}
		}

		// this part is responsible to change picture to the current profile
		if (!picture.equals("")) {
			if (e.getSource() == ChangePicture || e.getSource() == Picture) {
				if (currProfile != null) {
					GImage image = null;
					try {
						image = new GImage(picture);
						Picture.setText("");
						currProfile.setImage(image);
						canvas.displayProfile(currProfile);
						canvas.showMessage("Picture updated");
					} catch (ErrorException ex) {
						canvas.showMessage("Unable to open image file : " + picture);
					}
				} else {
					canvas.showMessage("Please select profile to change picture");
				}

			}
		}

		// this part is responsible to add friend to the current profile
		if (!friend.equals("")) {
			if (e.getSource() == AddFriend || e.getSource() == Friend) {
				if (currProfile != null) {
					if (dataBase.containsProfile(friend)) {
						if (currProfile.addFriend(friend)) {
							currProfile.addFriend(friend);
							dataBase.getProfile(friend).addFriend(currProfile.getName());
							canvas.displayProfile(currProfile);
							canvas.showMessage(friend + " added as a friend");
						} else {
							canvas.showMessage(name + " already has " + friend + " as a friend");
						}

					} else {
						canvas.showMessage(friend + " doesn't exist");
					}

				} else {
					canvas.showMessage("Please select profile to add friend");
				}
				Friend.setText("");
			}
		}

	}

	private JButton add;
	private JTextField Name;
	private JButton delete;
	private JButton lookUp;
	private JTextField Status;
	private JButton ChangeStatus;
	private JTextField Picture;
	private JButton ChangePicture;
	private JTextField Friend;
	private JButton AddFriend;
	private FacePamphletDatabase dataBase = new FacePamphletDatabase();
	private FacePamphletProfile currProfile;
	private FacePamphletCanvas canvas;

}
