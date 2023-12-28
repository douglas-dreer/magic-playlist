package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.utility.InformationGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArtistTest extends InformationGenerator implements BaseEntityTest {
    private static Artist entity = new Artist();
    private static UUID uuid = UUID.randomUUID();
    private static String artisticName = "Söejin";
    private static String realName = "Douglas Dreer";
    private static LocalDate birthday = LocalDate.of(1983, Month.AUGUST, 23);
    private static CityEnum nationality = CityEnum.MGA;
    private static int debutYear = 2023;
    private static List<Music> composedMusic;
    private static boolean isActive = true;

    @BeforeAll
    public static void setup() {
        entity = createAnArtistForTests();
        uuid = entity.getId();
        artisticName = entity.getArtisticName();
        realName = entity.getRealName();
        birthday = entity.getBirthday();
        nationality = entity.getNationality();
        debutYear = entity.getDebutYear();
        composedMusic = entity.getComposedMusic();
        isActive = entity.isActive();
    }


    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithConstructor() {
        Artist result = new Artist(uuid, artisticName, realName, birthday, nationality, debutYear, composedMusic, isActive);
        checking(result);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithSetters() {
        Artist entity = new Artist();
        entity.setId(uuid);
        entity.setArtisticName(artisticName);
        entity.setRealName(realName);
        entity.setNationality(nationality);
        entity.setBirthday(birthday);
        entity.setActive(isActive);

        checking(entity);
    }

    @Override
    @Test
    public void mustReturnSuccessWhenInitializeWithBuilder() {
        Artist entity = Artist.builder()
                .id(uuid)
                .artisticName(artisticName)
                .realName(realName)
                .nationality(nationality)
                .birthday(birthday)
                .isActive(isActive)
                .build();
        checking(entity);
    }

    @Override
    public void mustReturnSuccessWhenConvertToDTO() {
        ArtistDTO dto = entity.toDTO();
        checking(dto);
    }

    @Override
    public void mustReturnSuccessWhenConvertToJSON() throws JsonProcessingException {
        String toJSON = entity.toJSON();
        assertNotNull(toJSON);
    }

    @Override
    @Test
    public void mustReturnSUccessWhenConterToObject() throws IOException {
        final ArtistDTO toObject = entity.toObject(entity.toJSON());
        checking(toObject);
    }

    private void checking(Artist dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The artist should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getArtisticName(), "The artistic name should not be null"),
                () -> assertEquals(artisticName, dados.getArtisticName(), "The element artistic name must be the same as the one sent"),
                () -> assertNotNull(dados.getRealName(), "The real name should not be empty"),
                () -> assertEquals(realName, dados.getRealName(), "The element real name must be the same as the one sent"),
                () -> assertNotNull(dados.getNationality(), "The nationality should not be empty"),
                () -> assertEquals(nationality, dados.getNationality(), "The element nationality must be the same as the one sent"),
                () -> assertNotNull(dados.getBirthday(), "The birthday should not be empty"),
                () -> assertEquals(birthday, dados.getBirthday(), "The element birthday must be the same as the one sent"),
                () -> assertEquals(isActive, dados.isActive(), "The element isActive must be the same as the one sent")
        );
    }

    private void checking(ArtistDTO dados) {
        assertAll("Verify result list",
                () -> assertNotNull(dados, "The artist should not be null"),
                () -> assertNotNull(dados.getId(), "The id should not be null"),
                () -> assertEquals(uuid, dados.getId(), "The element id must be the same as the one sent"),
                () -> assertNotNull(dados.getArtisticName(), "The artistic name should not be null"),
                () -> assertEquals(artisticName, dados.getArtisticName(), "The element artistic name must be the same as the one sent"),
                () -> assertNotNull(dados.getRealName(), "The real name should not be empty"),
                () -> assertEquals(realName, dados.getRealName(), "The element real name must be the same as the one sent"),
                () -> assertNotNull(dados.getNationality(), "The nationality should not be empty"),
                () -> assertEquals(nationality, dados.getNationality(), "The element nationality must be the same as the one sent"),
                () -> assertNotNull(dados.getBirthday(), "The birthday should not be empty"),
                () -> assertEquals(birthday, dados.getBirthday(), "The element birthday must be the same as the one sent"),
                () -> assertEquals(isActive, dados.isActive(), "The element isActive must be the same as the one sent")
        );
    }
}
