package repasoEspaciado;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Flashcard {

    private int id;
    private static int numberOfFlashcards = 0;
    private String front;
    private String back;
    private Deck deck;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDate nextRevision = LocalDate.now();

    public Flashcard(String front, String back){
        this.front = front;
        this.back = back;
        id = numberOfFlashcards++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return  "id = " + id +
                ", front = '" + front + '\'' +
                ", back = '" + back + '\'' +
                ", Dia de Revision = " + nextRevision +
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Flashcard)) {
            return false;
        }

        Flashcard c = (Flashcard) o;

        // Compare the fronts of the flashcards, two are the same if they
        // have the same front
        return this.front.equals( c.getFront());
    }
}
