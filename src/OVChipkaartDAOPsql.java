import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OVChipkaartDAOPsql {

    Connection conn;
    String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

    public OVChipkaartDAOPsql(Connection conn){
        try {
            this.conn = DriverManager.getConnection(url);
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean save(OVChipkaart ovChipkaart){
        try {
            String query = "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES( ?, ?, ?, ?, ? )";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
            preparedStatement.setDate(2, java.sql.Date.valueOf(ovChipkaart.getGeldigTot()));
            preparedStatement.setInt(3, ovChipkaart.getKlasse());
            preparedStatement.setDouble(4, ovChipkaart.getSaldo());
            preparedStatement.setInt(5, ovChipkaart.getReizigerId());
            return true;
        }


        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(OVChipkaart ovChipkaart){
        try {
            String query = "UPDATE adres SET geldig_tot=?, klasse=?, saldo=? WHERE kaart_nummer=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(ovChipkaart.getGeldigTot()));
            preparedStatement.setInt(2, ovChipkaart.getKlasse());
            preparedStatement.setDouble(3, ovChipkaart.getSaldo());
            preparedStatement.setInt(4, ovChipkaart.getKaartNummer());
            return true;

        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(OVChipkaart ovChipkaart){
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
            return true;
        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }




}
