
/**
* The StarBucks class represents the StarBucks money
* floating around in space that the player can pick up.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Random;

public class StarBucks extends Movable {
	private int cashValue;
	private static int MAX_VALUE = 200;
	private static Random rand = new Random();
	public static int WIDTH = 50;
	public static int HEIGHT = 20;
	public StarBucks(int x, int y, int w, int h, int prize) {
		super(x, y, w, h, 0, 0, 0);
		cashValue = prize;
		loadImage("res/Assets/Starbucks.png");
	}
	
	
	public static StarBucks generateRandomStarBucks(int winW, int winH) {
		int randX = rand.nextInt(winW - WIDTH / 2 + 1); 
        int randY = rand.nextInt(winH - HEIGHT / 2 + 1);
        int randV = rand.nextInt(MAX_VALUE + 1);
        return new StarBucks(randX, randY, WIDTH, HEIGHT, randV);
	}
	
	public int getCashValue() {
		return cashValue;
	}

	@Override
	public Shape getShape() {
		return new Rectangle(getX(), getY(), getWidth(), getWidth());
	}

}