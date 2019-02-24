
/**
* The SpaceObject class represents all objects in space and their
* attributes, such as image, location, and dimensions.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
 
import javax.swing.ImageIcon;
 
/**
 * represents all objects in space
 * sotres: image, location, dimensions  
 * @author teddy.hsieh.1
 *
 */
public abstract class SpaceObject{
    private Image img;  //img of Ship
    private int w; //width of ship
    private int h; //height of ship
    private int x;
    private int y;
    private boolean visible; // visibility is also alive or not
     
    public SpaceObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        visible = true;
    }
     
    /**
     * Loads and store the image of space object from Assets
     */
    protected void loadImage(String imgName) {
        ImageIcon ii = new ImageIcon(imgName);
        img = ii.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
    }
     
    protected void scaleImage(int width, int height) {
        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
     
     
/* ------------------Collision---------------------*/
    public boolean isVisible() {
        return visible;
    }
     
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
     
    public abstract Shape getShape();
     
    /**
     * Gets the area of the SpaceObject according to its getShape()
     * NOT according to rectangular bounds.
     * @return area of SpaceObject interior
     */
    public Area getArea() {
        return new Area(getShape());
    }
     
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
    // Test if a coordinate is contained
    public boolean contains(int xCoord, int yCoord) {
        return getArea().contains(xCoord, yCoord);
    }
     
    /**
     * if this SpaceObject is completely contained in the specified SpaceObject
     * @param other
     * @return
     */
    public boolean contains(SpaceObject other) {
        Area temp = (Area) getArea().clone();
        other.getArea().intersect(temp);
        return temp.equals(getArea());
    }
     
    /**
     * Tests if two SpaceObjects intersect using their Area instance variable
     * Creates a temporary area representing the union of the two object Areas
     * @param other the other SpaceObject being tested for intersection
     * @return true if the interior of the two SpaceObject intersects
     */
    public boolean intersects(SpaceObject other) {
        Area temp = (Area) getArea().clone();
        temp.intersect(other.getArea());
        return !temp.isEmpty();
    }
 
/* ------------------_Basic Accessors---------------------*/
    /**
     * @return the left x coordinate of SpaceObject
     */
    public int getX() {
        return x;
    }
     
    /**
     * @return the top y coordinate of SpaceObject
     */
    public int getY() {
        return y;
    }
     
    /**
     * @return the width of SpaceObject
     */
    public int getWidth() {
        return w;
    }
     
    /**
     * @return the height of SpaceObject
     */
    public int getHeight() {
        return h;
    }
     
    public Image getImage() {
        return img;
    }
     
    @Override
    public String toString(){
        return visible + "\n"
                + "x: " + x + "\n"
                + "y: " + y  + "\n"
                + "width: " + w + "\n"
                + "height: " + h + "\n";
    }
     
/* ----------------Movement Methods-----------------------*/   
    public void changeX(int chg) {
        x += chg;
    }
     
    public void changeX(float chg) {
        x += Math.round(chg);
    }
     
    public void changeY(int chg) {
        y += chg;
    }
     
    public void changeY(float chg) {
        y += Math.round(chg);
    }
     
    public void setX(int num) {
        x = num;
    }
     
    public void setX(float num) {
        x = Math.round(num);
    }
     
    public void setY(int num) {
        y = num;
    }
     
    public void setY(float num) {
        y = Math.round(num);
    }
}