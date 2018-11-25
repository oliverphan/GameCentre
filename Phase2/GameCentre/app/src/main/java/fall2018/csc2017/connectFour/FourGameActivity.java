package fall2018.csc2017.connectFour;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fall2018.csc2017.R;
import fall2018.csc2017.SaveAndLoad;
import gamelauncher.ConnectFourActivity;
import gamelauncher.LoginActivity;
import scoring.LeaderBoard;
import scoring.Score;
import users.User;

public class FourGameActivity extends AppCompatActivity implements Observer, SaveAndLoad {
    /**
     * The SlidingBoard manager.
     */
    private FourBoardManager boardManager;

    /**
     * The buttons on the board.
     */
    private ArrayList<Button> boardButtons;

    /**
     * The difficulty of the current game.
     */
    private int difficulty;

    // Grid View and calculated column height and width based on device size
    private FourGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Current user logged in.
     */
    private User currentUser;

    /**
     * HashMap of all userAccounts
     */
    private HashMap<String, User> userAccounts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGameFromFile();
        userAccounts = loadUserAccounts();
        currentUser = userAccounts.get(loadCurrentUsername());
        difficulty = boardManager.getDifficulty();
        createBoardButtons();
        setContentView(R.layout.activity_connectfourgame);
//        addUndoButtonListener();
        gridView = findViewById(R.id.gridView);
        gridView.setNumColumns(7);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = gridView.getMeasuredWidth();
                int displayHeight = gridView.getMeasuredHeight();
                columnWidth = displayWidth / 7;
                columnHeight = displayHeight / 6;
                display();
            }
        });
    }
//
//    public void addUndoButtonListener(){
//        final Button undoButton = findViewById(R.id.undo);
//        undoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boardManager.undoMove(1);
//            }
//        });
//    }

    /**
     * Get the initial backgrounds of the pieces on the grid.
     */
    public void createBoardButtons() {
        FourBoard board = boardManager.getBoard();
        boardButtons = new ArrayList<>();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                Button tmp = new Button(getApplicationContext());
                tmp.setBackgroundResource(board.getPiece(col, row).getBackground());
                this.boardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds of the pieces on the grid.
     */
    public void updateBoardButtons() {
        FourBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : boardButtons) {
            int col = nextPos % 7;
            int row = nextPos / 7;
            b.setBackgroundResource(board.getPiece(col, row).getBackground());
            nextPos++;
        }
    }

    /**
     * Main method for AI player to make a smart move.
     *
     * @return return the best move (or random move if all moves result in a loss).
     */
    public int getComputerMove() {
        //TODO Remove test printing and optimize
        FourBoard board = boardManager.getBoard();
        ArrayList<Integer> potentialMoves = getPotentialMoves(boardManager.getBoard(), difficulty);
        ArrayList<Integer> bestMoves = new ArrayList<>();
        int bestMoveScore = Collections.max(potentialMoves);
        for (int i = 0; i < potentialMoves.size(); i++) {
            if (potentialMoves.get(i) == bestMoveScore && board.openRow(i) != -1) {
                bestMoves.add(i);
            }
        }
        System.out.print("Potential Moves:");
        System.out.println(potentialMoves);
        System.out.print("Best Move Score:");
        System.out.println(bestMoveScore);
        System.out.print("Best Moves:");
        System.out.println(bestMoves);
        ArrayList<Integer> allowedMoves = new ArrayList<>();
        for (int i = 0; i < board.NUM_COLS; i++) {
            if (board.openRow(i) != -1) {
                allowedMoves.add(i);
            }
        }
        int move = bestMoves.size() > 0 ? bestMoves.get(new Random().nextInt(bestMoves.size())) : allowedMoves.get(new Random().nextInt(allowedMoves.size()));
//        boardManager.previousMoves.push(move);
        return move;
    }

    /**
     * Helper method to evaluate and return the best scores of moves for a given board.
     *
     * @param board the board being evaluated
     * @param d     amount of moves to look ahead.
     * @return list of scores for potential moves
     */
    public ArrayList<Integer> getPotentialMoves(FourBoard board, int d) {
        //TODO Optimize and potentially extract more methods
        if (d == 0) {
            ArrayList<Integer> moves = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                moves.add(0);
            }
            return moves;
        }
        ArrayList<Integer> potentialMoves = new ArrayList<>();

        if (board.isBoardFull()) {
            ArrayList<Integer> moves = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                moves.add(0);
            }
            return moves;
        }

        for (int i = 0; i < 7; i++) {
            potentialMoves.add(0);
        }
        for (int move = 0; move < 7; move++) {
            FourBoard dupe = new FourBoard(board.pieces);
            if (dupe.openRow(move) == -1) {
                continue;
            }
            dupe.makeMove(move, 2);
            if (dupe.isWinner(2)) {
                potentialMoves.set(move, 1);
                break;
            } else {
                if (dupe.isBoardFull()) {
                    potentialMoves.set(move, 0);
                } else {
                    for (int eMove = 0; eMove < 7; eMove++) {
                        FourBoard dupe2 = new FourBoard(dupe.pieces);
                        if (dupe2.openRow(eMove) == -1) {
                            continue;
                        }
                        dupe2.makeMove(eMove, 1);
                        if (dupe2.isWinner(1)) {
                            potentialMoves.set(move, -1);
                            break;
                        } else {
                            ArrayList<Integer> results = getPotentialMoves(dupe2, d - 1);
                            int sum = 0;
                            for (int i : results) {
                                sum += i;
                            }
                            potentialMoves.set(move, sum / 49);
                        }
                    }
                }
            }
        }
        System.out.println(potentialMoves);
        return potentialMoves;
    }

    /**
     * Update and display changes to the gameBoard.
     */
    public void display() {
        updateBoardButtons();
        gridView.setAdapter(new FourCustomAdapter(boardButtons, columnWidth, columnHeight));
    }


    @Override
    public void update(Observable o, Object arg) {
        FourBoard board = boardManager.getBoard();
        if (boardManager.gameFinished()) {
            createToast(board.isWinner(1) ? "You Win!" : "You Lose");
            LeaderBoard leaderBoard = loadLeaderBoard();
            leaderBoard.updateScores("Connect Four", new Score(currentUser.getName(), boardManager.generateScore()));
            saveLeaderBoard(leaderBoard);
        } else {
            if (board.curPlayer == 2) {
                board.makeMove(getComputerMove(), 2);
            }
            board.switchPlayer();
        }
        display();
    }

    @Override
    public void onBackPressed() {
        switchToConnectFourActivity();
    }

    /**
     * Method to switch back to previous Activity
     */
    public void switchToConnectFourActivity() {
        writeNewValues();
        saveUserAccounts(userAccounts);
        if (!boardManager.gameFinished()) {
            createToast("Saved");
        } else {
            createToast("Saved Wiped");
        }
        finish();
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadGameFromFile() {
        try {
            InputStream inputStream = this.openFileInput(ConnectFourActivity.TEMP_SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (FourBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Store the new score and delete the old save in the User if the game is won.
     * If game hasn't been won, store the most recent boardManager to the User.
     */
    public void writeNewValues() {
        if (!boardManager.gameFinished()) {
            currentUser.writeGame(ConnectFourActivity.GAME_TITLE, boardManager);
        } else {
            currentUser.setNewScore(ConnectFourActivity.GAME_TITLE, boardManager.generateScore());
            currentUser.deleteSave(ConnectFourActivity.GAME_TITLE);
        }
    }

    /**
     * Display a message to the user in a Toast.
     *
     * @param msg The message to be displayed in the Toast.
     */
    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public Context getActivity() {
        return this;
    }
}
