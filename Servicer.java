package lab1;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.Scanner;

public class Servicer {
    public void start_service() throws IOException {
        int size ;
        char cpu;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the board dimension(4-10)(even):  ");
        size = input.nextInt();
        while (!(size >= 4 && size <= 10 && size % 2 == 0)){
            System.out.print("请输入正确的形式(4-10)(even):  ");
            size = input.nextInt();
        }
        System.out.print("Computer plays (X/O):  ");
        cpu = input.next().charAt(0);
        while (cpu != 'X' && cpu != 'O'){
            System.out.print("请输入正确形式(X/O) :");
            cpu = input.next().charAt(0);
        }
        Computer computer;
        Human human;
        switch (cpu){
            case 'X':
                computer = new Computer(size,'X');
                human = new Human(size, 'O');
                break;
            default:
                computer = new Computer(size, 'O');
                human = new Human(size, 'X');
        }

        Board board = new Board(size,computer,human);
        board.begin();
        String filename = "Reversi.csv";
        try {
            InFile.out(filename,board);
        } catch (WriteException | BiffException e) {
            e.printStackTrace();
        }
    }
}
