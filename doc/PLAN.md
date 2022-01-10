# Breakout Plan
### Edison Ooi

#### Examples

You need to put blank lines to write some text

in separate paragraphs.


Emphasis, aka italics, with *asterisks* or _underscores_.

Strong emphasis, aka bold, with **asterisks** or __underscores__.

Combined emphasis with **asterisks and _underscores_**.


You can also make lists:
* Bullets are made with asterisks
1. You can order things with numbers.


You can put links in like this: [Duke CompSci](https://www.cs.duke.edu)



## Interesting Breakout Variants

I thought vortex was particularly interesting because it flipped the basic
geometry of the Breakout game. It also presents the challenge of having
to account for all 360 degrees of possible directions the ball could fly
away vs just one side for the normal variant.

I also thought Super Breakout was interesting because of the introduction
of powerups or challenges, such as having multiple balls. I particularly
like the idea of multiple balls because it introduces a new degree of
difficulty because of having to keep more balls alive, but also provides
a game advantage by being able to break more bricks in a short time. Having
at least 2 balls is something I definitely would want to incorporate into
my own variation of Breakout.

## Paddle Ideas

 * Toggle a "catch" mode where the paddle will catch and hold the
ball when in catch mode and bounce like normal when not in catch mode.

 * Moving the paddle to the opposite side of the screen when it
moves past the opposite edge


## Block Ideas

 * Blocks that require a varying amount of hits to be destroyed

 * Blocks that activate certain powerups listed below

 * Blocks that slow down the ball temporarily when hit
 
## Power-up Ideas

 * Spawn an extra ball

 * Make paddle longer

 * Make your paddle invisible for a few seconds


## Cheat Key Ideas

 * Press a key to give an extra life (maximum once per game)

 * Press a key to reset the level 

 * Press a key to clear the bottom-most layer of bricks (maximum
once per level)

 * Press a key to slow the ball down for a few seconds (maximum
once per level)


## Level Descriptions

###Level 1
    5 5 5 5 5 5 5 5
    
    4 4 4 4 4 4 4 4
    
    3 3 3 B B 3 3 3
    
    2 2 2 2 2 2 2 2
    
    1 1 1 1 1 1 1 1
    
    0 0 0 0 0 0 0 0 

Basic version of Breakout, the number represents a block that takes
that number of hits to break. The B represents a brick which, when
broken, spawns another ball. The player won't lose a life until
all balls go off the screen. The 0's represent the empty space where the
ball and paddle will spawn.

###Level 2
    0 0 0 3 3 0 0 0
    
    0 0 I L L I 0 0
    
    0 2 2 2 2 2 2 0
    
    2 2 2 2 2 2 2 2
    
    1 1 1 1 1 1 1 1
    
    0 0 0 0 0 0 0 0

Same brick layout rules as the previous level. L is a brick with
a longer paddle powerup. I is a brick which makes the paddle become
invisible for a few seconds. 0's are just empty space. The paddle
and balls will still spawn at the bottom of the scene.

###Level 3
             Paddle
    
       0 0 0 0 0 0 0 0 0 
    
       0 0 0 0 0 0 0 0 0 
    
    P  0 0 0 0 1 0 0 0 0  P
    
    A  0 0 0 1 2 1 0 0 0  A
     
    D  0 0 1 2 I 2 1 0 0  D
    
    D  0 0 0 1 2 1 0 0 0  D
    
    L  0 0 0 0 1 0 0 0 0  L
    
    E  0 0 0 0 0 0 0 0 0  E
    
       0 0 0 0 0 0 0 0 0

             Paddle
This level will have four paddles on all sides of the scene. In
order to pass this level, all bricks will have to be broken. The
top and bottom paddles will always have the same X coordinates and
both be controlled by the left and right arrow keys. The same rule
applies to the left and right paddles, controlled by the up and
down arrow keys.

## Class Ideas

###Level
* Represents a single level in the game, storing block array,
applicable cheat keys, etc.
* reset() method which would reset the level back to its
original state before any blocks were broken

###Brick
* Represents one breakable (or unbreakable) brick in a level,
stores information on how many hits are needed to break and
which powerups are offered upon breaking
* hit() method which would perform the appropriate action to
the brick when it is hit, either destroying the brick or decreasing
its number of remaining hits by 1

###Ball
* Represents a ball that is being bounced around in a level,
stores information on its position, velocity, etc.
* Methods getX(), getY(), setX(), setY() to control its movement

###Scoreboard
* Represents and encapsulates the information that the in-game
scoreboard will present, such as number of lives remaining,
current score, current level, active powerups, etc.
* reset() method to reset all level statistics back to the
original state of the level


