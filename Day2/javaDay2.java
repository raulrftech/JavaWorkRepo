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
        RoutineCase rtC = new RoutineCase("Raul E R");
        TraumaCase tC = new TraumaCase("Emmanuel R", 8, 2500);
        ChronicCase cC = new ChronicCase("Raul R");

        TriageDesk mainTriage = new TriageDesk("All Cases Help", 18, rtC, tC, cC);
        System.out.println(mainTriage.processTransfers(tC, cC));
        System.out.println(mainTriage.getFullTriageReport(9995));

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



// What @Override actually checks precisely
//   For a subclass method to genuinely override a parent method, three things must match exactly
//      Method name, parameter list (types and order) and the return type must be the same 
//          or valid subtype (called covariant return types)
//          a more specific type than the parent's is allowed but not a completely unrelated one)
//      If any of these differ even slightly, a typo in the name, an extra param or different return type, 
//          Java doesnt override anything. It just created a brand new unrelated method
// Why @Override matters so much given that
//      Without it, a mistake like this compiles successfully and gives no warning - 
//          youd just have a subclass with what you think is a working override but its actually
//          a dead never called method sitting alongside the parents original unmodified version 
//              still being used
//      @Override forces the compiler to check your intent against reality - if you write @Override on something
//           that doesnt actually match a parents methods signature, compilation fails immediately with a clear error

// Access level rule for overrides
//   An override cannot reduce the visibility of the method its overriding
//   If a parent method is public, the override must also be public
//      It cannot become private or package private in the subclass
//      You can widen it (protected -> public) but never narrow it
class Shape {
    public Shape() {}
    public double calculateArea() {
        return 0.0;
    }
}
class Square extends Shape {
    double sideLength;
    public Square(double sideLength) { this.sideLength = sideLength; }

    @Override
    public double calculateArea() { return 0; }
}
// Small vehicle rental pricing system
class Vehicle {
    String type;
    double baseDailyRate;
    public Vehicle(String type, double baseDailyRate) {
        this.type = type; this.baseDailyRate = baseDailyRate;
    }
    public double calculateDailyCost() {
        return baseDailyRate;
    }
}
class Sedan extends Vehicle {
    public Sedan(String type, double baseDailyRate) {
        super(type, baseDailyRate);
    }
}
class SUV extends Vehicle {
    public SUV(String type, double baseDailyRate) {
        super(type, baseDailyRate);
    }
    @Override
    public double calculateDailyCost() {
        return super.calculateDailyCost() + 25.0;
    }
}
class LuxuryVehicle extends Vehicle {
    public LuxuryVehicle(String type, double baseDailyRate) {
        super(type, baseDailyRate);
    }
    @Override
    public double calculateDailyCost() {
        return baseDailyRate * 1.75;
    }
}

// Overriding equals(), Overriding a method that you did not write
//      Every Java class you create automatically inherits from a hidden root class called Object, whether you write extends or not
//      Object provides a handful of default methods every single object gets for free, including equals(Object other)
//          which by defualt just checks if two references point to the exact same object in memory - not whether their data looks the same

class Coordinate {
    double x; double y;
    public Coordinate(double x, double y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinate)) {
            // instance of checks if other is acutally a Coordinate before treating it as one
            //  since the parameter type is the general Object, not Coordinate specifically
            //      this is a strict req- overriding equals() must keep the exact Object param type, or it doesnt count as a real override
            return false;
        }
        Coordinate otherCoord = (Coordinate) other;
        // (Coordinate) other - a cast, converting the general Object reference into a specific Coordinate ref so you can access .x and .y on it
        // declare var that holds val and type (Coordinate otherCoord) which is casting the type of Coordinate to other
        return this.x == otherCoord.x && this.y == otherCoord.y;
    }
}
class Contact {
    String firstName; String lastName; String phoneNumber;
    public Contact(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Contact)) {
            return false;
        }
        Contact otherContact = (Contact) other;
        return this.phoneNumber == otherContact.phoneNumber;
    }
}
class ContactBook {
    Contact contact1;
    Contact contact2;
    Contact contact3;
    public ContactBook(Contact contact1, Contact contact2, Contact contact3) {
        this.contact1 = contact1; this.contact2 = contact2; this.contact3 = contact3;
    }
    public String findDuplicates() {
        boolean oneAndTwo = contact1.equals(contact2);
        boolean oneAndThree = contact1.equals(contact3);
        boolean twoAndThree = contact2.equals(contact3);
        return oneAndTwo ? "One and Two match" : oneAndThree ? "One and Three match" : twoAndThree ? "Two and Three match" : (oneAndTwo && oneAndThree && twoAndThree) ? "All numbers match" : "No contacts have the same number";
    }
}
// Overriding toString()
//   Another method every class inherits from Object - toString() - controls what gets shown whenever you print an object directly (System.out.println(someObj))
//      instead of printing one of its fields manually
//   The default version prints something unreadable like Contact@4517d9a3 - the calss name + a memory address.
//   Overriding it properly is standard practice for almost every real class youll write
/* 
    @Override
    public String toString() { return firstName + " " + lastName + " (" + phoneNumber + ")";}
 */
