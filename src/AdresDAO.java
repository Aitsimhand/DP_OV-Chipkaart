import java.util.ArrayList;

public interface AdresDAO {
    boolean save();
    boolean update();
    boolean delete();
    Adres findByReiziger(Reiziger reiziger);
    ArrayList<Adres> findAll();

}
