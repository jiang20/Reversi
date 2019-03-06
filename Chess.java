package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Chess {
    private char character;
    private int number;
    private int numOfChange;
    private int reason;
    private char[] place;
    private int order;
    private int size;
    private int operation;

    Chess(){
        this.character = '*';
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};
        this.order = 0;
        this.size = 0;
    }
    Chess(int size, int order, int operation){
        if(order == 1)
            this.character = 'X';
        else
            this.character = 'O';
        this.order = order;
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};//place 是即将放置棋子的位置，为一维数组
        this.size = size;
        this.operation = operation;
    }

    public int getNumber(){
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumOfChange() {
        return numOfChange;
    }
    public void setChangeNumber(int change){
        this.numOfChange = change;
    }//change number, and change the checkerboard(棋盘)
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public char[] place(char[][] board) throws IOException {//place应该有确定计算机是否有位置
        int[] rowNum = {-2, 0, 2};
        int[] colNum = {-2, 0, 2};
        int amount = 0;
        if(operation == 1) {
            Boolean isRight = false;
            for (int i = 1; i <= size; i++) {
                for (int j = 1; j <= size; j++) {
                    int tmp = 0;
                    if (board[i][j] == '.') {
                        //每个位置的八个方向？？
                        System.out.print(" after continue");
                        for (int row : rowNum) {
                            for (int col : colNum) {
                                if (row == 0 && col == 0)
                                    continue;
                                if (i + row >= 1 && j + col >= 1 && i + row < 5 && j + col < 5) {
                                    if (board[i + row][j + col] == this.character && board[i + row / 2][j + col / 2] != '.' && board[i + row / 2][j + col / 2] != this.character) {
                                        tmp++;
                                    }
                                }
                            }
                        }
                        if (tmp > amount) {
                            amount = tmp;
                            place[0] = (char) ('a' + i - 1);
                            place[1] = (char) ('a' + j - 1);
                        }
                    }
                }
            }
            //place 是原有的位置，当它没有发生更新的时候，他会照常输出，所以关键是这个place的步骤不能执行的
            System.out.print("Computer places "+character+" at "+place[0]+place[1]+" \n");
        }
        else{
            int tmp = 0;
            System.out.print("Enter move for "+character+" (RowCol): ");
//            Scanner input = new Scanner(System.in);
//            System.out.print("haha");
//            place[0] = input.next().charAt(0);
//            place[1] = input.next().charAt(1);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String text = buffer.readLine();
            place[0] = text.charAt(0);
            place[1] = text.charAt(1);
            int i = place[0] - 'a';
            int j = place[1] - 'a';
            if(board[i + 1][j + 1] != '.'){
                this.reason = 4;
//                for(int k = 0; k < size + 1; k++){
//                    for(int m = 0; m < size + 1; m++){
//                        System.out.print(board[k][m]);
//                        if(m == size)
//                            System.out.print("\n");
//                    }
//                }
                return null;
            }
            for (int row : rowNum) {
                for (int col : colNum) {
                    if (row == 0 && col == 0)
                        continue;
                    if (i + row >= 0 && j + col >= 0 && i + row < size && j + col < size) {
                        if (board[i + row + 1][j + col + 1] == this.character && board[i + row / 2 + 1][j + col / 2 + 1] != '.' && board[i + row / 2 + 1][j + col / 2 + 1] != this.character) {
                            tmp++;
                        }
                    }
                }
            }
            if (tmp > amount) {
                amount = tmp;
            }
            if(amount == 0)
                this.reason = 4;
        }
        if (amount > 0) {
            for (int row : rowNum) {
                for (int col : colNum) {
//                if(row == 0 && col == 0)
//                    continue;
                    if (place[0] + row - 'a' >= 0 && place[1] + col - 'a' >= 0 && place[0] + row - 'a' <= size - 1 && place[1] + col - 'a' <= size - 1) {
                        if (board[place[0] + row - 'a' + 1][place[1] + col - 'a' + 1] == this.character && board[place[0] + row / 2 - 'a' + 1][place[1] + col / 2 - 'a' + 1] != '.' && board[place[0] + row / 2 - 'a' + 1][place[1] + col / 2 - 'a' + 1] != this.character) {
                            board[place[0] + row / 2 - 'a' + 1][place[1] + col / 2 - 'a' + 1] = this.character;
                        }
                    }
                }
            }
            board[place[0] - 'a' + 1][place[1] - 'a' + 1] = this.character;
        }
        //输出改变之后的棋盘
        for(int i = 0; i < size + 1; i++){
            for (int j = 0; j < size + 1; j++){
                System.out.print(board[i][j]);
                if(j == size)
                    System.out.print("\n");
            }
        }
        setNumber(this.getNumber() + amount + 1);
        numOfChange = amount;
        return place;
    }//output where to put the next,if out is null,it means no place
    public Boolean isRight(char[] place,char[][] board){//只需要对人工进行检测；当计算机过来isOverBefor的关卡，那么在这里肯定是正确的
//        char row = place[0];
//        char col = place[1];
        if(operation == 1)
            return true;
        if(reason == 4)//place的位置是要加入棋子的位置
            return false;
//        return find(place,board);//放置位置是否正确
//        int[] rowdir = new int[]{-2,0,2};//应有棋子的位置
//        int[] coldir = new int[]{-2,0,2};
//        Boolean isRight = false;
//        for (int rowNum: rowdir){
//            for(int colNum: coldir){
//                if(isRight)
//                    return true;
//                if(colNum == 0 && rowNum == 0 || row + rowNum - 'a' < 0 || row + rowNum - 'a' < 0 || board[row + rowNum - 'a'][col + colNum - 'a'] == '.')
//                    continue;
//                if (board[row + rowNum - 'a'][col + colNum - 'a'] == board[row][col])
//                    isRight = (board[row][col] == board[row + rowNum / 2 - 'a'][col +colNum / 2 - 'a']);
//            }
//        }
//        if(board[row][col] == '.') {
//            if (row >= 'c' && col >= 'c' && board[row - 1 - 'a'][col - 1 - 'a'] != '.' && board[row - 1 - 'a'][col - 1 - 'a'] != board[row - 'a'][col - 'a'] && board[row - 2 - 'a'][col - 2 - 'a'] == board[row - 'a'][col - 'a']) {
//                return true;
//            }
//        }
        return true;
    }//judge whether it is legal
    private Boolean find(char[] place,char[][] board){
        char row = place[0];
        char col = place[1];
        Boolean isRight = false;
        int[] rowdir = new int[]{-2,0,2};//应有棋子的位置
        int[] coldir = new int[]{-2,0,2};
        aaa:for (int rowNum: rowdir){
            for(int colNum: coldir){
                if(isRight)
                    return true;
                if((colNum == 0 && rowNum == 0 ) || row + rowNum - 'a' < 0 || col + colNum - 'a' < 0 || row + rowNum - 'a' >= size || col + colNum - 'a' >= size)
                    ;
                else if( board[row + rowNum - 'a' + 1][col + colNum - 'a' + 1] == '.')
                    ;
                else if(row - 'a' < 0 || col - 'a' < 0 || row - 'a' >= size || col - 'a' >= size)
                    break aaa;
                else if (board[row + rowNum - 'a' + 1][col + colNum - 'a' + 1] == this.character)
                    isRight = (board[row - 'a' + 1][col - 'a' + 1] == '.' && board[row + rowNum / 2 - 'a' + 1][col +colNum / 2 - 'a' + 1] != this.character && board[row + rowNum / 2 - 'a' + 1][col +colNum / 2 - 'a' + 1] != '.');
            }
        }
        return isRight;
    }
    public int getReason(){
        return this.reason;
    }
    public void setReason(int reason){
        this.reason = reason;
    }
    public Boolean isOverBefore(char[][] board){//judge whether it is over on this side
        if(this.getNumber() == 0) {//当对方输出之后判断的情况，不考虑自己输入之后的情况
            setReason(2);
            return true;
        }
        if(reason == 0) {
            reason = 3;
            aaa:for (int i = 1; i < size + 1; i++) {
                for (int j = 1; j < size + 1; j++) {
                    char[] place = new char[]{(char) ('a' + i - 1), (char) ('a' + j - 1)};
                    if(board[i][j] == '.') {
                        if (find(place, board)) {
                            reason = 0;
                            return false;
                        }
                    }
                }
            }
        }
        if(reason == 3){
            return true;//当isOver时再根据reason的值，选择性输出，此处选择在board中输出
        }
        return false;
    }
    public Boolean isOverAfter(char[] place, char[][] board){
        if(isRight(place,board))
            return false;
        return true;
    }
//    public void over(){}//output the result on thr base of "reason"
//    把这个输出留给board做总结
    public void writeIn(){}//write the result in the file
}
