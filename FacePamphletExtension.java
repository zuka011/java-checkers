
import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

public class FacePamphletExtension extends Program implements FacePamphletConstantsExtension {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		setLayout(new BorderLayout());
		setBounds(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		canvas = new FacePamphletCanvasExtension();
		add(canvas);
		try {
			canvas.introduction();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataBase = new FacePamphletDatabaseExtension();
		upperSideOfWindow();
		leftSideOfWindow();
		addScrollBar();

		addActionListeners();
	}

	private void addScrollBar() {
		scrollUp = new JButton("UP");
		scrollDown = new JButton("DOWN");
		scrollUp.addActionListener(this);
		scrollDown.addActionListener(this);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(SCROLL_SIZE, 1));
		panel.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));

		panel.add(scrollUp);
		for (int i = 0; i < SCROLL_SIZE - 2; i++) {
			panel.add(new JPanel());
		}
		panel.add(scrollDown);
		add(panel, EAST);
	}

//This method adds interactors to the top of the window
	private void upperSideOfWindow() {
		nameLabel = new JLabel("Name");
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");
		add(nameLabel, NORTH);
		add(nameField, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
	}

	private void leftSideOfWindow() {
		changeStatus();
		changePicture();
		addFriend();
		initSelectGender();
		initHousing();
		initRelationships();
		initMore();
		initUpdateButton();

	}

// This method creates a button and text field to change the status
	private void changeStatus() {
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.setActionCommand("Change Status");
		changeStatusField.addActionListener(this);
		add(changeStatusField, WEST);
		changeStatusButton = new JButton("Change Status");
		add(changeStatusButton, WEST);
	}

// this method creates a button and text field to change picture
	private void changePicture() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.setActionCommand("Change Picture");
		changePictureField.addActionListener(this);
		add(changePictureField, WEST);
		changePictureButton = new JButton("Change Picture");
		add(changePictureButton, WEST);
	}

