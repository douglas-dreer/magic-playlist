package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArtistServiceImplTest {
    private final static String NOT_SAVED = "Unable to save artist";
    private static Artist artistSaved = new Artist();
    private static ArtistDTO artistDTO = new ArtistDTO();
    private static List<ArtistDTO> artistDTOList = new ArrayList<>();
    private static List<Artist> artistList = new ArrayList<>();
    @InjectMocks
    private ArtistServiceImpl service;
    @Mock
    private ArtistRepository repository;

    @BeforeAll
    public static void setup() {
        artistSaved.setId(UUID.randomUUID());
        artistSaved.setArtisticName("Söejin");
        artistSaved.setRealName("Douglas Dreer");
        artistSaved.setBirthday(LocalDate.of(1983, Month.AUGUST, 23));
        artistSaved.setNationality(CityEnum.MGA);
        artistSaved.setDebutYear(2023);
        artistSaved.setActive(true);

        artistDTO = artistSaved.toDTO();
        artistDTOList = Collections.singletonList(artistDTO);
        artistList = Collections.singletonList(artistSaved);
    }

    @Test
    void mustReturnListArtistDTOWhenList() {
        when(repository.findAll()).thenReturn(artistList);

        List<ArtistDTO> resultList = service.list();

        assertAll("Verify result",
                () -> assertFalse(resultList.isEmpty(), "Result list should not be empty"),
                () -> assertEquals(1, resultList.size(), "The results list must contain 1 element")
        );
    }

    @Test
    void mustReturnArtistDTOWhenFindId() {
        when(repository.findById(any())).thenReturn(Optional.of(artistSaved));

        UUID id = artistSaved.getId();

        ArtistDTO result = service.findById(id);

        assertAll(
                "item",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertEquals(id, result.getId(), "The element id must be the same as the one sent")
        );
    }

    @Test
    void mustReturnBusinessExceptionWhenFindId() {
        when(repository.findById(any())).thenThrow(new BusinessException("Not found record"));
        UUID id = artistSaved.getId();

        ArtistDTO result = service.findById(id);

        assertNull(result);
    }

    @Test
    void mustReturnListArtistDTOWhenFindByRealName() {
        when(repository.findAllByRealNameLikeIgnoreCase(anyString())).thenReturn(artistDTOList);

        String realName = artistSaved.getRealName();

        List<ArtistDTO> result = service.findByRealName(realName);

        assertAll("Verify result list",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertFalse(result.isEmpty(), "Result list should not be empty")
        );
    }

    @Test
    void mustReturnListArtistDTOWhenFindByArtistName() {
        when(repository.findAllByArtisticNameLikeIgnoreCase(anyString())).thenReturn(artistDTOList);

        String artistName = artistSaved.getArtisticName();

        List<ArtistDTO> result = service.findByArtisticName(artistName);

        assertAll("Verify result list",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertFalse(result.isEmpty(), "Result list should not be empty")
        );
    }

    @Test
    void mustReturnArtistDTOWhenSaveRecord() {
        when(repository.save(any())).thenReturn(artistSaved);

        ArtistDTO result = service.save(artistDTO);

        assertNotNull(result);
    }

    @Test
    void mustReturnArtistDTOSBusinessExceptionWhenSaveRecord() {
        when(repository.save(any())).thenThrow(new BusinessException(NOT_SAVED));

        ArtistDTO result = service.save(artistDTO);

        assertNull(result);
    }

    @Test
    void mustReturnArtistDTOWhenEdit() {
        artistSaved.setDebutYear(1999);

        when(repository.saveAndFlush(any())).thenReturn(artistSaved);
        when(repository.existsById(any())).thenReturn(true);

        ArtistDTO result = service.edit(artistDTO);

        assertAll("Verify result list",
                () -> assertNotNull(result, "Result list should not be null"),
                () -> assertEquals(artistSaved.getDebutYear(), result.getDebutYear(), "The element debut year must be the same as the one sent")
        );
    }

    @Test
    void mustReturnArtistDTOBusinessExceptionWhenEdit() {
        artistSaved.setArtisticName("JoAsc");
        artistDTO.setArtisticName("Joaquina Souza");

        when(repository.saveAndFlush(any())).thenReturn(artistSaved);
        when(repository.existsById(any())).thenReturn(false);

        ArtistDTO result = service.edit(artistDTO);

        assertAll("Verify result list",
                () -> assertNull(result, "Result list should not be null")
        );
    }

    @Test
    void mustReturnArtistDTOWhenDelete() {
        UUID id = artistSaved.getId();

        when(repository.findById(any())).thenReturn(Optional.of(artistSaved));
        doNothing().when(repository).deleteById(id);

        boolean result = service.delete(id);

        assertTrue(result);
    }

    @Test
    void mustReturnBusinessExceptionWhenDelete() {
        UUID id = artistSaved.getId();

        when(repository.findById(any())).thenThrow(new BusinessException("Not found record"));

        boolean result = service.delete(id);

        assertFalse(result);
    }

}
