package Day3;
import java.util.ArrayList;

public class day3 {
    public static void main(String[] args) {
        System.out.println(summation(0, 10));
        System.out.println(surpass(3));
        System.out.println(checkBeforeCont(4));
        System.out.println(continuingExample(new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14}));
        System.out.println(breakingExample(new int[] {120, 240, 300, 100, 800, 40, 50}));

    }

    // Loops - Full Concept
    // initalized by 
    /*
        for (int i = 0; i < 5; i++) {
            // code
        }
        Three parts inside the parentheses. semicolon separated: initialization (int i = 0, runs once, before the loops starts)
                                                                 condition (i < 5, checked before every iteration - if false, the loop never runs again)
                                                                 increment (i++, runs after every iterations body completed)
        Javas version isnt iterating a range obj at all, its three independent statements you control directly,
            Which means you can do things Swift range-based for-in cant easily express, like counting backward or skipping by twos

        for (int i = 10, i > 0; i-= 2) { code }
    */

    // while loop - condition checked brefore every iteration, same as swift
    /* 
        int count = 0;
        while (count < 5) { some code; count++ }

        if the condition is false the very first time, the body never runs at all
    */

    // do while loop
    /*
        no swift equivalent (repeat while is closes but worth confirming that its remembered correctly)
        The body runs once, unconditionally, then the condition is checked
            Meaning a do while always executes at least once, even if the condtion was false from the very start
        int x = 10;
        do { some code; x++; } while (x < 5)
        This print exactly once (print 10) then stops even though x < 5 was already false
            The distinguishing use case: anything where you need to do something first then decide whether to repeat, rather than deciding to start at all
    */

    // Enhanced for loop (the for each style) - closest thing Java has to swifts for in, used for iterating collections/arrays directly without manual indexing
    /*
        int[] numbers = {1, 2, 3, 4, 5};
        for (int n: numbers) { code }

        Read as for each int n in numbers
        No index variable, no manual bonds checking - but also no way to know which position youre at unless you track it yourself
            Which is why the clasic indexed for loop still matters for DSA specifically -
            most array/pointer based algs need the index itself, not just the value
    */

    // break and continue
    //      break exits the loop entirely, immediately
    //          for (int i = 0; i < 5; i++) { boolean check; continue; }
    //      continue skips the rest of the current iterations body and jumps straight to the next iteration
    //          for (int i = 0; i < 5; i++) { boolean check; continue; }
    //   Both work identically to swift

    

    // Exercise 1
    // classic for loop computing a running sum 1 to some number
    public static int summation(int start, int end) { // static since this is in the main class of the file
        int returningValue = 0;
        for (int i = start; i <= end; i++) {
            returningValue += i;
        }
        return returningValue;
    }
    // while loop that keeps doubling a starting value until it exceeds some threshold
    public static int surpass(int value) {
        int valueFound = 0;
        int staticEnd = (int) Math.pow(value, 2);
        while (value <= staticEnd) {
            valueFound = (int) Math.pow(value, 2);
            value++;
        }
        return valueFound;
    }
    // do while loop that specifically demos the runs at least once even if the condition is already false
    public static int checkBeforeCont(int value) {
        int valueFound = 0;
        int staticEnd = value + 5;
        do {
            valueFound = (int) Math.pow(value, 2);
            value++;
        } while (value <= staticEnd); return valueFound;
    }
    // enhanced for loop iterating over an array printing each value
    public static ArrayList<Integer> continuingExample(int[] numbers) {
        ArrayList<Integer> returningArray = new ArrayList<>();

        for (int numberIn: numbers) {
            if (numberIn % 2 == 0) { returningArray.add(numberIn);} else { continue;}
        }
        return returningArray;
    }
    public static ArrayList<Integer> breakingExample(int[] numbers) {
        ArrayList<Integer> returningArray = new ArrayList<>();
        for (int numberIn: numbers) {
            if (numberIn + ((int) (Math.round(numberIn * -1.3))) + ((int) (Math.round(numberIn * 0.5))) < 0) { break; } else { returningArray.add(numberIn); }
        }
        return returningArray;
    }
}
