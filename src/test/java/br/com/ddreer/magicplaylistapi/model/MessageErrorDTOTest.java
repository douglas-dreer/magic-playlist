package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.enums.StatusEnum;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class MessageErrorDTOTest extends InformationGenerator implements ModelBaseTest {
    private static String errorMessage;
    private static String localDateTime;
    private static String code;
    private static String details;
    private static MessageErrorDTO dto;
    private static StatusEnum status;

    @BeforeAll
    static void setup() {
        status = StatusEnum.ERROR;
        dto = createAMessageError();
        errorMessage = dto.getErrorMessage();
        localDateTime = dto.getLocalDateTime();
        code = dto.getCode();
        details = dto.getDetails();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        MessageErrorDTO result = new MessageErrorDTO(errorMessage, status);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        MessageErrorDTO result = new MessageErrorDTO();
        result.setErrorMessage(errorMessage);
        result.setCode(StatusEnum.INFO.getCode());
        result.setLocalDateTime(localDateTime);
        result.setDetails(details);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        MessageErrorDTO result = MessageErrorDTO.builder()
                .errorMessage(errorMessage)
                .details(details)
                .code(StatusEnum.OK.getCode())
                .localDateTime(localDateTime)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToEntity() {
        log.warn("This test is not necessary");
        assertTrue(true);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        final String toJSON = dto.toJSON();
        assertFalse(toJSON.isBlank());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConterToObject() {
        log.warn("This test is not necessary");
        assertTrue(true);
    }

    @Test
    public void mustReturnSuccessWhenInitializeWithCityEnum() {
        MessageErrorDTO result = new MessageErrorDTO("Error on CityEnum", StatusEnum.ERROR);
        assertNotNull(result);
    }

    private void checking(MessageErrorDTO data) {
        assertAll("Verify result list",
                () -> assertNotNull(data, "The messageErrorDTO should not be null"),
                () -> assertNotNull(data.getErrorMessage(), "The element errorMessage should not be null/empty"),
                () -> assertEquals(errorMessage, data.getErrorMessage(), "The element errorMessage must be the same as the one sent"),
                () -> assertNotNull(data.getCode(), "The element code should not be null/empty"),
                () -> assertNotNull(data.getDetails(), "The element details should not be null/empty"),
                () -> assertEquals(details, data.getDetails(), "The element details must be the same as the one sent"),
                () -> assertNotNull(data.getLocalDateTime(), "The element localDateTime should not be null/empty")
        );
    }
}
