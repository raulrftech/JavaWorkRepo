package Day1Intro;
// Since Java has no struct, every custom type built onward is a class.
public class Passenger {
    // no var or let, Java fiels are mutable by default unless you add final (rough equivalent of Swift's let)
    // type comes first, same ordering as everything else in Java
    String firstName;
    String lastName;
    int age; // remmeber that Integer can be null, but int cannot

    // this is a constructor, Java's version of init
    // Note it has no return type at all, not even void - constructors are the one exception to every method needs a return type. Constructor name must exactly match the class name
    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        // this refers to this specific instances field, without this, java would think youre just reassigning the parameter to itself, and the field would stya unset
    }
}