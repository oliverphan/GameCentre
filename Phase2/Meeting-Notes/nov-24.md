### Meeting Nov 24 2018:

### Update on Tasks completed as of today:
+ Abstraction (outlined in Design Decisions below)
+ Fixed the FileCannotReadError



#### Tasks in motion
+ Oliver on Memory
+ Savhanna/Meirbek on the testing suite
+ Jack on the Utilities files for commonly used saving and loading methods
 + Default methods
+ Jonathan on Leaderboard

### Tasks still need to be done
+ Create New folders/directory layout for the app (consequently, appropiate packages as well)
+ Memory:
 + Implementing undo
  + There should be two states the gameActivity is in, on first card selection or selection of second card
  + Thus undo should only work on second Card Selection
 + Linking the game's activity to its title activity
 + Difficulty generation
+ Leaderboard

### Design decisions
+ Abstracting to Token, Abstract Generic Board, Interface for Managers
 + Reasoning: Shared functionalities between the Tile/Card/Piece, Boards, and Managers
+ Using currentUsername to the save file instead of the User Object
+ Undo functionality in the Memory game only applicable for first card selection
 + Reasoning: To undo on second card selection would give no visual changes (user might not catch the effect)
+ Difficulties for Memory should only change column. Board will not be square, since not all square numbers are even and the User is to match two cards at a time.
 + Decision: the MemoryBoard will have 4 rows, and 3/4/5 columns as difficulty choice
 + Made a Utilities.java for all the repeated loading and saving methods
