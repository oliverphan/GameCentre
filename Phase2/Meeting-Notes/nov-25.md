### Meeting Nov 25 2018:

### Update on Tasks completed as of today:
+ Abstraction (outlined in Design Decisions below)
+ Fixed the FileCannotReadError
+ Renamed Concentration back to Matching Cards (game name
+ Linking the Matching Card's game activity to its title activity
+ Abstracted methods for loading boardManagers (aka the game state) to an interface to be shared across all games


#### Tasks in motion
+ Matching cards Ggame
+ Testing suite
+ Jack on the Utilities files for commonly used saving and loading methods
 + Default methods
+ Jonathan on Leaderboard

### Tasks still need to be done
+ Create New folders/directory layout for the app (consequently, appropiate packages as well)
+ Memory:
 + Implementing undo
  + There should be two states the gameActivity is in, on first card selection or selection of second card
  + Thus undo and isMatched should only work on second Card Selection

### Design decisions
+ Abstracting to Token, Abstract Generic Board, Interface for Managers
 + Reasoning: Shared functionalities between the Tile/Card/Piece, Boards, and Managers
+ Abstracting commonly used saving and loading methods for the boardManagers

### Absent from meeting: