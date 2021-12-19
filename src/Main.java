import org.postgresql.shaded.com.ongres.scram.common.ScramAttributes;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException{
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

        Connection conn;
        conn  = DriverManager.getConnection(url);
        ReizigerDAO reizigerDAOPsql = new ReizigerDAOPsql(conn);
        AdresDAO adresDAOPsql = new AdresDAOPsql(conn);
        OVChipkaartDAO ovChipkaartDAOsql = new OVChipkaartDAOPsql(conn);
        ProductDAO productDAOsql = new ProductDAOPsql(conn);
        testReizigerDAO(reizigerDAOPsql);
        testAdresDAO(adresDAOPsql, reizigerDAOPsql);
        testOVchipkaartDAO(adresDAOPsql, reizigerDAOPsql, ovChipkaartDAOsql);
        testProductDAO(productDAOsql, ovChipkaartDAOsql);


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

    private static void testAdresDAO(AdresDAO adresDAO, ReizigerDAO rdao) throws SQLException{
        System.out.println("Hier beginnen de test's van de Adres klasse" + "\n" + "------------------------------------------------" );


        // Haal alle reizigers op uit de database
        System.out.println("Hier begint de test van de .save() functie van de adresDAO" + "\n" + "------------------------------------------------" );

        List<Adres> adressen = adresDAO.findAll();
        System.out.println("[Test] adresDAO.findAll() geeft de volgende Adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }

        // Hier wordt een nieuw adres aangemaakt en deze word opgeslagen in de database.
        System.out.println("Hier begint de test van de .save() functie van de adresDAO" + "\n" + "------------------------------------------------" );
        String gbDatum = "1997-10-24";
        Reiziger reizigerA = new Reiziger(6, "A","", "Ait Si'Mhand", gbDatum );
        rdao.save(reizigerA);

        Adres adresAchraf = new Adres(6, "2964BL", "26", "Irenestraat", "Groot-Ammers");
        reizigerA.setAdres(adresAchraf);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save()");
        adresDAO.save(adresAchraf);
        List<Adres> adressenNaUpdate = adresDAO.findAll();
        System.out.println(adressenNaUpdate.size() + " reizigers\n");

        //Hier wordt de update() functie van Adres aangeroepen en getest.
        System.out.println("Hier begint de test van de update functie van de adres klasse" + "\n" + "------------------------------------------------" );
        adresAchraf.setHuisnummer("30");
        try{
            adresDAO.update(adresAchraf);
            System.out.println("Het adres is geupdate.");
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om het adres te updaten in de database");
            e.printStackTrace();
        }

        //Hier wordt de functie .findbyreiziger() getest.
        System.out.println("Hier begint de test van de .findByReiziger() functie van de adresDAO" + "\n" + "------------------------------------------------" );
        try {
            adresDAO.findByReiziger(reizigerA);
            System.out.println("Adres is opgehaald.");
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de het adres te vinden bij de reiziger.");
            e.printStackTrace();
        }

        //Hier word de delete() functie van adres aangeroepen.
        System.out.println("Hier begint de test van de .delete functie van de adresDAO" + "\n" + "------------------------------------------------" );
        System.out.println("Test delete() methode");
        System.out.println("Eerst" + adressen.size());
        adressenNaUpdate.forEach((value) -> System.out.println(value));
        System.out.println("[test] delete() geeft -> ");
        adresDAO.delete(adresAchraf); //delete adres
        rdao.delete(reizigerA);
        List<Adres> adressenNaDelete = new ArrayList<>(adresDAO.findAll());
        adressenNaDelete.forEach((value) -> System.out.println(value));

    }

    private static void testOVchipkaartDAO(AdresDAO adresDAO, ReizigerDAO reizigerDAO, OVChipkaartDAO ovChipkaartDAO){
        System.out.println("Hier beginnen de test's van de OVchipkaart klasse" + "\n" + "------------------------------------------------" );

        // Haal alle kaarten op uit de database
        System.out.println("Hier begint de test van de OVchipkaart.findall() functie van de OVchipkaartDAO" + "\n" + "------------------------------------------------" );
        List<OVChipkaart> ovChipkaarts = ovChipkaartDAO.findAll();
        System.out.println("[Test] OVchipkaartDAO.findAll() geeft de volgende ov's:");
        for (OVChipkaart ov : ovChipkaarts) {
            System.out.println(ov);
        }


        //Hier wordt er een nieuw OVchipkaart object aangemaakt en gepersisteerd in de datbase.
        OVChipkaart ovChipkaart = new OVChipkaart();
        ovChipkaart.setKaartNummer(12345);
        ovChipkaart.setGeldigTot("2022-10-24");
        ovChipkaart.setKlasse(1);
        ovChipkaart.setSaldo(350.00);
        ovChipkaart.setReizigerId(5);

        // Hier wordt een bepaalde Chipkaart verwijderd uit de database.
        System.out.println("Hier begint de test van OVChipkaart.delete()" + "\n" + "------------------------------------------------" );
        try {
            ovChipkaartDAO.delete(ovChipkaart);
            System.out.println("De kaart is met succes verwijderd uit de database.");
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de kaart te verwijderen.");
            e.printStackTrace();
        }
        ovChipkaarts = ovChipkaartDAO.findAll();

        System.out.println("De database bevatte voor het opslaan " + ovChipkaarts.size() + "kaarten" + "\n");
        for (OVChipkaart ov : ovChipkaarts) {
            System.out.println(ov);
        }

        try {
            ovChipkaartDAO.save(ovChipkaart);
            System.out.println("De nieuwe chipkaart is opgeslagen.");
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om het nieuwe opbject op te slaan in de database.");
            e.printStackTrace();
        }
        ovChipkaarts = ovChipkaartDAO.findAll();
        System.out.println("En de databse bevat na het opslaan " + ovChipkaarts.size());

        // Hier wordt de update functie de OVchipkaartDAO aangeroepen en getest.


        try {
            ovChipkaart.setSaldo(20.00);
            ovChipkaartDAO.update(ovChipkaart);
            //Hier halen we de lijst opnieuw op om er zo voor te zorgen dat de lijst klopt en dus hier uitggeprint kan worden
            ovChipkaarts = ovChipkaartDAO.findAll();
            System.out.println("De kaart is met succes geupdate, het saldo is gewzijzigd");
            for (OVChipkaart ov : ovChipkaarts){
                System.out.println(ov);
            }
        }

        catch (Exception e ){
            System.out.println("Het is niet gelukt om de kaart te udpaten.");
            e.printStackTrace();
        }

        System.out.println("Hier begint de test van OVChipkaart.findByReiziger" + "\n" + "------------------------------------------------" );
        Reiziger reiziger = reizigerDAO.findById(5);
        //Hier wordt de functie getest van OvchipkaartDAO.findByReiziger() getest
        System.out.println(ovChipkaartDAO.findByReiziger(reiziger));
        System.out.println("DONE");

    }

    public static void testProductDAO(ProductDAO productDAO, OVChipkaartDAO ovChipkaartDAO) throws SQLException{
        //Hall alle producten op uit de database
        System.out.println("Hier begint de test van de .save() functie van de productDAO\n"  + "------------------------------------------------" );
        List<Product> producten = productDAO.findAll();
        System.out.println("[Test] productDAO.findAll() geeft de volgende Adressen voor de .save():");
        for (Product product: producten) {
            System.out.println(product);
            System.out.println("Aantal producten in de database voor de save: " + producten.size());
        }

        //Hier wordt een nieuw product aangemaakt en opgeslagen in de database.

        Product testProduct = new Product(12345, "SeniorenProduct", "Mobiliteit voor ouderen", 25.00);
        System.out.println("Producten in de database na de .save()");
        try {
            productDAO.save(testProduct);
            List<Product> productenNaSave = productDAO.findAll();
            for (Product product: productenNaSave){
                System.out.println(product);

            }
            System.out.println("Aantal" + productenNaSave.size());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Hier wordt het product geupdate en gepersisteerd in de database.
        try {
            testProduct.setPrijs(9999.00);
            productDAO.update(testProduct);
            System.out.println("                                " +testProduct.getProduct_nummer());
            System.out.println("Producten in de databse na de .update()");
            List<Product> productenNaUpdate = productDAO.findAll();
            for (Product product: productenNaUpdate){
                System.out.println(product);
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Hier word de delete functie getest");
        try {
            //Hier wordt het product verwijderd.
            System.out.println("De producten in de database na het verwijderen");
            productDAO.delete(testProduct);
            List<Product> productenNaDelete = productDAO.findAll();
            for (Product product :productenNaDelete ){
                System.out.println(product);
        }
            System.out.println("Aantal producten na delete:" + productenNaDelete.size());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}