package br.com.ddreer.magicplaylistapi.dto;

import br.com.ddreer.magicplaylistapi.entity.Record;
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
public class RecordDTOTest extends InformationGenerator implements ModelBaseTest {
    private static RecordDTO dto = new RecordDTO();

    private static UUID uuid = UUID.randomUUID();
    private static String name = "Globo Records";
    private static CityEnum city = CityEnum.RIO;
    private static int foundingYear = 1999;
    private static boolean isActive = true;

    @BeforeAll
    public static void setup() {
        dto = createARecordDTOForTests();
        uuid = dto.getId();
        name = dto.getName();
        city = dto.getCity();
        foundingYear = dto.getFoundingYear();
        isActive = dto.isActive();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        RecordDTO result = new RecordDTO(uuid, name, city, foundingYear, isActive);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        RecordDTO result = new RecordDTO();
        result.setId(uuid);
        result.setName(name);
        result.setCity(city);
        result.setFoundingYear(foundingYear);
        result.setActive(isActive);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        RecordDTO result = RecordDTO.builder()
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
    public void mustReturnSuccessWhenConvertToEntity() {
        Record entity = dto.toEntity();
        checking(entity);
    }


    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        final String toJSON = dto.toJSON();
        assertFalse(toJSON.isBlank());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConterToObject() throws IOException {
        final String toJSON = dto.toJSON();
        checking(dto.toObject(toJSON));
    }

    private void checking(RecordDTO dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The record should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element title must be the same as the one sent"),
                () -> assertNotNull(dados.getCity(), "The city should not be null"),
                () -> assertEquals(city, dados.getCity(), "The element city must be the same as the one sent"),
                () -> assertEquals(foundingYear, dados.getFoundingYear(), "The element founding year must be the same as the one sent"),
                () -> assertTrue(dados.isActive(), "The element is active must be the same as the one sent")
        );
    }

    private void checking(Record dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The record should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element title must be the same as the one sent"),
                () -> assertNotNull(dados.getCity(), "The city should not be null"),
                () -> assertEquals(city, dados.getCity(), "The element city must be the same as the one sent"),
                () -> assertEquals(foundingYear, dados.getFoundingYear(), "The element founding year must be the same as the one sent"),
                () -> assertTrue(dados.isActive(), "The element is active must be the same as the one sent")
        );
    }

}
