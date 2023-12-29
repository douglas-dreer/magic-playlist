package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Album;
import br.com.ddreer.magicplaylistapi.model.common.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class AlbumDTO extends BaseModel<Album> {
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
