import java.awt.Color;

public interface FacePamphletConstantsExtension {
	public static final double BUTTON_WIDTH = 110;
	public static final double BUTTON_HEIGHT = 45;
	public static final Color VERY_DARK_GREEN = new Color(0, 102, 0);
	public static final Color COLOR_1 = new Color(30, 110, 100);
	/** The width of the application window */
	public static final int APPLICATION_WIDTH = 1200;

	/** The height of the application window */
	public static final int APPLICATION_HEIGHT = 700;

	/** Number of characters for each of the text input fields */
	public static final int TEXT_FIELD_SIZE = 15;

	/**
	 * Text to be used to create an "empty" label to put space between interactors
	 * on EAST border of application. Note this label is not actually the empty
	 * string, but rather a single space
	 */
	public static final String EMPTY_LABEL_TEXT = " ";

	/**
	 * Name of font used to display the application message at the bottom of the
	 * display canvas
	 */
	public static final String MESSAGE_FONT = "Dialog-18";

	/** Name of font used to display the name in a user's profile */
	public static final String PROFILE_NAME_FONT = "Dialog-26-bold";

	/**
	 * Name of font used to display the text "No Image" in user profiles that do not
	 * contain an actual image
	 */
	public static final String PROFILE_IMAGE_FONT = "Dialog-24-bold";
	
	public static final String NO_IMAGE_FONT = "Dialog-10";
	
	
	
	/** Name of font used to display the status in a user's profile */
	public static final String PROFILE_STATUS_FONT = "Dialog-20-bold";

	public static final String PROFILE_STATUS_FONT1 = "Dialog-26-bold";
	/**
	 * Name of font used to display the label "Friends" above the user's list of
	 * friends in a profile
	 */
	public static final String PROFILE_FRIEND_LABEL_FONT = "Dialog-24-bold";

	/**
	 * Name of font used to display the names from the user's list of friends in a
	 * profile
	 */
	public static final String PROFILE_FRIEND_FONT = "Dialog-20";
	
	public static final double EDGE_SIZE = 5;
	
	
	/** The width (in pixels) that profile images should be displayed */
	public static final double IMAGE_WIDTH = 200;
	public static final double IMAGE_WIDTH1 = 17;
	/** The height (in pixels) that profile images should be displayed */
	public static final double IMAGE_HEIGHT = 200;
	public static final double IMAGE_HEIGHT1 = 17;
	/**
	 * The number of pixels in the vertical margin between the bottom of the canvas
	 * display area and the baseline for the message text that appears near the
	 * bottom of the display
	 */
	public static final double BOTTOM_MESSAGE_MARGIN = 20;
	public static final double BETWEEN_FRIEND_NAMES = 23;
	public static final double BETWEEN_FRIEND_NAMES1 = 27;
	/**
	 * The number of pixels in the hortizontal margin between the left side of the
	 * canvas display area and the Name, Image, and Status components that are
	 * display in the profile
	 */
	public static final double LEFT_MARGIN = 20;

	public static final double FRIEND_BOX_WIDTH = 230;
	
	public static final double FRIEND_BOX_HEIGHT = 500;
	/**
	 * The number of pixels in the vertical margin between the top of the canvas
	 * display area and the top (NOT the baseline) of the Name component that is
	 * displayed in the profile
	 */
	public static final double TOP_MARGIN = 25;

	/**
	 * The number of pixels in the vertical margin between the baseline of the Name
	 * component and the top of the Image displayed in the profile
	 */
	public static final double IMAGE_MARGIN = 20;

	/**
	 * The number of vertical pixels in the vertical margin between the bottom of
	 * the Image and the top of the Status component in the profile
	 */
	public static final double STATUS_MARGIN = 25;
	public static final int SCROLL_SIZE = 15;
	public static final int SCROLL_WIDTH = 80;
	public static final int SCROLL_HEIGHT = 350;

}

