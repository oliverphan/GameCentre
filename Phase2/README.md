## Git Account Mapping
+ *Name of Contributor*: *Git username1*, *Git user email1*; *Git username2*, *Git user email2*; *Git username3*, *Git user email3*;
+ Jack Hang: jackjs.huang; jaskjs.huang@mail.utoronto.ca
+ Savhanna McLellan: SavhannaMcLellan, savhanna.mclellan@mail.utoronto.ca;
+ Joanthan Oxman: shade_of_ox, cheezmo42@gmail.com; oxmanjon, oxmanjonathan@gmail.com;
+ Oliver Phan: Oliver Phan, oliver.phan@mail.utoronto.ca; Oliver Phan, phanoliv@teach.cs.toronto.edu;
+ Meirbek Zeinulla: Meirbek, meirbek.zeinulla@mail.utoronto.ca; zeinulla, meirbek.zeinulla@mail.utoronto.ca;


# Setup Instructions for group_0654 CSC207 Phase2

## Cloning the repo
---
1. Open Android Studio and close any open projects. (File -> Close Project)
2. Click "Check out project from Version Control" from Git.
3. Paste to URL Box: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0654
4. Select the destination directory and press "Clone".


## Building the Project
---
1. As soon as you press Clone, press "Yes" to create an Android Studio Project.
2. Select "Import project from external model", choosing the non-Android Gradle model and click Next.
3. Add "\Phase1\GameCentre" to the Gradle project path, and make sure to use "Explicit Module Groups" and the "Default Gradle Wrapper". Press Finish.
4. If the project file already exists, overwrite it.
5. Press "Ok" on the Sync Android SDKs.
6. There may be a popup to say "Unregistered VCS root detected. Press "Add root".
7. Do not add GameCentre.iml to Git.
8. If caches are not being created properly, run the following command in the terminal:
 + cd $HOME/.gradle/caches
 + rm -rf $HOME/.gradle/caches, then re-open Android Studio

## Scoreboard
---
+ Design of scoreboard:
 + A user's score will not appear on the scoreboard if it is 0 (or if the user has never played the game).
 + A user's score will show up in the "your high score" field with the user's top score (or if the user has never played, it will show up as 0).
 + The scoreboard displays the 5 top scores in each game. 
+ Implementation of scoreboard
 + The leaderboard is stored in a HashMap mapping game names to ArrayLists of Score objects.
 + The leaderboard is updated every time a game finishes (is won or lost). 
 + When the leaderboard is updated, the new score is compared to all other scores currently stored in the leaderboard.
   If the new score is higher than a score already on the leaderboard, it is added to the leaderboard and all scores
   under it are shifted down one place.
 + The LeaderBoard object which stores the entire leaderboard is saved to a file. This file is loaded and saved whenever
   the leaderboard is updated. 

## Most Important Classes
---
+ Abstraction of Board, BoardManager, Token
+ BoardManager for both games & where the game logic is
+ Saving and loading interfaces
+ Leaderboard (Name for the "scoreboard")
 + Score class used to manage User scores in the Leaderboard
 + A toString method in the Score class to display the Scores in the Leaderboard

## Design Patterns Used
---
+ MVC
+ Iterator
 + For Abstract Tokens in Abstract Board
+ Observer
 + The Board Abstract class is being observed, the GameActivity is the observer
 
## Other Functionalities Implemented
---
### Menus
+ Login and Sign in using username and password
+ On the game selection screens, the User can swipe left or right to choose a different game to play
  (The White scroll bar in the Top Display Bar will indicate the position in the menu)
 + On the Leaderboard screen similar functionality exists (pressing back on the Leaderboard will take you to
   game selection screen for the corresponding game)
 + Both functionalities made possible by using Android Fragments
  + Android Fragments managed by SectionPageAdapters (which are children of FragmentStatePagerAdapter)

### Loading Games
+ The User is able to:
 + Save their games (games are saved automatically when the user returns to the login screen).
 + Load their saved games
 + See that they're signed in when they're choosing a game to play
+ If the User does not have a save available for the particular game, the "Load Button" is greyed out and unclickable


### Gameplay
+ Autosave and undo button for the SlidingTiles and MatchingCards game
 + On deafult without specification, the undo button will only undo one move
 + In the MatchingCards game, only one undo is provided per game, and can only be used when the User is selecting a second card to be matched
  + Reason: Using an undo when there are no cards being matched would give no visual changes, and giving too many undos would allow the User to effectively cheat
    through the game
  + Once used, the "Undo Button" will be greyed out and unclickable
 + Autosave is done after every move (which is called in the update() method in each of the GameActivity)
+ On any game completion, the screen has been frozen so that the User will be able to review the final game state
+ Top five scores leaderboard for each game viewable; along with personal Score for each game
+ All SlidingTile Boards are able to be solved
 + Implementation: The Board for SlidingTile is generated as solved, and then randomized by performing (100 * difficulty level) valid moves
 
### Difficulties
+ The SlidingTiles game can be played in 3x3, 4x4, or 5x5 complexity
+ The MatchingCards game can be played in 4x3, 4x4, or 4x5 complexity
+ The Connect4 game can be played on an Easy, Medium, or Hard difficulty, the differentiation being the number of moves the computer considers in advance
 + Easy: 0 moves looked ahead, Medium: 1 move looked ahead, Hard: 2 move looked ahead
 + The Connect4 AI is not a perfect player (as the perfect player would be too resource intensive)

### Image Completion & Choosing an image for Sliding Tiles
+ To use a picture in the game:
 + Ensure you have a picture downloaded already on the Storage, and the application will access it anywhere from the Gallery
 + Reason: Real life Users will have their own pictures on their device to use in the game
+ The User can change the background of the SlidingTiles game to arrange tiles from Ordered Numbers to assembling the downloaded image
 + Pressing the "User Image" button directs to the Gallery. Pick a saved picture to use as the new background for Tiles. Press the Choose Image Button again
 to revert back to the numbered Tiles.
 + At any point in the game (in case the order is forgotten) the user can switch between displaying tiles and user image (pressing "Use Tiles") tiles interchangeably
 + Upon game completion, the blank Tile is completed with the last tile of the user image