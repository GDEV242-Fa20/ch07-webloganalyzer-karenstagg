/**
 * Read web server data and analyse hourly access patterns.
 * Supplying your own filename is an added feature.
 * Also adding ability to analyze hourly, daily, and monthly patterns.
 * 
 * @author Karen Stagg
 * @version October 19,2020
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    //Where to calculate the daily access counts.
    private int[] dayCounts;
    //Where to calculate the monthly access counts.
    private int[] monthCounts;
    //Where to calculate the yearly access counts.
    private int[] yearCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Constructor
     * Create an object to analyze hourly web accesses. A default filename 
     * of weblog.txt will be ther reader name.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts, new day/hour starts at 00.
        hourCounts = new int[24];
        
        // Create the array object to hold the daily 
        // access counts, = 32 to adjust for array starting a 0.
        dayCounts = new int[32];
        
        // Create the array object to hold the monthly 
        // access counts, = 13 to adjust for array starting a 0.
        monthCounts = new int[13];
        
        // Create the array object to hold the yearly 
        // access counts, = 6 to adjust for array starting a 0.
        yearCounts = new int[6];
        
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Overlaoded Constructor to add your own filemane instead of using default.
     * Create an object to analyze hourly web accesses.
     * @param String filename is a filename given by the user to create a new 
     * reader with a supplied name instead of the default "weblog.txt".
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts, new day/hour starts at 00.
        hourCounts = new int[24];
        
        // Create the array object to hold the daily 
        // access counts, length = 32 to adjust for array starting at 0.
        dayCounts = new int[32];
        
        // Create the array object to hold the monthly 
        // access counts, length = 13 to adjust for array starting a 0.
        monthCounts = new int[13];
        
        // Create the array object to hold the yearly 
        // access counts, = 6 to adjust for array starting a 0.
        yearCounts = new int[6];
        
        // Create the reader to obtain the data.
        reader = new LogfileReader(filename);
    } 

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
      /**
     * Analyze the daily access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
        //Add back into array the days that didn't have activity
        for (int i = 0; i < dayCounts.length ; i++)
        {
            if (dayCounts[i] < 0)
            {
                dayCounts[i] = 0;
            }
        }
        
    }    
    
    /**
     * Analyze the monthly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month]++;
        }
        //Add back into array the months that didn't have activity
        for (int i = 0; i < monthCounts.length ; i++)
        {
            if (monthCounts[i] < 0)
            {
                monthCounts[i] = 0;
            }
        }
    }
    
    /**
     * Analyze the yearly access data from the log file.
     */
    public void analyzeyYearlyData()
    {
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int year = entry.getYear();
            yearCounts[year]++;
        }
        //Add back into array the years that didn't have activity
        for (int i = 0; i < yearCounts.length ; i++)
        {
            if (yearCounts[i] < 0)
            {
                yearCounts[i] = 0;
            }
        }
    }
    
    /**
     * Find the busiest hour (most accesses) from the log file.
     * @return returns an integer for the busiest hour
     */
    public int busiestHour()
    {
        int busiestHour = 0;
        //Start comparision of hour 0 to hour 1
        for (int hour = 1; hour < hourCounts.length; hour++) 
        {
            if (hourCounts[hour] > hourCounts[busiestHour])
            {
                busiestHour = hour;
            }    
        } 
        return busiestHour;
    }

    /**
     * Finds the quietest hour (least accesses) from the log file.
     * @return returns an integer for the quietest hour
     */
    public int quietestHour()
    {
        int quietestHour = 0;
        //Start comparision of hour 0 to hour 1
        for (int hour = 1; hour < hourCounts.length; hour++) 
        {
            if (hourCounts[hour] < hourCounts[quietestHour])
            {
                quietestHour = hour;
            }    
        } 
        return quietestHour;
    }

    /**
     * Finds the busiest consecutive two hours (most accesses) from the log file.
     * @return returns an integer for the starting hour of the busiest two hour period
     */
    public int busiestTwoHour()
    {
        int startBusiestTwoHour = 0;
        int busiestTwoHourTotal = 0;
        //Only need to go to hour 22 to compare hour 22 to hour 23
        for (int hour = 0; hour < hourCounts.length -1; hour++) 
        {
            if (hourCounts[hour] + hourCounts[hour + 1] >  busiestTwoHourTotal)
                      
            {            
                    startBusiestTwoHour = hour;
                    busiestTwoHourTotal = hourCounts[hour] + hourCounts[hour + 1];
            }    
        } 
        return startBusiestTwoHour;
    }
    /**
     * Return the number of hourly accesses recorded in the log file.
     * @return total is the int value of the number of hourly accesses recorded 
     * in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        //Add the value in each element of hourCounts to total.
        for (int index = 0; index < hourCounts.length; index++)
        {
        total += hourCounts[index];
        }
        return total;
    }

    /**
     * Find the busiest day (most accesses) from the log file.
     * @return returns an integer for the busiest day
     */
    public int busiestDay()
    {
        //Call the method to establish daily data
        analyzeDailyData();     
        int busiestDay = 1;
        //Start comparision of day 1 to day 2
        for (int day = 1; day < dayCounts.length; day++) 
        {
                if (dayCounts[day] > dayCounts[busiestDay])
                {
                    busiestDay = day;
                } 
        }
        return busiestDay;
    }
    
       /**
     * Find the quietest day (least accesses)  of greater than 0 from the log file.
     * @return returns an integer for the quietest day greater than 0.
     */
    public int quietestDay()
    {
        //Call the method to establish daily data
        analyzeDailyData();     
        int quietestDay = 1;
        //Start comparision of day 1 to day 2
        for (int day = 2; day < dayCounts.length; day++) 
        {
                if (dayCounts[day] < dayCounts[quietestDay] && dayCounts[day] > 0)
                {
                    quietestDay = day;
                } 
        }
        return quietestDay;
    }

    /**
     * Return and display the total number of accesses per month recorded in the log file.
     * @return total is the int value of the number of monthly accesses recorded 
     * in the log file.
     */
    public int totalAccessesPerMonth()
    {
        //Call the method to establish monthly data.
        analyzeMonthlyData();
        int total = 0;
        System.out.println("Mo: Count");
        //Add the value in each element of monthCounts to total.
        for (int month = 1 ; month < monthCounts.length; month++)
        {
            System.out.println(month + ": " + monthCounts[month]);
            total += monthCounts[month];
        }
        System.out.println("Grand total of monthly accesses is: " + total);
        return total;
    }
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
