import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findByOVChipkaart(OVChipkaart ov) throws SQLException;
    List<Product> findAll() throws SQLException;
}
