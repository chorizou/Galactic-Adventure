
/**
* The Movable class represents a collection of SpaceObjects 
* that move freely in the space world, such as asteroids and the
* player's ship.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
import java.awt.Shape;
 
public abstract class Movable extends SpaceObject{
    private float dx; 
    private float dy;
    private int mass;
     
    public Movable(int x, int y, int w, int h, int iDX, int iDY, int mass) {
        super(x, y, w, h);
        dx = iDX;
        dy = iDY;
    }
     
    public abstract Shape getShape();
     
    /**
     * Instructs how to move ship.
     * Stops ship at the edge of map
     */
    public void move(int w, int h) {
            changeX(dx);
            changeY(dy);
            if(getX() + this.getWidth() < 0 || getX() > w
                    || getY() + this.getHeight() < 0 || getY() > h)
                setVisible(false);      
    }
     
    public static float calcSpeed(float dx, float dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
     
     
/* ------------------_Basic Accessors/Mutators---------------------*/
     
    public float getDX() {
        return dx;
    }
         
    public float getDY() {
        return dy;
    }
     
    public void changeDX(float chg) {
        dx += chg;
    }
     
    public void changeDY(float chg) {
        dy += chg;
    }
     
    public void setDX(float num) {
        dx = num;
    }
     
    public void setDY(float num) {
        dy = num;
    }
     
    public int getMass() {
        return mass;
    }
     
    protected void setMass(int m) {
        mass = m;
    }
     
    @Override
    public String toString(){
        return super.toString()
                + "dx: " + dx + "\n"
                + "dy: " + dy + "\n"
                + "mass: " + mass + "\n";
    }
     
}