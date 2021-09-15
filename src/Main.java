import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException{
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

        Connection conn;
        conn  = DriverManager.getConnection(url);
        ReizigerDAO reizigerDAOPsql = new ReizigerDAOPsql(conn);
        AdresDAO adresDAOPsql = new AdresDAOPsql(conn);
        System.out.println("Objet has been created");
        testReizigerDAO(reizigerDAOPsql);
        testAdresDAO(adresDAOPsql, reizigerDAOPsql);


    }


    private static void testReizigerDAO( ReizigerDAO rdao) throws SQLException {

        System.out.println("\n---------- Test ReizigerDAO -------------");


        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", gbdatum);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //Hier word een reiziger geruturned op basis van de aangegeven ID.

        System.out.println("De {TEST} van de functie findById met ID 77" + "\n" + "---------------------------------------");
        System.out.println(rdao.findById(77));

        System.out.println("De {TEST} van de functie findByGbdatum()" + "\n" + "---------------------------------------");
        for (Reiziger r: rdao.findByGbdatum("1981-03-14")
             ) {
            System.out.println(r);
            
        }





        // De gegevens van een bestaande reiziger worden aangepast in een database.

        System.out.println("De {TEST} van de functie findByGbdatum()" + "\n" + "---------------------------------------");

        String geboorteDatum = "1950-04-12";
        sietske.setGeboortedatum(geboorteDatum);
        rdao.update(sietske);
        System.out.println("Reiziger Sietske is geupdate in de database.");


        // De verwijder een specifieke reiziger uit de database.

        System.out.println("De {TEST} van de functie delte() rsultaten" + "\n" + "------------------------------------");
        try {
            rdao.delete(sietske);
            System.out.println("Reiziger is met succes verwijderd");
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de reiziger te verwijderen.");
            e.printStackTrace();
        }



    }

    private static void testAdresDAO(AdresDAO adresDAO, ReizigerDAO reizigerDAO) throws SQLException{
        System.out.println("Hier beginnen de test's van de Adres klasse" + "\n" + "------------------------------------------------" );


        // Haal alle reizigers op uit de database
        List<Adres> adressen = adresDAO.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }

        // Hier word een nieuw adres aangemaakt en deze word opgeslagen in de database.
        Reiziger reizigerA = new Reiziger(6, "A","", "Ait Si'Mhand", "24-10-1997" );
        reizigerDAO.save(reizigerA);

        Adres adresAchraf = new Adres(6, "2964BL", "26", "Irenestraat", "Groot-Ammers");
        reizigerA.setAdres(adresAchraf);
        adresDAO.save(adresAchraf);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");


    }


}
