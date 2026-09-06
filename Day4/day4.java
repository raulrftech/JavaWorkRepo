package Day4;
import java.util.Optional; import java.util.TreeMap; import java.util.TreeSet;
import java.util.Scanner; import java.io.File; import java.util.HashMap;
import java.util.LinkedHashMap; import java.util.Map; import java.util.Objects;
import java.io.FileNotFoundException; import java.util.Random;


public class day4 {
    
    public static void main(String[] args) {
        Scanner newAccScanner = new Scanner(System.in);
        BankAccount_Enhanced newAccount = BankAccount_Enhanced.createBankAccount(newAccScanner);
        newAccount.deposit(); newAccount.withdraw();
    }

    // Optional <T> - full picture
    // Optional<T> is a container type repsenting "a value that might or might not be present", used specifically as an explicit alternative to returning or storing raw null
    // The problem it solves:
    //      null can hide anywhere a reference type is expected, with zero compile-time warning, and the only way you find out is through a NullPointerException
    // Optional forces the "might be absent" case to be visible in the type itself
    // Creating one:
    //      Optional.of(value) - wraps a value yorue certain isnt null, throws immediately if youre wrong
    //      Optional.ofNullable(value) - safely wraps something that might genuinely be null, producing an empty Optional if it is
    //      Optional.empty() - explicitly represents "nothing here"
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
    // Exercise 4 - All Three .orElse Variants in One Method, Layered Fallback Chain
    // This is the actual escalation - isntead of three separate consumer methods each demonstrating one strategy
    //      build one method that genuinely needds all three working together, not just sitting side by side
    // Build a small configuration-resolution system:
    //      a setting might be found in a user-specific override (Optional<String>) and if not there, checked against a system-default (also Optional<String>, itself possibly absent) and if neither exists
    //          the request shoud fail loudly rather than silently fall back to some arbitrary value
    // Design the methods actual logic: check the user override first, if present, use it directly, if not, fall back to chekcing the system default, but do this check lazily
    //      only actually look up the system default if the user override was genuinely missing, using .orElseGet() with a lambda that itself performs the system default lookup
    //          If this is also empty, .orElseThrow() with a custome xception naming which setting couldnt be resolved at all
    // Test three scenarios: user override present (system default should never even be consulted - prove this with a print statement inside the system-defaul lokkup that shouldnt fire)
    //      user override absent but system default present
    //      and both absent, throwing and catching the custom exception
    // Two questions before building:
    //      Given the method needs to chain user override -> system default -> throw, why does .orElseGet() specifically have to be the tool connecting the first two steps, rather than .orElse()
    //          what would break, or what would silently happen wrong, if you used .orElse() there instead
    //              so the main method would take the parameter of an Optional<String>, we can perform orElseGet on it with calling the systemDefault in the lambda so that if it isnt present the systemDefault is supplied
    //              but since we need to use orElseThrow with the custom exception so that in case the systemDefault doesnt exist, it fails loudly
    //      Can .orElseGet's lambda itself contain a second Optional lookup with its own .orElseThrow() chained onto it, or does that kind of nesting run into any real problem
    //          so the orElseGet lambda calls a function and orElseGet() is unlike ifPresent in which the unwrapped value is usable within its lambda but since the fucntion that is called can return an Optional<String>
    //          such checking settingName supplied then that can but if that is the case then all this would need ot be within a try/catch
    //      Product
    //          After testing with "Setting1" and "Seting1", both cases are verified where Setting1 returns Menu1 which the return value from userOverride which was set to uo
    //              then was checked if it was empty with systemDefault(uo) and if that was empty was well which is correct because an incorrect name supplied to userOverride propagated an empty value to systemDefault
    //              which then propagated its own returned empty value that was then checked by orElseThrow which would throw the exception if it was in fact empty
    public static Optional<String> userOverride(String settingName) {
        HashMap<String, String> settings = new HashMap<>(Map.of("Setting1", "Menu1"));
        if (settingName.isEmpty() || !settings.containsKey(settingName)) { return Optional.empty(); } else { return Optional.of(settings.get(settingName));}
    }
    public static Optional<String> systemDefault(String SD) {
        HashMap<String, String> systemDefaults = new HashMap<>(Map.of("General", "Various General Settings", "Cellular", "Data Usage"));
        if (!systemDefaults.containsKey(SD)) { return Optional.empty();} else { return Optional.of(systemDefaults.get(SD)); }
    }
    public static void checkOverride(String settingName) {
        if (settingName.isEmpty()) { System.out.println("Please input a setting name to run this method"); return; }
        Optional<String> uo = userOverride(settingName);
        try {
            System.out.println(uo.orElseGet(() -> systemDefault(settingName).orElseThrow()));
        } catch (Exception e) { System.out.println(e.getMessage()); }
    }
    // Exercise 5 - .map() chained with .orElse(), Transforming Before Falling back
    // Everything so far has used the raw wrapped value directly. This exercise introduced .map() - a method that transforms the value inside an Optional if present
    //      leaving it untouched (still empty) if not
    // Full mechanics before building: 
    //      .map(function) takes a lambda describing how to transform the wrapped value, applies it only if a value exists, and returns a new Optional wrapping the transformed result
    //          chaining.map() on an already-empty Optional is always safe and simply stays empty, no exception, no special handling needed
    //      Optional<String> name = Optional.of("Raul");
    //      Optional<Integer> length = name.map(n -> n.lgenth()) // optional[4], no unwrapping needed to transform
    // Build a small system
    //      a method returns Optional<String> representing a raw phone number that might not exist for a contact
    //      Chain .map() onto that result to transform the raw string into a formatted version (your choise of formatting; dashes, parantheses, whatever)
    //      Then chain .orElse() or .orElseGet() onto the result of the map() to supply a fallback if the original was empty
    //          proving .map() and a fallback strategy can be chained together in one fluent expression, not used as separate, disconnected steps
    // Two questions before building
    //      Why is it safe to call .map() on an Optional that might be emtpy, without needing to check .isPresent() first
    //          we havent used or gone over isPresent so this question wont be answered
    //      If the original Optional was empty, does .map() transformation lambda ever actually run at all
    //          no, it just returns empty which then the orElseGet receives an empty and it is so it conditionally runs the return value of the string depending on the return value of the map which depends on the value of the Optional<String> val
    public static Optional<String> getPhoneNumber(String contactName) {
        HashMap<String, Optional<String>> contactBook = new HashMap<>(Map.of("Martha", Optional.of("9153298148"), "Malacolm", Optional.of("9157316796"), "Karla", Optional.of("9156269967"), "Myself", Optional.of("9152164363")));
        if (!contactBook.containsKey(contactName)) { return Optional.empty(); } else { return contactBook.get(contactName); }
    }
    public static String formatPhoneNumber(String contactName) {
        return getPhoneNumber(contactName).map(num -> num.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3")).orElseGet(() -> "No phone number is associated with this contact name");
    }
    // Exercise 6 - isPrsent() as a Genuine Precondition, Not Paired with .isEmpty()
    // .isPresent() Full Mechanics
    //      returns a plain boolean, true if the Optional genuinely holds a value, false if its empty
    //      It performs a direct internal check of the Optional's state, nothing more
    //          it doesnt unwrap, doesnt transform, doesnt throw
    //          Purely a yes/no question you can safely ask before deciding whether to call something riskier like .get()
    /*
            Optional<String> maybeCode = Optional.ofNullable(getCode());
            if (maybeCode.isPresent()) { String code = maybeCode.get(); Sysoutprntln code } else { sysoutprntln no code available }
    */
   //       This is the literal translation of manually checking if x != nil in swift before force unwrapping
   //           .isPrsent() gives you the boolean and within that ture branch, calling .get() is genuinely safe, since youve just confirmed it is beforehand
   //       Why it might get chosen over .isEmpty() specifically, worht thinking through as the actual question underneath this exercise
   //           theyre logical opposites, so either can drive the same branching but which one you reach for often comes down to which case you want to read as the "main"
   //               first mentioned path
   //           if the "value exists, do the primary thing" case is the one you want front and center in your code
   //           .isPresent() as the if condition puts that case first
   //           If the "handle the missing case" logic is what you want emphasized or is genuinely the more complex branch, .isEmpty() as the leading condition puts that first isntead
   //       Its a readability/emphasis choice, not a functional one - both are always available and picking one over the other doesnt change what the code does, only how it reads
   // Build a method returning Optional<Double> representing a product's discount percentage, which might not exist for a given product
   // Using only .isPresent()  (not .isEmpty() alongside it, not a guard style early return either - genuinely just .isPresent() as the condition of one if/else), branch between
   //   "discount exits, apply it to a base price" and "no discount, use the base price unchanged"
   // This forces .isPresent() to carry the full weight o fthe branching on its own, rather than sharing the job with its inverse
   // One question before building:
   //       What is the actual difference in intent between raching for .isPresent() versus reaching for .isEmpty(), given theyre logical opposites and iether could technically do the same code
   //           the answer was given above specifally whenever you mentioned that which one I reach for depends on which case Id like to read as the main, first mentioned path. Purely a readability/emphasis choice
   // So the first thing I want to do is an hm that has k: productName, v: double<price>
    public static Optional<Double> getDiscountedPrice(String productName, Double discount) {
        HashMap<String, Optional<Double>> inventory = new HashMap<>(Map.of("bananas", Optional.of(12.5), "Phone", Optional.of(450.50), "Monitor", Optional.of(345.80), "Apple Mouse", Optional.of(120.50)));
        // before is present is used we need to confirm that the productName is there
        if (inventory.containsKey(productName)) {
            if (inventory.get(productName).isPresent() && discount > 0 && discount < 1.0) {
                return Optional.of(inventory.get(productName).get() * discount);
            } else { return Optional.empty(); }
        } else { return Optional.empty(); }
    }
    // Exercise 7 - .filter(), New Method, CHained with Everything Built So Far
    // Full explanation first, .fitler(predicate) takes a lambda returning boolean, and keeps the wrapped value only if that predicate returns true for it
    //      if the Optional is already empty, or if the predicate returns false for a rpesent value, .filter() produces an emtpy Optional either way
    //      This is the tool for "I have a value, but I only want to keep it if it also satisfies some condition" - distinct from .map() which transforms a value.
    //          .filter() only ever keeps or discards, never changes what the value actually is
    // The actual exercise, harder this time - chain .filter(), .map() and .orElseGet() together in one fluent expression, all 3 working in sequence
    // Build a method returning Optional<Integer> representing a coupon code's discount value, which might not exist
    // Chain .filter to keep it only if the discount is within some valid range (reject anything avoce a sensible cap, treating an out of range value as if it didnt exist at all)
    // Then .map() the surviving value into a formatted discount string, then .orElseGet() to supply a fallback message if either the original was empty or it got filtered out for being invalid
    // Test three cases: a valid discount that survives the whole chain, a discount that exists but gets filtered out for being out of range, and a coupon that doesnt exist at all
    //      confirm all three correctly land on the appropriate branch of the fluent chain
    public static String filterMethod(Optional<Integer> discountValue) {
        Optional<String> validValue = discountValue.filter(a -> a < 10).map(b -> String.format("Valid Discount Value: %d", b));
        return validValue.orElseGet(() -> "Invalid value");
    }
    // Exercise 8 - .ifPresent() and .ifPresentOrElse(), the Actual "if let/ if let else" Equivs
    // .ifPresent(consumer) takes a lambda that receives the unwrapped value directly inside its scope and only runs if a value exists - nothing happens at all if the Optional is emtpy, no fallback either
    // Optional<String> name = Optional.of("Raul"); name.ifPresent(unwrapped -> Sysout unwrapped)
    // .ifPresentOrElse(consumer, runnable) adds the issing half, a second lambda taking zero args that runs specifically when the Optional is empty
    // name.ifPresentOrElse(unwrapped -> Sysout SF unwrapped, () Sysout no name provided)
    // This is genuinely, literally the closest Java gets to Swifts if let or if let else - the unwrapped value lives isnide the lambdas own scope, not extracted into a variable you then use across like a guard let
    // Build a method that takes Optional<String> representing a users uploaded profile picture URL, which might not exist
    // Using .ifPresentOrElse(), print a personalized message using the unwrapped URL if present, or a message suggesting they upload one if otherwise
    // Then, separately, use plain .ifPresent() alone to demonstrate a case where genuinely doing nothing on the empty case is the correct behavior
    //      like logging an analytics even only when a value exists, where silence is the right response to absence, not an error or fallback message
    public static void checkPictureURL(Optional<String> urlOf) {
        urlOf.ifPresent(unwrapped_Valid -> System.out.println(String.format("%s is a valid url", unwrapped_Valid)));
        urlOf.ifPresentOrElse(unwrapped_Valid -> System.out.println(String.format("Successfully loaded %s for the profile picture", unwrapped_Valid)), () -> System.out.println("The url provided is incorrect and/or in the wrong format"));
    }
    // Exercise 9 - Every Optional Method COvered So Far, One Cohesive Systme
    // This is the actual synthesis test for the whole Optional arc - a single, real program requiring most of whats been built ( creation: .of, ofNullable, empty), presence checking, guard let pattern
    //      all three fallback strats; .map, .filter, .ifPresent/orElse, each used where its actually the right tool, not forced arbitrarily
    // Build a small job applicaton screening system
    //      Candidate has an Optional<Integer> years of experience and Optional<String> referral-source field both of which might be absent (not every applicant provides them)
    // Design a method that:
    //      uses guard let pattern to reject the application if years of experience is absent
    //      uses .filter() to ensure the experience value is realistic (reject negative numbers or absurdly high numbers)
    //      uses .map() to transform a valid experience number into a tier lable (jr, mid, senior, or whatever thresholds)
    //      uses .ifPresentOrElse() on the referral source specifically, logging a different message depending on whether one was provided
    //      uses .orElseGet() somewhere in the chain to supply a sensible default tier label if the experience value existed but got filtered out as unrealistic, rather than rejecting it whole
    public static class Candidate {
        String name; Optional<Integer> yOE; Optional<String> referralSource;
        public Candidate(String name, Optional<Integer> yOE, Optional<String> referralSource) {
            this.name = name; this.yOE = yOE; this.referralSource = referralSource;
        }
    }
    public static String returnDefaultTier(int yoe) {
        if (yoe < 0 || yoe < 10) { return "Basic"; } else if (yoe < 20) { return "Mid-Level";} else { return "Experienced"; }
    }
    public static void checkApplicant(Candidate applicant) {
        if (applicant.yOE.isEmpty()) { return; } else {
            System.out.println("------------------------------");
            String tierOf = applicant.yOE.filter(a -> a > 0 && a < 30).map(b -> returnDefaultTier(applicant.yOE.get())).orElseGet(() -> "Default");
            applicant.referralSource.ifPresentOrElse(unwrapped -> System.out.println(String.format("%s was referred by %s", applicant.name, unwrapped)), () -> System.out.println(String.format("%s has an invalid referral source", applicant.name)));
            System.out.println(String.format("%s is in the %s tier", applicant.name, tierOf));
        }
    }
    // Exercise 10 - Everything Plus a Real Design Decision You Have To Justify
    // Build a small subscription-renewal system. A subscriber has an Optional<String> payment method on file and an Optional<Integer> loyalty points based balance, both possibly absent
    // Design the full renewal-check method yourself, using whichever combination of everything covered feels correct for each piece
    //      but the actual req is this:
    //          decide and be ready to justify, whether a missing payment method should be a guard let style rejection or .orElseGet() styel fallback to a default payment method
    // Finally, implement whichever you choose, correctly, with real reasoning behind the choice rather than picking arbitrarily
    public static class Subscriber {
        String firstName; String lastName; Optional<String> paymentMethod; Optional<Integer> loyaltyPoints;
        private Subscriber(String firstName, String lastName, Optional<String> paymentMethod, Optional<Integer> loyaltyPoints) {
            this.firstName = firstName; this.lastName = lastName;
            this.paymentMethod = paymentMethod; this.loyaltyPoints = loyaltyPoints;
        }
        public static Subscriber createSubscriber(String firstName, String lastName, Optional<String> paymentMethod, Optional<Integer> loyaltyPoints) {
            if (firstName == null || lastName == null) { return null; }
            return new Subscriber(firstName, lastName, paymentMethod, loyaltyPoints);
        }
        public String getPaymentMethod() {
            return this.paymentMethod.isEmpty() ? "Not Available" : this.paymentMethod.get();
        }
        public int getLoyaltyPoints() {
            return this.loyaltyPoints.isEmpty() ? 0 : this.loyaltyPoints.get();
        }
    }
    public static boolean checkRenewalStatus(Subscriber subscriber) {
        // so after encountering numerous pay walls throughout my years on this great planet, Ive come to realize that whenever it comes to subscriptions
        // there always has to be a payment on file; if it was an otp; that payment method was optional to save
        // therefore, renewal check shall enforce the presence of a payment method
        // since I made the function above which is around the same functionality as orElseGet we just use this if statement below
        if (subscriber.getPaymentMethod().equals("Not Available")) { return false; } else {
            // since the paymentMethod is guaranteed to exist we can set it to a val
            String paymentMethodOf = subscriber.getPaymentMethod();
            // next is checking the value of the loyalty points; since I want somethign to be printed out Ill use ifPresentOrElse
            subscriber.loyaltyPoints.ifPresentOrElse(unwrapped -> System.out.println(String.format("%s currently has %d loyalty points", subscriber.firstName, unwrapped)), () -> System.out.println(String.format("%s has had no loyalty point balance set", subscriber.firstName)));
            // this is the end of this exercise, i couldve used subscriber.paymentMethod.isPresent() for the boolean check in the if statement above but this .orElseGet variant suffices
            return true;
        }
    }


    // Scanner - full mechanics before anything else
    // Scanner reads input from a source - most commonly System.in, the standard input stream, meaning whatever the user types into the terminal while the program is running
    // Scanner scanner = new Scanner(System.in); Sysout(print your name); String name = scanner.nextLine()
    // nextLine() reads an entire line of text as String, waiting until the user presses Enter
    // .nextInt(), .nextDouble(), .nextBoolean() read and parse a single token as that specific type
    //      throwing InputMismatchException if whats types doesnt amtch (typing letters where .nextInt() expects a number)
    // The classic gotcha
    //      .nextInt() and Double with .nextLine() in sequence causes a well known bug - .nextInt() only consumes the number itself, leaving the trailing newline character sitting unread in the buffer
    //          which the next .nextLine() call then immediately consumes an empty string, silently skipping whatever you actually wanted the user to type
    //      The standard fix is calling a throwaway scanner.nextLine() immediately after any .nextInt/Double call, specifically to consume that leftover newline before the next real read
    //      When you call .nextInt(), Scanner reads only the numeric characters themselves - it stops the instant it hits the numbers end, leaving the trailing newline character (from the user pressing Enter)
    //          still sitting unread in the input buffer. .nextInt() nver consumes that newLine, it only consumes the digits
    //          Now, if the very next call is .nextLine() expecting to read the next full line the user types - it doesnt wait for new input at all. It immediately finds that left over
    //              already-present newlin character sitting in the buffer from the precious .nextInt() call, treats that as the line has ended already, and returns an empty string, without every actually waiting for new stuff
    //          Modeled Below:
    //              int age = scanner.nextInt(); scanner.nextLine(); this consumers the leftover newLine (pressing Enter), and discards
    //              String name = scanner.nextLine(); this now correctly waits for real input
    // Scanner can read from several different sources beyond System.in - same class, different constructor argument
    // Scanner(String source) - reads directly from a String you already have in memory, rather than waiting on live user input
    //      Genuinely useful for testing, or parsing a chunk of text youve already received from somewhere else
    //      Scanner stringScanner = new Scanner("42 hello 3.14"); int num = stringScanner.nextInt(); String word = stringScanner.next(); double dec = stringScanner.nextDouble();
    //   .next() reads a single token, meaning one contiguous chunk of non-whitespace characters, stopping at the next space, tab, or newline
    //      Its the generic, string returning version, distinct from .nextLine() in a specific, important way worth being precise about
    //      This is genuinely useful when you expect input structured as multiple separate values on one line, and want to process them individually rather than getting the whole line back and having to split it yourself
    // Scanner(File file) - reads from an actual file on disk, line by line or token by token, same methods (.nextLine(), .nextInt(), etc) as System.in
    //      import java.io.File; import java.io.FileNotFoundException;
    //      try { Scanner fileScanner = new Scanner(new File("data.txt")); while (fileScanner.hasNextLine()) { Sysoutprntln(fileScanner.nextLine())}} catch (FileNotFoundException e) [ sysoutprntln e]
    //    new File() can throow the exception, a checked exception, meaning Java forces you to either catch it or declare that your method might throw it
    // The .hasNext() family - .hasNextLine(), hasNextInt(), hasNextDouble() - check whether more input of that type is actually available before trying to read it, letting you loop safely until input runs out
    //
    // Start of 15 Exercises with progressive complexity
    // Exercise 1 - .nextLine() from System.in, Guard-Let Pattern, Minimal OOP
    // Build a class representing a simple sign up form entry - fields for a username and an email, both optional<String>
    // Using Scanner reading from System.in, prompt the user for a username via .nextLine()
    // Using the guard let pattern (.isEmpty()-style check on whatever validity condition you choose) either construct a vlaid instance of your class or reject the input and print why
    // Keep this one intentionally simple - one field, ine prompt, prving the basic read-validate-construct flow works before layering complexity on top
    public static class SignedUp {
        Optional<String> username; Optional<String> email;
        public SignedUp(Optional<String> username, Optional<String> email) { this.username = username; this.email = email; }
    }
    public static SignedUp createUser() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter an username below");
        Optional<String> username = Optional.of(sc.nextLine()).filter(s -> !s.trim().isEmpty());
        // the two filters here return an empty optional if trimming the string inputted is truly empty, assigns a value to username if it isnt empty
        System.out.println("Enter an email address below");
        Optional<String> email = Optional.of(sc.nextLine()).filter(s -> !s.trim().isEmpty());

        if (username.isEmpty() || email.isEmpty()) { System.out.println("Both fields require a value to be passed in"); return null; } else {
            if (!email.get().contains("@")) { System.out.println("Rejecting this sign up due to a missing @ in the email address"); return null; } else {
                System.out.println(String.format("Welcome %s, you signed up using %s", username.get(), email.get())); return new SignedUp(username, email);
            }
        }
    }
    // Exercise 2 - .nextInt/Double with the Newline Gotach, Full OOp, Optional Required
    // Build a class representing a small inventory item - a name and quantity both Optional
    // Using a static scanner field using System.in, prompt for the quantity first via nextInt() then the name via .nextLine()
    // Prove you understand the gotcha by consuming the leftover newline after the nextInt() call, before calling nextLine()
    // deliberately in this order to force you to ahndle the leftover newLine gothca correctly with the throwaway consuming call in between
    // Wrap both reads in Optional based on validity (non negative qunatity and non blank name)
    // Apply the guard let pattern to reject construction if either fails and only build the real obj on the success path
    public static class InventoryItem {
        static Scanner scanner = new Scanner(System.in);
        Optional<String> name; Optional<Integer> quantity;
        private InventoryItem(Optional<String> name, Optional<Integer> quantity) { this.name = name; this.quantity = quantity; }
        public static InventoryItem createItem() {
            System.out.println("Enter quantity for item"); Optional<Integer> quant = Optional.of(scanner.nextInt()).filter(s -> s >= 0);
            // consumer
            scanner.nextLine(); System.out.println("Enter a name for the item");
            Optional<String> name = Optional.of(scanner.nextLine()).filter(s -> !s.trim().isEmpty());
            // guard let pattern
            if (name.isEmpty() || quant.isEmpty()) { System.out.println("Both fields need to have a value supplied. Try again"); return null; } else {
                // since we make sure that either field is not empty and valid we dont have to perform orElseGet etc
                System.out.println(String.format("New Item: %s with quantity of %d", name.get(), quant.get())); return new InventoryItem(name, quant);
            }
        }
    }
    // Exercise 3 - .next() for Token by Token Reading, Full OOP, Optional Required
    // Build a class representing a simple recipe ingredient - a name (Optional<String>) and a unit of measurement (Optional<String>)
    // like flour and cups
    // Prompt the user to type both values on the same line, separated by a space and use .next() twice to read them as two sep. tokens in one line of input
    // Apply the guard let patter on both, validating non blank values for each and construct the obj only on success
    // Before building - trace and predict after ttwo next calls consume both tokens on that line, does the newline gotcha from nextInt/Double still apply here
    //      or does next behave differently regarding what it leaves behind in the buffer
    public static class Ingredient {
        static Scanner sc = new Scanner(System.in);
        Optional<String> name; Optional<String> measurement;
        private Ingredient(Optional<String> name, Optional<String> measurement) {
            this.name = name; this.measurement = measurement;
        }
        public static Ingredient createIngredient() {
            System.out.println("Enter name and mesaurement to create an ingredient. Space-separated");
            if (!sc.hasNext()) { return null; } String name = sc.next();
            if (!sc.hasNext()) { return null; } String measurement = sc.next(); sc.nextLine(); // consumer
            if (name.isEmpty() || measurement.isEmpty()) { 
                System.out.println("Failed to create ingredient due to lack of input");
                return null;
            } else {
                System.out.println(String.format("Made new ingredient %s using %s as a measurement", name, measurement));
                return new Ingredient(Optional.of(name), Optional.of(measurement));
            }
        }
    }

