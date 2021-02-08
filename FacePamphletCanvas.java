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
	private GLabel label = new GLabel("");

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {

	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		label = new GLabel(msg);
		label.setFont(MESSAGE_FONT);
		add(label, getWidth() / 2 - label.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
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
		GLabel name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN  + name.getAscent());
		GRect rect = new GRect(LEFT_MARGIN, TOP_MARGIN + name.getAscent()  + IMAGE_MARGIN, IMAGE_WIDTH,
				IMAGE_HEIGHT);
		add(rect);
		GLabel nolabel = new GLabel("No Image");
		nolabel.setFont(PROFILE_IMAGE_FONT);
		add(nolabel, LEFT_MARGIN + IMAGE_WIDTH / 2 - nolabel.getWidth() / 2,
				TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT / 2 + nolabel.getAscent()/2 );
		GImage image = profile.getImage();
		if (image != null) {
			image.scale(IMAGE_WIDTH / image.getWidth(), IMAGE_HEIGHT / image.getHeight());
			add(image, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		}
		GLabel status;
		if (!profile.getStatus().equals("")) {
			status = new GLabel(profile.getName() + " is " + profile.getStatus());

		} else {
			status = new GLabel("No current status");

		}
		status.setFont(PROFILE_STATUS_FONT);
		add(status, LEFT_MARGIN, TOP_MARGIN + name.getAscent()  + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN
				+ status.getAscent() / 2);
		GLabel friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, getWidth() / 2, TOP_MARGIN + name.getAscent()  + IMAGE_MARGIN);
		Iterator<String> friendsArry = profile.getFriends();
		double margin = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN;
		while (friendsArry.hasNext()) {
			GLabel friend = new GLabel(friendsArry.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, margin + friend.getAscent());
			margin += friend.getAscent();
		}

	}

}
