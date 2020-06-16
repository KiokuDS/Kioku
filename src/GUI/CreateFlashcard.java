package GUI;

import BusinessLogic.util.LinkedList;
import BusinessLogic.util.SqliteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateFlashcard implements Initializable {


    @FXML
    private Button createFlashcardButton;

    @FXML
    private TextField front;

    @FXML
    private TextField back;

    @FXML
    private ChoiceBox<String> choiceDeck;

    @FXML
    void createFlashcardButtonAction(ActionEvent event) {
        //Falta validacion
        //Falta insercion en base de datos.


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SqliteDB db = new SqliteDB();
        LinkedList deckList = new LinkedList();
        db.listsDecks(deckList);
        for(int i=0; i< deckList.size(); i++){
            choiceDeck.getItems().addAll((String) deckList.get(i));
        }
        //Despues de validacion
        createFlashcardButton.setOnAction(e-> {
            db.insertFlashcard(front.getText(), back.getText(), choiceDeck.getSelectionModel().getSelectedIndex()+1);
            Stage stage = (Stage) createFlashcardButton.getScene().getWindow();
            stage.close();
        });
        choiceDeck.getSelectionModel().selectFirst();
    }
}
