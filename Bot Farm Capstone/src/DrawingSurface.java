import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represnts the DrawingSurface.
 * @author Harry Guan
 */

public class DrawingSurface extends PApplet implements MouseListener {

	public static final int WIDTH = 750;
	public static final int HEIGHT = 750;
	public static final int MAP_SIZE = 25;
	private String[] bots = {"blindbot", "explobot", "glitchbot"};
	public static PImage explob, explobb, glitchb, blindb, explobullet, glitchbullet, blindbullet, 
	androidbullet, rock, toxicgas, cursor, android, missile, button;
	private Player p1;
	private static Rectangle2D.Double border;
	private Button start, playAgain, info, goBack;
	public int gameState;
	// -1: Before start, 0: Playing, 1: Dead -2: info

	private ArrayList<MovingImage> list; //This ArrayList stores every single object represented on screen.
	private int spawnRate;
	private int kills;
	private int blind;
	private boolean keyW, keyA, keyS, keyD;

	public DrawingSurface() { //Initializes every field, creating images and objects, adding them to the list.
		
		list = new ArrayList<MovingImage>();
		gameState = -1;
		keyW = false;
		keyA = false;
		keyS = false;
		keyD = false;
		blind = 0;
	}
	public void settings() {
		size(WIDTH, HEIGHT);
	}
	/**
	 * Sets up most the background as well as the kill count. 
	 */
	public void setup() {
		android = loadImage("android.png");
		explob = loadImage("explobot.png");
		explobb = loadImage("explobotbaby.png");
		explobullet = loadImage("explobbullet.png");
		glitchb = loadImage("glitchb.png");
		blindb = loadImage("blindbot.png");
		rock = loadImage("rock.png");
		glitchbullet = loadImage("glitchbullet.png");
		blindbullet = loadImage("blindbullet.png");
		androidbullet = loadImage("androidbullet.png");
		cursor = loadImage("cursor.png");
		toxicgas = loadImage("toxicgas.png");
		missile = loadImage("cursor.png");
		button = loadImage("button.png");
		start = new Button(button, WIDTH/2, HEIGHT/2, 250, 50, "Start Game", 40);
		playAgain = new Button(button, WIDTH/2, 500, 250, 50, "Play Again?", 40);
		info = new Button(button, WIDTH/2,500, 250, 50, "Info", 40);
		goBack = new Button(button, WIDTH/2,700,250,50, "Go Back", 40);
		border = new Rectangle2D.Double(0, 0, MAP_SIZE * 50, MAP_SIZE * 50);
		p1 = new Player(android, WIDTH/2, HEIGHT/2, 42, 42);
		list.add(p1);
		this.frameRate(60);
		//cursor(cursor, 16,16);
		startGame();
	}
	
