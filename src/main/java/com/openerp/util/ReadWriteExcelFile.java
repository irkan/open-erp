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

	public static File organizationXLSXFile(List<Organization> organizations, String page) throws IOException {
		File file = new File(page+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(page) ;
		int rownum=0;
		for(Organization organization: organizations){
			XSSFRow row = sheet.createRow(rownum++);
			XSSFCell cell = row.createCell(0);
			cell.setCellValue(organization.getId());
			cell = row.createCell(1);
			cell.setCellValue(organization.getName());
			cell = row.createCell(2);
			cell.setCellValue(organization.getActive());
			cell = row.createCell(3);
			cell.setCellValue(organization.getCreatedDate());
			cell = row.createCell(4);
			cell.setCellValue(organization.getType()!=null?organization.getType().getName():"");
			cell = row.createCell(5);
			cell.setCellValue(organization.getDescription());
			cell = row.createCell(6);
			cell.setCellValue(organization.getOrganization()!=null?organization.getOrganization().getName():"");
			cell = row.createCell(7);
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getEmail():"");
			cell = row.createCell(8);
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getHomePhone():"");
			cell = row.createCell(9);
			cell.setCellValue(organization.getContact()!=null?organization.getContact().getMobilePhone():"");
			cell = row.createCell(10);
			cell.setCellValue((organization.getContact()!=null && organization.getContact().getCity()!=null)?organization.getContact().getCity().getName():"");
			cell = row.createCell(11);
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
			cell = row.createCell(1);
			cell.setCellValue(employee.getContractStartDate());
			cell = row.createCell(2);
			cell.setCellValue(employee.getSpecialistOrManager());
			cell = row.createCell(3);
			cell.setCellValue(employee.getBankAccountNumber());
			cell = row.createCell(4);
			cell.setCellValue(employee.getBankCardNumber());
			cell = row.createCell(5);
			cell.setCellValue((String) Util.check(employee.getContractEndDate()));
			cell = row.createCell(6);
			cell.setCellValue(employee.getSocialCardNumber());
			cell = row.createCell(7);
			cell.setCellValue(employee.getDescription());
			cell = row.createCell(8);
			cell.setCellValue(employee.getCreatedDate());
			cell = row.createCell(9);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFullName():"");
			cell = row.createCell(10);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFirstName():"");
			cell = row.createCell(11);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getLastName():"");
			cell = row.createCell(12);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getFatherName():"");
			cell = row.createCell(13);
			cell.setCellValue(employee.getPosition()!=null?employee.getPosition().getName():"");
			cell = row.createCell(14);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getDisability():false);
			cell = row.createCell(15);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getBirthday():null);
			cell = row.createCell(16);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getIdCardSerialNumber():"");
			cell = row.createCell(17);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getIdCardPinCode():"");
			cell = row.createCell(18);
			cell.setCellValue(employee.getPerson()!=null?employee.getPerson().getVoen():"");
			cell = row.createCell(19);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getGender()!=null)?employee.getPerson().getGender().getName():"");
			cell = row.createCell(20);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getMaritalStatus()!=null)?employee.getPerson().getMaritalStatus().getName():"");
			cell = row.createCell(21);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getNationality()!=null)?employee.getPerson().getNationality().getName():"");
			cell = row.createCell(22);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getEmail():"");
			cell = row.createCell(23);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(24);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(25);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null && employee.getPerson().getContact().getCity()!=null)?employee.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(26);
			cell.setCellValue((employee.getPerson()!=null && employee.getPerson().getContact()!=null)?employee.getPerson().getContact().getAddress():"");
			cell = row.createCell(27);
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
			cell = row.createCell(1);
			cell.setCellValue(nonWorkingDay.getIdentifier());
			cell = row.createCell(2);
			cell.setCellValue(nonWorkingDay.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(nonWorkingDay.getNonWorkingDate());
			cell = row.createCell(4);
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
			cell = row.createCell(1);
			cell.setCellValue(shortenedWorkingDay.getIdentifier());
			cell = row.createCell(2);
			cell.setCellValue(shortenedWorkingDay.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(shortenedWorkingDay.getWorkingDate());
			cell = row.createCell(4);
			cell.setCellValue(shortenedWorkingDay.getShortenedTime());
			cell = row.createCell(5);
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
			cell = row.createCell(1);
			cell.setCellValue(vacation.getIdentifier()!=null?vacation.getIdentifier().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(vacation.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(vacation.getStartDate());
			cell = row.createCell(4);
			cell.setCellValue(vacation.getEndDate());
			cell = row.createCell(5);
			cell.setCellValue(vacation.getActive());
			cell = row.createCell(6);
			cell.setCellValue((vacation.getEmployee()!=null && vacation.getEmployee().getPerson()!=null)?vacation.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(7);
			cell.setCellValue(vacation.getOrganization()!=null?vacation.getOrganization().getName():"");
			cell = row.createCell(8);
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
			cell = row.createCell(1);
			cell.setCellValue(businessTrip.getIdentifier()!=null?businessTrip.getIdentifier().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(businessTrip.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(businessTrip.getStartDate());
			cell = row.createCell(4);
			cell.setCellValue(businessTrip.getEndDate());
			cell = row.createCell(5);
			cell.setCellValue(businessTrip.getActive());
			cell = row.createCell(6);
			cell.setCellValue((businessTrip.getEmployee()!=null && businessTrip.getEmployee().getPerson()!=null)?businessTrip.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(7);
			cell.setCellValue(businessTrip.getOrganization()!=null?businessTrip.getOrganization().getName():"");
			cell = row.createCell(8);
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
			cell = row.createCell(1);
			cell.setCellValue(illness.getIdentifier()!=null?illness.getIdentifier().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(illness.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(illness.getStartDate());
			cell = row.createCell(4);
			cell.setCellValue(illness.getEndDate());
			cell = row.createCell(5);
			cell.setCellValue(illness.getActive());
			cell = row.createCell(6);
			cell.setCellValue((illness.getEmployee()!=null && illness.getEmployee().getPerson()!=null)?illness.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(7);
			cell.setCellValue(illness.getOrganization()!=null?illness.getOrganization().getName():"");
			cell = row.createCell(8);
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
			cell = row.createCell(1);
			cell.setCellValue(inventory.getGroup()!=null?inventory.getGroup().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(inventory.getName());
			cell = row.createCell(3);
			cell.setCellValue(inventory.getBarcode());
			cell = row.createCell(4);
			cell.setCellValue(inventory.getDescription());
			cell = row.createCell(5);
			cell.setCellValue(inventory.getInventoryDate());
			cell = row.createCell(6);
			cell.setCellValue(inventory.getOrganization()!=null?inventory.getOrganization().getName():"");
			cell = row.createCell(7);
			cell.setCellValue(inventory.getOld());
			cell = row.createCell(8);
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
			cell = row.createCell(1);
			cell.setCellValue(supplier.getName());
			cell = row.createCell(2);
			cell.setCellValue(supplier.getContractDate());
			cell = row.createCell(3);
			cell.setCellValue(supplier.getDescription());
			cell = row.createCell(4);
			cell.setCellValue(supplier.getPerson()!=null?supplier.getPerson().getFullName():"");
			cell = row.createCell(5);
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getEmail():"");
			cell = row.createCell(6);
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(7);
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null)?supplier.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(8);
			cell.setCellValue((supplier.getPerson()!=null && supplier.getPerson().getContact()!=null && supplier.getPerson().getContact().getCity()!=null)?supplier.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(9);
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
			cell = row.createCell(1);
			cell.setCellValue(action.getAction()!=null?action.getAction().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(action.getAmount());
			cell = row.createCell(3);
			cell.setCellValue(action.getApprove());
			cell = row.createCell(4);
			cell.setCellValue((String) Util.check(action.getApproveDate()));
			cell = row.createCell(5);
			cell.setCellValue(action.getOrganization()!=null?action.getOrganization().getName():"");
			cell = row.createCell(6);
			cell.setCellValue(action.getActionDate());
			cell = row.createCell(7);
			cell.setCellValue(action.getActive());
			cell = row.createCell(8);
			cell.setCellValue((action.getEmployee()!=null && action.getEmployee().getPerson()!=null)?action.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(9);
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
			cell = row.createCell(1);
			cell.setCellValue(transaction.getAction()!=null?transaction.getAction().getName():"");
			cell = row.createCell(2);
			cell.setCellValue(transaction.getAccount()!=null?transaction.getAccount().getAccountNumber():"");
			cell = row.createCell(3);
			cell.setCellValue(transaction.getApprove());
			cell = row.createCell(4);
			cell.setCellValue(Util.checkNull(transaction.getApproveDate()));
			cell = row.createCell(5);
			cell.setCellValue(transaction.getOrganization()!=null?transaction.getOrganization().getName():"");
			cell = row.createCell(6);
			cell.setCellValue(transaction.getTransactionDate());
			cell = row.createCell(7);
			cell.setCellValue(transaction.getTransaction()!=null?transaction.getTransaction().getId():0);
			cell = row.createCell(8);
			cell.setCellValue(transaction.getDebt());
			cell = row.createCell(9);
			cell.setCellValue(transaction.getAmount());
			cell = row.createCell(10);
			cell.setCellValue(transaction.getPrice());
			cell = row.createCell(11);
			cell.setCellValue(transaction.getCurrency());
			cell = row.createCell(12);
			cell.setCellValue(transaction.getRate());
			cell = row.createCell(13);
			cell.setCellValue(transaction.getSumPrice());
			cell = row.createCell(14);
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
			cell = row.createCell(1);
			cell.setCellValue(account.getAccountNumber());
			cell = row.createCell(2);
			cell.setCellValue(account.getCurrency());
			cell = row.createCell(3);
			cell.setCellValue(account.getBankAccountNumber());
			cell = row.createCell(4);
			cell.setCellValue(account.getBankCode());
			cell = row.createCell(5);
			cell.setCellValue(account.getBankName());
			cell = row.createCell(6);
			cell.setCellValue(account.getBankSwiftBic());
			cell = row.createCell(7);
			cell.setCellValue(account.getBalance());
			cell = row.createCell(8);
			cell.setCellValue(account.getDescription());
			cell = row.createCell(9);
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
			cell = row.createCell(1);
			cell.setCellValue(financing.getPrice());
			cell = row.createCell(2);
			cell.setCellValue(financing.getCurrency());
			cell = row.createCell(3);
			cell.setCellValue(financing.getFinancingDate());
			cell = row.createCell(4);
			cell.setCellValue(financing.getActive());
			cell = row.createCell(5);
			cell.setCellValue(financing.getInventory()!=null?financing.getInventory().getName():"");
			cell = row.createCell(6);
			cell.setCellValue(financing.getInventory()!=null?financing.getInventory().getBarcode():"");
			cell = row.createCell(7);
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
			cell = row.createCell(1);
			cell.setCellValue(advance.getPayed());
			cell = row.createCell(2);
			cell.setCellValue(advance.getActive());
			cell = row.createCell(3);
			cell.setCellValue(advance.getAdvanceDate());
			cell = row.createCell(4);
			cell.setCellValue(advance.getDebt());
			cell = row.createCell(5);
			cell.setCellValue(advance.getDescription());
			cell = row.createCell(6);
			cell.setCellValue(advance.getApprove());
			cell = row.createCell(7);
			cell.setCellValue(Util.checkNull(advance.getApproveDate()));
			cell = row.createCell(8);
			cell.setCellValue(advance.getFormula());
			cell = row.createCell(9);
			cell.setCellValue((advance.getEmployee()!=null && advance.getEmployee().getPerson()!=null)?advance.getEmployee().getPerson().getFullName():"");
			cell = row.createCell(10);
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
			cell = row.createCell(1);
			cell.setCellValue(customer.getContractDate());
			cell = row.createCell(2);
			cell.setCellValue(customer.getDescription());
			cell = row.createCell(3);
			cell.setCellValue(customer.getActive());
			cell = row.createCell(4);
			cell.setCellValue(customer.getOrganization()!=null?customer.getOrganization().getName():"");
			cell = row.createCell(5);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFullName():"");
			cell = row.createCell(6);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFirstName():"");
			cell = row.createCell(7);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getLastName():"");
			cell = row.createCell(8);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getFatherName():"");
			cell = row.createCell(9);
			cell.setCellValue(customer.getPerson()!=null?Util.checkNull(customer.getPerson().getBirthday()):"");
			cell = row.createCell(10);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getIdCardSerialNumber():"");
			cell = row.createCell(11);
			cell.setCellValue(customer.getPerson()!=null?customer.getPerson().getIdCardPinCode():"");
			cell = row.createCell(12);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getEmail():"");
			cell = row.createCell(13);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getHomePhone():"");
			cell = row.createCell(14);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getMobilePhone():"");
			cell = row.createCell(15);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber1():"");
			cell = row.createCell(16);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber2():"");
			cell = row.createCell(17);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getRelationalPhoneNumber3():"");
			cell = row.createCell(18);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getGeolocation():"");
			cell = row.createCell(19);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getCity()!=null)?customer.getPerson().getContact().getCity().getName():"");
			cell = row.createCell(20);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getAddress():"");
			cell = row.createCell(21);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null && customer.getPerson().getContact().getLivingCity()!=null)?customer.getPerson().getContact().getLivingCity().getName():"");
			cell = row.createCell(22);
			cell.setCellValue((customer.getPerson()!=null && customer.getPerson().getContact()!=null)?customer.getPerson().getContact().getLivingAddress():"");
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		return file;
	}

}
