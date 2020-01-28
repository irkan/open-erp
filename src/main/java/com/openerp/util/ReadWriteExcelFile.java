package com.openerp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.openerp.entity.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

public class ReadWriteExcelFile {

	public static void readXLSFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Test.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}
	
	public static void writeXLSFile() throws IOException {
		
		String excelFileName = "C:/Test.xls";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public static List<Item> readXLSXFileItems(InputStream inputStream) throws IOException {
		XSSFWorkbook  wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		Iterator rows = sheet.rowIterator();
		List<Item> items = new ArrayList<>();
		while (rows.hasNext()) {
			row=(XSSFRow) rows.next();
			if(row.getRowNum()>0){
				Item item = new Item();
				item.setBarcode(row.getCell(0).getStringCellValue());
				item.setName(row.getCell(1).getStringCellValue());
				item.setDescription(row.getCell(2).getStringCellValue());
				item.setAmount((int)row.getCell(3).getNumericCellValue());
				item.setPrice((double) row.getCell(4).getNumericCellValue());
				items.add(item);
			}
		}
		return items;
	}

	public static List<ShortenedWorkingDay> readXLSXFileShortenedWorkingDays(InputStream inputStream) throws IOException {
		XSSFWorkbook  wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		Iterator rows = sheet.rowIterator();
		List<ShortenedWorkingDay> shortenedWorkingDays = new ArrayList<>();
		while (rows.hasNext()) {
			row=(XSSFRow) rows.next();
			if(row.getRowNum()>0){
				ShortenedWorkingDay shortenedWorkingDay = new ShortenedWorkingDay();
				double value = row.getCell(0).getNumericCellValue()*24*60*60*1000-2209176000000l;
				shortenedWorkingDay.setWorkingDate(new Date((long) value));
				shortenedWorkingDay.setIdentifier(row.getCell(1).getStringCellValue());
				shortenedWorkingDay.setDescription(row.getCell(2).getStringCellValue());
				shortenedWorkingDay.setShortenedTime((int)row.getCell(3).getNumericCellValue());
				shortenedWorkingDays.add(shortenedWorkingDay);
			}
		}
		return shortenedWorkingDays;
	}
	
	public static List<NonWorkingDay> readXLSXFileNonWorkingDays(InputStream inputStream) throws IOException {
		XSSFWorkbook  wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		Iterator rows = sheet.rowIterator();
		List<NonWorkingDay> nonWorkingDays = new ArrayList<>();
		while (rows.hasNext()) {
			row=(XSSFRow) rows.next();
			if(row.getRowNum()>0){
				NonWorkingDay nonWorkingDay = new NonWorkingDay();
				double value = row.getCell(0).getNumericCellValue()*24*60*60*1000-2209176000000l;
				nonWorkingDay.setNonWorkingDate(new Date((long) value));
				nonWorkingDay.setIdentifier(row.getCell(1).getStringCellValue());
				nonWorkingDay.setDescription(row.getCell(2).getStringCellValue());
				nonWorkingDays.add(nonWorkingDay);
			}
		}
		return nonWorkingDays;
	}
	
	public static void writeXLSXFile() throws IOException {
		
		String excelFileName = "C:/Test.xlsx";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue("Cell "+r+" "+c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	public static File dictionaryXLSXFile(List<Dictionary> dictionaries, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Dictionary dictionary: dictionaries){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(dictionary.getId());
			cell = row.createCell(1);
			cell.setCellValue(dictionary.getDictionaryType().getName());
			cell = row.createCell(2);
			cell.setCellValue(dictionary.getName());
			cell = row.createCell(3);
			cell.setCellValue(dictionary.getAttr1());
			cell = row.createCell(4);
			cell.setCellValue(dictionary.getAttr2());
			cell = row.createCell(5);
			cell.setCellValue(dictionary.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File dictionaryTypeXLSXFile(List<DictionaryType> dictionaryTypes, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(DictionaryType dictionaryType: dictionaryTypes){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(dictionaryType.getId());
			cell = row.createCell(1);
			cell.setCellValue(dictionaryType.getName());
			cell = row.createCell(2);
			cell.setCellValue(dictionaryType.getAttr1());
			cell = row.createCell(3);
			cell.setCellValue(dictionaryType.getAttr2());
			cell = row.createCell(4);
			cell.setCellValue(dictionaryType.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File userXLSXFile(List<User> users, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(User user: users){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(user.getId());
			cell = row.createCell(1);
			cell.setCellValue(user.getUsername());
			cell = row.createCell(2);
			cell.setCellValue(user.getCreatedDate());
			cell = row.createCell(3);
			cell.setCellValue(user.getActive());
			cell = row.createCell(5);
			cell.setCellValue(user.getUserDetail().getPaginationSize());
			cell = row.createCell(6);
			cell.setCellValue(user.getUserDetail().getAdministrator());
			cell = row.createCell(7);
			cell.setCellValue(user.getUserDetail().getEmailNotification());
			cell = row.createCell(8);
			cell.setCellValue(user.getUserDetail().getSmsNotification());
			cell = row.createCell(9);
			cell.setCellValue(user.getUserDetail().getLanguage());
			cell = row.createCell(10);
			cell.setCellValue(user.getEmployee().getPerson().getFullName());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File moduleOperationXLSXFile(List<ModuleOperation> moduleOperations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(ModuleOperation moduleOperation: moduleOperations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(moduleOperation.getId());
			cell = row.createCell(1);
			cell.setCellValue(moduleOperation.getModule().getName());
			cell = row.createCell(2);
			cell.setCellValue(moduleOperation.getOperation().getName());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File moduleXLSXFile(List<Module> modules, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Module module: modules){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(module.getId());
			cell = row.createCell(1);
			cell.setCellValue(module.getName());
			cell = row.createCell(2);
			cell.setCellValue(module.getPath());
			cell = row.createCell(3);
			cell.setCellValue(module.getIcon());
			cell = row.createCell(4);
			cell.setCellValue(module.getActive());
			cell = row.createCell(5);
			cell.setCellValue(module.getModule()!=null?module.getModule().getName():"");
			cell = row.createCell(6);
			cell.setCellValue(module.getDescription());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File operationXLSXFile(List<Operation> operations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Operation operation: operations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(operation.getId());
			cell = row.createCell(1);
			cell.setCellValue(operation.getName());
			cell = row.createCell(2);
			cell.setCellValue(operation.getPath());
			cell = row.createCell(3);
			cell.setCellValue(operation.getIcon());
			cell = row.createCell(4);
			cell.setCellValue(operation.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File currencyRateXLSXFile(List<CurrencyRate> currencyRates, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(CurrencyRate currencyRate: currencyRates){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(currencyRate.getId());
			cell = row.createCell(1);
			cell.setCellValue(currencyRate.getName());
			cell = row.createCell(2);
			cell.setCellValue(currencyRate.getValue());
			cell = row.createCell(3);
			cell.setCellValue(currencyRate.getCode());
			cell = row.createCell(4);
			cell.setCellValue(currencyRate.getRateDate());
			cell = row.createCell(5);
			cell.setCellValue(currencyRate.getNominal());
			cell = row.createCell(6);
			cell.setCellValue(currencyRate.getType());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File notificationRateXLSXFile(Page<Notification> notifications, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Notification notification: notifications){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(notification.getId());
			cell = row.createCell(1);
			cell.setCellValue(notification.getMessage());
			cell = row.createCell(2);
			cell.setCellValue(notification.getSubject());
			cell = row.createCell(3);
			cell.setCellValue(notification.getTo());
			cell = row.createCell(4);
			cell.setCellValue(notification.getDescription());
			cell = row.createCell(5);
			cell.setCellValue(notification.getActive());
			cell = row.createCell(6);
			cell.setCellValue(notification.getSendingDate());
			cell = row.createCell(7);
			cell.setCellValue(notification.getSent());
			cell = row.createCell(8);
			cell.setCellValue(notification.getCreatedDate());
			cell = row.createCell(9);
			cell.setCellValue(notification.getType()!=null?notification.getType().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File logRateXLSXFile(Page<Log> logs, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Log log: logs){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(log.getId());
			cell = row.createCell(1);
			cell.setCellValue(log.getUsername());
			cell = row.createCell(2);
			cell.setCellValue(log.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(log.getOperation());
			cell = row.createCell(4);
			cell.setCellValue(log.getOperationDate());
			cell = row.createCell(5);
			cell.setCellValue(log.getTableName());
			cell = row.createCell(6);
			cell.setCellValue(log.getType());
			cell = row.createCell(7);
			cell.setCellValue(log.getActive());
			cell = row.createCell(8);
			cell.setCellValue(log.getRowId());
			cell = row.createCell(9);
			cell.setCellValue(log.getEncapsulate());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static void main(String[] args) throws IOException {
		
		writeXLSFile();
		readXLSFile();
		
		writeXLSXFile();
		//readXLSXFile();

	}

}
