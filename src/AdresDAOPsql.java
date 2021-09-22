import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    Connection conn;
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
        String query ="INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES( ?, ?, ?, ?, ?, ? )";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, adres.getId());
            preparedStatement.setString(2, adres.getPostcode());
            preparedStatement.setString(3, adres.getHuisnummer());
            preparedStatement.setString(4, adres.getStraat());
            preparedStatement.setString(5, adres.getWoonplaats());
            preparedStatement.setInt(6, adres.getId());
            preparedStatement.execute();
            return true;

        }

        catch (Exception e){
            System.out.println("De functie save() van adres failed");
            e.printStackTrace();
            return false;
        }

    }

    public boolean update(Adres adres){

        String query = "UPDATE adres SET postcode=?, huisnummer=?, straat=?, woonplaats=? WHERE adres_id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, adres.getPostcode());
            preparedStatement.setString(2, adres.getHuisnummer());
            preparedStatement.setString(3, adres.getStraat());
            preparedStatement.setString(4, adres.getWoonplaats());
            preparedStatement.setInt(5, adres.getId());
            preparedStatement.execute();
            return true;
        }

        catch (Exception e){
            System.out.println("Update Adres failed.");
            e.printStackTrace();
            return false;
        }


    }

    public boolean delete(Adres adres){
        String query = "DELETE FROM adres WHERE adres_id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, adres.getId());
            return true;

        }

        catch (Exception e){
            System.out.println("function adres delete failed");
            e.printStackTrace();
            return false;
        }
    }


    public Adres findByReiziger(Reiziger reiziger){

        String query = "SELECT * FROM adres WHERE reiziger_id=?";
        Adres adres = new Adres();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet myRs = preparedStatement.executeQuery();

            while (myRs.next()){
                adres.setId(myRs.getInt(1));
                adres.setPostcode(myRs.getString(2));
                adres.setHuisnummer(myRs.getString(3));
                adres.setStraat(myRs.getString(4));
                adres.setWoonplaats(myRs.getString(5));
                adres.setReiziger_id(myRs.getInt(6));

            }

            return adres;

        }

        catch (Exception e){
            System.out.println("Functie findReiziger() bij adres failed.");
            e.printStackTrace();
            return adres;
        }

    }

    public ArrayList<Adres> findAll(){
        ArrayList<Adres> adresArrayList = new ArrayList<>();
        String query = "SELECT * FROM adres";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet myRs = preparedStatement.executeQuery();

            while (myRs.next()){
                Adres adres = new Adres();
                adres.setId(myRs.getInt(1));
                adres.setPostcode(myRs.getString(2));
                adres.setHuisnummer(myRs.getString(3));
                adres.setStraat(myRs.getString(4));
                adres.setWoonplaats(myRs.getString(5));
                adres.setReiziger_id(myRs.getInt(6));
                adresArrayList.add(adres);
            }
            return adresArrayList;

        }

        catch (Exception e){

            e.printStackTrace();
            return adresArrayList;
        }
    }


}
