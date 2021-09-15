import java.sql.*;
import java.util.*;

public class ReizigerDAOPsql implements ReizigerDAO {

    Connection conn;

    String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

    public ReizigerDAOPsql(Connection conn){

        try {
            this.conn  = DriverManager.getConnection(url);
            System.out.println("Connection made.");
        }

        catch (Exception e){
            System.out.println("Failed to connect.");
            e.printStackTrace();
        }
    }

    public boolean save(Reiziger reiziger){

        String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES( ?, ?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getId());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, java.sql.Date.valueOf(reiziger.getGeboortedatum()));
            preparedStatement.execute();
            return true;
        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Reiziger reiziger){


        String query = "UPDATE reiziger SET voorletters=?, tussenvoegsel=?, achternaam=?, geboortedatum=? WHERE reiziger_id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, java.sql.Date.valueOf(reiziger.getGeboortedatum()));
            preparedStatement.setInt(5, reiziger.getId() );
            preparedStatement.execute();
            return true;
        }

        catch (Exception e){
            System.out.println("Update failed");
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(Reiziger reiziger){
        String query = "DELETE FROM reiziger WHERE reiziger_id=" + reiziger.getId();


        try {

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
            return true;

        }

        catch (Exception e){
            System.out.println("failed to delete user.");
            e.printStackTrace();
            return false;
        }

    }

    public Reiziger findById(int id){
        String query = "SELECT * FROM reiziger WHERE reiziger_id=" + id;

        Reiziger voorbeeld = new Reiziger();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet myRs = preparedStatement.executeQuery();


            while(myRs.next()){
                voorbeeld.setId(myRs.getInt(1));
                voorbeeld.setVoorletters(myRs.getString(2));
                voorbeeld.setTussenvoegsel(myRs.getString(3));
                voorbeeld.setAchternaam(myRs.getString(4));
                voorbeeld.setGeboortedatum(myRs.getString(5));

                System.out.println("#" + myRs.getString(1) + ": "
                        + myRs.getString(2) + ". " + myRs.getString(4)
                        + " " + "(" + myRs.getString(5) + ")");

            }

            return voorbeeld;
        }

        catch (Exception e){
            System.out.println("Geen reiziger om te returnen");
            e.printStackTrace();
            return voorbeeld;
        }
    }

    public List<Reiziger> findByGbdatum(String datum){
        ArrayList<Reiziger> reizigerLijst = new ArrayList<>();
        String query = "SELECT * FROM reiziger WHERE geboortedatum=?";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
                Reiziger reiziger = new Reiziger(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));
                System.out.println(reiziger);
                reizigerLijst.add(reiziger);

            }

            return reizigerLijst;


        }

        catch (Exception e) {
            System.out.println("findByGbDatum failed.");
            e.printStackTrace();
        }
        return reizigerLijst;


    }

    public List<Reiziger> findAll(){
        ArrayList<Reiziger> reizigersList = new ArrayList<>();
        String query = "SELECT * FROM reiziger";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Reiziger reiziger = new Reiziger();
                reiziger.setId(resultSet.getInt(1));
                reiziger.setVoorletters(resultSet.getString(2));
                reiziger.setTussenvoegsel(resultSet.getString(3));
                reiziger.setAchternaam(resultSet.getString(4));
                reiziger.setGeboortedatum(resultSet.getString(5));
                reizigersList.add(reiziger);
            }

        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de lijst op te halen.");
            e.printStackTrace();
        }
        return reizigersList;
    }
}
