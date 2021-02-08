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
	
	private GLabel name;
	private GLabel message=null;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in

		if(message!=null){
			remove(message);
		}
		
		GLabel msgWidth= new GLabel(msg);
		message= new GLabel (msg, getWidth()/2- msgWidth.getWidth()/2, getHeight()-BOTTOM_MESSAGE_MARGIN);
		message.setFont(MESSAGE_FONT);
		add(message);
	
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
		// You fill this in
	
		
		removeAll();
		
		setUsername(profile);
		setPicture(profile);
		setFriendslist(profile);
		setstatus(profile);
	
	}
	
	//shows user's profpic
	private void setPicture(FacePamphletProfile profile){
		GImage image= profile.getImage();
		if(image!=null){
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, LEFT_MARGIN, TOP_MARGIN+name.getAscent()+IMAGE_MARGIN);
		}else{
			GRect emptySquare/*felt*/= new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(emptySquare,LEFT_MARGIN, TOP_MARGIN+name.getAscent()+IMAGE_MARGIN );
			GLabel noImage= new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			add(noImage, (IMAGE_WIDTH-noImage.getWidth())/2+LEFT_MARGIN, TOP_MARGIN+name.getAscent()+IMAGE_MARGIN+IMAGE_HEIGHT/2+noImage.getAscent()/2 );
		}
	}
	
	//shows user's username
	private void setUsername(FacePamphletProfile profile){
		name= new GLabel (profile.getName());
		name.setColor(Color.blue);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN+name.getAscent());
	}
	
	//shows user's friendslist
	private void setFriendslist(FacePamphletProfile profile){
		GLabel friendsLabel= new GLabel("Friends:");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsLabel, (getWidth()-friendsLabel.getWidth())/2, TOP_MARGIN+name.getAscent() + IMAGE_MARGIN);
		Iterator <String> list= profile.getFriends();
		double position= TOP_MARGIN + IMAGE_MARGIN + friendsLabel.getHeight()+friendsLabel.getAscent()+5;
		while(list.hasNext()){
			String friends= list.next();
			GLabel friendslist= new GLabel(friends);
			position+=friendslist.getAscent()+2;
			friendslist.setFont(PROFILE_FRIEND_FONT);
			add(friendslist, (getWidth()-friendsLabel.getWidth())/2, position-friendslist.getAscent());
		}
	}
	
	//shows user's status
	private void setstatus(FacePamphletProfile profile){
		GLabel status= new GLabel(profile.getStatus());
		status.setFont(PROFILE_STATUS_FONT);
		if(profile.getStatus()!=""){
			add(status, LEFT_MARGIN, TOP_MARGIN+name.getAscent()+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN+status.getAscent());
		}else{
			status= new GLabel("No current status");
			add(status ,LEFT_MARGIN, TOP_MARGIN+name.getAscent()+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN + status.getAscent());
		}
	}
}