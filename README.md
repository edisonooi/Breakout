# Breakout

This project implements the original Atari Breakout game, with customizable levels, special 
powerups, and cheat keys. Built from scratch in JavaFX.

## Features

### Customizable Levels
Brick configurations for each level are fully customizable. By editing the level config files in 
the resources folder, you can place whatever type of brick you want, wherever you want within 
the grid.

### Multi-directional Paddles
Paddles can move not just horizontally, but also vertically. In extreme levels, there are 
four paddles in play at once, with one on each side of the screen. Paddle control is done using 
arrow keys. Horizontal paddles are controlled with left and right, while vertical paddles use up and down.

### Powerup Bricks
Some bricks, when broken, will give you a powerup (or anti-powerup!) such as spawning a new ball,
making your paddle longer/faster, or making your paddle invisible.

### Cheat keys
* 1-9 - Skip to level with that number, or highest level if number is higher than highest level.
* R - Reset current level including accumulated points.
* C - Advance to the next level, or end the game with a win if already on final level.
* L - Gives player an extra life for this level.
* T - Doubles paddle speed for certain amount of time. Can only be used once per level.
* S - Halves ball speed for certain amount of time.




