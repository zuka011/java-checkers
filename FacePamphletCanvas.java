/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	private GLabel msg;
	private GLabel name;
	private GLabel status;
	private GImage image;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		msg = null;
		name = null;
		status = null;
		image = null;
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		if (!msg.equals("")) {
			if (this.msg != null)
				remove(this.msg);

			this.msg = new GLabel((msg));
			this.msg.setFont(MESSAGE_FONT);

			add(this.msg, (getWidth() - this.msg.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
		}
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();

		displayName(profile.getName()); // displays name of the profile

		displayImage(profile.getImage()); // displays image of the profile

		displayStatus(profile.getStatus()); // displays status of the profile

		displayFriends(profile.getFriends()); // displays friend list of the profile
	}

	/** Creates new GLabel for selected profile */
	private void displayName(String name) {
		this.name = new GLabel(name);
		this.name.setFont(PROFILE_NAME_FONT);
		this.name.setColor(Color.blue);
		add(this.name, LEFT_MARGIN, TOP_MARGIN + this.name.getAscent());
	}

	/**
	 * Creates new GImage for the selected profile. if passed on image is null, then
	 * it creates empty GRect, with No Image GLabel in the middle
	 */
	private void displayImage(GImage image) {
		if (image != null) {
			this.image = image;
			this.image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(this.image, LEFT_MARGIN, this.name.getY() + IMAGE_MARGIN);
		} else {
			noImage();
		}

	}

	/** Creates Empty GRect and GLabel with No Image text */
	private void noImage() {
		GRect rect = new GRect(LEFT_MARGIN, this.name.getY() + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
		add(rect);

		GLabel text = new GLabel("No Image");

		add(text, rect.getX() + (IMAGE_WIDTH - text.getWidth()) / 2,
				rect.getY() + (IMAGE_HEIGHT - text.getHeight()) / 2);
	}

	/** Creates new GLabel with the passed on status of the profile */
	private void displayStatus(String status) {
		this.status = new GLabel(status);
		this.status.setFont(PROFILE_STATUS_FONT);

		add(this.status, LEFT_MARGIN, this.name.getY() + this.name.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT
				+ STATUS_MARGIN + this.status.getAscent());

	}

	/** Creates several GLabels of the names in the selected profile friend list */
	private void displayFriends(Iterator<String> friends) {
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, LEFT_MARGIN + IMAGE_WIDTH + label.getWidth(), name.getY());

		if (friends != null) {
			while (friends.hasNext()) {
				label = new GLabel(friends.next(), label.getX(), label.getY() + label.getHeight());
				label.setFont(PROFILE_FRIEND_FONT);
				add(label);
			}
		}
	}

}
