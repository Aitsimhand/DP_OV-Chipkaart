import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

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
            preparedStatement.executeQuery();

            return true;
        }

        catch (Exception e){
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Reiziger reiziger){

        int reiziger_ID = reiziger.getId();
        String query = "UPDATE reiziger SET reiziger_id == reiziger_I(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES( ?, ?, ?, ?, ?)";

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
            ResultSet myRs = preparedStatement.executeQuery(query);


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
        List<Reiziger> reizigerLijst = new List<Reiziger>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Reiziger> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Reiziger reiziger) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Reiziger> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Reiziger> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Reiziger get(int index) {
                return null;
            }

            @Override
            public Reiziger set(int index, Reiziger element) {
                return null;
            }

            @Override
            public void add(int index, Reiziger element) {

            }

            @Override
            public Reiziger remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Reiziger> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Reiziger> listIterator(int index) {
                return null;
            }

            @Override
            public List<Reiziger> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        String query = "SELECT * FROM reiziger WHERE geboortedatum=" + datum;

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Reiziger reiziger = new Reiziger(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5));

                reizigerLijst.add(reiziger);
            }


        }

        catch (Exception e) {
            System.out.println("findByGbDatum failed.");
            e.printStackTrace();
        }
        return reizigerLijst;


    }

    public List<Reiziger> findAll(){
        List<Reiziger> reizigers = null;
        String query = "SELECT * FROM reiziger";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){


            }

        }

        catch (Exception e){
            System.out.println("Het is niet gelukt om de lijst op te halen.");
            e.printStackTrace();
        }
        return reizigers;
    }
}
