package ru.vkbot;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Inform {
    public int test = 0;
    public String _Done = "Не найдена";
    public int indexCellGroup;
    public StringBuilder otvet = new StringBuilder();


    public String find (String group) throws IOException {
        File myFolder = new File("C:\\Расписание");
        File[] files = myFolder.listFiles();
        for (int i=0; i< files.length; i++){
            if (SearchGroup(group, files[i].toString()) == 1){
                _Done = printExcel(files[i].toString());
                break;
            }
            else{
                _Done = "Не найдена";
            }
        }
        return _Done;
    }
    public int SearchGroup(String group, String puti) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(puti);
        Workbook wb = new HSSFWorkbook(fileInputStream);
        for (Row row : wb.getSheetAt(0)) {
            for (Cell cell : row) {
                Cell r = cell;
                if (getCellText(cell).equals(group)){
                    indexCellGroup = r.getColumnIndex();
                    printExcel(puti);
                    test = 1;
                }

            }
        }
        return test;
    }

    public String printExcel(String puti) throws IOException{
        FileInputStream fis = new FileInputStream(puti);
        Workbook wb1 = new HSSFWorkbook(fis);
        int newDay = 0;
        int indexRow = 4;


        for(int i1 = 0; i1 < 60 && test != 1; i1++){
            String predmet = getCellText(wb1.getSheetAt(0).getRow(indexRow + i1 + newDay).getCell(indexCellGroup))+"\n";
            otvet.append(predmet);
        }

        return otvet.toString();


    }



    public String getCellText( Cell cell){
        String res=" ";
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                res = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    res = cell.getDateCellValue().toString() ;
                }
                else {
                    res = Double.toString(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                res = Boolean.toString(cell.getBooleanCellValue()) ;
                break;
            case Cell.CELL_TYPE_FORMULA:
                res = cell.getCellFormula().toString() ;
                break;
            default:
                res = "\n";
                break;
        }
        return res;
    }
}