    // Exercise 4 - Scanner(String source), Simulated Batch Input, Full OOP, Optional Required
    // Build a class representing a quiz question result - a question ID (Optional<Integer>) and whether it was answered correctly (Optional<Boolean>)
    // Instead of reading from System.in, construct a Scanner wrapping a String you build yourself, simulating a line of "batch input" like " 5 true "
    // Use .hasNext() checks before each read (matching the lesson from Exc 3)
    //      parsing the ID as an int and the correctness as a booleanl, wrapping both in Optional only once confrimed present
    // Apply the guard let patter, constructing the object only on success
    // Test with three separate Scanner instances built from three different String: one with both valid vals, one with one valid, one with no valid vals
    // so since I need a scanner with a valid string im going to create a method that takes an optional string then parse into the "parent" method
    public static Scanner createScanner(Optional<String> with) {
        return new Scanner(with.orElseGet(() -> "null 0"));
    }
    public static class QuizResult {
        Optional<String> questionID; Optional<Boolean> answerStatus;
        private QuizResult(Optional<String> questionID, Optional<Boolean> answerStatus) { this.questionID = questionID; this.answerStatus = answerStatus; }
        public static QuizResult createResult(Scanner sc) {
            // so heres the thing, I need to make sure that the values are present
            // we can do so with scanner.hasNext
            // .next() on a value provided such as "heythisisonestring" will provide that value
            if (!sc.hasNext()) { System.out.println("The scanner provided has no value"); return null; }
            // set value of the questionID
            Optional<String> qID = Optional.of(sc.next());
            if (!sc.hasNext()) { System.out.println("Scanner needs to have a second word it should be a number though but none were found"); return null; }
            Optional<Boolean> qStatus = Optional.of(true);
            System.out.println(String.format("Successfully made quiz result with ID of %s and status of %b", qID.get(), qStatus.get()));
            return new QuizResult(qID, qStatus);
        }
    }
    // Exercise 4 - Scanner(File), Reading Real Data From Disk, Full OOP, Optional Required
    // Full mechanics first since this introduces real file I/O and the first encounter with a checked exception
    // try { Scanner fileScanner = new Scanner(new File("students.txt"))}
    // Why they try catch is mandatory here and not optional is because the FileNotFoundException is a checked exception
    //      Javas compiler forces you to either catch it or explicitly declare  your method trows FNFE, refusing to compile otherwise
    //      File and network operations are the primary place checked exceptions show up in real Java code
    //          since the file might not exist is a category of failure the language wants you to explicitly acknowledge youve handled, not something forgotten
    // Build a small txt file yourself (via terminal or editor - a few lines, each representing one students name and grade, space separated)
    // Build a class representing a student record - name (Optional<String>, grade Optional<Integer>)
    // Using scanner(File) wrapped in a try/catch, read the file line by line with .hasNextLine() or nextLine()
    // Then use a second inner scanner String on each individual line to parse the name and grade tokens out of it
    //      this chains two different Scanner sources together - the file scanner driving the outer loop, string scanner parsing each lines content
    // Apply guard let validation and construct one StudentRecord per valid line
    // so student Record is the vallid output so it takes name and grade
    public static class StudentRecordMap{
        static TreeMap<String, Integer> studentGradesTM = new TreeMap<>(); // this sorts names alphabetically