class Book {
    String title; String author; int publicationYear;
    public Book(String title, String author, int publicationYear) {
        this.title = title; this.author = author;
        this.publicationYear = publicationYear;
    }
    @Override
    public String toString() { return String.format("%s by %s was published in %d", this.title, this.author, this.publicationYear);}
}
class Library {
    Book book1; Book book2; Book book3;
    public Library(Book book1, Book book2, Book book3) {
        this.book1 = book1; this.book2 = book2; this.book3 = book3;
    }
    public Book findOldest(Book book1, Book book2, Book book3) {
        if (book1.publicationYear > book2.publicationYear ) {
            if (book1.publicationYear > book3.publicationYear) { 
                return book1;
            } else {
                return book3;
            }
        } else { return book2.publicationYear > book3.publicationYear ? book2 : book3; }
    }
    public String getOldestBook() {
        return findOldest(book1, book2, book3).toString();
    }
}
class BankAccount {
    String ownerName; private double accountBalance;
    static int instanceCreated;
    public BankAccount(String ownerName, double accountBalance) {
        this.ownerName = ownerName;
        if (accountBalance > 0) {
            this.accountBalance = accountBalance;
        } else { this.accountBalance = 0.0; }
        instanceCreated++;
    }
    @Override
    public String toString() {
        return String.format("Customer %s(%d) currently has $%.2f in their account.", this.ownerName, this.instanceCreated, this.accountBalance);
    }

    // Getter due to subclass restricted access level for accBalance
    public double getBalanace() { return this.accountBalance; }
}
class SavingsAccount extends BankAccount {
    double InterestRate;
    public SavingsAccount(String ownerName, double accountBalance, double InterestRate) {
        super(ownerName, accountBalance); 
        if (InterestRate > 0.99) { this.InterestRate = 0.50; } else { this.InterestRate = InterestRate; }
    }
    @Override
    public String toString() {
        return String.format("%s%s", super.toString(), String.format(" Their interest rate is %.2f", this.InterestRate * 100));
        //return "super.toString() + String.format(" Their interest rate is %.2f%", (this.InterestRate * 1000))";
    }
    public double applyInterest() {
        return super.getBalanace() * (1 + this.InterestRate);
    }
}
// Build Vehicle -> Truck -> Pickup Truck
// Every single level overrides the same method String getSpecs()
// Every override must call super.getSpecs() and build on it
class VehicleExc {
    String make; int year;
    public VehicleExc(String make, int year) { this.make = make; this.year = year; }

    public String getSpecs() {
        return String.format("VEHICLE SPECS; %d %s", this.year, this.make);
    }
}
class Truck extends VehicleExc {
    double towingCapacity;
    public Truck(String make, int year, double towingCapacity) {
        super(make, year);
        this.towingCapacity = towingCapacity;
    }

    @Override
    public String getSpecs() {
        if (this.towingCapacity <= 0) {
            return super.getSpecs();
        } else {
            return String.format("%s%nTOWING CAPACITY: %.2f", super.getSpecs(), this.towingCapacity);
        }
    }
}
class PickupTruck extends Truck {
    int bedLengthInches;
    public PickupTruck(String make, int year, double towingCapacity, int bedLengthInches) {
        super(make, year, towingCapacity); this.bedLengthInches = bedLengthInches;
    }

