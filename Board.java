package lab1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Board {
    private int size;
    private char[][] board;
    private Chess black;
    private Chess white;
    private int reason;
    private long start;
    private long end;
    private int com;
    private int loser;
    private String date;

    Board(int size, Chess black, Chess white){
        this.size = size ;
        board = new char[this.size + 1][this.size + 1];
        for(int i = 0; i < this.size + 1; i++){
            for(int j = 0; j < this.size + 1; j++){
                if(i == 0) {
                    board[i][j] = (j == 0)?' ':(char) ('a' + j - 1);
                }
                else if(j == 0)
                    board[i][j] = (char)('a' + i - 1);
                else
                    board[i][j] = '.';
            }
        }
        board[size / 2][size / 2] = board[size / 2 + 1][size / 2 + 1] = 'O';
        board[size / 2 + 1][size / 2] = board[size / 2 ][size / 2 + 1] = 'X';
        this.black = black;
        this.white = white;
        com = black.getOperation();
        start = 0;
        end = 0;
        loser = 0;
        date = null;
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getReason(){return reason;}
    public int getCom(){return com;}
    public long getStart(){return start;}
    public long getEnd(){return end;}
    public Chess getBlack(){return black;}
    public Chess getWhite(){return white;}
    public int getLoser(){return loser;}
    public String getDate(){return date;}
    public void begin() throws IOException {
        Date dateNow = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        date = format.format(dateNow);
        start = System.currentTimeMillis();
        for(int i = 0 ; i <= size; i ++){
            for(int j = 0; j <= size; j++){
                System.out.print(board[i][j]);
                if( j == size)
                    System.out.print("\n");
            }
        }
        char[] place;
        while (true){
            if(black.getNumber() + white.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                outResult('*', 1);
                return;
            }
            while(black.isOverBefore(board)){
                white.isOverBefore(board);
                if(black.getReason() == 2){
                    outResult('X',2);
                    return;
                }
                else if(black.getReason() == 3 && white.getReason() == 3) {
                    outResult('*', 3);
                    return;
                }
                else if(black.getReason() == 3) {
                    while (black.getReason() == 3){
                        if(black.getNumber() + white.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                            outResult('*', 1);
                            return;
                        }
                        white.isOverBefore(board);
                        switch (white.getReason()){
                            case 2:outResult('O',2);return;
                            case 3:outResult('*',3);return;
                        }
                        outResult('X',3);
                        place = white.place(board);
                        black.setNumber(black.getNumber() - white.getNumOfChange());
                        if(!white.isRight(place,board))
                            outResult('O',4);
//                        if(white.getReason() == 3){
//                            outResult('*',3);
//                        }
                        black.isOverBefore(board);
                        switch (black.getReason()){
                            case 2:outResult('X',2);return;
                            case 3:break;
                        }
                    }
                }
            }
            place = black.place(board);
            white.setNumber(white.getNumber() - black.getNumOfChange());
            if(!black.isRight(place,board)){
                outResult('X',4);
                return;
            }
            if(black.getNumber() + white.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                outResult('*', 1);
                return;
            }
            while(white.isOverBefore(board)){
                black.isOverBefore(board);
                if(white.getReason() == 2){
                    outResult('O',2);
                    return;
                }
                else if(white.getReason() == 3 && black.getReason() == 3){
                    outResult('*',3);
                    return;
                }
                else if(white.getReason() == 3) {
                    while (white.getReason() == 3){
                        if(white.getNumber() + black.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                            outResult('*', 1);
                            return;
                        }
                        black.isOverBefore(board);
                        switch (black.getReason()){
                            case 2:outResult('X',2);return;
                            case 3:outResult('*',3);return;
                        }
                        outResult('O',3);
                        place = black.place(board);
                        white.setNumber(white.getNumber() - black.getNumOfChange());
                        if(!black.isRight(place,board))
                            outResult('X',4);
                        white.isOverBefore(board);
                        switch (white.getReason()){
                            case 2:outResult('O',2);
                            case 3:outResult('*',3);
                        }
                    }
                }
            }
            place = white.place(board);
            black.setNumber(black.getNumber() - white.getNumOfChange());
            if(!white.isRight(place,board)){
                outResult('O',4);
                return;
            }
            if(black.getNumber() + white.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                outResult('*', 1);
                return;
            }
        }
    }
    public void outResult(char player,int reason) throws IOException {
        char player2 = (player == 'O') ? 'X' : 'O' ;
        loser = ((player == 'X')?black:white).getOperation();
        if(reason == 1){
            if(black.getNumber() > white.getNumber()){
                player2 = 'X';
            }
            else
                player2 = 'O';
            System.out.print("The board is full.\nGame over.\n");
            System.out.print("X : O = " + black.getNumber()+" : "+white.getNumber()+"\n"+player2+" player wins.\n");
        }
        else if(reason == 2){
            System.out.print(player+" has no piece on the board.\nGame over.\n"+ player2 +" player wins.\n");
        }
        else if(reason == 3 && player == '*'){
            System.out.print("Both players have no valid move.\nGame over\n");
            System.out.print("X : O = " + black.getNumber()+" : "+white.getNumber()+"\n"+player2+" player wins.\n");
        }
        else if(reason == 3 ){
            System.out.print(player+" player has no valid move. ");
        }
        else if(reason == 4){
            System.out.print("Invalid move.\nGame over\n"+player2+" player wins.\n");
        }
        end = System.currentTimeMillis();
        this.reason = reason;
    }//output the result
}