        public static void returnSummaries() {
            for(Map.Entry<String, Integer> pair: studentGradesTM.entrySet()) {
                System.out.println(String.format("%s got a %d in the class", pair.getKey(), pair.getValue()));
            }
        }
        Optional<String> name; Optional<Integer> grade;
        private StudentRecordMap(Optional<String> name, Optional<Integer> grade) {
            this.name = name; this.grade = grade;
        }
        public static StudentRecordMap creaStudentRecord(Optional<String> name, Optional<Integer> grade) {
            // snce this is given to create a valid student record we can just return the new student record
            studentGradesTM.put(name.get(), grade.get());
            return new StudentRecordMap(name, grade);
        }
        // override equals and haschode
        @Override
        public boolean equals(Object other) {
            if (this == other) { return true;}
            if (!(other instanceof StudentRecordMap)) { return false; }
            StudentRecordMap otherStudentRecord = (StudentRecordMap) other;
            return this.name.equals(otherStudentRecord.name);
        }
        @Override
        public int hashCode() { return Objects.hash(name); }
    }
    // since i want this to be contained I will implement the container within the student record class and we can return a summary with its repsective method
    // since that is done we can start the mathod with a parameter of the file that itll be parsing over
    // then the scanner instantiated within the method will be with that file
    public static boolean checkRecordScanner(String fileName) {
        File file = new File(fileName);
        try {
            Scanner fileScanner = new Scanner(file);
            // this is the guard let pattern, checks if the file is empty or not
            if (!fileScanner.hasNext()) { System.out.println("It looks like this file is empty"); return false; }
            while (fileScanner.hasNext()) {
                // this iterates meanwhile the file contains another line
                String name = fileScanner.next();
                Integer grade = fileScanner.nextInt();
                 // consumer, we would have to separate the lines by whitespace if the particular length of the line wasnt known
                if (name.isEmpty() == false && grade > 0 && grade <= 100) {
                    StudentRecordMap.creaStudentRecord(Optional.of(name), Optional.of(grade));
                } else { continue; }
                
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(String.format("There was an error trying to read the file %nError: %s", e.getMessage()));
            return false;
        }
    }
    // Exercise 6/15 - Scanner(File) with a Genuine Two-Scanner Split, Correcting the Gap from Exc 5
    // Same domain as before - a class representing a record read from a file
    // This time, the split betweeen outer and inner scanner has to be real and necessary, not just present
    //      Build a file where lines have genuinely different shapes - some lines with two tokens, some with three
    // The outer Scanner(File) reads whole lines
    // A fresh inner Scanner(String), built per line, handles that lines actual token count and content, correctly reading the optional third token only when present
    //      (.hasNext() check on the inner scanner after reading the grade, before attempting to read honors status)
    // Add .hasNextInt() before the grade read this time, guarding against malformed data proper;y
    // Wrap all read vals in Optional, apply guard-let validation, construct the object only on success, and confirm both line shapes (two tokens and three)
    public static class StudentDesc implements Comparable<StudentDesc> {
        static TreeMap<StudentDesc, Optional<Boolean>> createdStudents = new TreeMap<>();
        public static void returnSummary() {
            createdStudents.forEach((student, status) -> System.out.println(String.format("%s got a %d in the class, has a %s status", student.name, student.grade, student.status.orElseGet(() -> false) ? "valid" : "invalid")));
        }
        String name; int grade; Optional<Boolean> status;
        private StudentDesc(String name, int grade, Optional<Boolean> status) {
            this.name = name; this.grade = grade; this.status = status;
        }
        public static StudentDesc createStudentDesc(String name, int grade, Optional<Boolean> status) {
            StudentDesc newStudent = new StudentDesc(name, grade, status);
            createdStudents.put(newStudent, newStudent.status); return newStudent;
        }
        @Override
        public int compareTo(StudentDesc other) {
            return this.name.compareTo(other.name);
        }
    }
    public static boolean checkStudentDesc(String fromFile) {
        File fileOf = new File(fromFile);
        try {
            Scanner fileScanner = new Scanner(fileOf);
            if (!fileScanner.hasNext()) { System.out.println("It appears the file passed in is empty"); return false; }
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(line);

                String name = lineScanner.next();
                if (!lineScanner.hasNextInt()) { continue; }
                int grade = lineScanner.nextInt();
                Optional<Boolean> honors;
                if (lineScanner.hasNext()) {
                    // theres a third token left in this line
                    honors = Optional.of(Boolean.parseBoolean(lineScanner.next()));
                } else { honors = Optional.empty(); }
                lineScanner.close();
                StudentDesc.createStudentDesc(name, grade, honors);
            }
            fileScanner.close();
            return true;
        } catch (FileNotFoundException e) { System.out.println(String.format("There was an error trying to read the file%nError: %s", e.getMessage())); return false; }
    }
    // Exercise 7/15 - Combining Everything, Multiple Input Sources Feeding Into One System
    // One program using two different Scanner source types together, not in isolation
    // Build a small even-registration system
    // Attendee names come from a file (Scanner(File filleName) with or without an inner line-scanner depending on what the files shape actually needs)
    //      one name per line, some lines deliberately blank or whitespace-only to force real validation
    // Once all valid attendees are loaded, prompt the user live via System.in to type a name and check whether that person is registered
    // Make sure to use nextLine(), guard let pattern and a lookup against whatever structure you loaded the file into (your choice, Set, TM, etc)
    // Wrap everything appropriately in Optional and make sure the live lookup correctly distinguishes three outcomes: name found/registerd, name types but not found, no result whatsoever
    public static void checkAttendees(String withFile) {
        File fileOf = new File(withFile);
        TreeSet<String> namesRegistered = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        Scanner promptUser = new Scanner(System.in);

        // since I need whole names we use nextLine() but what if that line is blank
        // I can use .next() for this case so we do need a per-line scanner
        // one to get the whole line, one to "check" the line with .next()
        try {
            Scanner fileScanner = new Scanner(fileOf);
            // check if file is empty
            if (!fileScanner.hasNext()) { System.out.println("It appears the file provided is emtpy."); return; }
            while (fileScanner.hasNext()) {
                // get current line
                String line = fileScanner.nextLine();
                Scanner currentLineScanner = new Scanner(line);
                // check if its empty and continue if so
                if (!currentLineScanner.hasNext()) { continue; }
                // add to set since its a valid line
                namesRegistered.add(line.toLowerCase());
                // names shouldnt be Optional but user input should be
                currentLineScanner.close();
            }
            fileScanner.close();

            // prompt user for a name to check the TS
            System.out.println("Enter a name to check if they have attended");
            Optional<String> nameOf = Optional.of(promptUser.nextLine().toLowerCase()).filter(s -> !s.trim().isEmpty());
            nameOf.ifPresentOrElse(unwrapped -> {
                if (namesRegistered.contains(unwrapped)) {
                    System.out.println(String.format("%s has attended the meeting", unwrapped));
                } else {
                    System.out.println(String.format("%s has not attended the meeting", unwrapped));
                }
                
            }, () -> System.out.println("No name in the list matches the name provided"));
            promptUser.close();
        } catch (FileNotFoundException e) { System.out.println(String.format("", e.getLocalizedMessage())); }
    }
    // Exercise 8/15 -- Scanner Driving a Loop Until a Sentinel Value, Full OOP, Optional Required
    // New mechanic worth explaining first: a common real pattern is reading repeated input until the user signals theyre done rather than a fixed number of reads
    // This uses a sentinel value - a specific input like typing done or quit that breaks the loop, checked before attempting to process that input as real data
    // Build a small expense tracker, Repeatedly prompt the user to enter an expense amount via nextLine(), parsing it as a Double wrapped in optional
    //      using try/catch around the parse itself, since malformed input like typing letters would throw NumberFormatException
    // Keep accumulating valid expenses into a running total, stored on a class you desing until the user types done to stop
    // Print final summary once the loop ends
    // One thing to think through before building, should the sentinel check done happen before or after attempting to parse the input as a number and what would go wrong if the order of this is incorrect
    // since were using the sentinel to break the execution then that means if that happens first, nothing else happens after that, if after, then further logic can occur
    public static void scannerExpenseTracker() {
        Scanner promptUser = new Scanner(System.in); double[] amount = new double[] { 0.00 };
        // instead of using while (true), a boolean var would be check, similar to that of an @State var in swift


        while (true) {
            System.out.println("Enter an amount to track. Enter done whenever finished.");
            String userInput = promptUser.nextLine();
            if (userInput.equalsIgnoreCase("done")) { promptUser.close(); break; }
            try {
                Optional<Double> retrievedAmount = Optional.of(Double.parseDouble(userInput)); // remember parseDouble can throw if invalid
                amount[0] += retrievedAmount.get(); 
            } catch (Exception e) { System.out.println(e.getLocalizedMessage());}
        }
    }
    // Exercise 9/15 -- Scanner Reading a Menu CHoice, Full OOP, Optional Required, Genuine Loop Controlled Interaction
    // Build a small text based menu system for a class you desing - something like a simple bacnk account simulator (deposit, withdraw, check balance, ecit) your choice of exact operations
    // Use a sentinel-driven loop (matching what you just built, whilte + break on a specific exit command) to repeatedly prompt for a menu choice via nextLine()
    // Based on the choice, branch into different behaviors - at least one branch that itself prompts for additional input (like an amount for deposit withdraw)
    // Wrap the accounts balance itself in Optional<Double> and think through - is that actually the right design choice for a balance that should probably always exist once an account is created
    //      Or would Optional be better reserved for something else in this specific system, like an optional account nickname
    // Decide deliberately and be ready to justify it, same standard as the subscription renewal exercises payment method decision
    // I actually like this exercise because it is somewhat coding logic then a UI or well a command line prompt scenario, somewhat like swift logic first then visuals following
    // This reqs a Bank class and the sentinel driven loop within the method can be static within the class or even per instance so guard to make sure caller is the person passed it
    public static class UserDriven_BankAccount {
        String firstName; String lastName;
        Optional<String> accountNickname;
        double balance = 0.00;
        LinkedHashMap<Double, Double> withdrawals = new LinkedHashMap<>(16, 0.75f, false);
        LinkedHashMap<Double, Double> deposits = new LinkedHashMap<>(16, 0.75f, false);
        private UserDriven_BankAccount(String firstName, String lastName) {
            this.firstName = firstName; this.lastName = lastName;
            this.accountNickname = Optional.empty();
        }
        public static UserDriven_BankAccount createBankAccount(String firstName, String lastName) {
            if (firstName == null || lastName == null) { System.out.println("Make sure that the first or last name are not empty"); return null; }
            return new UserDriven_BankAccount(firstName, lastName);
        }
        public void setAccountNickname(String to) {
            this.accountNickname = Optional.of(to);
        }
        public void getBalance() {
            System.out.println(String.format("%s currently has $%.2f in their account", this.firstName, this.balance));
        }
        public boolean deposit(double amount) {
            if (amount < 0) { System.out.println("Depositing amount has to be a positve amount"); return false; }
            this.balance += amount;
            deposits.merge(amount, this.balance, (oB, nB) -> nB); return true;
        }
        public boolean withdraw(double amount) {
            if (amount > this.balance) { System.out.println("Cannot withdraw more than your balance"); return false; }
            this.balance -= amount;
            withdrawals.merge(amount, this.balance, (oB, nB) -> nB); return true;
        }
        public void getAccountSummary() {
            System.out.println(String.format("Account Summary for %s", this.firstName));
            accountNickname.ifPresent(unwrapped -> System.out.println(String.format("%s has nicknamed their account to %s", this.firstName, unwrapped)));
            System.out.println("Deposit History");
            deposits.forEach((amountDeposited, newBalance) -> System.out.println(String.format("Deposit Amount: $%.2f, New Balance: $%.2f", amountDeposited, newBalance)));
            System.out.println("Withdrawal History");
            withdrawals.forEach((amountWithdrew, newBalance) -> System.out.println(String.format("Withdrawal Amount: $%.2f, New Balance: $%.2f", amountWithdrew, newBalance)));
        }
        public void runLoop() {
            Scanner promptUser = new Scanner(System.in);
            System.out.println("Enter Withdrawal for withdrawal, Deposit for Deposit, ACH for Account History, Exit when finished");

            while (true) {
                String userInput = promptUser.nextLine().toLowerCase();
                if (userInput.equalsIgnoreCase("withdrawal")) {
                    System.out.println(String.format("Enter an amount to withdraw. You currently have $%.2f", this.balance));
                    try {
                        Optional<Double> withdrawAmount = Optional.of(Double.parseDouble(promptUser.nextLine()));
                        if (this.withdraw(withdrawAmount.get())) {
                            System.out.println(String.format("Succesfully withdrew $%.2f, new balance is $%.2f", withdrawAmount.get(), this.balance));
                        }
                        System.out.println("Enter Withdrawal for withdrawal, Deposit for Deposit, ACH for Account History, Exit when finished");
                    } catch (NumberFormatException e) { System.out.println(e.getLocalizedMessage()); }
                } else if (userInput.equalsIgnoreCase("deposit")) {
                    System.out.println("Enter an amount to deposit");
                    try {
                        Optional<Double> depositAmount = Optional.of(Double.parseDouble(promptUser.nextLine()));
                        if (this.deposit(depositAmount.get())) {
                            System.out.println(String.format("Succesfully deposited $%.2f, new balance is $%.2f", depositAmount.get(), this.balance));
                        }
                        System.out.println("Enter Withdrawal for withdrawal, Deposit for Deposit, ACH for Account History, Exit when finished");
                    } catch (NumberFormatException e) { System.out.println(e.getLocalizedMessage()); }
                } else if (userInput.equalsIgnoreCase("ach")) {
                    this.getAccountSummary();
                    System.out.println("Enter Withdrawal for withdrawal, Deposit for Deposit, ACH for Account History, Exit when finished");
                } else if (userInput.equalsIgnoreCase("exit")) { break; } else {
                    System.out.println("Enter Withdrawal for withdrawal, Deposit for Deposit, ACH for Account History, Exit when finished");
                }
            }
        }
    }
    // Exercise 10/15 -- Scanner with a Transaction Log That Actually Survives Duplicates
    // This is the natural pickup of exactly what you just flagged
    // Build on the same bank account concept but fix the structural issue directly this time
    //      Instead of keying an LHM by transaction amount, use a List<String> (or a small record-like class you desing) to log every single transaction as its own independent entry
    //          regardless of whether the amount repeats
    // Same sentinel-driven menu loop, same chained Scanner reads within a single pass, but now two identical deposits/withdrawals should both show up distinctly in the account summary
    //      proven directly by testing that exact scenario again and confirming both entries survive this time
    interface PasswordVerifiable {
        default boolean createPassword(String attempt) {
            if (attempt.length() != 8) { System.out.println("Your password needs to be exactly 8 characters long. Please try again"); return false; }
            int numberCount = 0; int llCount = 0; int ulCount = 0;
            for (int i = 0; i < attempt.length(); i ++) {
                char c = attempt.charAt(i);
                if (Character.isDigit(c)) { numberCount++; }
                if (Character.isUpperCase(c)) { ulCount++; }
                if (Character.isLowerCase(c)) { llCount++; }
            }
            return (numberCount == 3 && llCount == 2 && ulCount == 3);
        }
        default boolean confirmPassword(String made, String confirmation) {
            System.out.println("Please confirm your password below");
            return made.equals(confirmation);
        }
    }
    public static class BankAccount_Enhanced implements AccountNumbers, PasswordVerifiable {
        // Complete 
            // Basic Info - name, 2FA choice
            // Password SetUp
            // Nickname SetUp
            // Instantiation option to deposit money
            // Change Nickname
            // Reset Password
            // Money Movement - Withdrawal/Deposit
            // Account Lock/Unlock
            // AN/RN Getter
            // Verification Helper
            // Withdrawal now reqs 2FA, implemented above helper
        // Pending
            // Main Menu
            // Fraud Possibilities - needs to be thought about
            // Withdrawal - Require 2FA
            // Account Summary
            // Configure changeNickname/Password to go back to main menu after in/successful setting
            // Correctly link users password to their 2FA code whenever instantiated
            // Refactor for user authentication, both password and 2FA(set up if necessary) -> boolean
        String firstName; String lastName; Scanner instanceScanner;
        Double balance; String accountNumber; String routingNumber;
        // or if true then we can ask for password if not created and then hold this password as a stored prop within the 2FA instance
        boolean authentication_2FA; String password; TwoFactorAuthentication user2FA;
        String accountNickname; boolean isLocked; int fraudPossibilities;

