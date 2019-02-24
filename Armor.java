/**
* The Armor class represents a subset of Cargo objects that
* can increase the ship's maximum health with each
* increasingly expensive and durable type.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

public class Armor extends Cargo{
	// defines the types of armor and their attributes
    private static final int[] typeArmorValues = {0, 5000, 10000, 15000, 30000, 50000};
    private static final String[] typeNames = 
        {"Light Steel", "Reinforced Steel", "Titanium", "Tritanium", "Xentronium", "Adamantium"};
    private static final int[] typePrices = {1000, 2500, 5000, 800, 15000, 30000};
 //   private static final int[] typeMasses = {25, 25, 30, 40, 80};
    
    // armor mark
    private int mk;
    
    /** Constructs a new Armor object with the given parameter
     *  @param the mark of the armor
     */
    public Armor(int mk) {
        super(typeNames[mk - 1] + " Armor ", typePrices[mk - 1]);
        this.mk = mk;
    }
     
    /** Returns the armor value
     *  @return the armor value
     */
    public int getArmorValue() {
        return typeArmorValues[mk - 1];
    }
 
}