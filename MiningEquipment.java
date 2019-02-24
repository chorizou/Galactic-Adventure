
/**
* The MiningEquipment class is a subset of Cargo objects that
* represent shovels and tools used to mine the destination planet.
* The class contains methods to add to the final score calculated
* when the player reaches the destination planet.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
public class MiningEquipment extends Cargo{
    private float valueMult;
 
    
    public MiningEquipment(String name, int price, float pointsValueMultiplier) {
        super(name, price);
        valueMult = pointsValueMultiplier;
    }
     
    public int getScore() {
        return Math.round(valueMult * super.getValue());
    }
 
 
}