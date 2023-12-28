package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Music;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
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
