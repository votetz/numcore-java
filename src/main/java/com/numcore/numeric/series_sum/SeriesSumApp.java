package com.numcore.numeric.series_sum;

import java.util.Scanner;

public class SeriesSumApp {

    public static void main(String[] args) {
        SeriesSumCalculator calculator = new SeriesSumCalculator(SeriesTermFunction.term());
        Scanner scanner = new Scanner(System.in);
        String continueInput;

        do {
            System.out.print("Enter epsilon (e.g. 0.1, 0.01, 0.001): ");
            double epsilon = scanner.nextDouble();

            double sum = calculator.compute(epsilon);
            System.out.printf("Sum with epsilon=%.6f: %.10f%n", epsilon, sum);

            System.out.print("Compute again? (y/n): ");
            continueInput = scanner.next();
        } while (continueInput.equalsIgnoreCase("y"));

        scanner.close();
    }
}