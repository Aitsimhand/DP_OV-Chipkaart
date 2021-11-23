import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{

    private Connection conn;
    private OVChipkaartDAOPsql ovChipkaartDAOPsql;
    private String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=rJFQu34u";

    public ProductDAOPsql(Connection conn){
        try{
            this.conn = DriverManager.getConnection(url);
            System.out.println("Connection made.");
        }

        catch (Exception exception){
            System.out.println("Failed to connect.");
            exception.printStackTrace();
        }
    }

    public boolean save(Product product){
        String query = "INSERT INTO product(product_nummer, naam, beschrijving, prijs) VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeshrijving());
            preparedStatement.setDouble(4, product.getPrijs());
            preparedStatement.execute();
            return true;

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Het opslaan van het product in de database is mislukt, zie de stacktrace hierboven");
            return false;
        }
    }

    public boolean update(Product product){

        String query = "UPDATE product SET product_nummer=?, naam=?, beschrijving=?, prijs=? WHERE product_nummer=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, product.getProduct_nummer());
            preparedStatement.setString(2, product.getNaam());
            preparedStatement.setString(3, product.getBeshrijving());
            preparedStatement.setDouble(4, product.getPrijs());
            preparedStatement.setInt(5, product.getProduct_nummer());
            preparedStatement.execute();
            return true;
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om het product te updaten.");
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(Product product){

        String query = "DELETE FROM product WHERE product_nummer=" + product.getProduct_nummer();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
            System.out.println("Product succesvol verwijderd.");
            return true;
        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om product te verwijderen.");
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart)  {

        String query = "SELECT * FROM product p INNER JOIN ov_chipkaart_product ovp ON ovp.product_nummer = p.product_nummer WHERE ovp.kaart_nummer =" + ovChipkaart.getKaartNummer();
        List<Product> ovChipkaartenList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();



            while(rs.next()) {

                int nummer = rs.getInt(1);
                String naam = rs.getString(2);
                String beschrijving = rs.getString(3);
                double prijs = rs.getDouble(4);

                Product product = new Product(nummer, naam, beschrijving, prijs);

                product.setOVChipkaarten(ovChipkaart);
                ovChipkaartenList.add(product);
                System.out.println("Het ophalen van de lijst is succesvol. ");
            }


        }

        catch (Exception e){
            System.out.println("Het ophalen doormiddel van een ovChipkaart is niet gelukt.");
            e.printStackTrace();

        }

        return ovChipkaartenList;

    }

    public List<Product> findAll()  {
        List<Product> alleProductenLijst = new ArrayList<>();
        String query = "SELECT * FROM product";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();



            while(rs.next()) {
                int nummer = rs.getInt(1);
                String naam = rs.getString(2);
                String beschrijving = rs.getString(3);
                double prijs = rs.getDouble(4);

                // Zet het product in een product object
                Product product = new Product(nummer, naam, beschrijving, prijs);

                // Set de OV chipkaarten van het product
                List<OVChipkaart> OVChipkaarten = ovChipkaartDAOPsql.findAll() ;
                if (!OVChipkaarten.isEmpty()) {
                    for (OVChipkaart ov : OVChipkaarten) {
                        product.setOVChipkaarten(ov);
                    }
                }

                alleProductenLijst.add(product);

            }

        }

        catch (Exception e){
            System.out.println("Het ophalen van de de productenlijst is niet gelukt.");
            e.printStackTrace();
        }
        return alleProductenLijst;

    }



}
