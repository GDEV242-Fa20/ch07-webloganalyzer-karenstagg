/**
 * Read web server data and analyse hourly, daily and monthly access patterns.
 * Supplying your own filename is an added feature.
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
     * Analyze the hour, day, and month access data from the log file.
     */
    public void analyzeData()
    {
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
            int day = entry.getDay();
            dayCounts[day]++;
            int month = entry.getMonth();
            monthCounts[month]++;
        }    
        //Add back into array the days that didn't have activity due to small sample size
        for (int i = 0; i < dayCounts.length ; i++)
            {
            if (dayCounts[i] < 0)
            {
                dayCounts[i] = 0;
            }
        }
        //Add back into array the months that didn't have activity due to small sample size
        for (int i = 0; i < monthCounts.length ; i++)
        {
            if (monthCounts[i] < 0)
            {
                monthCounts[i] = 0;
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
     * @return returns an integer for the quietest day greater than 0 (because of small sample size)
     */
    public int quietestDay()
    {
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
     * DISPLAYS and Returns the detail of the total number of accesses per month recorded in the log file.
     * @return total is the int value of the total number of monthly accesses recorded 
     * in the log file.
     */
    public int totalAccessesPerMonth()
    {
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
     * Find the quietest month (least accesses)  of greater than 0 from the log file.
     * @return returns an integer for the quietest month greater than 0 (because of small sample size)
     */
    public int quietestMonth()
    {
        int quietestMonth = 1;
        //Start comparision of month 1 to day 2
        for (int month = 2; month < monthCounts.length; month++) 
        {
                if (monthCounts[month] < monthCounts[quietestMonth] && monthCounts[month] > 0)
                {
                    quietestMonth = month;
                } 
        }
        return quietestMonth;
    }
    
    /**
     * Find the busiest month (most accesses) from the log file.
     * @return returns an integer for the busiest month
     */
    public int busiestMonth()
    {
        int busiestMonth = 1;
        //Start comparision of day 1 to day 2
        for (int month = 1; month < monthCounts.length; month++) 
        {
                if (monthCounts[month] > monthCounts[busiestMonth])
                {
                    busiestMonth = month;
                } 
        }
        return busiestMonth;
    }    
    
    /**
     * Displays and returns the average number of Accesseses per month from the log file.
     * @return returns a double with the average number of accesses per month
     */
    public double averageAccessesPerMonth() {
        double avgMoAccesses = 0.0;
        int total = 0;
        System.out.println("Mo: Mo Accesses");
        for (int month = 1; month < monthCounts.length; month++) 
        {
            System.out.println(month + ":     " + monthCounts[month]);
            total+= monthCounts[month];
        }
        avgMoAccesses = (total/12.0);
        System.out.print("Average accesses per month ");
        System.out.printf("%.2f", avgMoAccesses); 
        return avgMoAccesses;
    } 
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeData.
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
