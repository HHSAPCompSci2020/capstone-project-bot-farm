
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Represnts the DrawingSurface.
 * 
 * @author Harry Guan
 */
public class DrawingSurface extends PApplet implements MouseListener {

	public static final int WIDTH = 750;
	public static final int HEIGHT = 750;
	public static final int MAP_SIZE = 25;
	private String[] bots = { "blindbot", "explobot", "glitchbot" };
	public static PImage explob, explobb, glitchb, blindb, explobullet, glitchbullet, blindbullet, androidbullet, rock,
	toxicgas, cursor, android, missile, button, forcefieldU, forcefieldD, forcefieldL, forcefieldR, generatorU,
	generatorD, generatorL, generatorR, generatorC, backdrop, explosion;
	private Player p1;
	private static Rectangle2D.Double border;
	private Button start, playAgain, info, goBack;
	public int gameState;
	// -1: Before start, 0: Playing, 1: Dead -2: info

	private ArrayList<MovingImage> list, background; //This ArrayList stores every single object represented on screen.
	private ArrayList<Rock> brokenRocks;
	private ArrayList<Rectangle2D> stars;
	private int runTime, gameTime;
	private int spawnRate;
	private int kills;
	private int blind;
	private boolean keyW, keyA, keyS, keyD;

	/**
	 * Initializes every field, creating images and objects, and adds them to the
	 * list.
	 */
	public DrawingSurface() {
		//edited from https://www.geeksforgeeks.org/play-audio-file-using-java/
		//volume from http://helpdesk.objects.com.au/java/how-to-control-volume-of-audio-clip
		
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/data/gamemusic.wav"));
			// create clip reference
			Clip clip = AudioSystem.getClip();

			// open audioInputStream to the clip
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			double gain = 0.1;   
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stars = new ArrayList<Rectangle2D>();
		list = new ArrayList<MovingImage>();
		brokenRocks = new ArrayList<Rock>();
		background = new ArrayList<MovingImage>();
		gameState = -1;
		keyW = false;
		keyA = false;
		keyS = false;
		keyD = false;
		blind = 0;
		gameTime = 0;
		for (int i = 0; i < 100; i++) {
			double x = Math.random() * (double) WIDTH;
			double y = Math.random() * (double) HEIGHT;
			double size = Math.random() * 3 + 2;
			stars.add(new Rectangle2D.Double(x, y, size, size));
		}
	}

