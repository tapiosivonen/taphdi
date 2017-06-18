/**
 * @author Tapio Sivonen
 *
 * Class to modify a ParametricUnivariateFunction by rearranging
 * parameter order or fixing some parameters to constant values.
 **/

package taphdi;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.function.Logistic.Parametric;
import org.apache.commons.math3.exception.DimensionMismatchException;
import java.util.Arrays;

public class HDITapModelFunction
    implements ParametricUnivariateFunction
{
    /** target ParametricUnivariateFunction **/
    private ParametricUnivariateFunction targetFunction;
    /** constant base parameters for the target function **/
    private double[] baseParameters;
    /** index to which position each this function's parameter should be replaced to **/
    private int[] parameterIndex;

    /**
     * Creates a new HDITapModelFunction by rearranging and fixing some parameters.
     *
     * @param targetFunction the underlying ParametricUnivariateFunction to use
     * @param baseParameters the constants to overwrite partially by this function
     * @param parameterIndex index to indicate to which position each parameter to this function should be written in target function
     * @return created new function
     * @throws IllegalArgumentException if there are negative positions in parameterIndex
     **/
    public HDITapModelFunction(ParametricUnivariateFunction targetFunction,
                               double[] baseParameters,
                               int[] parameterIndex)
    {
        for(int current : parameterIndex)
            if(current < 0)
                throw new IllegalArgumentException("Negative parameter index"
                                                   +Arrays.toString(parameterIndex));
        this.targetFunction = targetFunction;
        this.baseParameters = baseParameters.clone();
        this.parameterIndex = parameterIndex.clone();
    }

    /**
     * Validates that there is correct amount of parameters,
     * which is the same amount as there were in parameterIndex in constructor.
     *
     * @param parameters parameters to be validated
     * @throws DimensionMismatchException if there is invalid lenght of parameters
     **/
    private void validateParameters(double... parameters)
    {
        if(parameters.length != parameterIndex.length)
            throw new DimensionMismatchException(parameters.length,
                                                 parameterIndex.length);
    }

    /**
     * Creates a new parameter array based on the base parameters and
     * current parameters.
     *
     * @param parameters current parameters to the function
     * @return combined parameters that are a clone of base parameters where parameters are written to the parameter index positions
     * @throws DimensionMismatchException if there is invalid lenght of parameters
     **/
    private double[] createRealParameters(double... parameters)
    {
        validateParameters(parameters);
        double[] realParameters = baseParameters.clone();
        for(int index = 0; index<parameters.length; index++)
            realParameters[parameterIndex[index]] = parameters[index];
        return realParameters;
    }

    /**
     * Creates a new result gradient by copying the proper positions in target function gradient.
     *
     * @param gradient target function gradient
     * @return gradient of this function
     **/
    private double[] createResultGradient(double[] gradient)
    {
        double[] result = new double[parameterIndex.length];
        for(int index = 0; index < parameterIndex.length; index++)
            result[index] = gradient[parameterIndex[index]];
        return result;
    }

    /**
     * Calls value function of target function with real parameters.
     *
     * @param x function variable
     * @param parameters parameters of this function
     * @return function value at point x with parameters
     * @throws DimensionMismatchException if there is invalid lenght of parameters
     **/
    public double value(double x, double... parameters)
    {
        return targetFunction.value(x, createRealParameters(parameters));
    }

    /**
     * Calls gradient function of target function with real parameters.
     *
     * @param x function variable
     * @param parameters parameters of this function
     * @return function gradient at point x with parameters
     * @throws DimensionMismatchException if there is invalid lenght of parameters
     **/
    public double[] gradient(double x, double... parameters)
    {
        return createResultGradient(targetFunction
                                    .gradient(x,
                                              createRealParameters(parameters)
                                              )
                                    );
    }
}
