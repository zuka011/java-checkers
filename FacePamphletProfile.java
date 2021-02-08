/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * This class keeps track of all the information for one profile
 * in the FacePamphlet social network.  Each profile contains a
 * name, an image (which may not always be set), a status (what 
 * the person is currently doing, which may not always be set),
 * and a list of friends.
 */

import acm.graphics.*;
import acm.util.ErrorException;

import java.util.*;

public class FacePamphletProfile implements FacePamphletConstants {

	/** Saves names of friends */
	private ArrayList<String> friends;

	/** Regular info */
	private String name;
	private String status;

	/** For saving image info */
	private GImage image;
	private String imageName;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * profile.
	 */
	public FacePamphletProfile(String name) {
		this.name = name;
		this.friends = new ArrayList<String>();
		this.status = "No current status";
		this.image = null;
		this.imageName = "";
	}

	/** This method returns the name associated with the profile. */
	public String getName() {
		return name;
	}

	/**
	 * This method returns the image associated with the profile. If there is no
	 * image associated with the profile, the method returns null.
	 */
	public GImage getImage() {
		return image;
	}

	/** This method sets the image associated with the profile. */
	public Boolean setImage(String image) {
		return openImage(image);
	}

	/**
	 * This method returns the status associated with the profile. If there is no
	 * status associated with the profile, the method returns the empty string ("").
	 */
	public String getStatus() {
		return status;
	}

	/** This method sets the status associated with the profile. */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method adds the named friend to this profile's list of friends. It
	 * returns true if the friend's name was not already in the list of friends for
	 * this profile (and the name is added to the list). The method returns false if
	 * the given friend name was already in the list of friends for this profile (in
	 * which case, the given friend name is not added to the list of friends a
	 * second time.)
	 */
	public boolean addFriend(String friend) {
		if (this.friends.contains(friend))
			return false;

		this.friends.add(friend);
		return true;
	}

	/**
	 * This method removes the named friend from this profile's list of friends. It
	 * returns true if the friend's name was in the list of friends for this profile
	 * (and the name was removed from the list). The method returns false if the
	 * given friend name was not in the list of friends for this profile (in which
	 * case, the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		if (this.friends.contains(friend)) {
			this.friends.remove(friend);
			return true;
		}
		return false;
	}

	/**
	 * This method returns an iterator over the list of friends associated with the
	 * profile.
	 */
	public Iterator<String> getFriends() {
		return friends.iterator();

	}

	/**
	 * This method returns a string representation of the profile. This string is of
	 * the form: "name (status): list of friends", where name and status are set
	 * accordingly and the list of friends is a comma separated list of the names of
	 * all of the friends in this profile.
	 * 
	 * For example, in a profile with name "Alice" whose status is "coding" and who
	 * has friends Don, Chelsea, and Bob, this method would return the string:
	 * "Alice (coding): Don, Chelsea, Bob"
	 */
	public String toString() {
		String str = "";

		str += this.name + ";";
		str += "{" + this.imageName + "};";
		str += "(" + this.status + ")";
		str += ";";

		for (int i = 0; i < friends.size(); i++) {
			str += ";" + friends.get(i);
		}

		return str;
	}

	/** Checks if this person is friends with selected one */
	public Boolean isFriendsWith(String friend) {
		return friends.contains(friend);
	}

	/**
	 * Opens the image file if it exists and sets imageName value to the current
	 * image name, so when data is saved to txt file and then it is recovered, it
	 * becomes easier to set images
	 */
	private Boolean openImage(String text) {
		GImage temp;

		try {

			temp = new GImage("images/" + text);

			if (temp != null) {
				this.imageName = text;
				this.image = temp;
			}
			return true;

		} catch (ErrorException ex) {

			return false;
		}
	}

}