        private BankAccount_Enhanced(String firstName, String lastName, boolean authentication_2FA, Scanner instanceScanner) {
            this.firstName = firstName; this.lastName = lastName;
            this.password = null; this.balance = null;
            this.authentication_2FA = authentication_2FA; this.password = null;
            this.user2FA = null; this.accountNickname = null; this.instanceScanner = instanceScanner;
            this.isLocked = false; this.fraudPossibilities = 0;
        }
        public static BankAccount_Enhanced createBankAccount(Scanner instanceScanner) {
            // reconfiguration for Scanner to recieve input to create instance
            System.out.println("Type in your first name");
            Optional<String> firstName = Optional.of(instanceScanner.nextLine()).filter(s -> !s.trim().isEmpty());
            System.out.println("Type in your last name");
            Optional<String> lastName = Optional.of(instanceScanner.nextLine().trim()).filter(s -> !s.trim().isEmpty());
            System.out.println("Would you like to set up two-factor authentication. Enter yes or no");
            Optional<String> userDecision = Optional.of(instanceScanner.nextLine().trim()).filter(s -> !s.trim().isEmpty());

            String lastChance_FirstName = "";
            String lastChance_lastName = "";
            // handle first and lastName
            if (firstName.isEmpty()) {
                System.out.println("I did not get your first name, please enter it.");
                lastChance_FirstName = instanceScanner.nextLine().trim();
            }
            if (lastName.isEmpty()) {
                System.out.println("I did not get your last name, please enter it.");
                lastChance_lastName = instanceScanner.nextLine().trim();
            }
            final String resolvedFirstName = firstName.orElse(lastChance_FirstName);
            final String resolvedLastName = lastName.orElse(lastChance_lastName);
            // if theyre still null then return null and provide reason why
            if (firstName.isEmpty() || lastName.isEmpty()) {
                if (lastChance_FirstName.isEmpty()|| lastChance_lastName.isEmpty()) { 
                    System.out.println("Since you have failed to either provide your last or first name, I cannot continue with account creation");
                    return null;
                }
            }
            
            // handle boolean value for 2FA
            boolean authentication_2FA = userDecision.map(value -> value.equalsIgnoreCase("yes")).orElse(false);
            BankAccount_Enhanced newAcc = new BankAccount_Enhanced(resolvedFirstName, resolvedLastName, authentication_2FA, instanceScanner);
            if (authentication_2FA) { newAcc.user2FA = new TwoFactorAuthentication(newAcc); }
            // since password creation at instantiation is mandatory we can prompt for a password then if they have 2FA send it to 2FA if not then keep it stored
            System.out.println(authentication_2FA ? "Please type in a 8 character password to set up 2FA" : "Please type in a password. You'll be asked to confirm afterwards.");
            // Since a password is necessary i wont use Optional<String> here
            String passwordCreated;
            do {
                System.out.println("Your password needs to be exactly 8 characters long, have 3 numbers, 2 lowercase letters, and 3 uppercase letters.");
                passwordCreated = instanceScanner.nextLine().trim(); 
            } while (!newAcc.createPassword(passwordCreated));
            String confirmedPassword;
            do { System.out.println("Please confirm your password"); confirmedPassword = instanceScanner.nextLine().trim(); } while (!newAcc.confirmPassword(passwordCreated, confirmedPassword));
            // then set confirmed pasword to prop and to 2FA account if applicable
            newAcc.password = confirmedPassword;
            if (authentication_2FA) { newAcc.user2FA.set2FA(confirmedPassword); }

            // set nickname
            String nickname;
            do { System.out.println("Please type in an account nickname at least 3 letters long"); nickname = instanceScanner.nextLine().trim(); } while (nickname.trim().length() < 3);
            newAcc.accountNickname = nickname.trim();

            // prompt to set starting balance
            double startingBalance = 0.00;
            System.out.println("Would you like to set a starting balance? Yes or No");
            if (instanceScanner.nextLine().trim().equalsIgnoreCase("yes")) {
                try {
                    System.out.println("Enter an amount below. Make sure it's more than 0.");
                    Double balance_promptResult;
                    try {
                        balance_promptResult = Double.parseDouble(instanceScanner.nextLine().trim());
                        startingBalance = balance_promptResult;
                    } catch (Exception e) {
                        System.out.println("Oopsies...The input you passed was not a number. Creation of account has failed. Please start over");
                    }
                    
                } catch (Exception e) { System.out.println("Seems like you did not type a valid amount. Starting balance will be set to 0. You can deposit later"); }
            }
            newAcc.balance = startingBalance;

            newAcc.accountNumber = newAcc.createAccountNumber();
            newAcc.routingNumber = newAcc.createRoutingNumber();
            System.out.println(String.format("New Bank Account%nFirst Name: %s%nLast Name: %s%n2FA: %s%nAccount Nickname: %s%nStarting Balance: $%.2f", resolvedFirstName, resolvedLastName, authentication_2FA ? "Set up and linked accounts" : "Not Set Up", newAcc.accountNickname, newAcc.balance));
            return newAcc;
        }

