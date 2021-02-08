package images;

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

public class FacePamphletCanvasExtension extends GCanvas implements FacePamphletConstantsExtension {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * dislogin
	 */
	public FacePamphletCanvasExtension() {
	}

	/**
	 * This method dislogins a message string near the bottom of the canvas. Every
	 * time this method is called, the previously dislogined message (if any) is
	 * replaced by the new message text passed in.
	 */

	public void showMessage(String msg) {
		if (message != null) {
			remove(message);
		}
		message = new GLabel(msg);
		double x = getWidth() / 2 - message.getWidth() / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		message.setColor(Color.BLACK);
		message.setFont(MESSAGE_FONT);
		message.move(x, y);
		add(message);
	}

	/**
	 * This method dislogins the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages dislogined near the bottom
	 * of the screen) and then the given profile is dislogined. The profile dislogin
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 * 
	 * @throws InterruptedException
	 */
	public void introduction() throws InterruptedException {
		try {
			TimeUnit.SECONDS.sleep((long) 2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nameOfProgram();
		login();
	}

	private void nameOfProgram() throws InterruptedException {
		facePamphlet = new GLabel("FACE  PAMPHLET", 0, 0);
		facePamphlet.setFont("Sanserif-bolditalic-50");
		facePamphlet.setColor(rgen.nextColor());
		double dx = APPLICATION_WIDTH / 2 - facePamphlet.getWidth() / 2;
		double dy = APPLICATION_HEIGHT / 3 + facePamphlet.getAscent() / 2;
		facePamphlet.move(dx, dy);
		for (int i = 0; i < 10; i++) {
			setBackground(rgen.nextColor());
			add(facePamphlet);
			TimeUnit.MILLISECONDS.sleep(200);
			remove(facePamphlet);
			facePamphlet.setColor(rgen.nextColor());
		}

		facePamphlet.setColor(Color.blue);
		add(facePamphlet);
	}

	private void login() throws InterruptedException {
		setBackground(Color.pink);
		loadText();
		loading();
		removeAll();

	}

	private void loadText() {
		double centreBoxX = (APPLICATION_WIDTH / 2 - BUTTON_WIDTH / 2) + BUTTON_WIDTH / 2;
		double centreBoxY = (APPLICATION_HEIGHT / 2 - BUTTON_HEIGHT / 2) + BUTTON_HEIGHT / 2;
		GLabel load = new GLabel("LOADING", 0, 0);
		load.setFont("Sanserif-bolditalic-20");
		load.setColor(Color.black);
		double dx = centreBoxX - load.getWidth() / 2;
		double dy = centreBoxY + load.getAscent() / 2;
		load.move(dx, dy);
		add(load);
	}

	// typical loading process
	private void loading() throws InterruptedException {
		whiteBox();
		for (int i = 0; i < (BUTTON_WIDTH + BUTTON_HEIGHT) / 2; i++) {
			double rectHeight = 2 * 8;
			double rectWidth = 2;
			double x = (APPLICATION_WIDTH / 2 - BUTTON_WIDTH / 2) - BUTTON_HEIGHT / 2 + i * rectWidth;
			double y = (APPLICATION_HEIGHT / 2 - BUTTON_HEIGHT / 2) + 3 * BUTTON_HEIGHT / 2;
			GRect rect = new GRect(x, y, rectWidth, rectHeight);
			rect.setColor(Color.green);
			rect.setFilled(true);
			rect.setFillColor(Color.green);
			add(rect);
			TimeUnit.MILLISECONDS.sleep(50);
		}
		TimeUnit.SECONDS.sleep(1);
	}

	// white background for green loading line
	private void whiteBox() {
		double rectHeight = 2 * 8;
		double rectWidth = 2 * (int) (BUTTON_WIDTH + BUTTON_HEIGHT) / 2;
		double x = (APPLICATION_WIDTH / 2 - BUTTON_WIDTH / 2) - BUTTON_HEIGHT / 2;
		double y = (APPLICATION_HEIGHT / 2 - BUTTON_HEIGHT / 2) + 3 * BUTTON_HEIGHT / 2;
		GRect whiteBox = new GRect(x, y, rectWidth, rectHeight);
		whiteBox.setFilled(true);
		whiteBox.setFillColor(Color.white);
		add(whiteBox);

	}

	public void displayProfile(FacePamphletProfileExtension profile) {
		removeAll();
		addFriendBox();
		addName(profile.getName());
		createProfilePicture(profile);
		createStatus(profile);
		createFriendLabel();
		createFriendList(profile);
	}

	/*
	 * This method adds a Label to the canvas. In one case it gets the coordinates
	 * of the lower left point of the label, and in the other case it gets the
	 * coordinates of the point to which the label should be centered.
	 */
	private void createLabel(String text, double x, double y, String font, Color color, boolean isCentre) {
		GLabel label = new GLabel(text, 0, 0);
		label.setFont(font);
		label.setColor(color);
		if (isCentre) {
			double dx = x - label.getWidth() / 2;
			double dy = y + label.getHeight() / 2;
			label.move(dx, dy);
		} else {
			label.move(x, y);
		}

		add(label);
	}

	// This method adds a Profile name label to the canvas.
	private void addName(String name) {
		createLabel(name, LEFT_MARGIN, TOP_MARGIN, PROFILE_NAME_FONT, COLOR_1, false);
	}
	/*
	 * This method adds a corresponding profile picture. If the user does not have
	 * an image selected, a rectangle and a label with the appropriate content will
	 * be added instead.
	 */

	private void createProfilePicture(FacePamphletProfileExtension profile) {
		if (profile.getImage() == null) {
			GRect image = new GRect(LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
			createLabel("No Image", image.getX() + IMAGE_WIDTH / 2, image.getY() + IMAGE_HEIGHT / 2, PROFILE_IMAGE_FONT,
					Color.BLACK.brighter(), true);

		} else {
			GImage image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setLocation(LEFT_MARGIN, IMAGE_MARGIN + TOP_MARGIN);
			add(image);
		}

	}

//  This method adds a status-appropriate label to the canvas
	private void createStatus(FacePamphletProfileExtension profile) {
		double y = IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN;
		double x = LEFT_MARGIN;
		if (profile.getStatus().equals("")) {
			createLabel("No current status", x, y, PROFILE_STATUS_FONT1, COLOR_1, false);
		} else {
			String status = profile.getName() + " is " + profile.getStatus();
			createLabel(status, x, y, PROFILE_STATUS_FONT, COLOR_1, false);
		}

	}

//This method adds a list of friends to Canvas
	private void createFriendList(FacePamphletProfileExtension profile) {

		for (int i = 0; i < profile.getFriendsAsList().size(); i++) {
			String name = profile.getFriendsAsList().get(i);
			GLabel label = new GLabel(name, 0, 0);
			label.setFont(PROFILE_FRIEND_FONT);
			label.setColor(VERY_DARK_GREEN);
			label.move(getWidth() / 2 + EDGE_SIZE,
					IMAGE_MARGIN + TOP_MARGIN + BETWEEN_FRIEND_NAMES1 + i * BETWEEN_FRIEND_NAMES);

			add(label);

			if ((label.getY() < friendBox.getY() + label.getHeight()
					|| label.getY() > friendBox.getY() + friendBox.getHeight()) && friendLabels.size() != 0) {
				label.setVisible(false);

			} else {
				label.setVisible(true);
			}
			friendLabels.add(label);
		}

	}

	public void scrollUp() {
		if (friendLabels.size() != 0) {
		if (friendLabels.get(0).getY() < friendBox.getY() + friendLabels.get(0).getHeight()) {
			for (int i = 0; i < friendLabels.size(); i++) {
				double x = friendLabels.get(i).getX();
				double y = friendLabels.get(i).getY() + BETWEEN_FRIEND_NAMES;
				friendLabels.get(i).setLocation(x, y);
				if ((y > friendBox.getY() + friendBox.getHeight()
						|| y < friendBox.getY() + friendLabels.get(i).getHeight()) && friendLabels.size() != 0) {
					friendLabels.get(i).setVisible(false);

				} else {
					friendLabels.get(i).setVisible(true);
				}

			}

		}
	}
	}

	public void scrollDown() {
		if (friendLabels.size() != 0) {
			if (friendLabels.get(friendLabels.size() - 1).getY() > friendBox.getY() + friendBox.getHeight())
				for (int i = 0; i < friendLabels.size(); i++) {
					double x = friendLabels.get(i).getX();
					double y = friendLabels.get(i).getY() - BETWEEN_FRIEND_NAMES;
					friendLabels.get(i).setLocation(x, y);

					if ((y < friendBox.getY() + friendLabels.get(i).getHeight()
							|| y > friendBox.getY() + friendBox.getHeight()) && friendLabels.size() != 0) {
						friendLabels.get(i).setVisible(false);
					} else {

						friendLabels.get(i).setVisible(true);
					}
				}

		}

	}

	private void addFriendBox() {
		friendBox = new GRect(getWidth() / 2, IMAGE_MARGIN + TOP_MARGIN, FRIEND_BOX_WIDTH, FRIEND_BOX_HEIGHT);
		friendBox.setFilled(true);
		friendBox.setFillColor(Color.WHITE);
		friendBox.setColor(Color.blue);
		add(friendBox);
	}

	public void showMore(FacePamphletProfileExtension currentProfile) {
		double y0 = IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT + 3 * STATUS_MARGIN;
		double x0 = LEFT_MARGIN;
		String friends = "Friends :  " + currentProfile.getFriendsAsList().size();
		createLabel(friends, x0, y0, PROFILE_STATUS_FONT, COLOR_1, false);

		double y1 = IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT + 6 * STATUS_MARGIN;
		double x1 = LEFT_MARGIN;
		String Gender = "Gender : " + currentProfile.getGender();
		createLabel(Gender, x1, y1, PROFILE_STATUS_FONT, COLOR_1, false);

		double y2 = IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT + 9 * STATUS_MARGIN;
		double x2 = LEFT_MARGIN;
		if ((currentProfile.getHousing() == null) || (currentProfile.getHousing().equals(""))) {

			String Housing = "Housing   : No Information";
			createLabel(Housing, x2, y2, PROFILE_STATUS_FONT, COLOR_1, false);

		} else {
			String housing = currentProfile.getHousing();
			String Housing = "Housing : " + housing;
			createLabel(Housing, x2, y2, PROFILE_STATUS_FONT, COLOR_1, false);
		}

		double y3 = IMAGE_MARGIN + TOP_MARGIN + IMAGE_HEIGHT + 12 * STATUS_MARGIN;
		double x3 = LEFT_MARGIN;
		if ((currentProfile.getRelationship() == null) || (currentProfile.getRelationship().equals(""))) {
			String relationships = "Relationships : No Information";
			createLabel(relationships, x3, y3, PROFILE_STATUS_FONT, COLOR_1, false);
		} else {
			String rel = currentProfile.getRelationship();
			String relationships = "Relationships : " + rel;
			createLabel(relationships, x3, y3, PROFILE_STATUS_FONT, COLOR_1, false);
		}

	}

	/*
	 * the label "Friends" above the user's list of friends in a profile
	 */
	private void createFriendLabel() {
		double x = getWidth() / 2 + friendBox.getWidth() / 2;
		double y = TOP_MARGIN / 2;
		createLabel("Friends ", x, y, PROFILE_FRIEND_LABEL_FONT, Color.red, true);
	}

	private ArrayList<GLabel> friendLabels = new ArrayList<GLabel>();
	private GRect friendBox;
	private GLabel message;
	private GLabel facePamphlet;
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
