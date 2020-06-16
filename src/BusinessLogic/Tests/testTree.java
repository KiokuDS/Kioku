package BusinessLogic.Tests;

import BusinessLogic.Flashcard;
import BusinessLogic.util.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class testTree {
    public static void main(String[] args) {
        Instant start=Instant.now();
        Instant finish=Instant.now();
        Tree testTree = new Tree();
        //ListArray testList = new ListArray();
        try {
            System.out.println("Leyendo archivo...");
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            System.out.println("Iniciando carga de registros...");
            start = Instant.now();
            int i=0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                testTree.add(new Flashcard("Front "+data, "Back "+data));
                System.out.print("\r" + i);
                i++;
            }
            myReader.close();
            finish = Instant.now();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //testTree.print();
        System.out.println();
        System.out.println("============================");
        System.out.println("Buscando registro...");
        Instant start2 = Instant.now();
        System.out.println(testTree.search(args[1]));
        Instant finish2 = Instant.now();

        System.out.println("Tiempo inserción: " + Duration.between(start,finish).toMillis());
        System.out.println("Tiempo en la búsqueda: " + Duration.between(start2,finish2).toMillis());


    }
}
