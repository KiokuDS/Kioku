package BusinessLogic;
import BusinessLogic.util.ListArray;

import java.util.ArrayList;
import java.util.Scanner;

public class Deck {

    private int id;
    private static ArrayList deckofList;
    private static int numberOfDecks;
    private String name;
    private ListArray<Flashcard> deckList;

    public Deck(String name){
        this.name = name;
        deckList = new ListArray<Flashcard>();
        id = numberOfDecks++;
    }

    public void insertFlashcard(Flashcard flashcard){
        deckList.insert(flashcard);
    }
    public void deleteFlashcard(Flashcard flashcard){
        deckList.delete(flashcard);
        System.out.println("Se ha eliminado la tarjeta");
    }
    public void printDeck(){
        System.out.println("Deck: "+ name);
        deckList.print();
    }

    public void revision(){
        System.out.println("Bienvenido a la revision diaria. Ingrese -1 para salir.");
        Scanner scanner = new Scanner(System.in);
        boolean exitLeccion = false;
        int i = 0;
        while(!exitLeccion){
            Object o = getDeckList().getItem(i);

            Flashcard f = (Flashcard) o;
            System.out.println(f);
            System.out.println("Ingrese la dificultad: Facil - 1, Dificil - 2");
            int dificulty = scanner.nextInt();
            switch(dificulty){
                case -1:
                    exitLeccion = true;
                case 1:
                    f.setNextEasy();
                    break;
                case 2:
                    f.setNextHard();
                    break;
                default:
                    System.out.println("Ingrese un valor entre 1 y 2");
            }
            if(dificulty==1 || dificulty==2){
                System.out.println("Nueva fecha de revision es "+f.getNextRevision());
                i++;
            }
            if(i==getDeckList().getSize()){
                exitLeccion= true;
            }
        }
    }

    public Deck getDailyDeck(Deck deck){
        Deck dailyDeck = new Deck(deck.getName());
        return dailyDeck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getNumberDecks() {
        return numberOfDecks;
    }

    public static void setNumberDecks(int numberDecks) {
        Deck.numberOfDecks = numberDecks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListArray<Flashcard> getDeckList() {
        return deckList;
    }

    public void setDeckList(ListArray<Flashcard> deckList) {
        this.deckList = deckList;
    }
}
