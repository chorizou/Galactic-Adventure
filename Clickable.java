
/**
* The Clickable class represents a subset of SpaceObjects
* that can be interactive by clicking on the Clickable object
* to retrieve its information like cargo merchandise.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
 
/**
 * @author teddy.hsieh.1
 *
 */
public class Clickable extends SpaceObject{
    private SpaceWorld world;
    private final Type objType;
    private final String name;
    private final int destination;
    private List<Cargo> merchandise;
    
    
    private int foodPrice;
    private int fuelPrice;
    private int repairPrice;

    public static Dimension DEFAULT_GATE_DIM = new Dimension(250,200);
    public static Dimension DEFAULT_PORT_DIM = new Dimension(300, 120);
    public Clickable(SpaceWorld world, int x, int y, int w, int h, String name) {
        super(x, y, w, h);
        objType = Type.SPACEPORT;
        
        this.world = world;
        this.name = name;
        loadImage("res/Assets/" + name + ".png");
        
        merchandise = new ArrayList<Cargo>();
        destination = -1;
    }
    
    public Clickable(SpaceWorld world, int x, int y, String name) {
    	super(x, y, DEFAULT_PORT_DIM.width, DEFAULT_PORT_DIM.height);
    	objType = Type.SPACEPORT;
        
        this.world = world;
        this.name = name;
        loadImage("res/Assets/" + name + ".png");
        
        merchandise = new ArrayList<Cargo>();
        destination = -1;
    }
    
    public Clickable(SpaceWorld world, int x, int y, int w, int h, String name, int destID) {
    	super(x, y, w, h);
    	objType = Type.WARPGATE;
    	this.world = world;
    	this.name = name;
    	loadImage("res/Assets/" + name + ".png");
    	
    	merchandise = null;
    	destination = destID;
    }
    
    public Clickable(SpaceWorld world, int x, int y, String name, int destID) {
    	super(x, y, DEFAULT_GATE_DIM.width, DEFAULT_GATE_DIM.height);
    	objType = Type.WARPGATE;
    	this.world = world;
    	this.name = name;
    	loadImage("res/Assets/" + name + ".png");
    	
    	merchandise = null;
    	destination = destID;
    }
    
    
    public void setFoodPrice(int price) {
    	world.getMenu().foodButton.setDefaultMsg(MouseInput.FOOD_AMOUNT + " food for $" + price);
    	foodPrice = price;
    }
    
    public void setFuelPrice(int price) {
    	world.getMenu().fuelButton.setDefaultMsg(MouseInput.FUEL_AMOUNT + " fuel for $" + price);
    	fuelPrice = price;
    }
    
    public void setRepairPrice(int price) {
    	world.getMenu().repairHPButton.setDefaultMsg(MouseInput.REPAIR_AMOUNT + " HP for $" + price);
    	repairPrice = price;
    }
    
    public int getFoodPrice() {
    	return foodPrice;
    }
    
    public int getFuelPrice() {
    	return fuelPrice;
    }
    
    public int getRepairPrice() {
    	return repairPrice;
    }
     
    public List<Cargo> getMerchandise(){
        return merchandise;
    }
    
    public void setMerch(int ind, Cargo newMerch) {
    	if(merchandise.size() > ind) 
    		merchandise.set(ind, newMerch);
    	else
    		merchandise.add(newMerch);
    	
    	if(ind == 0)
    		world.getMenu().cargoOneButton.setMsg(newMerch.getName() + " $" 
    				+ newMerch.getValue());
    	else if(ind == 1)
    		world.getMenu().cargoTwoButton.setMsg(newMerch.getName() + " $" 
    				+ newMerch.getValue());
    	else if(ind == 2)
    		world.getMenu().cargoThreeButton.setMsg(newMerch.getName() + " $" 
    				+ newMerch.getValue());
    		
    }
    
    public Shape getShape() {
        return new Ellipse2D.Float(getX(), getY(), getWidth(), getHeight());
    }
     
    public String getName() {
        return name;
    }

    public int getDest() {
    	return destination;
    }
    
    public Type getType() {
    	return objType;
    }
    
    @Override
    public String toString() {
    	return name;
    }
     
    enum Type{
        WARPGATE,
        SPACEPORT
    }
     
}