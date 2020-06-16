package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.SqliteDB;
import BusinessLogic.util.StackArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        StackArray demoDeck = new StackArray(100);
        SqliteDB db = new SqliteDB();
        DeckNameTitle.setText("Deck seleccionado: "+ db.getDeckName(Home.getSelected()));
        db.listsFlaschard(demoDeck, Home.getSelected());
        db.closeConnection();

        Flashcard first = (Flashcard) demoDeck.pop();
        Flashcard preview;
        cover.setText(first.getFront());
        back.setText(first.getBack());
        answerButton.setOnAction(e -> {
            lessonButtonBar.setVisible(true);
            answerButton.setVisible(false);
            back.setVisible(true);
            cover.setVisible(false);
            if(!demoDeck.empty()){
            easyButton.setOnAction(ae-> {
                Flashcard current = (Flashcard) demoDeck.pop();
                cover.setText(current.getFront());
                back.setText(current.getBack());
                lessonButtonBar.setVisible(false);
                answerButton.setVisible(true);
                cover.setVisible(true);
                back.setVisible(false);
            });
            } else {
                easyButton.setOnAction(ae -> {
                    returnHomeText.setVisible(true);
                    finishLessonButton.setVisible(true);
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
            if(!demoDeck.empty()){
                HardButton.setOnAction(ae-> {
                    Flashcard current = (Flashcard) demoDeck.pop();
                    cover.setText(current.getFront());
                    back.setText(current.getBack());
                    lessonButtonBar.setVisible(false);
                    answerButton.setVisible(true);
                    cover.setVisible(true);
                    back.setVisible(false);
                });
            } else {
                HardButton.setOnAction(ae -> {
                    returnHomeText.setVisible(true);
                    finishLessonButton.setVisible(true);
                    lessonButtonBar.setVisible(false);
                    back.setVisible(false);
                });
            }
        });
    }

}


