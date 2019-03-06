package lab1;

import java.io.IOException;
import java.util.Scanner;

public class Board {
    private int size;
    private char[][] board;
    private Chess black;
    private Chess white;

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
    }

    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void begin() throws IOException {
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
            System.out.print(black.getNumber()+"  he  "+white.getNumber());
            if(!black.isRight(place,board)){
                outResult('X',4);
                return;
            }
            if(black.getNumber() + white.getNumber() == size * size && black.getNumber() != 0 && white.getNumber() != 0) {
                outResult('*', 1);
                return;
            }
            while(white.isOverBefore(board)){
                System.out.print("\n why not? ");
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
//                        if(black.getReason() == 3){
//                            outResult('*',3);
//                        }
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
//            char[] place = new char[2];
//            place[0] = input.next().charAt(0);
//            place[1] = input.next().charAt(1);
//            if(player2 == 'X')
//                black.place(board);
//            else
//                white.place(board);
        }
        else if(reason == 4){
            System.out.print("Invalid move.\nGame over\n"+player2+" player wins.\n");
        }
    }//output the result
}
