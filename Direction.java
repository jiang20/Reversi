package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Direction {
    Chess chess;
    static int max = 0;
    int[] hor = {1,1,1,0,-1,-1,-1,0};
    int[] ver = {1,0,-1,-1,-1,0,1,1};
    Direction(Chess chess){
        this.chess = chess;
    }


    public int[] cpu_place(char[][] board ){
        int first_board = 0, second_board = 0, added_number = 0;
        int[] result = new int[3];
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j] == '.' && legal(board, i , j)[0] > result[0]){
                    first_board = i;
                    second_board = j;
                    result = legal(board, i ,j);
                    added_number = result[0];
                }
            }
        }
        chess.setNumber(chess.getNumber() + 1 + added_number);
        chess.setChangeNumber(added_number);
        changeBoard(board, first_board, second_board, result);
        return new int[]{first_board,second_board};
    }

    public int[] human_place(char[][] board ) throws IOException {
        System.out.print("Enter move for  " + chess.getCharacter() + " (RowCol) : ");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        String text = buffer.readLine();
        if(text.length() != 2 ){
            chess.set_if_giveup(true);
            return new int[]{0,0,0};
        }
        char out_hor = text.charAt(0), out_ver = text.charAt(1);
        int hor = out_hor - 'a' , ver = out_ver - 'a';
        chess.setPlace(new char[]{out_hor,out_ver});
        if ( (hor >= board.length || hor < 0 || ver >= board.length || ver < 0)) {
            chess.set_if_giveup(true);
            return new int[]{0,0,0};
        }
        if(board[hor][ver] != '.'){
            chess.set_if_giveup(true);
            return new int[]{0,0,0};
        }
        int[] result = legal(board, hor, ver);
        changeBoard(board, hor, ver, result);
        if(result[0] != 0) {
            chess.setNumber(chess.getNumber() + result[0] + 1);
            chess.setChangeNumber(result[0]);
            return result;
        }
        chess.set_if_giveup(true);
        return new int[]{0,0,0};
    }
    //用这个函数得到在相应位置的最后的结果
    public int[] legal(char[][] board, int i, int j) {
        max = 0;
        int times = -1;
        int horizontal , vertical;
        int out_hor = 0, out_ver = 0;
        int hor = 0, ver = 0;
        while ( ++times < 8) {
            horizontal = this.hor[times];
            vertical = this.ver[times];
                hor = i + horizontal;
                ver = j + vertical;
            while (hor >= 0 && hor < board.length && ver >= 0 && ver < board.length) {
                if (board[hor][ver] != chess.getCharacter() && board[hor][ver] != '.') {
                    hor += horizontal;
                    ver += vertical;
                }
                else if (board[hor][ver] == chess.getCharacter()) {
                    if (0 != Math.abs((hor - (i + horizontal))) || 0 != Math.abs(ver - (j + vertical))) {
                        max += Math.max( Math.abs((hor - (i + horizontal))), Math.abs(ver - (j + vertical)));
                        out_hor = horizontal;
                        out_ver = vertical;
                    }
                    break;
                }
                else if(board[hor][ver] == '.')
                    break;
            }
        }
        return new int[]{max, out_hor, out_ver};
    }
    private void changeBoard(char[][] board, int hor, int ver, int[] result){
        board[hor][ver] = chess.getCharacter();
        int number = 0, i = -1;
        int hor0 = hor , ver0 = ver;
        while ( ++i < 8){
            if(change_in_this_way(board , hor0 , ver0 , this.hor[i] , this.ver[i])){
                hor = hor0 + this.hor[i];
                ver = ver0 + this.ver[i];
                while (board[hor][ver] != '.' && board[hor][ver] != chess.getCharacter()){
                    board[hor][ver] = chess.getCharacter();
                    hor += this.hor[i];
                    ver += this.ver[i];
                }
            }
        }
    }
    private boolean change_in_this_way(char[][] board, int x,int y,int hor, int ver){
        int number = 0;
        x += hor;
        y += ver;
        while (x >= 0 && x < board[0].length && y >= 0 && y < board[0].length){
            if(board[x][y] != chess.getCharacter() && board[x][y] != '.'){
                number ++;
                x += hor;
                y += ver;
            }
            else if(board[x][y] == chess.getCharacter()){
                switch (number){
                    case 0:return false;
                    default:return true;
                }
            }
            else if(board[x][y] == '.')
                return false;
        }
        return false;
    }
}
