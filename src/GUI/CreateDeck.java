package GUI;

import BusinessLogic.util.LinkedList;
import BusinessLogic.util.SqliteDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateDeck implements Initializable {

    private static String newDeck;
    @FXML
    private TextField deckNameInput;

    @FXML
    private Button createDeckButton;

    @FXML
    void createDeckButtonAction(ActionEvent event) {
    }

    public void setNewDeck(String newDeck){
        this.newDeck = newDeck;
    }
    public static String getNewDeck(){
        return newDeck;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SqliteDB db = new SqliteDB();
        //Despues de validacion
        createDeckButton.setOnAction(e-> { db.insertDeck(
                deckNameInput.getText());
                setNewDeck(deckNameInput.getText());
                db.closeConnection();
                Stage stage = (Stage) createDeckButton.getScene().getWindow();
                stage.close();
                }
        );
    }
}
