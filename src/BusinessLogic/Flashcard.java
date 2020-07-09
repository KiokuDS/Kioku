package BusinessLogic;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Flashcard {

    private int id;
    private static int numberFlashcards = 0;
    public String front;
    private String back;
    private Deck deck;
    private LocalDate nextRevision = LocalDate.now();
    public Flashcard left;
    public Flashcard right;
    public String related1;
    public String related2;

    //Atributos de relacion entre tarjetas.
    private LinkedList relatedFlashcards;

    public LinkedList getRelatedFlashcards() {
        return relatedFlashcards;
    }

    public void setRelatedFlashcards(LinkedList relatedFlashcards) {
        this.relatedFlashcards = relatedFlashcards;
    }

    //Atributos relacionados al calculo de la dificultad.

    private int repetitions;
    private float easiness;

    //Dos formas diferentes de tener tarjetas relacionadas.
    //Una con una lista de estas, otra con la base de datos.
    public Flashcard(String front, String back){
        this.front = front;
        this.back = back;
        relatedFlashcards = new LinkedList<Flashcard>();
        id = numberFlashcards++;
    }

    public Flashcard(String front, String back, String related1, String related2){
        this.front = front;
        this.back = back;
        this.related1 = related1;
        this.related2 = related2;
        id = numberFlashcards++;
    }

    public void insertRelated(Flashcard f){
        this.relatedFlashcards.add(f);;
    }
    public void deleteRelated(Flashcard f){
        this.relatedFlashcards.remove(f);;
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


    public String getRelated1() {
        return related1;
    }

    public void setRelated1(String related1) {
        this.related1 = related1;
    }

    public String getRelated2() {
        return related2;
    }

    public void setRelated2(String related2) {
        this.related2 = related2;
    }

    public void setNextEasy(){
        this.nextRevision = nextRevision.plusDays(3);
    }
    public void setNextHard(){
        this.nextRevision = nextRevision.plusDays(0);
    }
    public void setNextEasyDB(){
        this.nextRevision = nextRevision.plusDays(3);
    }
    public void setNextHardDB(){
        this.nextRevision = nextRevision.plusDays(0);
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
