package com.numcore.numeric.series_sum;

import java.util.Scanner;

public class SeriesSumApp {

    public static void main(String[] args) {
        NumberSeries series = new DefaultNumberSeries(0.25, 1, n -> (3.0 * n - 2) / (3.0 * n + 4));
        Scanner scanner = new Scanner(System.in);
        String continueInput;

        do {
            System.out.print("Enter epsilon (e.g. 0.1, 0.01, 0.001): ");
            double epsilon = scanner.nextDouble();

            double sum = series.compute(epsilon);
            System.out.printf("Sum with epsilon=%.6f: %.10f%n", epsilon, sum);
            System.out.printf("Iterations: %d%n", series.countOfIterations());

            System.out.print("Compute again? (y/n): ");
            continueInput = scanner.next();
        } while (continueInput.equalsIgnoreCase("y"));

        scanner.close();
    }
}