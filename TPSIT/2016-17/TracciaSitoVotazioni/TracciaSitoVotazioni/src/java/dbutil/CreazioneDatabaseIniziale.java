package dbutil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CreazioneDatabaseIniziale {

    public static void creaBD(Connection conn, String nomeFileDB) throws IOException, SQLException {
        String testoSQL = "";
        BufferedReader b = new BufferedReader(new FileReader(nomeFileDB));
        String st;
        while ((st = b.readLine()) != null) {
            testoSQL += st;
        }
        b.close();
        String[] comandiSQL = testoSQL.split(";");

        for (int i = 0; i < comandiSQL.length; i++) {
            try {
                Statement stmt = conn.createStatement();
                System.out.println("Esecuzione di "+comandiSQL[i]);        
                stmt.executeUpdate(comandiSQL[i]);
            } catch (SQLException ex) {
                System.out.println("Eccezione SQL: " + ex.toString());
            }
        }

    }

    static String NOME_DB = "InfoVotazioni.db";

    public static void main(String[] args) throws IOException {
        Connection conn = UtilDB.getConnection();
        try {
            creaBD(conn, "fileComandiCreazione.sql");
            conn.close();  
            System.out.println("DB creato");
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
