Doc link: https://docs.google.com/document/d/1pWuifOPnTdZae6PQmoeD0Ug-97zmLmkb3Gp7pD3ks5o/edit?usp=sharing
AP Computer Science Final Project - README Template


Instructions:
The first step in creating an excellent APCS final project is to write up a README. At this stage, this README file acts as your project proposal. Once you’ve filled in all components, Shelby will read through it and suggest edits. Ultimately, you need a document that adequately describes your project idea and we must agree on this plan.


Have one member of your group make a copy of this Google Doc. Then, they should share it with all other members so that every group member has edit permissions.


There’s a lot of parts of this document that you might not have full answers for yet. Because you haven’t written the program yet, it’s difficult to think about the instructions or which group members will do which parts. Even though this is hard to think about, you must have something in these sections that acts as your current plan. However, during the course of the project, you’ll continuously update this document. This means that you will not be held to exactly what you put here - components of this document can change (and it’s pretty common!).


There is one exception: the Features List section. Once Shelby OKs your README, the Features List section cannot be modified. For this reason, it is most important that you get a solid idea of what you want to make and the primary features it will have now.


Talk with your group. Consider drawing some pictures of what you think your project might look like. Be precise. When you’re ready, fill this out together. Each component in brackets below ( [these things] ) should be replaced with your ideas. Note that there are several sample READMEs posted on this assignment for you to use as guidance.


-------------------When README is finalized, remove everything above this line--------------------


Bot Farm[a]
Authors: Harry Guan, Michael Chen, Zackery He
Revision: 4/19/21


Introduction: 
[In a few paragraphs totaling about ½ page, introduce the high-level concept of your program. What this looks like depends a lot on what type of thing you are making. An introduction for an application will look different than one for a game. In general, your introduction should address questions like these:
What does your program do? 
What problem does it solve? Why did you write it? 
What is the story?
What are the rules? What is the goal?
Who would want to use your program?
What are the primary features of your program?]


Our program is a “Player versus Environment” game where the user controls a player and fights against waves of bots, with the objective being to survive as long as possible, or perhaps conquer the formidable foes. After a devastating robot takeover of the Planet X-69, there is a miniscule amount of human lifeforms remaining on the planet which the massive army of robots seek to snuff out. The robots are rapidly advancing in their combat prowess, and the rebels must wipe them out before they are unstoppable. 
The player has several basic stats such as mana, which can be used to cast abilities, and health points, which are self-explanatory. Bots will appear with several different types, which each have their own unique shot patterns, range, health, speed, and resistances. The bots will become increasingly more difficult, with stronger stats as the game progresses. Likewise, the loot that the enemies will reward will increase in rarity as well. The game will also have several different menus players can navigate around, such as the main menu, death screen, and pause screen. It further features a variety of specialized items with varying strength for the player to choose from as well as many graphics and sprites for them. The map will be randomly generated and expand onwards as the player navigates further on. The variety of items and classes one can choose from adds diversity to the game and creates a more immersive and enjoyable experience. As for the rules; they are simple: defeat all the bots and survive for as long as possible.
[b][c]
We are writing this so we can bring our unique idea to life and create something to do when bored. This game is for those who enjoy playing video games or people who have some free time and cannot figure out what to do.




Instructions:
[Explain how to use the program. This needs to be specific: 
Which keyboard keys will do what? 
Where will you need to click? 
Will you have menus that need to be navigated? What will they look like? 
Do actions need to be taken in a certain order?]


