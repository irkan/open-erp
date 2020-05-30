package com.openerp.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class HansaUtil {
    public static void main(String[] args) throws IOException {
        InputStream ExcelFileToRead = new FileInputStream("C:\\Users\\i.ahmadov\\Desktop\\benchmarking.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

        for(int i=2; i<=4; i++){
            XSSFSheet sheet=wb.getSheetAt(i);
            XSSFRow row;
            XSSFCell cell;
            row = sheet.getRow(0);
            for(int j=1; j<row.getLastCellNum(); j++){
                cell = row.getCell(j);
                System.out.println("if(bpnr.name==\""+cell.getStringCellValue()+"\") then begin");
                for(int k=1; k<=sheet.getLastRowNum(); k++){
                    if(getNumeric(sheet.getRow(k).getCell(j)).intValue()==1){
                        System.out.println("  newINrw."+getString(sheet.getRow(k).getCell(0)).split("-")[1]+"=\"*\";");
                    }
                   // System.out.println(getString(sheet.getRow(k).getCell(0)) + " " + k + ". " + getNumeric(sheet.getRow(k).getCell(j)));
                }
                System.out.println("end;");
                System.out.println();
            }
        }
    }

    private static Double getNumeric(XSSFCell cell){
        try {
            return cell.getNumericCellValue();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0d;
    }

    private static String getString(XSSFCell cell){
        try {
            return cell.getStringCellValue();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
