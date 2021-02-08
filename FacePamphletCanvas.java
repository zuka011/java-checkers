
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
		GLabel label = new GLabel(msg);
		label.setFont(MESSAGE_FONT);
		double x0 = getWidth() / 2 - label.getWidth() / 2;
		double y0 = getHeight() - BOTTOM_MESSAGE_MARGIN;
		if (getElementAt(getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN) != null) {
			remove(getElementAt(getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN));
		}
		add(label, x0, y0);
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
		drawName(profile.getName());
		drawPicture(profile.getImage());
		drawStatus(profile.getName(), profile.getStatus());
		drawFriendsList(profile.getFriends());
	}

	/*
	 * this method draws name to the proper place.
	 */

	private void drawName(String name) {
		profileName = new GLabel(name);
		profileName.setFont(PROFILE_NAME_FONT);
		profileName.setColor(Color.BLUE);
		double x0 = LEFT_MARGIN;
		double y0 = TOP_MARGIN + profileName.getAscent();
		add(profileName, x0, y0);
	}

	/*
	 * this method adds image below name, or draws rectangular instead.
	 */

	private void drawPicture(GImage image) {
		if (image == null) {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect, LEFT_MARGIN, TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN);
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			add(noImage, LEFT_MARGIN + rect.getWidth() / 2 - noImage.getWidth() / 2, TOP_MARGIN
					+ profileName.getAscent() + IMAGE_MARGIN + rect.getHeight() / 2 + noImage.getAscent() / 2);
		} else {
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, LEFT_MARGIN, TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN);
		}
	}

	/*
	 * this method draws status below image/rect.
	 */
	private void drawStatus(String name, String status) {
		GLabel label;
		if (status == null) {
			label = new GLabel("No current status");
		} else {
			label = new GLabel(name + " is " + status);
		}
		label.setFont(PROFILE_STATUS_FONT);
		add(label, LEFT_MARGIN, TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN);

	}

	/*
	 * this method writes down friend list to the proper place. distance between
	 * freinds in list is 'distBetweenFriends'.
	 */

	private void drawFriendsList(Iterator<String> it) {
		double distBetweenFriends = 20;
		GLabel label = new GLabel("Friends: ");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, getWidth() / 2, TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN);
		int i = 1;
		while (it.hasNext()) {
			GLabel friend = new GLabel(it.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN + i * distBetweenFriends);
			i++;
		}
	}

	private GLabel profileName;
}
