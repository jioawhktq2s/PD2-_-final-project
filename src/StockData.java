import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StockData {
    String fileName;

    StockData(String fileName) {
        this.fileName = fileName;
    }
    
    // within column 0, find the row which contains the date
    // in  : date
    // out : rowNumber (start from 0)
    int findRowNumber(String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] columnCells = line.split(",");                

                if (columnCells.length > 0 && columnCells[0].contains(date)) {
                    reader.close();
                    return rowNumber;
                }
                rowNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    // within row 0, find the column which equals the columnName
    // in  : columnName
    // out : columnNumber (start from 0)   
    int findColumnNumber(String columnName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int columnNumber = 1;
            
            if ((line = reader.readLine()) != null) {
                String[] columnCells = line.split(",");

                while (columnNumber <= columnCells.length) {
                    if (columnCells[columnNumber - 1].equals(columnName)) {
                        reader.close();
                        return columnNumber - 1;
                    }
                    columnNumber++;
                }
            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // find the close price of the date
    // in  : date
    // out : close price   
    float getClosePrice(String date) {
        int rowNumber = findRowNumber(date);
        
        try {	
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            int rowCount = 1;

            // Skipping rows until reaching the desired row number
            while (rowCount < rowNumber) {
                reader.readLine();
                rowCount++;
            }

            line = reader.readLine();
            String[] columnCells = line.split(",");
            float cellValue = Float.parseFloat(columnCells[4]);

            reader.close();
            return cellValue;

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // find the the date which is in column 0 of the input rowNumber
    // in  : rowNumber
    // out : date 
    String getDate(int rowNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            int rowCount = 1;

            // Skipping rows until reaching the desired row number
            while (rowCount < rowNumber) {
                reader.readLine();
                rowCount++;
            }

            line = reader.readLine();
            String[] columnCells = line.split(",");
            String cellValue = columnCells[0];

            reader.close();
            return cellValue;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // calculate the moving average
    // in  : date, day
    // out : moving average of the days
    float getMovingAverage(String date, int day) {
        int rowNumber = findRowNumber(date) - day;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line;
            int rowCount = 1;
            float sum = 0;

            // Skipping rows until reaching the desired row number
            while (rowCount < rowNumber) {
                reader.readLine();
                rowCount++;
            }

            // Calculating sum
            int count = day;
            while ((line = reader.readLine()) != null && count > 0) {
                String[] columnCells = line.split(",");
                sum += Float.parseFloat(columnCells[4]);
                count--;
            }
            reader.close();
            return sum/day;
            
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }            
}
