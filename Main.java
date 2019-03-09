package lab1;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, BiffException, WriteException {
        int size ;
        char com;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the board dimension:  ");
        size = input.nextInt();
        System.out.print("Computer plays (X/O):  ");
        com = input.next().charAt(0);
        while (com != 'X' && com != 'O'){
            System.out.print("请输入正确形式(X/O) :");
            com = input.next().charAt(0);
        }
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
        String filename = "lab1.xls";
        InFile.out(filename,board);
    }
}
