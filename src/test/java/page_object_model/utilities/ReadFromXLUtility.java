package page_object_model.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFromXLUtility {
    public ArrayList<ArrayList<String>> readDataFromXL(String fileName, String sheetName){
        DataFormatter dataFormatter = new DataFormatter(); // POI data formatter to read all Excel values as Strings only
        ArrayList<ArrayList<String>> dataMatrix = new ArrayList<>(); // 2D arraylist to store data from Excel
        try {
            FileInputStream fip = new FileInputStream(new File(fileName)); // All data is saved in this file, in separate sheets
            XSSFWorkbook workbook = new XSSFWorkbook(fip);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> iterator = sheet.rowIterator();
            int maxColumns = sheet.getRow(0).getPhysicalNumberOfCells(); // Number of columns in header, to compensate empty cells of data rows with "" in retrieval for testing
            if (iterator.hasNext()) {
                iterator.next(); // Skip the first row (header)
            }
            while(iterator.hasNext()){
                Row row = iterator.next();
                ArrayList<String> matrixRow = new ArrayList<>(); // 1D arraylist to store a row
                for (int i = 0; i < maxColumns; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String cellValue = "";
                    // Using if else to resolve numbers into strings
                    switch (cell.getCellType()) {
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            cellValue = dataFormatter.formatCellValue(cell); // Convert numeric to String
                            break;
                        case BLANK:
                            cellValue = ""; // Keep empty cells as empty strings, to test empty form inputs
                            break;
                        default:
                            System.out.println("Wrong format");
                    }
                    matrixRow.add(cellValue); // Add value to 1D arraylist
                }
                dataMatrix.add(matrixRow);  // Add 1D arraylist to 2D arraylist as a row
            }
            return dataMatrix;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
