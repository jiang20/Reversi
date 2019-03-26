package lab1;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Board {
    private int size;
    private char[][] board;
    private Computer computer;
    private Human human;
    private long start;
    private long end;
    private int loser;
    private String date;
    private static String turn;
    private static final String CPU = "computer";
    private static final String HUMAN = "human";
    private StringBuffer end_remark = new StringBuffer();
    private StringBuffer out_remark = new StringBuffer();

    Board(int size, Computer computer, Human human){
        this.size = size ;
        board = new char[size][size];
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size ; j++){
                board[i][j] = '.';
            }
        }
        board[( size - 1 )/ 2][( size - 1 )/ 2] = board[( size - 1 ) / 2 + 1][( size - 1 ) / 2 + 1] = 'O';
        board[( size - 1 )/ 2 + 1][( size - 1 )/ 2] = board[( size - 1 )/ 2 ][( size - 1 ) / 2 + 1] = 'X';
        this.computer = computer;
        this.human = human;

        start = 0;
        end = 0;
        loser = 0;
        if(this.computer.getCharacter() == 'X')
            turn = CPU;
        else
            turn = HUMAN;
    }

    /*下棋过程*/
    public void begin() throws IOException {
        setDate();
        start = System.currentTimeMillis();
        printTable();
        is_not_finished:for(;!is_finished();) {
            switch (turn) {
                case CPU:
                    computer.place(board);
                    printTable();
                    change_other_number(computer,human);
                    setTurn(HUMAN);
                    break;
                case HUMAN:
                    human.place(board);
                    if (human.isIf_give_up())
                        break is_not_finished;
                    change_other_number(human, computer);
                    printTable();
                    setTurn(CPU);
                    break;
                default:
                    break;
            }
        }
        show_winner();
        end = System.currentTimeMillis();
    }

    /*输出棋盘*/
    private void printTable(){
        for(int i = 0 ; i < size; i ++){
            for(int j = 0; j < size; j++){
                System.out.print(board[i][j]);
                if( j == size - 1)
                    System.out.print("\n");
            }
        }
    }

    /*判断棋局是否结束*/
    private boolean is_finished(){
        /*棋盘满了*/
        if(computer.getNumber() + human.getNumber() == size * size){
            end_remark.append("The board is full\n");
            return true;
        }
        /*一方数目为0*/
        else if(computer.getNumber() == 0 || human.getNumber() == 0){
            return true;
        }
        /*两方均无处可下*/
         else if(computer.isOverBefore(board) && human.isOverBefore(board)){
            end_remark.append("Both players have no valid place\n");
            return true;
        }
        /*判断human是否放弃*/

        return false;
    }

    /*输出结局*/
    private void show_winner(){
        if(human.isIf_give_up()) {
            end_remark.append( human.getCharacter() + " give up. " + computer.getCharacter() + " wins.");
            out_remark.append("Human give up");
        }else {
            switch (computer.getCharacter()) {
                case 'X':
                    end_remark.append("X : O = " + computer.getNumber() + " : " + human.getNumber());
                    out_remark.append(computer.getNumber() + " : "+human.getNumber());
                    break;
                case 'O':
                    end_remark.append("X : 0 = " + human.getNumber() + " : " + computer.getNumber());
                    out_remark.append(human.getNumber() + " : " + computer.getNumber());
                    break;
                default:
                    break;
            }
            end_remark .append("\n" +( (computer.getNumber() > human.getNumber()) ? "X" : "O" + " wins."));
        }
        System.out.println(end_remark);
    }

    private void change_other_number(Chess winner, Chess loser){
        loser.setNumber(loser.getNumber() - winner.getNumOfChange());
    }

    public int getSize(){ return size; }
    public void setSize(int size){ this.size = size; }
    public long getStart(){return start;}
    public long getEnd(){return end;}
    public int getLoser(){return loser;}
    public void setTurn(String turn){ this.turn = turn; }
    public String getDate(){return date;}
    public void setDate(){
        Date dateNow = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        date = format.format(dateNow);
    }

    public Computer getComputer() {
        return computer;
    }

    private StringBuffer getEnd_remark() {
        return end_remark;
    }
    public StringBuffer getOut_remark(){return out_remark;}
}