    @Override
    public String getSpecs() {
        if (this.bedLengthInches >= 60) {
            return String.format("%s%nBED LENGTH(inches): %d", super.getSpecs(), this.bedLengthInches);
        } else {
            return super.getSpecs();
        }
    }
}
// Small Media Library System
class MediaItem {
    String title; int durationInMinutes;
    public MediaItem(String title, int durationInMinutes) {
        this.title = title; this.durationInMinutes = durationInMinutes;
    }
    public String getPlaybackInfo() { return String.format("%s is %d minutes long.", this.title, this.durationInMinutes);}
}
class Movie extends MediaItem {
    String rating;
    public Movie(String title, int durationInMinutes, String rating) {
        super(title, durationInMinutes); this.rating = rating;
    }
    @Override
    public String getPlaybackInfo() { return String.format("%s%nRATING: %s", super.getPlaybackInfo(), this.rating);}
}
class Podcast extends MediaItem {
    int episodeNumber;
    public Podcast(String title, int durationInMinutes, int episodeNumber) {
        super(title, durationInMinutes); this.episodeNumber = episodeNumber;
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Podcast)) { return false;}
        Podcast otherPodcast = (Podcast) other;
        return this.episodeNumber == otherPodcast.episodeNumber;
    }
    @Override
    public String toString() {
        return String.format("This podcast is currently on episode %d", this.episodeNumber);
    }
}


// Abstract Classes and Abstract Methods
//  An abstract class is one that can never be instantiated directly - new Shape() would be illegal
//      if Shape were abstract - it exists purely to be extended.
//  An abstract method is a method with no body at all, declared only as a signature, forcing
//      every concrete (non-abstract) subclass to provide its own implementation or the subclass itself
//      must also be marked abstract and pass the obligation further down
//  The real distinction from an interface, concretely:
//      Example; Shape holds actual state (color, a real field with real data) and a mix
//      of finished (describe()) and unfinished (calculateArea()) methods
//      - something an interface traditionally couldnt do at all, since interfaces only declare method signatures
//      plus optional defaults with no persistent state of their own
//      A class can only extends one abstract class but can implements many interfaces - same single vs mutltiple inhertiances rules as before
// Payroll Calculation System - abs class name, hoursWorked, constructor, abs method calculatePay and full-imp String getPaySummary
abstract class PayableWorker {
    String name; private double hoursWorked;

    public PayableWorker(String name, double hoursWorked) {
        this.name = name;
        if (hoursWorked <= 0) { this.hoursWorked = 20; } else { this.hoursWorked = hoursWorked;}
    }
    abstract double calculatePay();
    double getHoursWorked() { return this.hoursWorked; }
    String getPaySummary() {
        double totalPay = calculatePay();
        return String.format("%s made a total of $%.2f this pay period.", this.name, totalPay);
    }
}
class HourlyWorker extends PayableWorker {
    double hourlyRate;
    public HourlyWorker(String name, double hoursWorked, double hourlyRate) {
        super(name, hoursWorked);
        this.hourlyRate = hourlyRate;
    }

