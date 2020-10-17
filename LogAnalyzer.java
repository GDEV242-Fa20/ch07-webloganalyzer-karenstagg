/**
 * Read web server data and analyse hourly access patterns.
 * Supplying your own filename is an added feature.
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
     * Return the number of accesses recorded in the log file.
     * @return total is the int value of the number of accesses recorded 
     * in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        //Add the value in each element of hourCounts to total.
        /*for (int index = 0; index < hourCounts.length; index++)
        {
            total += hourCounts[index];
        }*/
        for (int hourCount:hourCounts)
            total = total + hourCount;
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
