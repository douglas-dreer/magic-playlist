package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class ArtistDTO extends ModelBase<Artist> {
    private UUID id;
    private String artisticName;
    private String realName;
    private LocalDate birthday;
    private CityEnum nationality;
    private int debutYear;
    private List<MusicDTO> composedMusic;
    private boolean isActive;

    public ArtistDTO() {
        super(Artist.class);
    }
}
