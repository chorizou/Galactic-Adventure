
/**
* The Engine class represents a collection of engines of different
* power, type, price, mass, and fuel consumption.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
public class Engine extends Cargo{
    private static final float[] typePower = {.51f, 1f, 2f, 5f, 8f, 10f};
    private static final int[] typeFuelConsumption = {5, 10, 15, 20, 30, 50};
    private static final int[] typePrices = {500, 800, 2000, 5000, 7500, 15000};
    private int mk;
    
    public Engine(int mk) {
        super("Engines Mark " + String.valueOf(mk)+ " ", typePrices[mk - 1]);
        this.mk = mk;
         
    }
    
     
    public int getType() {
        return mk;
    }
     
    public float getPower() {
        return typePower[mk - 1];
    }
     
    public int getFuelConsumption() {
        return typeFuelConsumption[mk - 1];
    }
    
}