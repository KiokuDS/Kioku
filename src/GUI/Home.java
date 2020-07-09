package GUI;

import BusinessLogic.util.LinkedList;
import BusinessLogic.util.SqliteDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    String newDeck;

    @FXML
    ListView<String> list;
    ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private Button createDeck;

    @FXML
    private Button createFlashcard;

    @FXML
    void createDeckAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CreateDeck.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 370, 240);
            Stage stage = new Stage();
            stage.setTitle("Crear Deck");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
        }
    }

    @FXML
    void createFlashcardAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("CreateFlashcard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 345, 480);
            Stage stage = new Stage();
            stage.setTitle("Crear Flashcard");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
        }
    }

    @FXML
    void enterDeckRevision(ActionEvent event){

    }

    public void changeScreenButtonPushed(MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Lesson.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //Esta linea toma la informacion de la scene
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void initActions(){
        //Detecting mouse clicked
        list.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent click) {
                    System.out.println("Deck seleccionado:"+ list.getSelectionModel().getSelectedIndex());
                    if(click.getClickCount()==2){
                        setSelected(list.getSelectionModel().getSelectedIndex()+1);
                        System.out.println("clicked dos veces");
                        try {
                            changeScreenButtonPushed(click);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
    }

    @FXML
    private MenuItem listaTarjetasMenu;

    @FXML
    void listaTarjetasMenuAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FlashcardList.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640 , 400);
            Stage stage = new Stage();
            stage.setTitle("Lista de Flashcards");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
        }
    }


    @FXML
    private MenuItem modifyTarjetaMenu;

    @FXML
    void modifyTarjetaMenuAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
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


    @FXML
    private MenuItem DictionaryMenu;


    @FXML
    void DictionaryMenuAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Dictionary.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640 , 400);
            Stage stage = new Stage();
            stage.setTitle("Diccionario");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
        }
    }
    private static int selection;
    public void setSelected(int select){
        selection = select;
    }
    public static int getSelected(){
        return selection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.setItems(items);
        SqliteDB db = new SqliteDB();
        LinkedList deckList = new LinkedList();
        db.listsDecks(deckList);
        for(int i=0; i< deckList.size(); i++) {
            items.addAll((String) deckList.get(i));
        }
        initActions();
        db.closeConnection();
    }
}