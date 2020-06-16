package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class FlashcardList implements Initializable {

    Queue FlashcardQueue = new Queue<Flashcard>(100);


    private ObservableList<ObservableList> data;

    @FXML
    private Text deckLabel;

    @FXML
    private ListView<?> deckListView;

    @FXML
    private Text flaschardLabel;

    @FXML
    private TableView<?> flaschardListTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
