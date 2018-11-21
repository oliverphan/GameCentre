# Setup Instructions for group_0540 CSC207 Phase1


## Cloning the repo
1. Open Android Studio and close any open projects. (File -> Close Project)
2. Click "Check out project from Version Control" from Git.
3. Paste to URL Box: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0540
4. Select the destination directory and press "Clone".

----
## Building the Project
1. As soon as you press Clone, press "Yes" to create an Android Studio Project.
2. Select "Import project from external model", choosing the non-Android Gradle model and click Next.
3. Add "\Phase1\GameCentre" to the Gradle project path, and make sure to use "Explicit Module Groups" and the "Default Gradle Wrapper". Press Finish.
4. If the project file already exists, overwrite it.
5. Press "Ok" on the Sync Android SDKs.
6. There should be a popup to say "Unregistered VCS root detected. Press "Add root".
7. Do not add GameCentre.iml to Git.


----
## Functionalities Implemented
+ Login and Sign in using username and password
+ The User is able to:
 + Save their games (games are saved automatically when the user returns to the login screen).
 + Load their saved games
 + See their top score for each game
 + See that they're signed in when they're choosing a game to play
+ Viewable top three scores leaderboard for each game
 + To go back to the SlidingTilesTitleActivity view, use the custom "Back" button to save information properly.
+ Autosave and undo button for the SlidingTiles game
 + On deafult without specification, the undo button will only undo one move
 + Autosave is done after every move (which is called in the update() method in SlidingTilesGameActivity)
+ The SlidingTiles game can be played in 3x3, 4x4, or 5x5 complexity
+ The User can change the background of the SlidingTiles game to arrange tiles from Ordered Numbers to assembling the downloaded image
 + Pressing the Choose Image button directs to the Gallery. Pick a saved picture to use as the new background for Tiles. Press the Choose Image Button again
 to revert back to the numbered Tiles.
+ Don't add any of the build files