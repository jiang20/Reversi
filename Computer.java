package lab1;

public class Computer extends Chess{
    private char character;
    private int number;
    private int numOfChange;
    private int reason;
    private char[] place;
    private int size;
    private int operation;
    private Direction direction;
    Computer(){
        this.character = '*';
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};
        this.size = 0;
        direction = new Direction(this);
    }
    Computer(int size, char character){
        this.character = character;
        this.number = 2;
        this.numOfChange = 0;
        this.reason = 0;
        this.place = new char[]{'*','*'};//place 是即将放置棋子的位置，为一维数组
        this.size = size;
        direction = new Direction(this);
    }

    /*计算机下棋*/
    public char[] place(char[][] board){
        int[]place;
        int amount = 0;
        if(isOverBefore(board)){
            out_when_no_place(board);
            return null;
        }
        place = direction.cpu_place(board);
        //place 是原有的位置，当它没有发生更新的时候，他会照常输出，所以关键是这个place的步骤不能执行的
        System.out.print("Computer places " + character + " at " + (char)(place[0] + 'a') + (char)(place[1] + 'a') + " \n");
        return this.place;
    }

    /*无位置可下*/
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
    public void setChangeNumber(int change){
        this.numOfChange = change;
    }//change number, and change the checkerboard(棋盘)
    public void setNumber(int number) {
        this.number = number;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setReason(int reason){this.reason = reason;}
}
