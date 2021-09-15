import java.util.Date;

public class Reiziger {
    int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private String geboortedatum;
    private Adres adres;

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adres getAdres() {
        return adres;
    }

    public Reiziger(){

    }

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, String geboortedatum){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoorletters(){
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }
    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return "De gegevens van deze reiziger"+ "\n"
                + "Naam: " + voorletters + " " + tussenvoegsel + " " + achternaam + "  " + geboortedatum;
    }
}
