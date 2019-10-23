package Se.JLexicon.John.data;

import Se.JLexicon.John.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityDaoJDBC implements CityDao {
    private static final String FIND_BY_ID = "SELECT * FROM city WHERE ID = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM city WHERE CountryCode = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM city WHERE Name = ?";
    private static final String FIND_ALL = "SELECT * FROM city";
    private static final String ADD_CITY = "INSERT INTO city (Name,CountryCode,District,Population" +
            "VALUES(?,?,?,?)";
    private static final String UPDATE = "UPDATE city SET Name = ?, CountryCode = ?, District = ?, Population = ? WHERE ID = ?";
    private static final String DELETE = "DELTEE FROM city WHERE ID = ?";


    private PreparedStatement createFindById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1,id);
        return statement;
    }

    private PreparedStatement createFindByCode(Connection connection, String code) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE);
        statement.setString(1,code);
        return statement;
    }

    private PreparedStatement createFindByName(Connection connection, String name) throws  SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME);
        statement.setString(1,name);
        return  statement;
    }

    private PreparedStatement createFindAll(Connection connection) throws SQLException {
        return connection.prepareStatement(FIND_ALL);
    }

    private PreparedStatement createAdd(Connection connection, City newCity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_CITY, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,newCity.getCityName());
        statement.setString(2,newCity.getCountryCode());
        statement.setString(3,newCity.getCityDistrict());
        statement.setInt(4,newCity.getCityPopulation());
        statement.executeUpdate();
        return statement;
    }

    private PreparedStatement createUpdate(Connection connection, City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE);
        statement.setString(1,city.getCityName());
        statement.setString(2,city.getCountryCode());
        statement.setString(3,city.getCityDistrict());
        statement.setInt(4,city.getCityPopulation());
        statement.setInt(5,city.getCityID());
        return statement;
    }

    private PreparedStatement createDelete(Connection connection, int cityID) throws  SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE);
        statement.setInt(1,cityID);
        return statement;
    }

    private City cityFromResultSet(ResultSet resultSet) throws SQLException {
        return new City(
                resultSet.getInt("ID"),
                resultSet.getString("Name"),
                resultSet.getString("CountryCode"),
                resultSet.getString("District"),
                resultSet.getInt("Population")
        );
    }

    @Override
    public Optional<City> findById(int cityId){
        City found = null;
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindById(connection, cityId);
                ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()){
                found = cityFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(found == null){
            return Optional.empty();
        }else{
            return Optional.of(found);
        }
    }

    @Override
    public List<City> findByCode(String code) {
        List<City> resultlist = new ArrayList<>();
        try(
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindByCode(connection, code);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                resultlist.add(cityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultlist;
    }

    @Override
    public List<City> findByName(String name) {
        List<City> resultlist = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindByName(connection,name);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                resultlist.add(cityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  resultlist;
    }

    @Override
    public List<City> findAll() {
        List<City> resultlist = new ArrayList<>();
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createFindAll(connection);
                ResultSet resultSet = statement.executeQuery()
        ){
            while (resultSet.next()) {
                resultlist.add(cityFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  resultlist;
    }

    @Override
    public City add(City newCity){
        if(newCity.getCityID() !=0){
            return newCity;
        }

        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createAdd(connection,newCity);
                ResultSet resultSet = statement.getGeneratedKeys();
        ){
                int cityID = 0;
                while(resultSet.next()){
                    cityID = resultSet.getInt(1);
                }

                newCity = new City(
                        cityID,
                        newCity.getCityName(),
                        newCity.getCountryCode(),
                        newCity.getCityDistrict(),
                        newCity.getCityPopulation()
                );
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return newCity;
    }

    @Override
    public City update(City city) throws IllegalArgumentException{
        if(city.getCityID() == 0){
            throw new IllegalArgumentException("Can not update object, city is not yet persisted");
        }
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createUpdate(connection, city)
        ){
                statement.execute();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public int delete(City city){
        int result = 0;
        try (
                Connection connection = Database.getConnection();
                PreparedStatement statement = createDelete(connection, city.getCityID())
        ){
            result = statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();;
        }
        return result;
    }
}



