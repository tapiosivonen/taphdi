/**
 * @author Tapio Sivonen
 *
 * Class for fitting Generalized Logistic models to HDI - Tap water prevalence data.
 **/

package taphdi;

import java.util.*;
import org.apache.commons.math3.fitting.*;
import org.apache.commons.math3.analysis.function.*;
import org.apache.commons.math3.analysis.*;

public class HDITapFitter
{
    /** start point for model fitting, m=0.5, b=1/0.05 **/
    private static final double[] STARTPOINT
        = {0.5d, 1.0d/0.05d};
    /** base parameters for logistic model fitting, k=1, m=0.0, b=1, q=1, a=0, n=1 **/
    private static final double[] BASEPARAMETERS
        = {1.0d, 0.0d, 1.0d, 1.0d, 0.0d, 1.0d};
    /** parameter indexes **/
    private static final int[] PARAMETERINDEX
        = {1,2};
    /** target logistic function **/
    private final ParametricUnivariateFunction targetFunction
        = new Logistic.Parametric();
    /** maximum iterations allowed **/
    private static final int MAXITER = 10000;
    /** Curve fitter to use **/
    private SimpleCurveFitter myFitter
        = SimpleCurveFitter
        .create(new HDITapModelFunction(targetFunction,
                                        BASEPARAMETERS,
                                        PARAMETERINDEX),
                STARTPOINT)
        .withMaxIterations(MAXITER);
    /** List to store HDITapEntries in **/
    private List<HDITapEntry> hdiTapEntryList;

    /**
     * Constructor to create a HDITapFitter with given hdiTapEntries.
     *
     * @param hdiTapEntries observation data on HDI - Tap water prevalence
     * @return created HDITapFitter
     **/
    public HDITapFitter(Collection<HDITapEntry> hdiTapEntries)
    {
        hdiTapEntryList = new ArrayList(hdiTapEntries);
        System.err.println(hdiTapEntryList.size());
    }

    /**
     * Fits a model to the observation data of given year.
     *
     * @param year target year to model.
     * @return parameters of the fitted model
     **/
    public synchronized double[] fitModelByYear(int year)
    {
        return myFitter
            .withStartPoint(STARTPOINT)
            .fit(HDITapObservationMapper
                 .mapWeightedObservedPointsOutOfHDITapEntriesAndFilterByYear(hdiTapEntryList,
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
        HDITapFitter fitter
            = new HDITapFitter(HDITapCSVReader.readHDITapEntries(System.in));
        System.out.println("1990: "+Arrays.toString(fitter.fitModelByYear(1990)));
        System.out.println("2015: "+Arrays.toString(fitter.fitModelByYear(2015)));
        return;
    }
}
