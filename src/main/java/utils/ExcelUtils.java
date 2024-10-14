package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private Workbook workbook;

    public ExcelUtils(String excelFilePath) {
        try {
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            workbook = WorkbookFactory.create(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Get row count for a specific sheet
    public int getRowCount(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        return sheet.getPhysicalNumberOfRows();
    }

    // Get cell data from the specified sheet, row, and column
    public String getCellData(int sheetIndex, int row, int column) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row rowData = sheet.getRow(row);
        Cell cell = rowData.getCell(column);

        if (cell == null) {
            return ""; // Return empty string if cell is null
        }
        return cell.getStringCellValue();
    }

    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
