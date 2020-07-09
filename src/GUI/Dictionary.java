package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.SqliteDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Dictionary implements Initializable {

    @FXML
    private TextField searchText;

    @FXML
    private Button searchButton;

    @FXML
    private Text flaschardLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private ColumnConstraints gridDictionary;

    @FXML
    private Text reverso;

    @FXML
    private Text relacionada1;

    @FXML
    private Text relacionada2;

    @FXML
    void searchButtonAction(ActionEvent event) {
    }

    @FXML
    void searchTextAction(ActionEvent event) {

    }
    @FXML
    private Text noEncontrada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridPane.setVisible(false);
        noEncontrada.setVisible(false);
        SqliteDB db = new SqliteDB();
        HashMap mapFlashcards = new HashMap();
        db.insertIntoMap(mapFlashcards);
        searchButton.setOnAction(e -> {
            String searchedText = searchText.getText();
            if(mapFlashcards.containsKey(searchedText)){
                Flashcard temp = (Flashcard) mapFlashcards.get(searchedText);
                reverso.setText(temp.getBack());
                relacionada1.setText(temp.getRelated1());
                relacionada2.setText(temp.getRelated2());
                gridPane.setVisible(true);
                noEncontrada.setVisible(false);
                System.out.println(mapFlashcards.get(searchedText));
            } else {
                gridPane.setVisible(false);
                noEncontrada.setVisible(true);
            }
        });
        db.closeConnection();
    }
}