	/**
	 * Draws all of the MovingImages in the list, and creates a hardcoded Start and game end HUD. 
	 */
	public void draw() {
		background(0,100,0);
		if (gameState == 0) {
			
			if (p1.isDead()) {
				if (list.contains(p1)) {
					blind = 0;
					list.remove(p1);
					delay(1500);
				}
				if (blind >= 255)
					gameState = 1;
				else
					blind += 3;
			}
			else
				runGame();
			if (keyW || keyS) {
				if (keyW) p1.setvY(-5);
				if (keyS) p1.setvY(5);
			}
			else p1.setvY((int)(p1.getVy() * 0.99));
			if (keyA || keyD) {
				if (keyA) p1.setvX(-5);
				if (keyD) p1.setvX(5);
			}
			else p1.setvX((int)(p1.getVx() * 0.99));
			
			for (MovingImage m : list)
				if (m instanceof Block) m.draw(this);
			for (MovingImage m : list)
				if (!(m instanceof Block)) m.draw(this);
			textSize(40);
			fill(200);
			pushMatrix();
			fill(0, blind);
			noStroke();
			rect(0, 0, width, height);
			popMatrix();
			if (blind > 0) blind--;
			//If the player is dead, display a death message.
			textSize(40);
			fill(200);
			this.text(kills + " kills.", 25, 50);
		}
		else if(gameState == -2) {
			pushMatrix();
			background(0);
			textSize(20);
			fill(200,30,30);
			this.text(" After a devastating robot takeover of the Planet X-69, " + "\n" + " there is a miniscule amount of human lifeforms remaining on the planet " + "\n" + " which the massive army of robots seek to snuff out. The robots are rapidly " + "\n" + " advancing in their combat prowess, and the rebels must wipe them out " + "\n" + " before they are unstoppable. \n"
					 , 50,100);
			this.text("MISSION\n"
					+ "Poison gas stops your retreat, defeat all the bots and survive for as \nlong as possible while avoiding clouds of poisonous gas."
					, 100,300);
			this.text("INSTRUCTIONS\n"
					+ "WASD: keys for movement\n"
					+ "Q: Special ability. Watch out for mana usage\n"
					+ "Left Mouse click: basic attacks\n"
					+ "", 100,500);
			fill(255);
			popMatrix();
			goBack.draw(this);
		}
		else if (gameState == -1){
			background(0);
//			fill(200);
//			this.rect(250, 350, 250, 50);
//			fill(255);
			pushMatrix();
			textSize(100);
			fill(200,30,30);
			this.text("BOT FARM", 150,300);
			fill(255);
			popMatrix();
//			this.text("Start Game", 270, 390);
			start.draw(this);
			info.draw(this);
		}
		else if (gameState == 1) {
			blind = 250;
			textSize(40);
			fill(200);
			pushMatrix();
			fill(0, blind);
			noStroke();
			rect(0, 0, width, height);
			popMatrix();
			fill(255);
			pushStyle();
			textAlign(CENTER, CENTER);
			textSize(100);
			fill(255, 0, 0);
			this.text("Game Over", WIDTH/2, 200);
			textSize(40);
			fill(200);
			this.text("You've killed " + kills + " enemies.", WIDTH/2, 330);
			popStyle();
			playAgain.draw(this);
			delay(20);
		}
	}
	/**
	 * Spawns the different Bots. 
	 */
	public void spawnEnemy() {
		for (int i = 0; i < 1; i++) {
			String bot = bots[(int)(Math.random() * bots.length)];
			boolean occupied = true;
			int enemyX = 0;
			int enemyY = 0;
			int xLeft = border.getX() < 0 ? 0 : (int)border.getX();
			int xRight = border.getX() + border.getWidth() > WIDTH ? WIDTH : (int) (border.getX() + border.getWidth());
			int yTop = border.getY() < 0 ? 0 : (int)border.getY();
			int yBot = border.getY() + border.getHeight() > HEIGHT ? HEIGHT : (int) (border.getY() + border.getHeight());
			while(occupied) {
				
				enemyX = (int) (Math.random() * (xRight + xLeft) - xLeft);
				enemyY = (int) (Math.random() * (yBot + yTop) - yTop);
				occupied = false;
				occupied = !border.intersects(enemyX, enemyY, 50, 50);
				for (MovingImage image : list) {
					if (image.intersects(enemyX, enemyY, 50, 50))
						occupied = true;
				}
			}

			//add enemies
			if (bot.equalsIgnoreCase("blindbot"))
				list.add(new BlindBot(blindb, enemyX, enemyY, 50, 50, 100));
			else if (bot.equalsIgnoreCase("explobot"))
				list.add(new ExploBot(explob, enemyX, enemyY, 50, 50, 50));
			else if (bot.equalsIgnoreCase("glitchbot"))
				list.add(new GlitchBot(glitchb, enemyX, enemyY, 50, 50, 100));
		}
	}


