package wordle.judges;

import java.util.Scanner;

import wordle.Judge;

public class Human extends Judge {
    private Scanner scanner;

    protected void setup() {
        scanner = new Scanner(System.in);
    }

    protected Correctness[] judgeWord(String word) {
        Correctness[] results = new Correctness[wordSize];
        boolean invalid = true;
        while (invalid) {
            System.out.print("Feedback (C/M/I): ");
            String input = scanner.nextLine();

            
            if (input.length() != wordSize)
                continue;
            
            invalid = false;
            int correctLetters = wordSize;    
            for (int i = 0; i < wordSize; i++) {
                switch (input.charAt(i)) {
                    case 'C':
                        results[i] = Correctness.CORRECT;
                        correctLetters--;
                        break;
                    case 'M':
                        results[i] = Correctness.MISPLACED;
                        break;
                    case 'I':
                        results[i] = Correctness.INCORRECT;
                        break;
                    default:
                        invalid = true;
                        System.out.println("Invalid feedback");
                        break;
                }
                if (correctLetters == 0)
                    finished = true;
                if (invalid) 
                    break;
            }
        }
        return results; 
    }
}
