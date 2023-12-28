package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.model.MusicDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TBL004_MUSICS")
@AllArgsConstructor
@Data
@Builder
public class Music extends EntityBase<MusicDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    @ManyToMany
    @JoinTable(
            name = "TBL006_MUSIC_COMPOSERS",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> musicComposers;
    private int releaseYear;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    public Music() {
        super(MusicDTO.class);
    }
}

