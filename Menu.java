
/**
* The Menu class represents and renders the different buttons and selections
* the player can choose from once he/she enters the main menu or store menu.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Menu 
{
    private int worldW;
    private int worldH;
      
    public static final int GAMELABEL_FONT = 100;      
	public static final int FONT_SIZE = 60;
	public static final int STORE_FONT_SIZE = 25;
	public static final Font fon = new Font("Agency FB", Font.BOLD, GAMELABEL_FONT);
	public static final Font fon2 = new Font("Agency FB", Font.BOLD, FONT_SIZE);
	public static final Font storeFont = new Font("Agency FB", Font.BOLD, STORE_FONT_SIZE);
     
    public int B_W;//all button width
    public int B_H;//all button height
      
    // main menu
    public int gameLabel_x;
    public int gameLabel_y;
    public int gameLabel_w;
    public int gameLabel_h;
     
    // main menu buttons
    public int startMenuBut_x; 
    
    public int startButton_y; 
    public int tutButton_y; 
    public int exitButton_y;  
    public int tutToStartButton_y;   
    public int endToMenuButton_y;
      
    // store menu
    public int exitStoreButton_x;
    public int exitStoreButton_y;
     
    public int storeLabel_x;
    public int storeLabel_y;
    public int storeLabel_width;
    public int storeLabel_height;
     
    public int fuelButton_x;   
    public int fuelButton_y;
    public int foodButton_x;
    public int foodButton_y;
    public int repairHPButton_x;
    public int repairHPButton_y;
    public int cargo1Button_x;
    public int cargo1Button_y;
    public int cargo2Button_x;
    public int cargo2Button_y;
    public int cargo3Button_x;
    public int cargo3Button_y;
      
    //GameOver
    public int gameOver_x = worldW / 2; 
    public int gameOver_y = worldH / 10;
      
    // the main menu buttons
    public Rectangle gameLabel;
    public ArrayList<Button> menuBut;
    public Button startButton;
    public Button tutorialButton;
    public Button exitButton;
    public Button tutToStartButton;
    public Button endToMenuButton;
     
    // the store menu buttons
    public Button exitStoreButton;
    public Button fuelButton;
    public Button foodButton;
    public Button cargoOneButton;
    public Button cargoTwoButton;
    public Button cargoThreeButton;
    public Button repairHPButton;
     
    public Rectangle gameOverLabel;
    private Rectangle storeLabel;
     
    public static final String GAME_TITLE = "GALACTIC ADVENTURE";
	public static final String START_MSG = "Start";
	public static final String TUT_MSG = "Tutorial";
	public static final String EXIT_MSG = "Exit";
	public static final String TUT_TO_START_MSG = "Start Game";
	public static final String END_TO_MENU_MSG = "Restart Game";
	public static final String EXIT_STORE_MSG = "Exit Store";
	public static final String STORELABEL_MSG = "Welcome to the Space Station! What do you want to buy?";
    
	private Image tutImg;
    /** Constructs a menu with the given parameters
     *  @param w the world width
     *  @param h the world height
     */
    public Menu(int w, int h) {
        worldW = w;
        worldH = h;
        init();
    }
      
    /** Initiates the menu with given main menu and store menu buttons
     */
    private void init() {
        B_W = worldW / 5;      
        B_H = worldH / 10; 
          
        // main menu
        gameLabel_x = worldW / 4; 
        gameLabel_y = worldH * 1 / 10;
        gameLabel_w = worldW / 2; 
        gameLabel_h = worldH / 5;
        
        startMenuBut_x = worldW / 2 - B_W / 2; 
        startButton_y = worldH * 2 / 5 - B_H / 2;  
        tutButton_y = worldH * 3 / 5 - B_H / 2;  
        exitButton_y = worldH * 4 / 5 - B_H / 2;  
        tutToStartButton_y = worldH * 7 / 8 - B_H / 2;  
        endToMenuButton_y = worldH * 7 / 8 - B_H / 2;
        
        repairHPButton_x = worldW / 10 - B_W / 2;   
        repairHPButton_y = worldH * 5 / 6 - B_H / 2;
        exitStoreButton_x = worldW * 9 / 10 - B_W / 2;   
        exitStoreButton_y = worldH * 5 / 6 - B_H / 2;
          
        // store 
        storeLabel_width = worldW * 3 / 4;
        storeLabel_height = worldH / 20;
        storeLabel_x = worldW / 2 - storeLabel_width / 2; 
        storeLabel_y = worldH * 1 / 20 - storeLabel_height / 2;
          
        fuelButton_x = worldW / 2 - B_W / 2;   
        fuelButton_y = worldH * 1 / 6 - B_H / 2;
        foodButton_x = worldW / 2 - B_W / 2;   
        foodButton_y = worldH * 2 / 6 - B_H / 2;
        cargo1Button_x = worldW / 2 - B_W / 2;   
        cargo1Button_y = worldH * 3 / 6 - B_H / 2;
        cargo2Button_x = worldW / 2 - B_W / 2;   
        cargo2Button_y = worldH * 4 / 6 - B_H / 2;
        cargo3Button_x = worldW / 2 - B_W / 2;   
        cargo3Button_y = worldH * 5 / 6 - B_H / 2;
          
        gameLabel = new Rectangle(gameLabel_x, gameLabel_y, gameLabel_w, gameLabel_h);
        startButton = new Button(startMenuBut_x, startButton_y, B_W, B_H, Button.Type.START);
        tutorialButton = new Button(startMenuBut_x, tutButton_y, B_W, B_H, Button.Type.TUTORIAL);
        exitButton = new Button(startMenuBut_x, exitButton_y, B_W, B_H,Button.Type.EXIT);
        tutToStartButton = new Button(startMenuBut_x, tutToStartButton_y, B_W, B_H, Button.Type.TUT_TO_START);
        endToMenuButton = new Button(startMenuBut_x, endToMenuButton_y, B_W, B_H, Button.Type.END_TO_MENU);
        exitStoreButton = new Button(exitStoreButton_x, exitStoreButton_y, B_W, B_H, Button.Type.EXITSTORE);
         
        storeLabel = new Rectangle(storeLabel_x, storeLabel_y, storeLabel_width, storeLabel_height);
        fuelButton = new Button(fuelButton_x, fuelButton_y, B_W, B_H, Button.Type.FUEL);
        foodButton = new Button(foodButton_x, foodButton_y, B_W, B_H, Button.Type.FOOD);
        repairHPButton = new Button(repairHPButton_x, repairHPButton_y, B_W, B_H, Button.Type.REPAIR);
        cargoOneButton = new Button(cargo1Button_x, cargo1Button_y, B_W, B_H, Button.Type.ONE);
        cargoTwoButton = new Button(cargo2Button_x, cargo2Button_y, B_W, B_H, Button.Type.TWO);
        cargoThreeButton = new Button(cargo3Button_x, cargo3Button_y, B_W, B_H, Button.Type.THREE);
    
        loadTutorialImage();
    }
    
    private void loadTutorialImage() {
		ImageIcon icon1 = new ImageIcon("res/Assets/Tutorial.PNG");
		tutImg = icon1.getImage().getScaledInstance(worldW, worldH, Image.SCALE_DEFAULT);
    
	}
	private void drawString(Graphics2D g, Rectangle rect, String msg, FontMetrics metric) {
		int stringX = rect.x + (rect.width - metric.stringWidth(msg)) / 2;
		int stringY = rect.y + ((rect.height - metric.getHeight()) / 2) + metric.getAscent();
		
		g.drawString(msg, stringX, stringY);
	}
	
	private void drawButtonMsg(Graphics2D g, Button but, FontMetrics metric) {
		Rectangle rect = but.getBounds();
		String msg = but.getMsg();
		drawString(g, rect, msg, metric);
	}
	
	public void renderMenuButtons(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(fon);
		g2.setColor(Color.white);
		drawString(g2, gameLabel, GAME_TITLE, g2.getFontMetrics());
		//g2.draw(gameLabel);

		// Drawing buttons
		g.setColor(Color.CYAN);
		g2.fill(startButton.getBounds());
		g2.fill(tutorialButton.getBounds());
		g2.fill(exitButton.getBounds());
		
		g2.setFont(fon2);
		g2.setColor(Color.BLACK);
		FontMetrics metrics = g.getFontMetrics(fon2);
		drawString(g2, startButton.getBounds(), START_MSG, metrics);
		drawString(g2, tutorialButton.getBounds(), TUT_MSG, metrics);
		drawString(g2, exitButton.getBounds(), EXIT_MSG, metrics);
	    
	}
	
	public void renderTutorial(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
        g.drawImage(tutImg,0 ,0, null);
    	
		g.setColor(Color.CYAN);
		g2.fill(tutToStartButton.getBounds());
		
		g.setFont(fon2);
		g.setColor(Color.BLACK);
	    drawString(g2, tutToStartButton.getBounds(), TUT_TO_START_MSG, g2.getFontMetrics());
		
//		g.setColor(Color.CYAN);
//		g.fillRect(TUTSTARTBUTTON_X, TUTSTARTBUTTON_Y, BUTTON_SIZE_X, BUTTON_SIZE_Y);
//		g.setFont(new Font("Agency FB", Font.BOLD, FONT_SIZE));
//		g.setColor(Color.BLACK);
//		g.drawString("Start Game", TUTSTARTBUTTON_X + 20, TUTSTARTBUTTON_Y + 35);
	}
	
	
	
	public void renderEndToMenu(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.CYAN);
		g2.fill(endToMenuButton.getBounds());

		g2.setFont(fon2);
		g2.setColor(Color.BLACK);
	    drawString(g2, endToMenuButton.getBounds(), END_TO_MENU_MSG, g2.getFontMetrics());
	    

	}
	
	public void renderStoreMenuButtons(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(storeLabel);
			
		g2.setFont(storeFont);
		exitStoreButton.setDefaultMsg(EXIT_STORE_MSG);
		
//		labelWidth = g.getFontMetrics().stringWidth("Start");
//		g.drawString("Welcome to the Space Station! What do you want to buy or sell?", STORELABEL_X, STORELABEL_Y);
//		
		
		
		g2.setColor(Color.CYAN);
		g2.fill(fuelButton.getBounds());
		g2.fill(foodButton.getBounds());
		g2.fill(cargoOneButton.getBounds());
		g2.fill(cargoTwoButton.getBounds());
		g2.fill(cargoThreeButton.getBounds());
		g2.fill(repairHPButton.getBounds());
		g2.fill(exitStoreButton.getBounds());
		
		g2.setColor(Color.BLACK);
		FontMetrics metric = g2.getFontMetrics();
		drawString(g2, storeLabel, STORELABEL_MSG, metric);
		drawButtonMsg(g2, fuelButton, metric);
		drawButtonMsg(g2, foodButton, metric);
		drawButtonMsg(g2, cargoOneButton, metric);
		drawButtonMsg(g2, cargoTwoButton, metric);
		drawButtonMsg(g2, cargoThreeButton, metric);
		drawButtonMsg(g2, repairHPButton, metric);
		drawButtonMsg(g2, exitStoreButton, metric);
	}

	
}