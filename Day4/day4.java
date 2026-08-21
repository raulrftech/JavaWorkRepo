package Day4;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Map;

public class day4 {
    
    public static void main(String[] args) {
        System.out.println("Found with regular throws"); foundWReg("eastWing"); foundWReg("whoKnows");
        System.out.println("Found with custom implementation"); foundWCustom("westWing"); foundWCustom("idkEither");
    }

    // Optional <T> - full picture
    // Optional<T> is a container type repsenting "a value that might or might not be present", used specifically as an explicit alternative to returning or storing raw null
    // The problem it solves:
    //      null can hide anywhere a reference type is expected, with zero compile-time warning, and the only way you find out is through a NullPointerException
    // Optional forces the "might be absent" case to be visible in the type itself
    // Creating one:
    //      Optional.of(value) - wraps a value yorue certain isnt null, throws immediately if youre wrong
    //      Optional.ofNullable(value) - safely wraps something that might genuinely be null, producing an empty Optional if it is
    //      Optional.empty() - explicitly represent "nothing here"
    // The full method set
    //      .isPresent/.isEmpty - boolean checks
    //      .get() - the dangerous one, direct equivalent of Swifts force-unwraps !; throws NoSuchElementException if empty
    //          Avoid using this directly, same as avoiding ! in Swift
    //      .orElse(default) - returns the value or a fallback if empty
    //      .orElseGet(supplier) - same idea, but the fallback is computed lazily via a lambda, only if actually needed
    //      .orElseThrow() - returns the value or throws
    //      .map(function) - transforms the value if present otherwise stays empty
    //      .filter(predicate) - keeps the value only if it satisfies a condition, otherwise becomes empty
    // The two patterns that actually map to Swift's optional-unwrapping syntax
    //      .ifPresent(consumer) is the real "if let" equivalent - a lambda that only runs if a value exists, receiving the unwrapped value directly inside
    //          Optional<String> name = Optional.ofNullable(getSomeName());
    //          name.ifPresent(unwrapped -> {
    //              System.out.println(got unwrapped); });
    //          Exactly if let unwrapped = name{ print(unwrapped)} in Swift, just as a lambda scope instead of an if block
    //      .ifPresentOrElse(consumer, runnable) is "if let/else"
    //          name.ifPresentOrElse(unwrapped -> System.out.println(got unwrapped), () -> System.out.println(nothing here))
    //      But heres the thing worth naming precisely
    //          ifPresent's lambda scoped shape doesnt map to what occurred in Day 3 with "guard let" implementations
    //      The actual Java equivalent of guard let is simpler than a new method its just if (optional.isEmpty()) { return } String value = optional.get(); // safe now since it passed the if statement
    // One real caution: Optional is meant to be used as a method return type, singalling to callers "check before you use this" is considered bad practice to use it as a class git pfield type or a method param type
    //      since it adds wrapping overhead without the same benefit.
    //      Keep it at return boundaries, not baked into data models


    // Intro Exc 1 - Optional.of() and Optional.ofNullable(), the creation methods, constrasted directly
    // Build a method that takes String which might be null (the callers choice) and demonstrates the actual difference between the two creation methods
    // Attempt to wrap it with Optional.of() inside a try/catch, proving it throws when the value is genuinely null
    // Then wrap the same possibly null value with Optional.ofNullable(), proving it safely produces an empty Optional instead of crashing
    // Call this with both a real string and a null, showing all four outcomes
    public static void checkValue(String of) {
        try { Optional<String> wrapped = Optional.of(of); System.out.println(String.format("Optional of succeeded: %s", wrapped.get()));} catch (NullPointerException e) {
            System.out.println("Optional.of threw" + e);
        }
        // this performs an internal null check itslef, and if the value is null, it throws NullPointerException immediately
        //      right there at the point of wrapping, rather than letting a null slip inside the Optional silently
        // THis is intentional; Optional.of() exists for situations where youre certain a value exists and you want an early, loud, failure if that certainty is wrong
        Optional<String> safe = Optional.ofNullable(of);
        System.out.println("Optional.ofNullable produced" + safe);
        // this performs the same null check internally, but instead of throwing, it produces a genuinely empty Optional when the value is null, just safe.isPresent() returning false
    }
    // Intro Exc 2 modeled below
    public static Optional<String> findUser(String id) {
        TreeMap<String, String> users = new TreeMap<>(Map.of("key1", "val1", "key2", "val2"));
        if (!users.containsKey(id)) { return Optional.empty(); }
        return Optional.of("Found " + id);
    }
    // Exercise 2 - isPresent() and isEmpty(), the two direct boolean checks
    // .isPresent() returns true if a value exists, false if empty
    // .isEmpty() (added later to Java, the more modern, arguably more readable choice) returns the exact oppsoite - true if empty, otherwise flase
    // Theyre direct inverses of each other; pick whichever reads more naturally for a given if condition
    // Build a method that takes an Optional<Integer> represent an account balance that might not exist yet (a new acc with no balance set)
    // Using .isPresent or .isEmpty directly in if conditions (not .get() blindly), print a different message depending on whether the balance exists and if it does, safely retrieve and display it
    // Test with a populated and an empty Optional<Integer>
    public static String checkExistingBalance(Optional<Integer> bal) {
        if (bal.isPresent()) { return String.format("The balance $%d exists", bal.get()); }
        if (bal.isEmpty()) { return "The balance given is empty; doesnt hold a value"; } else { return String.format("Checked if balance is not empty and it isnt. Balance is $%d", bal.get()); }
    }

