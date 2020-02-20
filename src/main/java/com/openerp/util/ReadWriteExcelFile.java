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

	public static List<IDDiscount> readXLSXFileItems(InputStream inputStream) throws IOException {
		XSSFWorkbook  wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		Iterator rows = sheet.rowIterator();
		List<IDDiscount> items = new ArrayList<>();
		while (rows.hasNext()) {
			row=(XSSFRow) rows.next();
			if(row.getRowNum()>0){
				IDDiscount item = new IDDiscount();
				item.setCode(row.getCell(0).getStringCellValue());
				item.setDiscount((double) row.getCell(1).getNumericCellValue());
				item.setDescription(row.getCell(2).getStringCellValue());

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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionary.getDictionaryType().getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionary.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionary.getAttr1());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionary.getAttr2());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionaryType.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionaryType.getAttr1());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(dictionaryType.getAttr2());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUsername());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getCreatedDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUserDetail().getPaginationSize());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUserDetail().getAdministrator());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUserDetail().getEmailNotification());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUserDetail().getSmsNotification());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(user.getUserDetail().getLanguage());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(moduleOperation.getModule().getName());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(module.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(module.getPath());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(module.getIcon());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(module.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(module.getModule()!=null?module.getModule().getName():"");
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(operation.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(operation.getPath());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(operation.getIcon());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(currencyRate.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(currencyRate.getValue());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(currencyRate.getCode());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(currencyRate.getRateDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(currencyRate.getNominal());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getMessage());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getSubject());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getTo());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getSendingDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getSent());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(notification.getCreatedDate());
			cell = row.createCell(row.getLastCellNum());
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
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getUsername());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getOperation());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getOperationDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getTableName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getType());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getRowId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(log.getEncapsulate());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File organizationXLSXFile(List<Organization> organizations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Organization organization: organizations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(organization.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getCreatedDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getType()!=null?organization.getType().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getOrganization()!=null?organization.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getEmail():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getHomePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getMobilePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((organization.getContact()!=null && organization.getContact().getCity()!=null)?organization.getContact().getCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getAddress():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File employeeXLSXFile(List<Employee> employees, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Employee employee: employees){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(employee.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getContractStartDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getSpecialistOrManager());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getBankAccountNumber());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getBankCardNumber());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((String) Util.check(employee.getContractEndDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getSocialCardNumber());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getCreatedDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFirstName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getLastName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFatherName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPosition()!=null?employee.getPosition().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getDisability():false);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getBirthday():null);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getIdCardSerialNumber():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getIdCardPinCode():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getVoen():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getGender()!=null)?employee.getPerson().getGender().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getMaritalStatus()!=null)?employee.getPerson().getMaritalStatus().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getNationality()!=null)?employee.getPerson().getNationality().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getEmail():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null && employee.getPerson().getContact().getCity()!=null)?employee.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getAddress():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(employee.getOrganization()!=null?employee.getOrganization().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File nonWorkingDayXLSXFile(Page<NonWorkingDay> nonWorkingDays, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(NonWorkingDay nonWorkingDay: nonWorkingDays){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(nonWorkingDay.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(nonWorkingDay.getIdentifier());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(nonWorkingDay.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(nonWorkingDay.getNonWorkingDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(nonWorkingDay.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File shortenedWorkingDayXLSXFile(Page<ShortenedWorkingDay> shortenedWorkingDays, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(ShortenedWorkingDay shortenedWorkingDay: shortenedWorkingDays){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(shortenedWorkingDay.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(shortenedWorkingDay.getIdentifier());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(shortenedWorkingDay.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(shortenedWorkingDay.getWorkingDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(shortenedWorkingDay.getShortenedTime());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(shortenedWorkingDay.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File vacationXLSXFile(Page<Vacation> vacations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Vacation vacation: vacations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(vacation.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getIdentifier()!=null?vacation.getIdentifier().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getStartDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getEndDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((vacation.getEmployee()!=null && vacation.getEmployee().getPerson()!=null)?vacation.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getOrganization()!=null?vacation.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(vacation.getDateRange());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File businessTripXLSXFile(Page<BusinessTrip> businessTrips, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(BusinessTrip businessTrip: businessTrips){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(businessTrip.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getIdentifier()!=null?businessTrip.getIdentifier().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getStartDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getEndDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((businessTrip.getEmployee()!=null && businessTrip.getEmployee().getPerson()!=null)?businessTrip.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getOrganization()!=null?businessTrip.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(businessTrip.getDateRange());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File illnessXLSXFile(Page<Illness> illnesses, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Illness illness: illnesses){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(illness.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getIdentifier()!=null?illness.getIdentifier().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getStartDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getEndDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((illness.getEmployee()!=null && illness.getEmployee().getPerson()!=null)?illness.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getOrganization()!=null?illness.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(illness.getDateRange());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File inventoryXLSXFile(Page<Inventory> inventories, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Inventory inventory: inventories){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(inventory.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getGroup()!=null?inventory.getGroup().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getBarcode());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getInventoryDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getOrganization()!=null?inventory.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getOld());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(inventory.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File supplierXLSXFile(List<Supplier> suppliers, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Supplier supplier: suppliers){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(supplier.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(supplier.getName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(supplier.getContractDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(supplier.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(supplier.getPerson()!=null?supplier.getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getEmail():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null && supplier.getPerson().getContact().getCity()!=null)?supplier.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getAddress():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File consolidateXLSXFile(Page<Action> actions, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Action action: actions){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(action.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getAction()!=null?action.getAction().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getAmount());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getApprove());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((String) Util.check(action.getApproveDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getOrganization()!=null?action.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getActionDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((action.getEmployee()!=null && action.getEmployee().getPerson()!=null)?action.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(action.getSupplier()!=null?action.getSupplier().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File transactionXLSXFile(Page<Transaction> transactions, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Transaction transaction: transactions){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(transaction.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getAction()!=null?transaction.getAction().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getAccount()!=null?transaction.getAccount().getAccountNumber():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getApprove());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(transaction.getApproveDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getOrganization()!=null?transaction.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getTransactionDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getTransaction()!=null?transaction.getTransaction().getId():0);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getDebt());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getAmount());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getPrice());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getCurrency());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getRate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getSumPrice());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(transaction.getBalance());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File accountXLSXFile(List<Account> accounts, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Account account: accounts){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(account.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getAccountNumber());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getCurrency());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getBankAccountNumber());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getBankCode());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getBankName());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getBankSwiftBic());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getBalance());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(account.getOrganization()!=null?account.getOrganization().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File financingXLSXFile(Page<Financing> financings, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Financing financing: financings){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(financing.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getPrice());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getCurrency());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getFinancingDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getInventory()!=null?financing.getInventory().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getInventory()!=null?financing.getInventory().getBarcode():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(financing.getOrganization()!=null?financing.getOrganization().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File advanceXLSXFile(Page<Advance> advances, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Advance advance: advances){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(advance.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getPayed());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getAdvanceDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getApprove());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(advance.getApproveDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getFormula());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((advance.getEmployee()!=null && advance.getEmployee().getPerson()!=null)?advance.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(advance.getOrganization()!=null?advance.getOrganization().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File customerXLSXFile(Page<Customer> customers, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Customer customer: customers){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(customer.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getContractDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getOrganization()!=null?customer.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFirstName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getLastName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFatherName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?Util.checkNull(customer.getPerson().getBirthday()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getIdCardSerialNumber():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getIdCardPinCode():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getEmail():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber1():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber2():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber3():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getGeolocation():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getCity()!=null)?customer.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getAddress():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getLivingCity()!=null)?customer.getPerson().getContact().getLivingCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getLivingAddress():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File salesXLSXFile(Page<Sales> sales, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Sales sale: sales){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(sale.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getSaleDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getService());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getSaled());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(sale.getGuaranteeExpire()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getOrganization()!=null?sale.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getCustomer()!=null?Util.checkNull(sale.getCustomer().getPerson().getId()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getFirstName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getLastName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getFatherName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?Util.checkNull(sale.getCustomer().getPerson().getBirthday()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getIdCardSerialNumber():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getIdCardPinCode():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer()!=null && sale.getCustomer().getPerson()!=null)?sale.getCustomer().getPerson().getVoen():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getEmail():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getHomePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getRelationalPhoneNumber1():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getRelationalPhoneNumber2():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getRelationalPhoneNumber3():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getGeolocation():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null && sale.getCustomer().getPerson().getContact().getCity()!=null)?sale.getCustomer().getPerson().getContact().getCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getAddress():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null && sale.getCustomer().getPerson().getContact().getLivingCity()!=null)?sale.getCustomer().getPerson().getContact().getLivingCity().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCustomer().getPerson()!=null && sale.getCustomer().getPerson().getContact()!=null)?sale.getCustomer().getPerson().getContact().getLivingAddress():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?sale.getPayment().getCash():false);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?sale.getPayment().getPrice():0);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?sale.getPayment().getLastPrice():0);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?sale.getPayment().getDown():0);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?Util.checkNull(sale.getPayment().getDiscount()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?Util.checkNull(sale.getPayment().getSchedulePrice()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?sale.getPayment().getDescription():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getPayment()!=null && sale.getPayment().getSchedule()!=null)?Util.checkNull(sale.getPayment().getSchedule().getName()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getPayment()!=null && sale.getPayment().getPeriod()!=null)?Util.checkNull(sale.getPayment().getPeriod().getName()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?Util.checkNull(sale.getPayment().getLatency()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?Util.checkNull(sale.getPayment().getSumOfInvoice()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getPayment()!=null?Util.checkNull(sale.getPayment().getUnpaid()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getConsole()!=null && sale.getConsole().getPerson()!=null)?sale.getConsole().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getVanLeader()!=null && sale.getVanLeader().getPerson()!=null)?sale.getVanLeader().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getDealer()!=null && sale.getDealer().getPerson()!=null)?sale.getDealer().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getCanavasser()!=null && sale.getCanavasser().getPerson()!=null)?sale.getCanavasser().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((sale.getServicer()!=null && sale.getServicer().getPerson()!=null)?sale.getServicer().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getNotServiceNext()!=null?sale.getNotServiceNext():false);
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(sale.getNotServiceNextReason()!=null?sale.getNotServiceNextReason():"");
			for(SalesInventory salesInventory: sale.getSalesInventories()){
				cell = row.createCell(row.getLastCellNum());
				cell.setCellValue(salesInventory.getInventory()!=null?salesInventory.getInventory().getName():"");
				cell = row.createCell(row.getLastCellNum());
				cell.setCellValue(salesInventory.getInventory()!=null?salesInventory.getInventory().getBarcode():"");
			}
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File demonstrationXLSXFile(Page<Demonstration> demonstrations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Demonstration demonstration: demonstrations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(demonstration.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(demonstration.getDemonstrateDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(demonstration.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(demonstration.getAmount());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(demonstration.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((demonstration.getEmployee()!=null && demonstration.getEmployee().getPerson()!=null)?demonstration.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(demonstration.getOrganization()!=null?demonstration.getOrganization().getName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File invoiceXLSXFile(Page<Invoice> invoices, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Invoice invoice: invoices){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(invoice.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getInvoiceDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getApprove());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(invoice.getApproveDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getCreditable());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getChannelReferenceCode());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getPrice());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getAdvance());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getSales()!=null?Util.checkNull(invoice.getSales().getId()):"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getOrganization()!=null?invoice.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(invoice.getPaymentChannel()!=null?invoice.getPaymentChannel().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue((invoice.getCollector()!=null && invoice.getCollector().getPerson()!=null)?invoice.getCollector().getPerson().getFullName():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File contactHistoryXLSXFile(Page<ContactHistory> contactHistories, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(ContactHistory contactHistory: contactHistories){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(contactHistory.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(contactHistory.getNextContactDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getCreatedDate());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getActive());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getOrganization()!=null?contactHistory.getOrganization().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getContactChannel()!=null?contactHistory.getContactChannel().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(contactHistory.getSales()!=null?Util.checkNull(contactHistory.getSales().getId()):"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File serviceRegulatorXLSXFile(Page<ServiceRegulator> serviceRegulators, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(ServiceRegulator serviceRegulator: serviceRegulators){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(serviceRegulator.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(serviceRegulator.getServicedDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(serviceRegulator.getServiceNotification()!=null?serviceRegulator.getServiceNotification().getName():"");
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(serviceRegulator.getSales()!=null?Util.checkNull(serviceRegulator.getSales().getId()):"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File idDiscountXLSXFile(List<IDDiscount> idDiscounts, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(IDDiscount idDiscount: idDiscounts){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(idDiscount.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(idDiscount.getCreatedDate()));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(idDiscount.getCode());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(idDiscount.getDiscount());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(idDiscount.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(idDiscount.getActive());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

	public static File endpointXLSXFile(Page<Endpoint> endpoints, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Endpoint endpoint: endpoints){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(endpoint.getId());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(Util.checkNull(endpoint.getConnectionType()!=null?endpoint.getConnectionType().getName():""));
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getHost());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getPort());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getUrl());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getDescription());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getFixedDelay());
			cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(endpoint.getLastStatusDate());
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

}
