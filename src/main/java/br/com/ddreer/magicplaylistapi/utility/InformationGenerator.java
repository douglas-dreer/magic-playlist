package br.com.ddreer.magicplaylistapi.utility;

import br.com.ddreer.magicplaylistapi.entity.Album;
import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.entity.Music;
import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;

public class InformationGenerator {
    public static Artist createAnArtistForTests() {
        return Artist.builder()
                .id(UUID.randomUUID())
                .realName("Francisco Manuel da Silva")
                .artisticName("Francisco Manuel da Silva")
                .birthday(LocalDate.of(1795, Month.FEBRUARY, 21))
                .nationality(CityEnum.RIO)
                .debutYear(1931)
                .composedMusic(Collections.emptyList())
                .isActive(false)
                .build();
    }

    public static ArtistDTO createAnArtistDTOForTests() {
        return createAnArtistForTests().toDTO();
    }

    public static Music createAMusicForTests(Artist artist) {
        return Music.builder()
                .id(UUID.randomUUID())
                .title("Hino à Bandeira Nacional")
                .releaseYear(1822)
                .album(null)
                .musicComposers(Collections.singletonList(artist))
                .releaseYear(1822)
                .build();
    }

    public static MusicDTO createAMusicDTOForTests(ArtistDTO artistDTO) {
        return createAMusicForTests(artistDTO.toEntity()).toDTO();
    }

    public static Record createARecordForTests() {
        return Record.builder()
                .id(UUID.randomUUID())
                .name("Brazil")
                .city(CityEnum.RIO)
                .foundingYear(1500)
                .isActive(true)
                .build();
    }

    public static RecordDTO createARecordDTOForTests() {
        return createARecordForTests().toDTO();
    }

    public static Album createAnAlbumForTests(List<Music> musicList, List<Artist> artistList, Record recordCompany) {
        return Album.builder()
                .id(UUID.randomUUID())
                .name("Discovered from Brazil")
                .musicList(musicList)
                .recordCompany(recordCompany)
                .artistList(artistList)
                .releaseYear(1500)
                .build();
    }

    public static AlbumDTO createAnAlbumDTOForTests(List<MusicDTO> musicList, List<ArtistDTO> artistList, RecordDTO recordDTO) {
        return createAnAlbumForTests(
                mapList(musicList, Music.class),
                mapList(artistList, Artist.class),
                recordDTO.toEntity()
        ).toDTO();
    }

    public static MessageErrorDTO createAMessageErrorDTO() {
        return MessageErrorDTO.builder()
                .errorMessage("The operation could not be performed.")
                .code("ERROR")
                .details("The operation could not be performed.")
                .localDateTime(LocalDateTime.now().toString())
                .build();
    }
}
