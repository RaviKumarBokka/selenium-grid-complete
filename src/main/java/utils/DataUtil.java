package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

public class DataUtil {
    public static List<Map<String, String>> readJson(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filePath), new TypeReference<List<Map<String, String>>>() {});
    }

    public static String[][] readExcel(String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows() - 1;
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] data = new String[rows][cols];
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                data[i-1][j] = row.getCell(j).toString();
            }
        }
        wb.close();
        return data;
    }
}