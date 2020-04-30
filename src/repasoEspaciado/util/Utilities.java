package repasoEspaciado.util;

import repasoEspaciado.Deck;
import repasoEspaciado.Flashcard;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilities {

     public static void menu() throws FileNotFoundException {
         Scanner scanner = new Scanner(System.in);

         //Un txt almacena unas tarjetas, luego se implementa una base de datos.
         Deck demoDeck = new Deck("Test Deck");

         Scanner demoData = new Scanner(new File("src\\repasoEspaciado\\sources\\naiveSource.txt"));
         Scanner input = new Scanner(System.in);
         demoData.useDelimiter("-|\n");

         while(demoData.hasNext()) {
             String front = demoData.nextLine();
             String back = demoData.nextLine();
             demoDeck.insertFlashcard(new Flashcard(front, back));
         }

        boolean exit = false;
        int option;

        do{
            System.out.println(" _____   ______    ___    __ \n" +
                    " |  __ \\ |  ____|  / _ \\  /_ |\n" +
                    " | |__) || |__    | | | |  | |\n" +
                    " |  _  / |  __|   | | | |  | |\n" +
                    " | | \\ \\ | |____  | |_| |_ | |\n" +
                    " |_|  \\_\\|______|  \\___/(_)|_|\n\n");
            System.out.println("1. Crear Flashcard");
            System.out.println("2. Lista de tarjetas");
            System.out.println("3. Lección");
            System.out.println("4. Salir");
            try {
                System.out.println("\nIngrese una de las opciones");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Ingrese el frente de la tarjeta");
                        String front = input.nextLine();
                        System.out.println("Ingrese el anverso de la tarjeta");
                        String back = input.nextLine();
                        demoDeck.insertFlashcard(new Flashcard(front, back));
                        break;
                    case 2:
                        demoDeck.printDeck();
                        break;
                    case 3:
                       demoDeck.revision();
                        break;
                    case 4:
                        exit = true;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Inserte un numero");
                scanner.next();
            }
        }while(!exit);
    }

}
