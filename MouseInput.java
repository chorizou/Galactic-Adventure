
/**
* The MouseInput class responds to a mouse action event by the user.
* Once a mouse is pressed, the MouseListener causes the states in 
* SpaceWorld to change to cause an action once a button is pressed.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

//import SpaceWorld.STATE;

public class MouseInput implements MouseListener
{
	private SpaceWorld world;
	private Menu m;
	private PlayerShip ship;
    
    public static final int FOOD_AMOUNT = 200;
    public static final int FUEL_AMOUNT = 1000;
    public static final int REPAIR_AMOUNT = 1000;
   	
	public MouseInput(SpaceWorld w, Menu m, PlayerShip s) {
		world = w;
		this.m = m;
		ship = s;
	}
	@Override
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		// menu
		if(world.State == SpaceWorld.STATE.MENU) {
			if(m.startButton.contains(mx, my)) 
				world.State = SpaceWorld.STATE.GAME;		// start button			
			else if(m.tutorialButton.contains(mx, my)) 
				world.State = SpaceWorld.STATE.TUTORIAL; 		// tutorial button			
			else if(m.exitButton.contains(mx, my)) 
				System.exit(1);		// exit button
		}
		
		else if(world.State == SpaceWorld.STATE.TUTORIAL && m.tutToStartButton.contains(mx, my))
				world.State = SpaceWorld.STATE.GAME;		// tutorial to start button
		
		else if(world.State == SpaceWorld.STATE.GAMEOVER && m.endToMenuButton.contains(mx, my)) {
			world.State = SpaceWorld.STATE.MENU;		// return to start menu
			world.restartGame();
		}
				
		
		
		
		else if(world.State == SpaceWorld.STATE.GAME) {
			for(int i = 0; i < world.getClickables().size(); i++) {
				Clickable c = world.getClickables().get(i);
				if(c.contains(mx, my) && c.intersects(ship)) {
					if(c.getType() == Clickable.Type.WARPGATE) {
						world.changeMap(c.getDest());
					}
						
					else
						world.State = SpaceWorld.STATE.STORE;
					break;
				}
			}
		}
		//store
		else if (world.State == SpaceWorld.STATE.STORE) {
			Clickable port = world.getClickables().get(0);
			if(m.fuelButton.contains(mx, my)) 
				storePress(m.fuelButton, port);		
			else if(m.foodButton.contains(mx, my))
				storePress(m.foodButton, port);
			else if(m.repairHPButton.contains(mx, my))
				storePress(m.repairHPButton, port);
			else if(m.cargoThreeButton.contains(mx, my))
				storePress(m.cargoThreeButton, port);
			else if(m.cargoTwoButton.contains(mx, my))
				storePress(m.cargoTwoButton, port);
			else if(m.cargoOneButton.contains(mx, my))
				storePress(m.cargoOneButton, port);
			else if(m.exitStoreButton.contains(mx, my))
				storePress(m.exitStoreButton, port);
		}
	}

	//TELL USER CAN'T AFFORD IT PLEASE TELL THEM
	private void storePress(Button but, Clickable store) {
		if(but.type == Button.Type.EXITSTORE) {
			world.State = SpaceWorld.STATE.GAME;
			return;
		}
		int cost = calcCost(but, store);
			
		if(ship.getWallet() < cost) {
			JOptionPane.showMessageDialog(world, "You're too poor! :(");
			return;
		}
			
		ship.changeWallet(-1 * cost);
		String msg ="";
		int ind = -1;
		switch(but.type){
		case FOOD: 
			ship.changeFood(FOOD_AMOUNT);
			msg = "Food Bought"; break;
		
		case FUEL: 
			ship.changeFuel(FUEL_AMOUNT);
			msg = "Fuel Bought"; break;
		
		case REPAIR: 
			ship.changeCurrentHP(REPAIR_AMOUNT);
			msg = "Repaired"; break;
		
		case THREE: ind++; 
		case TWO: ind++;
		case ONE: ind++;
			if(!buyEquipment(ind, store)) {
				ship.changeWallet(cost);
				
			}
			else {
				but.reset();
				msg = "Item Bought!";
			} break;
			
		
		default: msg = "Button Types are wrong."; break;
		}
		JOptionPane.showMessageDialog(world, msg);
	}

	private int calcCost(Button but, Clickable store) {
		int ind = -1;
		
		switch(but.type) {
		case FOOD: return store.getFoodPrice();
		case FUEL: return store.getFoodPrice();
		case REPAIR: return store.getRepairPrice();
		
		case THREE: ind++;
		case TWO: ind++;
		case ONE: ind++;
		if(store.getMerchandise().get(ind) != null)
			return store.getMerchandise().get(ind).getValue();
		else 
			return -1;
		default: JOptionPane.showMessageDialog(world, "Costs are wrong.");
			return -1;
		}
	}
	/**
	 * Buys Cargo from store and equip it on ship.
	 * @param ind index of cargo in store merchandise
	 * @param store the store being bought from
	 * @return false if merchandise already bought (store stocks empty)
	 * 		true if bought successfully
	 */
	private boolean buyEquipment(int ind, Clickable store) {
		if(ind >= store.getMerchandise().size() || store.getMerchandise().get(ind) == null)
			return false;
		Cargo equipment = store.getMerchandise().get(ind);
		
		ship.equip(equipment);
		store.getMerchandise().set(ind, null);
		return true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}