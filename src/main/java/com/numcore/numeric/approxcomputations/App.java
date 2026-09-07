package com.numcore.numeric.approxcomputations;

public class App {
    public static void main(String[] args) {
        RoundingEvaluator ac1 = new RoundingEvaluator(0.1545);
        RoundingEvaluator roundedA = ac1.round(3);
        RoundingEvaluator absoluteError = ac1.getAbsoluteError(roundedA);
        RoundingEvaluator relativePercent = ac1.getRelativePercent(roundedA);

        System.out.println("Task1");
        System.out.printf("A = %.4f%n", ac1.getValue());
        System.out.printf("roundedA = %.3f%n", roundedA.getValue());
        System.out.printf("Absolute error = %.4f%n", absoluteError.getValue());
        System.out.printf("Relative percent = %.3f%%%n", relativePercent.getValue());

        ErrorDerivation errorDerivation = new ErrorDerivation(4.872);
        double relativeDelta = 5.0;
        ErrorDerivation absoluteError2 = errorDerivation.calculatorAbsoluteFromRelative(4.872, relativeDelta);

        System.out.println("\nTask2");
        System.out.printf("a = %.3f, delta = %.1f%%%n", absoluteError2.getValue(), relativeDelta);
        System.out.printf("Absolute error = %.4f%n%n", absoluteError2.getValue());

        MeasurementComparator alpha = new MeasurementComparator(15.7);
        MeasurementComparator beta = new MeasurementComparator(71.0);
        MeasurementComparator relativeAlpha = alpha.calculateRelativeError(5.0);
        MeasurementComparator relativeBeta = beta.calculateRelativeError(0.0005);

        System.out.println("Task3");
        System.out.printf("Alpha length = %.6f%n", relativeAlpha.getValue());
        System.out.printf("Beta length = %.6f%n", relativeBeta.getValue());
        System.out.printf("%s measurement is more accurate%n%n",
                relativeAlpha.getValue() < relativeBeta.getValue() ? "Alpha" : "Beta");

        SignificantDigitAnalyzer targetNum = new SignificantDigitAnalyzer(0.00842);
        double deltaX = 0.1 * Math.pow(10, -2);
        int digitsCount = targetNum.countSignificantDigits(deltaX);

        System.out.println("Task 4");
        System.out.printf("x = %.5f, delta_x = %.4f%n", targetNum.getValue(), deltaX);
        System.out.printf("Number of correct digits (n) = %d%n", digitsCount);
    }
}