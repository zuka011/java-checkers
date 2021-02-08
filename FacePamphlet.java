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

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	private JTextField upperTextfield;
	private JTextField statusTextfield;
	private JTextField pictureTextfield;
	private JTextField friendTextfield;
	
	
	
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		// You fill this in
		name= new JLabel ("Name");
		add(name, NORTH);
		
		upperTextfield= new JTextField (TEXT_FIELD_SIZE);
		add(upperTextfield, NORTH);
		
		add= new JButton ("Add");
		delete= new JButton ("Delete");
		lookup= new JButton ("Lookup");
		add(add,NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);
		
		changeStatus= new JButton("Change Status");
		statusTextfield= new JTextField (TEXT_FIELD_SIZE);
		statusTextfield.setActionCommand("Change Status");
		JLabel upperEmptyLabel= new JLabel(EMPTY_LABEL_TEXT);
		add(statusTextfield, WEST);
		add(changeStatus, WEST);
		add(upperEmptyLabel,WEST);
		statusTextfield.addActionListener(this);
		
		changePicture= new JButton("Change Picture");
		pictureTextfield= new JTextField (TEXT_FIELD_SIZE);
		pictureTextfield.setActionCommand("Change Picture");
		JLabel middleEmptyLabel= new JLabel(EMPTY_LABEL_TEXT);
		add(pictureTextfield, WEST);
		add(changePicture, WEST);
		add(middleEmptyLabel,WEST);
		pictureTextfield.addActionListener(this);
		
		addFriend= new JButton("Add Friend");
		friendTextfield= new JTextField (TEXT_FIELD_SIZE);
		friendTextfield.setActionCommand("Add Friend");
		JLabel lowerEmptyLabel= new JLabel(EMPTY_LABEL_TEXT);
		add(friendTextfield, WEST);
		add(addFriend, WEST);
		add(lowerEmptyLabel,WEST);
		friendTextfield.addActionListener(this);
		
		
		addActionListeners();
		canvas= new FacePamphletCanvas();	
		add(canvas);
	
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
	private FacePamphletCanvas canvas;
	
    public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
 
    	//creates user if one doesn't exist, otherwise displays according message
    	
    	String user= upperTextfield.getText();
    	
    	if(!upperTextfield.getText().isEmpty()){
    		if(e.getActionCommand()=="Add" ){
    			if(!base.containsProfile(user)){
    				profileClass= new FacePamphletProfile(user);
    				base.addProfile(profileClass);
    				canvas.displayProfile(profileClass);
    				canvas.showMessage("New profile created");
    			}else{
    				profileClass= base.getProfile(user);
    				canvas.displayProfile(profileClass);
    				canvas.showMessage("A profile with the name " + user + " already exists");
    			}
    		
    		}
    	
    		//deletes user's profile if one exists, otherwise displays according message
    		if(e.getActionCommand()=="Delete" ){
    			if(base.containsProfile(user)){
    				base.deleteProfile(user);
    				canvas.removeAll();
    				canvas.showMessage("Profile of " + user + " deleted");
    			}else{
    				canvas.showMessage("A profile with the name " + user + " already exists");
    			}
    		}
    		
    		//shows user's profile if one exists, otherwise displays according message
    		if(e.getActionCommand()=="Lookup"){
    			
    			if(base.containsProfile(user)){
    				profileClass= base.getProfile(user);
    				canvas.displayProfile(profileClass);
    				canvas.showMessage("Displaying " + user);
    			}
    			else{
    				remove(canvas);
    				add(canvas);
    				canvas.showMessage("A profile with the name "+ user + " already exists" );
    			}	
    		}
    	}
    	
    	
    	//shows selected user's updated status
    	if(!statusTextfield.getText().isEmpty() && e.getActionCommand()=="Change Status"){
    		if(base.containsProfile(user)){
    			profileClass.setStatus(statusTextfield.getText());
    			canvas.displayProfile(profileClass);
    			canvas.showMessage("Status updated to " + statusTextfield.getText());
    			
    		}else{
    			canvas.displayProfile(profileClass);
    			canvas.showMessage("please select a profile to change status");
    		}
    		statusTextfield.setText("");
    	
    	}
    	
    	//shows selected user's updated picture
    	if(!pictureTextfield.getText().isEmpty() &&  e.getActionCommand()=="Change Picture"){
    	
    		if(base.containsProfile(user)){
    			changePicture(pictureTextfield.getText());
    		}else{
    			canvas.displayProfile(profileClass);
    			canvas.showMessage("Please select a profile to change picture");

    		}
    		pictureTextfield.setText("");
    	}
    	
    	//shows selected user's updated friendslist
    	if(!friendTextfield.getText().isEmpty() && e.getActionCommand()=="Add Friend"){
    		
    		if(base.containsProfile(user)){
    			if(base.containsProfile(friendTextfield.getText())){
    				if(profileClass.addFriend(friendTextfield.getText())){//!profileClass.getFriends().equals(friendTextfield.getText())){
    					FacePamphletProfile addedFriend= base.getProfile(friendTextfield.getText());
    					addedFriend.addFriend(user);
    					canvas.displayProfile(profileClass);
    					canvas.showMessage(friendTextfield.getText() + " added as a friend");
    					
    				}else{
    					canvas.displayProfile(profileClass);
    					canvas.showMessage(user +" already has " + friendTextfield.getText() + " as a friend");   					
    				}
    			
    			}else{
    				canvas.displayProfile(profileClass);
					canvas.showMessage(friendTextfield.getText() + " does not exist");
			
    			}
    		}else{
    			canvas.displayProfile(profileClass);
    			canvas.showMessage("Please select a profile to add friend");
    			
    		}
    	}
    	
    	friendTextfield.setText("");
    	
	}
    
    //displays profile pic if it's is readable, otherwise displays according message
    private void changePicture(String picName){
    	try{
//    		image = new GImage("Assignment7/images/"+picName);	
    		image = new GImage("./"picName)
    		profileClass.setImage(image);
    		canvas.displayProfile(profileClass);
			canvas.showMessage("Picture updated");
    	}catch (ErrorException ex){
    		canvas.displayProfile(profileClass);
    		canvas.showMessage("Unable to open image file " + pictureTextfield.getText() );
    	}
    }
    
    private GImage image=null;
    
    private JButton add;
    private JButton delete;
    private JButton lookup;
    private JButton changeStatus;
    private JButton changePicture;
    private JButton addFriend;
    
    private JLabel name;
    
    private FacePamphletDatabase base = new FacePamphletDatabase();
    
    private FacePamphletProfile profileClass;
}
