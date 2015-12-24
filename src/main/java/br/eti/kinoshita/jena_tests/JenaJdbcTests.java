package br.eti.kinoshita.jena_tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.jena.jdbc.tdb.TDBDriver;

public class JenaJdbcTests {

    public static void main(String[] args) throws Exception {
        try {
            TDBDriver.register();
            
            Connection con = DriverManager.getConnection("jdbc:jena:tdb:location=/tmp/jena123&must-exist=false");
            con.createStatement().execute("SELECT * WHERE { ?a ?b ?c }");
            con.close();
            System.out.println("OK!");
        } catch (SQLException e) {
            throw e;
        }
    }
    
}
