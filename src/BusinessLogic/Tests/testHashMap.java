package BusinessLogic.Tests;
import BusinessLogic.Flashcard;
import BusinessLogic.util.Map;
import java.time.Duration;
import java.time.Instant;

public class testHashMap {

    public static void main(String[] args) {

        Map testHash = new Map<String, String>();

        for(int i=0; i<100; i++){
            testHash.add("Frente "+i, "Reverso "+i);
        }

        System.out.println("Ejemplo cargar"+ testHash.get("Frente 1"));
        System.out.println("Ejemplo cargar"+ testHash.get("Frente 2"));

    }
}