    // Guard-let equivalent - .isEmpty() with early return, then safe unwrapped use for the rest of the method
    // This is the Java shape that actually mirrors Swift's quar let value = optional else { return } most direclty
    //      check for absence, exit immediately if so, and treat everything after that check as guarenteed-safe territory, no repeated null-checking needed for the rest of the method body
    // Sample:
    public static String processBlaance(Optional<Integer> bal) {
        if (bal.isEmpty()) { return "No balance on file"; }
        int value = bal.get(); // safe here - guarenteed present past this point
        // freely use value for the rest of the method, no further checks are needed
        return String.format("Processing balance of $%d", value);
    }
    // The key structural idea, once the if (bal.isEmpty()) { return; } line has run and not exited, you have a hard guarantee for the rest of the method that bal holds a real value
    //      valling.get() after that point is genuinely safe, not a risky force-unwrap, because the early return already eliminated the only case where it could fail
    // Exercise 3
    // Build a method using this exact guard-let shape (not .isPresent() with the logic inverted, genuinely the "check for absence, return early" structure) that takes an Optional<String>
    //      representing a customer's promo code, which might not exist
    // If absent, return immediately with a message
    // If present, do multiple things with the unwrapped value afterward proving the value stays safely usable for several subsequent lines, not just a isngle immediate use right after unwrapping
    public static String checkPromoCode(Optional<String> possibleCode) {
        if (possibleCode.isEmpty()) { return "Invalid code";}
        String validCode = possibleCode.get();
        System.out.println(String.format("%s is %d characters long", validCode, validCode.length()));
        int numberCount = 0; int letterCount = 0;
        for (int i = 0; i < validCode.length(); i++) {
            char c = validCode.charAt(i);
            if (Character.isLetter(c)) { letterCount++;} else if (Character.isDigit(c)) { numberCount++;}
        }
        return String.format("%s contains %d numbers and %d letters", validCode, numberCount, letterCount);
    }

