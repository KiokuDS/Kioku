package BusinessLogic.util;


import java.sql.*;

import BusinessLogic.Flashcard;
import BusinessLogic.util.LinkedList;
import BusinessLogic.util.StackArray;

public class SqliteDB {
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
    public void insertFlashcard(String front, String back, int deck){
        String sql = "INSERT INTO Flashcards(front, back, deck) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, front);
            pstmt.setString(2, back);
            pstmt.setInt(3, deck);
            pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
}
