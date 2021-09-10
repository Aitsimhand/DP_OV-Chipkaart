import java.sql.*;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException{
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

        Connection conn;
        conn  = DriverManager.getConnection(url);
        ReizigerDAOPsql reizigerDAOPsql = new ReizigerDAOPsql(conn);

        testReizigerDAO(reizigerDAOPsql);


    }


    private static void testReizigerDAO( ReizigerDAO rdao) throws SQLException {

        Connection conn;
        System.out.println("\n---------- Test ReizigerDAO -------------");


        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", gbdatum);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

}
