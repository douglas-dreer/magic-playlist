package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Album;
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
public class AlbumDTOTest extends InformationGenerator implements ModelBaseTest {
    private static AlbumDTO dto;
    private static MusicDTO music;
    private static List<MusicDTO> musicList;
    private static ArtistDTO artist;
    private static List<ArtistDTO> artistList;
    private static RecordDTO record;
    private static UUID uuid;
    private static String name;
    private static int releaseYear;

    @BeforeAll
    public static void setup() {
        artist = createAnArtistDTOForTests();
        artistList = Collections.singletonList(artist);
        music = createAMusicDTOForTests(artist);
        musicList = Collections.singletonList(music);
        record = createARecordDTOForTests();

        dto = createAnAlbumDTOForTests(musicList, artistList, record);
        uuid = dto.getId();
        name = dto.getName();
        releaseYear = dto.getReleaseYear();
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        AlbumDTO result = new AlbumDTO(uuid, name, artistList, musicList, record, releaseYear);
        checking(result);

    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        AlbumDTO result = new AlbumDTO();
        result.setId(uuid);
        result.setName(name);
        result.setArtistList(artistList);
        result.setMusicList(musicList);
        result.setRecordCompany(record);
        result.setReleaseYear(releaseYear);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        AlbumDTO result = AlbumDTO.builder()
                .id(uuid)
                .name(name)
                .artistList(artistList)
                .musicList(musicList)
                .recordCompany(record)
                .releaseYear(releaseYear)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToEntity() {
        Album entity = dto.toEntity();
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        String toJSON = dto.toJSON();
        assertFalse(toJSON.isBlank());
    }

    @Override
    public void mustReturnSuccessWhenConterToObject() throws IOException {
        final String toJSON = dto.toJSON();
        checking(dto.toObject(toJSON));
    }

    public void checking(AlbumDTO datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The music should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(uuid, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getName(), "The name should not be null"),
                () -> assertEquals(name, datas.getName(), "The element name must be the same as the one sent"),
                () -> assertFalse(datas.getArtistList().isEmpty(), "The artist list should not be empty"),
                () -> assertEquals(artistList.size(), datas.getArtistList().size(), "The element artist list composers must be the same as the one sent"),
                () -> assertFalse(datas.getMusicList().isEmpty(), "The music list should not be empty"),
                () -> assertEquals(musicList.size(), datas.getMusicList().size(), "The element music list composers must be the same as the one sent"),
                () -> assertNotNull(datas.getRecordCompany(), "The record company should not be null"),
                () -> assertEquals(record, datas.getRecordCompany(), "The element record must be the same as the one sent"),
                () -> assertEquals(releaseYear, datas.getReleaseYear(), "The element release year must be the same as the one sent")
        );
    }

    public void checking(Album datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The music should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(uuid, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getName(), "The name should not be null"),
                () -> assertEquals(name, datas.getName(), "The element name must be the same as the one sent"),
                () -> assertFalse(datas.getArtistList().isEmpty(), "The artist list should not be empty"),
                () -> assertEquals(artistList.size(), datas.getArtistList().size(), "The element artist list composers must be the same as the one sent"),
                () -> assertFalse(datas.getMusicList().isEmpty(), "The music list should not be empty"),
                () -> assertEquals(musicList.size(), datas.getMusicList().size(), "The element music list composers must be the same as the one sent"),
                () -> assertNotNull(datas.getRecordCompany(), "The record company should not be null"),
                () -> assertEquals(record.toEntity(), datas.getRecordCompany(), "The element record must be the same as the one sent"),
                () -> assertEquals(releaseYear, datas.getReleaseYear(), "The element release year must be the same as the one sent")
        );
    }
}
