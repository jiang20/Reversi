package lab1;


import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;

//excel文件的修改必须是在副本的基础上进行修改，因为对象是只读的，所以如果要修改Excel，需要创建一个可写的副本，副本指向原Excel文件
public class InFile {
//    private File file;
//    private Workbook book;
//    private String excelExtName;
//    static int number;
//    private Sheet sheet;
//    InFile(String filename) {
//        excelExtName = "";
//        number = 0;
//        file = new File(filename);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        for (int i = filename.length() - 1; i >= 0; i--) {
//            if (filename.charAt(i) == '.')
//                break;
//            excelExtName = filename.charAt(i) + excelExtName;
//        }
//        System.out.print(excelExtName);
//        if (excelExtName.equals("xls"))
//            book = new HSSFWorkbook();
//
//        else if ("xlsx".equals(excelExtName)) {
//            book = new XSSFWorkbook();
//        }
//        else return;
//        sheet = book.createSheet();
//    }
    public static void out(String filename,Board board) throws IOException, WriteException, BiffException {
        String[] strings = getRes(board);
        getFile(filename);
        setFile(filename,board);
    }
    //创建Excel文件
    private static void getFile(String filename) throws IOException, WriteException {
        File file = new File(filename);
        WritableWorkbook writableWorkbook;
        WritableSheet writableSheet;
        if(!file.exists()) {
            writableWorkbook = Workbook.createWorkbook(file);
            writableSheet = writableWorkbook.createSheet("first", 0);
            writableWorkbook.write();
            writableWorkbook.close();
        }
    }
    //创建Excel文件的副本来进行修改
    private static void setFile(String filename,Board board){
        File file = new File(filename);
        Workbook workbook = null;
        WritableWorkbook wb = null;
        WritableSheet sheet = null;
        try {
            workbook = Workbook.getWorkbook(file);
            wb = Workbook.createWorkbook(file,workbook);
            sheet = wb.getSheet(0);
            WritableFont font = new WritableFont(WritableFont.TIMES,10,WritableFont.NO_BOLD);
            WritableCellFormat writableCellFormat = new WritableCellFormat(font);
            int rowNum = sheet.getRows();
            String[] strings = getRes(board);
            for(int i = 0; i < 6; i++) {
                Label label = new Label(i, rowNum, strings[i] ,writableCellFormat);
                sheet.addCell(label);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert wb != null;
                wb.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                wb.close();
            } catch (WriteException | IOException e) {
                e.printStackTrace();
            }
            workbook.close();
        }
    }
    //根据board得到应该输进文件中的内容
    private static String[] getRes(Board board){
        String[] strings = new String[6];
        strings[0] = board.getDate() +"";
        strings[1] = (( board.getEnd() - board.getStart() ) / 1000)+"";
        strings[2] = board.getSize() + "*" +board.getSize();
        strings[3] = (board.getCom() == 1)?"computer":"human";
        strings[4] = (board.getCom() != 1)?"computer":"human";
        switch (board.getReason()){
            case 1:;
            case 2:;
            case 3:strings[5] = board.getBlack().getNumber()+" to "+board.getWhite().getNumber();break;
            case 4:strings[5] = (board.getLoser() == 1)?"computer gave up":"human gave up";break;
        }
        return strings;
    }

}
