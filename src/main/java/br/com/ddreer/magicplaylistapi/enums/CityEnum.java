package br.com.ddreer.magicplaylistapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CityEnum {
    NYC("New York City", "New York", "NY", "United States"),
    LA("Los Angeles", "California", "CA", "United States"),
    TOR("Toronto", "Ontario", "ON", "Canada"),
    MEX("Mexico City", "Ciudad de Mexico", "CMX", "Mexico"),

    // América Central
    SJO("San Jose", "San Jose", null, "Costa Rica"),
    PTY("Panama City", "Panama", null, "Panama"),

    // América do Sul
    BUE("Buenos Aires", "Buenos Aires", null, "Argentina"),
    RIO("Rio de Janeiro", "Rio de Janeiro", "RJ", "Brazil"),
    SAO("São Paulo", "São Paulo", "SP", "Brazil"),
    MGA("Maringá", "Paraná", "PR", "Brazil"),
    LIM("Lima", "Lima", null, "Peru"),

    // Europa
    LDN("London", "England", null, "United Kingdom"),
    PAR("Paris", "Île-de-France", null, "France"),
    BER("Berlin", "Berlin", null, "Germany"),
    ROM("Rome", "Lazio", null, "Italy"),

    // Ásia
    TKY("Tokyo", "Tokyo", null, "Japan"),
    BEI("Beijing", "Beijing", null, "China"),
    DEL("New Delhi", "Delhi", null, "India"),
    SEO("Seoul", "Seoul", null, "South Korea"),

    // Oceania
    SYD("Sydney", "New South Wales", "NSW", "Australia"),
    AKL("Auckland", "Auckland", null, "New Zealand");

    private final String cityName;
    private final String state;
    private final String stateCode;
    private final String country;
}
