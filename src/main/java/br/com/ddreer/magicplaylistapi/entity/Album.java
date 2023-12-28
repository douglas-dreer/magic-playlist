package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.model.AlbumDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TBL003_ALBUMS")
@AllArgsConstructor
@Getter
@Setter

@Builder
public class Album extends EntityBase<AlbumDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @ManyToMany(mappedBy = "album")
    private List<Music> musicList;
    @ManyToMany
    @JoinTable(
            name = "TBL005_ALBUM_ARTISTS",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artistList;
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record recordCompany;
    private int releaseYear;

    public Album() {
        super(AlbumDTO.class);
    }


}
