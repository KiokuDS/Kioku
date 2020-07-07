package GUI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import BusinessLogic.util.LinkedList;
import BusinessLogic.util.Map;
import BusinessLogic.util.SqliteDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Dictionary implements Initializable {

    //Vista de tabla
    private ObservableList<ObservableList> data;
    private TableView tableview;

    //Vista de lista de Decks

    private ListView<String> deckList;
    private ObservableList<String> items = FXCollections.observableArrayList();
    private int selectedDeck = 1;
    private int selectedFlashcard = 1;

    // Hashtable

    Map hashTableDemo = new Map<>();

    public void buildData(){
        Connection c ;
        SqliteDB database = new SqliteDB();
        data = FXCollections.observableArrayList();
        try{
            c = database.getC();
            //SQL FOR SELECTING ALL OF CUSTOME
            String SQL = "SELECT Id, Front as Frente, Back as Reverso FROM Flashcards WHERE Deck="+selectedDeck;
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){

                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                flaschardListTable.getColumns().addAll(col);
                System.out.println("Columna ["+i+"] ");
            }

            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Fila [1] añadida "+row );
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            flaschardListTable.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    @FXML
    private Text deckLabel;

    @FXML
    private ListView<String> deckListView;

    @FXML
    private Text flaschardLabel;

    @FXML
    private TableView flaschardListTable;

    private void cleanData(){
        flaschardListTable.getColumns().clear();
    }

    private void initActions(){
        deckListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent click) {
                System.out.println("Deck seleccionado:"+ deckListView.getSelectionModel().getSelectedIndex());
                selectedDeck = deckListView.getSelectionModel().getSelectedIndex()+1;
                cleanData();
                buildData();
                if(click.getClickCount()==2){
                    selectedDeck = deckListView.getSelectionModel().getSelectedIndex()+1;
                    System.out.println("clicked dos veces");
                }
            }
        });
        flaschardListTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent click) {
                System.out.println("Tarjeta seleccionada:"+ flaschardListTable.getSelectionModel().getSelectedIndex());
                selectedFlashcard = flaschardListTable.getSelectionModel().getSelectedIndex()+1;
                if(click.getButton() == MouseButton.SECONDARY){
                    Label label = new Label();
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("Editar");
                    item1.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // Abrir ventana de editar.
                        }
                    });
                    MenuItem item2 = new MenuItem("Eliminar");
                    item2.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            //Abrir ventana de eliminar.
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2);
                    flaschardListTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                        @Override
                        public void handle(ContextMenuEvent event) {
                            contextMenu.show(flaschardListTable, event.getScreenX(), event.getScreenY());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildData();
        initActions();
        deckListView.setItems(items);
        SqliteDB db = new SqliteDB();
        LinkedList deckList = new LinkedList();
        db.listsDecks(deckList);
        for(int i=0; i< deckList.size(); i++){
            items.addAll((String) deckList.get(i));
        }
        db.closeConnection();
    }
}