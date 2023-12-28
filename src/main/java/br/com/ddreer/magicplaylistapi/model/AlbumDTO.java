package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Album;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AlbumDTO extends ModelBase<Album> {
    private UUID id;
    private String name;
    private List<ArtistDTO> artistList;
    private List<MusicDTO> musicList;
    private RecordDTO recordCompany;
    private int releaseYear;

    public AlbumDTO() {
        super(Album.class);
    }
}