	/**
	 * Sets the size of the window.
	 */
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
		missile = loadImage("missile.png");
		button = loadImage("button.png");
		forcefieldU = loadImage("forcefield_up.png");
		forcefieldD = loadImage("forcefield_down.png");
		forcefieldL = loadImage("forcefield_left.png");
		forcefieldR = loadImage("forcefield_right.png");
		generatorU = loadImage("generator_up.png");
		generatorD = loadImage("generator_down.png");
		generatorL = loadImage("generator_left.png");
		generatorR = loadImage("generator_right.png");
		generatorC = loadImage("generator_corner.png");
		explosion = loadImage("explosion.png");
		start = new Button(button, WIDTH / 2, HEIGHT / 2, 250, 50, "Start Game", 40);
		playAgain = new Button(button, WIDTH / 2, 500, 250, 50, "Play Again?", 40);
		info = new Button(button, WIDTH / 2, 500, 250, 50, "Info", 40);
		goBack = new Button(button, WIDTH / 2, 650, 250, 50, "Go Back", 40);
		backdrop = loadImage("background.png");
		start = new Button(button, WIDTH/2, HEIGHT/2, 250, 50, "Start Game", 40);
		playAgain = new Button(button, WIDTH/2, 500, 250, 50, "Play Again?", 40);
		info = new Button(button, WIDTH/2,500, 250, 50, "Info", 40);
		goBack = new Button(button, WIDTH/2,650,250,50, "Go Back", 40);
		border = new Rectangle2D.Double(0, 0, MAP_SIZE * 50, MAP_SIZE * 50);
		p1 = new Player(android, WIDTH / 2, HEIGHT / 2, 42, 42);
		list.add(p1);
		this.frameRate(60);
		// cursor(cursor, 16,16);
		textFont(createFont("bahnschrift.ttf", 40));
		startGame();
	}

	/**
	 * Draws all of the MovingImages in the list, and creates a Start and death
	 * screen.
	 */
	public void draw() {

		runTime++;
		background(0, 100, 0);
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
			} else
				runGame();
			if (keyW || keyS) {
				if (keyW)
					p1.setvY(-5);
				if (keyS)
					p1.setvY(5);
			} else
				p1.setvY((int) (p1.getVy() * 0.99));
			if (keyA || keyD) {
				if (keyA)
					p1.setvX(-5);
				if (keyD)
					p1.setvX(5);
			} else
				p1.setvX((int) (p1.getVx() * 0.99));
			for (MovingImage b : background)
				b.draw(this);
			for (MovingImage m : list)
				if (m instanceof Block)
					m.draw(this);
			for (MovingImage m : list)
				if (!(m instanceof Block))
					m.draw(this);
			textSize(40);
			fill(200);
			pushMatrix();
			fill(0, blind);
			noStroke();
			rect(0, 0, width, height);
			popMatrix();
			if (blind > 0)
				blind--;
			textSize(40);
			fill(255);
			this.text(getTime(gameTime) + "\n" + kills + " kills ", 25, 50);
		}
		else if (gameState == -2) {
			pushMatrix();
			background(0);
			textSize(20);
			fill(200, 30, 30);
			//			this.text(" After a devastating robot takeover of the Planet X-69, " + "\n" + " there is a miniscule amount of human lifeforms remaining on the planet " + "\n" + " which the massive army of robots seek to snuff out. The robots are rapidly " + "\n" + " advancing in their combat prowess, and the rebels must wipe them out " + "\n" + " before they are unstoppable. \n"
			//					, 50,100);
			this.text(
					"     After a devastating robot takeover of the Planet X-69, there is a miniscule amount of human lifeforms remaining on the planet which the massive army of robots seek to snuff out. The robots are rapidly advancing in their combat prowess, and the rebels must wipe them out before they are unstoppable.",
					50, 100, WIDTH - 100, 1000);
			fill(200,30,30);
			//			this.text(" After a devastating robot takeover of the Planet X-69, " + "\n" + " there is a miniscule amount of human lifeforms remaining on the planet " + "\n" + " which the massive army of robots seek to snuff out. The robots are rapidly " + "\n" + " advancing in their combat prowess, and the rebels must wipe them out " + "\n" + " before they are unstoppable. \n"
			//					, 50,100);
			this.text("     After a devastating robot takeover of the Planet X-69, there is a miniscule amount of human lifeforms remaining on the planet which the massive army of robots seek to snuff out. The robots are rapidly advancing in their combat prowess, and the rebels must wipe them out before they are unstoppable."
					, 50,100, WIDTH-100,1000);
			this.text("MISSION\n"
					+ "You've been trapped in a forcefield by the robot army, defeat all the bots and survive for as long as possible while avoiding clouds of poisonous gas.",
					100, 300, WIDTH - 200, 1000);
			this.text("INSTRUCTIONS\n" + "WASD: keys for movement\n" + "Q: Special ability. Watch out for mana usage\n"
					+ "Left Mouse click: basic attacks\n" + "", 100, 500);
			fill(255);
			popMatrix();
			goBack.draw(this);
		} else if (gameState == -1) {
			background(0);
			pushMatrix();
			noStroke();
			for (Rectangle2D star : stars) {
				if (runTime % 10 == 0) {
					double width = Math.random() * 3 + 2;
					star.setFrame(star.getX() + 10, star.getY() + 1, width, width);
					if (star.getCenterX() > WIDTH || star.getCenterY() > HEIGHT)
						star.setFrame(0, (float) Math.random() * HEIGHT, width, width);
				}
				square((float) star.getX(), (float) star.getY(), (float) star.getWidth());
			}
			textSize(100);
			fill(200, 30, 30);
			pushStyle();
			textAlign(CENTER, CENTER);
			this.text("BOT FARM", WIDTH / 2, 200);
			popStyle();
			fill(255);
			popMatrix();
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
			this.text("Game Over", WIDTH / 2, 200);
			textSize(40);
			fill(200);
			this.text("You survived for " + getTime(gameTime) + "\nand killed " + kills + " enemies.", WIDTH / 2, 330);
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
			String bot = bots[(int) (Math.random() * bots.length)];
			boolean occupied = true;
			int enemyX = 0;
			int enemyY = 0;
			int xLeft = border.getX() < 0 ? 0 : (int) border.getX();
			int xRight = border.getX() + border.getWidth() > WIDTH ? WIDTH : (int) (border.getX() + border.getWidth());
			int yTop = border.getY() < 0 ? 0 : (int) border.getY();
			int yBot = border.getY() + border.getHeight() > HEIGHT ? HEIGHT
					: (int) (border.getY() + border.getHeight());
			while (occupied) {

				enemyX = (int) (Math.random() * (xRight + xLeft) - xLeft);
				enemyY = (int) (Math.random() * (yBot + yTop) - yTop);
				occupied = false;
				occupied = !border.intersects(enemyX, enemyY, 50, 50);
				for (MovingImage image : list) {
					if (image.intersects(enemyX, enemyY, 50, 50))
						occupied = true;
				}
			}

			// add enemies
			if (bot.equalsIgnoreCase("blindbot"))
				list.add(new BlindBot(blindb, enemyX, enemyY, 50, 50, 80));
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
		gameTime++;
		if (list.size() < 3) {
			spawnEnemy();
		}
		spawnRate++; // Spawn rate timer
		if (spawnRate % 200 == 0) { // Spawns enemy once every 400 1/60th of a second.
			spawnEnemy();
		}
		for (int i = 0; i < list.size(); i++) { // This code handles the collision.
			MovingImage actor = list.get(i);
			MovingImage actedUpon = actor.act(list);
			if (actedUpon != null) {
				if (actor instanceof Projectile) {
					if (actedUpon instanceof Player) {
						if (actor instanceof BlindProjectile) {
							blind = 255;
							((Player) actedUpon).loseHP(2);
						}
						// if player gets hit by bullet

						// if hit by explobb
						if (actor instanceof ExploBotBabyProjectile) {
							((Player) actedUpon).loseHP(10);
						}

						if (actor instanceof GlitchProjectile) {
							((Player) actedUpon).loseHP(25);
						}

						// ((Player) actedUpon).loseHP();
						// initiate method loseHP
						list.remove(actor);
						// remove the bullet
					} else if (actor instanceof AndroidBasicProjectile) {
						if (actedUpon instanceof Bot) {
							((Bot) actedUpon).loseHP(10);
							list.remove(actor);
							if (actedUpon instanceof ExploBotBaby) {
								// if explobotbaby gets hit by bullet
								((ExploBotBaby) actedUpon).explode(list);
								// die
								i--;
							}
							if (((Bot) actedUpon).isDead()) {
								kills++;

							}
						}
					} else if (actor instanceof AndroidMissile) {
						if (!(actedUpon instanceof NoClipBlock) || actedUpon == actor) {
							if (actedUpon instanceof ExploBotBaby) {
								((ExploBotBaby) actedUpon).explode(list);
								kills++;
							} else {
								list.remove(actor);
								list.add(new Explosion(explosion, actor.getCenterX(), actor.getCenterY(), 400, 400));
								ArrayList<MovingImage> temp = new ArrayList<MovingImage>(list);
								for (MovingImage exploded : ((AndroidMissile) actor).explode(list)) {
									temp.remove(exploded);
									if (exploded instanceof Bot)
										kills++;
									else if (exploded instanceof Rock) {
										((Rock) exploded).destroy();
										brokenRocks.add((Rock) exploded);
									}
								}
								list = temp;
							}
						}
					}
				}
			}
			if (actor instanceof Explosion) {
				if (((Explosion) actor).isExpired()) {
					list.remove(actor);
					i--;
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
		ArrayList<Rock> temp = new ArrayList<Rock>(brokenRocks);
		for (Rock rock : brokenRocks) {
			rock.act(list);
			if (!rock.isBroken() && !list.contains(rock) && !p1.intersects(rock)) {
				temp.remove(rock);
				list.add(rock);
			}
		}
		brokenRocks = temp;
	}

	/**
	 * Shoots AndroidBasicProjectile on mouse clicks.
	 */
	public void mousePressed() {
		if (gameState == 0 && !p1.isDead()) {
			Projectile proj = p1.shoot(mouseX, mouseY);
			if (proj != null)
				list.add(proj);
		} else if (gameState == -1) {
			if (start.isHovered(mouseX, mouseY)) {
				gameState = 0;
			} else if (info.isHovered(mouseX, mouseY)) {
				gameState = -2;
			}
		} else if (gameState == 1) {
			if (playAgain.isHovered(mouseX, mouseY)) {
				startGame();
				gameState = 0;
			}
		} else if (gameState == -2) {
			if (goBack.isHovered(mouseX, mouseY)) {
				gameState = -1;
			}
		}
	}

	/**
	 * Sets velocity with the WASD keys for player movement. Also shoots the
	 * AndroidMissile on Q press.
	 */
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_W) {
			keyW = true;
			p1.setvY(-5);
		}
		if (keyCode == KeyEvent.VK_A) {
			keyA = true;
			p1.setvX(-5);
		}
		if (keyCode == KeyEvent.VK_S) {
			keyS = true;
			p1.setvY(5);
		}
		if (keyCode == KeyEvent.VK_D) {
			keyD = true;
			p1.setvX(5);
		}
		if (keyCode == KeyEvent.VK_Q) {
			Projectile proj = p1.launchMissile(mouseX, mouseY);
			if (proj != null)
				list.add(proj);
		}
	}

	/**
	 * Setting velocity to 0 when WASD keys are released.
	 */
	public void keyReleased() {
		if (keyCode == KeyEvent.VK_W)
			keyW = false;
		if (keyCode == KeyEvent.VK_A)
			keyA = false;
		if (keyCode == KeyEvent.VK_S)
			keyS = false;
		if (keyCode == KeyEvent.VK_D)
			keyD = false;
	}

	/**
	 * Returns the border of the DrawingSurface
	 * 
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
		for (Rock rock : brokenRocks)
			rock.moveByAmount(-x, -y);
		double minX = Math.min(border.getX(), border.getX() + x);
		double maxX = Math.max(border.getX(), border.getX() + x);
		double minY = Math.min(border.getY(), border.getY() + y);
		double maxY = Math.max(border.getY(), border.getY() + y);
		for (MovingImage image : background) {
			image.moveByAmount(-x, -y);

			if (minX % 50 > maxX % 50)
				image.moveByAmount(Math.signum(x)*50, 0);
			if (minY % 50 > maxY % 50)
				image.moveByAmount(0, Math.signum(y)*50);
		}
	}

	private void startGame() {
		blind = 0;
		kills = 0;
		gameTime = 0;
		list = new ArrayList<MovingImage>();
		brokenRocks = new ArrayList<Rock>();
		p1 = new Player(android, WIDTH / 2, HEIGHT / 2, 42, 42);
		border = new Rectangle2D.Double(0, 0, MAP_SIZE * 50, MAP_SIZE * 50);
		list.add(p1);
		for (int x = -2; x < MAP_SIZE + 2; x++) {
			for (int y = -2; y < MAP_SIZE + 2; y++) {
				MovingImage block = new MovingImage(backdrop, x*50, y*50, 50, 50);
				background.add(block);
			}
		}
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				if (x <= 1 || x >= MAP_SIZE - 2 || y <= 1 || y >= MAP_SIZE - 2) {

					if (x == 1) {
						Block border = new Block(forcefieldR, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (x == MAP_SIZE - 2) {
						Block border = new Block(forcefieldL, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (y == 1) {
						Block border = new Block(forcefieldD, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (y == MAP_SIZE - 2) {
						Block border = new Block(forcefieldU, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (x == 0) {
						Block border = new Block(generatorR, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (x == MAP_SIZE - 1) {
						Block border = new Block(generatorL, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (y == 0) {
						Block border = new Block(generatorD, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if (y == MAP_SIZE -1) {
						Block border = new Block(generatorU, x * 50, y * 50, 50, 50);
						list.add(border);
					}
					if(x==0 && y == 0 || x==MAP_SIZE-1 && y == 0 ||x==0 && y == MAP_SIZE-1 ||x==MAP_SIZE-1 && y == MAP_SIZE-1) {
						Block border = new Block(generatorC, x * 50, y * 50, 50, 50);
						list.add(border);
					}
				} else {
					float chance = (float) Math.random();
					if (!p1.intersects(x * 50, y * 50, 50, 50) && chance < 0.05) {
						NoClipBlock gas = new NoClipBlock(toxicgas, x * 50, y * 50, 50, 50);
						list.add(gas);
					} else if (!p1.intersects(x * 50, y * 50, 50, 50) && chance < 0.15) {
						Rock block = new Rock(rock, x * 50, y * 50, 40, 40);
						list.add(block);
					}
				}
			}
		}
	}

	private String getTime(int frames) {
		String time = "";
		int seconds = frames / 60;
		int minutes = seconds / 60;
		int leftOver = (int) ((frames - seconds*60)/60.0 * 100);
		if (minutes > 0)
			time += minutes + ":";
		if ((seconds - minutes*60) < 10)
			time += "0";
		time += (seconds - minutes*60) + "." + leftOver;
		return time;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
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
}
