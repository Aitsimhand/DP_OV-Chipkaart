import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{

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

    @Override
    public boolean save(OVChipkaart ovChipkaart){
        try {
            String query = "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES( ?, ?, ?, ?, ? )";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
            preparedStatement.setDate(2, java.sql.Date.valueOf(ovChipkaart.getGeldigTot()));
            preparedStatement.setInt(3, ovChipkaart.getKlasse());
            preparedStatement.setDouble(4, ovChipkaart.getSaldo());
            preparedStatement.setInt(5, ovChipkaart.getReizigerId());
            preparedStatement.execute();
            return true;
        }


        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean update(OVChipkaart ovChipkaart){
        try {
            String query = "UPDATE ov_chipkaart SET geldig_tot=?, klasse=?, saldo=? WHERE kaart_nummer=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(ovChipkaart.getGeldigTot()));
            preparedStatement.setInt(2, ovChipkaart.getKlasse());
            preparedStatement.setDouble(3, ovChipkaart.getSaldo());
            preparedStatement.setInt(4, ovChipkaart.getKaartNummer());
            preparedStatement.execute();
            return true;

        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public boolean delete(OVChipkaart ovChipkaart){
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
            preparedStatement.execute();
            return true;
        }

        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public OVChipkaart findByReiziger(Reiziger reiziger){
        String query =  "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
        OVChipkaart ovChipkaart = new OVChipkaart();

       try {
           PreparedStatement preparedStatement = conn.prepareStatement(query);
           preparedStatement.setInt(1, reiziger.getId());
           ResultSet myRs = preparedStatement.executeQuery();

           while (myRs.next()){
               ovChipkaart.setKaartNummer( myRs.getInt(1));
               ovChipkaart.setGeldigTot(myRs.getString(2));
               ovChipkaart.setKlasse(myRs.getInt(3));
               ovChipkaart.setSaldo(myRs.getDouble(4));
               ovChipkaart.setReizigerId(myRs.getInt(5));
           }
       }

       catch (Exception e){
           System.out.println("Het is niet gelukt om de OvChipkaart op te halen");
           e.printStackTrace();

       }
        return ovChipkaart;

    }

    public List<OVChipkaart> findAll(){
        List<OVChipkaart> ovChipkaartArrayList = new ArrayList<>();
        String query = "SELECT * FROM ov_chipkaart";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet myRs = preparedStatement.executeQuery();

            while (myRs.next()){
                OVChipkaart ovChipkaart = new OVChipkaart();
                ovChipkaart.setKaartNummer(myRs.getInt(1));
                ovChipkaart.setGeldigTot(myRs.getString(2));
                ovChipkaart.setKlasse(myRs.getInt(3));
                ovChipkaart.setSaldo(myRs.getDouble(4));
                ovChipkaart.setReizigerId(myRs.getInt(5));
                ovChipkaartArrayList.add(ovChipkaart);
            }
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de functie findAll van ovchipkaart op te halen");
            e.printStackTrace();
        }
        return ovChipkaartArrayList;
    }




}
