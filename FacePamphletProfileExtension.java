package images;

import acm.graphics.*;
import java.util.*;

public class FacePamphletProfileExtension implements FacePamphletConstantsExtension {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * profile.
	 */
	public FacePamphletProfileExtension(String name) {
		userName = name;
		profilePicture = null;
		userStatus = "";
	}

	/** This method returns the name associated with the profile. */
	public String getName() {
		return userName;
	}

	/**
	 * This method returns the image associated with the profile. If there is no
	 * image associated with the profile, the method returns null.
	 */
	public GImage getImage() {

		return profilePicture;
	}

	/** This method sets the image associated with the profile. */
	public void setImage(GImage image) {
		profilePicture = image;
	}

	/**
	 * This method returns the status associated with the profile. If there is no
	 * status associated with the profile, the method returns the empty string ("").
	 */
	public String getStatus() {

		return userStatus;
	}

	/** This method sets the status associated with the profile. */
	public void setStatus(String status) {
		userStatus = status;

	}

	public void setGender(String gender) {
		userGender = gender;
	}

	public String getGender() {
		return userGender;
	}

	public void setHousing(String housing) {
		userHousing = housing;
	}

	public String getHousing() {
		return userHousing;
	}

	public void setRelationship(String rel) {
		userRelationship = rel;
	}

	public String getRelationship() {
		return userRelationship;
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
		boolean p = userFriendList.contains(friend);
		if (!p) {
			userFriendList.add(friend);
			return true;
		} else
			return false;
	}

	/**
	 * This method removes the named friend from this profile's list of friends. It
	 * returns true if the friend's name was in the list of friends for this profile
	 * (and the name was removed from the list). The method returns false if the
	 * given friend name was not in the list of friends for this profile (in which
	 * case, the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		boolean p = userFriendList.contains(friend);
		if (p) {
			userFriendList.remove(friend);
			return true;
		}
		return false;
	}

	/**
	 * This method returns an iterator over the list of friends associated with the
	 * profile.
	 */
	public Iterator<String> getFriends() {
		Iterator<String> it = userFriendList.iterator();
		return it;
	}

	// This method returns the corresponding profile friends list as ArrayList
	public ArrayList<String> getFriendsAsList() {
		ArrayList<String> copy = new ArrayList<String>();
		Iterator<String> it = userFriendList.iterator();
		while (it.hasNext()) {
			copy.add(it.next());
		}
		return copy;
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
		String friends = "";
		for (int i = 0; i < userFriendList.size(); i++) {
			if (i != userFriendList.size() - 1) {
				friends = friends + ((ArrayList<String>) userFriendList).get(i) + ", ";
			} else {
				friends = friends + ((ArrayList<String>) userFriendList).get(i);
			}
		}

		String profile = "\"" + userName + "(" + userStatus + "): " + friends + "\"";
		return profile;
	}

	private String userHousing;
	private String userGender;
	private String userRelationship;
	private String userName;
	private String userStatus;
	private GImage profilePicture;
	private Collection<String> userFriendList = new ArrayList<String>();

}