        // main menu
        public void accountMainMenu() {

        }
        // refactor method in order to verify user
        public boolean verifyUser() {
            // first check if user has 2FA
            if (this.authentication_2FA) {
                // verify password then prompt for 2FA
                // once chance
                System.out.println("Please enter your password");
                if (this.instanceScanner.nextLine().trim().equals(this.password)) {
                    // prompt for 2fa
                    String receivedCode = this.user2FA.get2FACode();
                    System.out.println(String.format("Please enter the 2FA code provided, exactly as it appears. You only have one chance", receivedCode));
                    if(this.instanceScanner.nextLine().trim().equals(receivedCode)) {
                        return true;
                    } else { System.out.println("Invalid code entered. You're not verified"); return false; }
                } else {
                    System.out.println("Invalid password entered. You're not verified"); return false;
                }
            } else {
                // set up 2fa
                System.out.println("Since you do not have 2FA set up at this time, you'll be guided through the process\nYou'll be provided a code, enter it exactly as it appears");
                this.authentication_2FA = true; this.user2FA = new TwoFactorAuthentication(this); this.user2FA.password_bankAccount = this.password;
                String receivedCode = this.user2FA.get2FACode();
                System.out.println(String.format("Your code is %s", receivedCode));
                if (this.instanceScanner.nextLine().trim().equals(receivedCode)) {
                    System.out.println("Verified successfully"); return true;
                } else {
                    System.out.println("Invalid code entered, thus you're not verified. Please try again via the main menu"); return false;
                }
            }
        }
        // get account or routing number or both
        public void getAccountInfo() {
            // Verification happens first, needs both password and 2FA
            System.out.println("Please enter your password in order to get your account information");
            if (this.instanceScanner.nextLine().trim().equals(this.password)) { 
                System.out.println("Succesfully verified. Enter the 2FA code provided below. You will be prompted to set 2FA up if you have not already");
                if (this.authentication_2FA) {
                    String receivedCode = this.user2FA.get2FACode();
                    System.out.println(String.format("Code: %s", receivedCode));
                    if (this.instanceScanner.nextLine().trim().equals(receivedCode)) { System.out.println("2FA verified");} else {
                        System.out.println("Incorrect 2FA code entered. Please utilize the option in main menu in order to get your information");
                    }
                } else {
                    System.out.println("You are getting a 2FA account set up for you. In order to verify, a 2FA code will be sent to you. You have one chance to enter it.");
                    this.authentication_2FA = true; this.user2FA = new TwoFactorAuthentication(this); this.user2FA.password_bankAccount = this.password;
                    String receivedCode = this.user2FA.get2FACode();
                    System.out.println(String.format("Enter: %s", receivedCode));
                    if (this.instanceScanner.nextLine().equals(receivedCode)) { System.out.println("2FA verified and set up successfully");} else {
                        System.out.println("2FA code entered incorrectly. 2FA account set up and linked to your account"); return;
                    }
                }
            } else { System.out.println("Wrong password entered, please choose this option again in the main menu in order to get your information"); return; }
            System.out.println("Enter AN for Account Number, RN for Routing Number, or Both for Both");
            String passedInput = this.instanceScanner.nextLine().trim();
            if (passedInput.equalsIgnoreCase("an")) {
                System.out.println(String.format("Your account number is %s", this.accountNumber));
            } else if (passedInput.equalsIgnoreCase("rn")) {
                System.out.println(String.format("Your routing number is %s", this.routingNumber));
            } else if (passedInput.equalsIgnoreCase("both")) {
                System.out.println(String.format("Account Number: %s%nRouting Number: %s", this.accountNumber, this.routingNumber));
            } else { System.out.println("Invalid input receieved. Utilize the main menu option to run this again"); return; }
        }
        // unlock account
        public void unlockAccount() {
            // since this is like a real-world case where a user needs to verify password/2FA code to get it unlocked
            // first see, what causes a user to get their account locked whihc is passing in an incorrect password
            // first check if they have 2FA but since this is a main menu option
            // well can be, just like in swift we can implement conditional logic in order for it to show whenever needed
            if (this.authentication_2FA) {
                // this utilizes 2FA code then password, im sure it is safe to assume that they know their password
                // why would someone try to get a password more than 3 times rather than just resetting it
                System.out.println("In order to unlock you account, a 2FA code will be sent to you. Enter it exactly as it appears");
                String receivedCode = this.user2FA.get2FACode();
                System.out.println(String.format("Code to Enter: %s", receivedCode));
                if (this.instanceScanner.nextLine().trim().equals(receivedCode)) {
                    System.out.println("Thank you for verifying");
                    this.user2FA.delete2FA();
                    String passedPassword; int tries = 4; boolean isAuthenticated = false;
                    do {
                        passedPassword = this.instanceScanner.nextLine().trim();
                        if (passedPassword.equals(this.password)) { isAuthenticated = true; break; } else { tries -=1;}
                    } while (tries >= 0);
                    if (!isAuthenticated) { System.out.println("Since you failed four times, you'll need to reset your password. You may be asked for another 2FA code"); 
                        System.out.println("If you fail this time, your account will remain locked and will be flagged");
                        if (!changePassword()) { this.fraudPossibilities += 2; } else { System.out.println("Thank you for confirming. Your account is now unlocked"); this.isLocked = false;}
                    }
                }
            } else {
                // set up 2FA, get code, confirm code, reset password
                // changePassword sets up 2FA so i can just run that
                if (changePassword()) {
                    System.out.println("Your account is now unlocked");
                } else { System.out.println("There was an error resetting your password. Your account will remain locked."); this.isLocked = true; }
            }
        }
        // deposit
        public void deposit() {
            if (this.isLocked) { System.out.println("Your account is currently locked, a deposit is impossible at this time.\nPlease utilize Unlock Your Account via the main menu."); return; }
            // Password Prompt
            System.out.println("Please enter your password prior depositing an amount. If you fail 3 times, your account will be locked");
            String passwordMatch; int tries = 3; boolean isAuthenticated = false;
            do {
                passwordMatch = this.instanceScanner.nextLine().trim(); if (passwordMatch.equals(this.password)) { isAuthenticated = true; break;} else { tries -= 1; }
            } while (tries != 0);
            // Deposit Handling
            if (isAuthenticated) {
                double depositAmount; boolean isValid = false;
                while (!isValid) {
                    System.out.println("Please enter an amount to deposit. Make sure it is above zero");
                    try {
                        depositAmount = Double.parseDouble(this.instanceScanner.nextLine().trim());
                        isValid = depositAmount > 0; if (isValid) { 
                            this.balance += depositAmount;
                            System.out.println(String.format("Successfully deposited $%.2f, new balance stands at $%.2f", depositAmount, this.balance));
                        }
                    } catch (Exception e) { System.out.println(e.getMessage()); }
                    
                }
            } else { System.out.println("You have failed your password 3 times, your account will now be locked. Please utilize Unlock Your Account via the main menu"); this.fraudPossibilities += 1; this.isLocked = true;}
            
        }
        public void withdraw() {
            if (this.isLocked) { System.out.println("Your account is currently locked, a withdrawal is impossible at this time.\nPlease utilize Unlock Your Account via the main menu"); return; }
            // verify user - verifies password and 2FA
            if (this.verifyUser()) {
                double withdrawalAmount; boolean isValid = false;
                while (!isValid) {
                    System.out.println("Please enter an amount to withdraw. Make sure it is above zero.");
                    try {
                        withdrawalAmount = Double.parseDouble(this.instanceScanner.nextLine().trim());
                        isValid = withdrawalAmount > 0; if (isValid) { 
                        this.balance -= withdrawalAmount;
                        System.out.println(String.format("Successfully deposited $%.2f, new balance stands at $%.2f", withdrawalAmount, this.balance));
                    } } catch (Exception e) { System.out.println(String.format("You've entered an unacceptable value", e.getMessage())); }
                }
            } else {
                System.out.println("You have failed your password 3 times, your account will now be locked. Please utilize Unlock Your Account via the main menu"); this.fraudPossibilities += 1; this.isLocked = true;
            }
        }
        // change nickname 
            // reqs password
                // if forgotten, option to reset with 2FA code
        public void changeNickname() {
            if (this.isLocked) {
                System.out.println("Your account is currently locked, please Unlock Your Account via the main menu"); return;
            } else {
                System.out.println("Since you are trying to change your nickname, please enter your password.\nYou have only 3 tries, if at any point (before 3 tries are used) please type 'forgot'. In which case, if you do not have 2FA set up, it'll be set up for you and you will need the code that'll be sent to you.");
                // sentinel value which is an increment on tries
                int tries = 3; String passwordMatch; boolean isAuthenticated = false;

                do { 
                    passwordMatch = this.instanceScanner.nextLine().trim();
                    if (passwordMatch.equals(this.password)) { isAuthenticated = true; break; } else { tries -= 1; }
                } while (tries >= 0);

                if (!isAuthenticated) { System.out.println("You have inputted a wrong password 3 times. You will not be able to proceed. This will be marked on your account for possible unauthorized use");
                    this.fraudPossibilities += 1;
                    // in order for the password to be reset it needs to have the chance to sent the 2FA if wanted, if not, then lock account
                    // so we can reset password which returns boolean on success path then prompt again
                    System.out.println("Would you like to reset your password? A 2FA code will be sent, otherwise, your account will be locked. Enter yes or no.");
                    if (this.instanceScanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        if (!changePassword()) { 
                            System.out.println("Since the attempt to reset your password has failed, your account will be locked. Please utilize Unlock Your Account via the main menu");
                            this.isLocked = true;
                            return; } else {
                            // decrement fraudPossibilities
                            this.fraudPossibilities -= 1; // since user has verified and reset their password which needed 2FA
                            // proceed for nickname change
                            System.out.println("Password verified. Enter new nickname below. You only have one chance to do this");
                            String newNickname = this.instanceScanner.nextLine().trim();
                            this.accountNickname = !newNickname.isEmpty() ? newNickname : this.accountNickname;
                            System.out.println(String.format("Changed nickname succesfully to '%s'", this.accountNickname));
                        }
                    } else { System.out.println("Your account is now locked, please utilize the main menu option to unlock account"); this.isLocked = true; return; }
                    return;
                }
                // proceed for nickname change
                System.out.println("Password verified. Enter new nickname below. You only have one chance to do this");
                String newNickname = this.instanceScanner.nextLine().trim();
                this.accountNickname = !newNickname.isEmpty() ? newNickname : this.accountNickname;
                System.out.println(String.format("Changed nickname succesfully to '%s'", this.accountNickname));
                // Return back to main menu
                this.accountMainMenu();
            }
        }
        public boolean changePassword() {
            if (this.isLocked) { System.out.println("Your account is locked. Please utilize the main menu option to unlock account"); return false; }
            System.out.println(String.format("%s%n%s", "Changing a password requires a 2FA code.",  this.authentication_2FA ? "You will be sent a 2FA code, enter it exactly as it appears" : "You do not have 2FA set up, would you like to set it up. If yes type yes, otherwise you'll need to go back to the main menu"));
            // check if the user has 2FA set up otherwise it would be null and this would break
            if (this.authentication_2FA == false ) {
                //if (this.instanceScanner.hasNextLine()) { this.instanceScanner.nextLine().trim();} // clear buffer
                if (this.instanceScanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    this.authentication_2FA = true; this.user2FA = new TwoFactorAuthentication(this);

                    String receivedCode = this.user2FA.get2FACode();
                    System.out.println(String.format("Your code is: %s%nYou have only one chance to enter it", receivedCode));
                    if (this.instanceScanner.nextLine().trim().equals(receivedCode)) {
                        String passwordCreated;
                        do {
                            System.out.println("Your password needs to be exactly 8 characters long, have 3 numbers, 2 lowercase letters, and 3 uppercase letters.");
                            passwordCreated = instanceScanner.nextLine().trim(); 
                        } while (!this.createPassword(passwordCreated));
                        String confirmedPassword;
                        do { System.out.println("Please confirm your password"); confirmedPassword = instanceScanner.nextLine().trim(); } while (!this.confirmPassword(passwordCreated, confirmedPassword));
                        // then set confirmed pasword to prop and to 2FA account if applicable
                        this.password = confirmedPassword;
                        if (authentication_2FA) { this.user2FA.set2FA(confirmedPassword); } this.user2FA.delete2FA(); return true;
                    } else { System.out.println("You did not enter the code correctly, please go back to the main menu to get another attempt"); return false; }
                } else { System.out.println("You did not type in yes, please go back to the main menu if you choose to reset your password"); return false; }
            } else {
                String receivedCode = this.user2FA.get2FACode();
                System.out.println(String.format("Your code is: %s%nYou have only one chance to enter it", receivedCode));
                if (this.instanceScanner.nextLine().trim().equals(receivedCode)) {
                    String passwordCreated;
                    do {
                        System.out.println("Your password needs to be exactly 8 characters long, have 3 numbers, 2 lowercase letters, and 3 uppercase letters.");
                        passwordCreated = instanceScanner.nextLine().trim(); 
                    } while (!this.createPassword(passwordCreated));
                    String confirmedPassword;
                    do { System.out.println("Please confirm your password"); confirmedPassword = instanceScanner.nextLine().trim(); } while (!this.confirmPassword(passwordCreated, confirmedPassword));
                    // then set confirmed pasword to prop and to 2FA account if applicable
                    this.password = confirmedPassword;
                    if (authentication_2FA) { this.user2FA.set2FA(confirmedPassword); } return true;
                } else { System.out.println("You did not enter the code correctly, please go back to the main menu to get another attempt"); return false; }
            }
        }
    }
    interface AccountNumbers {
        default String createAccountNumber() {
            Random random = new Random();
            StringBuilder acn = new StringBuilder(10);

            for (int i = 0; i < 10; i++) { acn.append(random.nextInt(9));}
            if (Integer.parseInt(acn.substring(0, 1)) == 0) { createAccountNumber(); }
            return Long.parseLong(acn.toString()) % 10 == 0 ? acn.toString() : createAccountNumber();
        }
        default String createRoutingNumber() {
            Random random = new Random();
            StringBuilder rn = new StringBuilder(9);

            for (int i = 0; i < 9; i++) { rn.append(random.nextInt(9));}
            if (Integer.parseInt(rn.substring(0, 1)) == 0) { createRoutingNumber(); }
            return Integer.parseInt(rn.toString()) % 8 == 0 ? rn.toString() : createRoutingNumber();
        }
    }
    public static class TwoFactorAuthentication {
        BankAccount_Enhanced assignedTo; String current2FA;  String password_bankAccount;
        public TwoFactorAuthentication(BankAccount_Enhanced assignedTo) { this.assignedTo = assignedTo; this.current2FA = null; this.password_bankAccount = null; }

