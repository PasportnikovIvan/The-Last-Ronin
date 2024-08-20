## The game platformer is written in Java, using swing libraries for GUI and JDK version 16 without any external libraries. More on [WIKI](https://github.com/PasportnikovIvan/The-Last-Ronin/wiki) page.

### Things to need to create the game:
- GameWindow 
- Gameloop
- Inputs
- Animations
- Levels
- Line of Sight
- Enemies
- Collisions
- Event handling
- Rendering
- Sound

One by one they were added to the game. The _GameWindow_ allows to see everything. Then the _GameLoop_ was added, using the `Thread` the game got a good one loop, and also thanks to saving the DeltaTime the game got smoother FPS. _Animations_ and _sprites/atlas_ are the key of the game, they are giving a lot more charm to the game! Also _Sounds_ were added to the game for making the game more attractive. _Sounds_ were added without any external libraries, using the `Java Clip`, interpreting it as sound. _Levels_ are addes by the specific way - as a `Image`, which allows to loop throug the _level_ once in method and for each pixel test it and load _LevelData_, _Entities_ and _Objects_.

#### _The rest of the documentation is represented by numerous comments in the code, unfortunately JavaDoc does not generate the whole documentation with a description the way I wanted :disappointed:._

## The following classes were used:

## Main Group:
1.1. `Game`:
- Description: This class serves as the central component of the game, responsible for managing the game loop, updating the game state, and rendering the graphics.
- Responsibilities: Game loop management, game state updates, graphics rendering.

1.2. `GamePanel`:
- Description: This class is responsible for the game screen and extends JPanel. It handles the drawing of game graphics on the screen.
- Responsibilities: Drawing game graphics on the screen.

1.3. `GameWindow`:
- Description: This class is responsible for creating the game window and extends JFrame. It provides the graphical user interface (GUI) for the game, including window settings, such as title, border, size, and components like buttons and text fields.
- Responsibilities: Game window creation, GUI management.

1.4. `MainClass`:
- Description: This class holds the main method of the game. It initializes the game and starts the game loop.
- Responsibilities: Initialization and starting of the game.

## Inputs Group:
2.1. `MouseInputs`:
- Description: This class handles mouse inputs and ensures appropriate responses to mouse events, such as left-clicks and right-clicks.
- Responsibilities: Mouse input handling.

2.2. `KeyboardInputs`:
- Description: This class handles keyboard inputs and ensures appropriate responses to keyboard events, such as movement using keys like A, D, and spacebar.
- Responsibilities: Keyboard input handling.

## Audio Group:
3.1. `AudioPlayer`:
- Description: This class handles audio in the game, allowing for the playback of sound effects and music.
- Responsibilities: Audio management.

## Effects Group:
4.1. `EmotionEffect`:
- Description: This class provides simple emotional effects for enemies when they reach the wall or edge while attacking.
- Responsibilities: Adding emotional effects to enemies.

4.2. `Sakura`:
- Description: This class handles the visual effects of sakura particles in the game.
- Responsibilities: Adding sakura particle effects.

## Entities Group:
5.1. `Enemy`:
- Description: This abstract class defines common properties and behaviors for all enemies in the game. It provides methods for updating and rendering enemy entities.
- Responsibilities: Enemy behavior definition, updating, and rendering.

5.2. `EnemyManager`:
- Description: This class manages the enemies in the game. It has a similar role to the LevelManager but specifically handles enemy-related functionality.
- Responsibilities: Enemy management.

5.3. `Entity`:
- Description: This abstract class serves as the base class for all entities in the game. It provides common methods for updating and rendering entities.
- Responsibilities: Entity behavior definition, updating, and rendering.

5.4. `FastMob`:
- Description: This class represents a specific type of enemy called "FastMob" and extends the Enemy class.
- Responsibilities: FastMob-specific behavior.

5.5. `Goblin`:
- Description: This class represents a specific type of enemy called "Goblin" and extends the Enemy class.
- Responsibilities: Goblin-specific behavior.

5.6. `Mob`:
- Description: This class represents a specific type of enemy called "Mob" and extends the Enemy class.
- Responsibilities: Mob-specific behavior.

5.7. `Player`:
- Description: This class represents the player character in the game and extends the Entity class.
- Responsibilities: Player-specific behavior.

## Gamestates Group:
6.1. `Credits`:
- Description: This class represents the game credits in the gamestates. It implements the Statemethods interface.
- Responsibilities: Displaying game credits, implementing game state methods.

6.2. `GameOptions`:
- Description: This class represents the game options in the gamestates. It provides access to sound controls and implements the Statemethods interface.
- Responsibilities: Handling game options, implementing game state methods.

6.3. `Gamestate`:
- Description: This enum class stores data corresponding to different game states, such as Menu, Playing, LvlSelect, and Settings.
- Responsibilities: Managing game state data.

6.4. `Menu`:
- Description: This class represents the game menu in the gamestates. It implements the Statemethods interface.
- Responsibilities: Managing the game menu, implementing game state methods.

