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
		
		Message = new GLabel ("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
			
		Message.setLabel(msg);
		Message.setColor(Color.BLACK);
		Message.setFont(MESSAGE_FONT);
		double x = (getWidth() - Message.getWidth())/2 ;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN - Message.getHeight();
		add(Message, x, y);
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
		profileName(profile.getName());
		profilePicture(profile.getImage());
		StatusOnProfile (profile.getName(), profile.getStatus());
		friendsOnProfile (profile.getFriends());
	}


	private void friendsOnProfile(Iterator<String> friends) {
		// This method show friend's list on profile
		
		Friends = new GLabel ("Friends");
		Friends.setColor(Color.BLACK);
		Friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		double NameY = ProfileName.getY();
		double x = getWidth()/2;
		double y = NameY + IMAGE_MARGIN;
		add (Friends, x, y);
		addFriendsList(x, y, friends );
	}


	private void addFriendsList(double x, double y, Iterator<String> friends) {
		// This method writes names of friends
		
		for (int i= 1; friends.hasNext(); i++) {
			GLabel List = new GLabel (friends.next());
			List.setColor(Color.BLACK);
			List.setFont(PROFILE_FRIEND_FONT );
			double y1 = y + i * Friends.getHeight();
			add(List, x, y1);
		}
	}


	private void StatusOnProfile(String name, String status) {
		// This method writes status on profile
		
		if (status == null) {
			status = "NO CURRENT STATUS";
		}else {
			status = name + " is " + status;
		}
		GLabel Status = new GLabel(status);
		Status.setColor(Color.BLACK);
		Status.setFont(PROFILE_STATUS_FONT);
		double x = LEFT_MARGIN;
		double y = ProfileName.getY() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
		add(Status, x, y);
	}


	private void profilePicture(GImage image) {
		// This method set picture place on profile
		
		double ProfileNameSize = ProfileName.getY();
		double x = LEFT_MARGIN ;
		double y = IMAGE_MARGIN + ProfileNameSize;
		if (image == null) {
			drawFrame(x,y);
		}else {
			addPicture(x, y , image);
		}
	}


	private void addPicture(double x, double y, GImage image) {
		// This method adds picture on profile
		
		double ImageWidth = image.getWidth();
		double ImageHeight = image.getHeight();
		double sx = IMAGE_WIDTH / ImageWidth;
		double sy = IMAGE_HEIGHT / ImageHeight;
		image.scale(sx, sy);
		add (image, x,y);
	}


	private void drawFrame(double x, double y) {
		// This method draws picture frame when picture isn't uploaded yet
		GRect frame = new GRect (IMAGE_WIDTH , IMAGE_HEIGHT );
		frame.setColor(Color.BLACK);
		add (frame, x,y);
		GLabel text = new GLabel ("NO IMAGE");
		text.setFont(PROFILE_IMAGE_FONT);
		text.setColor(Color.BLACK);
		double textWidth = text.getWidth();
		double x1 = x + ((IMAGE_WIDTH - textWidth)/ 2);
		double y1 = y + IMAGE_HEIGHT /2;
		add (text, x1, y1);
	}


	private void profileName(String name) {
		// This method writes name on profile
		
		ProfileName = new GLabel(name);
		ProfileName.setColor(Color.BLUE);
		ProfileName.setFont(PROFILE_NAME_FONT);
		double x = LEFT_MARGIN ;
		double y = TOP_MARGIN ;
		add(ProfileName, x, y);
	}

	private GLabel ProfileName;
	private GLabel Friends;
	private GLabel Message;
}
