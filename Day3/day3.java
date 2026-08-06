package Day3;
import java.util.ArrayList; import java.util.Collections;
import java.util.Arrays; import java.util.List;
import java.util.HashMap; import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map; import java.util.Set; import java.util.TreeMap;

public class day3 {
    public static void main(String[] args) {
        ArrayList<Integer> rolls = new ArrayList<>(List.of(1, 2, 5, 4, 1, 5, 6, 4, 3, 2, 6, 6, 4, 3, 1, 3));
        Exc6(rolls);
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

    // Exercise 2 - HashMap Values That Are Collections Themselves
    // Build a HashMap<String, ArrayList<Integer>> - each key maps not to a single value but to an entire list of vals
    // Build a method that takes a key and a value to add and correctly handles both cases
    //      If the key already exists, append the new value to its existing list
    //      If the key doesnt exist, create a brand new ArrayList, add the value to it and put it to the HM
    // Think carefully about the check then create or append logic here - theres a real risk of a NullPointerException if you try to call .add on a list that doesnt exist yet
    // Populate the map by calling this method several times with a mix of new and existing keys, then iterate the whole strcuture using .entrySet()
    public static void alterCreatedHM(HashMap<String, ArrayList<Integer>> HM, String k, Integer valueForV) {
        // 2nd approach to this, if a val isnt present in HM add it; accordingly
        // base case would be if not anything is present in the list
        if ((HM.containsKey(k) == false && HM.get(k) == null) || (HM.containsKey(k) && HM.get(k) == null)) {
            // add these two statements above since the solution below applies
            ArrayList<Integer> newVAL = new ArrayList<>(); newVAL.add(valueForV);
            HM.put(k, newVAL);
            // next case would be if the k alrleady exists
            // this is starting out as an else if branch below but lets make sure thats justified and correct
            // above two cases cover if theres no k nor v, or if v doesnt so other case left guarantees that k exists so else is needed not else if
        } else  {
            HM.get(k).add(valueForV);
        }

        // iteration below
        for (Map.Entry<String, ArrayList<Integer>> entry: HM.entrySet()) {
            System.out.println(String.format("KEY: %S, VAL: %s", entry.getKey(), entry.getValue()));
        }
    }

    // Exercise 3 - HashMap<String, Integer> as a Frequency Counter
    // Directly foreshadows DSA problems that are hit constantly (counting character frequencies, counting occurrences in an array, etc)
    // Build a method that takes a String[] of words (some that are repeated)
    //      returns a HashMap<String, Integer> counting how many times each word appears
    // For each word, if its already a key, increment its count; if not, add it with a starting count of 1
    // Same checl then branch logic as previous exercises but
    //      Since Integer is immutable think through it
    public static void frequencyDeterminator(String[] strArr) {
        HashMap<String, Integer> frequencies = new HashMap<>();
        for (int stringIndex = 0; stringIndex < strArr.length; stringIndex++) {
            String currentString = strArr[stringIndex];
            if (frequencies.containsKey(currentString)) {
                // since put updates or adds I can do this
                frequencies.put(currentString, frequencies.get(currentString) + 1);
            } else {
                frequencies.put(currentString, 1);
            }
        }
        for (Map.Entry<String, Integer> entry: frequencies.entrySet()) {
            System.out.println(String.format("KEY: %S, VAL: %s", entry.getKey(), entry.getValue()));
        }
    }

    // Exercise 4 -- HashMap<Integer, ArrayList<String>>, Reversed Key/Value Roles
    // Build a method that takes an ALString of names and groups them by length, producing the HM
    //      where each key is a name length and each value is the list of every name with that exact length
    public static void OrganizeNames(ArrayList<String> strings) {
        HashMap<Integer, ArrayList<String>> organizedHM = new HashMap<>();

        // so iterate through the array, check length, if it matches a key then add it to its AL
        // otherwise create the pair, add the string to the new AL
        for (String string: strings) {
            Integer currentLength = string.length();
            organizedHM.computeIfAbsent(currentLength, k -> new ArrayList<>()).add(string);
        }

        for (Map.Entry<Integer, ArrayList<String>> entry: organizedHM.entrySet()) {
            System.out.println(String.format("KEY: %S, VAL: %s", entry.getKey(), entry.getValue()));
        }
    }

    // Exercise 5 -- Nested HashMap, Two Levels of Keys
    // Build a HMstring, HMstring, Integer where each val is itself another map
    // Model something like a small gradebook, outer key is a students name, inner maps keys are subject names, inner maps values are scores
    // Build a method that takes a student name, subject, and a score and handles four real cases using only manual logic
    // If student doesnt exist, create both outer entry and a fresh inner map
    // Not going to keep going with reqs because they should be obvious
    // HashMap Structure -- HashMap<String, HashMap<String, Integer>>
    public static void recordStudentGrade(HashMap<String, HashMap<String, Integer>> HM, String studentName, String subject, Integer score) {
        if (HM.containsKey(studentName) == false) {
            HashMap<String, Integer> studentCourses = new HashMap<>();
            studentCourses.put(subject, score);
            HM.put(studentName, studentCourses);
        } else {
            HashMap<String, Integer> studentCourseHM = HM.get(studentName);
            if (studentCourseHM.containsKey(subject)) {
                Integer courseScore = studentCourseHM.get(subject);
                if (courseScore < score) { studentCourseHM.put(subject, score);}
            } else {
                studentCourseHM.put(subject, score);
            }
        }
        for (Map.Entry<String, HashMap<String, Integer>> entry: HM.entrySet()) {
            System.out.println(String.format("KEY: %S, VAL: %s", entry.getKey(), entry.getValue()));
        }
    }

    // Exercise 6 -- HashMap with ArrayList Keys...Sort Of - THe actual Trap of Mutable Keys
    // Attempt to build a HashMap<ArrayList<Intger>, String> using an ArrayList as the key instead of a String or Integer
    // Put a few entries in, using different small ArrayLists as keys
    // Then, after inserting, take one of the original ArrayList objects you used as a key and mutate it - call .add directly
    // Afterwards, try to .get() that same key again, using the same reference you just mutated
    // Predict in writing prior running whether that .get call will still successfully find the entry or whether it will fail
    //      reasoning from what you now know about how HashMap uses hash codes to locate entries in buckets then run and confirm whether the prediction was right
    public static void validEditableKeys(HashMap<ArrayList<Integer>, String> usingHM, ArrayList<Integer> k1, ArrayList<Integer> k2, ArrayList<Integer> k3) {
        usingHM.put(k1, "Pair 1"); usingHM.put(k2, "Pair 2"); usingHM.put(k3, "Pair3");
        k1.add(5); System.out.println(usingHM.get(k1));
        // since each arrayList is its own object, I dont believe that it would cause any runtime errors but considering how HashMap uses hashcodes to locate entries in buckets
        // if I do edit an ArrayList as a key then that bucket would be different than before since a new value was added so I do think itll cause an error well not an error but print out null
        // after running, it did infact print null
    }

    // Exercise 7 -- HashMap Driving Real Decision Logic
    // Build an inventory system
    // HashMap<String, Integer> tracks item names to stock counts
    // Build a method that takes the HM, itemName, and quantityRequested
    //      if the item doesnt exist in the map at all, reject the order with a clear message
    //      If it exists but current stock is less than the req quant, reject with a different message including the current stock
    //      If stock is sufficient, subtract the requested quantity from the map using .put to update and confirm the order succeeded by printing new remanining stock
    // Populate an inventory with several items and varying stock levels. Test all three outcomes explicitly and print inventorys full state before and after all 3 attempts
    public static void processOrder(HashMap<String, Integer> inventory, String itemName, int quantityRequested) {
        for (Map.Entry<String, Integer> entry: inventory.entrySet()) {
            System.out.println(String.format("Item: %s, Stock: %d", entry.getKey(), entry.getValue()));
        }

        if (!inventory.containsKey(itemName)) {
            System.out.println(String.format("%S is not an item that is currently being sold. The list above is our current items and their respective stock.", itemName));

            for (Map.Entry<String, Integer> entry: inventory.entrySet()) {
            System.out.println(String.format("Item Name: %s, Item Quantity: %d", entry.getKey(), entry.getValue()));
        }
        } else if (inventory.containsKey(itemName) && quantityRequested > inventory.get(itemName)) {
            Integer currentStock = inventory.get(itemName);
            System.out.println(String.format("There is only %d available of %s. Lower your requested amount.", currentStock, itemName));

            for (Map.Entry<String, Integer> entry: inventory.entrySet()) {
            System.out.println(String.format("Item Name: %s, Item Quantity: %d", entry.getKey(), entry.getValue()));
        }
        } else {
            inventory.put(itemName, inventory.get(itemName) - quantityRequested);
            // no other logic needed for updated the item such as doing the .get on its own line. that would work fine but code would be redundant
            System.out.println(String.format("You successfully bought %d of %s. Come again.", quantityRequested, itemName));
            System.out.println(String.format("New stock of %s: %d", itemName, inventory.get(itemName)));

            for (Map.Entry<String, Integer> entry: inventory.entrySet()) {
            System.out.println(String.format("Item Name: %s, Item Quantity: %d", entry.getKey(), entry.getValue()));
        }
        }        
    }

    // Exercise 8 -- HashMap Combined with Two Pointers
    // Solve the classic two sum problem but using a HashMap based single pass approach not the sorted two pointers approach form exc 4
    // Given an unsorted int[] and a target sum, walk through the array once using HM<Int, Int> to track values yovue already seen mapped to their index
    // For each number calculate what its complement would need to be (target - currentNumber) and check if that complement is already a key in the map
    //      if it is, youve found your pair immediately, using the current index and the stored index of the complement
    //      if it isnt, add the current number and its index to the map and continue
    public static void twoSumHM(int[] numbers, int target) {
        HashMap<Integer, Integer> occurences = new HashMap<>();
        // what i got from the clarification that lead to this simple if else statement
        // if the complement was already account for, example target= 11, currentNumber = 2, complement is therefore 9
        // so if 9 is there it prints out that complement and the current number which wouldve been 2. Valid pair
        // I have sysoutprntln to print out pairs as they come, theyre not stored dynamically then formatted for sysoutprntln
        // the else block adds the current number and its index
        for (int i = 0; i < numbers.length; i++) {
            int currentNumber = numbers[i];
            int complement = target - currentNumber;
            if (occurences.containsKey(complement)) {
                System.out.println(String.format("Found Pair: (%d, %d)", complement, currentNumber));
            } else { occurences.put(currentNumber, i);}
        }
    }


    // Set<T> - the interface and where it actually matters beyond no duplicates
    // Interface itself declared .add, .remove, .contains, .size, .isEmpty - same shape as List minus anything index-based
    //      Since a Set has no positions, only memeber ship
    // The real distinguishing behavior: .add on a vlaue already present returns false and does nothing, rather than throwing or duplicating
    //      worth resting that return value directly rather than assuming

    // Exercise 1 - St<T> as a Parameter Type, .add()'s Return Value Actually Used
    // Build a method typed to accept Set<Integer>(inferface)
    // Inside of it, attmept to .add a series of values, some of which are genuine dupes
    // Insted of ignoring the boolean .add() returns, capture and use it
    //      print whether each individual add attempt actually succeeeded or was silently rejected as a duplicate
    // Call this method passing in a genuine HashSet<Integer>
    // Prove the actual no dupes guarantee at the data level, not just the return value, after all additions print sets final size and confirm it accurately reflects only unique vals
    public static void addToSet(Set<Integer> setOf, int[] intsOf) {
        int counter = 0;
        for (int number: intsOf) {
            // since there is no return or throwing done with an .add on a Set, Ill use if else statement and a counter to confirm that it has added all of the elements
            if (setOf.add(number)) {
                System.out.println(String.format("Added %d to the set", number));
                counter += 1;
            }
        }
        if ( counter == intsOf.length) {
            System.out.println("All the elements were added to the array");
        } else {
            double percentage = ((double) counter / (double) intsOf.length) * 100;
            System.out.println(String.format("Only %f percent (%d/%d) were added to the set", percentage, counter, intsOf.length));
        }
    }

    // Exercise 2 - Set for Genuine Deduplication Across Tow Sources, Then Intersection
    // Build a mthod that takes two separate int[], each of which may contain internal duplicates of its own
    // Using two separate Set<Integer> ( one for each array ), deduplicate each array independently first
    // Then without using any built in intersection method, manually, using a loop and .contains() - find every value that exists in both deduplicated sets
    //      building a third Set<Integer> containing only the genuine overlap
    // Print all three sets clearly labeled, first arrays unique vals, same for 2nd, and the intersection Set
    // Test with two arrays specifically designed so some vals are unique to each array, some overlap and at least one array has inteneral duplicate that need deduplicating
    public static void findIntersection(int[] arr1, int[] arr2) {
        // init holder sets
        Set<Integer> set1 = new HashSet<>(); Set<Integer> set2 = new HashSet<>(); Set<Integer> intersectionValues = new HashSet<>();

        // deduplication to both arrs
        for (int number: arr1) {
            // no need for an if else statement
            set1.add(number);
        }
        for (int number: arr2) {
            set2.add(number);
        }

        // find intersections
        //for (int firstNumber: set1) {
          //  for (int secondNumber: set2) {
              //  if (secondNumber == firstNumber) { intersectionValues.add(secondNumber); } else {
                //    System.out.println(String.format("%d is unique to set2", secondNumber));
            //    }
          //  }

            // this for loop compares each currentNumber to each currentNumber of first set
            // this may not be opportune since its n^2 time and will run a total of set1.size() * set2.size() times
            // below will be using contains
        //}

        // find intersections - improved with .contains()
        // ill compare set1 v set2
        for (int number: set1) {
            if (set2.contains(number)) { intersectionValues.add(number); } else {
                System.out.println(String.format("%d is unique to set 1", number));
            }
        }
        System.out.println(set1); System.out.println(set2.toString()); System.out.println(intersectionValues.toString());
        // the reason why I did toString on only one is to see the what differs in the string format it returns
    }

    // Exercise 3 - Set for Detecting Duplicates Without Removing Them
    // This exercise uses Set purely as a detection tool, while the orginal data ( with dupes intact) stays untouched
    // Build a method that takes an int[] which may contains dupes and returns two things:
    //      whether the array contains any duplicate at all ( a boolean ) and specifically which values are duped (not just yes or no)
    //          the actual repeated vals themselves, each listed only once even if a value contains three or more times in the input
    // Do this using two separate Set<Integer> working together in single pass through the array
    //      think through what role each one needs to play, since one Set alone can only tell you "have I seen this before"
    // Need to accumulate a separate answer (which specific answer are dupes) without contaminating the "have I seen it" tracking
    // Test with an array where one value appears exactly twicee, another appears three times, and several vals appear only once
    //      confirming the dupe value output correctly lists each repeated value exactly once, regardless of how many times it actually repeated
    public static void searchDupes(int[] numbers) {
        // this is rather simple, we iterate the array passed in as the param and add each val to a set since its .add method adds whether it is unique to the set and not a dupe
        // so one set contains the unique vals (no dupes), the other contains the vals which were duped
        Set<Integer> uniqueSet = new HashSet<>(); Set<Integer> dupeContainer = new HashSet<>();
        //for (int val: numbers) {
          //  if (uniqueSet.contains(val)) {
                // this is already a duplicate so we append it to the dupeContainer
                // but since sets dont accept duplicates such as in the arr { 1, 2, 2, 3}
                // trying to add index 2 to dupeContainer will fail so we check like aboce
            //    if (!dupeContainer.contains(val)) { dupeContainer.add(val); }
           // } else { uniqueSet.add(val); } // im going to reformat this so i dont need a branched if else
      //  }

        for (int val: numbers) {
            if (!uniqueSet.add(val)) { dupeContainer.add(val); // adds to dupeContainer to see if its not been accounted for 
            }
        }
        System.out.println(String.format("Original: %s%nUnique: %s%nDuplicates: %s", Arrays.toString(numbers), uniqueSet, dupeContainer));
    }

    
    // Moving onto Map with .forEach() introduced
    // .forEach() - a method based alternative to the enhanced for loop, taking a lambda
    // numbers.forEach(n -> Sysoutprntln(n))
    // Functionally similar to for (int n: numbers) but expressed as a method call on the collection itself, passing a lamda describing what too do with each ele
    // Works on any Collection and notably Map has its own two arg version
    //      someMap.forEach((key, value) Sysoutprntln(key + "" + v))
    //      This is genuinely useful for Map specifically, since it gives you both the key and value directly as two separate lambda params, without needing Map.Entry or .getKey()
    //          a more concise alternative to the entrySet() loop

    // Exercise 1 -- Map<K, V> Interface, .forEach() as the Primary Iteration Method
    // Rebuild something conceptually similar to the first HashMap exc ( populate a map, check keys, iterate)
    // But this time, the methods parameter must be typed Map<String, Integer> and all iteration must use .forEach(), not entrySet() not any manual for loop at all
    // Populate a map representing something of your choosing then use .forEach() with a two parameter lambda to print every entry
    //      and separately use .forEach() again to compute something real across all entries - like a running total
    //          proving you can accumulate state acorss a .forEach call, not just print inside of it
    public static void reduceMap(Map<String, Integer> map) {
        // output map with .forEach()
        map.forEach((key, value) -> System.out.println(String.format("(KEY: %s, VAL: %d", key, value)));

        // since a val used in a .forEach has to be final, well use a single digit array
        int[] totalSum = {0};
        // reduce map
        map.forEach((string, integer) -> {
            totalSum[0] += integer;
        });
        System.out.println(String.format("Map values summed: %d", totalSum[0]));
    }

    // TreeMap<K,V> - A Map implementation backed by a red-black tree, not a hash table
    // HashMap stores entries by computing each key's hash code and placing it into a bucket - fast (O(1)) average fro get/put but with no ordering guarantee
    // TreeMap instead stores entries in a self-balancing binary search tree structure internally
    //      Organized entirely by key comparison (using iether the key's natural ordering - like alphabetical for String, numeric for Integer - or a custom comparator you can supply)
    //      Every time you insert, the tree reorganizes itself to stay balanced, keeping every operation efficient even as it grows
    // What this means for performance, concretely
    //      get/put remove on a TreeMap run in O(log(n)) time - slower tahn HashMaps O(1) avg case because
    //          navigating a tree requires comparing against multiple nodes rather than jumping straight to a hash bucket
    //      Youre paying a performance cost specifically to gain guaranteed sorted iteration order, soemthing HashMap cannot offer at any cost since it doesnt track order at all
    // Memory-Wise
    //      TreeMap requires more overhed per entry than Hashmap, since each internal tree node needs to store references to its parent and child nodes to maintain the tree strcucture
    // What it holds and how its accessed
    //      identical external API to HashMap
    //          .put, .get, .containsKey, .forEach, .entrySet
    //      Only observable difference from the outside is iteration order, which is always sorted by key, every single time
    // Additional methods TreeMap offers that HashMap doesnt, since sorted order enables them
    //      .firstKey, .lastKey, .lowerKey(k) (next key strictly greater/less than a given one), .headMap(k), .tailMap(k) (sub maps of everything before/after a given key)

    // Exercise 2 -- Map<K, V> Interface, Two Implementations Compared via .forEach
    // Build a method typed to accept Map<String, Integer>
    // Using .forEach for all iteration as established in the exercise above
    // Call it twixe with S, I and TreeMap S I
    //      TreeMap automatically sorts entries by key during iteration unlike HMs unpredictable bucket order
    // Populate both with the same five k-v pairs in the same insertion order
    // Print both .forEach outputs cleary labeled and compare directly
    //      confirm TreeMaps iteration comes back in sorted key order regardless of insertion order, while Hms stays in its usual hash-bucket order
    public static void compareMaps(Map<String, Integer> map) {
        map.forEach((string, integer) -> System.out.println(String.format("KEY: %s, VAL: %d", string, integer)));
    }

    // Exercise 3 - Map Method Chaining with .merge()
    // .merge(k, v, remappingFunction) - a single method that replaces the manual check if key exists the either combine with existing value or insert fresh
    // It takes a key, value, lambda describing how to combine the new value with an existing one, if there is one
    // counts.merge("apple", 1, (oldValue, newValue) -> oldValue + newValue)
    //      If apple isnt in the map yet, this simply inserts 1 under that key, the lambda isnt called
    //      If apple is present, the lambda runs, receiving the existing value and the new value you passed in, and whatever the lambda returns becomes the new stored value
    // Storage/performance
    //      .merge() doesnt change anything about how the map itself stores data internally - purely a convenience method wrapping the same underlying get/put ops
    //          meaning theres no performance cost or benefit versus doing it yourself with an if/else, only a difference in code length and readability
    // Build a wor-frequency counter, implemented using only .merge, no manual if/else/containsKey()
    // Test against an array with genuine repeats and confirm the output matches what the original manual version would have produces
    public static void mergeFrequency(String[] strings) {
        TreeMap<String, Integer> sortedOccurences = new TreeMap<>();
        for (String string: strings) {
            sortedOccurences.merge(string, 1, (currentCount, updatedCount) -> currentCount + updatedCount);
        }
        System.out.println(sortedOccurences);
    }

    // Exercise 4 - .merge with a Remapping Function That Can Remove Entries
    // Something worth knowing - if the lambda you provide returns null, .merge doesnt just store null as the value - it removes the key entirely from the map
    // Build a method modeling a small "strikes" system 
    // Map<String, Integer> tracking how many strikes each person has
    // Each time a person gets a new strike call .merge to increment their count, same as the freq counter
    // But this time the remapping lambda needs real condition logic inside it
    //      if incrementing would push someones count to 3 or more, the lambda should return null instead of the incremented number
    // Test with several people, some accumulating enough strikes to hit the removal threshold, some staying under it
    // Print the map after every single strike is recorded so you can see specific people disappear from the map exactly when they cross the threshold
    public static void strikeRecord(String name,Map<String, Integer> players) {
        players.merge(name, 1, (oldValue, newValue) -> {
                if (oldValue + newValue <=2) { return oldValue + newValue;} else { return null;}
        });
        System.out.println(players);
    }

    // Exercise 5 - .compute(), the More General Sibling of .merge()
    // .comput(key, remappingFunction) - similar spirit to .merge but more general and worth understanding how it differs
    // .merge's lambda receives two vals (old value and new value thats passed in) and only runs if the key already exists (otherwise it just inserts your new value directly, lambda skipped)
    // .compute's lambda instead receives the key itself and the current value (which will be bull if the key doesnt exist yet) 
    //      meaning .compute's lambda always runs, every time, whether the key existed before or not
    //          and its the lambda's own job to handle the "value might be null" case internally, rather than .merge handling that automatically
    // map.compute(key, (k, currentValue)) -> { if (currentValue == null) { return 1;} else { return currentValue + 1;} }
    // This produces the same result as .merge(key, 1, (old, new)) for a simple increment case
    //      but .compute gives you access to the key inside the lambda too (useful if the logic needs to reference the keys value, not just the count)
    //          and forces you to explicitly handle the null case
    // Rebuild the strikes system from Exc 4 using .compute
    // Explicitly handle the player being null inside the lambda
    // Test with at least one player who isnt already in the map when their first strike is recorded
    public static void strikeRecord_Compute(String keyPlayerName, Map<String, Integer> players) {
        // logic to retain -- if key doesnt exist then that pair doesnt exist

                // since compute supplies the key itself and the current value keeping mind the "logic to retain" above
                // we guarantee its existence since were using a forEach, so were iterating over what currently exists
                // given the key and current value we can do the logic for removal or adding to strike score
                // add to score
                
                // I honestly have to think about these lambdas as a closure in Swift, Ive confusen myself in this compute body several times so far
                // what Ive began to understand that its a function thats being ran and returns the new value associated with the current key being k
                // justified by the documentation given by hovering over the compute keyword
                // now im doing something right because Ive ran into the ConcurrentModificationException error again meaning that we cannot use a for each
                // therefore we can use a name as a key as a param being passed in and just use compute, no forEach or get needed
        players.compute(keyPlayerName, (k, v) -> {
            if (v == null) { return 1;} // this adds this pair (keyPlayerName, 1)
            Integer newScore = v + 1;
            if (newScore == 3) { return null;} else { return newScore; }
        });
        // now this version works just like using the merge did but not to the extent we need it too, since we pass in a keyPlayerName to look up the key need to make sure its not null
        // updated version is above 
        System.out.println(players);
    }


    // LinkedHashMap<K, V> -- Full Explanation Before Any Exercises
    // HashMap stores by hash bucket, no order guarantee
    // TreeMap stores in a tree, guaranteeing sorted-by-key order.
    // LinkedHashmap takes a third approach
    //      internally, it still uses the same hash-bucket mechanism as HashMap for fast O(1) lookups
    //      but, it additionally maintains a doubly-linked list threading thorugh every entry in the order they were inserted, purely for iteration purps
    // When you iterate a LinkedHashMap, it walks that separate linked list, not the hash buckets directly, which is why iteration order matches insertion order exactly, every time, guaranteed
    // Performance Wise - 
    //      get/put remain O(1) avg case, identical to HashMap since underlying hash mechanism is unchanged
    //      The cost is purely in memory overhead - every single entry now needs two extra pointer refs (previous/next in the linked list)
    //          beyond what a plain HashMap entry needs
    //      And theres a small constant-time cost to maintaining that linked list on every insertion/removal
    //          TreeMap, by comparison, traded get/put speed itself (O(log n)) for its ordering guarantee; LHM trades only memory and a small constant overhead, keeping get/put just as fast as HM
    // One additional capability worth knowing exists:
    //      LHM has a special constructor mode that can maintain access order instead of insertion order
    //          meaning the most recently read or written entry moves to the end of the iteration order every time you touch it, rather than styaing fixed at its orig pos
    //      This specific mode is the actual mechanism real LRU (Least Recently Used) caches are built on - directly relevant to DSA since "implement an LRU cache" is one of the most commonly asked interview problems
    //          LHMs access order mode does most of the heavy lifting for you
    // Every method, What It Does and Whats Happening Underneath
    //      Since LHM extends HashMap ( a real class inheritance relationship, not just a similar looking sibling) it inherits every single method HM has
    //          .put, .get, .remove, .containsKey, .containsValue, .getOrDefault, .merge, .compute, .forEach, .entrySet, .keySet, .values, .size, .isEmpty, .clear
    //              The only difference is how iteration-related ones (.entrySet, .keySetm .values, .forEach) walk through the data, using the internal linked list instead of raw bucket order
    // Whats Genuinely New or Different, not just inherited
    //      There is no .firstKey or .lastKey - unlike TreeMap, LHM doesnt expose direct "give me the first or last entry" methods
    //      Since it maintains insertion order via the internal linked list but doesnt expose that list directly, the standard way to get the first entry is calling .entrySet().iterator().next()
    //          grabbing an iterator over the entry set (which will always start from the first inserted entry, guaranteed) and taking just the first element it produces without looping through the rest
    // The access-order constructor
    //      new LinkedHashMap<>(initialCapacity, loadFactor, true) - the boolean arg switches the map from insertion order mode (the default) to access-order mode
    //      In this mode, every .get or .put on an existing key moves that entry to the end of the iteration order, as if it were just re-inserted
    //      This is what makes LHM the standard building block for LRU caches - combined with overriding a protected method called removeEldestEntry() (which youd override in a subclass to automatically evict the oldest entry once the map exceeds some size)
    //          it gives you a working "least recently used" eviction policy, almost for free

    // Intro Exercise - LHM Proving Insertion Order Directly, Against a Non-Trivial Key Set
    // Build a HashMap<String, Integer>  and a LHM same type
    // Insert the same five keys in the same deliberately non alpha order into both and print both using .forEach
    // Confirm directly; does LHMs output match your exact insertion order every single time while HMs output is unpredictable
    public static void compareOrder(Map<String, Integer> map) { map.forEach((k, v) -> System.out.println(String.format("KEY: %s, Val: %d", k, v))); }

    // Exercise 1 - LHM Driving Real Logic
    // Build a recently viewed items tracker - LHM<String, Integer> where key is an itemName and value is a view count
    // Build a method that records a view; if the item hasnt been seen before, add it with a count of 1; if it has, increment its count
    // Heres the actual req forcing real logic -
    //      after every single view is recorded, print the first key in the map (the oldest item still tracked, by insertion order) using an actual method call, not just eyeballing printed output
    // LHM doesnt have .firstKey the way TreeMap does so figure out how to correctly retrieve the first entry
    public static void recentlyViewed(LinkedHashMap<String, Integer> lhm, String key_ItemName, int maxSize) {
        // since were working with insertion order, the LHM will remain with its default constructor
        // so were basically adding to the value of the pair given by the key_ItemName
        // simple way to do this would be getOrDefault since it appends to the map if it doesnt exist
        // tried with gOD but it doesnt return an Integer value, sicne it doenst exist we can just append with .put
        //      now this makes me run into ConcurrentModificationException whenever adding the pair that hasnt been added

        // "Modifying a map" during iteration specifically means structural changes
        //      adding a new key, removing a key - anything that changes the maps size or entry set membership
        // Simply updating an existing keys value like .merge incrementing a count for a key thats already present does not count as structural modification since the entry itself still exists, only its value changed
        // The compute didnt work since .put does exactly that, it modifies structure
        // .merge doesnt have this problem because its self contained - you never call .put yorself inside .merge's lambda
        //      .merge handles the actual insert or update internally based on what your lambda returns, not based on you separately calling another mutating method
        //      Real Distinction: .merge's lambda only computes a value and returns it
        // The fix for compute: never call .put from inside a compute/merge lambda - just return the value you want stored, and let the method itself handle the actual insertion
        lhm.merge(key_ItemName, 1, (oldValue, newValue) -> { return oldValue + newValue; });
        // lhm.compute(key_ItemName, (k, currentValue) -> {
        //     if (currentValue == null) { lhm.put(key_ItemName, 1); return 1;} else {
        //         return currentValue + 1;
        //     }
        // });
        if (lhm.size() > maxSize) { 
            lhm.remove(lhm.entrySet().iterator().next().getKey());
            // with a maxSize of 3, once a fourth distinct item gets added, the maps size becomes 4, exceeding the limit so the oldest entry (the first inserted) gets remove
            //  Whenever the sysoutprntln is called below, it will be the next oldest item which would be index 1
        }
        System.out.println(lhm.entrySet().iterator().next());
        // Eviction Logic - The Core Idea
        //      you need a max size limit
        //      Everytime a view is recorded, if the maps size exceeds that limit after the insert remove the oldest entry - and since LHM maintains insertion order, the oldest entry is always
        //          whatever .entryset.iterator.next give you
        //      The only new piece is: get that first entry's key specifically (not just print it) and call .remove on that key
        //  Logic updated above
    }

    // Exercise 2 - Access-Order Mode, Built and Proven Directly
    // This exercise requires actually constructing a LinkedHashMap in access-order mode and proving that reading an existing entry moves it to the end of iteration order
    // LinkedHashMap<String, Integer> lhm = new LinkedHashMap(16, 0.75f, true);
    // The three constructor arguments:
    //      16(reasonable default): initial capacity
    //      0.75f: load factor, .75 f is standard default - the threshold at which the internal structure resizes
    //          This ogverns when a hash-based strcuture resizes itself internally
    //          Every HashMap/LHM starts with some number of internal "buckets" (the initial capacity - 16 by default)
    //          As you .put more entries in, the map fills up those buckets.
    //          Load factor is the threshold, expressed as a fraction, at which the map decides its getting too full and needs to grow
    //          0.75f means once the map is 75% full relative to its current capacity, it automatically resizes -
    //              doubling its internal bucket count and rehashes every existing entry into the new larger strcutor
    //          Why 0.75 specifically and why its a tradeoff and not an arbitrary default
    //              a lower load factor (resizing sooner, when less full) means more wasted empty bucket space at any given time, but fewer hash collisions (two different keys landing in the same bucket)
    //                  keeping lookups closer to true O(1)
    //              A higher load factor (waiting until nearly full before resizing) means better memroy efficiency, less wasted space but more collisions
    //              Since buckets get crowded before the resize kicks in, which can degrade get/put performance closer to O(n) in the worst case if many keys collide into the same bucket
    //          Resizing itself has a real, if usually invisible, cost:
    //              Every time a resize happens, every single existing entry has to be rehashed and redistributed into the new bucket layout -
    //                  an O(n) operation that happens automatically and infrequently but is worth knowing about since its part of why HM/LHM are described as O(1) avg case
    //              An insertion that triggers a resize is momentarily more expensive than a typical one
    // Build a small program: populate this access-order map with 5 entries. Print full iteration order. Then call .get on one of the middle entries
    // Print iteration order again and confirm directly that the entry you just read has moved to the very end while everything else's relative order stayed the same
    // This exercise was done in the main func

    // Exercise 3 - LRU Cache, Built for Real
    // Build a class (not just a static method this time - a real, small class) implementing a basic LRU cache: a fixed maximum size, using an access-order LHM internally as its storage
    // When a new item is added and the cache is already at capacity, the least recently used entry - determine by access-order mode, meaning whichever entry hasnt been read or written in the longest time should be evicted
    // using the if vs while lesson from Exc 1 correctly applied (should only ever need to evict one entry per insertion, if capacity is enforced correctly on every single add - reason thoruh why thats true here, unlike Exercise 1 where the map started over limit)
    // Give the class two public methods: one to record a "get" (which should count as a use, moving that entry to the end via access order mode automatically) and one to record a "put"
    // Test it with a small capacity (like 3), inserting more items than the capacity allows, interspersing some .get() calls on existing entries between insertions to prove that reading an item protects it from eviction just as much as writing one
    // then insert enough new items to force eviction and confirm specifically that the item you deliberately kept fresh via .get survives while an item you never touched again gets evicted first, exactly as true LRU behavior demands
    public static class LRUExample {
        String name; LinkedHashMap<String, Integer> lhm; int maxSize;
        public LRUExample(String name, LinkedHashMap<String, Integer> lhm, int maxSize) {
            this.name = name; this.lhm = lhm; this.maxSize = maxSize;
        }
        public final Integer getVal(String keyName) {
            return lhm.get(keyName);
        }
        public final void putPair(String keyName, Integer value) {
            lhm.put(keyName, value);
            if (lhm.size() > maxSize) { lhm.remove(lhm.entrySet().iterator().next().getKey());}
        }
        public final void returnLHM() { 
            lhm.forEach((keyName, integerValue) -> {
                System.out.println(String.format("Key: %s, Value: %d", keyName, integerValue));
                // again eviction happens whenever a pair is read or written to so the "return value" after 5 insertions should be 3-5
            });
        }
    }

    // Exercise 4 -- Two LRU Caches, One Feeding Evictions Into the Other
    // This forces the cache to interact with something outside itself as a side effect of eviction, rather than evicted entries simply disappearing - a real patter (write behind / overflow caching) worth building correctly
    // Build a new version of the LRU Cache above so that whenever an entry is evicted, instead of just being removed and discarded, it gets recorded into a second, separate strcuture,
    //      a plain HM<String, Integer> acting as cold storage for everything thats ever fallen out of the hot cache
    //      This seocnd map should never evict anything itself - just accumulates every evicted key-value pair permanently
    // Build a method, String checkAnywhere(String key) that searches both structures - checks hot LRU cache first and if found, this counts as a genuine use menaing it should trigger the same access order protections as a normal read
    //      if not found there, checks cold storage map
    // Return astring indicating exactly where the key was found hot cache vs cold storage or that it doesnt exist in either
    // Test with enough insertions to force several evictions into cold storage then call checkAnywhere on a key you know has been evicted (confirming its found in cold storage, not hot cache)
    //      and on a key you know is still in the hot cach (confirming its found there instead and that checking it via checkAnywhere actually protects it from future eviction same as a direct get would)
    public static class LRUExample2 {
        String name; LinkedHashMap<String, Integer> lhm; int maxSize; TreeMap<String, Integer> evictionContainer;

        public LRUExample2(String name, LinkedHashMap<String, Integer> lhm, int maxSize, TreeMap<String, Integer> evictionContainer) {
            this.name = name; this.lhm = lhm; this.maxSize = maxSize; this.evictionContainer = evictionContainer;
        }

        public final void putPair(String keyName, Integer value) {
            lhm.put(keyName, value);
            if (lhm.size() > maxSize) {
                Map.Entry<String, Integer> evictingPair = lhm.entrySet().iterator().next();
                evictionContainer.put(evictingPair.getKey(), evictingPair.getValue());
                lhm.remove(evictingPair.getKey());
            }
        }

        public String checkAnywhere(String keyName) {
            Boolean hot = false; Boolean notAnywhere = false;
            if (lhm.get(keyName) != null ) { hot = true; } else if (evictionContainer.get(keyName) != null ) {} else { notAnywhere = true; }
            String returnValue = notAnywhere ? String.format("%s was not found anywhere", keyName) : hot ? String.format("%s was found in the LRU", keyName) : String.format("%s was found in the eviction container", keyName);
            return returnValue;
        }

        public final void returnLHM() { 
            lhm.forEach((keyName, integerValue) -> {
                System.out.println(String.format("Key: %s, Value: %d", keyName, integerValue));
                // again eviction happens whenever a pair is read or written to so the "return value" after 5 insertions should be 3-5
            });
        }
    }

    // Exercise 5 -- Two Independent LRU Caches, Cross-Cache Promotion
    // Might be the hardest one in this stretch since it forces reasoning about two separate access-order structures interacting with each other, not just one cache plus a passive cold storage sink
    // Build a two tier cache system: a small hot LRU cache (maxSize 2) and large warm lru cache (maxsize 4) both using access-order LHMs internally
    //      both genuinely LRU (evicting their own oldest-unused entry when they exceed their own limit)
    // When something is evicted from the hot cache, instead of being discarded or dumped into a passive map, it should be inserted into the warm cache
    //      meaning the warm cahce's own LRU eviction logic might itself trigger as a consequence, potentially evicting somethign from the warm cache too (which, for this exc, can simply be discarded - no third tier needed)
    // Build one method, access(String key, Integer value) that always writes to the hot cache first.
    // Build a second method, promote(String key) that specifically searches the warm cache for a key and if found mvoes it back into the hot cache, removing it from war, inserting it into hot (which may itself trigger a hot cache eviction, cascading back into warm again)
    // Test a sequenece where an item gets written to hot, evicted into warm (by writing enough new items to hot)
    // Then explicitly promoted back into hot via promote and confirm its genuinely back in the hot cache afterward while also confirming that whatever the promotion evicted from hot correclty lands in warm as a result
    public static class LRUExample3 {
        LinkedHashMap<String, Integer> hotLRU; int hotLRU_MAXSIZE; LinkedHashMap<String, Integer> warmLRU; int warmLRU_MAXSIZE;

        public LRUExample3(LinkedHashMap<String, Integer> hotLRU, int hotLRU_MAXSIZE, LinkedHashMap<String, Integer> warmLRU, int warmLRU_MAXSIZE) {
            this.hotLRU = hotLRU; this.hotLRU_MAXSIZE = hotLRU_MAXSIZE; this.warmLRU = warmLRU; this.warmLRU_MAXSIZE = warmLRU_MAXSIZE;
        }

        public final void accessHOT(String keyName, Integer value) {
            hotLRU.put(keyName, value); System.out.println(String.format("New Size of Hot LRU is %d", hotLRU.size()));
            if (hotLRU.size() > hotLRU_MAXSIZE) {
                Map.Entry<String, Integer> removingPair = hotLRU.entrySet().iterator().next();
                accessWARM(removingPair.getKey(), removingPair.getValue());
                hotLRU.remove(removingPair.getKey());
                System.out.println("new size of hot lru is " + hotLRU.size() + " removed " + removingPair.getKey());
            }
        }
        public final void accessWARM(String keyName, Integer value) {
            warmLRU.put(keyName, value); System.out.println("A pair was added to the warm lru: " + keyName);
            if (warmLRU.size() > warmLRU_MAXSIZE) {
                warmLRU.remove(warmLRU.entrySet().iterator().next().getKey());
                System.out.println(String.format("%s was removed completely", keyName));
            }
        }
        public final Boolean promote(String keyName) {
            if (warmLRU.get(keyName) != null) {
                warmLRU.remove(keyName);
                accessHOT(keyName, warmLRU.get(keyName));
                return true;
            } else { System.out.println(hotLRU.size()); return false; }
        }

        public final void returnLHM() { 
            hotLRU.forEach((keyName, integerValue) -> {
                System.out.println(String.format("Key: %s, Value: %d", keyName, integerValue));
                // again eviction happens whenever a pair is read or written to so the "return value" after 5 insertions should be 3-5
            });
            System.out.println("-------");
            warmLRU.forEach((keyName, integerValue) -> {
                System.out.println(String.format("Key: %s, Value: %d", keyName, integerValue));
                // again eviction happens whenever a pair is read or written to so the "return value" after 5 insertions should be 3-5
            });
        }
    }

    // Discussion With Claude - Breaking Your Own Two Tier System on Purpose
    // This exercise is about finding its actual limits rather than proving another case that already works, think adversarially about the LRUExample3
    // Specifically: what happens if promote() is called on a key that exists in neither hot nor warm at all?
    // Trace the code for this case before running anything - does promote() handle a completely nonexistent key gracefull or does something break? Test it directly
    // Separately, and more interesting structurally: what happens if the hot caches maxSize is set to something that would make a single promotion cascade trigger two or more evictions in a row
    // is the current eviction logic (if, not while, inside accessHOT) actually sufficient for every possible scenario or could you construct a specific sequence of calls where
    //      hot ends up sitting more than one entry over its stated limit because eviction only fires once per accessHOT call, same if vs while question in Exc1 and 3
    // Test both scenarios directly, report what actually happens and if the second one reveals a real gap, fix it
    // Answer to above
    // Since the if else checks if the .get return val is not null then this is okay, the else branch does absolutely nothing
    // what would happen if the hot caches max size is set to somethign that would make a single promototion cascade trigger two or more evictions in a row is this
    //      lets say we have 5 pairs just like we had before, hot MS is 2 and warm is 4, hot size would be 2 and warm would be 3
    //       and then I promote two of those, those two are inputted into hot and the two oldest pairs are then sent to warm so size of each after operations would remain the same
    //       so final verdict is this, if the total amount of entries doesnt exceed the summed size of hot and warm then their size will remain the same after n promotions
    //       justification of this: promote all 3 of the ones in warm, 3 get sent to hot, 3 get sent back to warm which would include one of the ones that were promoted
    //       so this means that each lhm helps each other in this case and if there were 6 entries, any amount of promotions would send that same number of entries back to warm
    //       but if there were 7, that final one would get sent to warms eviction
    // The current eviction logic is complete for this particular scenario since the structure of these methods only allow single calls which are completed line by line

    // Exercise 6 - A Third Tier, Built From Scratch
    // Build a genuine three tier cache - hot, warm and cold
    //      hot overflows into warm and warm itself now also has real eviction logic that cascades into cold
    //      Cold can simply be a plain HashMap with no size limit and no further eviction - a "resting place"
    // Build one method insert(String key, Integer value) that always writes to hot, and correctly lets a single insertion potentially cascase all the way through all 3 tiers in the worst case
    //      hot evicts into warm, and that specific arrival into warm itself immediately cause warm to evict into cold, all as a consequence of one single insert call
    // Test with small capacities on hot and warm like 1 and 2 and enough insertions to force at least one item to travel all the way to cold in a single traceable sequence
    //      printing the state of all 3 tieres after each insertion so the full cascade is visible step by step
    public static class LRUExample4 {
        LinkedHashMap<String, Integer> hot; int hotMS;
        LinkedHashMap<String, Integer> warm; int warmMS;
        Map<String, Integer> cold;
        public LRUExample4(LinkedHashMap<String, Integer> hot, int hotMS, LinkedHashMap<String, Integer> warm, int warmMS, Map<String, Integer> cold) {
            this.hot = hot; this.hotMS = hotMS; this.warm = warm; this.warmMS = warmMS; this.cold = cold;
        }

        public final void insert(String keyName, Integer value) {
            // the base case would be if both warm and hot are full so start with appending this entry
            hot.put(keyName, value);
            Boolean hotExceeded = hot.size() > hotMS;
            Boolean warmFull = warm.size() == warmMS;
            if (hotExceeded && warmFull) {
                // get removing pair which is the lru on the lhs; remove first so it doesnt exist in 2 places at the same time
                // since theyre both at max size, we need the lru from warm too
                Map.Entry<String, Integer> removingHotPair = hot.entrySet().iterator().next();
                Map.Entry<String, Integer> removingWarmPair = warm.entrySet().iterator().next();
                hot.remove(removingHotPair.getKey());
                warm.remove(removingWarmPair.getKey());
                insertWarm(removingHotPair); insertCold(removingWarmPair);
                // justification for the if logic differing is because we add regardless to hot, this causes size to exceed maxSize
                // so we need to make sure that if this eviction causes warm to exceed then warm would then evict down to cold
                // this ensures that in this particular scenario that the removingPair from hot does not trigger warms eviction logic, its done here
                System.out.println(String.format("Hot exceeded its size whenever %s was added. Therefore, %s was removed from hot and placed into warm. But since warm also exceeded its size, %s had to be removed from warm and cascaded down to cold. %n Be aware that any more calls to this method will trigger this message.", keyName, removingHotPair.getKey(), removingWarmPair.getKey()));
            } else if (hotExceeded) {
                // this can then cleanly evict down to warm
                Map.Entry<String, Integer> removing = hot.entrySet().iterator().next();
                hot.remove(removing.getKey());
                insertWarm(removing);
                System.out.println(String.format("%s triggered this eviction. %s is now going to the warm lhm", keyName, removing.getKey()));
            } else { System.out.println(String.format("%s was added successfully", keyName));}
        }

        private final void insertWarm(Map.Entry<String, Integer> nowWarm) {
            warm.put(nowWarm.getKey(), nowWarm.getValue());
            if (warm.size() > warmMS) {
                Map.Entry<String, Integer> removing = warm.entrySet().iterator().next();
                warm.remove(removing.getKey());
                insertCold(removing);
            }
        }

        private final void insertCold(Map.Entry<String, Integer> evictedPair) {
            cold.put(evictedPair.getKey(), evictedPair.getValue());
        }
        public final void returnCold() {
            System.out.println("HOT: " + hot); System.out.println("WARM: " + warm); System.out.println("COLD: " + cold);
        }
    }

    // Exercise 7 - N-Tier Cascade, No Hardcoded Branches
    // The LRUExample 4 correctly handles 3 tiers but it does so with explicit hardcoded branching for hot alone versus hot and warm both.
    // This approach doesnt scale - a fourth tier would require a fourth hardocded combination, a fifth even more
    // This exercise requires solving the general problem: build a cache system with an arbitrary number of tiers (test with at least 4)
    //      where a single insertion can cascade through as many tiers as necessary in once call, without writing a separate hardcoded branch for each possible combination of how many tiers overflowed
    // Think through the actual shape this needs: rather than checking hotExceeded && warmFUll as two named bools, you likely need some kind of loop or recursive structure that says keep push the evicted entry to the next tier, as long as the tier its landing in also now over capacity
    //      stopping naturally once it lands somewhere with room or falls off the final tier into a bottomless cold storage
    // Design the data structure holding the tiers (a List of LHMs, each paired with its own max size, might be the natural shape) and build the general cascade logic
    // Test with 4 tiers, small capacities, and enough insertions to force a single insert to cascade through all four in one call, proving the logic generalizes rather than being hardocded
    // List with LHMs -> List<Map.Entry<LinkedHashMap<String, Integer>, Integer>> tiers = new ArrayList<>();
    //  an ArrayList (concrete implementation) held as a List (interface type) where each indiv element is itself a Map.Entry - pairing an LHM together with an Integer (that resembles its maxSize)
    //  So tiers.get(0) would give the hot lhm and so forth - an ordered list where position is its tier rank, letting your cascade logic loop thorugh pos generally rather than naming each one respectively
    // Building one entry to add to this list since Map.Entry isnt something you construct with new the way most classes are - use the static factory method instead 
    //      tiers.add(Map.entry(new LinkedHashMap<>(16, 0.75f, true), 2));
    //          Map.entry(k, v) is a static factory method (lowercase entry, on the Map interface itself) that constructs an immutable Map.Entry pairiing whathever two values you give it
    //              here, a fresh lhm as the key and 2 as its MS as the val
    static List<Map.Entry<LinkedHashMap<String, Integer>, Integer>> tiers = new ArrayList<>();
    public static void insertPair(String keyName, Integer value) {
        // first breaking down the structure of the ArrayList since it can be kind of confusing
        // an entry would me the LHM obj and its max size
        String keyName2 = keyName; Integer value2 = value;

        // for (int i = 0; i < tiers.size(); i++) {
        //     LinkedHashMap<String, Integer> currentLHM = tiers.get(i).getKey();
        //     Integer currentMS = tiers.get(i).getValue();

        //     if ( currentLHM.size() + 1 <= currentMS) {
        //         currentLHM.put(keyName2, value2);
        //         break;
        //     } else if (currentLHM.size() + 1 > currentMS) {
        //         currentLHM.put(keyName2, value2); // always gets added

        //         Map.Entry<String, Integer> rp = currentLHM.entrySet().iterator().next();
        //         keyName2 = rp.getKey(); value2 = rp.getValue();

        //         currentLHM.remove(rp.getKey());
        //     }
        // }

        for(Map.Entry<LinkedHashMap<String, Integer>, Integer> tier: tiers) {
            LinkedHashMap<String, Integer> currentLHM = tier.getKey();
            currentLHM.put(keyName2, value2);

            if (currentLHM.size() > tier.getValue()) {
                Map.Entry<String, Integer> evicted = currentLHM.entrySet().iterator().next();
                currentLHM.remove(evicted.getKey());
                keyName2 = evicted.getKey(); value2 = evicted.getValue();
            } else { break; }
        }

        System.out.println(tiers);
    }

    // Ten Exercises Combining around 3 concepts covered - respectively
    // Exercixe 1 - Combinging HM, AL, and Set
    // Build a method that takes an ArrayList<String> of words ( with genuine dupes ) and does two things simultaneuously
    //      builds a HM<String, Integer> frequency counter and seprately builds a Set<String> containing words that appear more than once
    // Populated by checking the frequency map as you go, not as a separate pass forward
    public static void Exc1(ArrayList<String> words) {
        HashMap<String, Integer> frequencyCounter = new HashMap<>();
        Set<String> dupes = new HashSet<>();

        for (String word: words) {
            if (frequencyCounter.containsKey(word)) { 
                frequencyCounter.put(word, frequencyCounter.get(word) + 1);
                if (frequencyCounter.get(word) > 1) { dupes.add(word);}
            } else { frequencyCounter.put(word, 1); }
        }
        System.out.println(frequencyCounter); System.out.println(dupes);
    }

    // Exercise 2 - Combining TreeMap, .merge() and List interface
    // Build a method typed to accept List<String> (interface, not ArrayList specifically)
    //      representing product names being sold throughout a day, some repeated across multiple sales
    // Using a TreeMap<String, Integer> and .merge (not manual if/containsKey branching this time)
    // Build a running sales-count map
    // Since its a TM, the final printed output should come back in guaranteed alphabeticl order by rpoduct name, regardless of the order sales actually occurred in - print and confirm
    public static void Exc2(List<String> items) {
        // .merge takes (k, v, rempapping function)
        TreeMap<String, Integer> occurrences = new TreeMap<>();
        for (String word: items) { occurrences.merge(word, 1, (currentCount, updatedCount) -> currentCount + updatedCount);}
        System.out.println(occurrences);
    }

    // Exercise 3 - COmbing LHM, Set and access-order eviction
    // Build a small unique visitor tracker
    //      an LHM<String, Integer> in access-order mode, tracking visitor names to visit counts
    // with a fixed max size acting as an LRU cache
    // Separately, maintain a Set<String> that accumulates every visitor ever evicted from the tracker - a perm rec of everyone whos fallen out of the currently active window with no dupes even if the same person gets evicted, re-added, evicted again later
    // Build one method, recordVisit(String name) that increments the visitors count (or adds them fresh at 1), triggers eviction if over capacity and adds any evicted name to the Set
    // Test with enough visits and enough distinct visitors to force multiple evictions, including at least one visitor who gets evicted then visits again later (re added fresh)
    //      confirming they still only appear once in the evicted-names Set despite potentially being evicted a second time
    static LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>(16, 0.75f, true);
    static Integer lhmMS = 4;
    static Set<String> evicted = new HashSet<>();
    public static void Exc3(String name) {
        // this line adds or updates to lhm
        lhm.merge(name, 1, (priorVisits, updatedVisits) -> priorVisits + updatedVisits);

        // if lhm size exceeds lhmMS then evict and add to set
        if (lhm.size() > lhmMS) {
            Map.Entry<String, Integer> rp = lhm.entrySet().iterator().next();
            lhm.remove(rp.getKey());
            evicted.add(rp.getKey());
        }
        System.out.println("LHM: " + lhm); System.out.println("Evicted: " + evicted);
    }

    // Exercise 4 - Combining HM with ArrayList vals, .computeIfAbsent() (new method) and Collections.max()
    // .computeIfAbsent(key, remappingFunction) explanation
    //      .computeIfAbsent(key, k -> new ArrayList<>()) checks if key exists, if not, it creates a value using hte lambda andinserts it
    //          either way, it returns the value now associated with that key, letting you chain .add directly onto the result
    //      functionally identical to manual if containskey else built by hand in the original HM values are lists exc
    // Collections.max(collection) - a Collections utility method
    //      Takes any Collection (a List, a Set, anything implementing that interface) and returns the largest element, using natural ordering - the utility class equic of Arrays.sort() for finding a single max without sorting the whole thing first
    // Build a method grouping a list of exam scores by letter grade ( same length- grouping shape from earlier but by grade bracket this time - A/B/C/D/F bases on score thresholds you define)
    //      using a HM<String, ArrayList<Integer>> populated via .computeIfAbsent this time, not manual branching
    // After grouping, use Collections.max() to find and print the single highest score within each grade bracket separately
    public static void Exc4(List<Integer> scores) {
        HashMap<String, ArrayList<Integer>> gradebook = new HashMap<>();

        for (Integer score: scores) {
            String letterGrade;
            if (score < 70) { letterGrade = "F"; } else if (score < 75) {
                letterGrade = "D";
            } else if (score < 80) { letterGrade = "C"; } else if (score < 90) {
                letterGrade = "B";
            } else { letterGrade = "A"; }
            gradebook.computeIfAbsent(letterGrade, k -> new ArrayList<>()).add(score);
        }
        gradebook.forEach((grade, scoreList) -> {
            System.out.println("Highest in " + grade + ": " + Collections.max(scoreList));
        });
    }

    // Exercise 5 - Combining TreeMap, Set and List.of
    // Build a method taht takes a List<String> of usernames attempting to register on some system
    // where usernamesmust be unique and are compared case-insensitivity (direclty building on the case of case-sensitivity that surfaced on Exercise 2)
    // Use a TreeMap<String, Boolean> to track each normalized (lowercased) username against whether it was successfully registered (true) or rejected as a dupe (false)
    // Separately, use a Set<String> built from List.of() - a small, fixed, immutable blocklist of forbidden usernames (like admin, root, sudo, test)
    // Reject any registration attempt matching the blocklist too, before even checking the for dupes
    public static void Exc5(List<String> usernames) {
        TreeMap<String, Boolean> validUsers = new TreeMap<>((String a, String b) -> { return a.toLowerCase().compareTo(b.toLowerCase());});
        Set<String> invalid = new HashSet<>(List.of("sudo", "test", "admin", "root"));

        for (String username : usernames) {
            if (invalid.contains(username)) { continue; } // using continue instead of break so it just skips this username and goes to the next one
            validUsers.put(username, true);
        }
        System.out.println(validUsers);
    }
    
    // Exercise 6 - Combining ArrayList, HM and Collections.frequency()
    // Collections.frequency(collection, element) 
    //      takes any Collection and a specific value, returns how many times that exact value appears in it
    //      Direct alternative to building a HM frequency counter by hand, when you only care about one specific vals count rather than every vals count at once
    // Build a method that takes an AL<Integer> of dice rolls (cals 1-6, with realistic repeats) and without manually looping use .frequency() 6 times
    //      once per possible face value to build a HM<Integer, Integer> mapping each face value to how many times it was rolled
    // Print the resulting map and confirm the 6 frequency counts sum to the orignals lists total size, proving nothing was miscounted or double counted
    public static void Exc6(ArrayList<Integer> rolls) {
        HashMap<Integer, Integer> frequencyCollector = new HashMap<>();
        Integer oneFreq = Collections.frequency(rolls, 1); Integer twoFreq = Collections.frequency(rolls, 2); Integer threeFreq = Collections.frequency(rolls, 3);
        Integer fourFreq = Collections.frequency(rolls, 4); Integer fiFreq = Collections.frequency(rolls, 5); Integer sixFreq = Collections.frequency(rolls, 6);
        frequencyCollector.put(1, oneFreq); frequencyCollector.put(2, twoFreq); frequencyCollector.put(3, threeFreq); frequencyCollector.put(4, fourFreq);
        frequencyCollector.put(5, fiFreq); frequencyCollector.put(6, sixFreq);
        Integer[] sum = {0};
        frequencyCollector.forEach((value, amount) -> {
            sum[0] += amount; System.out.println(sum[0]);
        });
        System.out.println(sum[0]);
    }
}
