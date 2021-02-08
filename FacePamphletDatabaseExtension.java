
import java.util.*;

public class FacePamphletDatabaseExtension implements FacePamphletConstantsExtension {
	private Map<String, FacePamphletProfileExtension> data = new HashMap<String, FacePamphletProfileExtension>();

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */
	public FacePamphletDatabaseExtension() {
		// You fill this in
	}

	/**
	 * This method adds the given profile to the database. If the name associated
	 * with the profile is the same as an existing name in the database, the
	 * existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(FacePamphletProfileExtension profile) {
		String name = profile.getName();
		data.put(name, profile);
	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public FacePamphletProfileExtension getProfile(String name) {
		if (data.containsKey(name)) {
			FacePamphletProfileExtension profile = data.get(name);
			return profile;
		} else
			return null;
	}

	/**
	 * This method removes the profile associated with the given name from the
	 * database. It also updates the list of friends of all other profiles in the
	 * database to make sure that this name is removed from the list of friends of
	 * any other profile.
	 * 
	 * If there is no profile in the database with the given name, then the database
	 * is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if (containsProfile(name)) {
			data.remove(name);
			for (String key : data.keySet()) {
				FacePamphletProfileExtension profile = data.get(key);
				Iterator<String> it = profile.getFriends();
				while (it.hasNext()) {
					String friend = it.next();
					if (friend.equals(name)) {
						profile.removeFriend(name);
					}
				}
			}
		}
	}

	/**
	 * This method returns true if there is a profile in the database that has the
	 * given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		return data.containsKey(name);
	}

}
