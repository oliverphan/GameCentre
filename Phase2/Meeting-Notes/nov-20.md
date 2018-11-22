### Meeting Nov 20 2018: Phase 1 Feedback released

### Update on Tasks completed today:
+ Renamed "Matching" to "Memory"
+ Meirbek fixed issue of Tile IDs 1 not being number 1 tile
+ Created a general makeToast method in LoginActivity
+ Oliver fixed difficulty of project set-up
 + @Still needs to update documentation with Terminal commands
+ Savhanna created Board and "Piece" abstract class for the Tiles in Sliding Tiles and Cards in Memory game
 + Reasoning: SlidingTile & Memory game have similar components in Board and definition of the Cards and Tiles
   used to play the game
+ Updated README to reflect multiple accounts being used for Git

#### Tasks in motion
+ Oliver & Savhanna on matching Tiles
+ Meirbek on Connect 4
+ Jack on UI
+ Jonathan on Leaderboard

### Tasks still need to be done
+ Create New folders/directory layout for the app (consequently, appropiate packages as well)
+ Both Games
+ Leaderboard
+ Catch the little/"obvious"! items of code smells

#Phase 1 Feedback Items Still Outstanding

+ @Go into detail why certain design decisions made in the Meeting Minutes, and alternatives
 + @Document the decision for currentUser
+ @Document the set up Instructions
 + Try File -> New Import Project, choose Project Location of cloned project
 + UNIX Instructions: cd $HOME/.gradle/caches, rm -rf $HOME/.gradle/caches, then re-open Android Studio

+ Login Issues
 + Serialization not working (?)
 + Can not read file: java.io.InvalidClassException: fall2018.csc2017.slidingtiles.BoardManager;
  + @Consideration: Check why the catch statement doesn't working
  + https://stackoverflow.com/questions/8335813/java-serialization-java-io-invalidclassexception-local-class-incompatible  

+ Image upload
 + Specify where file must be saved on README; Instead
 
+ UI Too basic
 +@Consideration: Should Upgrade UI components and colours