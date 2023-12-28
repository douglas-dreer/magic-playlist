package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Music;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicDTOTest extends InformationGenerator implements ModelBaseTest {
    private static UUID uuid;
    private static String title;
    private static List<ArtistDTO> musicComposers;
    private static ArtistDTO artist;
    private static int releaseYear = 0;
    private static AlbumDTO album;
    private static MusicDTO dto;

    private static RecordDTO record;

    @BeforeAll
    public static void setup() {
        artist = createAnArtistDTOForTests();
        dto = createAMusicDTOForTests(artist);

        uuid = dto.getId();
        title = dto.getTitle();
        musicComposers = dto.getMusicComposers();
        releaseYear = dto.getReleaseYear();

        album = createAnAlbumDTOForTests(
                Collections.singletonList(dto),
                Collections.singletonList(artist),
                createARecordDTOForTests()
        );
        dto.setAlbum(album);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        MusicDTO result = new MusicDTO(uuid, title, musicComposers, album, releaseYear);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        MusicDTO result = new MusicDTO();
        result.setId(uuid);
        result.setTitle(title);
        result.setMusicComposers(musicComposers);
        result.setAlbum(album);
        result.setReleaseYear(releaseYear);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        MusicDTO result = MusicDTO.builder()
                .id(uuid)
                .title(title)
                .musicComposers(musicComposers)
                .album(album)
                .releaseYear(releaseYear)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToEntity() {
        Music entity = dto.toEntity();
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        String toJSON = dto.toJSON();
        assertNotNull(toJSON);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConterToObject() throws IOException {
        final String toJSON = dto.toJSON();
        checking(dto.toObject(toJSON));
    }

    private void checking(Music dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The music should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getTitle(), "The title should not be null"),
                () -> assertEquals(title, dados.getTitle(), "The element title must be the same as the one sent"),
                () -> assertFalse(dados.getMusicComposers().isEmpty(), "The music composer should not be empty"),
                () -> assertEquals(musicComposers.size(), dados.getMusicComposers().size(), "The element music composers must be the same as the one sent"),
                () -> assertEquals(releaseYear, dados.getReleaseYear(), "The element release year must be the same as the one sent"),
                () -> assertNotNull(dados.getAlbum(), "The album should not be null"),
                () -> assertEquals(album.toEntity(), dados.getAlbum(), "The element album must be the same as the one sent")
        );
    }

    private void checking(MusicDTO dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The music should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getTitle(), "The title should not be null"),
                () -> assertEquals(title, dados.getTitle(), "The element title must be the same as the one sent"),
                () -> assertFalse(dados.getMusicComposers().isEmpty(), "The music composer should not be empty"),
                () -> assertEquals(musicComposers.size(), dados.getMusicComposers().size(), "The element music composers must be the same as the one sent"),
                () -> assertEquals(releaseYear, dados.getReleaseYear(), "The element release year must be the same as the one sent"),
                () -> assertNotNull(dados.getAlbum(), "The album should not be null"),
                () -> assertEquals(album, dados.getAlbum(), "The element album must be the same as the one sent")
        );
    }
}
