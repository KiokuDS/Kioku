package BusinessLogic;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class Flashcard {

    private int id;
    private static int numberFlashcards = 0;
    public String front;
    private String back;
    private Deck deck;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private LocalDate nextRevision = LocalDate.now();
    public Flashcard left;
    public Flashcard right;

    public Flashcard(String front, String back){
        this.front = front;
        this.back = back;
        id = numberFlashcards++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {  /* no entiendo los set, pr qué devulven eso?*/
        this.id = id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }


    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", front='" + front + '\'' +
                ", back='" + back + '\'' +
                ", nextDay=" + nextRevision +
                '}';
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public LocalDate getNextRevision() {
        return nextRevision;
    }

    public void setNextRevision(LocalDate nextRevision) {
        this.nextRevision = nextRevision;
    }

    public void setNextEasy(){
        this.nextRevision = nextRevision.plusDays(3);
    }
    public void setNextHard(){
        this.nextRevision = nextRevision.plusDays(1);
    }

    // Dos tarjetas no pueden tener el mismo front.
    public int compareTo(Flashcard flashcard){
        if(this.front==flashcard.front){
            return 1;
        }
        return 0;
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Flashcard)) {
            return false;
        }

        // casting to the object
        Flashcard c = (Flashcard) o;

        // Compare the fronts of the flashcards, two are the same if they
        // have the same front
        return this.front.equals( c.getFront());
    }
}
