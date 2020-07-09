package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.LinkedList;
import BusinessLogic.util.SqliteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class SearchAndModify implements Initializable {

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    @FXML
    private Label detectedText;

    @FXML
    private GridPane gridHead;

    @FXML
    private TextField front;

    @FXML
    private TextField back;

    @FXML
    private ChoiceBox<String> choiceDeck;

    @FXML
    private ChoiceBox<String> relatedFlashcard1;

    @FXML
    private ChoiceBox<String> relatedFlashcard2;

    @FXML
    private Button buttonEditExit;

    @FXML
    private GridPane gridEdit;


    @FXML
    void buttonEditExitAction(ActionEvent event) {

    }

    @FXML
    void searchButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SqliteDB db = new SqliteDB();
        TreeSet flashcardsTree = new TreeSet();
        db.insertIntoTree(flashcardsTree);


        LinkedList deckList = new LinkedList();
        LinkedList flashcardList = new LinkedList();
        db.listsFlashcards(flashcardList);
        db.listsDecks(deckList);

        for(int i=0; i< deckList.size(); i++){
            choiceDeck.getItems().addAll((String) deckList.get(i));
        }
        for(int i=0; i< flashcardList.size(); i++){
            relatedFlashcard1.getItems().addAll((String) flashcardList.get(i));
            relatedFlashcard2.getItems().addAll((String) flashcardList.get(i));
        }

        searchButton.setOnAction(e -> {
            String seachedText = search.getText();
            if(flashcardsTree.contains(seachedText)){
                Flashcard temp = db.getFlashcardByFront(seachedText);
                detectedText.setText("Tarjeta encontrada");
                front.setText(temp.getFront());
                back.setText(temp.getBack());
                relatedFlashcard1.getSelectionModel().select(temp.getRelated1());
                relatedFlashcard2.getSelectionModel().select(temp.getRelated2());
                gridEdit.setVisible(true);
                buttonEditExit.setVisible(true);
            }else{
                detectedText.setText("Tarjeta no encontrada");
                gridEdit.setVisible(false);
                buttonEditExit.setVisible(false);
            }
        });

        buttonEditExit.setOnAction(e-> {
            db.modifyFlashcard(new Flashcard(front.getText(), back.getText(), relatedFlashcard1.getSelectionModel().getSelectedItem(), relatedFlashcard2.getSelectionModel().getSelectedItem()), choiceDeck.getSelectionModel().getSelectedIndex()+1, search.getText());
            db.closeConnection();
            Stage stage = (Stage) buttonEditExit.getScene().getWindow();
            stage.close();
        });
    }
}
