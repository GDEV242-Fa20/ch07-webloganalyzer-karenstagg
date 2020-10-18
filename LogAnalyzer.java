/**
 * Read web server data and analyse hourly access patterns.
 * Supplying your own filename is an added feature.
 * Also adding ability to analyze days patterns.
 * 
 * @author Karen Stagg
 * @version October 19,2020
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Constructor
     * Create an object to analyze hourly web accesses. A default filename 
     * of demo.log will be ther reader name.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * Overlaoded Constructor to add your own filemane instead of using default.
     * Create an object to analyze hourly web accesses.
     * @param String namedFile is a filename given by the user to create a new 
     * reader with a supplied name insted of the default "demo.log".
     */
    public LogAnalyzer(String filename)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
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
     * Return the number of accesses recorded in the log file.
     * @return total is the int value of the number of accesses recorded 
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
