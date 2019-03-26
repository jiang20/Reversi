package lab1;

import java.io.IOException;

public abstract class Chess {
    private char character;
    private int number;
    private int numOfChange;
    private int reason;
    private char[] place;
    private int size;
    private int operation;
    private Direction direction;
    private boolean if_giveup;

    Chess(){
        this.character = '*';
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};
        this.size = 0;
        direction = new Direction(this);
    }
    Chess(int size, int order, int operation){
        if(order == 1)
            this.character = 'X';
        else
            this.character = 'O';
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};//place 是即将放置棋子的位置，为一维数组
        this.size = size;
        this.operation = operation;
        direction = new Direction(this);
    }

    /*下棋，没有位置可下时返回null*/
    public char[] place(char[][] board) throws IOException {return place; }

    /*判断human是否放弃*/
    public Boolean if_giveup(char[][] board){ return false; }

    /*判断整个棋盘无位置可下*/
    public Boolean isOverBefore(char[][] board){return false;}

    /*判断具体的位置是否可下*/
    private Boolean find(int row, int col, char[][] board){ return false;}

    public void out_when_no_place(char[][] board){ }

    public char getCharacter() { return character; }
    public int getNumber(){ return this.number; }
    public int getOperation(){return this.operation;}
    public int getNumOfChange() { return numOfChange; }
    public void setChangeNumber(int change){ this.numOfChange = change; }//change number, and change the checkerboard(棋盘)
    public void setNumber(int number) { this.number = number; }
    public void setSize(int size){ this.size = size; }
    public void setReason(int reason){this.reason = reason;}
    public void set_if_giveup(boolean bool){
        this.if_giveup  = bool;
    }
    public void setPlace(char[] place) { this.place = place; }
}
