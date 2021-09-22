import java.util.ArrayList;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart);
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    Adres findByReiziger(Reiziger reiziger);
    ArrayList<Adres> findAll();

}
