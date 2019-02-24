
import java.awt.Shape;

public class Aliens extends Movable{
	private static int DEFAULT_WIDTH = 80;
	private static int DEFAULT_HEIGHT = 60;
	
	
	public Aliens(int x, int y, int iDX, int iDY, int mass) {
		super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, iDX, iDY, mass);
		initAliens();
	}
	
	private void initAliens() {
		
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return null;
	}
}