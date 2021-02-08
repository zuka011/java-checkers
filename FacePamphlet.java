
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acmx.export.java.util.Iterator;
import acm.graphics.*;
import acm.util.*;
import acmx.export.javax.swing.JButton;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


public class FacePamphlet extends Program implements FacePamphletConstants {
	private FacePamphletDatabase data = new FacePamphletDatabase();
	private FacePamphletCanvas canvas = new FacePamphletCanvas();
	private JLabel name = new JLabel("Name");
	private JTextField fld = new JTextField(TEXT_FIELD_SIZE);
	private JButton add = new JButton("Add");
	private JButton delete = new JButton("Delete");
	private JButton loookup = new JButton("loookup");
	private JTextField statusfld = new JTextField(TEXT_FIELD_SIZE);
	private JButton status = new JButton("Change Status");
	private JTextField picturefld = new JTextField(TEXT_FIELD_SIZE);
	private JButton picture = new JButton("Change Picture");
	private JTextField friendfld = new JTextField(TEXT_FIELD_SIZE);
	private JButton friend = new JButton("Add Friend");
	private String fldName = "";

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		add(canvas);
		add(name, NORTH);
		add(fld, NORTH);
		add(add, NORTH);
		add.addActionListener(this);
		add(delete, NORTH);
		delete.addActionListener(this);
		add(loookup, NORTH);
		loookup.addActionListener(this);
		add(statusfld, WEST);
		statusfld.addActionListener(this);
		add(status, WEST);
		status.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(picturefld, WEST);
		picturefld.addActionListener(this);
		add(picture, WEST);
		picture.addActionListener(this);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendfld, WEST);
		friendfld.addActionListener(this);
		add(friend, WEST);
		friend.addActionListener(this);

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			if (!fld.getText().equals("")) {
				fldName = fld.getText();
				if (!data.containsProfile(fldName)) {
					data.addProfile(new FacePamphletProfile(fldName));
					canvas.displayProfile(data.getProfile(fldName));
					canvas.showMessage("New profile created");
				} else {
					canvas.displayProfile(data.getProfile(fldName));
					canvas.showMessage("A profile with the name " + fldName + " already exists");
				}
			}
		}
		if (e.getSource() == delete) {
			if (!fld.getText().equals("")) {
				fldName = fld.getText();
				if (data.containsProfile(fldName)) {
					data.deleteProfile(fldName);
					canvas.removeAll();
					canvas.showMessage("Profile of " + fldName + " deleted");
					fldName = "";
				} else {
					canvas.removeAll();
					canvas.showMessage("A profile with the name" + fldName + "do not exists");
					fldName = "";
				}
			}
		}
		if (e.getSource() == loookup) {
			if (!fld.getText().equals("")) {
				fldName = fld.getText();
				if (data.containsProfile(fldName)) {
					FacePamphletProfile profile = data.getProfile(fldName);
					canvas.displayProfile(profile);
					canvas.showMessage("Displaying " + fldName);
					
				}else {
					canvas.removeAll();
					canvas.showMessage("A profile with the name " + fldName + " do not exists");
					fldName ="";
				}
			}
		}
		if (e.getSource() == statusfld || e.getSource() == status) {
			if (!statusfld.getText().equals("")) {
				if(!fldName.equals("")) {
				if (data.containsProfile(fldName)) {
					data.getProfile(fldName).setStatus(statusfld.getText());
					canvas.displayProfile(data.getProfile(fldName));
					canvas.showMessage("Status updated to " + statusfld.getText());	
				}else {
					canvas.removeAll();
					canvas.showMessage("Please select a profile to change status");
				}
				}
			}
		}
		if (e.getSource() == picturefld || e.getSource() == picture) {
			if (!picturefld.getText().equals("")) {
				if(!fldName.equals("")) {
				if ( data.containsProfile(fldName)) {
					GImage image = null;
					try {
						image = new GImage(picturefld.getText());
					} catch (ErrorException ex) {
						canvas.removeAll();
						canvas.displayProfile(data.getProfile(fldName));
						canvas.showMessage("Unable to open image file:" + picturefld.getText());
					}
					if (image != null) {
						data.getProfile(fldName).setImage(image);
						canvas.removeAll();
						canvas.displayProfile(data.getProfile(fldName));
						canvas.showMessage("Updated profile picture");
					}
				}else {
					canvas.removeAll();
					canvas.showMessage("Please select a profile to change status");
				}
			}
			}
		}
		if (e.getSource() == friendfld || e.getSource() == friend) {
			if (!friendfld.getText().equals("") ) {
				if(!fldName.equals("")) {
				if ( data.containsProfile(fldName)) {
					if (data.containsProfile(friendfld.getText())) {
						if(!fldName.equals(friendfld.getText())) {
							if(data.getProfile(fldName).addFriend(friendfld.getText())) {
							data.getProfile(friendfld.getText()).addFriend(fldName);
							canvas.displayProfile(data.getProfile(fldName));
							canvas.showMessage( friendfld.getText() +" added as a friend");
							}else {
								canvas.removeAll();
								canvas.displayProfile(data.getProfile(fldName));
								canvas.showMessage(fldName+" already has " +friendfld.getText()+ " as a friend.");
							}
						}else {
							canvas.removeAll();
							canvas.displayProfile(data.getProfile(fldName));
							canvas.showMessage("you can't add yourself in friend list");
						}
					}else {
						canvas.removeAll();
						canvas.displayProfile(data.getProfile(fldName));
						canvas.showMessage( friendfld.getText()+ " does not exist.");
					}
				}else {
					canvas.removeAll();
					canvas.showMessage("Please select a profile to change status");
				}
				}
			}
		}

	}
	

}
