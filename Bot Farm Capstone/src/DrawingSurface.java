import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.util.Scanner;

/**
 * Represnts the DrawingSurface.
 * @author Harry Guan
 */

public class DrawingSurface extends PApplet implements MouseListener {

    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    private String[] bots = {"blindbot", "explobot", "glitchbot"};
    public static PImage explob, explobb, glitchb, blindb, explobullet, glitchbullet, blindbullet, 
    androidbullet, rock, toxicgas, cursor, android;
    private final Player p1;
    public boolean gameStarted;

    private final ArrayList<MovingImage> list; //This ArrayList stores every single object represented on screen.
    private int spawnRate;
    private int kills;
    private boolean keyX, keyY;

    public DrawingSurface() { //Initializes every field, creating images and objects, adding them to the list.
    	android = loadImage("../assets/android.png");
    	explob = loadImage("../assets/explobot.png");
    	explobb = loadImage("../assets/explobotbaby.png");
    	explobullet = loadImage("../assets/explobbullet.png");
    	glitchb = loadImage("../assets/glitchb.png");
    	blindb = loadImage("../assets/blindbot.png");
    	rock = loadImage("../assets/rock.png");
    	glitchbullet = loadImage("../assets/glitchbullet.png");
    	blindbullet = loadImage("../assets/blindbullet.png");
    	androidbullet = loadImage("../assets/androidbullet.png");
    	cursor = loadImage("../assets/cursor.png");
    	toxicgas = loadImage("../assets/toxicgas.png");
    	p1 = new Player(android, WIDTH/2, HEIGHT/2, 42, 42);
    	list = new ArrayList<MovingImage>();
    	list.add(p1);
        gameStarted = false;
        keyX = false;
        keyY = false;
    }
    /**
	 * Sets up most the background as well as the kill count. 
	 */
    public void setup() {
        size(WIDTH, HEIGHT);
        this.frameRate(60);
    //    cursor(cursor, 16,16);
        File f = new File("../assets/blocks.txt");
        Scanner file = null;
        try {
            file = new Scanner(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 15; i++) { //looks through the rows
            for (int j = 0; j < 15; j++) { // looks through columns
                int curBlock = file.nextInt();
                switch (curBlock) {
                    case 1:
                        Block block = new Block(rock, j * 50, i * 50, 40, 40);
                        list.add(block);
                        break;
                    case 2:
                        NoClipBlock lavva = new NoClipBlock(toxicgas, j * 50, i * 50, 50, 50);
                        list.add(lavva);
                    case 3:
                        Block white = new Block(toxicgas, j * 50, i * 50, 40, 40);
                        list.add(white);

                }
            }
        }
        textSize(40);
        fill(200);
        this.text(kills / 6 + " kills.", 730, 730);
    }
    /**
	 * Draws all of the MovingImages in the list, and creates a hardcoded Start and game end HUD. 
	 */
    public void draw() {
        background(255, 255, 255);
        if (gameStarted) {
            //Under this comment, draw every MovingImage in list.
        	p1.setvX(keyX ? (int)p1.getVx() : (int)(p1.getVx() * 0.99));
        	p1.setvY(keyY ? (int)p1.getVy() : (int)(p1.getVy() * 0.99));
            for (MovingImage m : list) {
                if (m instanceof Block) m.draw(this);
            }
            for (MovingImage m : list) {
                if (!(m instanceof Block)) m.draw(this);
            }
            textSize(40);
            fill(200);
            //If the player is dead, display a death message.
            if (!p1.isDead()) {
                runGame();
            } else {
            	fill(200);
                this.text("Game Over", 200, 375);
                this.text("You've killed " + kills / 6 + " enemies.", 200, 330);
                delay(4000);
                //exit();
            }
        } else {
            textSize(40);
            fill(200);
            this.text("Start Game", 350, 350);
            this.rect(350, 350, 150, 50);
        }

    }
    /**
	 * Spawns the different Bots. 
	 */
    public void spawnEnemy() {
        for (int i = 0; i < 1; i++) {
        	String bot = bots[(int)(Math.random() * bots.length)];
            int enemyX = (int) (Math.random() * WIDTH);
            int enemyY = (int) (Math.random() * (HEIGHT / 2));
            //add enemies
            if (bot.equalsIgnoreCase("blindbot"))
            	list.add(new BlindBot(blindb, enemyX, enemyY, 50, 50, 100));
            else if (bot.equalsIgnoreCase("explobot"))
            	list.add(new ExploBot(explob, enemyX, enemyY, 50, 50, 100));
            else if (bot.equalsIgnoreCase("glitchbot"))
            	list.add(new GlitchBot(glitchb, enemyX, enemyY, 50, 50, 100));
        }
    }
    
    
    /**
	 * Runs the game and detects for collision. 
	 */
    public void runGame() {
        if (list.size() < 3) {
            spawnEnemy();
        }
        spawnRate++; //Spawn rate timer
        if (spawnRate % 200 == 0) { //Spawns enemy once every 400 1/60th of a second.
            spawnEnemy();
        }
        
        int pVx = 0;
        int pVy = 0;
        for (int i = 0; i < list.size(); i++) { //This code handles the collision.
            MovingImage actor = list.get(i);
            if (actor instanceof Player) {
            	pVx = ((Player) actor).getVx();
            	pVy = ((Player) actor).getVy();
            }
            MovingImage actedUpon = actor.act(list);
            if (actedUpon != null) {
                if (actor instanceof Projectile) {
                    if (actedUpon instanceof Player) {
                        //if player gets hit by bullet
                        
                    	//if hit by explobb
                    	if(actor instanceof ExploBotBabyProjectile) {
                    		((Player) actedUpon).loseHP(10);
                    	}
                    	
                    	//((Player) actedUpon).loseHP();
                        //initiate method loseHP
                        list.remove(actor);
                        //remove the bullet
                        i--;
                    } else if (actedUpon instanceof ExploBotBaby) {
                        //if explobotbaby gets hit by bullet
                        list.remove(actor);
                        //remove bullet
                        ((ExploBotBaby) actedUpon).explode(list);
                        //die
                        i--;
                        //need to implement explosion
                        kills++;
                    }
                }
                }
                if (actor instanceof Player) {
                	if (actedUpon instanceof Block && !(actedUpon instanceof NoClipBlock)) {
//                		while (actor.act(list) != null)
//                			sideScroll(-pVx, -pVy);
                	}
                	else {
                		sideScroll(p1.getVx(), p1.getVy());
                	}
                		
                	if (actedUpon instanceof Bot) {
	                    list.remove(actedUpon);
	                    ((Player) actor).loseHP();
	                    //lose hp
	                    i--;
                	}
                }
                if (actor == actedUpon) {
                    list.remove(actedUpon);
                    i--;
                }
            }
        }
    
    /**
	 * Shoots AndroidBasicProjectile on mouse clicks. 
	 */
    public void mousePressed() {
        if (gameStarted) {
            list.add(p1.shoot(mouseX, mouseY));
        } else {
            if (mouseX < 500 && mouseX > 350 && mouseY < 400 && mouseY > 50) {
                gameStarted = true;
            }
        }
    }

    /**
     * 
	 * Setting velocity with the WASD keys for player movement.  
	 */
    public void keyPressed() {
    	if (keyCode == KeyEvent.VK_W) {
    		keyY = true;
    		p1.setvY(5);
//            p1.setvY(-5);
//            up.resize(42, 42);
//            p1.image = up;
        }
        if (keyCode == KeyEvent.VK_A) {
        	keyX = true;
        	p1.setvX(5);
            //p1.setvX(-5);
            //p1.image = left;
        }
        if (keyCode == KeyEvent.VK_S) {
        	keyY = true;
        	p1.setvY(-5);
            //p1.setvY(5);
            //p1.image = down;
        }
        if (keyCode == KeyEvent.VK_D) {
        	keyX = true;
        	p1.setvX(-5);
            //p1.setvX(5);
            ///right.resize(42, 42);
            //p1.image = right;
        }
    }
    /**
     * Setting velocity to 0 when WASD keys are released. 
     */
    public void keyReleased() {
        if (keyCode == KeyEvent.VK_W) keyY = false;
        if (keyCode == KeyEvent.VK_A) keyX = false;
        if (keyCode == KeyEvent.VK_S) keyY = false;
        if (keyCode == KeyEvent.VK_D) keyX = false;
    }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
	private void sideScroll(int x, int y) {
		for (MovingImage image : list) {
			if (!(image instanceof Player))
				image.moveByAmount(x, y);
		}
	}
}