    @Override
    double calculatePay() {
        return this.hourlyRate * this.getHoursWorked();
    }
}
class SalariedWorker extends PayableWorker {
    double fixedWeeklyPay;
    public SalariedWorker(String name, double hoursWorked, double fixedWeeklyPay) {
        super(name, hoursWorked); this.fixedWeeklyPay = fixedWeeklyPay;
    }
    @Override
    double calculatePay() {
        return this.fixedWeeklyPay;
    }
}
abstract class Publication {
    String title;
    public Publication(String title) { this.title = title;}
    abstract double calculateShippingCost();
    abstract String getFormatDescription();
    String getOrderSummary() {
        return String.format("Confirmed Order! %s", getFormatDescription());
    }
}
class PhysicalBook extends Publication {
    double weight_Pounds;
    public PhysicalBook(String title, double weight_Pounds) {
        super(title); this.weight_Pounds = weight_Pounds;
    }
    @Override
    double calculateShippingCost() {
        return this.weight_Pounds * 2.50;
    }
    @Override
    String getFormatDescription() {
        return String.format("%s is a Physical Hardcover%nShipping Cost: $%.2f", this.title, this.calculateShippingCost());
    }
}
class EBook extends Publication {
    double fileSize_MB;
    public EBook(String title, double fileSize_MB) {
        super(title); this.fileSize_MB = fileSize_MB;
    }
    @Override
    double calculateShippingCost() { return 0.0; }
    @Override
    String getFormatDescription() {
        return String.format("%s is %.2f MB in size. Shpping cost is %.2f", this.title, this.fileSize_MB, this.calculateShippingCost());
    }
}
class Employee_AbsExc {
    String name; private double hoursWorked;
    public Employee_AbsExc(String name, double hoursWorked) {
        this.name = name;
        if (hoursWorked < 0) {
            this.hoursWorked = 2;
        } else { this.hoursWorked = hoursWorked; }
    }
}
abstract class ManagementRole extends Employee_AbsExc {
    int jobTier;
    public ManagementRole(String name, double hoursWorked, int jobTier) {
        super(name, hoursWorked);
        if (jobTier < 0) { this.jobTier = 1;} else {
            if (jobTier > 4) { this.jobTier = 4; }
            this.jobTier = jobTier;
        }
    }
    abstract double calculateLeadershipBonus();
    String getManagementSummary() {
        double bonus = this.calculateLeadershipBonus();
        return String.format("%s is a %d Manager who is going to get a $%.2f bonus.", this.name, this.jobTier, bonus);
    }
}
class TeamLead extends ManagementRole {
    double hourlyRate;
    public TeamLead(String name, double hoursWorked, int jobTier, double hourlyRate) {
        super(name, hoursWorked, jobTier);
        if (hourlyRate <= 0.00) { this.hourlyRate = 7.50;}else{this.hourlyRate = hourlyRate;}
    }
    @Override
    double calculateLeadershipBonus() {
        double regBonus = jobTier * 450;
        return regBonus - 300;
    }
}
class Director extends ManagementRole {
    double yearsWorked;
    public Director(String name, double hoursWorked, int jobTier, double yearsWorked) {
        super(name, hoursWorked, jobTier);
        if (yearsWorked <= 0.00) {
            this.yearsWorked = 1.0;
        } else { this.yearsWorked = yearsWorked;}
    }
    @Override
    double calculateLeadershipBonus() {
        double regBonus = jobTier * 2000;
        double yearsWorkedBonus = yearsWorked * 500;
        return yearsWorkedBonus + regBonus;
    }
}
// Abstract Class Implementing an Interface
interface Discountable {
    double applyDiscount(double originalPrice);
}
abstract class Product implements Discountable {
    String name; double basePrice;
    public Product(String name, double basePrice) {
        this.name = name; this.basePrice = basePrice;
    }
    String getPriceSummary() {
        return String.format("Original Price: $%.2f, With Discount: $%.2f", basePrice, applyDiscount(basePrice));
    }
}
class ClearanceItem extends Product {
    public ClearanceItem(String name, double basePrice) {
        super(name, basePrice);
    }
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * 0.50;
    }
}
class MemberOnlyItem extends Product {
    public MemberOnlyItem(String name, double basePrice) {
        super(name, basePrice);
    }
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * 0.85;
    }
}
// Abstract class with an Abstract Method and a Regular Method both calling each other
abstract class GradedAssignment {
    String studentName; double pointsEarned; double pointsPossible;
    public GradedAssignment(String studentName, double pointsEarned, double pointsPossible) {
        this.studentName = studentName;
        this.pointsEarned = pointsEarned;
        this.pointsPossible = pointsPossible;
    }
    abstract String getLetterGrade();
    String getFullReport() {
        double trueGrade = pointsEarned / pointsPossible;
        String gradeNote = "";
        if ((trueGrade * 100) <= 70) { gradeNote = "; Needs Improvement"; }
        return String.format("Grade: %.2f(%S)%s", (trueGrade * 100), getLetterGrade(), gradeNote);
    }
}
class StandardAssignment extends GradedAssignment {
    public StandardAssignment(String studentName, double pointsEarned, double pointsPossible) {
        super(studentName, pointsEarned, pointsPossible);
    }
    @Override
    public String getLetterGrade() {
        double respectiveGrade = (this.pointsEarned / this.pointsPossible) * 100;
        if (respectiveGrade < 70.0) { return "F"; } else if (respectiveGrade >= 70.0 && respectiveGrade < 80.0) { return "C"; } else if (respectiveGrade >= 80.0 && respectiveGrade < 90.0) {
            return "B";
        } else { return "A";}
    }
}
class PassFailAssignment extends GradedAssignment {
    public PassFailAssignment(String studentName, double pointsEarned, double pointsPossible) {
        super(studentName, pointsEarned, pointsPossible);
    }
    @Override
    public String getLetterGrade() {
        double respectiveGrade = this.pointsEarned / this.pointsPossible;
        if ((respectiveGrade * 100) < 70.0) { return "Fail";} else { return "Pass";}
    }
}
// Abstract Classes vs Interfaces, Used Together Deliberately
// Real skill here isnt new syntax but the architectural judement of when to reach for which tool and building someting that genuinely needs both at once rather than either one alone
// Concrete Decision Rule, stated plainly;
//      Use an abstract class when subclasses share actual state (fields with real data) and a tight,
//          natural "is-a" relationship where only one parent makes sense
//      Use an interface when unrelated classes across different hierarchies just need to promise the same capability, 
//          with no shared state required and where a single class might need multiple such promises at once
// Exc1; One Class, One Abstract Parent, Multiple Interfaces
interface Auditable { String getAuditLog(); }
interface Exportable { String exportData(); }
abstract class FinancialRecord {
    String recordID;
    double amount;
    public FinancialRecord(String recordID, double amount) {
        this.recordID = recordID;
        if (amount < 0) { this.amount = 1;} else { this.amount = amount;}
    }
    abstract String getRecordType();
    String getBaseSummary() { return String.format("Record %s%nAmounnt: %f", this.recordID, this.amount);}
}
class Transaction extends FinancialRecord implements Auditable, Exportable {
    double priceOf; double taxRate;
    public Transaction(String recordID, double amount, double priceOf, double taxRate) {
        super(recordID, amount);
        if (priceOf < 0.00) { this.priceOf = 1.00; } else { this.priceOf = priceOf; }
        if (taxRate < 0.00 || taxRate > 1) { this.taxRate = 0.0825; } else { this.taxRate = taxRate; }
    }
    // Usable data manips for @Override funcs (subtotal, taxes)
    private double getSubtotal() { return this.amount * this.priceOf; }
    private double getTotal() { return getSubtotal() * (1 + taxRate); }

