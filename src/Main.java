// Taiwan 50 ETF Investment Strategy Backtesting
// The data were downloaded from https://hk.finance.yahoo.com/quote/0050.TW/history/ .

public class Main {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    
    static StockData stockData;
	final day = 60;
    
    public static void main(String[] args) {       
                      
        stockData = new StockData(args[0]);
        
        String[] startMonth = {"2008/10",
                               "2009/10",        
                               "2010/10",
                               "2011/10",
                               "2012/10",
                               "2013/10",
                               "2014/10",
                               "2015/10", 
                               "2016/10",
                               "2017/10",
                               "2018/10",
                               "2019/10",
                               "2020/10",
                               "2021/10",  
                               "2022/10",  
                               "2023/10"};

        
        for (int i = 0; i < startMonth.length; i++) {
            
            Strategy1(startMonth[i], day);
        }
    }    
        
    // Strategy1 : Buy in October and sell with 10% gain or -20% loss. 
    public static void Strategy1(String startMonth, int day) {

        float buyPrice, sellPrice, profit;
        String buyDate, sellDate;

        int rowNumber = stockData.findRowNumber(startMonth);        
        String date = stockData.getDate(rowNumber);
        
        float closePrice = stockData.getClosePrice(date);        
        float averagePrice = stockData.getMovingAverage(date, day);    
        
        // buy
        while (closePrice >= averagePrice) {
            
            date = stockData.getDate(++rowNumber);
            closePrice = stockData.getClosePrice(date);
            averagePrice = stockData.getMovingAverage(date, day);          
        }       
        
        buyPrice = closePrice;
        buyDate = date;
        
        // sell
        while (true) {
            
            date = stockData.getDate(++rowNumber);
            closePrice = stockData.getClosePrice(date);
            
            if (((closePrice / buyPrice) >= 1.1) || ((closePrice / buyPrice) <= 0.8)) {

                break;
            }
        }  

        sellPrice = closePrice;
        sellDate = date;
        
        // profit
        profit = (sellPrice - buyPrice) / buyPrice;

        if (profit < 0)
            System.out.println("profit = " + ANSI_RED + String.format("%+.2f%%", profit*100) + ANSI_RESET + " (" + buyDate + "~" + sellDate + ")");
        else
            System.out.println("profit = " + String.format("%+.2f%%", profit*100) + " (" + buyDate + "~" + sellDate + ")");
    }     
}