// this method creates a button and text field to add friend
	private void addFriend() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.setActionCommand("Add Friend");
		addFriendField.addActionListener(this);
		add(addFriendField, WEST);
		addFriendButton = new JButton("Add Friend");
		add(addFriendButton, WEST);

	}

	private void initSelectGender() {
		selectGender = new JComboBox();
		selectGender.addItem("Female");
		selectGender.addItem("Male");
		selectGender.setEditable(true);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(new JLabel("             Select Gender"), WEST);
		add(selectGender, WEST);
	}

	private void initMore() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		more = new JComboBox();
		more.addItem("Show More Info");
		more.addItem("Show Less Info");
		more.setEditable(false);
		more.setSelectedItem("Show Less Info");
		more.setActionCommand("Enter");
		add(new JLabel("                     More"), WEST);
		add(more, WEST);
	}

	private void initRelationships() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		relField = new JTextField(TEXT_FIELD_SIZE);
		relField.setActionCommand("Relationships");
		relField.addActionListener(this);
		add(relField, WEST);
		rel = new JButton("Relationships");
		add(rel, WEST);
	}

	private void initHousing() {
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		housingField = new JTextField(TEXT_FIELD_SIZE);
		housingField.setActionCommand("Housing");
		housingField.addActionListener(this);
		add(housingField, WEST);
		housing = new JButton("Housing");
		add(housing, WEST);
	}

	private void initUpdateButton() {
		update = new JButton("Update");
		add(update, WEST);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		/*
		 * After clicking the Add button, we will see the name entered in the Name text
		 * field and look for the profile with this name in the database.
		 */
		if (!(nameField.getText().equals("")) && e.getSource() == add) {
			String name = nameField.getText();
			if (dataBase.containsProfile(name)) {
				currentProfile = dataBase.getProfile(name);
				canvas.displayProfile(currentProfile);
				showInfo();

				canvas.showMessage("A profile with name " + name + " already exists");

			} else {
				// If a profile with such a name does not exist,
				// we will create a new one and display the message.
				FacePamphletProfileExtension newUser = new FacePamphletProfileExtension(name);
				currentProfile = newUser;
				dataBase.addProfile(newUser);
				canvas.displayProfile(currentProfile);
				showInfo();

				canvas.showMessage("New profile created");

			}
		}
		/*
		 * If the profile exists, we show the message and delete it, if it does not
		 * exist we show the corresponding message.
		 */
		if (!(nameField.getText().equals("")) && e.getSource() == delete) {
			currentProfile = null;
			String name = nameField.getText();
			if (dataBase.containsProfile(name)) {
				dataBase.deleteProfile(name);
				canvas.removeAll();
				canvas.showMessage("Profile of " + name + " deleted");

			} else {
				canvas.removeAll();
				canvas.showMessage("Profile of " + name + " does not exist");

			}
		}
		/*
		 * If a profile exists we show the profile and the corresponding message, if it
		 * does not exist, only the relevant message will appear.
		 */
		if (!(nameField.getText().equals("")) && e.getSource() == lookup) {
			String name = nameField.getText();
			if (dataBase.containsProfile(name)) {
				currentProfile = dataBase.getProfile(name);
				canvas.displayProfile(currentProfile);
				showInfo();

				canvas.showMessage("Displaying " + name);

			} else {
				currentProfile = null;
				canvas.removeAll();

				canvas.showMessage("Profile of " + name + " does not exist");

			}
		}
		/*
		 * When changing the status we just need to check if the user has chosen the
		 * current profile
		 */
		if (!(changeStatusField.getText().equals("")) && e.getActionCommand().equals("Change Status")) {
			if (currentProfile != null) {
				String status = changeStatusField.getText();
				currentProfile.setStatus(status);
				canvas.displayProfile(currentProfile);
				showInfo();

				canvas.showMessage("Status updated to " + status);

			} else {
				canvas.showMessage("Please select a profile to change status");

			}

		} else if (e.getActionCommand().equals("Change Status") && currentProfile == null) {
			canvas.showMessage("Please select a profile to change status");
		}

		if (!(changePictureField.getText().equals("")) && e.getActionCommand().equals("Change Picture")) {
			if (currentProfile != null) {

				String imageName = changePictureField.getText();
				GImage image = null;
				try {
					image = new GImage(imageName);
					currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					showInfo();

					canvas.showMessage("Picture updated");

				} catch (ErrorException ex) {
					// If the user enters the wrong file name
					canvas.showMessage("Unable to open image file: " + imageName);
				}

			} else {
				canvas.showMessage("Please select a profile to change picture");

			}

		} else if (e.getActionCommand().equals("Change Picture") && currentProfile == null) {
			canvas.showMessage("Please select a profile to change picture");
		}

		if (!(addFriendField.getText().equals("")) && e.getActionCommand().equals("Add Friend")) {
			if (currentProfile != null) { // The user must have the current profile selected
				String name = addFriendField.getText();

				// The name entered must be in the database and must not match the current
				// profile name
				if (dataBase.containsProfile(name) && !name.equals(currentProfile.getName())) {
					if (currentProfile.getFriendsAsList().size() == 0
							|| !currentProfile.getFriendsAsList().contains(name)) {
						currentProfile.addFriend(name);
						dataBase.getProfile(name).addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						showInfo();

						canvas.showMessage(name + " added as a friend");

					} else if (currentProfile.getFriendsAsList().contains(name)) {
						// If the entered name was found in the current profile friends list
						canvas.showMessage(currentProfile.getName() + " already has " + name + " as a friend");

					}
				} else {
					canvas.showMessage(name + " does not exist");

				}

			} else {
				canvas.showMessage("Please select a profile to add friend");

			}
			addFriendField.setText("");

		} else if (e.getActionCommand().equals("Add Friend") && currentProfile == null) {
			canvas.showMessage("Please select a profile to add friend");
		}

		if (e.getActionCommand().equals("Housing")) {
			if (currentProfile != null && !(housingField.getText() == null) && !housingField.getText().equals("")) {
				String Housing = housingField.getText();
				currentProfile.setHousing(Housing);
				canvas.displayProfile(currentProfile);
				showInfo();

				canvas.showMessage("Housing updated to " + Housing);

			} else if (currentProfile == null) {
				canvas.showMessage("Please select a profile to update housing");

			}
			housingField.setText("");
		}
		if (e.getActionCommand().equals("Relationships")) {
			if (currentProfile != null && !(relField.getText() == null) && !relField.getText().equals("")) {
				String rel = relField.getText();
				currentProfile.setRelationship(rel);
				canvas.displayProfile(currentProfile);
				showInfo();
				canvas.showMessage("Relationship updated to " + rel);

			} else if (currentProfile == null) {
				canvas.showMessage("Please select a profile to update relationship");

			}
			relField.setText("");
		}

		if (e.getSource().equals(scrollUp) && currentProfile != null) {

			canvas.scrollUp();
		} else if (e.getSource().equals(scrollDown) && currentProfile != null) {

			canvas.scrollDown();
		}

		if (e.getSource().equals(update) && currentProfile != null) {
			canvas.displayProfile(currentProfile);
			showInfo();
			canvas.showMessage(" Profile is updated ");
		} else if (e.getSource().equals(update) && currentProfile == null) {
			canvas.showMessage("Please select a profile ");
		}

	}

	private void showInfo() {
		if (currentProfile != null) {
			info = (String) more.getSelectedItem();
			if (info.equals("Show More Info")) {
				String gender = (String) selectGender.getSelectedItem();
				currentProfile.setGender(gender);
				canvas.showMore(currentProfile);
			}
		} else {
			canvas.showMessage("Please select a profile ");
		}
	}

	private JButton update;
	private String info;
	private JTextField relField;
	private JTextField housingField;
	private JButton housing;
	private JButton rel;
	private JComboBox more;
	private JComboBox selectGender;
	private JButton scrollUp;
	private JButton scrollDown;
	private FacePamphletCanvasExtension canvas;
	private FacePamphletProfileExtension currentProfile;
	private FacePamphletDatabaseExtension dataBase;
	private JButton add;
	private JButton delete;
	private JButton lookup;
	private JButton changeStatusButton;
	private JButton changePictureButton;
	private JButton addFriendButton;
	private JTextField nameField;
	private JTextField changeStatusField;
	private JTextField changePictureField;
	private JTextField addFriendField;
	private JLabel nameLabel;

}
