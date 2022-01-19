# Breakout
## Edison Ooi


DO NO FORK THIS REPOSITORY, clone it directly to your computer.


This project implements the game of Breakout.

### Timeline

Start Date: January 9, 2022

Finish Date: January 18, 2022

Hours Spent: 25

### Resources Used
Used this StackOverflow post, written by user Slaw, as reference on how to use
KeyFrames to cause a task to happen after a certain delay. This was useful for
specifying powerup durations.

### Running the Program

Main class: Main.java

Data files needed: level1config.txt, level2config.txt, level3config.txt, all in
resources folder.

Key/Mouse inputs: Paddle control is done using arrow keys. Horizontal paddles
are controlled with left and right, while vertical paddles use up and down.

Cheat keys:
* 1-9 - Skip to level with that number, or highest level if number is higher than highest level.
* R - Reset current level including accumulated points.
* C - Advance to the next level, or end the game with a win if already on final level.
* L - Gives player an extra life for this level.
* T - Doubles paddle speed for certain amount of time. Can only be used once per level.
* S - Halves ball speed for certain amount of time.

### Notes/Assumptions

Assumptions or Simplifications: 
* Only NormalLevels (levels 1 and 2) support the extra ball powerup, and they only
support at most one extra ball.
* Level layouts are more or less hard-coded, except for the region in which
bricks are placed and their configuration/types.
* Level config file names must follow the format "level{level number}config.txt"
* Level config files must have a rectangular configuration of bricks. 0's can be
entered in place of empty space.

Known Bugs: 
* If ball goes off screen while it is slowed, it will have double speed when it
respawns and the powerup expires.
* If a ball hits the corner of a Rectangle, it may phase through or remain attached
to its sides.

Extra features or interesting things we should not miss:
* As long as you follow the assumptions listed above, you can modify the config
files to create any brick configuration of any size you want.
* Brick color corresponds to its durability, and a colored outline represents
a powerup brick.


### Impressions
This assignment was a fun, lightweight introduction to larger scale software
design as well as JavaFX as a whole. I revisited a lot of old knowledge about
pass by reference/value, inheritance, abstractions, and enumerations. If I were
to change anything I would probably decrease the number of cheat keys needed, because
in my opinion having 6 cheat keys instead of, say, 3 or 4, doesn't prove anything
more about how well-written or dynamic your code is than you reasonably see just
from scanning through the code.


