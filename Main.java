package lab1;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int size ;
        char com;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the board dimension:  ");
        size = input.nextInt();
        System.out.print("Computer plays (X/O):  ");
        com = input.next().charAt(0);
        Chess black , white;
        if(com == 'X') {//黑棋的order是1，计算机的operation是1
            black = new Chess(size, 1 , 1);
            white = new Chess(size, 2 , 2);
        }
        else {
            black = new Chess(size , 1 , 2);
            white = new Chess(size , 2 , 1);
        }
        Board board = new Board(size,black,white);
        board.begin();
    }
}
