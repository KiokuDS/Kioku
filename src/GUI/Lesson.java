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


    static String selectedCard;
    static boolean fromAnother;

    static void setFromAnother(boolean b){
        fromAnother=b;
    }
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

    @FXML
    private Button editFlashcard;

    @FXML
    void editFlashcardAction(ActionEvent event, String selectedCard) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            this.selectedCard = selectedCard;
            fxmlLoader.setLocation(getClass().getResource("SearchAndModify.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 345 , 480);
            Stage stage = new Stage();
            stage.setTitle("Editar Tarjeta");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /* En cuanto a la GUI, la leccion fue primero implementada con un Stack, luego con una lista enlazada.
         * Se ordena por dificultad, se traen solo las tarjetas para el dia. Se sigue el algoritmo calculateDificulty()
         * en Flashcard y si la tarjeta no debe ser revisada mas por el dia se elimina del LinkedList de esta leccion.
         */

        AtomicInteger currentFlashcard = new AtomicInteger();
        /*StackArray demoDeck = new StackArray(100);*/
        ListArray dailyDeck = new ListArray();
        SqliteDB db = new SqliteDB();
        DeckNameTitle.setText("Deck seleccionado: " + db.getDeckName(Home.getSelected()));
        db.linkedFlaschard(dailyDeck, Home.getSelected());
        // Tarjetas Relacionadas Demo
        // Es necesario seralizar el ListArray de tarjetas relacionadas y almancenar esta info en la base de datos pero esto requiere que Flashcard sera serializable, lo cual aun no
        // ha sido implementado.

        //Bucle de leccion.
        if (dailyDeck.getSize() == 0) {
            returnHomeText.setVisible(true);
            finishLessonButton.setVisible(true);
            relatedFlashcardsView.setVisible(false);
            relatedText.setVisible(false);
            lessonButtonBar.setVisible(false);
            back.setVisible(false);
            answerButton.setVisible(false);
            db.closeConnection();
        } else {
            Flashcard lessonFlashcard = (Flashcard) dailyDeck.getItem(currentFlashcard.get());
            cover.setText(lessonFlashcard.getFront());
            back.setText(lessonFlashcard.getBack());
            restantes.setText("Tarjetas restantes: " + dailyDeck.getSize());
            itemsRelated.addAll(lessonFlashcard.getRelated1(), lessonFlashcard.getRelated2());
            relatedFlashcardsView.setItems(itemsRelated);
            db.setEasy(lessonFlashcard);
            answerButton.setOnAction(e -> {
                lessonButtonBar.setVisible(true);
                answerButton.setVisible(false);
                back.setVisible(true);
                relatedText.setVisible(false);
                relatedFlashcardsView.setVisible(false);
                itemsRelated.clear();
                cover.setVisible(false);
                if (!dailyDeck.isEmpty() && dailyDeck.getSize() != 1) {
                    easyButton.setOnAction(ae -> {
                        dailyDeck.delete(currentFlashcard.get());
                        Flashcard current = ((Flashcard) dailyDeck.getItem(currentFlashcard.get()));
                        db.setEasy(current);
                        itemsRelated.clear();
                        itemsRelated.addAll(current.getRelated1(), current.getRelated2());
                        cover.setText(current.getFront());
                        back.setText(current.getBack());
                        lessonButtonBar.setVisible(false);
                        answerButton.setVisible(true);
                        relatedFlashcardsView.setVisible(true);
                        relatedText.setVisible(true);
                        cover.setVisible(true);
                        back.setVisible(false);
                        restantes.setText("Tarjetas restantes: " + dailyDeck.getSize());
                    });
                } else {
                    easyButton.setOnAction(ae -> {
                        db.closeConnection();
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
                if (!dailyDeck.isEmpty()) {
                    HardButton.setOnAction(ae -> {
                        Flashcard current = ((Flashcard) dailyDeck.getItem(currentFlashcard.getAndIncrement()));
                        current.setNextHard();
                        cover.setText(current.getFront());
                        back.setText(current.getBack());
                        itemsRelated.clear();
                        itemsRelated.addAll(current.getRelated1(), current.getRelated2());
                        lessonButtonBar.setVisible(false);
                        relatedFlashcardsView.setVisible(true);
                        relatedText.setVisible(true);
                        answerButton.setVisible(true);
                        cover.setVisible(true);
                        back.setVisible(false);
                    });
                } else {
                    HardButton.setOnAction(ae -> {
                        db.closeConnection();
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
}




