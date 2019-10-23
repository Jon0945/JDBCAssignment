package Se.JLexicon.John.data;

import Se.JLexicon.John.model.City;
import java.util.List;
import java.util.Optional;

public interface CityDao {
    Optional<City> findById(int id);
    List<City> findByCode(String code);
    List<City> findByName(String name);
    List<City> findAll();
    City add(City city);
    City update(City city);
    int delete(City city);
}