6.5. `Playing`:
- Description: This class represents the playing state in the gamestates. It implements the Statemethods interface.
- Responsibilities: Managing the playing state, implementing game state methods.

6.6. `State`:
- Description: This class serves as the superclass for all game states. It provides common functionality and methods for game state management.
- Responsibilities: Game state management.

6.7. `Statemethods`:
- Description: This interface defines a collection of methods that each class implementing it must have. It ensures that all implementing classes have the required methods.
- Responsibilities: Defining required methods for game state classes.

## Levels Group:
7.1. `Level`:
- Description: This class stores data related to a specific level in the game, including its image and other properties.
- Responsibilities: Storing level data.

7.2. `LevelManager`:
- Description: This class manages the levels in the game. It handles loading and transitioning between levels.
- Responsibilities: Level management.

## Objects Group:
8.1. `Archer`:
- Description: This class represents the archer object in the game, which acts as a static enemy. It extends the GameObject class.
- Responsibilities: Archer-specific behavior.

8.2. `BackgroundBamboo`:
- Description: This class represents the objects of drawn bamboo and sakura in the game levels for aesthetic purposes.
- Responsibilities: Handling bamboo and sakura objects.

8.3. `GameContainer`:
- Description: This class serves as a container for objects such as boxes and barrels in the game. It extends the GameObject class.
- Responsibilities: Object container management.

8.4. `GameObject`:
- Description: This class is the superclass for both potions and containers in the game. It provides common properties and behaviors for game objects.
- Responsibilities: Game object management.

8.5. `Grass`:
- Description: This class represents the objects of drawn grass in the game levels for aesthetic purposes.
- Responsibilities: Handling grass objects.

8.6. `ObjectManager`:
- Description: This class handles the management of game objects, including adding, removing, and updating them.
- Responsibilities: Game object management.

8.7. `Potion`:
- Description: This class represents the potion objects in the game, including red and blue potions. It extends the GameObject class.
- Responsibilities: Potion-specific behavior.

8.8. `Projectile`:
- Description: This class represents the projectile objects in the game, specifically arrows.
- Responsibilities: Handling projectile objects.

8.9. `Spike`:
- Description: This class represents the spike trap objects in the game. It extends the GameObject class.
- Responsibilities: Spike-specific behavior.

## UI Group:
9.1. `AudioOptions`:
- Description: This class represents the audio options interface in the game. It is connected to the options and pause screens and manages sound controls.
- Responsibilities: Managing audio options.

9.2. `GameCompletedOverlay`:
- Description: This class represents the overlay menu displayed when the game is completed.
- Responsibilities: Managing the game completed overlay menu.

9.3. `GameOverOverlay`:
- Description: This class represents the overlay menu displayed when the game is over.
- Responsibilities: Managing the game over overlay menu.

9.4. `LevelCompletedOverlay`:
- Description: This class represents the overlay menu displayed when a level is completed.
- Responsibilities: Managing the level completed overlay menu.

9.5. `MenuButton`:
- Description: This class represents the buttons in the game menu. It stores different images for each button.
- Responsibilities: Managing menu buttons.

9.6. `PauseButton`:
- Description: This superclass represents the buttons used in the pause menu. It stores position and hitbox information.
- Responsibilities: Managing pause menu buttons.

9.7. `PauseOverlay`:
- Description: This class represents the pause overlay menu in the game.
- Responsibilities: Managing the pause overlay menu.

9.8. `SoundButton`:
- Description: This class represents the sound buttons in the pause menu. It extends the PauseButton class.
- Responsibilities: Managing sound buttons in the pause menu.

9.9. `UrmButton`:
- Description: This class represents the UnPause, Replay, and Menu buttons in the pause menu. It extends the PauseButton class.
- Responsibilities: Managing UnPause, Replay, and Menu buttons in the pause menu.

9.10. `VolumeButton`:
- Description: This class represents the volume slider button in the pause menu. It extends the PauseButton class.
- Responsibilities: Managing the volume slider button.

## Utils Group:
10.1. `Constants`:
- Description: This class provides game constants and other constant classes within it.
- Responsibilities: Managing game constants.

10.2. `HelpMethods`:
- Description: This class provides helper methods for various operations in the game. It contains static methods that can be used beyond the player.
- Responsibilities: Providing helper methods.

10.3. `LoadSave`:
- Description: This class handles loading and saving data in the game.
- Responsibilities: Managing data loading and saving.

## `Resources` _Folder_:
The resources folder contains the images used in the game, including game sprites, atlases, and two additional folders:

`lvls`: This folder stores the images with level data.
`audio`: This folder contains all the sounds used in the game.

## This documentation provides an overview of the classes and groups involved in developing the platformer game, along with additional information about the game's resources folder.

![image](https://github.com/user-attachments/assets/07dba13f-2f85-440f-892f-9b9cb3c8a4fc)
