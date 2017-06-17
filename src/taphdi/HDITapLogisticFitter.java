/**
 * @author Tapio Sivonen
 *
 * Class for fitting Generalized Logistic models to HDI - Tap water prevalence data.
 **/

package hditap;

import java.util.*;
import org.apache.commons.math3.fitting.*;

public class HDITapLogisticFitter
{
    /** start point for logistic model fitting, k=1, m=0, b=1, q=1, a=0, n=1 **/
    private static final double[] STARTPOINT = {1.0d, 0.0d, 1.0d, 1.0d, 0.0d, 1.0d};
    /** maximum iterations allowed **/
    private static final int MAXITER = 10000;
    /** Curve fitter to use **/
    private SimpleCurveFitter myFitter
        = SimpleCurveFitter
        .create(new Logistic.Parametric(),
                STARTPOINT)
        .withMaxIterations(MAXITER);
    /** List to store HDITapEntries in **/
    private List<HDITapEntry> hdiTapEntryList;

    /**
     * Constructor to create a HDITapLogisticFitter with given hdiTapEntries.
     *
     * @param hdiTapEntries observation data on HDI - Tap water prevalence
     * @return created HDITapLogisticFitter
     **/
    public HDITapLogisticFitter(Collection<HDITapEntry> hdiTapEntries)
    {
        hdiTapEntryList = new ArrayList(hdiTapEntries);
    }

    /**
     * Fits a generalized logistic model to the observation data of given year.
     *
     * @param year target year to model.
     * @return parameters of the fitted model
     **/
    public synchronized double[] fitLogisticModelByYear(int year)
    {
        return myFitter
            .fit(HDITapObservationMapper
                 .mapWeightedObservationPointsOutOfHDITapEntriesAndFilterByYear(hdiTapEntryList,
                                                                                year)
                 );
    }

    /**
     * Main method for reading given HDI - Tap water prevalence data, fitting models for years
     * 1990 and 2015 and then outputing the results.
     * Reads HDI Tap water data from System.in and outputs fit models to System.out.
     *
     * @see HDITapEntry
     *
     * @param args command line arguments, not used currently
     **/
    public static void main(String[] args)
    {
        HDITapLogisticFitter fitter
            = new HDITapLogisticFitter(HDITapCSVReader.readHDITapEntries(System.in));
        System.out.println("1990: "+Arrays.deepToString(fitter.fitLogisticModelByYear(1990)));
        System.out.println("2015: "+Arrays.deepToString(fitter.fitLogisticModelByYear(2015)));
        return;
    }
}
