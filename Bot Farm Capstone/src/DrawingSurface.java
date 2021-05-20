import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

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
	public static final int MAP_SIZE = 25;
	private String[] bots = {"blindbot", "explobot", "glitchbot"};
	public static PImage explob, explobb, glitchb, blindb, explobullet, glitchbullet, blindbullet, 
	androidbullet, rock, toxicgas, cursor, android, missile;
	private final Player p1;
	private static Rectangle2D.Double border;
	public boolean gameStarted;

	private final ArrayList<MovingImage> list; //This ArrayList stores every single object represented on screen.
	private int spawnRate;
	private int kills;
	private int blind;
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
		missile = loadImage("../assets/cursor.png");
		border = new Rectangle2D.Double(0, 0, MAP_SIZE * 50, MAP_SIZE * 50);
		p1 = new Player(android, WIDTH/2, HEIGHT/2, 42, 42);
		list = new ArrayList<MovingImage>();
		list.add(p1);
		gameStarted = false;
		keyX = false;
		keyY = false;
		blind = 0;
	}
	/**
	 * Sets up most the background as well as the kill count. 
	 */
	public void setup() {
		size(WIDTH, HEIGHT);
		this.frameRate(60);
		//cursor(cursor, 16,16);
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				if (x <= 1 || x >= MAP_SIZE - 2 || y <= 1 || y >= MAP_SIZE - 2) {
					Block border = new Block(toxicgas, x * 50, y * 50, 50, 50);
					list.add(border);
				}
				else {
					float chance = (float)Math.random();
					if (!p1.intersects(x * 50, y * 50, 50, 50)) {

						if (chance < 0.05) {
							NoClipBlock gas = new NoClipBlock(toxicgas, x * 50, y * 50, 50, 50);
							list.add(gas);
						}
						else if (chance < 0.15) {
							Block block = new Block(rock, x * 50, y * 50, 40, 40);
							list.add(block);
						}
					}
				}
			}
		}

		//        File f = new File("../assets/blocks.txt");
		//        Scanner file = null;
		//        try {
		//            file = new Scanner(f);
		//        } catch (Exception e) {
		//            e.printStackTrace();
		//        }
		//        for (int i = 0; i < 15; i++) { //looks through the rows
		//            for (int j = 0; j < 15; j++) { // looks through columns
		//                int curBlock = file.nextInt();
		//                switch (curBlock) {
		//                    case 1:
		//                        Block block = new Block(rock, j * 50, i * 50, 40, 40);
		//                        list.add(block);
		//                        break;
		//                    case 2:
		//                        NoClipBlock lavva = new NoClipBlock(toxicgas, j * 50, i * 50, 50, 50);
		//                        list.add(lavva);
		//                    case 3:
		//                        Block white = new Block(toxicgas, j * 50, i * 50, 40, 40);
		//                        list.add(white);
		//
		//                }
		//            }
		//        }
	}
	/**
	 * Draws all of the MovingImages in the list, and creates a hardcoded Start and game end HUD. 
	 */
	public void draw() {
		background(0,100,0);
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
			if (!p1.isDead()) {
				runGame();
			} else {
				fill(200);
				background(100);
				this.text("Game Over", 200, 375);
				this.text("You've killed " + kills + " enemies.", 200, 330);
				delay(4000);
				//exit();
			}
			
		} else {
			textSize(40);
			background(100);
			fill(200);
			this.rect(250, 350, 250, 50);
			fill(255);
			this.text("Start Game", 270, 390);
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
							((Player) actedUpon).loseHP(10);
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
								kills++;  
							}
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
		if (gameStarted) {
			list.add(p1.shoot(mouseX, mouseY));
		} else {
			if (mouseX < 400 && mouseX > 250 && mouseY < 400 && mouseY > 350) {
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
		if (keyCode == KeyEvent.VK_Q) {
			list.add(p1.launchMissile(mouseX,  mouseY));
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
	public static Rectangle2D.Double getBorder() {
		return border;
	}
	private void sideScroll(int x, int y) {
		border.setFrame(border.getX() + x, border.getY() + y, border.getWidth(), border.getHeight());
		for (MovingImage image : list) {
			if (!(image instanceof Player))
				image.moveByAmount(x, y);
		}
	}
}
