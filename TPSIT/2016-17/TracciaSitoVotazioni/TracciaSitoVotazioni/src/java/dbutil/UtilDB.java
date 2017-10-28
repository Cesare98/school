package dbutil;


import beans.Registrazione;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilDB {

    final static String NOME_DB = "InfoVotazioni.db";

    final static String DRIVE = "C:\\dbsqlite\\";

    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + DRIVE + NOME_DB);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    public static boolean esisteUserName(String username) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PERSONA WHERE userName = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static boolean esisteCognomeNome(String cognomeNome) {
        boolean ris = false;
        try {
            Connection con = getConnection();
            String q = "SELECT * FROM PERSONA WHERE CognomeNome = ?";
            PreparedStatement st = con.prepareStatement(q);
            st.setString(1, cognomeNome);
            ResultSet rs = st.executeQuery();
            ris = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;
    }

    public static void inserimentoPersona(Registrazione r) {
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "INSERT INTO PERSONA (CognomeNome,Indirizzo,UserName,Password)"
                + "     VALUES(?,?,?,?)";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, r.getCognomeNome());
            st.setString(2, r.getIndirizzo());
            st.setString(3, r.getUserName());
            st.setString(4, r.getPassword1());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int verificaCredenziali(String usr, String pwd) {
        int ris = -1;
        Connection conn = dbutil.UtilDB.getConnection();
        String query = "SELECT IdPersona FROM PERSONA WHERE UserName=? AND Password=? ";
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.setString(1, usr);
            st.setString(2, pwd);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            if (rs.next()) ris=rs.getInt("IdPersona");
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ris;

    }
    
     public static void inserisciVoto(int idp,int idc,String voto) {
        Connection conn = dbutil.UtilDB.getConnection();
        String queryHaVotato = "INSERT INTO VOTO (IdPersona,IdCosaVotare)"
                + "     VALUES(?,?)";
        int qsi=voto.equals("S")?1:0;
        int qno=voto.equals("N")?1:0;
        int qas=voto.equals("A")?1:0;
        String queryAggiornaConteggi = "UPDATE COSAVOTARE SET Votanti=Votanti+1,"
                + "Favorevoli=Favorevoli+?, "
                + "Contrari=Contrari+?, "
                + "Astenuti=Astenuti+? "
                + "WHERE IdCosaVotare=?";
      
        PreparedStatement stV;
        PreparedStatement stA;
        try {
            stV = conn.prepareStatement(queryHaVotato);
            stV.setInt(1, idp);
            stV.setInt(2, idc);
            stV.executeUpdate();
            stA = conn.prepareStatement(queryAggiornaConteggi);
            stA.setInt(1, qsi);
            stA.setInt(2, qno);
            stA.setInt(3, qas);
            stA.setInt(4, idc);
            int c =stA.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UtilDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
