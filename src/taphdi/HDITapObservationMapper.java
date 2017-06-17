/**
 * @author Tapio Sivonen
 *
 * Class to map WeightedObservations out of HDITapEntries.
 **/

package taphdi;

import java.util.stream.*;
import org.apache.commons.math3.fitting.WeightedObservationPoint;

public class HDITapObservationMapper
{
    /**
     * Collects a Collection of WeightedObservationPoint out of the HDITapEntry Stream.
     *
     * @param hdiTapStream source stream of HDITapEntry
     * @return WeightedObservationPoint Collection of the target HDITapEntries so that weight is always 1.0,
     * x is HDI and y is tap water prevalence
     **/
    private static Collection<WeightedObservationPoint>
        collectWeightedObservationPoints(Stream<HDITapEntry> hdiTapStream)
    {
        return
            hdiTapStream
            .map(inHDITapEntry ->
                 new WeightedObservationPoint(1.0d, inHDITapEntry.getHdi(), inHDITapEntry.getTapPrevalence())
                 )
            .collect(Collectors.toList());
    }

    /**
     * Maps WeightedObservationPoints out of Collection of HDITapEntries
     * and filters them by target year.
     *
     * @param hdiTapEntries the source Collection of HDITapEntry
     * @param year the target year
     * @return WeightedObservationPoint Collection of the target HDITapEntries so that weight is always 1.0,
     * x is HDI and y is tap water prevalence
     **/
    public static Collection<WeightedObservationPoint>
        mapWeightedObservationPointsOutOfHDITapEntryesAndFilterByYear(Collection<HDITapEntry> hdiTapEntries, int year)
    {
        return
            collectWeightedObservationPoints(       
                                             hdiTapEntries
                                             .stream()
                                             .parallel()
                                             .filter(currentHdiTapEntry ->
                                                     currentHdiTapEntry.getYear() == year
                                                     )
                                                    );
    }
}
