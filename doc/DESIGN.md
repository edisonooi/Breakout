# Breakout Design
## Edison Ooi


## Design Goals
One of my favorite features that I added was the ability to make any kind of brick
configuration you want just by creating a rectangular grid of characters in a text
file. Of course, the characters had to be within a certain set in order to be
translated into valid brick types. I also wanted to make it easier to add new
level designs, such as extra paddles moving in different directions, so that is why
I created an abstract Level class that could handle its own step, key input, and
rendering functions.


## High-Level Design
The Breakout class is essentially the conductor, creating and playing levels and
switching between them if necessary. It also creates its Scene and Group that serves
as the basis of all graphics for the entire lifespan of the game.
The Level class and its subclasses are in charge of actually rendering and keeping
track of all the elements on screen, except for the Scoreboard, which keeps track
of the total score of the game. Levels maintain their own set of Bricks, Balls,
and Paddles.


## Assumptions or Simplifications
All functionality in classes assumes that an instance of Breakout exists and has
been set up properly, and all gameplay interactions assume that at least one Level
exists and is being rendered. One major simplification is the fact that not all
Powerups are actually functional on all Levels. For example, extra balls cannot
exist in ExtremeLevels. Also, it is assumed that all instances of NormalLevel have
the same "layout" outside of the brick configuration, same with ExtremeLevel.


## Changes from the Plan
* Instead of adding a "catch" mode to the paddle, I added powerups that allow
the paddle to be wider and to move faster. This was because I felt that if the
paddle already had to be hitting the ball to "catch" it, that wouldn't provide any
real benefit in terms of saving lives.
* Instead of having a brick that slows the ball down when hit, I just made that
a cheat key.
* I did not implement a cheat key to clear the bottom-most layer of bricks,
simply because I ran out of time and could not figure out a good way to do it.


## How to Add New Levels
It is pretty easy to add new Levels of the layouts that already exist in my game.
All you need to do is create a config file with correct name format, and create an
instance of the level in Breakout.goToLevel(). If you wanted to add a completely
new level layout, you would have to extend Level and override all of its abstract
methods, notably setting up all of the components and handling certain powerups.

