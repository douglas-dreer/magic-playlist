package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.entity.Music;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ArtistDTOTest extends InformationGenerator implements ModelBaseTest {
    private static ArtistDTO dto = new ArtistDTO();
    private static UUID uuid = UUID.randomUUID();
    private static String artisticName;
    private static String realName;
    private static LocalDate birthday;
    private static CityEnum nationality;
    private static List<MusicDTO> composedMusicList;
    private static int debutYear;
    private static boolean isActive;

    @BeforeAll
    public static void setup() {

        dto = createAnArtistDTOForTests();
        uuid = dto.getId();
        artisticName = dto.getArtisticName();
        realName = dto.getRealName();
        birthday = dto.getBirthday();
        nationality = dto.getNationality();
        debutYear = dto.getDebutYear();

        isActive = dto.isActive();

        MusicDTO music = createAMusicDTOForTests(dto);
        dto.setComposedMusic(Collections.singletonList(music));
        composedMusicList = dto.getComposedMusic();

    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        ArtistDTO result = new ArtistDTO(uuid, artisticName, realName, birthday, nationality, debutYear, composedMusicList, isActive);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        ArtistDTO result = new ArtistDTO();
        result.setId(uuid);
        result.setArtisticName(artisticName);
        result.setRealName(realName);
        result.setBirthday(birthday);
        result.setNationality(nationality);
        result.setComposedMusic(composedMusicList);
        result.setDebutYear(debutYear);
        result.setActive(isActive);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        ArtistDTO result = ArtistDTO.builder()
                .id(uuid)
                .artisticName(artisticName)
                .realName(realName)
                .birthday(birthday)
                .nationality(nationality)
                .debutYear(debutYear)
                .composedMusic(composedMusicList)
                .isActive(isActive)
                .build();
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToEntity() {
        Artist entity = dto.toEntity();
        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        String toJSON = dto.toJSON();
        assertFalse(toJSON.isBlank());
    }

    @Override
    @Test
    public void mustReturnSuccessWhenConterToObject() throws IOException {
        final String toJSON = dto.toJSON();
        checking(dto.toObject(toJSON));
    }

    private void checking(ArtistDTO datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The music should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(uuid, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getArtisticName(), "The artitic name should not be null"),
                () -> assertEquals(artisticName, datas.getArtisticName(), "The element artistic name must be the same as the one sent"),
                () -> assertNotNull(datas.getRealName(), "The real name name should not be null"),
                () -> assertEquals(realName, datas.getRealName(), "The element real name must be the same as the one sent"),
                () -> assertNotNull(datas.getBirthday(), "The birthday list should not be null"),
                () -> assertEquals(nationality, datas.getNationality(), "The element nationality must be the same as the one sent"),
                () -> assertNotNull(datas.getNationality(), "The nationality list should not be null"),
                () -> assertEquals(birthday, datas.getBirthday(), "The element birthday must be the same as the one sent"),
                () -> assertEquals(debutYear, datas.getDebutYear(), "The element debut year must be the same as the one sent"),
                () -> assertFalse(datas.getComposedMusic().isEmpty(), "The composered music list should not be empty"),
                () -> assertEquals(composedMusicList, datas.getComposedMusic(), "The element composed music list year must be the same as the one sent"),
                () -> assertFalse(datas.isActive(), "The element is active year must be the same as the one sent")
        );
    }

    private void checking(Artist datas) {
        assertAll("Verify result list",
                () -> assertNotNull(datas, "The music should not be null"),
                () -> assertNotNull(datas.getId(), "The id should not be null"),
                () -> assertEquals(uuid, datas.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(datas.getArtisticName(), "The artitic name should not be null"),
                () -> assertEquals(artisticName, datas.getArtisticName(), "The element artistic name must be the same as the one sent"),
                () -> assertNotNull(datas.getRealName(), "The artitic name should not be null"),
                () -> assertEquals(realName, datas.getRealName(), "The element real name must be the same as the one sent"),
                () -> assertNotNull(datas.getBirthday(), "The birthday list should not be null"),
                () -> assertEquals(nationality, datas.getNationality(), "The element nationality must be the same as the one sent"),
                () -> assertNotNull(datas.getNationality(), "The nationality list should not be null"),
                () -> assertEquals(birthday, datas.getBirthday(), "The element birthday must be the same as the one sent"),
                () -> assertEquals(debutYear, datas.getDebutYear(), "The element debut year must be the same as the one sent"),
                () -> assertFalse(datas.getComposedMusic().isEmpty(), "The composered music list should not be empty"),
                () -> assertEquals(mapList(composedMusicList, Music.class), datas.getComposedMusic(), "The element composed music list year must be the same as the one sent"),
                () -> assertFalse(datas.isActive(), "The element is active must be the same as the one sent")
        );
    }
}
