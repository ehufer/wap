

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.wap.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.mum.wap.project.domain.Definition;
import edu.mum.wap.project.domain.Word;

/**
 *
 * @author Keith Levi
 * @since 14 Feb 2016 A very simple example of JDBC connection to a MySQL
 * database. Uses same technique taught in the FPP course. Does not do any
 * connection management or pooling, just opens and closes the connection for
 * each request.
 */
public class DBconnection {

    DBProperties properties;
    public DBconnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL JDBC driver not found in DBConnection\n" + ex);
            System.exit(0);
        }
        properties = new DBProperties();
    }

    public List<Definition> getDefinitionsByTerm(String term) {
        String readQuery = "SELECT wordtype, definition from entries where word = ?";
        List<Definition> defs = new ArrayList<>();
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(readQuery);) {
        	stmt.setString(1, term);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                defs.add(new Definition(rs.getString("definition"),rs.getString("wordtype")));
            }
            stmt.close();

        } catch (SQLException s) {
            System.out.println("Exception thrown in retrieveDefinition ....");
            s.printStackTrace();
        }
        if(defs.size() == 0){
        	defs.add(new Definition("No definition found for the requested word: " + term,""));
        }
        return defs;
    }

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(properties.getDatabase(), properties.getDbUser(), properties.getDbPassword());
	}
    
    public List<Word> getAllWords() {
        String readQuery = "SELECT DISTINCT WORD FROM entries.entries ORDER BY 1";
        List<Word> words = new ArrayList<>();
        try (Connection con = getConnection();
                Statement stmt = con.createStatement();) {
            ResultSet rs = stmt.executeQuery(readQuery);
            while (rs.next()) {
                words.add( new Word(rs.getString("WORD")));
            }
            stmt.close();

        } catch (SQLException s) {
            System.out.println("Exception thrown in retrieveDefinition ....");
            s.printStackTrace();
        }
        return words;

    }
    
    public List<Word> getAllWordsBeginsWith(String begins) {
        String readQuery = "SELECT DISTINCT WORD FROM entries.entries WHERE WORD LIKE ? ORDER BY 1 LIMIT 10";
        List<Word> words = new ArrayList<>();
        try (Connection con = getConnection();
                    PreparedStatement stmt = con.prepareStatement(readQuery);) {
        	stmt.setString(1, begins + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                words.add( new Word(rs.getString("WORD")));
            }
            stmt.close();

        } catch (SQLException s) {
            System.out.println("Exception thrown in retrieveDefinition ....");
            s.printStackTrace();
        }
        return words;

    }
}
