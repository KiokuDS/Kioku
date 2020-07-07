package BusinessLogic.Tests;

import BusinessLogic.Flashcard;
import BusinessLogic.util.Map;
import java.time.Duration;
import java.time.Instant;

public class testHashMap {

    public static void main(String[] args) {
        // Para las diferentes magnitudes que se deben medir se hizo

        Map testHash = new Map<>();
        Instant start = Instant.now();
        for(int i=0; i< 10000; i++){
            testHash.add(i, new Flashcard("Frente"+i,"Back"+i));
        }
        Instant finish = Instant.now();
        System.out.println("tiempo insercion "+ Duration.between(start, finish).toMillis());
        Instant start2 = Instant.now();
        for(int i=0; i< 10000; i++){
            testHash.remove(i);
        }
        Instant finish2 = Instant.now();
        System.out.println("tiempo eliminacion: "+ Duration.between(start2, finish2).toMillis());
    }
}