        private String generate2FACode() {
            Random rand = new Random();
            StringBuilder code = new StringBuilder(8);

            int runningTotal = 0;
            // Code format will be: U L N L U N U N
            char firstUpper = (char) (rand.nextInt(26) + 65); code.append(firstUpper); runningTotal += (int) firstUpper;
            char firstLower = (char) (rand.nextInt(26) + 97); code.append(firstLower); runningTotal += (int) firstLower;
            char firstNumber = (char) (rand.nextInt(10) + 48); code.append(firstNumber); runningTotal += (int) firstNumber;
            char secondLower = (char) (rand.nextInt(26) + 97); code.append(secondLower); runningTotal += (int) secondLower;
            char secondUpper = (char) (rand.nextInt(26) + 65); code.append(secondUpper); runningTotal += (int) secondUpper;
            char secondNumber = (char) (rand.nextInt(10) + 48); code.append(secondNumber); runningTotal += (int) secondNumber;
            char thirdUpper = (char) (rand.nextInt(26) + 65); code.append(thirdUpper); runningTotal += (int) thirdUpper;
            char lastChar = (char) (rand.nextInt(10) + 48); code.append(lastChar); runningTotal += (int) lastChar;

            return (runningTotal == 560) ? code.toString() : generate2FACode();
        }
        public String get2FACode() {
            String code = this.generate2FACode();
            this.current2FA = code;
            return code;
        }
        public void delete2FA() { this.current2FA = null; }
        public void set2FA(String confirmedPassword) { this.password_bankAccount = confirmedPassword; }
    }
    // Exercise 11/15 - Fresh Domain, Targeting %6's Actual Lesson: Consistent Policy Across Multiple Entry Points
    // Build a small file-access permission system - multiple methods (viewFile, editFile, deleteFile) that all require the same underlying authorization check before proceeding
    // The actual test: build one shared verification method, matching the spirit of your own verifyUser() abstraction
    // Prove that a failed check produces the identical consequence (a lockout, a strike counter increment, whatever you design)
    //      regardless of which of the three entry-point methods triggered it - the exact discipline #6 was missing, now built correctly from the start rather than retrofitted
    // Two questions before building:
    //      why does routing every entry point through one shared verification method make it structurally impossible for the kind of inconsistency #6 had to happen again
    //          
    //      What is the actual tradeoff of that approach
    //          is there ever a legitimate reason two different actions should have different consequences for the same kind of failure
    //          Or is uniform consequences always the right call
    interface VerifiablePassword {
        default boolean createPassword(String attempt) {
            if (attempt.length() != 8) { System.out.println("Your password needs to be exactly 8 characters long. Please try again"); return false; }
            int numberCount = 0; int llCount = 0; int ulCount = 0;
            for (int i = 0; i < attempt.length(); i ++) {
                char c = attempt.charAt(i);
                if (Character.isDigit(c)) { numberCount++; }
                if (Character.isUpperCase(c)) { ulCount++; }
                if (Character.isLowerCase(c)) { llCount++; }
            }
            return (numberCount == 3 && llCount == 2 && ulCount == 3);
        }
        default boolean confirmPassword(String made, String confirmation) {
            System.out.println("Please confirm your password below");
            return made.equals(confirmation);
        }
    }
    interface User_2FA {
        default String generate2FACode() {
            Random rand = new Random();
            StringBuilder code = new StringBuilder(8);

            int runningTotal = 0;
            // Code format will be: U L N L U N U N
            char firstUpper = (char) (rand.nextInt(26) + 65); code.append(firstUpper); runningTotal += (int) firstUpper;
            char firstLower = (char) (rand.nextInt(26) + 97); code.append(firstLower); runningTotal += (int) firstLower;
            char firstNumber = (char) (rand.nextInt(10) + 48); code.append(firstNumber); runningTotal += (int) firstNumber;
            char secondLower = (char) (rand.nextInt(26) + 97); code.append(secondLower); runningTotal += (int) secondLower;
            char secondUpper = (char) (rand.nextInt(26) + 65); code.append(secondUpper); runningTotal += (int) secondUpper;
            char secondNumber = (char) (rand.nextInt(10) + 48); code.append(secondNumber); runningTotal += (int) secondNumber;
            char thirdUpper = (char) (rand.nextInt(26) + 65); code.append(thirdUpper); runningTotal += (int) thirdUpper;
            char lastChar = (char) (rand.nextInt(10) + 48); code.append(lastChar); runningTotal += (int) lastChar;

            return (runningTotal == 560) ? code.toString() : generate2FACode();
        }
    }
    public static abstract class IdentifiableUser implements VerifiablePassword, User_2FA {
        String firstName; String lastName; String password; Scanner scanner = new Scanner(System.in);
        public IdentifiableUser(String firstName, String lastName) {
            this.firstName = firstName; this.lastName = lastName;
            this.password = null;
        }
        // REMEMBER:
            // Each type that inherits from this class has a Scanner for as long as its alive
        public boolean verifyUser_Access() {
            // since this method has to verify any type of User ill do so by verifying user password and 2FA
            System.out.println("Please verify your password below");
            if (this.scanner.nextLine().equals(this.password)) {
                String receivedCode = this.generate2FACode();
                System.out.println(String.format("Please verify the 2FA code below", receivedCode));
                if (this.scanner.nextLine().equals(receivedCode)) {
                    System.out.println("Access granted"); return true;
                } else { System.out.println("Could not verify the 2FA code. Please try again"); return false;}
            } else { System.out.println("Password was not verified. Please try again"); return false; }
        }

