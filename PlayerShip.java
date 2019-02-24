
/**
* The PlayerShip class represents the user's spaceship and its attributes,
* which include health (hp), fuel, food, money, and cargo inventory.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
 
public class PlayerShip extends Movable{
    private Engine engine;
    private Armor armor;
    private int version;
     
    private static final int STARTING_HP = 50000;
    private static final int STARTING_FUEL = 10000;
    private static final int STARTING_FOOD = 10000;
    private static final int STARTING_MONEY = 2000;
    
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 80;
    public static final int FOOD_CONSUMPTION = 1;
     
    private int maxHP;
    private int currentHP;//health
    private int maxFuel;
    private int fuelLevel;
    private int foodStorage;
    private int wallet;
    private List<Cargo> inventory;
     
    private float a;
    private float maxA;
    private float maxSpeed;
    private float currentSpeed;
     
    private double direction;
     
    private static final int POWER_TO_SPEED = 10;
    private static final int DEFAULT_MASS = 100;
 
 
    private boolean[] keyFlags; 
    //{left, right, up, down, brake, throttle up, throttle down, heal}
    /** Constructs the player's space ship
     *  @param x starting x coordinate 
     *  @param y starting y coordinate
     *  @param w starting width 
     *  @param h starting height
     *  @param iDX initial change in x
     *  @param iDY initial change in y
     */
    public PlayerShip(int x, int y, int w, int h, int iDX, int iDY) {
        super(x, y, w, h, iDX, iDY, DEFAULT_MASS);
        initShip();
    }
     
    /** Assigns starting values to all of the ship's instance variables,
     * such as engine, armor, health, food, money, inventory, and speed
     */
    private void initShip() {
        engine = new Engine(1); // default engines
        armor = new Armor(1);
         
        keyFlags = new boolean[8];
        maxHP = STARTING_HP + armor.getArmorValue();
        currentHP = maxHP;
        maxFuel = STARTING_FUEL;
        fuelLevel = maxFuel;
        foodStorage = STARTING_FOOD;
        wallet = STARTING_MONEY;
        inventory = new ArrayList<Cargo>();
        inventory.add(engine);
        inventory.add(armor);
         
        a = engine.getPower();
        maxA = a;
        maxSpeed = engine.getPower() * POWER_TO_SPEED;
        currentSpeed = 0;
         
        direction = 0;
        loadImage("res/Assets/Spaceship 1.png");
    }
     
     
    @Override
    public String toString() {
        return "Ship State: \n" + 
                "hp: " + currentHP + "\n"
                + "Current Speed: " + currentSpeed + "\n"
                + "x: " + getX() + "\n"
                + "y: " + getY() + "\n"
                + "Dx: " + getDX() + "\n"
                + "Dy: " + getDY() + "\n";
    }
     
     
