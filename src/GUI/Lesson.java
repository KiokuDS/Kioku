package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.ListArray;
import BusinessLogic.util.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Lesson implements Initializable {

    @FXML
    private Button answerButton;

    @FXML
    private Text cover;

    @FXML
    private Text DeckNameTitle;

    @FXML
    private Text back;

    @FXML
    private ButtonBar lessonButtonBar;

    @FXML
    private Button easyButton;

    @FXML
    private Button HardButton;

    @FXML
    void easyButtonAction(ActionEvent event) throws IOException {

    }

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //Esta linea toma la informacion de la scene
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private TextArea returnHomeText;

    @FXML
    void hardButtonAction(ActionEvent event) {
        back.setVisible(true);
    }

    @FXML
    void showAnswer(ActionEvent event) {

    }
    @FXML
    private Button finishLessonButton;

    @FXML
    void finishLessonButtonAction(ActionEvent event) {
            backButton.fire();
    }

    public void changeScreenButtonPushed(MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //Esta linea toma la informacion de la scene
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private Label restantes;

    @FXML
    private ListView<String> relatedFlashcardsView;

    @FXML
    ObservableList<String> itemsRelated = FXCollections.observableArrayList();

    @FXML
    private Text relatedText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /* En cuanto a la GUI, la leccion fue primero implementada con un Stack, luego con una lista enlazada.
         * Se ordena por dificultad, se traen solo las tarjetas para el dia. Se sigue el algoritmo calculateDificulty()
         * en Flashcard y si la tarjeta no debe ser revisada mas por el dia se elimina del LinkedList de esta leccion.
         */

        AtomicInteger currentFlashcard = new AtomicInteger();
        /*StackArray demoDeck = new StackArray(100);*/
        ListArray dailyDeck = new ListArray();
        ListArray staticDeck = new ListArray();
        SqliteDB db = new SqliteDB();
        DeckNameTitle.setText("Deck seleccionado: "+ db.getDeckName(Home.getSelected()));
        db.linkedFlaschard(dailyDeck, Home.getSelected());
        db.linkedFlaschard(staticDeck, Home.getSelected());

        // Tarjetas Relacionadas Demo
        // Es necesario seralizar el ListArray de tarjetas relacionadas y almancenar esta info en la base de datos pero esto requiere que Flashcard sera serializable, lo cual aun no
        // ha sido implementado.

        relatedFlashcardsView.setItems(itemsRelated);
        
        //Bucle de leccion.

        Flashcard lessonFlashcard = (Flashcard) dailyDeck.getItem(currentFlashcard.get());
        cover.setText(lessonFlashcard.getFront());
        back.setText(lessonFlashcard.getBack());
        restantes.setText("Tarjetas restantes: "+dailyDeck.getSize());
        answerButton.setOnAction(e -> {
            lessonButtonBar.setVisible(true);
            answerButton.setVisible(false);
            back.setVisible(true);
            relatedText.setVisible(false);
            relatedFlashcardsView.setVisible(false);
            itemsRelated.clear();
            cover.setVisible(false);
            if(!dailyDeck.isEmpty()){
                easyButton.setOnAction(ae-> {
                    dailyDeck.delete(currentFlashcard.get());
                    Flashcard current = ((Flashcard) dailyDeck.getItem(currentFlashcard.get()));
                    current.setNextEasy();
                    cover.setText(current.getFront());
                    back.setText(current.getBack());
                    lessonButtonBar.setVisible(false);
                    answerButton.setVisible(true);
                    relatedFlashcardsView.setVisible(true);
                    relatedText.setVisible(true);
                    cover.setVisible(true);
                    back.setVisible(false);
                    restantes.setText("Tarjetas restantes: "+dailyDeck.getSize());
                });
            } else {
                easyButton.setOnAction(ae -> {
                    returnHomeText.setVisible(true);
                    finishLessonButton.setVisible(true);
                    relatedFlashcardsView.setVisible(false);
                    relatedText.setVisible(false);
                    lessonButtonBar.setVisible(false);
                    back.setVisible(false);
                });
            }
        });
        HardButton.setOnAction(e -> {
            lessonButtonBar.setVisible(true);
            answerButton.setVisible(false);
            back.setVisible(true);
            cover.setVisible(false);
            relatedText.setVisible(false);
            relatedFlashcardsView.setVisible(false);
            if(!dailyDeck.isEmpty()){
                HardButton.setOnAction(ae-> {
                    Flashcard current = ((Flashcard) dailyDeck.getItem(currentFlashcard.getAndIncrement()));
                    current.setNextHard();
                    cover.setText(current.getFront());
                    back.setText(current.getBack());
                    lessonButtonBar.setVisible(false);
                    relatedFlashcardsView.setVisible(true);
                    relatedText.setVisible(true);
                    answerButton.setVisible(true);
                    cover.setVisible(true);
                    back.setVisible(false);
                });
            } else {
                HardButton.setOnAction(ae -> {
                    returnHomeText.setVisible(true);
                    finishLessonButton.setVisible(true);
                    relatedFlashcardsView.setVisible(false);
                    relatedText.setVisible(false);
                    lessonButtonBar.setVisible(false);
                    back.setVisible(false);
                });
            }
        });

    }
}




