package lab1;

import java.io.IOException;

public class Human extends Chess{
    private char character;
    private int number;
    private int numOfChange;
    private int reason;
    public char[] place;
    private int size;
    private int operation;
    private Direction direction;
    private boolean if_give_up;

    Human(){
        this.character = '*';
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};
        this.size = 0;
        direction = new Direction(this);
    }
    Human(int size, char character){
        this.character = character;
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};//place 是即将放置棋子的位置，为一维数组
        this.size = size;
        direction = new Direction(this);
        if_give_up = false;
    }

    /*人下棋*/
    public char[] place(char[][] board) throws IOException {
        out_when_no_place(board);
        direction.human_place(board);
        return place;
    }
    /*判断human是否放弃*/
    public Boolean if_giveup(char[][] board){
        if(!find(place[0] - 'a',place[1] - 'a', board))
            if_give_up = true;
        return if_give_up;
    }

    /*判断整个棋盘上是否有位置可下*/
    public Boolean isOverBefore(char[][] board){//judge whether it is over on this side
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                if(board[i][j] == '.' && find(i, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*判断具体的位置是否可下*/
    private Boolean find(int row, int col, char[][] board){
        if(row < 0 || col < 0 || row >= size || col >= size)
            return false;
        return (direction.legal(board, row, col)[0]!= 0);
    }

    public void out_when_no_place(char[][] board){
        if(isOverBefore(board)) {
            System.out.println(this.getCharacter() + " has no valid place  ");
        }
    }

    public char getCharacter() { return character; }
    public int getNumber(){
        return this.number;
    }
    public int getOperation(){return this.operation;}
    public int getNumOfChange() {
        return numOfChange;
    }
    public int getReason(){return reason;}
    public int getSize(){
        return size;
    }
    public boolean isIf_give_up() { return if_give_up; }
    public void setChangeNumber(int change){
        this.numOfChange = change;
    }//change number, and change the checkerboard(棋盘)
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSize(int size){
        this.size = size;
    }

    @Override
    public void set_if_giveup(boolean bool) {
//        super.set_if_giveup(bool);
        this.if_give_up = bool;
    }

    public void setReason(int reason){this.reason = reason;}

    @Override
    public void setPlace(char[] place) {
        this.place = place;
    }
}
