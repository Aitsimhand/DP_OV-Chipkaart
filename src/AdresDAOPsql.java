import java.sql.*;
import java.util.concurrent.ExecutionException;

public class AdresDAOPsql {

    Connection conn;
    ReizigerDAO rdao;
    String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

    public AdresDAOPsql(Connection conn){
        try{
            this.conn = DriverManager.getConnection(url);

        }

        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public boolean save(Adres adres){
        int aderes_iD = adres.getId();
        String query = "INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES( ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getReiziger_id());
            preparedStatement.execute();

            return true;

        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
