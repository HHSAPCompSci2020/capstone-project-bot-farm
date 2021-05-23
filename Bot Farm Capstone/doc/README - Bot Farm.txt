Bot Farm
Authors: Harry Guan, Michael Chen, Zackery He
Revision: 4/19/21


Introduction: 


Our program is a “Player versus Environment” game where the user controls a player and fights against waves of bots, with the objective being to survive as long as possible, or perhaps conquer the formidable foes. After a devastating robot takeover of the Planet X-62, there is a miniscule amount of human lifeforms remaining on the planet which the massive army of robots seeks to snuff out. The robots are rapidly advancing in their combat prowess, and the rebels must wipe them out before they are unstoppable. 


The player has two basic stats: mana, which can be used to cast abilities, and health points, which are self-explanatory. Bots will appear with several different types, which each have their own unique shot patterns, range, health, speed, and resistances. The game will also have several different menus players can navigate around, such as the main menu and death screen. The map will be randomly generated when the game starts. As for the rules; they are simple: defeat all the bots and survive for as long as possible.


We are writing this so we can bring our unique idea to life and create something to do when bored. This game is for those who enjoy playing video games or people who have some free time and cannot figure out what to do.




Instructions:


* WASD keys for movement (W up, A left, S down, D right)
* Q corresponding to the ability of the character
* Mouse click for basic attacks
* Main menu screen with a concept display play button
* Death screen with score earned


Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
* Map is generated before the game runs and has a world border which cannot be bypassed in any way. Maps would be randomly generated with terrain like toxic gas (which damages the player and enhances the enemy), rocks, and trees. 
* A player with hit points and mana. Contact with an enemy hitbox or projectile will cause the player to lose a set amount of hit points. Mana will be used to cast spells. Mana and HP will regenerate over time.
*  3 enemies with differing defensive and offensive stats with varying shots. One of the enemies will be immobile and spawn smaller enemies which run towards the player and explode into a ring of projectiles on collision/death. The other one will teleport to random locations within its previous locations every few seconds like a glitch, and has a directed attack with other secondary projectiles. The last enemy will have a damaging field surrounding it and will try to walk towards the player every few frames. The enemy will shoot a blinding projectile which does minimal damage and gives the screen an semi opaque black cover for 2 seconds.
* Player can move in all directions using the keyboard
* Player can rotate the screen clockwise and counterclockwise using the keyboard. Note: didn't finish
*  A nice player interface with title screen and interactive buttons.
* The class of the character is called an android, which has one ability; a ranged missile launcher which launches upon Q being pressed and the basic attack (right click) will be a plasma pistol which is single target but faster attack speed.
Want-to-have Features:
* Different difficulty levels. Each difficulty level may include different enemies, ramp up damage or change up the attack patterns.
* A minimap that will show terrain and enemies around the player.
* Boss battles with a stronger enemy. Could have more attack patterns or hitpoints. 
* 2D randomly generated environments that will be different every time the game is played.
* Custom items that further boost stats.


Stretch Features:
*  Level structure. The player will gain exp as it defeats enemies. It will gain more abilities, or stats. 
*  Character customization. Defeating enemies or bosses could drop items such as weapons. Different classes or archetypes with different stats and abilities.
* Auto generating terrain. The map would randomly infinitely generate as the player moves further from its initial position




Class List:
* Projectile - represents a projectile
* AndroidBasicProjectile - the basic attack projectile from the character
* BlindProjectile - projectile shot by the blind bot
* GlitchProjectile - projectile shot by glitch bot
* ExploBotBabyProjectile - projectile shot by the ExploBotBaby bot
* AndroidMissile - the android ability (missile launcher)


* Player - represents the player
* Android - one of the classes of the player, has a missile launcher and a plasma pistol 
* Bot - represents all the enemies the player fights
* MovingImage - describes the movement of an object
* BlindBot - the enemy that blinds
* ExploBot - the enemy that spawns exploding entities
* ExploBotBaby - the exploding entities spawned by ExploBot
* GlitchBot - the enemy which glitches around 


* DrawingSurface - draws everything
* Main - runs the program
* Button - represents a button and is able to tell if the mouse is on it


* Block - one tile of the terrain that can not be passed through
* NoClipBlock - a tile of the terrain which can be passed through (toxic gas, for example) 
* Rock - Represents a rock on the map which can be respawned when broken by a missile


Credits:
* List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
   * Harry - README work, MovingImage, DrawingSurface, Main, creative process, GlitchBot, GlitchBotProjectile, all graphics, map reading, sound, most Javadoc documentation
   * Michael - UML work, created Javadocs and Jar, Bot class, BlindBot, BlindProjectile, side scrolling. auto generating terrain, graphics quality increase, button glow, pathfinding
   * Zackery - README work, Player changes, ExploBot, ExploBotBaby, ExploBotBabyProjectile, menu features, audio loop and file research, game balance
* Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]
   * Previous project by Harry assisted in DrawingSurface,  MovingImage, and Player. 
   * ProcessingSoundLibrary from the Google Drive Demos
   * How to control volume of audio Clip | web development helpdesk (objects.com.au)
   * https://www.geeksforgeeks.org/play-audio-file-using-java/ 
   * Space Trip - Royalty Free Music (dl-sounds.com)