	/**
	 * Runs the game and detects for collision of projectiles. 
	 */
	public void runGame() {
		if (list.size() < 3) {
			spawnEnemy();
		}
		spawnRate++; //Spawn rate timer
		if (spawnRate % 200 == 0) { //Spawns enemy once every 400 1/60th of a second.
			spawnEnemy();
		}
		for (int i = 0; i < list.size(); i++) { //This code handles the collision.
			MovingImage actor = list.get(i);
			MovingImage actedUpon = actor.act(list);
			if (actedUpon != null) {
				if (actor instanceof Projectile) {
					if (actedUpon instanceof Player) {
						if (actor instanceof BlindProjectile) {
							blind = 255;
							((Player) actedUpon).loseHP(2);
						}
						//if player gets hit by bullet

						//if hit by explobb
						if(actor instanceof ExploBotBabyProjectile) {
							((Player) actedUpon).loseHP(10);
						}

						if(actor instanceof GlitchProjectile) {
							((Player) actedUpon).loseHP(15);
						}

						//((Player) actedUpon).loseHP();
						//initiate method loseHP
						list.remove(actor);
						//remove the bullet
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
					else if (actor instanceof AndroidBasicProjectile ){
						if (actedUpon instanceof Bot) {
							((Bot) actedUpon).loseHP(10);
							list.remove(actor);
							if (actedUpon instanceof ExploBotBaby) {
								//if explobotbaby gets hit by bullet
								((ExploBotBaby) actedUpon).die();
								//die
								i--; 
							}
							if (((Bot) actedUpon).isDead())
								kills++;
						}
					} else if (actor instanceof AndroidMissile){
						if (actedUpon instanceof Bot) {
							((Bot) actedUpon).loseHP(100);
							list.remove(actor);
							if (actedUpon instanceof ExploBotBaby) {
								//if explobotbaby gets hit by bullet
								((ExploBotBaby) actedUpon).die();
								//die
								i--;
								kills++;  
							}
						}
					}
				}
			}
			if (actor instanceof Player) {
				sideScroll(p1.getVx(), p1.getVy());
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
		if (gameState == 0 &&  !p1.isDead()	) {
			Projectile proj = p1.shoot(mouseX, mouseY);
			if (proj != null)
				list.add(proj);
		} else if (gameState == -1){
			if (start.isHovered(mouseX, mouseY)) {
				gameState = 0;
			} else if(info.isHovered(mouseX,mouseY)) {
				gameState = -2;
			}
		}
		else if (gameState == 1) {
			if (playAgain.isHovered(mouseX, mouseY)) {
				startGame();
				gameState = 0;
			}
		}
		else if(gameState == -2) {
			if(goBack.isHovered(mouseX,mouseY)) {
				gameState = -1;
			}
		}
	}

	/**
	 * 
	 * Setting velocity with the WASD keys for player movement.  
	 */
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_W) {
			keyW = true;
			p1.setvY(-5);
			//            p1.setvY(-5);
			//            up.resize(42, 42);
			//            p1.image = up;
		}
		if (keyCode == KeyEvent.VK_A) {
			keyA = true;
			p1.setvX(-5);
			//p1.setvX(-5);
			//p1.image = left;
		}
		if (keyCode == KeyEvent.VK_S) {
			keyS = true;
			p1.setvY(5);
			//p1.setvY(5);
			//p1.image = down;
		}
		if (keyCode == KeyEvent.VK_D) {
			keyD = true;
			p1.setvX(5);
			//p1.setvX(5);
			///right.resize(42, 42);
			//p1.image = right;
		}
		if (keyCode == KeyEvent.VK_Q) {
			list.add(p1.launchMissile(mouseX,  mouseY));
		}
	}
	/**
	 * Setting velocity to 0 when WASD keys are released. 
	 */
	public void keyReleased() {
		if (keyCode == KeyEvent.VK_W) keyW = false;
		if (keyCode == KeyEvent.VK_A) keyA = false;
		if (keyCode == KeyEvent.VK_S) keyS = false;
		if (keyCode == KeyEvent.VK_D) keyD = false;
	}

	/**
	 * Returns the border of the DrawingSurface
	 * @return border A Rectangle2D object representing the border.
	 */
	public static Rectangle2D.Double getBorder() {
		return border;
	}
	
	private void sideScroll(int x, int y) {
		border.setFrame(border.getX() - x, border.getY() - y, border.getWidth(), border.getHeight());
		for (MovingImage image : list) {
			if (!(image instanceof Player))
				image.moveByAmount(-x, -y);
		}
	}
	private void startGame() {
		blind = 0;
		kills = 0;
		list = new ArrayList<MovingImage>();
		p1 = new Player(android, WIDTH/2, HEIGHT/2, 42, 42);
		border = new Rectangle2D.Double(0, 0, MAP_SIZE * 50, MAP_SIZE * 50);
		list.add(p1);
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				if (x <= 1 || x >= MAP_SIZE - 2 || y <= 1 || y >= MAP_SIZE - 2) {
					Block border = new Block(toxicgas, x * 50, y * 50, 50, 50);
					list.add(border);
				}
				else {
					float chance = (float)Math.random();
					if (!p1.intersects(x * 50, y * 50, 50, 50) && chance < 0.05) {
						NoClipBlock gas = new NoClipBlock(toxicgas, x * 50, y * 50, 50, 50);
						list.add(gas);
					} else if (!p1.intersects(x * 50, y * 50, 50, 50) && chance < 0.15) {
						Block block = new Block(rock, x * 50, y * 50, 40, 40);
						list.add(block);
					}
				}
			}
		}
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
