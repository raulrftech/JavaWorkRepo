package Day1Intro;

public class Flight {
    String flightNumber;
    String destination;
    int seatsAvailable;

    public Flight(String flightNumber, String destination, int seatsAvailable) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    // String Interpolation
    // String.format("String words %s and %d", flightNumber, seatsAvailable)
    // %s is a placeholder for a string, %d is the placeholder for an int, they get filled in order by the arguments that follow
    // printf("String words %s and%n %d") with %n beinga. newline
    public String getAttrs() {
        return String.format("Flight Number: %s, Seats Available: %d", this.flightNumber, seatsAvailable);
    }
}
