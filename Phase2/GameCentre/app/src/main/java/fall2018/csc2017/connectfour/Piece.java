package fall2018.csc2017.connectFour;

import fall2018.csc2017.slidingtiles.R;

public class Piece {
    private int player;
    private int background;

    Piece(){
        this.player = 0;
        this.background = R.drawable.empty_piece;
    }

    int getPlayer(){
        return this.player;
    }

    int getBackground(){
        return this.background;
    }

    void setPlayer(int player){
        this.player = player;
        if (player == 1){
            this.background = R.drawable.piece_1;
        }else{
            this.background = R.drawable.piece_2;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this){
            return true;
        }
        if (!(o instanceof Piece)){
            return false;
        }
        Piece p = (Piece) o;

        return p.player == this.player;
    }
}
