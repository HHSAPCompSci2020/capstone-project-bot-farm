import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.util.Scanner;

public class DrawingSurface extends PApplet implements MouseListener {

    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static PImage explob, explobb, glitchb, blindb, explobullet, glitchbullet, blindbullet, 
    androidbullet, rock, toxicgas, cursor;
    private final Player p1;
    public boolean gameStarted;

    private final ArrayList<MovingImage> list; //This ArrayList stores every single object represented on screen.
    private int spawnRate;
    private int kills;

    public DrawingSurface() { //Initializes every field, creating images and objects, adding them to the list.
    	explob = loadImage("../assets/explob.png");
    	explobb = loadImage("../assets/explobb.png");
    	explobullet = loadImage("../assets/explobbbullet.png");
    	glitchb = loadImage("../assets/glitchb.png");
    	blindb = loadImage("../assets/blindb.png");
    	rock = loadImage("../assets/rock.png");
    	glitchbullet = loadImage("../assets/glitchbbullet.png");
    	blindbullet = loadImage("../assets/blindbullet.png");
    	androidbullet = loadImage("../assets/androidbullet.png");
    	cursor = loadImage("../assets/cursor.png");
    	toxicgas = loadImage("../assets/toxicgas.png");
    	p1 = new Player(loadImage("../assets/explob.png"), 270, 550, 42, 42);
    	list = new ArrayList<MovingImage>();
    	list.add(p1);
        gameStarted = false;
    }

    public void setup() {
        size(WIDTH, HEIGHT);
        this.frameRate(60);
        cursor(cursor, 20, 20);
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

    public void draw() {
        background(255, 255, 255);
        if (gameStarted) {
            //Under this comment, draw every MovingImage in list.
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

    public void spawnEnemy() {
        //Spawns the enemy.
        //You may spawn the enemy however you like for as many times as you'd like.
        //To spawn the enemy, you would do list.add(new Enemy([parameters]);.
        //It is recommended that you randomize the x coordinate (Math.random() * 200).
        //It is also recommended that you spawn multiple enemies with a loop.
        for (int i = 0; i < 1; i++) {
            int enemyX = (int) (Math.random() * WIDTH);
            int enemyY = (int) (Math.random() * (HEIGHT / 2));
            //add enemies
            list.add(new BlindBot(blindb, enemyX, enemyY, 50, 50, 100));
        }
    }

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
                        //if player gets hit by bullet
                        ((Player) actedUpon).loseHP();
                        //initiate method loseHP
                        list.remove(actor);
                        //remove the bullet
                        i--;
                    } else if (actedUpon instanceof ExploBotBaby) {
                        //if explobotbaby gets hit by bullet
                        list.remove(actor);
                        //remove bullet
                        ((ExploBotBaby) actedUpon).die();
                        //die
                        i--;
                        //need to implement explosion
                        kills++;
                    }
                }
                }
                if (actor instanceof Player && actedUpon instanceof Bot) {
                    list.remove(actedUpon);
                    ((Player) actor).loseHP();
                    //lose hp
                    i--;
                }
                if (actor == actedUpon) {
                    list.remove(actedUpon);
                    i--;
                }
            }
        }
    

    public void mousePressed() {
        if (gameStarted) {
            list.add(p1.shoot(mouseX, mouseY));
        } else {
            if (mouseX < 500 && mouseX > 350 && mouseY < 400 && mouseY > 50) {
                gameStarted = true;
            }
        }
    }

    /*
        if (keyCode == KeyEvent.VK_W) {
            p1.setvY(-5);
            up.resize(42, 42);
            p1.image = up;
        }
        if (keyCode == KeyEvent.VK_A) {
            p1.setvX(-5);
            p1.image = left;
        }
        if (keyCode == KeyEvent.VK_S) {
            p1.setvY(5);
            p1.image = down;
        }
        if (keyCode == KeyEvent.VK_D) {
            p1.setvX(5);
            right.resize(42, 42);
            p1.image = right;
        }

    } */
    @Override
    public void keyPressed() {
    	if (keyCode == KeyEvent.VK_W) {
            p1.setvY(-5);
//            up.resize(42, 42);
//            p1.image = up;
        }
        if (keyCode == KeyEvent.VK_A) {
            p1.setvX(-5);
            //p1.image = left;
        }
        if (keyCode == KeyEvent.VK_S) {
            p1.setvY(5);
            //p1.image = down;
        }
        if (keyCode == KeyEvent.VK_D) {
            p1.setvX(5);
            ///right.resize(42, 42);
            //p1.image = right;
        }
    }

    public void keyReleased() {
        //Create four if statements, W/A/S/D, that sets the player (p1)'s velocity x or y to 0.
        if (keyCode == KeyEvent.VK_W) {
            p1.setvY(0);
        }
        if (keyCode == KeyEvent.VK_A) {
            p1.setvX(0);
        }
        if (keyCode == KeyEvent.VK_S) {
            p1.setvY(0);
        }
        if (keyCode == KeyEvent.VK_D) {
            p1.setvX(0);
        }

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

}
