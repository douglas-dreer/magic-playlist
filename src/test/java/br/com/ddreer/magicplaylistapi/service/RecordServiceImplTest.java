package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.repository.RecordRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecordServiceImplTest {
    private final static String NOT_SAVED = "Unable to save record";
    private static Record recordSaved = new Record();
    private static RecordDTO recordDTO = new RecordDTO();
    private static List<RecordDTO> recordDTOList = new ArrayList<>();
    private static List<Record> recordList = new ArrayList<>();
    @InjectMocks
    private RecordServiceImpl service;
    @Mock
    private RecordRepository repository;

    @BeforeAll
    public static void setup() {
        recordSaved.setId(UUID.randomUUID());
        recordSaved.setName("Globa Records");
        recordSaved.setCity(CityEnum.RIO);
        recordSaved.setFoundingYear(1990);
        recordSaved.setActive(true);

        recordDTO = recordSaved.toDTO();
        recordDTOList = Collections.singletonList(recordDTO);
        recordList = Collections.singletonList(recordSaved);
    }

    @Test
    void mustReturnListRecordDTOWhenList() {
        when(repository.findAll()).thenReturn(recordList);

        List<RecordDTO> resultList = service.list();

        assertAll("Verify result",
                () -> assertFalse(resultList.isEmpty(), "Result list should not be empty"),
                () -> assertEquals(1, resultList.size(), "The results list must contain 1 element")
        );
    }

    @Test
    void mustReturnRecordDTOWhenFindId() {
        when(repository.findById(any())).thenReturn(Optional.of(recordSaved));

        UUID id = recordSaved.getId();

        RecordDTO result = service.findById(id);

        assertAll(
                "item",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertEquals(id, result.getId(), "The element id must be the same as the one sent")
        );
    }

    @Test
    void mustReturnBusinessExceptionWhenFindId() {
        when(repository.findById(any())).thenThrow(new BusinessException("Not found record"));
        UUID id = recordSaved.getId();

        RecordDTO result = service.findById(id);

        assertNull(result);
    }

    @Test
    void mustReturnListRecordDTOWhenFindByName() {
        when(repository.findAllByNameLikeIgnoreCase(anyString())).thenReturn(recordDTOList);

        String name = recordSaved.getName();

        List<RecordDTO> result = service.findByName(name);

        assertAll("Verify result list",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertFalse(result.isEmpty(), "Result list should not be empty"),
                () -> result.forEach(item -> assertTrue(item.getName().contains(name), "Each RecordDTO name should contain the search term"))
        );
    }

    @Test
    void mustReturnRecordDTOWhenSaveRecord() {
        when(repository.save(any())).thenReturn(recordSaved);

        RecordDTO result = service.save(recordDTO);

        assertNotNull(result);
    }

    @Test
    void mustReturnRecordDTOSBusinessExceptionWhenSaveRecord() {
        when(repository.save(any())).thenThrow(new BusinessException(NOT_SAVED));

        RecordDTO result = service.save(recordDTO);

        assertNull(result);
    }

    @Test
    void mustReturnRecordDTOWhenEdit() {
        recordSaved.setCity(CityEnum.RIO);
        recordDTO.setCity(CityEnum.RIO);

        when(repository.saveAndFlush(any())).thenReturn(recordSaved);
        when(repository.existsById(any())).thenReturn(true);

        RecordDTO result = service.edit(recordDTO);

        assertAll("Verify result list",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertEquals(CityEnum.RIO, result.getCity(), "The element city must be the same as the one sent")
        );
    }

    @Test
    void mustReturnRecordDTOBusinessExceptionWhenEdit() {
        recordSaved.setCity(CityEnum.RIO);
        recordDTO.setCity(CityEnum.RIO);

        when(repository.saveAndFlush(any())).thenReturn(recordSaved);
        when(repository.existsById(any())).thenReturn(false);

        RecordDTO result = service.edit(recordDTO);

        assertAll("Verify result list",
                () -> assertNull(result, "Result list should not be null")
        );
    }

    @Test
    void mustReturnRecordDTOWhenDelete() {
        UUID id = recordSaved.getId();

        when(repository.findById(any())).thenReturn(Optional.of(recordSaved));
        doNothing().when(repository).deleteById(id);

        boolean result = service.delete(id);

        assertTrue(result);
    }

    @Test
    void mustReturnBusinessExceptionWhenDelete() {
        UUID id = recordSaved.getId();

        when(repository.findById(any())).thenThrow(new BusinessException("Not found record"));

        boolean result = service.delete(id);

        assertFalse(result);
    }
}
