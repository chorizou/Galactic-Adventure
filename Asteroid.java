
/**
* The Asteroid class represents a collection of randomly
* generated asteroids and their attributes, such as 
* diameter, rotated speed, and rotated angle
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;
 
public class Asteroid extends Movable {
    //private Shape asteroidShape;
    public static final int MAX_DIAMETER = 75;
    public static final int MIN_DIAMETER = 5;
    public static final int MAX_SPEED = 5;
    public static final float DENSITY = .2f;
     
    private double rotationSpeed;
    private double rotatedAngle;
    private static Random rand = new Random();
    private int type;
     
    /** Constructs a new Asteroid object with the given parameters
     *  @param x the x coordinate
     *  @param y the y coordinate
     *  @param w the width of the Asteroid
     *  @param h the height of the Asteroid
     *  @param iDX the initial change in x
     *  @param iDY the initial change in y
     *  @param da the rotation speed
     */
    public Asteroid(int x, int y, int w, int h, int iDX, int iDY, double da) {
        super(x, y, w, h, iDX, iDY, 0);
        initAsteroids(da);
    }
    
    /** Constructs a new Asteroid object with the given parameters
     *  @param x the x coordinate
     *  @param y the y coordinate
     *  @param w the width of the Asteroid
     *  @param h the height of the Asteroid
     *  @param iDX the initial change in x
     *  @param iDY the initial change in y
     */
    public Asteroid(int x, int y, int w, int h, int iDX, int iDY) {
    	super(x, y, w, h, iDX, iDY, 0);
    	initAsteroids(calcSpeed(iDX, iDY));
    }
    
    /** Loads the image of asteroids in the SpaceWorld
     *  @param da the rotation speed
     */
    private void initAsteroids(double da) {
        String randName = String.valueOf((int) (Math.random() * 3) + 1);
        type = Integer.parseInt(randName);
        loadImage("res/Assets/Asteroid " + randName + ".png");
         
        setMass(calcMass(getWidth()));
        //asteroidShape = new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
        rotationSpeed = da;
        rotatedAngle = 0;
    }
    
    /** Calculates and returns the mass of the asteroid
     *  @param dia the given diameter
     *  @return returns the calculated mass of the asteroid
     */ 
    private static int calcMass(int dia) {
        float radius = (float)dia / 2;
        int mass = (int) (radius * radius * DENSITY);
        return Math.max(1, mass);
    }
     
    /** Returns a randomly generated asteroid
     *  @param winW the window width
     *  @param winH the window height
     *  @return the randomly generated asteroid
     */
    public static Asteroid generateRandomAsteroid(int winW, int winH) {
        int randD = rand.nextInt(MAX_DIAMETER - MIN_DIAMETER + 1) + MIN_DIAMETER;
        int randX = rand.nextInt(winW - randD + 1); 
        int randY = rand.nextInt(winH - randD + 1);
        int randDX = rand.nextInt(2 * Asteroid.MAX_SPEED + 1) - MAX_SPEED;
        int randDY = rand.nextInt(2 * Asteroid.MAX_SPEED + 1) - MAX_SPEED;
        
        return new Asteroid(randX, randY, randD, randD, randDX, randDY);
    }
     
    /** Returns an asteroid with a random edge
     *  @param winW the window width
     *  @param winH the window height
     *  @return the random edge asteroid
     */
    public static Asteroid generateRandomEdgeAsteroid(int winW, int winH) {
        int randD = rand.nextInt(MAX_DIAMETER - MIN_DIAMETER + 1) + MIN_DIAMETER;
        int randEdge = rand.nextInt(4);
        int randX = rand.nextInt(winW - randD + 1);
        int randY = rand.nextInt(winH - randD + 1);
        int randDX = rand.nextInt(MAX_SPEED + 1) + 1;
        int randDY = rand.nextInt(MAX_SPEED + 1) + 1;
         
        switch(randEdge) { //left, right, top, bottom
        case 0: randX = 0 - randD; 
        randDY = (int)(Math.random() * (2 * MAX_SPEED + 1)) - MAX_SPEED; break;
        case 1: randX = winW - randD; randDX *= -1; 
        randDY = (int)(Math.random() * (2 * MAX_SPEED + 1)) - MAX_SPEED; break;
        case 2: randY = 0 - randD; 
        randDX = (int)(Math.random() * (2 * MAX_SPEED + 1)) - MAX_SPEED; break;
        case 3: randY = winH - randD; randDY *= -1;
        randDX = (int)(Math.random() * (2 * MAX_SPEED + 1)) - MAX_SPEED; break;
        }
        double randDA = calcSpeed(randDX, randDY);
        if(rand.nextBoolean())
            randDA *= -1;
         
        return new Asteroid(randX, randY, randD, randD, randDX, randDY, randDA);
    }
    
    /** Returns the type of asteroid in string format
     *  @return the type of asteroid in string format
     */
    public String toString() {
    	return type + "\n" + super.toString();
    }
     
    @Override
    /** Returns the shape of the asteroid
     *  @return the shape of the asteroid
     */
    public Shape getShape() {
        return new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
    }
    
    /** Returns the transformed shape of the asteroid
     *  @return the transformed shape of the asteroid
     */
    public Shape getTransformedShape(){
        return new Ellipse2D.Float(getWidth() / -2 ,getHeight() / -2,getWidth(), getHeight());
    }
     
    /** Returns the rotated angle of the asteroid
     *  @return the rotated angle of the asteroid
     */
    public double getRotatedAngle() {
        return rotatedAngle;
    }
    
    /** Increases the rotated angle by the rotation speed
     */
    public void incrementAngle() {
        rotatedAngle += rotationSpeed;
    }
 
}