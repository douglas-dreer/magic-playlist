package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.repository.RecordRepository;
import br.com.ddreer.magicplaylistapi.service.common.BaseServiceTest;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecordServiceImplTest extends InformationGenerator implements BaseServiceTest {
    @InjectMocks
    private RecordServiceImpl service;
    @Mock
    private RecordRepository repository;

    private final static String RECORD_NOT_SAVED = "Unable to save record";
    private static final RecordDTO record = createARecordDTOForTests();
    private static final List<Record> recordList = Collections.singletonList(createARecordForTests());

    @Override
    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(recordList);
        checking(service.list());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.of(record.toEntity()));
        checking(service.findById(record.getId()));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertNull(service.findById(record.getId()));
    }

    @Test
    void mustReturnSuccessWhenFindByName() {
        when(repository.findAllByNameLikeIgnoreCase(anyString())).thenReturn(recordList);
        checking(service.findByName(record.getName()));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(record.toEntity());
        checking(service.save(record));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenSave() {
        when(repository.save(any())).thenThrow(new BusinessException(RECORD_NOT_SAVED));
        assertNull(service.save(record));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenEdit() {
        when(repository.existsById(any())).thenReturn(true);
        when(repository.saveAndFlush(any())).thenReturn(record.toEntity());
        checking(service.edit(record));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenEdit() {
        when(repository.existsById(any())).thenReturn(false);
        assertNull(service.edit(record));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenDelete() {
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(record.getId());
        assertTrue(service.delete(record.getId()));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenDelete() {
        when(repository.existsById(any())).thenReturn(false);
        assertFalse(service.delete(record.getId()));
    }

    private void checking(RecordDTO model) {
        assertAll(
                "checking record's properties",
                () -> assertNotNull(model, "Result list should not be null"),
                () -> assertEquals(record.getId(), model.getId(), "The element id must be the same as the one sent")
        );
    }

    private void checking(List<RecordDTO> modelList) {
        assertAll(
                "checking record's properties from list.",
                () -> assertFalse(modelList.isEmpty()),
                () -> assertEquals(modelList.size(), recordList.size())
        );
    }
}
