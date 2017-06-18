/**
 * @author Tapio Sivonen 2017-06-17
 *
 * Bean to hold entries of country HDI and tap water prevalence in a given year.
 **/

package taphdi;

public class HDITapEntry
{
    /** internal state **/
    private int year;
    private double hdi;
    private double tapPrevalence;

    /**
     * Empty constructor
     *
     * @return new HDITapEntry
     **/
    public HDITapEntry()
    {
    }

    /**
     * Gets year
     *
     * @return year
     **/
    public int getYear()
    {
        return year;
    }

    /**
     * Gets HDI
     *
     * @return hdi
     **/
    public double getHdi()
    {
        return hdi;
    }

    /**
     * Gets tap water prevalence
     *
     * @return tapPrevalence
     **/
    public double getTapPrevalence()
    {
        return tapPrevalence;
    }

    /**
     * Sets year
     *
     * @param year
     * @return this for chaining
     **/
    public synchronized HDITapEntry setYear(int year)
    {
        this.year = year;
        return this;
    }

    /**
     * Sets hdi
     *
     * @param hdi
     * @return this for chaining
     **/
    public synchronized HDITapEntry setHdi(double hdi)
    {
        this.hdi = hdi;
        return this;
    }

    /**
     * Sets tap water prevalence
     *
     * @param tapPrevalence
     * @return this for chaining
     **/
    public synchronized HDITapEntry setTapPrevalence(double tapPrevalence)
    {
        this.tapPrevalence = tapPrevalence;
        return this;
    }

    /**
     * Creates a CSV formatted String out of the contents of this entry
     *
     * @return CSV String with format of year;hdi;tapPrevalence
     **/
    public synchronized String toString()
    {
        return ""+getYear()+";"+getHdi()+";"+getTapPrevalence();
    }

    /**
     * Reads a HDITapEntry out of source String, and returns null if there is an Exception.
     * Source String is expected to be in format of year;hdi;tapPrevalence
     *
     * @param source the string to read
     * @return HDITapEntry read from the source CSVcontents or null if there is an exception
     **/
    public static HDITapEntry readClean(String source)
    {
        try {
            return read(source);
        }
        catch(IllegalArgumentException iae) {
            return null;
        }
    }
    
    /**
     * Reads a HDITapEntry out of source String.
     * Source String is expected to be in format of year;hdi;tapPrevalence
     *
     * @param source the string to read
     * @return HDITapEntry read from the source CSVcontents
     * @throws IllegalArgumentException if the source is not formatted as expected
     **/
    public static HDITapEntry read(String source)
        throws IllegalArgumentException
    {
        String[] part = source.split(";");
        if(part.length != 3)
            throw new IllegalArgumentException("Invalid HDITapEntry source: "+source);
        HDITapEntry result = new HDITapEntry();
        result.setYear(Integer.parseInt(part[0]));
        result.setHdi(Double.parseDouble(part[1]));
        result.setTapPrevalence(Double.parseDouble(part[2]));
        return result;
    }
}
