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


 * Idea #2

 * Idea #3


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


