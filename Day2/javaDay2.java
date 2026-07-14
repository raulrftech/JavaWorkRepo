package Day2;

// Constructor Overloading, Access Modifiers, getters/setters

// Constructor overloading
//      Java lets you write multiple constructors with different parameter lists, same idea as Swifts designated/convenience patter just withou thte convenvience keyword
//      One constructor can delegate to another using this() as the very first line

/*
public class Flight {
    String flightNumber;
    String destination;
    int seatsAvailable;

    public Flight(String flightNumber, String destination, int seatsAvailable) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    public Flight(String flightNumber, String destination) {
        this(flightNumber, destination, 0);
    }
}
 */
// The second constructor calls the first, filling in a default of 0 for seatsAvailable - direct parallel to how a Swift convenience init calls self.init()
// Access Modifiers
//      public - accessible from anywhere, same as Swift
//      private - accessible only inside the same class. Swifts private is actually stricter (scoped to the enclosing declaration specifically), Javas is scope to whole class
//      Package-private (no modifier at all) - write a filed or method with no access keyword and its accessible only within the same package(folder)
//          Swift has no exact equivalent = internal is the closest cousin but internal spans your whole module not just one folder
//      protected - package-private, plus visible to subclasses even outside the package. Also has no clean Swift equivalent since Swift doesnt have a protected concept at all

// Getters and Setters 
//      Java has no computed properties or willSet/didSet, so this manual pattern is how encapsulation gets done
/*
private int seatsAvailable;

public int getSeatsAvailable() {
    return seatsAvailable;
}

public void setSeatsAvailable(int seatsAvailable) {
    this.seatsAvailable = seatsAvailable;
}
*/
// Fields go private, and external code interacts through the get/set methods instead of touching the field directly
//      this indirection is what lets you add validation or logic later without breaking anything that already depends on the class

// One fil, one public class holding main, as many package-private helper classes underneath it as you want
public class javaDay2 {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Raul Rodriguez", 118000, 12);
        Manager man1 = new Manager("Alex", 118000, 12, 45);
        SeniorManager SM1 = new SeniorManager("Daniel Hernandez", 118000, 22, 12, 18);
        System.out.println(String.format("CONFIGURATED BONUSES:%n   %s: $%.2f%n   %s: $%.2f%n   %s: $%.2f", emp1.name, emp1.calculateBonus(), man1.name, man1.calculateBonus(), SM1.name, SM1.calculateBonus()));
    }
}

class Classroom {
    String teacherName;
    int age; private int numberOfStudents;

    public Classroom(String teacherName, int age) {
        this.teacherName = teacherName;
        this.age = age;
    }

    public Classroom(String teacherName) {
        this.teacherName = teacherName;
        this.age = 32;
    }

    public void setNumberOfStudents(int number) {
        this.numberOfStudents = number;
    }
    public String getNumberOfStudents() {
        return String.format("%s has %d students in the class", this.teacherName, this.numberOfStudents);
    }
}

// Three Constructor Overload Chain
// At least one must delage to another via this(), at least one must not delegate (independent, field assignment)
// one field gets validated inside constructor or setter - rejects bad value by setting a safe default
class UserSignUp {
    String firstName;
    String lastName;
    String userEmail;
    String userName;
    private int userID;

    // full constructor
    public UserSignUp(String firstName, String lastName, String userEmail, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    // Non-Delegating Constructor
    public UserSignUp(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = null;
        this.userName = String.format("%s%s", this.firstName, this.lastName);
    }

    // Delegating Constructor
    public UserSignUp(String firstName, String lastName, String userEmail) {
        this(firstName, lastName, userEmail, String.format("%s%s", firstName, lastName));
    }

    public void setID(int id) {
        if (id % 2 != 0) {
            // if 7 / 2 = 3 then I can add one then add 1 to id
            this.userID = id + 1;
        } else {
            this.userID = id;
        }
    }
    public int getID() { return this.userID; }
}

// Static Field and why they are different than what was just built
// Every non-static field lives INSIDE a specific object
//      when you write new UserSignUp(...),
//          Java carves out a fresh chunk of memory for that one object
//          and every non-static field gets its own separate copy inside that chink
//      Three instances means three separate firstNames, etc, completely independent of each other
//          This is why acc1.userID and the acc2 ID can hold totally different values, theyre not the same var at all, just two vars that happen to share a name because theyre both fields of the same class
// A Static field breaks that rule on purpose - it lives once, attached to the class itself, not to any individual object.
//   No matter how many instances are created, theres still only ever one copy of that static field, shared by all of the,
//   If one instance changes it, every other instance and the class itself sees that same new value because there was never more than once copy to begin with
// Imagine a class of students, each with their own notebook (thats a normal field - each students notebokk is separate, writing in one doesnt affect the others)
//    versus one whiteboard at the front of the room that every student can see and write on (thats a static field - theres only one whiteboard, and everyones looking at the same one)
// Anything that should be a fact about the class as a whole - a running total, a shared config value, a counter of how many objects have ever been made - has to be static
//    because a non-static field literally cannot represent one shared value across all instances, it can only represent this one objs personal value

class Ticket {
    String eventName;
    double price;
    static int amountSold;

    public Ticket(String eventName, double price) {
        this.eventName = eventName; this.price = price;
        amountSold++;
    }

