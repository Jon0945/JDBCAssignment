package Se.JLexicon.John;

import Se.JLexicon.John.data.CityDaoJDBC;
import Se.JLexicon.John.model.City;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        CityDaoJDBC jdbcYAY = new CityDaoJDBC();
        List<City> testlist;
        testlist = jdbcYAY.findAll();
        testlist.forEach(System.out::println);
    }
}
