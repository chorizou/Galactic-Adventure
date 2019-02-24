
/**
* The SpaceWorld class represents the panel where the game takes place.
* It includes different SpaceObjects, asteroids, space stations, and
* warpgates. The player can choose to play the game by clicking
* on the buttons shown in the SpaceWorld class.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
 
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
//import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.InputMismatchException;
//import java.awt.image.BufferStrategy;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
//import javax.imageio.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import java.io.File;
//import java.io.IOException;
 
public class SpaceWorld extends JPanel{
	public STATE State;
	private int mapID;
	public static int NUM_MAPS = 5;
    
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int HEIGHT = screenSize.height;
    private static int WIDTH = screenSize.width;
    
	private Image[] backgroundImages;
	private PlayerShip ship;
    private Asteroid endPlanet;
    
    private Timer frameTimer;   
    private Timer spawnTimer;
    private long timeTracker;
    private static final int INIT_DELAY = 100;    
    private static final int FRAME_PERIOD = 25; // Timer delay
    private static final int SPAWN_PERIOD = 2000;
    
    

    private int maxObstacles;
    private int numAst;
    private int asteroidSpawnSize;
    private int bucksSpawnSize;
    private List<Movable> obstacles;
    private List<Clickable> places;
    
    private Menu menu;   
    
    private static final int HP_AND_FUEL_LABEL_X = 0;
    private static final int HP_LABEL_Y = HEIGHT / 30;
    private static final int FUEL_LABEL_Y = HEIGHT / 10;
    private static final int THROTTLE_LABEL_Y = HEIGHT / 6;
    private static final int HP_AND_FUEL_LABEL_W = WIDTH / 10;
    private static final int HP_AND_FUEL_LABEL_H = HEIGHT / 30;
    private static final int HUDBARS_X = WIDTH / 10;
    private static final int HPBAR_Y = HEIGHT / 30;
    private static final int FUELBAR_Y = HEIGHT / 10;
    private static final int THROTTLEBAR_Y = HEIGHT / 6;
    private static final int HUDBARS_W = WIDTH / 5;
    private static final int HUDBARS_H = HEIGHT / 30;
    // HUD Extra Stats
    private static final int STATSLABEL_Y = HEIGHT / 30;
    private static final int STATSLABEL_W = WIDTH / 5;
    private static final int STATSLABEL_H = HEIGHT / 30;
    private static final int CARGOLABEL_X = WIDTH * 2 / 5;
    private static final int CARGOLABEL_Y = HEIGHT / 10;
    private static final int CARGOLABEL_W = WIDTH * 3 / 5;
    private static final int CARGOLABEL_H = HEIGHT / 30;
    private static final int FOOD_LABEL_X = WIDTH * 2 / 5;
    private static final int MONEY_LABEL_X = WIDTH * 3 / 5;     
    private static final int DAY_LABEL_X = WIDTH * 4 / 5;
   
    private static final int GAMEOVER_WIDTH = WIDTH / 5; 
    private static final int GAMEOVER_HEIGHT = HEIGHT / 10;
   	private static final int GAMEOVER_LOCATION_X = WIDTH / 2 - GAMEOVER_WIDTH / 2; 
    private static final int GAMEOVER_LOCATION_Y = 4 * HEIGHT / 10 - GAMEOVER_HEIGHT / 2;
    
   	private static final int DAY_LENGTH = 200;
   	private static final int HUD_FONT = 30;
   	private static final int STATS_FONT = 40;
   	private static final int GAMEOVER_FONT = 100;
   	
   	//Score Ratios
   	private static final int FOOD_RATIO = 3;
    private static final int FUEL_RATIO = 5;
    private static final int HP_RATIO = 2;
    private static final int DAY_RATIO = 10;
   	
   	
   	private static ScriptEngineManager mgr = new ScriptEngineManager();
   	private static ScriptEngine converter = mgr.getEngineByName("JavaScript");

    public SpaceWorld(){
        init();
    }
    
    public static enum STATE
    {
    	MENU,
    	TUTORIAL,
    	GAME,
    	MAPCHANGE,
    	STORE,
    	GAMEOVER,
    }
     
    /**
     * Loads background and set frame size
     */
    private void init() {
        State = STATE.MENU;
        mapID = 1;
        addKeyListener(new KAdapter());
        
        setBackground(Color.BLACK);
        setFocusable(true); // focusable means something can be the field focused for receiving user input (keystrokes, clicks)
        setPreferredSize(screenSize);
        setDoubleBuffered(true);

        ship = new PlayerShip(0, screenSize.height / 2, PlayerShip.DEFAULT_WIDTH, PlayerShip.DEFAULT_HEIGHT, 0, 0);
        
        loadBackgroundImage();
        loadObstacles();
        loadBucks();
//        loadBackgroundImage();
//        loadPlayerShip();
//        loadObstacles();

        menu = new Menu(screenSize.width, screenSize.height);
        loadPlaces();
        
        addMouseListener(new MouseInput(this, menu, ship));
        frameTimer = new Timer();
        spawnTimer = new Timer();
        frameTimer.scheduleAtFixedRate(new StepTask(), INIT_DELAY, FRAME_PERIOD);
        spawnTimer.scheduleAtFixedRate(new SpawnTask(), INIT_DELAY, SPAWN_PERIOD);
        timeTracker = 0;
        
    }
    
    /*-------------------------Data Loading--------------------------*/
    
    private void reloadMap() {
//    	loadBackgroundImage();
        loadObstacles();
        loadPlaces();
    } 
    
    private void loadBackgroundImage() {
    	backgroundImages = new Image[NUM_MAPS];
    	for(int i = 0; i < NUM_MAPS; i++)
    	{
    		ImageIcon icon = new ImageIcon("res/Assets/Background " + (i+1) + ".png");
    		backgroundImages[i] = icon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_DEFAULT);
    	}
    }
    /** Uploads the space stations and warpgate clickable objects to the SpaceWorld
     */
    private void loadPlaces() {
    	places = new ArrayList<Clickable>();
    
    	try {
    	//spacePort first, then gates.
    	
    	Scanner s = new Scanner(new File("res/Map_Data/StoreData_" + mapID + ".txt"));
		StringTokenizer st = new StringTokenizer(s.nextLine());
		int x = readFile(st.nextToken(), Clickable.DEFAULT_PORT_DIM.width, Clickable.DEFAULT_PORT_DIM.height);
		int y = readFile(st.nextToken(), Clickable.DEFAULT_PORT_DIM.width, Clickable.DEFAULT_PORT_DIM.height);
		//JOptionPane.showMessageDialog(this,"worldW: " + screenSize.width + " worldH: " + screenSize.height + "\n" +
		//		"x: " + x + " y: " + y);
		Clickable newPort = new Clickable(this, x, y, "Space station");
		places.add(newPort);
		st = new StringTokenizer(s.nextLine());
		newPort.setFoodPrice(Integer.parseInt(st.nextToken()));
    	newPort.setFuelPrice(Integer.parseInt(st.nextToken()));
    	newPort.setRepairPrice(Integer.parseInt(st.nextToken()));
    	newPort.setMerch(0, new Engine(mapID + 1));
    	newPort.setMerch(1, new Armor(mapID + 1));
    	
    	st = new StringTokenizer(s.nextLine());
    	String mineName = st.nextToken();
    	int mineCost = Integer.parseInt(st.nextToken());
    	float mineMult = Float.parseFloat(st.nextToken());
    	MiningEquipment newMerch = new MiningEquipment(mineName, mineCost, mineMult);
    	newPort.setMerch(2, newMerch);
    	
    	
    	s = new Scanner(new File("res/Map_Data/GateData_" + mapID + ".txt"));
    	int numGates= 2;
    	int i = 0;
    	if(mapID == 1) //if first map, no prev gate
    		i++;
    	else if(mapID == NUM_MAPS) //if last map, no next gate
    		numGates = 1;

    	while(i < numGates) {
    		st = new StringTokenizer(s.nextLine());
    		int gateX = readFile(st.nextToken(), Clickable.DEFAULT_GATE_DIM.width, Clickable.DEFAULT_GATE_DIM.height);
    		int gateY = readFile(st.nextToken(), Clickable.DEFAULT_GATE_DIM.width, Clickable.DEFAULT_GATE_DIM.height);
    		places.add(new Clickable(this, gateX, gateY, "Warpgate", mapID - 1 + 2 * i));
    		i++;
    	}
    	
    	if(mapID != 1) {
    		ship.setX(places.get(1).getX());
    		ship.setY(places.get(1).getY());
    	}
    		
    	
    	if(places.get(0).getType() != Clickable.Type.SPACEPORT)
    		JOptionPane.showMessageDialog(this, "First object in places must be a Spaceport!!");
    	
    	s.close();
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(this, "Oops! Incorrect Level Data Files.");
			e.printStackTrace();
		}catch(InputMismatchException e) {
			JOptionPane.showMessageDialog(this, "Oops! Level Data incorrect format. Mismatch");
			e.printStackTrace();
		}catch(NoSuchElementException e) {
			JOptionPane.showMessageDialog(this, "Oops! Level Data incorrect format. NoSuchElement");
			e.printStackTrace();
		} catch (ScriptException e) {
			JOptionPane.showMessageDialog(this, "Bad JavaScript :(");
			e.printStackTrace();
		}
    }
    
    private void loadObstacles(){
        try {
            /*BufferedReader br = new BufferedReader(new FileReader("res/Map_" + mapID + "_Data/ObstclesData"));
            StringTokenizer st = new StringTokenizer(br.readLine());
            */
            
        if(mapID == NUM_MAPS) {
        		Scanner s = new Scanner(new File("res/Map_Data/PlanetData_5.txt"));
        		int x = readFile(s.next(), Clickable.DEFAULT_GATE_DIM.width, Clickable.DEFAULT_GATE_DIM.height);
        		int y = readFile(s.next(), Clickable.DEFAULT_GATE_DIM.width, Clickable.DEFAULT_GATE_DIM.height);
        		endPlanet = new Asteroid(x, y, Clickable.DEFAULT_GATE_DIM.width, Clickable.DEFAULT_GATE_DIM.height, 0, 0);
        		endPlanet.loadImage("res/Assets/End planet.png");
        	}
        Scanner s = new Scanner(new File("res/Map_Data/AsteroidsData_" + mapID + ".txt"));
        StringTokenizer st = new StringTokenizer(s.nextLine());
        obstacles = new ArrayList<Movable>();
             
        maxObstacles = Integer.parseInt(st.nextToken());
        asteroidSpawnSize = Integer.parseInt(st.nextToken());
        numAst = 0;
        int initialRandSpawns = Integer.parseInt(st.nextToken());
        int initialFixedSpawns = Integer.parseInt(st.nextToken());

         
        for(int i = 0; i < initialRandSpawns; i++) {
        	numAst++;
        	obstacles.add(Asteroid.generateRandomAsteroid(screenSize.width, screenSize.height));
        }
            
       
        for(int i = 0; i < initialFixedSpawns; i++) {
        	st = new StringTokenizer(s.nextLine());
        	int dia = readFile(st.nextToken(),0,0);
        	//JOptionPane.showMessageDialog(this, dia);
        	int x = readFile(st.nextToken(),dia, dia);
        	int y = readFile(st.nextToken(),dia, dia);
        	int dx = Integer.parseInt(st.nextToken());
        	int dy = Integer.parseInt(st.nextToken());
        	//JOptionPane.showMessageDialog(this,"worldW: " + screenSize.width + " worldH: " + screenSize.height + "\n" +
    		//		"x: " + x + " y: " + y);
        	numAst++;
        	obstacles.add(new Asteroid(x, y, dia, dia, dx, dy));
        }
       
        s.close();
        } catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(this, "Oops! Incorrect Level Data Files.");
			e.printStackTrace();
		}catch(InputMismatchException e) {
			JOptionPane.showMessageDialog(this, "Oops! Level Data incorrect format. Mismatch");
			e.printStackTrace();
		}catch(NoSuchElementException e) {
			JOptionPane.showMessageDialog(this, "Oops! Level Data incorrect format. NoSuchElement");
			e.printStackTrace();
        } catch (ScriptException e) {
        	JOptionPane.showMessageDialog(this, "Bad JavaScript :(");
			e.printStackTrace();
		}
         
    }
    
    private void loadBucks() {
    	Scanner s;
		try {
			s = new Scanner(new File("res/Map_Data/BucksData_" + mapID + ".txt"));
			
    	int initialNumBucks = s.nextInt();
    	//JOptionPane.showMessageDialog(this, initialNumBucks);
    	bucksSpawnSize = s.nextInt();
    	for(int i = 0; i < initialNumBucks; i++)
    		obstacles.add(StarBucks.generateRandomStarBucks(screenSize.width, screenSize.height));
    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    private int readFile(String in, int objW, int objH) throws ScriptException {
    	String temp = "";
    	for(int i = 0; i < in.length(); i++)
    		temp += String.valueOf(checkVariable(in.charAt(i), objW, objH));
    	return (int) converter.eval(temp);
    }
    
    private String checkVariable(char c, int objW, int objH) {
    	if(c <= '9' && c >= '0')
    		return String.valueOf(c);
    	switch(c) {
    	case 'w': return String.valueOf(screenSize.width); 
		case 'h': return String.valueOf(screenSize.height); 
		case 'm': return String.valueOf(screenSize.width / 35); 
		case 'n': return String.valueOf(screenSize.height / 25); 
		case 'a': return String.valueOf(objW); 
		case 'b': return String.valueOf(objH); 
		case '*': return "*";
		case '-': return "-";
		default: JOptionPane.showMessageDialog(this, "File Variable Error"); return "";
    	}
    }
    
    
    
    
    /*-----------------------------Painting----------------------------*/
    @Override
    /** Paints the panel differently according to the current state (GAME,
     *  MENU, TUTORIAL, STORE, GAMEOVER)
     *  @param g the graphics object
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImages[mapID - 1], 0, 0, null); //draws backdrop

        if(State == STATE.GAME)
        {
        	drawGame(g);
        }
        else if(State == STATE.MENU)
        {
        	menu.renderMenuButtons(g);
        }
        else if(State == STATE.TUTORIAL)
        {
        	menu.renderTutorial(g);
        }
        else if(State == STATE.STORE)
        {
        	//JOptionPane.showMessageDialog(this, "Store Pressed");
        	menu.renderStoreMenuButtons(g);
        }
        else if(State == STATE.GAMEOVER)
        {
            frameTimer.cancel();
            spawnTimer.cancel();
            drawGameOver(g);
        	menu.renderEndToMenu(g);
        }
		
        Toolkit.getDefaultToolkit().sync(); // makes animation run smoother 
    }

    
    /**
     * Literally just paintComponent but private
     * Draws gamestate, including background and all visible SpaceObjects.
     */
    private void drawGame(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setClip(null);
        
        if(mapID == NUM_MAPS)
        	g2.drawImage(endPlanet.getImage(), endPlanet.getX(), endPlanet.getY(), this);
        
        
        drawMovable(g2);
        drawClickables(g2);
        drawExtraStats(g2);
        drawHUD(g2);
        drawShip(g2);
        
    }
    /** Draws the ship in 2D and adds it to the SpaceWorld
     *  @param g2 the Graphics2D object
     */
    private void drawShip(Graphics2D g2) {
        if(ship.isVisible()) {
        	
            g2.translate(ship.getX() + ship.getWidth() / 2, ship.getY() + ship.getHeight() / 2);
            g2.rotate(ship.getDirection());
            g2.drawImage(ship.getImage(), ship.getWidth() / -2, ship.getHeight() / -2, this);
            g2.rotate(-1 * ship.getDirection());
            g2.translate(-1 * (ship.getX() + ship.getWidth() / 2), -1 * (ship.getY() + ship.getHeight() / 2));
            
        	
        	//g2.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        }   
    }
    /** Draws the movable object adds it to the SpaceWorld
     *  @param g2 the Graphics2D object
     */
    private void drawMovable(Graphics2D g2){
        for(int i = 0; i < obstacles.size(); i++)
            if(obstacles.get(i).isVisible()) {
            	if(obstacles.get(i) instanceof Asteroid) {
                Asteroid m = (Asteroid) obstacles.get(i);
                
                g2.translate(m.getX() + m.getWidth() / 2 , m.getY() + m.getHeight() / 2 );
                g2.rotate(Math.toRadians(m.getRotatedAngle()));
                //g2.setClip(m.getTransformedShape());
                g2.drawImage(m.getImage(), m.getWidth() / -2, m.getHeight() / -2, this);
                g2.rotate(Math.toRadians(m.getRotatedAngle()) * -1);
                g2.translate(-1.0 * (m.getX() + m.getWidth() / 2), -1.0 * (m.getY() + m.getHeight() / 2 ));

                m.incrementAngle();
            	}
            
            else if(obstacles.get(i) instanceof StarBucks) {
            		StarBucks s = (StarBucks) obstacles.get(i);
            		
            		g2.drawImage(s.getImage(), s.getX(), s.getY(), this);
            	}
            }
    }
    /** Draws the health , fuel and throttle bars located at the top-left of the screen
     *  @param g2 the given Graphics2D object
     */
    private void drawHUD(Graphics2D g2) {
    	//draw bars outline
    	g2.setColor(Color.white);
        g2.drawRect(HUDBARS_X, HPBAR_Y, HUDBARS_W, HUDBARS_H);
        g2.drawRect(HUDBARS_X, FUELBAR_Y, HUDBARS_W, HUDBARS_H);
        g2.drawRect(HUDBARS_X, THROTTLEBAR_Y, HUDBARS_W, HUDBARS_H);
          
        //draws "empty" bar
        g2.setColor(Color.gray);
        g2.fillRect(HUDBARS_X, HPBAR_Y, HUDBARS_W, HUDBARS_H);
        g2.fillRect(HUDBARS_X, FUELBAR_Y, HUDBARS_W, HUDBARS_H);
        g2.fillRect(HUDBARS_X, THROTTLEBAR_Y, HUDBARS_W, HUDBARS_H);
          
        //fills current bar levels
        g2.setColor(Color.green);
        g2.fillRect(HUDBARS_X, HPBAR_Y, (int) ((float) ship.getCurrentHP() / ship.getMaxHP() * HUDBARS_W), HUDBARS_H);
        g2.setColor(Color.red);
        g2.fillRect(HUDBARS_X, FUELBAR_Y, (int) ((float) ship.getCurrentFuel() / ship.getMaxFuel() * HUDBARS_W), HUDBARS_H);
        g2.setColor(Color.blue);
        g2.fillRect(HUDBARS_X, THROTTLEBAR_Y, (int) (ship.getA() / ship.getMaxA() * HUDBARS_W), HUDBARS_H);
           
        Font fon = new Font("Agency FB", Font.BOLD, HUD_FONT);
        g2.setFont(fon);
        g2.setColor(Color.white);
        FontMetrics metric = g2.getFontMetrics(fon);
        int HPLabelx = HP_AND_FUEL_LABEL_X + (HP_AND_FUEL_LABEL_W - metric.stringWidth("HP")) / 2;
        int HPLabely = HP_LABEL_Y + ((HP_AND_FUEL_LABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g2.drawString("HP", HPLabelx, HPLabely);
 
        int fuelLabelx = HP_AND_FUEL_LABEL_X + (HP_AND_FUEL_LABEL_W - metric.stringWidth("HP")) / 2;
        int fuelLabely = FUEL_LABEL_Y + ((HP_AND_FUEL_LABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g2.drawString("Fuel", fuelLabelx, fuelLabely);
         
        int throttleLabelx = HP_AND_FUEL_LABEL_X + (HP_AND_FUEL_LABEL_W - metric.stringWidth("HP")) / 2;
        int throttleLabely = THROTTLE_LABEL_Y + ((HP_AND_FUEL_LABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g2.drawString("Throttle", throttleLabelx, throttleLabely);
          
        g2.setFont(new Font("Agency FB", Font.PLAIN, HUD_FONT));
        String hpDis = ship.getCurrentHP() + "/" + ship.getMaxHP();
        String fuelDis = ship.getCurrentFuel() + "/" + ship.getMaxFuel();
        String throttleDis = ship.getA() + "/" + ship.getMaxA();
        g2.drawString(hpDis, HUDBARS_X + HUDBARS_W / 2 - g2.getFontMetrics().stringWidth(hpDis) / 2
                , HPBAR_Y + HUDBARS_H / 2 - g2.getFontMetrics().getAscent() / 2);
        g2.drawString(fuelDis, HUDBARS_X + HUDBARS_W / 2 - g2.getFontMetrics().stringWidth(fuelDis) / 2
                , FUELBAR_Y + HUDBARS_H / 2 - g2.getFontMetrics().getAscent() / 2);
        g2.drawString(throttleDis, HUDBARS_X + HUDBARS_W / 2 - g2.getFontMetrics().stringWidth(throttleDis) / 2
                , THROTTLEBAR_Y + HUDBARS_H / 2 - g2.getFontMetrics().getAscent() / 2);
    }
    /** Draws Clickable objects across the SpaceWorld panel
     *  @param g2 the given Graphics2D object
     */
    private void drawClickables(Graphics2D g2) {
    	for(int i = 0; i < places.size(); i++) {
    		Clickable place = places.get(i);
    		g2.drawImage(place.getImage(), place.getX(), place.getY(), this);
    	}
    }
    /** Draws the information about food, money, days elapsed, and cargo on the stats bar
     *  @param g the given Graphics object
     */
    private void drawExtraStats(Graphics g)
    {   
        Font fon = new Font("Agency FB", Font.BOLD, STATS_FONT);
        g.setFont(fon);
        g.setColor(Color.white);
        FontMetrics metric = g.getFontMetrics(fon);
        int foodLabelx = FOOD_LABEL_X + (STATSLABEL_W - metric.stringWidth("Food (Onions): " + String.valueOf(ship.getFood()))) / 2;
        int foodLabely = STATSLABEL_Y + ((STATSLABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g.drawString("Food: " + String.valueOf(ship.getFood()), foodLabelx, foodLabely);
 
        int moneyLabelx = MONEY_LABEL_X + (STATSLABEL_W - metric.stringWidth("Starbucks: $" + String.valueOf(ship.getWallet()))) / 2;
        int moneyLabely = STATSLABEL_Y + ((STATSLABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g.drawString("Starbucks: $" + String.valueOf(ship.getWallet()), moneyLabelx, moneyLabely);
         
        int dayLabelx = DAY_LABEL_X + (STATSLABEL_W - metric.stringWidth("Days Elapsed: " + String.valueOf(timeTracker / DAY_LENGTH))) / 2;
        int dayLabely = STATSLABEL_Y + ((STATSLABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        g.drawString("Days Elapsed: " + String.valueOf(timeTracker / DAY_LENGTH), dayLabelx, dayLabely);
 
        int cargoLabelx = CARGOLABEL_X + (CARGOLABEL_W - metric.stringWidth("Cargo: " + String.valueOf(ship.getInventory()))) / 2;
        int cargoLabely = CARGOLABEL_Y + ((CARGOLABEL_H - metric.getHeight()) / 2) + metric.getAscent();
        String inventory = ship.getInventory().get(0).toString();
        for(int i = 0; i < ship.getInventory().size(); i++)
        	inventory += ", " + ship.getInventory().get(i).toString();
        		
        g.drawString("Cargo: " + ship.getInventory(), cargoLabelx, cargoLabely);
    }
     
    /** Draws the game over message and displays the user's score
     *  @param g the given Graphics object
     */
    private void drawGameOver(Graphics g) 
    {
    	Font fon = new Font("Agency FB", Font.BOLD, GAMEOVER_FONT);
        g.setFont(fon);
        g.setColor(Color.white);
        FontMetrics metric = g.getFontMetrics(fon);
        int gameOverx = GAMEOVER_LOCATION_X + (GAMEOVER_WIDTH - metric.stringWidth("GAME OVER!")) / 2;
        int gameOvery = GAMEOVER_LOCATION_Y + ((GAMEOVER_HEIGHT - metric.getHeight()) / 2) + metric.getAscent();
        g.drawString("GAME OVER!", gameOverx, gameOvery);
         
        calcScore();
    }
    
    private void calcScore() {
    	long finalScore = 0;
        long highScore = 0;
        finalScore = ship.getWallet() + ship.getInventory().get(0).getValue() + ship.getFood() * FOOD_RATIO + ship.getCurrentFuel() * FUEL_RATIO 
            + ship.getCurrentHP() * HP_RATIO - timeTracker / DAY_LENGTH * DAY_RATIO;
        
        int cargoScore = 0;
        for(int i = 0; i < ship.getInventory().size(); i++) {
        	Cargo c = ship.getInventory().get(i);
        	if(c instanceof MiningEquipment)
        		cargoScore += ((MiningEquipment) c).getScore();
        	else
        		cargoScore += c.getValue();
        }
        finalScore += cargoScore;
        
        System.out.println("Food: " + ship.getFood() + " Onions * " + FOOD_RATIO);
        System.out.println("Starbucks: $" + ship.getWallet());
        System.out.println("Fuel: " + ship.getCurrentFuel() + " Gallons * " + FUEL_RATIO);
        System.out.println("Equipment and Upgrades: " + cargoScore);
        System.out.println("Health: " + ship.getCurrentHP() + " HP * " + HP_RATIO);
        System.out.println("Time Elapsed: -" + timeTracker / DAY_LENGTH + " * " + DAY_RATIO);
        System.out.println("Total Score: " + finalScore);
 
        if(finalScore > highScore)
        {
            highScore = finalScore;
            System.out.println("New High Score!");
        }
    }
     
    /*----------------------------Timers& Update Methods------------------------*/
    /** Changes the map and begins running, repaints the storem and adds time elapsed
     */
    private class StepTask extends TimerTask{
    	@Override
        public void run() {
    			
    		if(State == STATE.MAPCHANGE) {
        	   reloadMap();
        	   State = STATE.GAME;
        	   
           }
           else if(State == STATE.GAME){
        	   gameStep();
        	   timeTracker++;
           }
    		
           else
        	   repaint();
        }
    }
    
    private class SpawnTask extends TimerTask{
        @Override
        public void run() {
        
           spawn();
             
        }
    }
     
    private void spawn() {
    	int aSize = Math.min(asteroidSpawnSize, maxObstacles - numAst);
    	
    	for(int i = 0; i < aSize ; i++)
    		obstacles.add(Asteroid.generateRandomEdgeAsteroid(screenSize.width, screenSize.height)); 
    	numAst += aSize;
    	for(int i = 0; i < bucksSpawnSize; i++)
    		obstacles.add(StarBucks.generateRandomStarBucks(screenSize.width, screenSize.height));
    }
 
    /**
     *  Literally just ScheduleTask but private
     *  Only repaints +- 1 pixel around objects for optimization
     */
    private void gameStep() {
        updateShip();//update all variable SpaceObjects
        updateMovable();
        checkCollisions();
        
        if(ship.getCurrentHP() <= 0 || ship.getCurrentFuel() <= 0 || ship.getFood() <= 0)
            State = STATE.GAMEOVER; 
        
        if(mapID == NUM_MAPS && endPlanet.intersects(ship))
        	State = STATE.GAMEOVER;
        repaint();
    }
     
    private void updateShip() {
        if(ship.isVisible()) {
            ship.move(getWidth(), getHeight());
        }
             
    }
    private void updateMovable() {
        for(int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).isVisible())
                obstacles.get(i).move(getWidth(), getHeight());
            else {
                obstacles.remove(i);
                numAst--;
                i--;
            }
                 
        }
    }
    private void checkCollisions() {
    	
        for(int i = 0; i < obstacles.size(); i++) {
            Movable m = obstacles.get(i);
            if(ship.intersects(m) && m.isVisible()) {
                ship.collide(m);
            }
        }
         
                
    }

    
    /*--------------------------Accessors----------------------*/
    public List<Clickable> getClickables(){
    	return places;
    }
    
    public Menu getMenu() {
    	return menu;
    }
    
    /*-------------------------State Changers-------------------*/
    public void changeMap(int dest) {
    	mapID = dest;
    	State = STATE.MAPCHANGE;
    }
    
    public void restartGame() {
    	init();
    }

    private class KAdapter extends KeyAdapter{
         
        @Override
        public void keyReleased(KeyEvent e) {
            ship.keyReleased(e);
        }
         
        @Override
        public void keyPressed(KeyEvent e) {
        	ship.keyPressed(e);
        }
    }

}