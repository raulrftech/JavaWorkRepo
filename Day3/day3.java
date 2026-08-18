package Day3;
import java.util.ArrayList; import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays; import java.util.List;
import java.util.HashMap; import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map; import java.util.Optional;
import java.util.Objects;
import java.util.Set; import java.util.TreeMap;




public class day3 {
    public static void main(String[] args) {
        PackageMaker maker = new PackageMaker();

        PackageSender raul = PackageSender.createSender("Raul", "Rodriguez", 4931234);
        PackageReceiver alex = PackageReceiver.createReceiver("Alex", "Chavez", 4935678);
        PackageSender elena = PackageSender.createSender("Elena", "Castillo", 4939012);
        PackageReceiver martha = PackageReceiver.createReceiver("Martha", "Mack", 4933456);

        maker.acceptOrder("SHP001", "1A76B001XJ", "El Paso, Texas", "Fort Worth, Texas", 590.5, "Standard", raul, alex);
        maker.acceptOrder("SHP002", "1A76B001XK", "El Paso, Texas", "Austin, Texas", 575.0, "Express", elena, martha);
        maker.acceptOrder("SHP003", "1A76B001XL", "El Paso, Texas", "Houston, Texas", 745.2, "Standard", raul, martha);
        maker.acceptOrder("SHP004", "1A76B001XM", "El Paso, Texas", "Dallas, Texas", 600.8, "Express", elena, alex);

        // deliberate duplicate tag number, should be rejected
        maker.acceptOrder("SHP001", "1A76B001XN", "El Paso, Texas", "San Antonio, Texas", 550.0, "Standard", raul, alex);

        Warehouse warehouse = new Warehouse(maker);
        warehouse.requestOrders(3);
        warehouse.processOrders(2);

        System.out.println(raul.getSummaryOfPerson());

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

    // Exercise 7 - Combining LHM, TM and .entrySet()
    // Build a method that takes LHM<String, Integer> already populated in some specific insertion order (representing, say, a sequence of customer orders and their totals)
    //      and produces a second, brand new  TM<String, Integer> containing the exact same data but now sorted alphabetically by key
    // Do this by iterating the original via .entrySet() and inserting each pair into the new TM one at a time
    // Print both maps, rpving the LHM retains its orginal insertion order untocuhed while the newly built TM shows identical data in a different sorted order
    public static void Exc7(LinkedHashMap<String, Integer> lhm) {
        TreeMap<String, Integer> sorted = new TreeMap<>();
        for(Map.Entry<String, Integer> order: lhm.entrySet()) {
            sorted.put(order.getKey(), order.getValue());
        }
        System.out.println(lhm); System.out.println(sorted);
    }

    // Exercise 8 - Combining Set, List, Collections.unmodifiable()
    // Collections.unmodifiable(list) explained
    //      Takes any existing list and returns a read-only wrapper around it - unlike List.of() (which builds a genuinely independent immutable list from scratch)
    //       and Arrays.asList() (which allows l.set() but blocks resizing)
    //       Coll.umf(list)  wrapper blcoks all mutation attempts through the wrapper (.add, .remove, .set, everything throws)
    //          while the original list underneath remains fully mutable through its own ref - meaning changes to the original do still show up thorw the wrapper (same live view as subList()'s behavior)
    // Build a method that takes an AL<String> of tags (with genuine duplicates), builds a Set<String> of the unique tags, converts that set into a List<String> and wraps that resulint list with collections.umf
    // Prove 3 things directly; via try/catch, .add, .set, and actual proof of the live view behavior 
    public static void Exc8(ArrayList<String> tags) {
        Set<String> unique = new HashSet<>();
        for (String tag: tags) { unique.add(tag); } List<String> converted = new ArrayList<>(unique); List<String> view = Collections.unmodifiableList(converted);
        try { view.add("Hello"); } catch (Exception e) { System.out.println(e);} try { view.set(1, "eeee");} catch (Exception e) { System.out.println(e);}
        System.out.println(view); converted.remove(1); System.out.println(view);
    }

    // Exercise 9 - Combining HM, Set and .compute()
    // Build a small voting/poll tally system
    // An HM<String, Integer> tracks candidate names to vote counts
    // Separately, a Set<String> tracks which voters have already voted to prevent double voting
    // Build method castVote(String name, String CandidateName, HashMap<String, Integer> tally, Set<String> votedAlready) that
    //     first checks if voterName is already in votedAlready, rejects vote netirely if so and if not use .compute accordingly
    // Test with several distinct voters voting for a mix of candidates and at least one voter attempting to vote a second time for a different canditate than their first vote
    public static void Exc9(String voterName, String candidateName, HashMap<String, Integer> tally, Set<String> votedAlready) {
        if (votedAlready.add(voterName)) {
            tally.compute(candidateName, (k, v) -> {
                if (v == null) { return 1; }
                return v + 1;
            });
        } else { System.out.println(String.format("Rejecting %s for %s since they already voted", voterName, candidateName)); return; }
    }

    // Exercise 10 - Combining LHM, TM, Set, and Collections.max()
    // Build a small top scorers leaderboard system,, deliberately pulling together several concepts from acorss all ten exercises into one program
    // A LHM<String, Integer> tracks players and scores in the order they first played
    // Build a method recordScore(String player, int points) that adds a pleyer fresh at points if new or adds points onto their existing total if returning - same accumulation pattern
    // After a batch of scores has been recorded, build a second method  getLeaderboard() that converts the LHM into a TreeMap<String, Integer>
    //      separately finds the single highest score using collections.max() on the values (youll need .values() to get Collection<Integer> to pass in) and builds a Set<String>
    //          containing every player whos currently tied for that highest score - since more than one player could share the max
    static LinkedHashMap<String, Integer> playersScores = new LinkedHashMap<>(16, 0.75f, false);
    public static void recordPlayer(String name, int points) {
        playersScores.merge(name, points, (oldValue, newValue) -> oldValue + newValue);
    }
    public static void getLeaderboard(LinkedHashMap<String, Integer> returnValFrom) {
        TreeMap<String, Integer> tm = new TreeMap<>(playersScores);
        Integer maxScore = Collections.max(tm.values());
        Set<String> tiedPlayers = new HashSet<>();
        tm.forEach((name, score) -> {
            if (score.equals(maxScore)) { tiedPlayers.add(name);}
        });
        System.out.println(tiedPlayers);
    }


    // Below are 3 exercises that require everything that is covered throughout the entirety of this file
    // Exercise 1 - Full Toolkit Req'd
    // Build a small library checkout system
    // Track books (title and how many copies exist) and active checkouts (who currently has which book)
    // A book can only be checked out if a copy is abailable; once all copies are out, further checkout attempts must be rejected and recorded somewhere as "waitlisted"
    //      no dupes in the waitlist per book even if multiple people try and fail for the same title
    // When a book is returned, if anyone is waitlisted for it, automatically check it out to whoever's been waiting the longest
    // At any point, produce a report showing: every book alphabetically with its current availability, the single most-checked out book of all time
    //      (by total historical checkouts, not current)
    // and the complete waitlist for any book that has one
    // HashMap is unsorted
    // LHM keeps insertion order and can also track lru
    // Set declines duplicates
    // TM gives automatic sorting
    public static class LibraryBook implements Comparable<LibraryBook> {
        String name; String author; int copies;
        public LibraryBook(String name, String author, int copies) {
            this.name = name; this.author = author; this.copies = copies;
        }
        public LibraryBook(String name, String author) {
            this.name = name; this.author = author;
            this.copies = 5;
        }
        public final void changeCopies(int by) {
            this.copies += by;
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof LibraryBook)) { return false; }
            LibraryBook otherBook = (LibraryBook) other;
            return this.name.equals(otherBook.name) && this.author.equals(otherBook.author);
        }
        // hashCode() must be overridden alongside equlas(), never one without the other
        // Any two objs considered equal by equals() must produce the same hashCode() or HM/HashSet will silently misbehave (an object could be added but then never findable via get, contains)
        // since thsoe methods use hashCode first to even locate which bucket to look in
        // Objects.hash(name, author) - an utility method,  generates a combined hash from however many fields you pass it, correctly building a hash that stays consistent with whichever fiels equals actually compares
        @Override
        public int hashCode() {
            return Objects.hash(name, author);
        }
        @Override
        public int compareTo(LibraryBook other) {
            return this.name.compareTo(other.name);
        }
    }
    public static class MainLibrary {
        String name;
        Set<LibraryBook> rentableBooks = new HashSet<>();
        TreeMap<String, Integer> mostCheckedOut = new TreeMap<>();
        LinkedHashMap<BookRenter, Set<LibraryBook>> waitlist = new LinkedHashMap<>(16, 0.75f, true);
        TreeMap<BookRenter, Set<LibraryBook>> currentlyRenting = new TreeMap<>();
        List<BookRenter> toFulfill = new ArrayList<>();
        public MainLibrary(String name) {
            this.name = name;
        }

        public void addBook(LibraryBook book) {
            rentableBooks.add(book);
        }
        public void addBooks(Set<LibraryBook> books) {
            books.forEach(book -> {
                rentableBooks.add(book);
            });
        }
        public final Boolean processRequest(BookRenter renter, LibraryBook forBook) {
            // check the renters renting book
            if (renter.rentingBooks.contains(forBook)) { return false; } else {
                if (currentlyRenting.containsKey(renter) && currentlyRenting.get(renter).contains(forBook)) { return false; }

                if (forBook.copies >= 1) {
                    mostCheckedOut.merge(forBook.name, 1, (oldCount, newCount) -> oldCount + newCount);
                    //  this goes into currentlyRenting the renter is the key and their rented books is the value
                    forBook.changeCopies(-1); renter.addBook(forBook);
                    currentlyRenting.put(renter, renter.rentingBooks);
                    System.out.println(String.format("%s is now renting %s", renter.firstName, forBook.name));
                    return true;
                } else {
                    waitlist.computeIfAbsent(renter, k -> new HashSet<>()).add(forBook);
                    return false;
                }
                
            }
        }
        public final void processReturn(BookRenter from, LibraryBook forBook) {
            // check renter has book
            if (from.returnBook(forBook)) {
                // check if library has book but since im only using one instance of a library to keep things simple well just go ahead with the return
                forBook.changeCopies(1);
            }

            for (Map.Entry<BookRenter, Set<LibraryBook>> pair : waitlist.entrySet()) {
                if (pair.getValue().contains(forBook)) {
                    toFulfill.add(pair.getKey());
                }
            }
            if (!toFulfill.isEmpty()) {
                BookRenter longestWaiting = toFulfill.get(0);
                boolean success = this.processRequest(longestWaiting, forBook);
                if (success) { waitlist.get(longestWaiting).remove(forBook);}
            }
        }
        public final void getLibraryReport() {
            int checkedOutTheMost = Collections.max(mostCheckedOut.values());
            Set<String> mostSoughtBooks = new HashSet<>();
            for (Map.Entry<String, Integer> pair : mostCheckedOut.entrySet()) {
                if (pair.getValue() == checkedOutTheMost) { mostSoughtBooks.add(pair.getKey());} else { continue; }
            }
            System.out.println(String.format("LISTED BOOKS TO RENT FROM %s", name));
            rentableBooks.forEach((book) -> {
                System.out.println(String.format("BOOK: %s has %d copies available", book.name, book.copies));
            });
            System.out.println(String.format("MOST CHECKED OUT: %n%s", mostCheckedOut));
            mostSoughtBooks.forEach((name) -> System.out.println(name));
            System.out.println("CURRENTLY RENTING");
            currentlyRenting.forEach((renter, set) -> {
                System.out.println(String.format("Current Rentals of %s", renter.firstName));
                set.forEach((book) -> { System.out.println(book.name); });
            });
            waitlist.forEach((renter, wanting) -> {
                System.out.println(String.format("%s is currently wanting the following book(s)", renter.firstName));
                wanting.forEach((book) -> System.out.println(String.format("%s with %d copies available", book.name, book.copies)));
            });
            rentableBooks.forEach(book -> {
                System.out.println(String.format("UPDATED INVENTORY:%nBOOK: %s has %d copies available", book.name, book.copies));
            });
        }

    }
    public static class BookRenter implements Comparable<BookRenter> {
        String firstName; String lastName;
        Set<LibraryBook> rentingBooks = new HashSet<>();
        MainLibrary rentingFrom;
        public BookRenter(String firstName, String lastName, MainLibrary rentingFrom) {
            this.firstName = firstName; this.lastName = lastName; this.rentingFrom = rentingFrom;
        }
        public final void submitRequest(LibraryBook forBook) {
            if (rentingBooks.contains(forBook) ) { return; } else {
                if (rentingFrom.processRequest(this, forBook)) {
                    System.out.println(String.format("%s successfully rented %s", this.firstName, forBook.name));
                }
            }
        }
        private final void addBook(LibraryBook book) { rentingBooks.add(book);}
        private final Boolean returnBook(LibraryBook book) {
            if (rentingBooks.contains(book) == false) {
                System.out.println(String.format("%s cannot return %s since he/she is not currently renting this book", this.firstName, book.name));
                return false;
            } else { rentingBooks.remove(book); return true;}
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof BookRenter)) { return false; }
            BookRenter otherRenter = (BookRenter) other;
            return this.firstName.equals(otherRenter.firstName) && this.lastName.equals(otherRenter.lastName);
        }
        @Override
        public int hashCode() { return Objects.hash(firstName, lastName); }
        @Override
        public int compareTo(BookRenter other) {
            int lastNameCompare = this.lastName.compareTo(other.lastName);
            if (lastNameCompare != 0) { return lastNameCompare;}
            return this.firstName.compareTo(other.firstName);
        }
    }
    
    // Exercise 2
    // Build a small event-scheduling conflict detector
    // Track rooms (name, capacity) and bookings (which roo,, which organizer, a start and end time, may be represented by integers)
    // A booking request must be rejected if it would overlap with an already-confirmed booking in the same room
    //      meaning you need real overlap-detection logic, not just an exact-match check
    // Track, per room, every organizer whos ever successfully booked it, with no dupes
    // Separately, track the single most booked room across the whole system (by total successful booking count)
    // Produce a report showing, alphabetically by room name: the room's capacity, every confrimed booking in chronological order by start time
    // And the complete list of unique organizers whove ever used that room
    public static boolean doesOverlap(int existingStart, int existingEnd, int newStart, int newEnd) {
        // in order to check that these two meets overlap, the end time of one has to be somewhere in between the second meeting
        return newStart < existingEnd && existingStart < newEnd;
    }
    public static class MeetingBooker {
        Set<Organizer> organizers = new HashSet<>();
        Set<MeetingRoom> rooms = new HashSet<>();
        Set<Booking> bookings = new HashSet<>();
        TreeMap<MeetingRoom, Integer> roomDemand = new TreeMap<>();
        public MeetingBooker() {}
        
        public final void addRooms(ArrayList<MeetingRoom> rooms) { rooms.forEach((room) -> rooms.add(room)); }
        public final void addOrganizers(ArrayList<Organizer> organizers) { organizers.forEach((organizer) -> organizers.add(organizer)); }

        public final void processRequest(Organizer organizer, Booking forBooking) {
            organizers.add(organizer); rooms.add(forBooking.room); bookings.add(forBooking);
            roomDemand.compute(forBooking.room, (k, v) -> {
                if (v == null) { return 1;} else { return v + 1;}
            });

        }
        // How Do You Get list of unique organizers per room
        // Rooms have LHM of bookings
        //      bookings have their designated organizer
        public final void returnSummary() {
            Set<String> tiedRooms = new HashSet<>();
            int mostBooked = Collections.max(roomDemand.values());
            roomDemand.forEach((room, bookingCount) -> {
                if (bookingCount.equals(mostBooked)) { tiedRooms.add(room.name); }
            });
            System.out.println(String.format("HIGHEST BOOKED ROOM(S) OF %d BOOKINGS:%n%s", mostBooked, tiedRooms));
            rooms.forEach((room) -> room.getSummary());
        }
        
    }
    public static class MeetingRoom implements Comparable<MeetingRoom> {
        String name; int capacity;
        LinkedHashMap<Booking, String> pastBookings = new LinkedHashMap<>(16, 0.75f, false);
        public MeetingRoom(String name, int capacity) {
            this.name = name; this.capacity = capacity;
        }
        public final void addBooking(Booking booking, Organizer organizer) {
            if (pastBookings.containsKey(booking)) { return; } else {
                pastBookings.put(booking, organizer.name);
            }
        }
        public final void getSummary() {
            pastBookings.forEach((booking, organizerName) -> {
                System.out.println(String.format("Organized By %s%nStarted At: %d%nEnded At: %d%nAssigned Room: %s", organizerName, booking.startTime, booking.endTime, booking.room.name));
            });
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof MeetingRoom)) { return false; }
            MeetingRoom otherRoom = (MeetingRoom) other;
            return this.name.equals(otherRoom.name);
        }
        @Override
        public int hashCode() { return Objects.hash(name); }
        @Override
        public int compareTo(MeetingRoom other) {
            int roomName = this.name.compareTo(other.name);
            if (roomName != 0) { return roomName;}
            return this.name.compareTo(other.name);
        }
    }
    public static class Organizer {
        String name; MeetingBooker bookingSystem;
        Set<MeetingRoom> pastMeetings = new HashSet<>();
        public Organizer(String name, MeetingBooker bookingSystem) {
            this.name = name; this.bookingSystem = bookingSystem;
        }
        public final void submitRequest(MeetingRoom room, int startTime, int endTime) {
            Booking desiredBooking = new Booking(this, room, startTime, endTime);
            boolean processable = true;
            for (Booking booking: room.pastBookings.keySet()) {
                if (doesOverlap(booking.startTime, booking.endTime, desiredBooking.startTime, desiredBooking.endTime)) { processable = false;} 
            }
            if (processable) {
                room.addBooking(desiredBooking, this);
                pastMeetings.add(room);
                bookingSystem.processRequest(this, desiredBooking);
            } else { System.out.println(String.format("Unable to complete booking request for %s at %s in the %S room", this.name, desiredBooking.startTime, room.name));}
        }
    }
    public static class Booking {
        Organizer organizer;
        MeetingRoom room;
        int startTime;
        int endTime;
        public Booking(Organizer organizer, MeetingRoom room, int startTime, int endTime) {
            this.organizer = organizer; this.room = room;
            this.startTime = startTime; this.endTime = endTime;
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Booking)) { return false;}
            Booking otherBooking = (Booking) other;
            return this.startTime == otherBooking.startTime && this.endTime == otherBooking.endTime;
        }
        @Override
        public int hashCode() { return Objects.hash(startTime, endTime);}
    }

    // Exercise 3
    // Build a small ride-share matching system
    // Track drivers(name, vehicle capacity) and riders(name, grou size - how many people need a seat)
    // A ride request can only be matched to a driver whose remaining capacity can fit the riders group size
    //      if no driver currently has room, the rider goes onto a waitlist, no dupes even if they somewhow request while already waiting
    // When a driver completes a trip and drops off their current riders (freeing up their full capacity again)
    //      automatically check the waitlist and fill their newly-freed seats with as many waiting riders as will fit, in the order theyve been waiting
    //          first come first serve
    // Track, per driver, the complete historical list of every rider theyve ever transported (allowing repeats - the same rider could ride with the same driver multiple times and each occurrence shoudl count)
    // Separately track the single most frequently paired rider-drive combination across the whole system
    // Produce a report:
    //      Every driver alphabetically by name, their current remaining capacity, everyone currently in their car and their full historical rider list
    //      Then the single most paired rider driver combination and how many times theyve ridden together
    public static boolean confirmAN(String accountNumber) {
        if (accountNumber == null || accountNumber.length() != 10) { return false; }
        int upperCount = 0; int lowerCount = 0; int digitCount = 0; int digitSum = 0;
        for (int i =0; i < accountNumber.length(); i++) {
            char c = accountNumber.charAt(i);
            if (Character.isUpperCase(c)) { upperCount++;} else if (Character.isLowerCase(c)) { lowerCount++; } else if (Character.isDigit(c)) {
                digitCount++;
                // Convert char digit to its numeric value and add to sum
                digitSum += Character.getNumericValue(c);
            } else {
                return false;
            }
        }
        return upperCount == 3 && lowerCount == 4 && digitCount == 3 && digitSum == 25;
    }
    public static class RideShareHandling {
        String name;
        Set<Driver> drivers = new HashSet<>(); // set to contain drivers that contain their ride history 
        Set<Rider> riders = new HashSet<>();
        LinkedHashMap<Rider, Integer> waitlist = new LinkedHashMap<>(16, 0.75f, true); // rider and its requested occuapancy

        public RideShareHandling(String name) { this.name = name; }
        public final void addDriver(Driver driver) { drivers.add(driver);}

        public final RideResult processRequest(Rider forRider, int numberOfRiders) {
            Optional<Driver> assignableDriver = drivers.stream().filter(driver -> driver.acceptableCapacity >= numberOfRiders).findFirst();
            // Handle the result safely (similar to Swift's if let)
            if (assignableDriver.isPresent()) {
                Driver driver = assignableDriver.get();
                if (waitlist.get(forRider) != null) { waitlist.remove(forRider); }
                driver.acceptableCapacity -= numberOfRiders;
                return new RideResult(true, driver);
            } else {
                waitlist.put(forRider, numberOfRiders); // this avoids dupes
                return new RideResult(false, null);
            }
        }

        public final void handleWaitlist() {
            Map.Entry<Rider, Integer> oldestWaiting = waitlist.entrySet().iterator().next();
            oldestWaiting.getKey().submitRequest(oldestWaiting.getValue());
        }   

        public static class RideResult {
            public final boolean success; public final Driver driver;
            public RideResult(boolean success, Driver driver) { this.success = success; this.driver = driver; }
        }
    }
    public static class Driver implements Comparable<Driver> {
        String firstName; String lastName; String accountNumber; RideShareHandling rideShareApp; int acceptableCapacity;
        Set<Rider> riders = new HashSet<>();
        TreeMap<Rider, Integer> occurrences = new TreeMap<>();

        public Driver(String firstName, String lastName, String accountNumber, RideShareHandling rideShareApp, int acceptableCapacity) {
            this.firstName = firstName; this.lastName = lastName;
            this.accountNumber = accountNumber; this.rideShareApp = rideShareApp;
            this.acceptableCapacity = acceptableCapacity;
        }

        public final void receiveRequest(Rider forRider, Integer forCapacity) {
            riders.add(forRider); this.acceptableCapacity -= forCapacity;
            occurrences.merge(forRider, 1, (oV, nV) -> oV + 1);
        }

        public final void finishRide(Integer forCapacity) { this.acceptableCapacity += forCapacity;}

        public final void returnOccurences() {
            occurrences.forEach((rider, times) -> {
                System.out.println(String.format("%s has ridden with %s %d times", rider.firstName, this.firstName, times));
            });
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Driver)) { return false; }
            Driver otherDriver = (Driver) other;
            return this.accountNumber.equals(otherDriver.accountNumber);
        }
        @Override
        public int hashCode() { return Objects.hash(accountNumber);}
        @Override
        public int compareTo(Driver other) {
            int compareVal = this.accountNumber.compareTo(other.accountNumber);
            if (compareVal != 0) { return compareVal;}
            return this.accountNumber.compareTo(other.accountNumber);
        }
    }
    public static class Rider implements Comparable<Rider> {
        String firstName; String lastName; int age; String accountNumber; RideShareHandling rideShareApp;
        Set<Driver> driversEncountered = new HashSet<>();

        public Rider(String firstName, String lastName, int age, String accountNumber, RideShareHandling rideShareApp) {
            this.firstName = firstName; this.lastName = lastName;
            this.age = age; this.accountNumber = accountNumber;
            this.rideShareApp = rideShareApp;
        }

        // a rider submits request to the RSH and then it finds an available driver then pairs those two
        public final void submitRequest(int forCapacity) {
            // since this is quite basic im going to keep it basic and not include anything considering destination/location or cost
            // check accountNumber
            if (confirmAN(this.accountNumber)) {
                RideShareHandling.RideResult requestStatus = rideShareApp.processRequest(this, forCapacity);
                if (requestStatus.success == true) {
                    driversEncountered.add(requestStatus.driver);
                    System.out.println(String.format("Your ride request was succesfully fulfilled%nYour driver is %s", requestStatus.driver.firstName));
                } else { System.out.println(String.format("Dear %s, your request for a total of %d riders was not accepted at this time. You've been added to the waitlist", this.firstName, forCapacity)); }
            } else {
                System.out.println(String.format("Your account number %s does not sufffice the requirements. Fix before requesting a ride.", this.accountNumber));
            }

        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Rider)) { return false; }
            Rider otherRider = (Rider) other;
            return this.accountNumber.equals(otherRider.accountNumber);
        }
        @Override
        public int hashCode() { return Objects.hash(accountNumber);}
        @Override
        public int compareTo(Rider other) {
            int compareVal = this.accountNumber.compareTo(other.accountNumber);
            if (compareVal != 0) { return compareVal;}
            return this.accountNumber.compareTo(other.accountNumber);
        }
    }


    // Refresher 1/8 -- Designated vs Convenience Initializers
    // A designated initializer does the real work - every stored property gets a value, and if the class has a superclass, it calls super.init() as part of that responsibility. Every class needs at least one
    // A convenience init, marked with the convenience keywork, provides a shortcut with some values pre-filled, but is req'd to delegate to another intiializer on the same class via self.init, never super.init directly
    // This is the exact same relationship as Java's constructor overloading via this(), one constructor doing the real work, a second one delegating to it with defaults filled in
    // Quick Question: Why can a convenience init nver call super.init*) directly but only self.init
    // Anser to Question: A convenience init is provided defaults to its own constructor, thus less args are given to user whenever initializing a new instance of that class
    //                    Since super.init refers to a parent's classes initis, child class might have props that arent a part of the super class overall its required to delegate
    //                    to the childs class full constructor. the full constructor has the responsibility to call super.init. so basics: "this" isn fully put together then calls super.init
    // Actual Answer: a designated init is the one responsible for guaranteeing every one of its own class's stored props get a value, and then handing off to super.init to guarantee
    //                the inherited props get set too
    //                A convenience initializer is explicitly not trusted with that full responsibility; it exists purely to provide a shortcut
    //                If convenience init were allowed to call super.init directly, it would be bypassing the designated init entirely - meaning the class's own guarantee (everyone of my stored props always gets set, no exceptions)
    //                could be silently skipped, since nothing would force the convenience path to actually touch every prop the designated init otherwise ensures
    //                The rule is that every path into an obj's initialization must pass through a designated init at some point and super.init is specifically the desig init job to call
    // Base class: Membership, four stored props, one designated init requiring all four
    // Two convenience inits; one representing sign up for the cheapest possible tier the other representing "corporate/gifted membership"
    // Subclass: FamilyMembership extends Membership with new prop
    // Its own designated init takes all five vals calling super.init with the first four then setting its respective prop
    // Its own convenience init takes only two, defaulting the rest and delegating via self.init to FamilyMemberships own designated init
    // Biild all four initializer paths, create one instance through eac, print something confirming every property landed correctly on each instance
    public static class Membership {
        String memberName; String membershipTier; Double monthlyFee; boolean autoRenew;

        public Membership(String memberName, String membershipTier, Double monthlyFee, boolean autoRenew) {
            this.memberName = memberName; this.membershipTier = membershipTier;
            this.monthlyFee = monthlyFee; this.autoRenew = autoRenew;
        }
        public Membership(String memberName, boolean autoRenew) {
            this(memberName, "Plus", 25.00, autoRenew);
        }
        public Membership(String memberName, String membershipTier, Double monthlyFee) {
            this(memberName, membershipTier, monthlyFee, false);
        }
        public Membership(String memberName) {
            this(memberName, "Basic", 10.00, true);
        }

        public void getSummary() {
            String tierOf = membershipTier != null ? membershipTier : "";
            String hasSub = monthlyFee != null ? String.format("%.2f", monthlyFee) : "0.00";
            System.out.println(String.format("MEMBER SUMMARY%nNAME: %s%n%nTIER: %s%nMonthly Charge: $%s%nAutoPay: %b", memberName, tierOf, hasSub, autoRenew));
        }
    }
    public static class FamilyMembership extends Membership {
        int amountOfMembers;
        public FamilyMembership(String memberName, String membershipTier, Double monthlyFee, boolean autoRenew, int amountOfMembers) {
            super(memberName, membershipTier, monthlyFee, autoRenew); this.amountOfMembers = amountOfMembers;
        }
        public final void getTotalCharge() {
            System.out.println(String.format("%.2f", amountOfMembers * monthlyFee));
        }
        @Override
        public final void getSummary() {
            String tierOf = membershipTier != null ? membershipTier : "";
            String hasSub = monthlyFee != null ? String.format("%.2f", monthlyFee * amountOfMembers) : "0.00";
            System.out.println(String.format("MEMBER SUMMARY%nNAME: %s%n%nTIER: %s%nMonthly Charge: $%s%nAutoPay: %b", memberName, tierOf, hasSub, autoRenew));
        }
    }

    // Exercise 2 - Static Methods and Fields
    // A static member belongs to the class itself, not any individual instance - one shared copy exists regardless of how many objs created and its accessible without instantiating
    // A non-static member reqs an actual instance to exist first,s ince it belongs to that specific objs own memory
    // Rule: a static method cannot directly access non-static methods or fields because a static context has no this
    //       The reverse is fine; a non-static instance method can freely access static fields/methods, since an instance always has access to whatevers shared at the class level
    // Static Factory Methods;
    //      a static method that constructs and returns an instance, giving you room to do things a plain constructor cant (validate and return null, mutate external objs, choose which subclass to build)
    // Build a class resembling a ticket booth selling event tickets
    public static class TicketManager {
        static LinkedHashMap<String, Double> ticketsSold = new LinkedHashMap<>(16, 0.75f, false);
        static TreeMap<String, Integer> eventsVisited = new TreeMap<>();
        static Integer totalTicketsSold = 0;

        String eventName; double ticketPrice; String assignedSeat;
        public TicketManager(String eventName, double ticketPrice, String assignedSeat) {
            this.eventName = eventName; this.ticketPrice = ticketPrice; this.assignedSeat = assignedSeat;
        }
        public static TicketManager makeTicket(String eventName, double ticketPrice, String assignedSeat) {
            if (!ticketsSold.containsKey(assignedSeat)) {
                if (eventName.length() < 5) { return null; }
                if (ticketPrice < 15.00) { return null; }
                ticketsSold.put(assignedSeat, ticketPrice);
                eventsVisited.merge(eventName, 1, (o, n) -> o + n);
                totalTicketsSold += 1;
                return new TicketManager(eventName, ticketPrice, assignedSeat);
            } else { return null; }
            
        }
        public static void getTicketsSold() { System.out.println(String.format("A total of %d tickets have been sold.", totalTicketsSold)); }
        public static double getRevenue() {
            double total = 0.00;
            for (Map.Entry<String, Double> entry: ticketsSold.entrySet()) {
                total += entry.getValue();
            }
            return total;
        }
        public static void getSummary() {
            eventsVisited.forEach((place, visits) -> {
                System.out.println(String.format("%s was visited %d times", place, visits));
            });
        }
    }

    // Exercise 3 - Interfaces, basic conformance witha. default method
    // An interface declares a contract - method signatures any conforming class must implement, with no state of its own (no fields)
    // A class conformas via implements
    //      Must provide a real body for every req'd method or it wont compule
    // Can also include default methods - a method with an actual body written directly inside the interface, which is then inherited or otherwise overrridden
    // Build a payment proessor - different payment types (card, cash, digital wallet) all conforming to one shared interface
    interface Processable {
        boolean processPayment(double amount);
        boolean processDeposit(double amount);
        void returnAllSummaries();
        default void attemptPayment(double amount) {
            boolean success = processPayment(amount);
            System.out.println(success ? "Transaction approved." : "Transaction declined");
        }
    }
    public static class DebitCard implements Processable {
        String owningCompany; String ch_firstName; String ch_lastName;
        String cardNumber; String expiration; int cvc; double balance; boolean overdraft_enabled;
        static Set<String> addedCards_CNs = new HashSet<>();
        static TreeMap<String, Integer> cardCompanies = new TreeMap<>();
        static HashMap<String, Integer> cardHolders = new HashMap<>();

        public DebitCard(String owningCompany, String ch_firstName, String ch_lastName, String cardNumber, String expiration, int cvc, double balance, boolean overdraft_enabled) {
            this.owningCompany = owningCompany; this.ch_firstName = ch_firstName;
            this.ch_lastName = ch_lastName; this.cardNumber = cardNumber; this.expiration = expiration; 
            this.cvc = cvc; this.balance = balance; this.overdraft_enabled = overdraft_enabled;
        }
        private static boolean confirmCN(String cardNumber) {
            if (cardNumber == null || !cardNumber.matches("\\d{16}")) { return false; }
            int sum = 0; boolean alternate = false;
            for (int i = cardNumber.length() - 1; i >= 0; i--) {
                int n = Integer.parseInt(cardNumber.substring(i, i + 1));

                if (alternate) {
                    n *= 2; if (n > 9) { n = (n % 10) + 1; }
                }
                sum += n; alternate = !alternate;
            }
            return (sum % 10 == 0);
        }
        public static DebitCard createCard(String owningCompany, String ch_firstName, String ch_lastName, String cardNumber, String expiration, int cvc, double balance, boolean overdraft_enabled) {
            if (addedCards_CNs.contains(cardNumber)) { System.out.println("This card has already been added. Not adding it again."); return null; }
            if (expiration == null || expiration.length() != 5) { System.out.println("There was an issue with the expiration date."); return null; }
            if (expiration.charAt(2) != '/') { System.out.println("The expiration date was formatted incorrectly. Make sure to separate month and year with / or -"); return null; }
            if (!confirmCN(cardNumber)) { System.out.println("The card number is incorrect. Recheck"); return null; }

            try {
                String monthStr = expiration.substring(0, 2);
                String yearStr = expiration.substring(3, 5);
                int month = Integer.parseInt(monthStr); int year = Integer.parseInt(yearStr);

                if (month < 1 || month > 12) { return null; }
                if (year < 0 || year > 99) { return null; }

            } catch (NumberFormatException e) { return null; }
            String interpolatedNames = String.format("%s %s", ch_firstName, ch_lastName);
            String lastFourOf = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
            cardHolders.put(interpolatedNames, Integer.parseInt(lastFourOf));

            cardCompanies.merge(owningCompany, 1, (o, n) -> 0 +n);
            addedCards_CNs.add(cardNumber);
            return new DebitCard(owningCompany, ch_firstName, ch_lastName, cardNumber, expiration, cvc, balance, overdraft_enabled);
        }

        public void returnAllSummaries() {
            System.out.println("Card Holders");
            cardHolders.forEach((owner, lastFour) -> System.out.println(String.format("CardHolder: %s%n    Last 4: %d", owner, lastFour)));
            System.out.println("Card Companies Used");
            cardCompanies.forEach((company, usedBy) -> System.out.println(String.format("%s stands for %d people", company, usedBy)));
        }

        @Override
        public boolean processPayment(double amount) {
            double remainingBalance = balance - amount;
            String lastFour = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
            if (remainingBalance <= 0.00) {
                if (overdraft_enabled) {
                    this.balance = remainingBalance;
                    return true;
                } else {
                    System.out.println(String.format("The payment in the amount of $%.2f was unsuccessful for the card ending in %s. Current Balance: $%.2f", amount, lastFour, balance));
                    return false;
                }
            } else {
                this.balance = remainingBalance;
                System.out.println(String.format("The payment in the amount of $%.2f was successful for the card ending in %s. Current Balance: $%.2f", amount, lastFour, balance));
                return true;
            }
        }

        @Override
        public boolean processDeposit(double amount) {
            this.balance += amount;
            System.out.println(String.format("Deposited $%.2f. New Balance is $%.2f", amount, balance));
            return true;
        }

    }
    public static class BankNote implements Processable {
        int value; String serialNumber; String identifier;
        boolean hasFSSeal; String president;
        static int netWorth = 0;
        static TreeMap<String, Integer> serializedBills = new TreeMap<>();
        static TreeMap<String, Integer> presidentsEncountered = new TreeMap<>();

        public BankNote(int value, String serialNumber, String identifier, boolean hasFSSeal, String president) {
            this.value = value; this.serialNumber = serialNumber; this.identifier = identifier; this.hasFSSeal = hasFSSeal; this.president = president;
        }
        private static boolean checkPresident(String name, int value) {
            switch (name) {
                case "Washington": return value == 1; case "Lincoln": return value == 5;
                case "Hamilton": return value == 10;  case "Jackson": return value == 20;
                case "Grant": return value == 50;    case "Franklin": return value == 100;
                default: return false;
            }
        }
        
        public static BankNote createBankNote(int value, String serialNumber, String identifier, boolean hasFSSeal, String president) {
            if (serializedBills.containsKey(serialNumber)) { return null; }
            if (!hasFSSeal) { return null; }
            if (checkPresident(president, value)) {
                if(serialNumber == null || serialNumber.length() != 8) { return null; }

                int letterSum = 0; int numberSum = 0;
                for (int i = 0; i < serialNumber.length(); i++) {
                    char c = serialNumber.charAt(i);

                    if (Character.isDigit(c)) { numberSum++;} else if (Character.isLetter(c)) { letterSum++;} else { return null;}
                }
                if (letterSum != 2 && numberSum != 8) { return null; }
                if (identifier.length() != 2) { return null; }

                netWorth += value;
                serializedBills.put(serialNumber, value); presidentsEncountered.merge(president, 1, (o, n) -> o + n);
                return new BankNote(value, serialNumber, identifier, hasFSSeal, president);
            } else { return null; }
        }
        
        @Override
        public void returnAllSummaries() {
            System.out.println(String.format("NET WORTH OF BANK: %d", netWorth));
            System.out.println("Serialized Bills with Associated Value");
            serializedBills.forEach((sn, val) -> System.out.println(String.format("SERIAL NUMBER: %s, VALUE: $%d", sn, val))); // this is safe because no >= two bills have same sn
            System.out.println("Presidents Encountered");
            for (Map.Entry<String, Integer> entry: presidentsEncountered.entrySet()) {
                System.out.println(String.format("%s was encountered %d times", entry.getKey(), entry.getValue()));
            }
        }
        @Override
        public boolean processPayment(double amount) {
            // just keeping this rather simple for sake of the exc
            if (value < amount) { System.out.println(String.format("Cannot process payment of $%.2f. Only less than or equal to $%d is allowed", amount, value)); return false;} else {
                System.out.println("Processed payment"); return true;
            }
        }

        @Override
        public boolean processDeposit(double amount) {
            System.out.println(String.format("Processed deposit of %.2f", amount));
            return true;
        }
    }
    public static class DigitalWallet implements Processable {
        String walletAddress; double balance;
        static double totalNW = 0.00; static LinkedHashMap<String, Double> madeAndAccessed = new LinkedHashMap<>(16, 0.75f, true);

        public DigitalWallet(String walletAddress, double balance) { this.walletAddress = walletAddress; this.balance = balance;}
        public static DigitalWallet makeWallet(String walletAddress, double balance) {
            if (madeAndAccessed.containsKey(walletAddress)) { return null; }
            if (balance < 0) { return null; }
            int letterCount = 0; int numberCount = 0;
            for (int i = 0; i < walletAddress.length(); i++) {
                char c = walletAddress.charAt(i);
                if (Character.isDigit(c)) { numberCount++;} if (Character.isLetter(c)) { letterCount++;}
            }
            if (letterCount != 8 && numberCount != 11) { return null;}

            totalNW += balance; madeAndAccessed.put(walletAddress, balance);
            return new DigitalWallet(walletAddress, balance);
        }

        @Override
        public boolean processPayment(double amount) {
            if (amount > balance) { return false;} else {
                double remainingBalance = balance - amount;
                System.out.println(String.format("Processed Payment of $%.2f. Remamining Balance: $%.2f", amount, remainingBalance));
                return true;
            }
        }

        @Override
        public boolean processDeposit(double amount) {
            System.out.println(String.format("Processed deposit of $%.2f. New balance: $%.2f", amount, (amount + balance)));
            return true;
        }

        @Override
        public void returnAllSummaries() {
            System.out.println(String.format("Total Net Worth: $%.2f", totalNW));
            madeAndAccessed.forEach((s, d) -> System.out.println(String.format("Address: %s (value: $%.2f)", s, d)));
        }
    }

    // Refresher 4 - Interfaces, Multiple Conformance with a Genuine Default Method Conflict
    // When a class implements two interfaces and both happen to declare a default method with an identical name and signature
    //      Java forces you to resolve the ambiguity explicitly - it will not the class compule without you deciding which behavior wins, or building a combined one
    // The resolutuion sysntax is InterfaceName.super.methodname(), callable only from isnide the class doing the overriding, never from the outside
    // Sample: A.super.greet()
    // The domain: 
    //      a media file thats both Playable and Exportable - two separate capability interfaces
    //                                                        both providing a default method with the same name
    // Build the two interfaces, each with a default method sharing a name and signature, one concrete class implementing both and resolving the confict
    // Prove the resolution works correctly whenever called
    interface Playable {
        default void preview(double forDuration) {
            System.out.println(String.format("Playing the first %.2f minutes of the video", forDuration));
        }
    }
    interface Exportable {
        default void preview(double forDuration) {
            double sampleVideo = forDuration * 0.60;
            System.out.println(String.format("Exporting now. Here's the current amount uploaded %.2f", sampleVideo));
        }
    }
    public static class HDVideo implements Playable, Exportable {
        String name; double duration; String format;
        public HDVideo(String name, double duration, String format) {
            this.name = name; this.duration = duration; this.format = format;
        }
        public static HDVideo makeVideo(String name, double duration, String format) {
            if (name == null || format == null) { System.out.println("Either the name is invalid or the format is invalid. Please fix"); return null; }
            return new HDVideo(name, duration, format);
        }
        @Override
        public void preview(double forDuration) {
            Playable.super.preview(forDuration);
            Exportable.super.preview(forDuration);
        }
    }

    // Refresher 5 - Abstract Classes, One Abstract Method
    // An abstract calss can never be instantiated directly - only extended (inherited from)
    // An abstract method has no body at all, just a signature ending in a ;
    //      forcing every concrete subclass to supply its own implementation
    // A non-abstract method on the same abstract class is fully implemented and can call the abstract method internally
    //      trusting whatever subclass eventually exists to have supplied real behavior
    // An abstract class can have its own properties but
    //      An abstract class can hold real stored fields, whatever real data the shared concept genuinely needs
    // The domain: a shape hierarchy where every shape computes its own area, formula differing per shape
    // Build an abstract class, at least two concrete subclasses with genuinely different area formulas, and prove the shared printArea() style method correctly dispatches to each subclass's own implementation
    static abstract class Shape {
        String color; int sides;
        public Shape(String color, int sides) { this.color = color; this.sides = sides; }
        abstract double calculateArea();
    }
    static public class Square extends Shape {
        double sideLength;
        public Square(String color, double sideLength) {
            super(color, 4); this.sideLength = sideLength;
        }
        @Override
        public double calculateArea() {
            return Math.pow(sideLength, 2);
        }
    }
    static public class Octagon extends Shape {
        double sideLength;
        public Octagon(String color, double sideLength) {
            super(color, 8); this.sideLength = sideLength;
        }
        @Override
        public double calculateArea() {
            return 2 * (1 + Math.sqrt(2)) * Math.pow(sideLength, 2);
        }
    }

    // Refresher 6 - Abstract Classes, Multiple Abstract Methods and a Mid-Chain Abstraction
    // An abstract class can require more than one abstract method, forcing every subclass to independently satisfy each one
    // An abstract class can sit in the middle of an inheritance chain, extending a concrete class below it while still being abstract itself
    //      meaning the "cannot instantiate directly" rule applies to it even though its own parent is perfectly instantiable
    //          the abstract keyword on a class is what blocks instantiation, not something inherited or dependent on the parents nature
    // The domain: a vehicle rental system - a concrete base class, an abstract tier above it with two abs methods, and concrete subclasses satisfying both independently
    // Build a concrete base class, an abs class extending it with two different abs methods, at least two concrete subclassess of that abs class, each satisfying both abs methods
    // Prove the abstract tier itself cannot be instantiated directly, even though its own parent class can be
    public static class Vehicle {
        String make; String model; String vin; double mpg;

        public Vehicle(String make, String model, String vin, double mpg) {
            this.make = make; this.model = model; this.vin = vin; this.mpg = mpg;
        }
        public static Vehicle makeVehicle(String make, String model, String vin, double mpg) {
            if (make == null || model == null || vin == null) { return null; }
            if (mpg < 6.0) { return null; }

            return new Vehicle(make, model, vin, mpg);
        }
    }
    static abstract class Operable extends Vehicle {
        boolean qualifiedEmissions;
        public Operable(String make, String model, String vin, double mpg, boolean qualifiedEmissions) { 
            super(make, model, vin, mpg);
            this.qualifiedEmissions = qualifiedEmissions;
        }
        abstract boolean confirmEmissions(double mpg, boolean qualifiedEmissions);
        abstract String returnVehicleSummary(boolean resultFrom);
    }
    public static class Ford extends Operable {
        int edition;
        public Ford(String model, String vin, double mpg, boolean qualifiedEmissions, int edition) {
            super("Ford", model, vin, mpg, qualifiedEmissions);
            this.edition = edition;
        }
        @Override
        public boolean confirmEmissions(double mpg, boolean qualifiedEmissions) {
            if ((this.edition * mpg) < 24) { 
                qualifiedEmissions = false;
            } else { qualifiedEmissions = true; }
            return qualifiedEmissions;
        }
        @Override
        public String returnVehicleSummary(boolean resultFrom) {
            return String.format("%d Edition %s %s with vin (%s) that makes %.2f miles per gallon. %s", edition, make, model, vin, mpg, resultFrom ? "Emissions are stable." : "Emissions are unstable.");
        }
    }
    public static class Mercedez extends Operable {
        int edition;
        public Mercedez(String model, String vin, double mpg, boolean qualifiedEmissions, int edition) {
            super("Mercedez", model, vin, mpg, qualifiedEmissions);
            this.edition = edition;
        }
        @Override
        public boolean confirmEmissions(double mpg, boolean qualifiedEmissions) {
            if ((this.edition * mpg) < 24) {  
                qualifiedEmissions = false;
            } else { qualifiedEmissions = true; }
            return qualifiedEmissions;
        }
        @Override
        public String returnVehicleSummary(boolean resultFrom) {
            return String.format("%d Edition %s %s with vin (%s) makes %.2f miles per gallon. %s", edition, make, model, vin, mpg, resultFrom ? "Emissions are stable." : "Emissions are unstable.");
        }
    }


    // Refresher 7 - Inheritance, MultiLevel Cahing, 3+ Deep
    // Every level in a chain calls super() to its immediate parent, not skipping ahead
    // Even in a 3 level chain, the middle class is responsible for reaching the top, not the bottom class reaching all the way up directly.
    // Method overriding can also happen at any level, and super.methodName (distinct from super()) lets a level call its immediate parents version of a method before adding to it
    //      rather than fully replacing it
    // The domain: an employee hierarchy - worker, then supervisor, then regional manager, each level building on the one below
    // Build three genuine levels, each with at least one new field of its own, correct super() chaining through all three levels of construction
    //      at least one method that gets progressively built upon at each level using super.methodName() rather than being fully rewritten from scratch every time
    static abstract class Employable {
        String firstName; String lastName; String empID; String position; double baseRate; int scheduledHours_Weekly;
        public Employable(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly) {
            this.firstName = firstName; this.lastName = lastName; this.empID = empID;
            this.position = position; this.baseRate = baseRate; this.scheduledHours_Weekly = scheduledHours_Weekly;
        }
        String returnName() {
            return String.format("%s %s", firstName, lastName);
        }
        abstract String returnMonthlySummary(String cocPos);

    }
    interface Payable {
        double grossMonthlyPay();
        double netMonthlyPay(double stateTax, double fedTax, double iraDeductions);
        double getPaycheck(double forHours);
    }
    interface ControlEmployment {
        void hireCandidate(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly);
        void fireEmployee(Employee employee); // not using Employable since that'll consist of Employee, Supervisor, RegManager
    }
    public static boolean confirmID(String employableID) {
        int numberCount = 0; int letterCount = 0;
        for (int letterIndex = 0; letterIndex < employableID.length(); letterIndex++) {
            char c = employableID.charAt(letterIndex);
            if (Character.isLetter(c)) { letterCount++; } if (Character.isDigit(c)) { numberCount++; }
        }
        boolean isValid = (numberCount == 3 && letterCount == 4);
        System.out.println(isValid ? "ID is valid" : "ID is not valid");
        return isValid;
    }
    private static class Employee extends Employable implements Payable {
        static TreeMap<String, String> employees = new TreeMap<>(); // Employee firstLast with Employee empID
        static TreeMap<String, Integer> positions = new TreeMap<>(); // position and # of occurrences
        static TreeMap<String, Integer> employeeHours = new TreeMap<>();
        static LinkedHashMap<String, Double> employeeComp = new LinkedHashMap<>(16, 0.75f, false); // Employee with their month sal
        static int totalEmployeeCharge = 0;

        public static void getEmployeeInfo() { employees.forEach((name, id) -> System.out.println(String.format("Employees Created%nNAME: %s, ID: %s", name, id))); }
        public static void getPosSummary() { 
            for (Map.Entry<String, Integer> numOfPos: positions.entrySet()) { 
                System.out.println(String.format("There are %d employees that are a %s", numOfPos.getValue(), numOfPos.getKey()));
            }
        }
        public static void getHourSummary() { employeeHours.forEach((name, hrs) -> System.out.println(String.format("Employee Hours%n  %s works %d hours per week", name, hrs))); }
        public static void getMonthlySal() { 
            employeeComp.forEach((name, comp) -> System.out.println(String.format("Employee Monthly Compensation%n  %s makes $%.2f per month", name, comp)));
            System.out.println(String.format("It costs the business %d per hour to compensate each employee", totalEmployeeCharge));
        }

        private Employee(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly) { 
            super(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly);
        }

        public static Employee hireEmployee(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly) {
            if (firstName == null || lastName == null) { return null; } if (baseRate < 7.00 ) { return null; } if (scheduledHours_Weekly < 18) { return null; }
            String interpolatedName = String.format("%s%s", firstName, lastName);
            if (employees.containsKey(interpolatedName)) { return null; }
            if (employees.containsValue(empID)) { System.out.println("Cannot use an employee ID for two employees"); return null; }
            if (!confirmID(empID)) { return null; }
            
            employees.put(interpolatedName, empID); positions.merge(position, 1, (o, n) -> o + n);
            totalEmployeeCharge += baseRate; employeeComp.put(interpolatedName, (baseRate * (scheduledHours_Weekly * 4)));
            employeeHours.put(interpolatedName, scheduledHours_Weekly);
            return new Employee(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly);
        }
        @Override
        public double grossMonthlyPay() { return baseRate * (scheduledHours_Weekly * 4); }
        @Override
        public double netMonthlyPay(double stateTax, double fedTax, double iraDeductions) {
            if (stateTax > 1) { stateTax = 0.20;} if (fedTax > 1) { fedTax = 0.1;}
            double baseMP = grossMonthlyPay();
            baseMP -= ((baseMP * stateTax) + (baseMP * fedTax));
            if (iraDeductions > baseMP) { iraDeductions = (baseMP * 0.05);}
            baseMP -= iraDeductions;
            return baseMP;
        }
        @Override
        public double getPaycheck(double forHours) {
            if (forHours > (scheduledHours_Weekly) * 2) { forHours -= 0.50; }
            double grossPay = forHours * baseRate;
            double stateTax = 0.20; double fedTax = 0.1; double iraDeductions = grossPay * 0.05;
            return (grossPay - ((grossPay * stateTax) + (grossPay * fedTax) + iraDeductions));
        }
        @Override
        public String returnMonthlySummary(String cocPos) {
            String identificationString = String.format("Summary for %s(%s) %s %s %s", cocPos, empID, position, firstName, lastName);
            String salaryString = String.format("Gross Pay(monthly): $%.2f%nNet Pay(monthly): $%.2f", this.grossMonthlyPay(), this.netMonthlyPay(0.2, 0.1, 234.50));
            return String.format("%s%n%s", identificationString, salaryString);
        }
    }
    public static class Supervisor extends Employee implements ControlEmployment {
        static TreeMap<String, String> supervisors = new TreeMap<>(); // supervisors firstLast with their respective ID
        static LinkedHashMap<String, Integer> employeesManaging = new LinkedHashMap<>(16, 0.75f, false); // spvsr w/ # of emps

        TreeMap<String, String> managingEmployees = new TreeMap<>(); double pto_Hours; int performanceBonus;

        public static void getSupervisorsSummary() {
            supervisors.forEach((name, id) -> System.out.println(String.format("Supervisors Created%nNAME: %s, ID: %s", name, id)));
        }
        public static void employeesManagingSummary() {
            employeesManaging.forEach((name, amt) -> System.out.println(String.format("%s manages %d employees", name, amt)));
        }

        private Supervisor(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly, double pto_Hours, int performanceBonus) {
            super(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly); this.pto_Hours = pto_Hours; this.performanceBonus = performanceBonus;
        }
        public static Supervisor hireSupervisor(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly, double pto_Hours, int performanceBonus) {
            if (firstName == null || lastName == null || empID == null || position == null) { return null; } if (!confirmID(empID)) { return null; }
            if (baseRate < 20) { return null; } if (scheduledHours_Weekly < 40) { return null; } if (pto_Hours < 60) { return null; } if (performanceBonus < 0) { return null; }

            String interpolatedName = String.format("%s%s", firstName, lastName);
            if (supervisors.containsKey(interpolatedName)) { return null; }

            supervisors.put(interpolatedName, empID);
            return new Supervisor(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly, pto_Hours, performanceBonus);
        }

        public void getManagingEmpSummary() {
            managingEmployees.forEach((name, id) -> System.out.println(String.format("Employees that %s Manages%nNAME: %s, ID: %s", firstName, name, id)));
        }

        public void usePTO(double hours, boolean absolutelyNecessary) {
            if (hours > pto_Hours) { 
                if (absolutelyNecessary) {
                    System.out.println("Since this request is absolutely necessary to fulfill, it will be fulfilled but will be penalized with a 2% reduction on performance bonus");
                    performanceBonus -= (performanceBonus * 0.05);
                    pto_Hours -= hours;
                } else { System.out.println(String.format("Cannot use %.2f hours. Only %.2f hours are available. If this is absolutely necessary, resubmit request with noted necessity.", hours, pto_Hours));}
                
            } else { pto_Hours -= hours; System.out.println(String.format("Used %.2f pto hours. %.2f hours of pto remaining to use", hours, pto_Hours)); }
        }
        @Override
        public double grossMonthlyPay() {
            return super.grossMonthlyPay() + performanceBonus;
        }
        @Override
        public double netMonthlyPay(double stateTax, double fedTax, double iraDeductions) {
            double regNetMonthly = super.netMonthlyPay(stateTax, fedTax, iraDeductions);
            double feeOnPTO = (pto_Hours * baseRate) * 0.05; regNetMonthly -= feeOnPTO;
            return regNetMonthly;
        }
        public double accessEmpVersion_NETMONTHLY(double stateTax, double fedTax, double iraDeductions) { return super.netMonthlyPay(stateTax, fedTax, iraDeductions); }
        @Override
        public double getPaycheck(double forHours) {
            double preliminaryBonus = performanceBonus * 0.05;
            return super.getPaycheck(forHours) + preliminaryBonus;
        }
        @Override
        public String returnMonthlySummary(String cocPos) {
            String perfAndPTO = String.format("%s currently has %.2f available PTO hours and will be expecting a $%d as a bonus at the end of the month", firstName, pto_Hours, performanceBonus);
            return String.format("%s%n%s", super.returnMonthlySummary(cocPos), perfAndPTO);
        }
        public void hireCandidate(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly) {
            if (this.managingEmployees.containsKey(firstName) == false) { 
                Employee employee = Employee.hireEmployee(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly);
                if (employee != null) {
                    managingEmployees.put(employee.firstName, employee.empID);
                    employeesManaging.merge(this.firstName, 1, (o, n) -> o +n);
                } else { System.out.println("An error occurred while trying to hire " + firstName); }
            } else { 
                System.out.println(String.format("%s was already hired by %s", firstName, this.firstName)); 
            }
        }
        public void fireEmployee(Employee employee) {
            if (managingEmployees.containsKey(employee.firstName)) {
                managingEmployees.remove(employee.firstName); employeesManaging.merge(this.firstName, 1, (o, n) -> o - n);
            } else { System.out.println(String.format("It does not seem like %s is %s's supervisor.", firstName, employee.firstName)); }
        }
    }
    public static class RegionalManager extends Supervisor {
        int storesManaging;
        boolean vacationBonusApproved = false;
        boolean canConvert_PTOtoBonus;
        static TreeMap<String, String> regManagersCreated = new TreeMap<>();
        static LinkedHashMap<String, String> supervisorsManaging = new LinkedHashMap<>(16, 0.75f, false); // Name w/ empID]
        // compareTo isnt necessary here since the maps work with only strings not objs

        public static void managersCreated() {
            regManagersCreated.forEach((name, id) -> System.out.println(String.format("Regional Managers Created: %nNAME: %s, ID: %s", name, id)));
        }
        public static void managingSupes() {
            for (Map.Entry<String, String> supes : supervisorsManaging.entrySet()) { System.out.println(String.format("Managing Supervisors%nNAME: %s, ID: %s", supes.getKey(), supes.getValue())); }
        }

        private RegionalManager(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly, double pto_Hours, int performanceBonus, int storesManaging, boolean canConvert_PTOtoBonus) {
            super(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly, pto_Hours, performanceBonus);
            this.storesManaging = storesManaging; this.canConvert_PTOtoBonus = canConvert_PTOtoBonus;
        }
        public static RegionalManager createRegionalManger(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly, double pto_Hours, int performanceBonus, int storesManaging, boolean canConvert_PTOtoBonus) {
            if (firstName == null || lastName == null || empID == null || position == null) { return null; }
            if (!confirmID(empID)) { return null; } if (baseRate < 60) { return null; }
            if (scheduledHours_Weekly < 45) { return null; } if (pto_Hours < 80) { return null; }
            if (performanceBonus < 8000) { return null; } if (storesManaging < 1) { return null; }

            String interpolatedName = String.format("%s%s", firstName, lastName);
            if (regManagersCreated.containsKey(interpolatedName)) { return null; }
            regManagersCreated.put(interpolatedName, empID);
            return new RegionalManager(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly, pto_Hours, performanceBonus, storesManaging, canConvert_PTOtoBonus);
        }

        public void createSupervisor(String firstName, String lastName, String empID, String position, double baseRate, int scheduledHours_Weekly, double pto_Hours, int performanceBonus) {
            Supervisor hiredSup = super.hireSupervisor(firstName, lastName, empID, position, baseRate, scheduledHours_Weekly, pto_Hours, performanceBonus);
            String interpolatedName = String.format("%s%s", firstName, lastName);
            if (hiredSup != null) {
                if (supervisorsManaging.containsKey(interpolatedName)) { System.out.println("This supervisor has already been hired"); return; } else {
                    supervisorsManaging.put(interpolatedName, empID);
                }
            } else {
                System.out.println("Something wrong occurred while trying to hire this supervisor");
            }
        }
        public void approveVacationRequest() {
            this.vacationBonusApproved = (this.scheduledHours_Weekly > 55 && this.baseRate > 70 && this.performanceBonus > 10000);
            System.out.println(this.vacationBonusApproved ? String.format("Vacation has been approved for %s", firstName) : String.format("Vacation was not approved for %s", firstName));
        }
        public String convertPTOtoBonus() {
            if (canConvert_PTOtoBonus) {
                if (pto_Hours > 0) {
                    double converted = (pto_Hours * baseRate);
                    performanceBonus += converted; double preConversion = pto_Hours; pto_Hours = 0.00;
                    return String.format("Successfully converted %s's %.2f PTO hours to be added to their bonus. Adding a total of $%.2f to bonus. Bonus will now be $%d", firstName, preConversion, converted, performanceBonus);
                } else {
                    return String.format("Since %s has %.2f PTO hours available, we cannot add it to their bonus", firstName, pto_Hours);
                }
            } else {
                return String.format("%s cannot convert their PTO hours to be added to thier bonus", firstName);
            }
        }

        @Override
        public void usePTO(double hours, boolean absolutelyNecessary) {
            if (absolutelyNecessary) {
                if (hours > pto_Hours) {
                    System.out.println("Since this request is absolutely necessary to fulfill, it will be fulfilled and will not be penalized against bonus nor pto hours");
                } else {
                    pto_Hours -= hours; System.out.println(String.format("Used %.2f pto hours. %.2f hours of pto remaining to use", hours, pto_Hours));
                }
            } else {
                if (hours > pto_Hours) {
                    pto_Hours -= hours;
                    System.out.println(String.format("%s cannot use %.2f hours. Only %.2f hours are available. PTO will be approved but will be deducted from next set of PTO hours awarded", firstName, hours, pto_Hours));
                } else {
                    pto_Hours -= hours; System.out.println(String.format("Used %.2f pto hours. %.2f hours of pto remaining to use", hours, pto_Hours));
                }
            }
        }
        @Override
        public double getPaycheck(double forHours) {
            return super.getPaycheck(forHours) + (performanceBonus * 0.05);
        }
        @Override
        public double netMonthlyPay(double stateTax, double fedTax, double iraDeductions) {
            return super.accessEmpVersion_NETMONTHLY(stateTax, fedTax, iraDeductions) + baseRate;
        }
        @Override
        public String returnMonthlySummary(String cocPos) {
            String didConvert = (canConvert_PTOtoBonus && pto_Hours == 0.00) ? String.format("%s %s chose to convert their PTO hours to be applied to their bonus", cocPos, firstName) : String.format("%s has not chose to convert their pto hours to be applied to their bonus so they have %.2f pto hours", firstName, pto_Hours);
            String performance = String.format("%s will be expecting a $%d as a bonus at the end of the month", firstName, performanceBonus);
            String hoursThisMonth = String.format("%s was scheduled for %d hours this month accmulating to a gross check of $%.2f", firstName, scheduledHours_Weekly * 4, grossMonthlyPay());
            return String.format("%s%n%s%n%s", didConvert, performance, hoursThisMonth);
        }
    }

    // Refresher 8 -- Inheritance Combined with An Interface, Closing Synthesis
    // This pulls together the whole set - a concrete inheritance chain where only some subclassess additionally implement a separate capability interface, not all of them
    // The domain: a notification system, different notification types sharing a common parent class but only some of them are also urgent (a separate interface capability the others dont have)
    // Build a base notif class (shared field like message, timestamp), at least two/three concrete subclasses, where only soem of them additionally implements an Urgent interface (with something like a default escalation method)
    //  proving the inheritance hierarchy and the interface capabiliyt are genuinely independent of each other
    //      a subclass can be deep in the inheritance chain without being Urgent, and Urgency doesnt require any particullar position in that chain
    interface UrgentNotification {
        Notification raiseUrgency(int to);
    }
    public static abstract class Notification {
        String appName; String title; int timeStamp; int importancy;
        public Notification(String appName, String title, int timeStamp, int importancy) { this.appName = appName; this.title = title; this.timeStamp = timeStamp; this.importancy = importancy; }
        String getSummary() { return String.format("Notification from %s: %s at %d. Importancy %d", appName, title, timeStamp, importancy); }
    }
    public static class BasicNotification extends Notification implements UrgentNotification {

        private BasicNotification(String appName, String title, int timeStamp, int importancy) {
            super(appName, title, timeStamp, importancy);
        }
        public static BasicNotification createBasicNotif(String appName, String title, int timeStamp, int importancy) {
            if (appName == null || title == null) { 
                System.out.println("An error occurred trying to process the app name and title of this notification. Resubmit");
                return null;
            } if (timeStamp < 0 || String.valueOf(timeStamp).length() != 4 || timeStamp > 2359) { 
                System.out.println("There is an error with the time. The hours should be between 0 and 23, nothing greater"); return null; 
            }
            String timeString = String.valueOf(timeStamp);
            String hourString = timeString.substring(0,2);
            String minuteString = timeString.substring(2,4);
            int hours = Integer.parseInt(hourString); if (hours > 23) { return null; }
            int minutes = Integer.parseInt(minuteString); if (minutes > 59) { return null; }
            if (importancy < 0 || importancy > 2) { 
                System.out.println("Basic notifications shall have an importancy of either 1 or 2. If importancy is higher, submit a higher importancy notification");
                return null;
            }
            return new BasicNotification(appName, title, timeStamp, importancy);
        }
        @Override
        public Notification raiseUrgency(int to) { 
            if (to > 6 || to < 3) { System.out.println("Importancy level cannot be higher than 6 or lower than 3"); return null; }
            if (to == 3 || to == 4) {
                return MidNotification.createMidNotif(appName, title, timeStamp, to);
            } else {
                return HighNotification.createHighNotif(appName, title, timeStamp, to);
            }
        }
    }
    public static class MidNotification extends Notification implements UrgentNotification {
        private MidNotification(String appName, String title, int timeStamp, int importancy) {
            super(appName, title, timeStamp, importancy);
        }
        public static MidNotification createMidNotif(String appName, String title, int timeStamp, int importancy) {
            if (appName == null || title == null) { 
                System.out.println("An error occurred trying to process the app name and title of this notification. Resubmit");
                return null;
            } if (timeStamp < 0 || String.valueOf(timeStamp).length() != 4 || timeStamp > 2359) { 
                System.out.println("There is an error with the time. The hours should be between 0 and 23, nothing greater"); return null; 
            }
            String timeString = String.valueOf(timeStamp);
            String hourString = timeString.substring(0,2);
            String minuteString = timeString.substring(2,4);
            int hours = Integer.parseInt(hourString); if (hours > 23) { return null; }
            int minutes = Integer.parseInt(minuteString); if (minutes > 59) { return null; }
            if (importancy < 3 || importancy > 4) { 
                System.out.println("Mid-Level notifications shall have an importancy of either 1 or 2. If importancy is higher, submit a higher importancy notification");
                return null;
            }
            return new MidNotification(appName, title, timeStamp, importancy);
        }

        @Override
        public Notification raiseUrgency(int to) { 
            if (to > 6 || to < 5) { System.out.println("Importancy level cannot be higher than 6 or lower than 3"); return null; }
            return HighNotification.createHighNotif(appName, title, timeStamp, to);
        }
    }
    public static class HighNotification extends Notification {
        private HighNotification(String appName, String title, int timeStamp, int importancy) {
            super(appName, title, timeStamp, importancy);
        }
        public static HighNotification createHighNotif(String appName, String title, int timeStamp, int importancy) {
            if (appName == null || title == null) { 
                System.out.println("An error occurred trying to process the app name and title of this notification. Resubmit");
                return null;
            } if (timeStamp < 0 || String.valueOf(timeStamp).length() != 4 || timeStamp > 2359) { 
                System.out.println("There is an error with the time. The hours should be between 0 and 23, nothing greater"); return null; 
            }
            String timeString = String.valueOf(timeStamp);
            String hourString = timeString.substring(0,2);
            String minuteString = timeString.substring(2,4);
            int hours = Integer.parseInt(hourString); if (hours > 23) { return null; }
            int minutes = Integer.parseInt(minuteString); if (minutes > 59) { return null; }
            if (importancy < 5 || importancy > 6) { 
                System.out.println("High-Level notifications shall have an importancy of either 1 or 2. If importancy is higher, submit a higher importancy notification");
                return null;
            }
            return new HighNotification(appName, title, timeStamp, importancy);
        }
    }

    // Exercise 1 of 5 - Full OOP Wrapping, No Scaffold
        // This stretch requires genuine class hierarchies (inheritance and/or interfaces)) wrapping around Day 3's container work
        //      not containers used standalone the way most of this file has been
        // Design a system of your choosing - abstract class or interface as the backbone
        // At least two conrete subclasses
        //      where the classes themselves use at least two different Day3 container types internally (any combination of Map, List or Set)
        //          to track their own state, not just as method parameters
        // So Im going to build a bank system with a signup class and eligibility and accounts like savings/checkings with own features
        // The client will have a prop to each, and will have their beneficiaries which will be a set which would have to be a person of age
    public static abstract class PersonalDetails {
            String firstName; String lastName; String dob; int idNumber; String idExpiry;
            public PersonalDetails(String firstName, String lastName, String dob, int idNumber, String idExpiry) {
                this.firstName = firstName; this.lastName = lastName; this.dob = dob;
                this.idNumber = idNumber; this.idExpiry = idExpiry;
            }
        }
        public static class Person extends PersonalDetails implements Comparable<Person>{
            static String todaysDate = "08142026";

            // static maps resembling history of deposits/withdrawals/receivals

            double cashOnHand; CheckingAccount checkings = null; SavingsAccount savings = null;

            private Person(String firstName, String lastName, String dob, int idNumber, String idExpiry, double cashOnHand) {
                super(firstName, lastName, dob, idNumber, idExpiry); this.cashOnHand = cashOnHand;
            }
            public static Person createPerson(String firstName, String lastName, String dob, int idNumber, String idExpiry, double cashOnHand) {
                if (firstName == null || lastName == null) {
                    System.out.println("An error occurred whilst trying to set the name parameters to this Person obj. Recheck");
                    return null;
                }
                if (dob.length() != 8) {
                    System.out.println("Make sure the dob of this person is MMDDYYYY");
                    return null;
                }
                if (String.valueOf(idNumber).length() != 8) {
                    System.out.println("ID numbers shall be exactly 8 digits"); return null;
                }
                if (Integer.parseInt(idExpiry) < Integer.parseInt(todaysDate)) {
                    System.out.println("You cannot make a person with an expired ID"); return null;
                }
                int dob_day = Integer.parseInt(dob.substring(2, 4));
                int dob_month = Integer.parseInt(dob.substring(0, 2));
                int dob_year = Integer.parseInt(dob.substring(4, 8));
                if (dob_month > 12 || dob_month < 0) {
                    System.out.println("The month should not be greater than 12"); return null;
                } else {
                    if (dob_month != 1 || dob_month != 3 || dob_month != 5 || dob_month != 7 || dob_month != 8 || dob_month != 10 || dob_month !=12) {
                        if (dob_month == 2) {
                            if (dob_day > 28) {
                                System.out.println("February does not have more than 28 days"); return null;
                            }
                        } else {
                            if (dob_day > 30) {
                                System.out.println("The month you passed in does not have more than 30 days. Reevaluate");
                                return null;
                            }
                        }
                    } else {
                        if (dob_day > 31) {
                            System.out.println("No months in the year have more than 31 days. Resubmit"); return null;
                        }
                    }
                }
                if ((dob_year + 18) > Integer.parseInt(todaysDate.substring(4, 8))) {
                    System.out.println("We do not accept minors"); return null;
                }
                if (Integer.parseInt(idExpiry) <= Integer.parseInt(todaysDate)) {
                    System.out.println("We're sorry but we do not accept expired ID's");
                    return null;
                }
                if (cashOnHand < 0.00) { System.out.println("You cannot try to register an account with debt."); return null; }

                System.out.println(String.format("Successfully created new Person Obj%nNAME: %s %s%nDOB: %s%nID NUMBER: %d%nID EXPIRY: %s%nAVAILABLE CASH: $%.2f", firstName, lastName, dob, idNumber, idExpiry, cashOnHand));
                return new Person(firstName, lastName, dob, idNumber, idExpiry, cashOnHand);
            }


            public String recieveMoney(double amount) { 
                if (amount < 0) { return null; }
                this.cashOnHand += amount; return String.format("%s received $%.2f, now has $%.2f cash on hand.", firstName, amount, cashOnHand);
            }
            public String giveMoney(double amount) {
                if (amount < 0 || amount > cashOnHand) { return null; }
                this.cashOnHand -= amount; return String.format("%s gave $%.2f, now has $%.2f cash on hand.", firstName, amount, cashOnHand);
            }
            @Override public boolean equals(Object other) {
                String thisIDNumber = String.valueOf(this.idNumber);
                if (this == other) { return true; }
                if (!(other instanceof Person)) { return false; }
                Person otherperson = (Person) other;
                String otherIDNUmber = String.valueOf(otherperson.idNumber);
                return thisIDNumber.equals(otherIDNUmber);
            }
            @Override
            public int hashCode() { return Objects.hash(String.valueOf(idNumber)); }

            @Override
            public int compareTo(Person other) {
                String thisIDNum = String.valueOf(this.idNumber);
                String otherIDNum = String.valueOf(other.idNumber);
                return thisIDNum.compareTo(otherIDNum);
            }

            public String getNetworth() {
                return String.format("%s has a net worth of $%.2f.", firstName, (checkings == null ? 0 : checkings.balance) + (savings == null ? 0 : savings.balance) + this.cashOnHand);
            }
        }
        public abstract static class AccountRegistration {
            public AccountRegistration() {}
            abstract boolean RegisterAccount(Person forPerson, String accountType);
        }
        public abstract static class BasicAccountFeatures {
            Person person; String AccountNumber; String RoutingNumber; double balance;
            public BasicAccountFeatures(Person person, String AccountNumber, String RoutingNumber, double balance) {
                this.person = person; this.AccountNumber = AccountNumber;
                this.RoutingNumber = RoutingNumber; this.balance = balance;
            }
            abstract boolean deposit(double amount);
            abstract boolean withdraw(double amount);
        }
        interface Transferrable {
            default boolean transferTo(Person person, double amount, BasicAccountFeatures from, BasicAccountFeatures to, LinkedHashMap<Person, Integer> staticFrequencyTracker, LinkedHashMap<Double, Integer> selfTransferTracker) {
                if ((from instanceof CheckingAccount && to instanceof CheckingAccount || (from instanceof SavingsAccount && to instanceof SavingsAccount))) { return false; }
                if (!from.person.equals(person)) { return false; }
                if (!to.person.equals(person))   { return false; }

                if (from instanceof CheckingAccount) {
                    if (amount > from.balance) { 
                        System.out.println(String.format("You cannot perform a transfer more than your current balance which stands at $%.2f", from.balance));
                        return false;
                    } else {
                        from.balance -= amount;
                        to.balance += amount;
                        System.out.println(String.format("Successfully transferred $%.2f from your checking account.%nCurrent Balances:%n  Checking: $%.2f%n  Savings: $%.2f", amount, from.balance, to.balance));
                        staticFrequencyTracker.merge(person, 1, (o, n) -> o + n); selfTransferTracker.merge(amount, 1, (o, n) -> o +n);
                        return true;
                    }
                } else {
                    if (amount > from.balance) { 
                        System.out.println(String.format("You cannot perform a transfer more than your current balance which stands at $%.2f", from.balance));
                        return false;
                    } else {
                        from.balance -= amount;
                        to.balance += amount;
                        System.out.println(String.format("Successfully transferred $%.2f from your savings account.%nCurrent Balances:%n  Savings: $%.2f%n  Checkings: $%.2f", amount, from.balance, to.balance));
                        staticFrequencyTracker.merge(person, 1, (o, n) -> o + n); selfTransferTracker.merge(amount, 1, (o, n) -> o +n);
                        return true;
                    }
                }
            }
        }
        public static class CheckingAccount extends BasicAccountFeatures implements Transferrable, Comparable<CheckingAccount> {
            static Set<Person> peopleEnrolled = new HashSet<>();
            static LinkedHashMap<Person, Double> startingBalances = new LinkedHashMap<>(16, 0.75f, false);
            static TreeMap<Person, Integer> depositFrequency = new TreeMap<>(); // this works because Person uses compareTo based on ID number
            static TreeMap<Person, Integer> withdrawalFrequency = new TreeMap<>();
            static LinkedHashMap<Person, Integer> transferFrequency = new LinkedHashMap<>(16, 0.75f, false);

            public static void getTransferFrequency() {
                for (Map.Entry<Person, Integer> pair: transferFrequency.entrySet()) {
                    System.out.println(String.format("%s has transferred %d times", pair.getKey().firstName, pair.getValue()));
                }
            }
            public static void getPeopleEnrolled() {
                for (Person personEnrolled: peopleEnrolled) { System.out.println(String.format("%s %s enrolled successfully", personEnrolled.firstName, personEnrolled.lastName)); }
            }
            public static void getStartingBalance() {
                startingBalances.forEach((person, balance) -> {
                    System.out.println(String.format("%s opened an account wtih $%.2f", person.firstName, balance));
                });
            }
            public static void getDepositFrequency() {
                for (Map.Entry<Person, Integer> pair: depositFrequency.entrySet()) {
                    System.out.println(String.format("%s deposited %d times", pair.getKey().firstName, pair.getValue()));
                }
            }
            public static void getWithdrawalFrequency() {
                for (Map.Entry<Person, Integer> pair: withdrawalFrequency.entrySet()) {
                    System.out.println(String.format("%s withdrew %d times", pair.getKey().firstName, pair.getValue()));
                }
            }

            boolean overdraftEnabled; LinkedHashMap<Double, Integer> depositOccurrences = new LinkedHashMap<>(16, 0.75f, false);
            LinkedHashMap<Double, Integer> withdrawalOccurrences = new LinkedHashMap<>(16, 0.75f, false);
            LinkedHashMap<Double, Integer> transferredTimes = new LinkedHashMap<>(16, 0.75f, false);

            public CheckingAccount(Person person, String AccountNumber, String RoutingNumber, double balance, boolean overdraftEnabled) {
                super(person, AccountNumber, RoutingNumber, balance); this.overdraftEnabled = overdraftEnabled;
            }
            public static CheckingAccount createCheckings(Person person, String AccountNumber, String RoutingNumber, double balance, boolean overdraftEnabled) {

                return new CheckingAccount(person, AccountNumber, RoutingNumber, balance, overdraftEnabled);
            }
            public void getTransferredAmounts() {
                transferredTimes.forEach((amount, times) -> {
                    System.out.println(String.format("%s has transferred $%.2f %d times", this.person.firstName, amount, times));
                });
            }
            @Override
            public boolean deposit(double amount) {
                if (amount < 0) { System.out.println(String.format("Dear %s, you cannot deposit an amount less than 0", person.firstName)); return false; }
                if (amount > person.cashOnHand) { System.out.println("You need to have money in order to deposit. You do not have enough"); return false; }
                this.person.giveMoney(amount);
                balance += amount; System.out.println(String.format("Succesfully deposited $%.2f, new balance stands at $%.2f", amount, balance));
                depositOccurrences.merge(amount, 1, (o, n) -> o +n); depositFrequency.merge(this.person, 1, (o, n) -> o + n);
                return true;
            }
            @Override
            public boolean withdraw(double amount) {
                if (amount > balance) {
                    if (overdraftEnabled) {
                        balance -= amount; System.out.println(String.format("Overdraft has been enabled, new balance stands at $%.2f", balance));
                        withdrawalOccurrences.merge(amount, 1, (o, n) -> o +n); withdrawalFrequency.merge(this.person, 1, (o, n) -> o + n);
                        System.out.println(this.person.recieveMoney(amount)); return true;
                    } else { 
                        System.out.println(String.format("%s you only have $%.2f; you cannot withdraw $%.2f since you do not have overdraft enabled", person.firstName, balance, amount));
                        return false;
                    }
                } else {
                    System.out.println(this.person.recieveMoney(amount));
                    balance -= amount; System.out.println(String.format("%s you just withdrew $%.2f, your new balance is $%.2f", person.firstName, amount, balance));
                    withdrawalOccurrences.merge(amount, 1, (o, n) -> o +n); withdrawalFrequency.merge(this.person, 1, (o, n) -> o + n);
                    return true;
                }
            }
            @Override
            public boolean equals(Object other) {
                if (this == other) { return true; }
                if (!(other instanceof CheckingAccount)) { return false; }
                CheckingAccount otherCA = (CheckingAccount) other;
                return this.AccountNumber.equals(otherCA.AccountNumber);
            }
            @Override
            public int hashCode() { return Objects.hash(AccountNumber); }
            @Override
            public int compareTo(CheckingAccount other) {
                return this.AccountNumber.compareTo(other.AccountNumber);
            }
            public void getDepositHistory() {
                depositOccurrences.forEach((amount, times) -> {
                    System.out.println(String.format("%s deposited $%.2f %d times", this.person.firstName, amount, times));
                });
            }
            public void getWithdrawalHistory() {
                withdrawalOccurrences.forEach((amount, times) -> {
                    System.out.println(String.format("%s withdrew $%.2f %d times", this.person.firstName, amount, times));
                });
            }
        }
        public static class SavingsAccount extends BasicAccountFeatures implements Transferrable, Comparable<SavingsAccount> {
            static Set<Person> peopleEnrolled = new HashSet<>();
            static LinkedHashMap<Person, Double> startingBalances = new LinkedHashMap<>(16, 0.75f, false);
            static TreeMap<Person, Integer> depositFrequency = new TreeMap<>(); // this works because Person uses compareTo based on ID number
            static TreeMap<Person, Integer> withdrawalFrequency = new TreeMap<>();
            static LinkedHashMap<Person, Integer> transferFrequency = new LinkedHashMap<>(16, 0.75f, false);

            public static void getTransferFrequency() {
                for (Map.Entry<Person, Integer> pair: transferFrequency.entrySet()) {
                    System.out.println(String.format("%s has transferred %d times", pair.getKey().firstName, pair.getValue()));
                }
            }
            public static void getPeopleEnrolled() {
                for (Person personEnrolled: peopleEnrolled) { System.out.println(String.format("%s %s enrolled successfully", personEnrolled.firstName, personEnrolled.lastName)); }
            }
            public static void getStartingBalance() {
                startingBalances.forEach((person, balance) -> {
                    System.out.println(String.format("%s opened an account wtih $%.2f", person.firstName, balance));
                });
            }
            public static void getDepositFrequency() {
                for (Map.Entry<Person, Integer> pair: depositFrequency.entrySet()) {
                    System.out.println(String.format("%s deposited %d times", pair.getKey().firstName, pair.getValue()));
                }
            }
            public static void getWithdrawalFrequency() {
                for (Map.Entry<Person, Integer> pair: withdrawalFrequency.entrySet()) {
                    System.out.println(String.format("%s withdrew %d times", pair.getKey().firstName, pair.getValue()));
                }
            }
 
            double api; LinkedHashMap<Double, Integer> depositOccurrences = new LinkedHashMap<>(16, 0.75f, false);
            LinkedHashMap<Double, Integer> withdrawalOccurrences = new LinkedHashMap<>(16, 0.75f, false);
            LinkedHashMap<Double, Integer> transferredTimes = new LinkedHashMap<>(16, 0.75f, false);

            public SavingsAccount(Person person, String AccountNumber, String RoutingNumber, double balance, double api) {
                super(person, AccountNumber, RoutingNumber, balance); this.api = api;
            }
            public static SavingsAccount createSavings(Person person, String AccountNumber, String RoutingNumber, double balance, double api) {
                if (startingBalances.containsKey(person)) { return null; }
                if (person == null) { System.out.println("There must have been something wrong whenever settign this persons information up"); return null; }
                if (!AccountNumber.matches("\\d+")) { System.out.println("Account Numbers only contain digits"); return null; }
                if (!RoutingNumber.matches("\\d+")) { System.out.println("Routing Numbers only contain digits"); return null; }
                if (!AccountNumber.startsWith("273")) {
                    System.out.println("This bank enforces Account Numbers to start with '273'"); return null;
                }
                if (!RoutingNumber.startsWith("112")) {
                    System.out.println("This bank enforces Routing Numbers to start with '112'"); return null;
                }
                if (balance < 0) { System.out.println("You cannot create an account with a negative balance"); return null;}
                if (balance > person.cashOnHand) { System.out.println("We cannot start an account with an amount greater than what you have on hand"); return null; }
                if (api < 0) { System.out.println("API rates cannot start below 0"); return null; }
                
                person.cashOnHand -= balance; 
                SavingsAccount newSavings = new SavingsAccount(person, AccountNumber, RoutingNumber, balance, api);
                person.savings = newSavings;
                System.out.println(String.format("NEW SAVINGS ACCOUNT:%n NAME: %s%n AN: %s%n RN: %s%nBAL: $%.2f%n API: %.2f percent", person.firstName, AccountNumber, RoutingNumber, balance, api * 100));
                System.out.println(String.format("%s now has $%.2f left on hand", person.firstName, person.cashOnHand));
                peopleEnrolled.add(person); // this will work since Person overrides equals and hashCode
                startingBalances.put(person, balance); // no merge needed since we enforce that they have not been added to this map at a preceding time
                return newSavings;
            }
            public void getTransferredAmounts() {
                transferredTimes.forEach((amount, times) -> {
                    System.out.println(String.format("%s has transferred $%.2f %d times", this.person.firstName, amount, times));
                });
            }
            public String getCurrentInterest(int months) {
                if (api < 0 || api > 0.99) { api = 0.23; System.out.println("Since the api rate was either below 0 or above 1 at instantiation, we set it to 23 percent for you");}
                System.out.println(String.format("Balance before interest: $%.2f", balance));
                balance *= (Math.pow(1 + (api /12), 12));
                return String.format("Balance after interest applied: $%.2f", balance);
            }

            @Override
            public boolean deposit(double amount) {
                if (amount < 0) { System.out.println("If you are trying to perform a withdrawal, please use the respective method."); return false; }
                if (amount > person.cashOnHand) { System.out.println("You cannot deposit more than you have");return false; } System.out.println(this.person.giveMoney(amount));
                balance += amount; System.out.println(String.format("Succesfully deposited $%.2f, new balance is $%.2f", amount, balance));
                depositOccurrences.merge(amount, 1, (o, n) -> o +n); depositFrequency.merge(this.person, 1, (o, n) -> o + n);
                return true;
            }
            @Override
            public boolean withdraw(double amount) {
                if (amount > balance) { System.out.println("We do not allow wihtdrawals greater than your current balance on a Savings account."); return false; }
                System.out.println(this.person.recieveMoney(amount)); balance -= amount; 
                System.out.println(String.format("Succesfully withdrew $%.2f, new balance is $%.2f", amount, balance));
                withdrawalOccurrences.merge(amount, 1, (o, n) -> o +n); withdrawalFrequency.merge(this.person, 1, (o, n) -> o + n);
                return true;
            }
            public void getDepositHistory() {
                depositOccurrences.forEach((amount, times) -> {
                    System.out.println(String.format("%s deposited $%.2f %d times", this.person.firstName, amount, times));
                });
            }
            public void getWithdrawalHistory() {
                withdrawalOccurrences.forEach((amount, times) -> {
                    System.out.println(String.format("%s withdrew $%.2f %d times", this.person.firstName, amount, times));
                });
            }
            @Override
            public boolean equals(Object other) {
                // equals and hashCode is used for anything that places an obj into a hash such as HashSet/Map
                if (this == other) { return true; }
                if (!(other instanceof SavingsAccount)) { return false; }
                SavingsAccount otherSA = (SavingsAccount) other;
                return this.AccountNumber.equals(otherSA.AccountNumber);
            }
            @Override
            public int hashCode() { return Objects.hash(AccountNumber); }
            @Override
            public int compareTo(SavingsAccount other) {
                return this.AccountNumber.compareTo(other.AccountNumber);
            }
        }

        // Exrecise 2 - Build a small competive tournament bracket system
        // Lean on List/ArrayList as a first-class structural piece this time
        //      something that genuinely needs ordered, indexed access ( not just iteration )
        // and at least one Collections utility methods doing real work beyond a single demonstration call
        // Design it your way, your classes, hierarchy, your choise of waht needs equals/hashCode/Comparable
        // so since I need to recreate a game of some sort and have rounds that means I need to have an even number of players
        // so we can implement a Player obj with name, wins, losses
        // a Game class that has a prop AL for all players and another for the winning and losing
        private static class Player {
            String name; int specialNumber;
            int wins; int lossess;
            private Player(String name, int wins, int losses, int specialNumber) {
                this.name = name;
                this.wins = wins;
                this.lossess = losses; this.specialNumber = specialNumber;
            }
            public Player(String name, int specialNumber) {
                this(name, 0, 0, specialNumber);
            }
            
            public void noteWin() { this.wins += 1; }
            public void noteLoss() { this.lossess += 1; }

            @Override
            public boolean equals(Object other) {
                if (this == other) { return true; }
                if (!(other instanceof Player)) { return false; }
                Player otherPlayer = (Player) other;
                return this.name.equals(otherPlayer.name);
            }
            @Override
            public int hashCode() { return Objects.hash(name); }
        }
        public static class Game {
            static List<Player> winners = new ArrayList<>();
            static List<Player> losers = new ArrayList<>();

            List<Player> players = new ArrayList<>();
            private Game(ArrayList<Player> players) { this.players = players; }
            public static Game createGame(ArrayList<Player> players) {
                if (players.size() % 2 > 0) { System.out.println("Make sure the number of players is even"); return null;}
                return new Game(players);
            }

            public void addPlayer(String name, int specialNumber) {
                Player newPlayer = new Player(name, specialNumber);
                if (players.contains(newPlayer)) { System.out.println("There's already a player with that name"); return; }
                players.add(newPlayer);
            }
            private Player determineLoser(Player leftPlayer, Player rightPlayer) {
                int leftSNLength = String.valueOf(leftPlayer.specialNumber).length();
                int rightSNLength = String.valueOf(rightPlayer.specialNumber).length();
                
                int leftNameLength = String.valueOf(leftPlayer.name).length();
                int righNameLength = String.valueOf(rightPlayer.name).length();

                boolean longestSN = leftSNLength > rightSNLength; // if true then left else then right
                boolean longestName = leftNameLength > righNameLength;
                
                // so we have a 2 way win which only winner can have both
                return (longestSN && longestName) ? rightPlayer : leftPlayer;
            }
            private void initiateMatch(List<Player> players) {
               for (int left = 0; left < players.size() - 1;  left += 2) {
                int right = left + 1;
                Player leftPlayer = players.get(left);
                Player rightPlayer = players.get(right);
                System.out.println(String.format("Round between  %s and %s is happening now.", leftPlayer.name, rightPlayer.name));

                // the method for the winner needs to return player to remove
                // since determineLoser returns loser we can remove it from players, add into losers
                if (determineLoser(leftPlayer, rightPlayer).equals(leftPlayer)) {
                    System.out.println(String.format("%s lost this round, adding to the losers list and adding %s to the winners list", leftPlayer.name, rightPlayer.name));

                    players.remove(players.indexOf(leftPlayer)); players.remove(players.indexOf(rightPlayer)); losers.add(leftPlayer); winners.add(rightPlayer);
                    leftPlayer.noteLoss(); rightPlayer.noteWin();
                } else {
                    players.remove(players.indexOf(rightPlayer)); players.remove(players.indexOf(leftPlayer)); losers.add(rightPlayer); winners.add(leftPlayer);
                    leftPlayer.noteWin(); rightPlayer.noteLoss();
                    System.out.println(String.format("%s lost this round, adding to the losers list and adding %s to the winners list", rightPlayer.name, leftPlayer.name));
                }
               }
            }
            public void playGame() {
                do {
                    initiateMatch(this.players);
                    System.out.println(String.format("SIZES:%n Winners: %d%n Losers: %d%n Players: %d", winners.size(), losers.size(), this.players.size()));
                } while (players.size() != 0);

                if (winners.size() >= 2 && winners.size() % 2 == 0) {
                    do {
                        initiateMatch(winners);
                    } while (winners.size() != 1);
                }
                winners.forEach((player) -> System.out.println(String.format("Winner %s has now won %d times and has lost %d times", player.name, player.wins, player.lossess)));
                losers.forEach((player) -> System.out.println(String.format("Loser %s has lost %d times and has won %d times", player.name, player.lossess, player.wins)));
                Player mostWins = Collections.max(winners, new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) { return Integer.compare(p1.wins, p2.wins); }
                });
                System.out.println(String.format("%S has won the most, has won %d games", mostWins.name, mostWins.wins));
            }
        }

    // Exercise 3 of 5
    // Build a small warehouse inventory system with shipment tracking
    //      items moving in and out of storage locations
    //      some kind of hierarchy or capability split
    //          design yourself (which classes, whether abs or intf)
    // First things first; shipment should rely on FIFO wich means either LHM, List, Set thatll be the first index whilst LHM is non-assertion-mode
    // shipments shall have weight, destination, origin, distance form orgin-destination to sort into a warehouse dept such as heavy shipments
    public static abstract class FirstAndThirdParties {
        boolean isSender; String firstName; String lastName; int IDNumber; ShipmentPackage pkg;
        public FirstAndThirdParties(boolean isSender, String firstName, String lastName, int IDNumber) {
            this.isSender = isSender; this.firstName = firstName; this.lastName = lastName; this.IDNumber = IDNumber; this.pkg = null;
        }
        void assingPackage(ShipmentPackage pkg) { this.pkg = pkg; }
        String getSummaryOfPerson() {
            return String.format("%s%nName: %s %s%nID Number: %s%nPackage Information: %n%s", isSender ? "Sender's Information" : "Receiver's Information", firstName, lastName, IDNumber, pkg.getPackageSummary());
        }
    }
    public static class PackageSender extends FirstAndThirdParties {
        private PackageSender(boolean isSender, String firstName, String lastName, int IDNumber) {
            super(true, firstName, lastName, IDNumber);
        }
        public static PackageSender createSender(String firstName, String lastName, int IDNumber) {
            if (firstName == null || lastName == null) { System.out.println("Either the first/last name or the package associated with this person didn't contstruct properly"); return null; }
            if (String.valueOf(IDNumber).length() != 7) { System.out.println("The length of the ID number must be exactly 7 digits"); return null; }
            if (!String.valueOf(IDNumber).startsWith("493")) { System.out.println("The ID number must start with 493"); return null; }
            return new PackageSender(true, firstName, lastName, IDNumber);
        }
    }
    public static class PackageReceiver extends FirstAndThirdParties {
        private PackageReceiver(boolean isSender, String firstName, String lastName, int IDNumber) {
            super(false, firstName, lastName, IDNumber);
        }
        public static PackageReceiver createReceiver(String firstName, String lastName, int IDNumber) {
            if (firstName == null || lastName == null) { System.out.println("Either the first/last name or the package associated with this person didn't contstruct properly"); return null; }
            if (String.valueOf(IDNumber).length() != 7) { System.out.println("The length of the ID number must be exactly 7 digits"); return null; }
            if (!String.valueOf(IDNumber).startsWith("493")) { System.out.println("The ID number must start with 493"); return null; }
            return new PackageReceiver(false, firstName, lastName, IDNumber);
        }
    }
    public static abstract class Shippable {
        String tagNumber; String trackingNumber; String origin; String destination;
        double distanceToDest; String type; FirstAndThirdParties sentFrom; FirstAndThirdParties personToReceive;

        public Shippable(String tagNumber, String trackingNumber, String origin, String destination, double distanceToDest, String type, FirstAndThirdParties sentFrom, FirstAndThirdParties personToReceive) {
            this.tagNumber = tagNumber; this.trackingNumber = trackingNumber; this.origin = origin; this.destination = destination;
            this.distanceToDest = distanceToDest; this.type = type; this.sentFrom = sentFrom; this.personToReceive = personToReceive;
        }
        String getPackageSummary() {
            return String.format("SHIPMENT INFORMATION OF :%nTagNumber: %s%nTrackingNumber:%s%nOrigin: %s%nDestination: %s%nDistance To Destination: %.2f miles%nShipment Type: %s%nSent From: %s%nWill Be Received By: %s", tagNumber, trackingNumber, origin, destination, distanceToDest, type, sentFrom.firstName, personToReceive.firstName);
        }
    }
    interface VerifiedPackage {
        static boolean verifyPackage(String tagNumber, String trackingNumber, String origin, String destination, double distanceToDest, String type, FirstAndThirdParties sentFrom, FirstAndThirdParties personToReceive) {
            if (tagNumber == null || trackingNumber == null || origin == null || destination == null || type == null || sentFrom == null || personToReceive == null) {
                System.out.println("One of the fields were invalid. Please check and resubmit"); return false;
            }
            if (!tagNumber.startsWith("SHP")) { System.out.println("TagNumber must start with SHP"); return false; }
            if (!trackingNumber.startsWith("1A76B001")) { System.out.println("TrackingNumber must start with 1A76B001"); return false;}
            if (origin.length() == 2 || origin.length() == 3) { System.out.println("Please write the origin completely such as El Paso, Texas for example"); return false; }
            if (destination.length() == 2 || destination.length() == 3) { System.out.println("Please write the destination completely such as Fort Worth, Texas for example"); return false; }
            if (distanceToDest < 0) { System.out.println(String.format("The value %.2f cannot be negative for the distance to the destination", distanceToDest)); return false; }
            return true;
        }
    }
    private static class ShipmentPackage extends Shippable implements Comparable<ShipmentPackage> {
        private ShipmentPackage(String tagNumber, String trackingNumber, String origin, String destination, double distanceToDest, String type, FirstAndThirdParties sentFrom, FirstAndThirdParties personToReceive) {
            super(tagNumber, trackingNumber, origin, destination, distanceToDest, type, sentFrom, personToReceive);
        }
        
        @Override
        public boolean equals(Object other) {
            if (this == other) { return true; }
            if (!(other instanceof ShipmentPackage)) { return false; }
            ShipmentPackage otherPackage = (ShipmentPackage) other;
            return this.tagNumber.equals(otherPackage.tagNumber);
        }
        @Override
        public int hashCode() { return Objects.hash(tagNumber); }

        @Override
        public int compareTo(ShipmentPackage other) {
            return this.tagNumber.compareTo(other.tagNumber);
        }
    }
    public static class Warehouse {
        PackageMaker orderRetriever;
        public Warehouse(PackageMaker orderRetriever) { this.orderRetriever = orderRetriever; }
        TreeMap<ShipmentPackage, Double> sortedByDistance = new TreeMap<>(
            Comparator.comparingDouble((ShipmentPackage p) -> p.distanceToDest).thenComparing(p -> p.tagNumber)
        );

        public void requestOrders(int amount) {
            // first call func to retrivee the AL of packages needed to sort
            // since we need to sort, if we sort based on distance and weight that means that one or more
            // shipments can be in two different locations at the same time which is not possible
            // however, they are overall organized by FIFO or insertion order with removing lru's
            // considering this, the most feasible way to sort these would be by distance
            ArrayList<ShipmentPackage> retrievedOrders = orderRetriever.sendPkgsToWarehouse(amount);
            retrievedOrders.forEach((p) -> sortedByDistance.put(p, p.distanceToDest));
        }
        public void processOrders(int amount) {
            int limit = Math.min(amount, sortedByDistance.size());
            ArrayList<ShipmentPackage> processing = new ArrayList<>();
            for (Map.Entry<ShipmentPackage, Double> entry: sortedByDistance.entrySet()) {
                if (processing.size() == limit) { break; }
                processing.add(entry.getKey());
            }
            ShipmentPackage furthest = Collections.max(processing, Comparator.comparingDouble(p -> p.distanceToDest));
            System.out.println(String.format("Furthest Package Description:%n Senders Summary:%n%s Package Summary:%n%s", furthest.sentFrom.getSummaryOfPerson() ,furthest.getPackageSummary() ));
            processing.forEach((p) -> System.out.println(p.getPackageSummary()));
        }
    }
    public static class PackageMaker implements VerifiedPackage {
        ArrayList<ShipmentPackage> packages = new ArrayList<>();
        public PackageMaker() {}

        // since this class makes methods or in other words accepts orders, we store these into packages in order to send to the warehouse so the warehouse needs to be a prop
        // also since the Shipment static method uses a verifier, it is guaranteed that the obj passed into here will not be accepted
        public void acceptOrder(String tagNumber, String trackingNumber, String origin, String destination, double distanceToDest, String type, FirstAndThirdParties sentFrom, FirstAndThirdParties personToReceive) {
            if (VerifiedPackage.verifyPackage(tagNumber, trackingNumber, origin, destination, distanceToDest, type, sentFrom, personToReceive)) {
                // since the packages are going to be compared and also sorted they need to implement equals, hc, and compareTo
                // saying this right here because i do need to make sure there arent any dupes and .contains uses .equals whenever iterating over the List
                // that is now complete, now I can check that if the AL already has it, its based on tagNumber
                ShipmentPackage newPkg = new ShipmentPackage(tagNumber, trackingNumber, origin, destination, distanceToDest, type, sentFrom, personToReceive);
                if (packages.contains(newPkg)) { System.out.println("This package has already been added, if the other information (not counting the tagNumber) is different than a previous version, please change the tag number"); return; } else { sentFrom.assingPackage(newPkg); personToReceive.assingPackage(newPkg); packages.add(newPkg); }
            } else { System.out.println("Oopsies...Something wrong happened trying to make this package. Call this method again but correctly :)"); }
        }

        // so now we have accepted valid packages in this objs prop of the AL, now its time to send it to the warehouse
        // this class does what its called, it just accepts orders and the wh has teh responsibility of sorting etc
        public ArrayList<ShipmentPackage> sendPkgsToWarehouse(int amount) {
            // so since the warehouse owns an instance of this class itll call this method in that case itll be its responsibility to add
            // the packages to its whatever it has which means that this method needs to return a list of that # of paackages
            // make sure we send a valid amount to return
            int limit = Math.min(amount, packages.size());
            List<ShipmentPackage> sublistView = packages.subList(0, limit);
            ArrayList<ShipmentPackage> packagesToSend = new ArrayList<>(sublistView);
            sublistView.clear(); // this removes the packages from the "this"'s AL of packages
            return packagesToSend;
        }
    }
}