    static int getTotalTicketsSold() {
        return amountSold;
    }
}

// Interfaces - Kind of similar to protocols in Swift
//      An interface declares method signatures with no body - any class that implements it must provide the actual logic which matches that of Swift
// is decd with a class with the syntax   class Employee implements Payable
// implements is Javas word for what Swift calls conformance - a class implements an interface (versus extends for inheriting from another class, which is different)
// The class is now reqd to provide the logic for whichever method is decd in the Interface or it will not compile

// Where it genuinely diverges from Swifts protocols, Java interfaces can have default methods which is a method with an actual body written inside the interface itself
//      whcih implementing classes can inherit automatically unless they choose to override it such as the following default usage example
/*
interface Payable {
    double calculatePayment(); // this one will need its logic to be decd in the implementing class
    default String paymentSummary() {
        return "Payment due " + calculatedPayment
    }
}
 */
// Every class that implements this Payable interface now gets paymentSummary for free, without writing it
// closest Swift parallel is a protocol extension providing a default implementation, just built directly into the interface declaration instead of a separate extension block

interface Describable {
    String getDescription();

    default void printDescription() {
        System.out.println(getDescription());
    }
}
class SensitiveData implements Describable {
    String name;
    private String dob;
    static int totalOccurrences;

    public SensitiveData(String name) {
        this.name = name; totalOccurrences++;
    }

    public String getDescription() {
        return String.format("%s's DOB is %s", this.name, this.dob);
    }
}

interface Trackable {
    default void logEvent() {
        System.out.println("Event logged from Trackable interface");
    }
}
interface Notifiable {
    default void logEvent() {
        System.out.println("Event logged from Notifiable interface");
    }
}
class Event implements Trackable, Notifiable {
    String name;
    double duration;

    public Event(String name, double duration) {
        this.name = name;
        this.duration = duration;
    }

    // Java requires you to explicitly resolve the ambiguity of which logEvent to use, even if you dont want to use either one
    public void logEvent() {
        Trackable.super.logEvent();
    }
    public void logEvent_Notifiable() {
        Notifiable.super.logEvent();
    }
}

// Inheritance - extends keyword
// A class inherits every non-private field and method forma. parent class by declaring extends ParentClassName
// The child class is called a subclass; the class it inherits from is the superclass.
// Once a class extends another, every instance of the subclass automatically has acess to everything the superclass defined
//      It doesnt need to redeclare those fields or mthods
// Java only allows single inheritance - a class can extends exactly one other class, never more than one
// If you need a class to gain capabilities from multiple unrelated sources, interfaces are the tool, extends is strictly one parent, one lineage
// super(...) calss the parents constructor
// When a subclass has its own constructor, Java needs to know how the inherited fields get initialized
//      THe subclass's constructor doesnt automatically know how to set them up, super is how you explicitly call the superclass's constructor  from within the subclass's constructor
// The struct rule; if you call super at all, it must be the very first line of the subclass's constructor - nothing can come before it. THis is because Java reqs the parents portion of the object to be fully constructed before the subclass starts builiding its own additional fields on top
// What happens if you dont write super at all??
//      Java will automatically inserts an invisible call to the superclass's no arg constructor as the first line silently but only if the superclass actually has a no arg constructor available
//      If the superclass only has constructors that req params, and the subclass doesnt explicitly call super with those params, the code wont compule since Java jas no valid way to construct the parent portion of the object
class Employee {
    String name;
    double baseSalary;
    private int yearsEmployed; //  validate based on whether its negative, store 0 instead

    public Employee(String name, double baseSalary, int yearsEmployed) {
        this.name = name;
        this.baseSalary = baseSalary;
        if (yearsEmployed < 0) { this.yearsEmployed = 0; } else { this.yearsEmployed = yearsEmployed; }
    }
    public int getYearsEmployed() { return this.yearsEmployed; }
    public double calculateBonus() { return this.baseSalary * 0.05; }
}
class Manager extends Employee {
    int teamSize;

    public Manager(String name, double baseSalary, int yearsEmployed, int teamSize) {
        super(name, baseSalary, yearsEmployed);
        this.teamSize = teamSize;
    }
}
class Intern extends Employee {
    public Intern(String name, double baseSalary, int yearsEmployed) {
        super(name, baseSalary, yearsEmployed);
    }
}
class Executive extends Employee {
    double stockOptionsValue;

    public Executive(String name, double baseSalary, int yearsEmployed, double stockOptionsValue) {
        super(name, baseSalary, yearsEmployed); this.stockOptionsValue = stockOptionsValue;
    }
    @Override
    public double calculateBonus() { return this.baseSalary * 0.20; }
}
class SeniorEmployee extends Employee {
    int yearsOfLeadership;

    public SeniorEmployee(String name, double baseSalary, int yearsEmployed, int yearsOfLeadership) {
        super(name, baseSalary, yearsEmployed); this.yearsOfLeadership = yearsOfLeadership;
    }

    @Override
    public double calculateBonus() {
        double baseBonus = super.calculateBonus(); return baseBonus + (yearsOfLeadership * 500);
    }
}
class SeniorManager extends Manager {
    int regionsManaged;

    public SeniorManager(String name, double baseSalary, int yearsEmployed, int teamSize, int regionsManaged) {
        super(name, baseSalary, yearsEmployed, teamSize); this.regionsManaged = regionsManaged;
    }

    @Override
    public double calculateBonus() {
        double baseBonus = super.calculateBonus();
        return baseBonus + (regionsManaged * 1000);
    }
}