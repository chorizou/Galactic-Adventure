
/**
* The Cargo class represents all merchandise the player can 
* purchase or possess and contains all of the cargo's attributes,
* such as price, mass, image, and name.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
import java.awt.Image;
 
import javax.swing.ImageIcon;
 
public abstract class Cargo {
    private String name;
    private Image img;
    private int price;

    private String flavorText;
     
    private static final int ICON_SIZE = 15;
    
    /** Constructs a new Cargo item with the given parameters
     *  @param cargoName the name of the cargo item
     *  @price the price of the cargo item
     */
    public Cargo(String cargoName, int price) {
        name = cargoName;
        this.price = price;
        loadIcon();
    }
     
    /** Loads the icon image of different cargo items
     */
    private void loadIcon() {
        ImageIcon ii = new ImageIcon("res/Assets/" + name + ".png");
        img = ii.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT);
    }
     
    /** Returns the price of the cargo item
     *  @return the price of the cargo item
     */
    public int getValue() {
        return price;
    }
    
    /** Returns the image of the cargo item
     *  @return the image of the cargo item
     */
    public Image getIcon() {
    	return img;
    }
     
    /** Returns the name of the cargo item
     *  @return the name of the cargo item
     */
    public String getName() {
    	return name;
    }
    
    /** Sets the name of the cargo item to the designated string
     *  @param s the name to set
     */
    public void setName(String s) {
    	name = s;
    }
    
    /** Sets the price of the cargo item to the designated price
     *  @param price the price to be set
     */
    public void setPrice(int price) {
    	this.price = price;
    }
    
    @Override
    public String toString() {
    	return name;
    }
}