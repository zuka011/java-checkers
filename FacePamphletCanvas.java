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
	
	//private variables again and i know how much of a trainwreck this assignment is, i had to get help from friends and some google beacuse i had a hard time
	//i've been behind all the work but shall do my best till the exams.
	GLabel message;
	GLabel profileName;
	GLabel status;
	GLabel friendsList;
	double nameHeight = 0;
	double lastX = 0;
	double lastY = 0;
	
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("FACEBOOK WHO?");
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth() /2), (getHeight()-(BOTTOM_MESSAGE_MARGIN*2)));
		}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		double x = getWidth()/2 - message.getWidth()*3/4;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		if(getElementAt(lastX, lastY) != null) {
			remove(getElementAt(lastX, lastY));
		}
		lastX = x;
		lastY = y;
		message.setFont(MESSAGE_FONT);
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
	public void displayProfile(FacePamphletProfile profile) {	// I had to get google's help here and i realize my mistake and dont have time to fix it...
		removeAll();
		message.setLabel("");
		add(message);
		TheName(profile);
		ThePicture(profile);
		TheStatus(profile);
		TheFriends(profile);
	}

	private void TheName(FacePamphletProfile profile) {
		profileName = new GLabel(profile.getName(), LEFT_MARGIN, TOP_MARGIN);
		profileName.setFont(PROFILE_NAME_FONT);
		profileName.setColor(Color.blue);
		profileName.move(0, profileName.getAscent() / 2);
		add(profileName);
	}

	private void ThePicture(FacePamphletProfile profile) {
		if(profile.getImage() == null){
			GRect rect = new GRect (LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel noImage = new GLabel("No Image", LEFT_MARGIN + rect.getWidth()/2, TOP_MARGIN+IMAGE_MARGIN + rect.getHeight()/2);
			noImage.setFont(PROFILE_IMAGE_FONT);
			noImage.move(-noImage.getWidth()/2, noImage.getAscent()/2);
			add(rect);
			add(noImage);
		}else{
			GImage profilePicture = profile.getImage();
			profilePicture.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			profilePicture.setLocation(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
			add(profilePicture);
		}
	}

	private void TheStatus(FacePamphletProfile profile) {
		if(profile.getStatus().equals("")){
			status = new GLabel("No current status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		}else{
			status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		}
	}

	private void TheFriends(FacePamphletProfile profile) {  // need more tech savvy friends to be honest.
		GLabel friendsLabel = new GLabel("Friends: ", getWidth()/2, TOP_MARGIN+IMAGE_MARGIN);
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsLabel);
		String listOfFriends = "";
		Iterator<String> friends = profile.getFriends();
		for (int i = 1; friends.hasNext(); i++){
			friendsList = new GLabel(friends.next());
			friendsList.setLocation(getWidth()/2, TOP_MARGIN + IMAGE_MARGIN + friendsList.getAscent() * i);
			add(friendsList);
		}
	}








}
