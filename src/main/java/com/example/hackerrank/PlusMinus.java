package com.example.hackerrank;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

class ResultPlusMinus {

    /*
     * Complete the 'plusMinus' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void plusMinus(List<Integer> arr) throws IOException {
        // Write your code here
        if (!arr.isEmpty() && arr.size() <= 100) {
            DecimalFormat myFormatter = new DecimalFormat("0.000000");
            double positives = 0;
            double negatives = 0;
            double zeros = 0;
            for (Integer number : arr) {
                if (number > 100 || number < -100) {
                    throw new IOException("Input field cannot be less than -100 and greater than 100");
                }
                if (number > 0) {
                    positives++;
                } else if (number < 0) {
                    negatives++;
                } else {
                    zeros++;
                }
            }
            System.out.println(myFormatter.format(positives / arr.size()));
            System.out.println(myFormatter.format(negatives / arr.size()));
            System.out.println(myFormatter.format(zeros / arr.size()));
        } else {
            // Input is violating size constraint
            throw new IOException("Input array is violating size constraint");
        }

    }

}

public class PlusMinus {
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            int n = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());
            if (arr != null && n != arr.size())
                throw new IOException("Mismatch in numbers of expected input and actual input");
            if (n < 0 || n > 100)
                throw new IOException("Constraint failed");
            ResultPlusMinus.plusMinus(arr);

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
