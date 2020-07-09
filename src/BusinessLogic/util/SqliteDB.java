package BusinessLogic.util;


import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

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
            ResultSet rs = statement.executeQuery("SELECT back FROM Flashcards");
            int i = 0;
            while(rs.next()){
                String flashcardFront = rs.getString("back");
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
    public void linkedFlaschard(ListArray linkedList, int deckId) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front, back, related1, related2 FROM Flashcards WHERE deck="+deckId+" AND revisionDate="+"'"+currentDate+"'");
            int i = 0;
            while(rs.next()){
                String front = rs.getString("front");
                String back = rs.getString("back");
                String related1 = rs.getString("related1");
                String related2 = rs.getString("related2");
                linkedList.insert(new Flashcard(front, back, related1, related2));
            }
        } catch (Exception e){
            System.out.println("Error al traer lista de tarjetas.");
        }
    }

    public void setEasy(Flashcard f){
        String sql = "UPDATE Flashcards SET revisionDate = ? WHERE front = ?";
        System.out.println(f.getNextRevision().plusDays(3).toString());
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, f.getNextRevision().plusDays(3).toString());
            pstmt.setString(2, f.getFront());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertIntoMap(HashMap map) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front, back, related1, related2 FROM Flashcards");
            while(rs.next()){
                String front = rs.getString("front");
                String back = rs.getString("back");
                String related1 = rs.getString("related1");
                String related2 = rs.getString("related2");
                map.put(front ,new Flashcard(front, back, related1, related2));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void insertIntoTree(TreeSet tree) {
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT front FROM Flashcards");
            while(rs.next()){
                String front = rs.getString("front");
                tree.add(front);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void modifyFlashcard(Flashcard f, int deck, String oldFront){
        String sql = "UPDATE Flashcards SET front = ?, back = ?, deck = ?, related1 = ?, related2 =? WHERE front = ?";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, f.getFront());
            pstmt.setString(2, f.getBack());
            pstmt.setInt(3, deck);
            pstmt.setString(4, f.getRelated1());
            pstmt.setString(5, f.getRelated2());
            pstmt.setString(6, oldFront);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Flashcard getFlashcardByFront(String oldFront){
        String sql = "SELECT front, back, related1, related2 FROM Flashcards WHERE front="+"'"+oldFront+"'";
        Flashcard temp = new Flashcard("","","","");
        try{
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                temp.setFront(rs.getString("front"));
                temp.setBack(rs.getString("back"));
                temp.setRelated1(rs.getString("related1"));
                temp.setRelated2(rs.getString("related2"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return temp;
    }
    public void insertFlashcard(String front, String back, String related1, String related2, int deck){
        LocalDate localDate = LocalDate.now();
        String sql = "INSERT INTO Flashcards(front, back, deck, related1, related2, revisionDate) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, front);
            pstmt.setString(2, back);
            pstmt.setInt(3, deck);
            pstmt.setString(4, related1);
            pstmt.setString(5, related2);
            pstmt.setString(6, localDate.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
    public void insertFlashcard(Flashcard f, int deck){
        LocalDate localDate = LocalDate.now();
        String sql = "INSERT INTO Flashcards(front, back, deck, related1, related2, revisionDate) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, f.getFront());
            pstmt.setString(2, f.getBack());
            pstmt.setInt(3, deck);
            pstmt.setString(4, f.getRelated1());
            pstmt.setString(5, f.getRelated2());
            pstmt.setString(6, localDate.toString());
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

    public Connection getC() {
        return c;
    }
    public void setC(Connection c) {
        this.c = c;
    }
}
