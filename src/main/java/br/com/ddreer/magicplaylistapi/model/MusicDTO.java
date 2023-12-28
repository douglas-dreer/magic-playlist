package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class MusicDTO extends ModelBase<Music> {
    private UUID id;
    private String title;
    private List<ArtistDTO> musicComposers;
    private AlbumDTO album;
    private int releaseYear;

    public MusicDTO() {
        super(Music.class);
    }
}
