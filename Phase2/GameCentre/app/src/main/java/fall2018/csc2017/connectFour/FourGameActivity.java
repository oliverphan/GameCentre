package fall2018.csc2017.connectFour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import fall2018.csc2017.slidingtiles.R;

public class FourGameActivity extends AppCompatActivity implements Observer {
    private FourBoardManager boardManager;
    private ArrayList<Button> boardButtons;
    private int difficulty = 2;
    private FourGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new FourBoardManager(2);
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

    public void updateBoardButtons(){
        FourBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b: boardButtons){
            int col = nextPos%7;
            int row = nextPos/7;
            b.setBackgroundResource(board.getPiece(col, row).getBackground());
            nextPos++;
        }
    }

    public int getComputerMove(){
        FourBoard board = boardManager.getBoard();
        ArrayList<Integer> potentialMoves = getPotentialMoves(boardManager.getBoard(), difficulty);
        ArrayList<Integer> bestMoves = new ArrayList<>();
        int bestMoveScore = Collections.max(potentialMoves);
        for (int i = 0; i < potentialMoves.size(); i++){
            if (potentialMoves.get(i) == bestMoveScore){
                bestMoves.add(i);
            }
        }
        System.out.print("Potential Moves:");
        System.out.println(potentialMoves);
        System.out.print("Best Move Score:");
        System.out.println(bestMoveScore);
        System.out.print("Best Moves:");
        System.out.println(bestMoves);
        int move =  bestMoves.get(new Random().nextInt(bestMoves.size()));
//        boardManager.previousMoves.push(move);
        return move;
    }

    public ArrayList<Integer> getPotentialMoves(FourBoard board, int d){
        if (d == 0){
            ArrayList<Integer> moves = new ArrayList<>();
            for (int i = 0; i < 7; i++){moves.add(0);}
            return moves;
        }
        ArrayList<Integer> potentialMoves = new ArrayList<>();

        if (board.isBoardFull()){
            ArrayList<Integer> moves = new ArrayList<>();
            for (int i = 0; i < 7; i++){moves.add(0);}
            return moves;
        }

        for (int i = 0; i < 7; i++){potentialMoves.add(0);}
        for (int move = 0; move < 7; move++){
            FourBoard dupe = new FourBoard(board.pieces);
            if (dupe.openRow(move) == -1){
                continue;
            }
            dupe.makeMove(move, 2);
            if (dupe.isWinner(2)){
                potentialMoves.set(move, 1);
                break;
            }else{
                if (dupe.isBoardFull()){
                    potentialMoves.set(move, 0);
                }else{
                    for (int eMove = 0; eMove < 7; eMove++){
                        FourBoard dupe2 = new FourBoard(dupe.pieces);
                        if (dupe.openRow(eMove) == -1){
                            continue;
                        }
                        dupe2.makeMove(eMove, 1);
                        if (dupe2.isWinner(1)){
                            potentialMoves.set(move, -1);
                            break;
                        }else{
                            ArrayList<Integer> results = getPotentialMoves(dupe2, d-1);
                            int sum = 0;
                            for (int i: results){
                                sum += i;
                            }
                            potentialMoves.set(move, sum/49);
                        }
                    }
                }
            }
        }
        System.out.println(potentialMoves);
        return potentialMoves;
    }

    public void display() {
        updateBoardButtons();
        gridView.setAdapter(new FourCustomAdapter(boardButtons, columnWidth, columnHeight));
    }

    @Override
    public void update(Observable o, Object arg) {
        FourBoard board = boardManager.getBoard();
        if (boardManager.gameFinished()) {
            createToast(board.isWinner(1) ? "You Win!" : "You Lose");
        }else{
        if (board.curPlayer == 2){
            board.makeMove(getComputerMove(),2);}
            board.switchPlayer();
        }
        display();
    }

    private void createToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
