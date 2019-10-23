package Se.JLexicon.John.model;

import java.util.Objects;

public class City {
    private int cityID;
    private String cityName;
    private String countryCode;
    private String cityDistrict;
    private int cityPopulation;

    public int getCityID() { return cityID; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getCityDistrict() { return cityDistrict; }
    public void setCityDistrict(String cityDistrict) { this.cityDistrict = cityDistrict; }
    public int getCityPopulation() { return cityPopulation; }
    public void setCityPopulation(int cityPopulation) { this.cityPopulation = cityPopulation; }

    public City(int cityID, String cityName, String countryCode, String cityDistrict, int cityPopulation) {
        this.cityID = cityID;
        setCityName(cityName);
        setCountryCode(countryCode);
        setCityDistrict(cityDistrict);
        setCityPopulation(cityPopulation);
    }

    public City(String cityName, String countryCode, String cityDistrict, int cityPopulation) {
        this(0,cityName,countryCode,cityDistrict,cityPopulation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return cityID == city.cityID &&
                cityPopulation == city.cityPopulation &&
                Objects.equals(cityName, city.cityName) &&
                Objects.equals(countryCode, city.countryCode) &&
                Objects.equals(cityDistrict, city.cityDistrict);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityID, cityName, countryCode, cityDistrict, cityPopulation);
    }

    @Override
    public String toString() {
        return "City ID: " + cityID +
                "\nCity Name: " + cityName +
                "\nCountry Code: " + countryCode +
                "\nCity District: " + cityDistrict +
                "\nCity Population: " + cityPopulation +"\n";
    }
}
