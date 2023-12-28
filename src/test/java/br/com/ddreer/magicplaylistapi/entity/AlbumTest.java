package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.model.AlbumDTO;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.model.MusicDTO;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlbumTest extends InformationGenerator implements BaseEntityTest {
    private static Album entity;
    private static UUID uuid;

    private static String name;
    private static Artist artist;
    private static List<Music> musicList;
    private static List<Artist> artistList;
    private static Record record;
    private static int releaseYear;


    @BeforeAll
    static void setup() {
        artist = createAnArtistForTests();
        Music music = createAMusicForTests(artist);
        record = createARecordForTests();
        entity = createAnAlbumForTests(Collections.singletonList(music), Collections.singletonList(artist), record);

        uuid = entity.getId();
        name = entity.getName();
        musicList = entity.getMusicList();
        artistList = entity.getArtistList();
        releaseYear = entity.getReleaseYear();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        Album result = new Album(uuid, name, musicList, artistList, record, releaseYear);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        Album result = new Album();
        result.setId(uuid);
        result.setName(name);
        result.setMusicList(musicList);
        result.setArtistList(artistList);
        result.setRecordCompany(record);
        result.setReleaseYear(releaseYear);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        Album result = Album.builder()
                .id(uuid)
                .name(name)
                .musicList(musicList)
                .artistList(artistList)
                .recordCompany(record)
                .releaseYear(releaseYear)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToDTO() {
        AlbumDTO dto = entity.toDTO();
        checking(dto);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        final String toJSON = entity.toJSON();
        assertNotNull(toJSON);
    }

    @Override
    @Test
    public void mustReturnSUccessWhenConterToObject() throws IOException {
        final AlbumDTO toObject = entity.toObject(entity.toJSON());
        checking(toObject);
    }

    private void checking(Album dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The album should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element id must be the same as the one sent"),
                () -> assertFalse(dados.getMusicList().isEmpty(), "The music list should not be empty"),
                () -> assertEquals(musicList, dados.getMusicList(), "The element music list must be the same as the one sent"),
                () -> assertFalse(dados.getArtistList().isEmpty(), "The artist list should not be empty"),
                () -> assertEquals(artistList, dados.getArtistList(), "The element artist list must be the same as the one sent"),
                () -> assertNotNull(dados.getRecordCompany(), "The record company list should not be empty"),
                () -> assertEquals(record, dados.getRecordCompany(), "The element record company must be the same as the one sent"),
                () -> assertEquals(releaseYear, dados.getReleaseYear(), "The element release year must be the same as the one sent")
        );
    }

    private void checking(AlbumDTO dados) {
        List<MusicDTO> musicDTOList = mapList(musicList, MusicDTO.class);
        List<ArtistDTO> artistDTOList = mapList(artistList, ArtistDTO.class);
        RecordDTO recordDTO = record.toDTO();
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The album should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getName(), "The name should not be null"),
                () -> assertEquals(name, dados.getName(), "The element id must be the same as the one sent"),
                () -> assertFalse(dados.getMusicList().isEmpty(), "The music list should not be empty"),
                () -> assertEquals(musicDTOList, dados.getMusicList(), "The element music list must be the same as the one sent"),
                () -> assertFalse(dados.getArtistList().isEmpty(), "The artist list should not be empty"),
                () -> assertEquals(artistDTOList, dados.getArtistList(), "The element artist list must be the same as the one sent"),
                () -> assertNotNull(dados.getRecordCompany(), "The record company list should not be empty"),
                () -> assertEquals(recordDTO, dados.getRecordCompany(), "The element record company must be the same as the one sent"),
                () -> assertEquals(releaseYear, dados.getReleaseYear(), "The element release year must be the same as the one sent")
        );
    }
}