        // Think about other methods pertaining, editing, viewing, owning, deleting
        // Separation of Concerns
            // Regular User
                // View low-protection files (limited number)
                // Own files (limited number)
                // CANNOT edit files
            // Manager User
                // View low-high protected fles
                // CANNOT own files
                // Edit low/mid protected files
            // Admin User
                // All Capabilities
    }
    public static class RegularUser extends IdentifiableUser {
        int filesViewed_amt; int filesOwned_amt;
        // TreeMap for files, k: file, v: sizeOf
        private RegularUser(String firstName, String lastName, int filesViewed_amt, int filesOwned_amt) {
             super(firstName, lastName);
             this.filesViewed_amt = filesViewed_amt;
             this.filesOwned_amt = filesOwned_amt;
            }
        public static RegularUser createRegUser() {
            return null;
        }
    }
    public static class ManagerUser extends IdentifiableUser {
        // history of viewed files, sort based on importance(low-high)
        // history of edited files, sort based on importance(low-high)
        private ManagerUser(String firstName, String lastName) { super(firstName, lastName); }
        public static ManagerUser createManagerUser() {

            return null;
        }
    }
    public static class AdminUser extends IdentifiableUser {
        private AdminUser(String firstName, String lastName) { super(firstName, lastName); }
        public static AdminUser createAdmin() {

            return null;
        }
    }
    
}