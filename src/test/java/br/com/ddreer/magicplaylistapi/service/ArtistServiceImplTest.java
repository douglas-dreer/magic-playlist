package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.repository.ArtistRepository;
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
class ArtistServiceImplTest extends InformationGenerator implements BaseServiceTest {
    @InjectMocks
    private ArtistServiceImpl service;
    @Mock
    private ArtistRepository repository;

    private static final String ARTIST_NOT_SAVED = "Unable to save artist";
    private static final ArtistDTO artist = createAnArtistDTOForTests();
    private static final List<Artist> resultList = Collections.singletonList(createAnArtistForTests());

    @Override
    @Test
    public void mustReturnSuccessWhenList() {
        when(repository.findAll()).thenReturn(resultList);
        checking(service.list());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.of(artist.toEntity()));
        checking(service.findById(artist.getId()));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenFindById() {
        when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertNull(service.findById(artist.getId()));
    }

    @Test
    void mustReturnListArtistDTOWhenFindByRealName() {
        when(repository.findAllByRealNameLikeIgnoreCase(anyString())).thenReturn(resultList);
        checking(service.findByRealName(artist.getRealName()));
    }

    @Test
    void mustReturnListArtistDTOWhenFindByArtistName() {
        when(repository.findAllByArtisticNameLikeIgnoreCase(anyString())).thenReturn(resultList);
        checking(service.findByArtisticName(artist.getArtisticName()));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenSave() {
        when(repository.save(any())).thenReturn(artist.toEntity());
        checking(service.save(artist));
    }

    @Override
    @Test
    public void mustReturnArtistDTOSBusinessExceptionWhenSave() {
        when(repository.save(any())).thenThrow(new BusinessException(ARTIST_NOT_SAVED));
        assertNull(service.save(artist));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenEdit() {
        when(repository.existsById(any())).thenReturn(true);
        when(repository.saveAndFlush(any())).thenReturn(artist.toEntity());
        checking(service.edit(artist));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenEdit() {
        when(repository.existsById(any())).thenReturn(false);
        assertNull(service.edit(artist));
    }

    @Override
    @Test
    public void mustReturnSuccessWhenDelete() {
        when(repository.existsById(any())).thenReturn(true);
        doNothing().when(repository).deleteById(artist.getId());
        assertTrue(service.delete(artist.getId()));
    }

    @Override
    @Test
    public void mustReturnBusinessExceptionWhenDelete() {
        when(repository.existsById(any())).thenReturn(false);
        assertFalse(service.delete(artist.getId()));
    }

    private void checking(ArtistDTO model) {
        assertAll(
                "checking Artist's properties",
                () -> assertNotNull(model, "Result list should not be null"),
                () -> assertEquals(artist.getId(), model.getId(), "The element id must be the same as the one sent")
        );
    }

    private void checking(List<ArtistDTO> modelList) {
        assertAll(
                "checking Artist's properties from list.",
                () -> assertFalse(modelList.isEmpty()),
                () -> assertEquals(modelList.size(), resultList.size())
        );
    }
}
