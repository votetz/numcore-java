package com.numcore.numeric.power_series;

import java.util.Scanner;

public class CosSeriesApp {
    public static void main(String[] args) {
        PowerSeriesCalculator calculator = new PowerSeriesCalculator(new CosSeriesTerm());
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a: ");
        double a = scanner.nextDouble();

        System.out.print("Enter b: ");
        double b = scanner.nextDouble();

        System.out.print("Enter k (number of points): ");
        int k = scanner.nextInt();

        System.out.print("Enter n (number of terms): ");
        int n = scanner.nextInt();

        double h = (b - a) / (k - 1);

        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "x", "Exact", "By n terms", "By epsilon", "Error");

        for (int i = 0; i < k; i++) {
            double x = a + i * h;
            double exact = Math.cos(x);
            double byTerms = calculator.computeByTerms(x, n);
            double byEpsilon = calculator.computeByEpsilon(x, 0.0001);

            System.out.printf("%-10.4f %-15.10f %-15.10f %-15.10f %-15.6f%n",
                    x, exact, byTerms, byEpsilon, Math.abs(exact - byEpsilon));
        }

        scanner.close();
    }
}