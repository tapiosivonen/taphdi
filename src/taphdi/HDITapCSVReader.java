/**
 * @author Tapio Sivonen
 *
 * Class to read HDITapEntries from InputStream.
 **/

package taphdi;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class HDITapCSVReader
{
    /**
     * Reads HDITapEntries of an InputStream.
     *
     * @see HDITapEntry
     *
     * @param in InputStream to read from
     * @return Collection of HDITapEntries read
     * @throws IOException in case there occurs IOException while reading
     **/
    static Collection<HDITapEntry> readHDITapEntries(InputStream in)
        throws UncheckedIOException
    {
        try (InputStreamReader isr
             = new InputStreamReader(in);
             BufferedReader bufInput
             = new BufferedReader(isr);
             ){     
            return
                bufInput
                .lines()
                .parallel()
                .map(l -> HDITapEntry.readClean(l))
                .filter(e -> e != null)
                .collect(Collectors.toList());
        }
        catch(IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }
}