* WASD keys for movement (W up, A left, S down, D right)
* Q corresponding to the ability of the character
* Right click for basic attacks
* C and Z for rotating the screen
* Main menu screen with a concept display play button, difficulty bar, and settings (if we have time)
* A class list if we have more than one class
* Escape button in game shows a pause menu with resume and back to main menu buttons
* Death screen with score earned, back to main menu and play again buttons


Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
[These are features that we agree you will definitely have by the project due date. A good final project would have all of these completed. At least 5 are required. Each feature should be fully described (at least a few full sentences for each)]
* Map is generated before the game runs and has a world border which cannot be bypassed in any way. Maps would be randomly generated with terrain like toxic gas (which damages the player and enhances the enemy), rocks, and trees. 
* A player with hit points and mana. Contact with an enemy hitbox or projectile will cause the player to lose a set amount of hit points. Mana will be used to cast spells. Mana and HP will regenerate over time.
*  3 enemies with differing defensive and offensive stats with varying shots. One of the enemies will be immobile and spawn smaller enemies which run towards the player and explode into a ring of projectiles on collision/death. The other one will teleport to random locations within its previous locations every few seconds like a glitch, and has a directed attack with other secondary projectiles. The last enemy will have a damaging field surrounding it and will try to walk towards the player every few frames. The enemy will shoot a blinding projectile which does minimal damage and gives the screen an semi opaque black cover for 2 seconds.
* Player can move in all directions using the keyboard
*  Player can rotate the screen clockwise and counterclockwise using the keyboard.
*  A nice player interface with title screen and interactive buttons.
* The class of the character is called an android, which has one ability; a ranged missile launcher which launches upon Q being pressed and the basic attack (right click) will be a plasma pistol which is single target but faster attack speed.
Want-to-have Features:
[These are features that you would like to have by the project due date, but you’re unsure whether you’ll hit all of them. A good final project would have perhaps half of these completed. At least 5 are required. Again, fully describe each.]
* Different difficulty levels. Each difficulty level may include different enemies, ramp up damage or change up the attack patterns.
* A minimap that will show terrain and enemies around the player.
* Boss battles with a stronger enemy. Could have more attack patterns or hitpoints. 
* 2D randomly generated environments that will be different every time the game is played.
* Custom items that further boost stats.


Stretch Features:
[These are features that we agree a fully complete version of this program would have, but that you probably will not have time to implement. A good final project does not necessarily need to have any of these completed at all. At least 3 are required. Again, fully describe each.]
*  Level structure. The player will gain exp as it defeats enemies. It will gain more abilities, or stats. 
*  Character customization. Defeating enemies or bosses could drop items such as weapons. Different classes or archetypes with different stats and abilities.
* Auto generating terrain. The map would randomly infinitely generate as the player moves further from its initial position[d][e]




Class List:
[This section lists the Java classes that make up the program and very briefly describes what each represents. [f]It’s totally fine to put this section in list format and not to use full sentences.]
[g]
* Projectile - represents a projectile[h]
* NoClipProjectile - a projectile that will not be interrupted by blocks
* Player - represents the player
* Android - one of the classes of the player, has a missile launcher and a plasma pistol 
* Bot - represents all the enemies the player fights
* Drawingsurface - draws everything
* Block - one tile of the terrain[i] that can not be passed through
* NoClipBlock - a tile of the terrain which can be passed through (toxic gas, for example) 
* Map - minimap
* MovingImages - describes the movement of an object
Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
* List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
   * Have done:
   * Harry - Created all of the classes and led the creative process. 
   * Michael - UML work, created Javadocs and Jar
   * Zackery - UML work, update the README in the project file, the note taker for the group (writes down the updates and ideas we have for the project in README)
   * * Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]
   * N/A for now


[a]I think this is a very distinct Idea, it has a lot of potential and I've never played a game like this before. In my opinion this is a very good and cool idea.
[b]Is there a way to win the game?
[c]No. There will be a points/time system that will give the player a sense of progression
[d]Isn't that the same as one of the want to have features, because generating the environment will be the same as making a different one every time you play
[e]This is different. The want to have feature only creates a predetermined size of terrain and the player can only move there
[f]Where will you put the enemies, because you also need to add a class to represent the bot
[g]I think you'd need a class to represent your mini-map.
[h]Wouldn't you need more classes for different projectiles?
[i]If you want to have different kinds of terrain(impassable objects like rocks and walls, etc), you might want to build some subclasses for this.