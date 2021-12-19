import java.util.ArrayList;
import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private List<OVChipkaart> OVChipkaarten = new ArrayList<>();


    public Product(int product_nummer, String naam, String beshrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beshrijving;
        this.prijs = prijs;
    }

    public Product(){
        product_nummer = 0;
        naam = "";
        beschrijving = "";
        prijs = 0;


    }


    public void setOVChipkaarten(List<OVChipkaart> ovChipkaarten) {
        for (OVChipkaart ovChipkaart: ovChipkaarten){
            this.OVChipkaarten.add(ovChipkaart);
        }
    }

    public void removeOVchipkaart(OVChipkaart ovChipkaart){
        this.getOVChipkaarten().remove(ovChipkaart);
    }

    public List<OVChipkaart> getOVChipkaarten() { return OVChipkaarten; }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeshrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setBeshrijving(String beshrijving) {
        this.beschrijving = beshrijving;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beshrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", OVChipkaarten=" + OVChipkaarten +
                '}';
    }
}
