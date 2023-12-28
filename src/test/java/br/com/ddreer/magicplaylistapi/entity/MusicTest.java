package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.model.MusicDTO;
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
public class MusicTest extends InformationGenerator implements BaseEntityTest {
    private static UUID uuid;
    private static String title;
    private static List<Artist> musicComposers;
    private static Artist entity;
    private static int releaseYear = 0;
    private static Album album;
    private static Music music;
    private static Record record;

    @BeforeAll
    public static void setup() {
        entity = createAnArtistForTests();
        music = createAMusicForTests(entity);
        List<Artist> artistList = Collections.singletonList(createAnArtistForTests());

        List<Music> musicList = Collections.singletonList(music);

        record = createARecordForTests();
        album = createAnAlbumForTests(musicList, artistList, record);

        uuid = music.getId();
        title = music.getTitle();
        musicComposers = music.getMusicComposers();
        releaseYear = music.getReleaseYear();
    }

    @Test
    void mustReturnMusicDTOWhenToDTO() {
        MusicDTO result = music.toDTO();
        checking(result);
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
                () -> assertEquals(releaseYear, music.getReleaseYear(), "The element release year must be the same as the one sent"),
                () -> assertNotNull(dados.getAlbum(), "The album should not be null"),
                () -> assertEquals(album, dados.getAlbum(), "The element album must be the same as the one sent")
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
                () -> assertEquals(releaseYear, music.getReleaseYear(), "The element release year must be the same as the one sent")
        );
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        Music result = new Music(uuid, title, musicComposers, releaseYear, album);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        Music result = new Music();
        result.setId(uuid);
        result.setTitle(title);
        result.setMusicComposers(musicComposers);
        result.setReleaseYear(releaseYear);
        result.setAlbum(album);
        checking(result);
    }

    @Override
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        Music result = Music.builder()
                .id(uuid)
                .title(title)
                .musicComposers(musicComposers)
                .releaseYear(releaseYear)
                .album(album)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() {
        MusicDTO result = music.toDTO();
        checking(result);
    }

    @Override
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        final String toJSON = music.toJSON();
        assertNotNull(toJSON);
    }

    @Override
    public void mustReturnSUccessWhenConterToObject() throws IOException {
        final String toJSON = music.toJSON();
        checking(music.toObject(toJSON));
    }
}
