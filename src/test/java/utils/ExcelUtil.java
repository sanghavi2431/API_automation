package utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static Object[][] getHostSearchData(String filePath, String sheetName) {

	    List<Object[]> data = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        int lastRow = sheet.getLastRowNum();

	        for (int i = 1; i <= lastRow; i++) { // skip header

	            Row row = sheet.getRow(i);
	            if (row == null) {
	                continue; // skip empty row
	            }

	            String mobileNo = getString(row, 0);
	            double lat = getDouble(row, 1);
	            double lang = getDouble(row, 2);
	            String mode = getString(row, 3);
	            int range = (int) getDouble(row, 4);
	            String host = getString(row, 5);
	            String modeType = getString(row, 6);
	            String address = getString(row, 7);
	            String wolooType = getString(row, 8);
	            String wahScore = getString(row, 9);

	            data.add(new Object[]{
	                    mobileNo, lat, lang, mode, range,
	                    host, modeType, address, wolooType, wahScore
	            });
	        }

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to read Excel data", e);
	    }

	    return data.toArray(new Object[0][]);
	}

	private static String getString(Row row, int cellIndex) {
	    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	    return cell == null ? "" : cell.toString().trim();
	}

	private static double getDouble(Row row, int cellIndex) {
	    Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	    if (cell == null) {
	        throw new RuntimeException("Numeric cell missing at column " + cellIndex);
	    }

	    if (cell.getCellType() == CellType.NUMERIC) {
	        return cell.getNumericCellValue();
	    }

	    try {
	        return Double.parseDouble(cell.toString());
	    } catch (NumberFormatException e) {
	        throw new RuntimeException("Invalid numeric value at column " + cellIndex);
	    }
	}

	public static Object[][] getEnrouteSearchData(String filePath, String sheetName) {

	    List<Object[]> data = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheet(sheetName);
	        int lastRow = sheet.getLastRowNum();

	        for (int i = 1; i <= lastRow; i++) { // skip header

	            Row row = sheet.getRow(i);
	            if (row == null) {
	                continue; // skip empty row
	            }

	            String mobileNo = getString(row, 0);
	            double srclat = getDouble(row, 1);
	            double srclng = getDouble(row, 2);
	            double deslat = getDouble(row, 3);
	            double deslng =getDouble(row, 4);
	            String googleMode = getString(row, 5);
	            String mode = getString(row, 6);
	            String host = getString(row, 7);
	            String modeType = getString(row, 8);

	            data.add(new Object[]{
	                    mobileNo, srclat, srclng, deslat, deslng,
	                    googleMode, mode, host, modeType
	            });
	          
	        }

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to read Excel data", e);
	    }

	    return data.toArray(new Object[0][]);
	}
	
	public static List<Map<String, String>> getData(String filePath, String sheetName) throws Exception {

		List<Map<String, String>> dataList = new ArrayList<>();

		DataFormatter formatter = new DataFormatter();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook wb = WorkbookFactory.create(fis)) {

			Sheet sheet = wb.getSheet(sheetName);

			if (sheet == null) {
				throw new RuntimeException("Sheet not found: " + sheetName);
			}

			Row headerRow = sheet.getRow(0);

			if (headerRow == null) {
				throw new RuntimeException("Header row is missing");
			}

			int cols = headerRow.getLastCellNum();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {

				Row row = sheet.getRow(i);

				// Stop execution if row is null or completely empty
				if (row == null || isRowEmpty(row, cols, formatter)) {
					break;
				}

				Map<String, String> rowData = new HashMap<>();

				for (int j = 0; j < cols; j++) {

					Cell headerCell = headerRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					Cell valueCell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

					String key = formatter.formatCellValue(headerCell).trim();

					String value = formatter.formatCellValue(valueCell).trim();

					rowData.put(key, value);
				}

				dataList.add(rowData);
			}
		}

		return dataList;
	}

	private static boolean isRowEmpty(Row row, int cols, DataFormatter formatter) {

		for (int j = 0; j < cols; j++) {

			Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

			String value = formatter.formatCellValue(cell).trim();

			if (!value.isEmpty()) {
				return false;
			}
		}

		return true;
	}

}