    @Override
    public String getAuditLog() {
        return String.format("Record %s", this.recordID);
    }
    @Override
    public String exportData() {
        return String.format("TRANSACTION%nRECORD: %s%nUNIT PRICE: %.2f%nAMOUNT: %.2fTAX RATE: %f percent%nTOTAL: $%.2f", this.recordID, this.priceOf, this.amount, (this.taxRate * 100), getTotal());
    }
    @Override
    public String getRecordType() {
        if (getTotal() > 1000.00) { return String.format("Business Transaction of $%.2f", getTotal());} else { return "Regular Transaction";}
    }
}
interface Archivable { String archive(); }
abstract class Document {
    String title; 
    public Document(String title) { this.title = title; }
    abstract String getContentSummary();
}
class Report extends Document implements Archivable {
    int pageCount;
    public Report(String title, int pageCount) { super(title); this.pageCount = pageCount; }
    @Override
    public String getContentSummary() {
        return String.format("%s is currently on page %d", this.title, this.pageCount);
    }
    @Override
    public String archive() {
        return String.format("Submitting %s to archive, on page %d", this.title, this.pageCount);
    }
}
abstract class Equipment {
    String assetTag;
    public Equipment(String assetTag) { this.assetTag = assetTag; }
    abstract String getConditionStatus();
}
class Laptop extends Equipment implements Archivable {
    int ageInMonths;
    public Laptop(String assetTag, int ageInMonths) { super(assetTag); this.ageInMonths = ageInMonths;}
    public String getConditionStatus() {
        return String.format("Laptop %s is currently %d months old.", this.assetTag, this.ageInMonths);
    }
    @Override
    public String archive() {
        return String.format("Storing laptop %s to section %d AgeInMonths in archive", this.assetTag, this.ageInMonths);
    }
}
// Interfaces as a Method Parameter Type
class ArchiveManager {
    public ArchiveManager() {}
    public String processArchive(Archivable item) {
        return String.format("PROCESSED: %s", item.archive());
    }
}
// Interface parameters
interface Playable { String play(); }
class BoardGame implements Playable {
    String name; int playerCount;
    public BoardGame(String name, int playerCount) {
        this.name = name;
        if (playerCount < 0 ) { this.playerCount = (playerCount * -1);}
    }
    @Override
    public String play() {
        return String.format("Starting game %s with %d players", this.name, this.playerCount);
    }
}
class VideoGame implements Playable {
    String title; String platform;
    public VideoGame(String title, String platform) {
        this.title = title; this.platform = platform;
    }
    @Override
    public String play() {
        return String.format("Now playing %s on %S", this.title, this.platform);
    }
}
class CardGame implements Playable {
    String name; int deckSize;
    public CardGame(String name, int deckSize) {
        this.name = name;
        if (deckSize < 0) { this.deckSize = (deckSize * -1);}
    }
    @Override
    public String play() {
        return String.format("Playing a game of %s with a deck of %d cards.", this.name, deckSize);
    }
}
class GameNight {
    public GameNight() {}
    public String hostGameNight(Playable first, Playable second, Playable third) {
        return String.format("Game Night Results%nFIRST: %s%nSECOND: %s%nTHIRD%n%s", first.play(), second.play(), third.play());
    }
}
// Choosing Between Abstract Class and Interface
// Smalll library with 3 kinds of items, Book, Magazine and DVD
//      All share real data, String title, String barcodeID abd int getLoanPeriodDays() returning possible loan duration
//      Only Book and Magazing need the ability to be renewed (extending a checkout without returning it first)
interface Extendable {
    void extendLoanDuration(int original, int extendBy);
}
abstract class Loanable {
    String title; String barcodeID; int loanDuration;
    public Loanable(String title, String barcodeID, int loanDuration) {
        this.title = title; this.barcodeID = barcodeID; this.loanDuration = loanDuration;
    }
    int getLoanDuration() { return this.loanDuration; }
    String getSummary() {
        return String.format("TITLE: %s%nBARCODE: %s%nLoan Duration: %d", this.title, this.barcodeID, this.getLoanDuration());
    }
}
class LastBookExc extends Loanable implements Extendable {
    public LastBookExc(String title, String barcodeID, int loanDuration) {
        super(title, barcodeID, loanDuration);
    }
    @Override
    public void extendLoanDuration(int original, int extendBy) {
        this.loanDuration += extendBy;
    }
}
class Magazine extends Loanable implements Extendable {
    public Magazine(String title, String barcodeID, int loanDuration) {
        super(title, barcodeID, loanDuration);
    }
    @Override
    public void extendLoanDuration(int original, int extendBy) {
        this.loanDuration += (extendBy - 1);
    }
}
class DVD extends Loanable {
    public DVD(String title, String barcodeID, int loanDuration) {
        super(title, barcodeID, loanDuration);
    }
}
class LibraryLoans {
    public LibraryLoans() {}
    public String getLoanedDurations(Loanable first, Loanable second, Loanable third) {
        return String.format("Loan Durations%nFIRST:%n   %s for %d days%nSECOND:   %s for %d days%nTHIRD:   %s for %d days", first.title, first.getLoanDuration(), second.title, second.getLoanDuration(), third.title, third.getLoanDuration());
    }
    public String getLoanSummaries(Loanable first, Loanable second, Loanable third) {
        return String.format("SUMMARIES%n%s%n%s%n%s", first.getSummary(), second.getSummary(), third.getSummary());
    }
    public void extendLoanDurations(LastBookExc first, Magazine second, int by) {
        System.out.println(String.format("Extending %s loan of %d days by %d more days", first.title, first.getLoanDuration(), by));
        first.extendLoanDuration(first.getLoanDuration(), by);
        System.out.println(String.format("New loan duration of %s is %d days", first.title, first.getLoanDuration()));
        System.out.println(String.format("Extending %s loan of %d days by %d more days", second.title, second.getLoanDuration(), by));
        second.extendLoanDuration(second.getLoanDuration(), by);
        System.out.println(String.format("New loan duration of %s is %d days", second.title, second.getLoanDuration()));
    }
}
// MP - Emergency Department Triage System
abstract class MedicalCase {
    String patientName; int severityLevel;
    private double treatmentCostEstimate;
    public MedicalCase(String patientName, int severityLevel) {
        this.patientName = patientName;
        if (severityLevel < 0 || severityLevel > 10) {
            this.severityLevel = 3;
        } else { this.severityLevel = severityLevel;}
    }
    public MedicalCase(String patientName) {
        this.patientName = patientName;
        this.severityLevel = 3;
    }
    void addFee(double of) {
        this.treatmentCostEstimate += of;
    }
    void setCostEstimate(double to) {
        if (to < 0) { this.treatmentCostEstimate = (to * -1);} else {
            this.treatmentCostEstimate = to;
        }
    }
    double getCostEstimate() { return this.treatmentCostEstimate; }
    abstract int getEstimatedWait_Minutes();
    String getCaseSummary(double costEstimate) {
        setCostEstimate(costEstimate);
        return String.format("PATIENT SUMMARY%nNAME: %S%nSEVERITY: %d%nCOST: $%.2f%nWAIT TIME: %d",this.patientName, this.severityLevel, getCostEstimate(), getEstimatedWait_Minutes());
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MedicalCase)) { return false; }
        MedicalCase otherCase = (MedicalCase) other;
        return this.patientName == otherCase.patientName;
    }
    @Override
    public String toString() {
        return String.format("Patient %s has a severity level of %d", this.patientName, this.severityLevel);
    }
}
interface Transferable { String transferToFacility(String facilityName); }
class RoutineCase extends MedicalCase {
    public RoutineCase(String patientName) {
        super(patientName);
    }
    @Override
    public int getEstimatedWait_Minutes() {
        int costToInt = (int) this.getCostEstimate(); return costToInt / 6;
    }
}
class TraumaCase extends MedicalCase implements Transferable {
    double fee;
    public TraumaCase(String patientName, int severityLevel, double fee) {
        super(patientName, severityLevel);
        this.fee = fee;
    }
    String traumaDescription = "";
    @Override
    public String getCaseSummary(double costEstimate) {
        setCostEstimate(costEstimate);
        super.addFee(fee);
        if (costEstimate > 5000) { traumaDescription = "This is a very important case";} else { traumaDescription = "This case is going to be taken care of quite easily.";}
        return String.format("PATIENT SUMMARY%nNAME: %S%nSEVERITY: %d%nCOST: $%.2f%nWAIT TIME: %d%n%s",this.patientName, this.severityLevel, getCostEstimate(), getEstimatedWait_Minutes(), traumaDescription);
    }
    @Override
    public int getEstimatedWait_Minutes() {
        int feeToInt = (int) fee; return feeToInt / 2;
    }
    @Override
    public String transferToFacility(String facilityName) {
        traumaDescription += String.format(" Transferring %s to %s", this.patientName, facilityName);
        return String.format("Submitted transfer to %s for %s", facilityName, this.patientName);
    }
}
class ChronicCase extends MedicalCase implements Transferable {
    public ChronicCase(String patientName) { super(patientName);}
    @Override
    public int getEstimatedWait_Minutes() { return 120; }
    @Override
    public String transferToFacility(String facilityName) {
        return String.format("Submitted transfer to %s for %s", facilityName, this.patientName);
    }
}
class TriageDesk {
    String name; int StationNumber; 
    MedicalCase routineCase;
    MedicalCase traumaCase;
    MedicalCase chronicCase;
    public TriageDesk(String name, int StationNumber, MedicalCase routineCase, MedicalCase traumaCase, MedicalCase chronicCase) {
        this.name = name; this.StationNumber = StationNumber;
        this.routineCase = routineCase; this.traumaCase = traumaCase; this.chronicCase = chronicCase;
    }
    public String getFullTriageReport(double CostEstimate) {
        String areEqual = "";
        if (routineCase.equals(traumaCase) && routineCase.equals(chronicCase)) { areEqual = String.format("Found that all three cases are for %s", routineCase.patientName);}
        return String.format("----%s Triage Desk %d Report----%n%s%n%s%n%s%n%s%n", this.name, this.StationNumber, routineCase.getCaseSummary(CostEstimate), traumaCase.getCaseSummary(CostEstimate), chronicCase.getCaseSummary(CostEstimate), areEqual);
    }
    public String processTransfers(Transferable first, Transferable second) {
        return String.format("%s%n%s", first.transferToFacility("UMC"), second.transferToFacility("VDS"));
    }
}