/* ---------------------Movement--------------------------*/
     
     
     
    @Override
    /** Moves the ship according to the given width and height of the ship
     *  @param width the width of the SpaceWorld component
     *  @param height the height of the SpaceWorld component
     */
    public void move(int w, int h) {
        shipResponse();
       
        
        float posX = getX() + getDX();
        float posY = getY() + getDY();
        
        //bounds control
        if(posX < 0) 
            setX(0);
        else if(posX + getWidth() > w) 
            setX(w - getWidth());   
        else
            changeX(getDX());
         
        if(posY < 0)
            setY(0);
        else if(posY + getHeight() > h)
            setY(h - getHeight());  
        else
            changeY(getDY());
        
        
        if(posX - getDX() <= 0 || posX + getWidth() - getDX() >= w)
            setDX(0);   
        if(posY - getDY() <= 0 || posY + getHeight() - getDY() >= h)
            setDY(0);
         
        currentSpeed = calcSpeed(getDX(), getDY());
        if(currentSpeed > maxSpeed) {
            float r = maxSpeed / currentSpeed;
            setDX(r * getDX());
            setDY(r * getDY());
            currentSpeed = calcSpeed(getDX(), getDY());
        }
        /* 
        float tempDX = (Math.abs(getDX()) < .1)  ?  0:getDX();
        float tempDY = (Math.abs(getDY()) < .1)  ?  0:getDY();
        if(calcSpeed(tempDX, tempDY) != 0) {
            direction = Math.atan2(tempDY, tempDX);
        }
        */
    }
     
 
 
 
    /**
     * Changes the PlayerShip according to user key presses.
     * 2D acceleration controlled by WASD.
     * Allows for independent x and y axis acceleration.
     * In case of directionally contradicting key presses, favors the left and up keys
     * Spacebar overrides all other keys and brakes the ship at its engine power.
     * 
     * Up and down key controls engine throttle to allow more precise moving with fast engines.
     * 
     * In the absence of directional key presses, natural drag is applied, 
     * decelerating the ship at half the engine power.
     * 
     * All directional acceleration and braking uses fuel at the engine's fuel efficiency.
     */
    protected void shipResponse() {
        final float DECELERATION = -.05f;
        final int INCREMENT = 100;
         
        foodStorage -= FOOD_CONSUMPTION;
         
        //Burning Fuel
        if(keyFlags[0] || keyFlags[1] || keyFlags[2] || keyFlags[3] || keyFlags[4])
            fuelLevel -= engine.getFuelConsumption() * a / maxA;
         
        //Throttle Control
        if(keyFlags[5] && a < maxA)
            a += maxA / INCREMENT;
        else if(keyFlags[6] && a > 0)
            a -= maxA / INCREMENT;
         
        //Repair Ability
        final int HEAL_AMOUNT = maxHP / 1000;
        final int HEAL_FOOD_COST = 5;
        final int HEAL_FUEL_COST = 5;
        if(keyFlags[7] && currentHP < maxHP) {
            foodStorage -= HEAL_FOOD_COST;
            fuelLevel -= HEAL_FUEL_COST;
            if(maxHP - currentHP > HEAL_AMOUNT)
                currentHP += HEAL_AMOUNT;
            else
                currentHP = maxHP;
        }
         
        //Brake
        if(keyFlags[4]) {
            if(getDX() != 0)
                changeDX(-1 *    Math.signum(getDX()) * maxA);
            if(getDY() != 0)
                changeDY(-1 * Math.signum(getDY()) * maxA);
            return;
        }
         
        //Accelerating
        if(keyFlags[0])
            changeDX(-1 * a);
        else if (keyFlags[1])
            changeDX(a);
        else
            changeDX(DECELERATION * Math.signum(getDX()) * maxA);
         
        if(keyFlags[2])
            changeDY(-1 * a);
        else if (keyFlags[3])
            changeDY(a);
        else
            changeDY(DECELERATION * Math.signum(getDY()) * maxA);   
         
        
        //Change Directions
        direction = Math.atan2(getDY(), getDX());
        
    }
     
 
     
 
          
    /**
     * 
     * @param e user keypress
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
         
        switch(key) {
        case KeyEvent.VK_A: keyFlags[0] = true; break;
        case KeyEvent.VK_D: keyFlags[1] = true; break;      
        case KeyEvent.VK_W: keyFlags[2] = true; break;  
        case KeyEvent.VK_S: keyFlags[3] = true; break;  
        case KeyEvent.VK_SPACE: keyFlags[4] = true; break;
        case KeyEvent.VK_UP: keyFlags[5] = true; break;
        case KeyEvent.VK_DOWN: keyFlags[6] = true; break;
        case KeyEvent.VK_H: keyFlags[7] = true; break;
        }
    }
     
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch(key) {
        case KeyEvent.VK_A: keyFlags[0] = false; break;
        case KeyEvent.VK_D: keyFlags[1] = false; break;     
        case KeyEvent.VK_W: keyFlags[2] = false; break; 
        case KeyEvent.VK_S: keyFlags[3] = false; break; 
        case KeyEvent.VK_SPACE: keyFlags[4] = false; break;
        case KeyEvent.VK_UP: keyFlags[5] = false; break;
        case KeyEvent.VK_DOWN: keyFlags[6] = false; break;
        case KeyEvent.VK_H: keyFlags[7] = false; break;
        }
    }
    
     
     
/* ---------------------Collision--------------------------*/
    /**
     * Determines what happens to PlayerShip when it collides with other SpaceObjects
     * if collides with asteroids, ship health is reduced and damage is directly related to KE
     */
    public void collide(SpaceObject obj) {
        if(obj instanceof Asteroid) {
            Asteroid ast = (Asteroid) obj;
 
            double dmg = ast.getMass() * Math.pow(calcSpeed(getDX() - 
                    ast.getDX(), getDY() - ast.getDY()), 2);
            ast.setVisible(false);
            /*
            System.out.println("--------------------------------------------");
            System.out.println("Asteroid mass: " + ((Asteroid)obj).getMass());
            System.out.println("Speed Difference: " + calcSpeed(getDX() - 
                    ast.getDX(), getDY() - ast.getDY()));
            System.out.println("Damage: " + dmg);
            System.out.println("--------------------------------------------");
            */
            currentHP -= dmg;
        }
        else if(obj instanceof StarBucks) {
        	StarBucks bucks = (StarBucks) obj;
        	wallet += bucks.getCashValue();
        	bucks.setVisible(false);
        }
             
    }
