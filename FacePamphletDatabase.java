/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import acm.util.ErrorException;

public class FacePamphletDatabase implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * database.
	 */
	public FacePamphletDatabase() {
		// You fill this in
		data = new HashMap<String, FacePamphletProfile>();
		existingData();

	}

	/** Reads Data.txt file by BufferedReader and adds Existing profiles to data */
	private void existingData() {

		BufferedReader file = null;
		String line;
		try {
			file = new BufferedReader(new FileReader("Data.txt"));
			while (true) {
				if ((line = file.readLine()) == null)
					break;
				addProfile(toProfile(line));
			}

			file.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	/** Transforms information from string to FacePamphletProfile class */
	private FacePamphletProfile toProfile(String line) {
		ArrayList<String> str = new ArrayList<String>();

		addInfoToList(new StringTokenizer(line, ";"), str);

		FacePamphletProfile profile = new FacePamphletProfile(str.get(0));

		if (!str.get(1).equals("{}"))
			profile.setImage(str.get(1).substring(1, str.get(1).length() - 1));

		if (!str.get(2).contentEquals("():"))
			profile.setStatus(str.get(2).substring(1, str.get(2).length() - 1));

		for (int i = 3; i < str.size(); i++) {
			profile.addFriend(str.get(i));
		}

		return profile;
	}

	/** StringTokenizer divides info into usable pieces */
	private void addInfoToList(StringTokenizer tk, ArrayList<String> str) {
		String temp;

		while (tk.hasMoreTokens()) {
			temp = tk.nextToken();
			str.add(temp);
		}

	}

	/** Saves each update of the profile list to data file */
	public void saveProfileData() {
		Iterator<String> it = data.keySet().iterator();

		try {
			// Creates a BufferedWriter
			BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt"));

			// Writes data to the file
			while (it.hasNext()) {
				writer.write(data.get(it.next()).toString());
				writer.newLine();
			}
			// Flushes data to the destination
			writer.flush();

			writer.close();
		}

		catch (Exception e) {
			e.getStackTrace();
		}
	}

	/**
	 * This method adds the given profile to the database. If the name associated
	 * with the profile is the same as an existing name in the database, the
	 * existing profile is replaced by the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		data.put(profile.getName(), profile);
	}

	/**
	 * This method returns the profile associated with the given name in the
	 * database. If there is no profile in the database with the given name, the
	 * method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		return data.get(name);
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
		Iterator<String> list = data.get(name).getFriends();
		FacePamphletProfile friend;

		if (list != null) {
			while (list.hasNext()) {

				friend = data.get(list.next());

				friend.removeFriend(name);
			}
		}

		data.remove(name);

	}

	/**
	 * This method returns true if there is a profile in the database that has the
	 * given name. It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		if (data.containsKey(name))
			return true;
		return false;
	}

	/** HashMap where every FacePamphletProfile is saved */
	private HashMap<String, FacePamphletProfile> data;

}
