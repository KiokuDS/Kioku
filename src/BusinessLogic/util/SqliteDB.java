package BusinessLogic.util;


import java.sql.*;
import java.time.LocalDate;

import BusinessLogic.Flashcard;
import BusinessLogic.util.StackArray;
public class SqliteDB {

    LocalDate currentDate = LocalDate.now();
    Connection c = null;

    public SqliteDB(){
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:app.sqlite");
            System.out.println("Connected to DB OK!!!");
        } catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
    }
    public void listsDecks(LinkedList linkedlist) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM Decks");
            int i = 0;
            while(rs.next()){
                String deckName = rs.getString("name");
                linkedlist.insert(i, deckName);
                i++;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void listsFlashcards(LinkedList linkedlist) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front FROM Flashcards");
            int i = 0;
            while(rs.next()){
                String flashcardFront = rs.getString("front");
                linkedlist.insert(i, flashcardFront);
                i++;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public String getDeckName(int id) {
        String deckName = "";
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM Decks WHERE id = "+id);
            deckName = rs.getString("name");
            } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deckName;
    }
    public void listsFlaschard(StackArray stackArray, int deckId) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front, back FROM Flashcards WHERE deck="+deckId);
            int i = 0;
            while(rs.next()){
                String front = rs.getString("front");
                String back = rs.getString("back");
                stackArray.push(new Flashcard(front, back));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void linkedFlaschard(ListArray linkedList, int deckId) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front, back FROM Flashcards WHERE deck="+deckId+" AND revisionDate="+"'"+currentDate+"'");
            int i = 0;
            while(rs.next()){
                String front = rs.getString("front");
                String back = rs.getString("back");
                linkedList.insert(new Flashcard(front, back));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void insertFlashcard(String front, String back, int deck){
        LocalDate localDate = LocalDate.now();
        String sql = "INSERT INTO Flashcards(front, back, deck, revisionDate) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, front);
            pstmt.setString(2, back);
            pstmt.setInt(3, deck);
            pstmt.setString(4, localDate.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    public int getRelated1ById(int id){
        int related = -1;
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT related1 FROM Flashcards WHERE id="+id+1);
            while(rs.next()){
                related = rs.getInt("related1");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return related;
    }
    public int getRelated2ById(int id){
        int related = -1;
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT related2 FROM Flashcards WHERE id="+id+1);
            while(rs.next()){
                related = rs.getInt("related2");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return related;
    }

    public void insertDeck(String deck){
        String sql = "INSERT INTO Decks(name) VALUES(?)";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, deck);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void closeConnection(){
        try{
            c.close();
        } catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
    }
    /*public ResultSet executeQuery(String query){
        try{
            Statement statement = c.createStatement();
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (Exception e){
            System.out.println("Error al hacer query");
        }
       return rs;
    }*/

    public Connection getC() {
        return c;
    }
    public void setC(Connection c) {
        this.c = c;
    }
}
