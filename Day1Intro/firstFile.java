package Day1Intro;
// JVM, Compilation Model and Primitive Types
/* 
javac compiles .java into bytecode - a .class file full of instructions for a vm, not actual cpu
java fileName starts Java Virtual machine which reads that bytecode and executes it, translating int into real machine instruction

Why this two step design exists
    the same .class file runs unmodified on Windows, Linux, or Max because the JVM is the thing that is platform specific, not the compiled code
    This is Javas original write once, run anywhere ptuch and its the direct reason enterprise and government systems lean on it so heavily

JDK vs JRE
    JDK - Java Development Kit - includes the compiler and other tools for development
    JRE - Java Runtime Environment - includes the JVM and libraries needed to run Java programs, but not the compiler
    Is the full development kit - compiler (javac), debugger, and other tools
    The JRE is just the JVM and runtime libs, enough to run Java programs but not biild them

The Entry Point
    Every Java program has a main method, which is the entry point for the program
    public static void main(String[] args) {
        // code here
    }
    public - accessible from anywhere
    static - can be called without creating an instance of the class
    void - does not return a value
    main - the name of the method that is called when the program starts
    String[] args - an array of strings that can be passed to the program from the command line

Primitive Types
    Java has 8 primitive types: byte, short, int, long, float, double, char, boolean
    These are not objects and are stored directly in memory
    They have a fixed size and range of values

    Seperately, every custom type you write is a class - theres no struct equivalent, full stop
    Thats a bigger departure but it means Javas built in numeric types behave nothin like the Swift Int which is a full featured struct under the hood with methods and protocol conformances

    Since primtive types are objects, java has wrapper classes, Integer wraps int, Double wraps double, Boolean wraps boolean, for the cases where you need a number to actually behave like an obj (stored in a collection, for instacne, since Java's
    collections can only hold objects, not raw primitives). Java automatically converts between the primitive and wrapper types when needed, a feature called autoboxing and unboxing.)
 */

public class firstFile {
    public static void main(String[] args) {
        //double result = celsiusToFahrenheit(25.0);
        //boolean booleanTest = isEven(4);
        //String oddEven = booleanTest ? "even" : "odd";
        //System.out.println("25 degrees Celsius is " + result + " degrees Fahrenheit and it is " + oddEven);
        //Passenger p1 = new Passenger(null, null, 24);
        //System.out.println(p1.age);
        Flight f1 = new Flight("KA8402", "EPTX", 4);
        System.out.println(f1.getAttrs());
    }

    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}

