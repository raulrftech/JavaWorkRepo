package Day4;

public class day4 {
    
    public static void main(String[] args) {

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
    // One real caution: Optional is meant to be used as a method return type, singalling to callers "check before you use this" is considered bad practice to use it as a class field type or a method param type
    //      since it adds wrapping overhead without the same benefit.
    //      Keep it at return boundaries, not baked into data models
}
