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

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
/** 
 * Constructor
 * This method takes care of any initialization needed for 
 * the display
 */
	public FacePamphletCanvas() {
		message = new GLabel("");
	}

	
/** 
 * This method displays a message string near the bottom of the 
 * canvas.  Every time this method is called, the previously 
 * displayed message (if any) is replaced by the new message text 
 * passed in.
 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		message.setFont(MESSAGE_FONT);
		double x = (getWidth() - message.getWidth()) / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(message, x, y);
	}
	
	
/** 
 * This method displays the given profile on the canvas.  The 
 * canvas is first cleared of all existing items (including 
 * messages displayed near the bottom of the screen) and then the 
 * given profile is displayed.  The profile display includes the 
 * name of the user from the profile, the corresponding image 
 * (or an indication that an image does not exist), the status of
 * the user, and a list of the user's friends in the social network.
 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		addName(profile);
		addPicture(profile);
		addStatus(profile);
		addFriendsList(profile);
	}
	
/*
 * 	Adds name to the profile's page
 */
	private void addName(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + name.getAscent();
		add(name, x, y);
	}
	
/*
 * 	Adds picture to the profile's page
 */
	private void addPicture(FacePamphletProfile profile) {
		if (profile.getImage() == null) {
			addImageFrame();
		} else {
			addImage(profile);
		}
	}
	
/*
 * 	Adds status to the profile's page
 */
	private void addStatus(FacePamphletProfile profile) {
		GLabel status = new GLabel("");
		if (profile.getStatus().equals("")) {
			status.setLabel("No current status");
		} else {
			status.setLabel(profile.getName() + " is " + profile.getStatus());
		}
		status.setFont(PROFILE_STATUS_FONT);
		double x = LEFT_MARGIN;
		double y = name.getY() + name.getDescent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
		add(status, x, y);
	}
	
/*
 * 	Adds friends list to the profile's page
 */
	private void addFriendsList(FacePamphletProfile profile) {
		GLabel l = new GLabel("Friends:");
		l.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x = getWidth() / 2;
		double y = name.getY() + name.getDescent() + IMAGE_MARGIN + l.getAscent();
		add(l, x, y);
		addFriends(profile, y);
	}
	
/*
 * 	Add the list of the friends under the Friends: label
 */
	private void addFriends(FacePamphletProfile profile, double y) {
		Iterator<String> iterator = profile.getFriends();
		double lastY = y;
		while (iterator.hasNext()) {
			GLabel l = new GLabel(iterator.next());
			l.setFont(PROFILE_FRIEND_FONT);
			add(l, getWidth() / 2, lastY + l.getHeight());
			lastY = l.getY();
		}
	}
	
/*
 * 	Adds image frame and suitable label to the profile's
 * 	page if there is no picture for the profile chosen
 */
	private void addImageFrame() {
		GRect r = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		double x = LEFT_MARGIN;
		double y = name.getY() + name.getDescent() + IMAGE_MARGIN;
		GLabel l = new GLabel("No Image");
		l.setFont(PROFILE_IMAGE_FONT);
		double lx = x + IMAGE_WIDTH / 2 - l.getWidth() / 2;
		double ly = y + IMAGE_HEIGHT / 2 + l.getAscent() / 2;
		add(r, x, y);
		add(l, lx, ly);
	}
	
/*
 * 	Adds image to the profile
 */
	private void addImage(FacePamphletProfile profile) {
		GImage image = profile.getImage();
		image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		double x = LEFT_MARGIN;
		double y = name.getY() + name.getDescent() + IMAGE_MARGIN;
		add(image, x, y);
	}
	
/*	Private instance variables */
	private GLabel name;
	private GLabel message;
}