package Day3;
import java.util.ArrayList; import java.util.Collections;
import java.util.Arrays; import java.util.List;
import java.util.HashMap; import java.util.Map;

public class day3 {
    public static void main(String[] args) {
        
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

    // Exercise 2.1 -- Nested Loops
    // takes an int[] and finds every pair of numbers in that array that sum to a specific target value
    // using a classic nested for loop (outer picks lhs number) (inner picks rhs)
    public static ArrayList<int[]> findTargetSufficient(int[] numbers, int target) {
        ArrayList<int[]> returningArray = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) { returningArray.add(new int[] {i, j}); }
            }
        }
        return returningArray;
    }
    // Exercise 2.2
    // do while loop as outer loop and while loop as inner
    // find the largest single val in array by comparing element agaisnt a running max
    public static int findLargest(int[] numbers) {
        int largest = 0;
        int currentIndex = 0;
        do {
            while (largest < numbers[currentIndex]) { largest = numbers[currentIndex];}
            currentIndex++;
        } while (currentIndex < numbers.length);
        return largest;
    }

    // Exercise 3 - A Problem That Actually Requires Nesting, Not Just Permits It
    // method with 2D int[][] array and find the sum of every row
    // return results as int[], where each positon holds that rows total
    public static int[] heavyReduce(int[][] numberInNumbers) {
        ArrayList<Integer> reducedOutcomes = new ArrayList<>();

        for (int i = 0; i < numberInNumbers.length; i++) {
            int currentArrayCounter = 0;
            for (int j = 0; j < numberInNumbers[i].length; j++) {
                currentArrayCounter += numberInNumbers[i][j];
            }
            reducedOutcomes.add(currentArrayCounter);
        }

        int[] finalArray = new int[reducedOutcomes.size()];
        for (int i = 0; i < reducedOutcomes.size(); i++) { finalArray[i] = reducedOutcomes.get(i);}
        return finalArray;
    }

    // Exercise 4 -- Two Pointers
    public static ArrayList<int[]> twoPointers(int[] numbers, int target) {
        ArrayList<int[]> targetsFound = new ArrayList<>();

        int left = 0; int right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                targetsFound.add(new int[] { numbers[left], numbers[right]}); left++; right--;
            } else if (sum < target) { left++;} else { right--;}
        }
        
        return targetsFound;
    }

    // Arrays - the primitive, fixed-size versions
    // int[] scores = new int[5]
    //   Creates an array of exactly 5 ints, every slot defaulting to 0,
    //   Size is fixed at creation and can never change - no append, no resize, ever
    //   Is closer to a fixed block of memory than a dynamic cretion
    // Literal Syntax
    //      int[] scores = { 2, 4, 5, 6, 7 };
    //      Size is inferred from how many values provided, still fixed once created
    //      scores[0] reads and scores[0] = 100 writes
    //      .length gives size - length is a property not a method
    //      Arrays.sort(arrayName) -- sorts in place, ascending
    //      Arrays.toString(arrayName) - usable readable format
    //      Arrays.equals(array1, array2) - compares two arrays contents, not references
    //      Arrays.fill(arrayName, 0) - sets every element to the same value
    //      Arrays.copyOf(arrayName, newLength) - this is how to get a bigger array; creates new array of specified length, copying over what fits, defaults rest if theres more
    //      Arrays.binarySearch(arrayName, target) - reqs a sorted array, returns index if found ( directly relevant to two pointers and sliding window)
    // ArrayList<T> - the dynamic, resizable alternative, lives in java.util
    //   ArrayList<Integer> scores = new ArrayList<>();
    //   Grows and shrrinks freely via .add(), .remove(), .get(index) instead of subscript syntax
    //   .size() instead of .count
    //   The <> after new ArrayList<>() is called the diamond operator
    //      Java can infer the generic type from the left sife so no need to repeat it on the right
    //   ArrayList can only hold objects never primitives
    //      ArrayList<int> doesnt compule, must use capital Integer
    //      Autoboxing handles the conversion silently when you .add(85) - Java converts primitive int to an Integer obj behind the scenes
    //   Literal Syntax
    //      .contains(element) - checks for an exact equals() match directly, no closure needed for simple value checks
    //      .indexOf(element) - returns the index of the first match, -1 if not found
    //      .set(index, newValue) - replaces value at the specific index
    //      .isEmpty() - cleaner than checking .size() == 0
    //      .clear() - removes everything, results in size 0
    //      .addAll(otherList) - appends every element from another ArrayList at once
    //      .subList(from, to) - returns a view into a portion of the list
    //          this is a live view, not a copy. Changes to this affect the original list too
    //      .sort(null) or Collections.sort(list) - sorting an ArrayList (two different valid syntaxes, Collections is another util class similar to Arrays)
    //      .toArray() - converts back to a plain array, the reverse of the arrayList from array pattern
    // When to Reach for Which
    //      fixed-size array when you know exact count in advance and it will never change ( fixed-grid, small lookup table )
    //      ArrayList for anything else which describes the majority of real DSA problems

    // Exercise 1 - Arrays.sort, Arrays.toString, Arrays.binarySearch
    // takes an unsorted int[], sorts it. print with toString
    // use binSearch twice against sorted array - once for value that exists and one that doesnt
    public static String sortList(int[] numbers, int target1, int target2) {
        if (numbers.length == 0 ) { return "List passed in is empty"; } else {
            Arrays.sort(numbers);
            int bsRes1 = Arrays.binarySearch(numbers, target1);
            int bsRes2 = Arrays.binarySearch(numbers, target2);
            if (bsRes1 >= 0 && bsRes2 >= 0) {
                return String.format("Sorted Array:%n   %s%nFound Both Targets!%n   First found at index %d, Second found at index %d", Arrays.toString(numbers), bsRes1, bsRes2);
            } else {
                String whichOneWasntAsString = "";
                Boolean whichOneWasnt = false;
                Boolean noneWereFound = false;
                // since this else block only runs if one of the res were not greater than 0, first check if both were less than 0, if not, then only one of them is less than 0
                if (bsRes1 < 0 && bsRes2 < 0) {
                    whichOneWasntAsString = "No results were found"; noneWereFound = true;
                } else {
                    if (bsRes1 < 0 ) { whichOneWasnt = true; whichOneWasntAsString = String.format("Target %d was not found but Target %d was", target1, target2); } else {
                        whichOneWasntAsString = String.format("Target %d was not found but Target %d was", target2, target1);
                    }
                }
                return String.format("Sorted Array:%n   %s%n%S", Arrays.toString(numbers), whichOneWasntAsString);
            }
        }
    }

    // Exercise 2 - Arrays.copyOf, Arrays.copyOfRange, Arrays.equals
    // takes a fixed int[] and uses copyOf to create a version thats larger than the orginal
    //      Confirm what value(s) are new
    // Use copyOfRange to extract a sub-section from somewhere in the middle of the original array
    // Use equals to compare two separate arrays that have the same values in the same order and two arrays that have same values but in different order
    public static String expandList(int[] numbers, int by) {
        int[] copyOf = Arrays.copyOf(numbers, numbers.length + by);
        int[] newValues = Arrays.copyOfRange(copyOf, numbers.length, copyOf.length);
        return Arrays.toString(newValues);
    }
    public static String extractMidsection(int[] of, int by) {
        if (by > of.length) { return "By parameter is out of bounds";}
        if (by % 2 != 0) { by -= 1; }
        int subSectionLength = by / 2;
        return Arrays.toString(Arrays.copyOfRange(of, subSectionLength, of.length - subSectionLength));
    }

    // Exercise 3 -- ArrayList.contains, .indexOf, .set, .isEmpty
    // Build an ArrayList<String> representing something of my choosing
    // Use .isEmpty() before adding anything then add elements and check again
    // Use .contains() to check for an element that exists and one that does not, print both results
    // Use .indexOf() to find position of a specific element, then use .set() to replace that value with something new
    //      print array of before and after
    public static String fillStringArray(ArrayList<String> fillable, String findable, String nonFindable, String replaceWith) {
        // existing string placeholder
        String shouldExist = "";
        String shouldntExist = "";
        if (!fillable.isEmpty()) { return "Please use an empty array for this func call"; }
        // fill the array
        for (int i = 0; i <= 6; i++) {
            fillable.add(String.format("Index %d", i));
        }

        // check for an existing element
        if (fillable.contains(findable)) {
            shouldExist = String.format("The argument '%S' was found", findable);
        } else { shouldExist = String.format("The argument '%S' was not found", findable);}
        // check for a non-existing element
        if (fillable.contains(nonFindable)) { shouldntExist = String.format("The argument '%S' was found but should not have been found", nonFindable);} else {
            shouldntExist = String.format("The argument '%S' was not found as expected.", nonFindable);
        }

        // Replacement
        System.out.println(fillable);
        fillable.set(fillable.indexOf("Index 4"), replaceWith);
        System.out.println(fillable);
        return String.format("Existential Findings%n%s%n%s", shouldExist, shouldntExist);
    }

    // Exercise 4 -- ArrayList.addAll, .subList, .clear
    // Create two separate ArrayList<Integer> with different starting vals
    // Use .addAll() to merge the second lists contents into the first, confirming the first lists size and contents reflect both lists combined
    // On the merged list, use .subList(from, to) to grab a portion of it
    //      Instead of just printing sublist, mutate the sublist directly (using .set()) then print original list afterwards
    // Confirm what actually happens
    // use .clear() on either list and confirm size of each, especially which list correctly reports size of 0
    public static String subListTest(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        arr1.addAll(arr2); System.out.println(arr1);
        List<Integer> subList = arr1.subList(arr1.size() - (arr1.size() - 1), arr1.size());
        System.out.println("Current Sublist, " + subList);
        subList.set(1, 1001);
        System.out.println(arr1);

        arr1.clear(); arr2.clear();
        return String.format("%s", (arr1.isEmpty() && arr2.isEmpty() ? "Both Lists are empty" : "At least one didnt clear"));
    }

    // Exercise 5 -- .toArray(), Collections.sort(), Arrays.asList()
    // use Collections.sort() to sort in place, print before and after
    // use .toArray() to convert now sorted into plain array
    //      toArray() with no args returns Object[], not Integer[], which wont let you treat the results as numbers directly without a cast
    // Find and use the correct overload approach to get back a proper Integer[] you can actually work with
    // Use Arrays.asList() on a plain array to convert it back into a List view
    //      test whether that resulting list supports .add() 
    public static void convertable(ArrayList<Integer> arr1) {
        System.out.println("Before " + arr1); Collections.sort(arr1); System.out.println("After " + arr1);

        Object[] arr1AsObjects = arr1.toArray();
        int[] primitiveArray = new int[arr1AsObjects.length];
        for (int i = 0; i < arr1AsObjects.length; i++) {
            primitiveArray[i] = (int) arr1AsObjects[i]; // casts each element at index i to int and sets the primitiveArray index of i to that value
        }
        List<Object> properlist = Arrays.asList(arr1AsObjects); // this is a list of individual elements not one array wrapped as a single object
        List<int[]> nowIs = Arrays.asList(primitiveArray);
        System.out.println(properlist);
        // sets this array as an object within the List<int[]> since Arrays.asList() takes parameters to put into a list and not separated each value within this obj as its own int[]
        // nowIs can only contain int[] objs not separate integers
    }

    // Exercise 6 -- Arrays.asList(), Forcing the Fixed Size Boundary Multiple Ways
    // Construct three separate attempts to break Arrays.asList()'s fixed-size restriction using 3 opps - .add, .remove and .clear
    //      Each on its own separate Arrays.asList() backed list
    // For each of the three
    //      wrap the call in a try catch block try {} catch (Exception e) { Sysoutprntln stringVal + e }
    //          This lets program continue running past a thrown exception instead of crashing entirely
    // Then prove the other side of Arrays.asList()'s behavior - that .set() genuinely does work on it
    //      since only size changing operations are restricted, not all mutation
    // Use .set to successfully change a value and print the underlying orginal array afterward to confirm the change reflects there too
    //      this is the same live view proof as subList but now on Arrays.asList() specifically
    // Build all four pieces - three caught exceptions, one successful .set() with the underlying array proof
    public static void alterArrays(Integer[] arr1, Integer[] arr2, Integer[] arr3) {
        List<Integer> returnedArr1 = Arrays.asList(arr1); // calling asList returns type List<T> or in this case List<ArrayList<Integer>>
                             // per documentation, changes made to orig will be visible to this and vice versa
                             // however; this new List implements optional Collection methods except those that would change the size
        List<Integer> returnedArr2 = Arrays.asList(arr2);
        List<Integer> returnedArr3 = Arrays.asList(arr3);

        try { returnedArr1.add(2);} catch (Exception e) { System.out.println("Appending Error: " + e); }
        try { returnedArr2.remove(0);} catch (Exception e) { System.out.println("Appending Error: " + e); }
        try { returnedArr3.clear();;} catch (Exception e) { System.out.println("Appending Error: " + e); }

        System.out.println("%nNow attempting .set. Here is the original array first " + Arrays.toString(arr1));
        returnedArr1.set(0, 444); System.out.println("%nModified the asList version here: " + returnedArr1);
        System.out.println("%nConfirm original shows change too: " + Arrays.toString(arr1));
    }

    // Exercise 7 -- asList() Interacting with a Genuine ArrayList and Where the Restriction Actually Ends
    // take an Integer[], wrap it with asList() then construct a genuine brand new ArrayList<Integer> by passing that same fixed size list into ArrayLists constructor
    // prove that this new list is fully resizable
    // Prove the two lists are now genuinely independent--mutate new ArrayList further then print the original asList and confirm there was no change
    //      confirming that wrapping it in new ArrayList<>(listName) created a real, separate copy, breaking the live view relationship that subList and direct .set calls preserved
    public static void confirmRelationship(Integer[] arr1) {
        List<Integer> arr1AsList = Arrays.asList(arr1); // still fixed size, .get .set will still make changes to original
        ArrayList<Integer> newArr1 = new ArrayList<>(arr1AsList); // this is now a new obj; made from the List thus transforming from Integer[] to ArrayList<Integer>
        try { arr1AsList.add(2);} catch (Exception e) {
            System.out.println(e); // confirming that the List of arr1 is not alterable
        }
        try {
            newArr1.add(2); System.out.println(newArr1); // confirming this change occurrs
            newArr1.remove(2); System.out.println(newArr1);
        } catch (Exception e) { System.out.println(e); }
        System.out.println("Confirming theres no change done to original: " + Arrays.toString(arr1));
    }

    // List<T> - the interface, not a class
    // Every ArrayList<T> is technically a List<T> too
    //      ArrayList is one specific class that implements the List interface
    //          Is one concrete implementation that holds full contract
    // asList()'s return value is a different implementation, honoring the contract only partially as just proven above
    // The practical, real world reason this matters - declaring vars and params as List<T> instead of ArrayList<T>
    //      List<Integer> numbers = new ArrayList<>()
    //          This compiles and owrks identically to deccing it as ArrayList<Integer> on left side 
    //          but its considered the better Java practice and reasoning is worth understanding
    //              If you write a method that takes ArrayList<Integer> as a parameter, that method can only ever accept genuine ArrayList instances not Arrays.asList result, not any other List implementation that might exist
    //              If you write that same method taking List<Integer> instead, it can accept any class that implements List, ArrayList, asList() result, LinkedList, anything
    // Where this genuinely matters in DSA specifically
    //      many classic algorithm sigs are written accepting List<Integer> rather than ArrayList<Integer>,
    //          precisely so the same method works regardless of which concrete List implementation the calller happens to be using
    // Exercise 1 - List<T> as a Parameter Type, Accepting Multiple Implementations
    // Build a method whose param is List<Integer>, does real work like summing every element or finding the maximum
    // Use a loop of your choice
    // Call same method 3 times passing in three different things each time
    //      ArrayList<Integer> you build and populate normally
    //      Result of asList() on an Integer[]
    //      List<Integer> built via List.of()
    public static int reduceConformingList(List<Integer> list) {
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) { continue;}
            counter += list.get(i);
            // since List can hold null vals, I obv cant add a null value to counter
            // I can either add 0 or skip; skipping that index is the better choice
        }
        return counter;
    }

    // HashMap<K, V> - Java's Dictionary Type, the direct equivalent of Swift's [key: value]
    /*
        HashMap<String, Integer> ages = new HashMap<>();
        ages.put("Raul", 24); ages.put("Alex", 30);
        int RaulAge = ages.get("Raul");
    */
    // .put(key, value) - adds or updates, .get(key) retireves, returns null if the kye doesnt exist
    //      .get() on a missing key doesnt throow, it silently hands back null, which will cause a NullPointerException later if you try to use it as a primtive without checking first
    //          This is exactly why .get(key) into an int var dirrectly is dangerous, since unboxing a null Integer crashes
    // .containsKey(key) or .containsValue(value) - existences checks, map equivalent of ArrayList.contains()
    // .remove(key) - deletes an entry
    // .getOrDefault(key, defaultValue) - the safe alternative to raw .get(), returning a fallback instead of null when the key is missing
    //      Worth reaching for this over .get() whenever a missing key shouldnt crash the program
    // .keySet() - returns a Set of every key, iterable
    // .values() - returns a Collection of every value
    // .entrySet() - returns every key-value pair together, typically iterated like for (Map.Entry<String, Integer> entry : ages.entrySet()), giving you entry.getKey and entry.getValue() in one pass
    //      the most common way to loop through a whole map when you need both pieces at once
    // HashSet<T> - Java's set, direct equivalent of Swift's Set<T>
    //      No dupes allowed, no guaranteed order
    //      .add, .remove, .contains, same names as ArrayList, but .add on an already present value silently does nothing rather than throwing or duping - worht confirming that behavior directly rather assuming

    // Exercise 1 - HashMap. Full Method Set
    //      Build a HashMap<String, Integer> representing something of your choosing
    //      Populate it with .put
    //      Use .containsKey() to check for a key that exists and one that doesnt
    //      Deliberately call .get() on a key you know doesnt exist and print result directly - see the raw null
    //      Then use .getOrDefault() on that same missing key with a real fallback value, proving the safer alternative actually avoids the null
    //      Use .remove() on an existing key and confirm via .containsKey() after that its genuinely gone
    //      Finally iterate the whole map using .entrySet(), printing every key-value pair
    public static void checkAndIterate(HashMap<String, Integer> iteratingHM, String checkForKey) {
        System.out.println(String.format("Key Check Results:%n%s", (iteratingHM.containsKey(checkForKey)) ? String.format("iteratingHM does contain %s", checkForKey) : String.format("iteratingHM does not contain %s", checkForKey)));
        System.out.println(String.format("Deliberately checked for key that does not exist %s", iteratingHM.get("heyyyyy")));
        System.out.println(String.format("Deliberately checked for key that does not exist but safely %s", iteratingHM.getOrDefault("heyyy", 69)));
        iteratingHM.put("Will Remove", 88);
        iteratingHM.remove("Will Remove");
        System.out.println(String.format("Added k,v of ('Will Remove', 88) and then removed. Result: %b", iteratingHM.containsKey("Will Remove")));

        for (Map.Entry<String, Integer> entry: iteratingHM.entrySet()) {
            System.out.println(String.format("KEY: %S, VAL: %d", entry.getKey(), entry.getValue()));
        }
    }
}
