package lab1;


import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import java.io.*;

//excel文件的修改必须是在副本的基础上进行修改，因为对象是只读的，所以如果要修改Excel，需要创建一个可写的副本，副本指向原Excel文件
public class InFile {
    static String[] strings;
    static int number;
    public static void out(String fileName,Board board) throws IOException, WriteException, BiffException {
        strings = getRes(board);
        write_file(fileName);
    }

    private static void write_file(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file , true));
        String str = "";
        try {
            number++;
            int i = 0;
            while(i < strings.length) {
                str = strings[i++];
                bw.write(str+",");
            }
            bw.newLine();
            } catch (IOException e) {
            e.printStackTrace();
            }finally {
            bw.close();
        }
    }

    /*根据board的内容得到要写进文件的内容*/
    private static String[] getRes(Board board){
        String[] strings = new String[6];
        strings[0] = board.getDate() +"";
        strings[1] = (( board.getEnd() - board.getStart() ) / 1000)+"";
        strings[2] = board.getSize() + "*" +board.getSize();
        strings[3] = (board.getComputer().getCharacter() == 'X')?"computer":"human";
        strings[4] = (strings[3].equals("human"))?"computer":"human";
        strings[5] = board.getOut_remark().toString();
        return strings;
    }

}
