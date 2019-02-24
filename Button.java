
/**
* The Button class represents the buttons that the user can click on
* in the main menu and store menu to elicit an action, such as 
* starting the game or buying cargo items.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.Rectangle;

public class Button {
	// defines the bounds and type of button
	Rectangle bounds;
	public Type type;
	
	private String defaultMsg;
	private String message;
	
	/** Constructs a new Button object with the given parameters
     *  @param x the x coordinate
     *  @param y the y coordinate
     *  @param w the width of the button
     *  @param h the height of the button
     *  @param t the type of button
     */
	public Button(int x,int y,int w,int h, Type t) {
		bounds = new Rectangle(x, y, w, h);
		type = t;
		defaultMsg = "SOLD";
		message = null;
	}
	
	/** Returns whether or not the button contains the given coordinates
     *  @param xcoord the x coordinate to test
     *  @param ycoord the y coordinate to test
     *  @return whether or not the button contains the given coordinates
     */
	public boolean contains(int xcoord, int ycoord){
		return bounds.contains(xcoord, ycoord);
	}
	
	/** Returns the bounds of the button
     *  @return the bounds of the button
     */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/** Sets the default message to the given string
     *  @param msg the given string
     */
	public void setDefaultMsg(String msg) {
		defaultMsg = msg;
	}
	
	/** Sets the message to the given string
     *  @param msg the given string
     */
	public void setMsg(String msg) {
		message = msg;
	}
	
	/** Returns the message of the button
     *  @return the message of the button
     */
	public String getMsg() {
		if(message == null)
			return defaultMsg;
		return message;
	}
	
	/** Sets the message to null
     */
	public void reset() {
		message = null;
	}
	
	 /** Defines the different types of buttons
     */
	public static enum Type{
		START,
		TUTORIAL,
		EXIT,
		TUT_TO_START,
		END_TO_MENU,
		FOOD,
		FUEL,
		REPAIR,
		ONE,
		TWO,
		THREE,
		EXITSTORE
	}
}