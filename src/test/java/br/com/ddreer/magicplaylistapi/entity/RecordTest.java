package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecordTest extends InformationGenerator implements BaseEntityTest {
    private static Record entity = new Record();
    private static UUID uuid;
    private static String name;
    private static CityEnum city;
    private static int foundingYear;
    private static boolean isActive;

    @BeforeAll
    public static void setup() {
        entity = createARecordForTests();
        uuid = entity.getId();
        name = entity.getName();
        city = entity.getCity();
        foundingYear = entity.getFoundingYear();
        isActive = entity.isActive();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        final Record entity = new Record(uuid, name, city, foundingYear, isActive);
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        final Record result = new Record();
        result.setId(uuid);
        result.setName(name);
        result.setCity(city);
        result.setFoundingYear(foundingYear);
        result.setActive(isActive);
        checking(result);
    }

    @Override
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        final Record result = Record.builder()
                .id(uuid)
                .name(name)
                .city(city)
                .foundingYear(foundingYear)
                .isActive(isActive)
                .build();
        checking(result);

    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() {
        final RecordDTO resultDTO = entity.toDTO();
        checking(resultDTO);
    }

    @Override
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        final String toJSON = entity.toJSON();
        assertNotNull(toJSON);
    }

    @Override
    public void mustReturnSUccessWhenConterToObject() throws IOException {
        final String toJSON = entity.toJSON();
        checking(entity.toObject(toJSON));

    }

    private void checking(Record dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The music should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element name must be the same as the one sent"),
                () -> assertNotNull(dados.getCity(), "The city should not be empty"),
                () -> assertEquals(city, dados.getCity(), "The element music composers must be the same as the one sent"),
                () -> assertEquals(foundingYear, dados.getFoundingYear(), "The element city must be the same as the one sent"),
                () -> assertTrue(dados.isActive(), "The element isActive must be the same as the one sent")
        );
    }

    private void checking(RecordDTO dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The music should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element name must be the same as the one sent"),
                () -> assertNotNull(dados.getCity(), "The city should not be empty"),
                () -> assertEquals(city, dados.getCity(), "The element music composers must be the same as the one sent"),
                () -> assertEquals(foundingYear, dados.getFoundingYear(), "The element city must be the same as the one sent"),
                () -> assertTrue(dados.isActive(), "The element isActive must be the same as the one sent")
        );
    }
}