/* ---------------------Health--------------------------*/
     
 
/* ------------------Basic Accessors/Mutators---------------------*/
    @Override
    /**
     * Returns Shape of PlayerShip. The Shape is identical to the Rectangular bounds
     * of SpaceObject's getBounds() method
     * @return Shape of PlayerShip
     */
    public Shape getShape() {
        return getBounds();
    }
     
    public Engine getEngine() {
        return engine;
    }
     
    public int getCurrentHP() {
        return currentHP;
    }
     
    public int getMaxHP() {
        return maxHP;
    }
         
    public int getCurrentFuel() {
        return fuelLevel;
    }
    
    public int getMaxFuel() {
    	return maxFuel;
    }
 
    public int getFood() {
        return foodStorage;
    }
     
    public int getWallet() {
        return wallet;
    }   
    
    public float getA() {
    	return a;
    }
    
    public float getMaxA() {
    	return maxA;
    }
     
    public double getDirection() {
         return direction;
    }
     
    public void changeCurrentHP(int chg) {
        currentHP += chg;
        if(currentHP > maxHP)
        	currentHP = maxHP;
    }
 
    public void changeMaxHP(int chg) {
        maxHP += chg;
    }
     
    public void changeFuel(int chg) {
        fuelLevel += chg;
    }
     
    public void changeFood(int chg) {
        foodStorage += chg;
    }
     
    public void changeWallet(int chg) {
        wallet += chg;
    }
     
    /**
     * The inventory is a list of Cargo objects(engine, armor, and mining equipment) the Player currently owns
     * Index 0 always contain the equipped engine, and index 1 always contain the equipped armor.
     * @return Inventory of PlayerShip
     */
    public List<Cargo> getInventory(){
        return inventory;
    }
    
    public void equip(Cargo c){
    	if(c instanceof Engine){
    		Engine e = (Engine)inventory.get(0);
    		inventory.set(0, c);
    		inventory.add(e);
    		engine = (Engine) c;
    		maxA = engine.getPower();
    	}
    	else if(c instanceof Armor){
    		Armor a = (Armor)inventory.get(1);
    		inventory.set(1, c);
    		inventory.add(a);
    		armor = (Armor) c;
    		maxHP = STARTING_HP + armor.getArmorValue();
    	}
    	else if(c instanceof MiningEquipment)
    		inventory.add(c);
    		
    }
     
    public float getMaxSpeed() {
        return maxSpeed;
    }
}