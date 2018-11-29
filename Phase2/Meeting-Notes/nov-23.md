### Meeting Nov 23 2018:

### Update on Tasks completed as of today:
+ Meirbek implemented connect4 for two player
 + Available to fix the gridView for Memory
 + Still needs score (possibly a difficulty selection)
+ A default image is saved to the phone, so that User will not need to preemptively download to change the background
 + Needs to be documented in the README (& that user images can be saved anywhere)
+ Autosave completed in Memory


#### Tasks in motion
+ Oliver & Savhanna on Memory
+ Jack on UI
 + UI Needs fixing on the main Activity: The Slider bar not visible enough
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
+ Undo functionality in the Memory game only applicable for first card selection
 + Reasoning: To undo on second card selection would give no visual changes (user might not catch the effect)
+ Difficulties for Memory should only change column. Board will not be square, since not all square numbers are even and the User is to match two cards at a time.
 + Decision: the MemoryBoard will have 4 rows, and 3/4/5 columns as difficulty choice
 + Made a Utilities.java for all the repeated loading and saving methods



#Phase 1 Feedback Items Still Outstanding

+ @Go into detail why certain design decisions made in the Meeting Minutes, and alternatives
 + @Document the decision for currentUser to be saved to Files instead of intents
+ @Document the set up Instructions
 + Try File -> New Import Project, choose Project Location of cloned project
 + UNIX Instructions: cd $HOME/.gradle/caches, rm -rf $HOME/.gradle/caches, then re-open Android Studio

+ Login Issues
 + Serialization not working (?)
 + Can not read file: java.io.InvalidClassException: fall2018.csc2017.slidingtiles.BoardManager;
  + @Consideration: Check why the catch statement doesn't working
  + https://stackoverflow.com/questions/8335813/java-serialization-java-io-invalidclassexception-local-class-incompatible
  
### Absent from meeting:
+ Jonathan
+ Jack