    // .orElse(), .orElseGet(), .orElseThrow() - full explanation before the exercise, since these three look similar but behave meaningfully differently
    // .orElse(fallbackValue) - returns the wrapped value if present, otherwise returns whatever fallback you provide
    //      Critical detail worth understanding precisely: the fallback value is always computed, every single time this line runs, regardless of whether its actually needed
    //      If your fallback is a cheap literal (0, ""), thats irrelevant
    //      But if computing the fallback is expensive ( a database call, a heavy calculation), .orElse() pays that cost unconditionally, 
    //          even when the Optional already has a value and the fallback will just be thrown away
    // .orElseGet(supplier) - same job but the fallback is a lambda, only actually executed if the Optional is genuinely empty
    //      This is the "lazy" version - if a value is present, the supplier lambda never runs at all, avoiding wasted work
    // int a = someOptional.orElse(computeExpensiveDefault()); // computeExpensiveDefault() ALWAYS runs
    // int b = someOptional.orElseGet(() -> computeExpensiveDefault()); // only runs if someOptional is empty
    // .orElseThrow() - returns the value if present, or throws an exception if not
    //      Called with a lambda supplying a custom exception, it thorws that instead - letting you produce a meaningful, speciifc error rather than Javas generic default
    // Example
    public static int computeDefault() {return 0;}
    public static int getScore(Optional<Integer> score) {
        int viaOrElse = score.orElse(computeDefault());
        int viaOrElseGet = score.orElseGet(() -> computeDefault());
        return viaOrElseGet;
    }
    // An .orElseGet() lambda specifically cant take arguments the way a general lambda might elsewhere
    //      its constrained by what Supplier<T> (the functional interface .orElseGet() expects) requires: 
    //          zero parameters in, one value out
    //      The empty () isnt optional or stylistic, its mandatory for this specific use
    // But you can still use external values inside the lambdas body, captured from the surrounding scope - same effectively-final catpure rules from before
    public static int getScoreWithFallback(Optional<Integer> score, int defaultBase, int bonus) {
        return score.orElseGet(() -> {
            int computed = defaultBase + bonus;
            System.out.println("Computing fallback: " + computed); return computed;
        });
    }
    // defaultBase and bonus are genuine method params, captured and used freely isnide the lambdas body, but the lambdas own parameter list, (), stays empty, because Supplier<T> itslef never
    //      hands the lambda anything to work with; it only ever expects a value handed back
    // If you needed a lambda that actually receives an argument (like transforming an existing value) that would be a different functional interface entirely - Function<T, R> which .map() uses
    // Exercise 1 (Starting Exc Partition 1-10 working with Optional.orElse methods) - .orElse, Get, Throw
    // Build a small user-lookup system. A method takes a userID and returns Optional<String> )the username, or empty if not found)
    //      some ID exist, some dont, your call on the lookup logic
    // Using the result of this lookup, build three separate methods demonstrating each fallback strategy
    //      One using .orElse() with a fallback value computed by a separate helper method that prints something distinctive whenever it runs
    //          call this with both a found and not found ID, confirm the helper mehtods print statement fires both times, proving eager evaluation
    //      Antoher using .orElseGet() with the same helper method wrapped in a lambda - call this with both a found and not found ID, cofirm the hlpers statement
    //          fires only on hte not found case prvoing lazy evaluation, the actual contrast this exercise needs
    //      Lastly, one using .orElseThrow() with a custom exception carrying a message that includes the specific ID that wasnt found - call it with a not found ID inside a try/catch and print caught exceptions message
    public static Optional<String> userLookup(String userID) {
        if (userID.isEmpty()) { return Optional.empty(); }
        int lengthOf = userID.length();
        int letterAOcc = 0;
        for (int i = 0; i < userID.length(); i++) {
            char c = userID.charAt(i);
            if (c == 'a') { letterAOcc++; }
        }
        if (letterAOcc % 2 == 0 && lengthOf % 2 == 0) { return Optional.of(String.format("%d%s", letterAOcc, userID)); } else { return Optional.empty(); }
    }
    public static String uidNF() { System.out.println("uidNF() ran"); return String.format("User not found %d", -1); }
    public static void foundUser(Optional<String> lookupResult) {
        String result = lookupResult.orElse(uidNF());
        if (result.equals(uidNF()) == false) { System.out.println(String.format("Confirmed username exists: %s", lookupResult)); } else {
            System.out.println("No username associated with the ID provided");
        }
    }
    public static void foundWGet(Optional<String> lookupResult) {
        String validID = lookupResult.orElseGet(() -> uidNF());
        if (validID.equals(uidNF()) == false) { System.out.println(String.format("Confirmed username exists: %s",lookupResult)); } else {
            System.out.println("No username associated with the ID provided");
        }
    }
    public static void foundWThrow(Optional<String> lookupResult) {
        try {
            String value = lookupResult.orElseThrow(() -> new IllegalArgumentException("Return value is emtpy"));
            System.out.println("Value found: " + value);
        } catch (IllegalArgumentException e) { System.out.println("Caught Exception: " + e); e.getCause(); }
    }
    // Exercise 2 - .orElse, .orElseGet, on a Numeric Optional
    // Build a method returning Optional<Integer> representing a studnets exam score, empty if the student never took the exam
    // Build two consumer methods
    //      One using .orElse() with a fallback computed by a helper method (printing when it runs)
    //      Another using .orElseGet() with the same helper wrapped in a lambda
    // Test both against a student who too the exam and one who didnt, priving the same eager vs lazy contrast with fresh data
    // Two questions before building
    //      what is .orElse() doing, precisely in terms of when its argument gets evaluated
    //          If the Optional<Integer> is empty then it returns the fallback value, if it isnt, the fallback value is still computed but is not assigned to the var
    //      what is .orElseGet() doing differently, and why does that difference matter if the fallback computation were expensive
    //          What this method does is only running if the Optional<Integer> were to be empty, checks if it is, if it is then runs if else then returns wrapped value of Integer
    //          It matters because it avoids expensive computation and designates this use case to only if the Optional were to be empty
    public static Integer ScoreNotFound(String name) { System.out.println(String.format("ScoreNotFound(%s) ran", name)); return -1; }
    public static Optional<Integer> examLookup(String studentName) {
        if ((studentName.length() -1) % 2 == 0) { return Optional.of(studentName.length() * 3); } else {
            return Optional.empty();
        }
    }
    public static void foundScoreWElse(String studentName) {
        Optional<Integer> optionalScore = examLookup(studentName);
        Integer wrappedValue = optionalScore.orElse(ScoreNotFound(studentName));
        if (wrappedValue != -1) { System.out.println(String.format("%s scored a %d", studentName, wrappedValue));} else { System.out.println(String.format("No score found for %s", studentName)); }
        // optionalScore is either empty or contains the score so the wrappedValue either gets assigned -1 or the students valid score
        // if statement doesnt run ScoreNotFound again since wrappedValue gets assigned either return of -1 or studentsScore
        // thus it is save to use wrappedValue in the formatted string
    }
    public static void foundScoreWGet(String studentName) {
        Optional<Integer> optionalScore = examLookup(studentName);
        if (optionalScore.orElseGet(() -> ScoreNotFound(studentName)) != -1) { System.out.println(String.format("%s scored a %d", studentName, optionalScore.get()));} else { System.out.println(String.format("No score found for %s", studentName)); }
        // similar body as above but no reason to assign the return value of orElseGet to a var since the return value of the lambda is only returned only if the optionalScore is empty
    }
    // Exercise 3 - .orElseThrow(), Two Variants
    // Build a method returning Optional<Stirng> representing a warehouse item's storage location
    //      empty if the item isnt in inventory at all
    // Build two consumer methods
    //      One using .orElseThrow() with no arguments (the generic no such element exception)
    //      One using .orElseThrow() with a lambda supplying a custom exception carrying a message that icnludes the specific item name that wasnt found
    // Test both against a missing item isnide try/catch, printing the caught exception in each case and confirm the two exception types/messages are different
    // Two questions before building
    //      What does .orElseThrow() with no arguments actually throw and wehre does Java decide what that exception's message says,given no message is supplied
    //          the no arguments variant is default so it supplies whatever javas internal methods find such as that nosuchelementexception
    //      What does the lambda-argument varient of orElseThrow let you do that the no argument version genuinely cant
    //          it lets you make a custom exception which is a class that extends Exception and uses super(message) which has to be of type String
    public static Optional<String> findLocation(String of) {
        if (of.startsWith("east") || of.startsWith("north") || of.startsWith("west") || of.startsWith("south")) { return Optional.of(String.format("Found %s", of));} else { return Optional.empty(); }
    }
    public static void foundWReg(String item) {
        Optional<String> optionalResult = findLocation(item);
        try {
            System.out.println(optionalResult.orElseThrow());
        } catch (Exception e) { System.out.println(e.getMessage());}
    }
    public static class ItemNotFound extends Exception {
        private final int errorCode; private final String item;
        public ItemNotFound(String message, int errorCode, String item) { super(String.format("Error %d! Item %s not found", errorCode, item)); this.errorCode = errorCode; this.item = item; }
    }
    public static void foundWCustom(String item) {
        Optional<String> optionalResult = findLocation(item);
        try {
            String found = optionalResult.orElseThrow(() -> new ItemNotFound("Missing Item", 404, item)); // this message value gets overridden by what is passed into super in the class dec
            System.out.println(String.format("Found item %s", found));
        } catch (ItemNotFound e) { System.out.println(e.getMessage()); }
    }
}
