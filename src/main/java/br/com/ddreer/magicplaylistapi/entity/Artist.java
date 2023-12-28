package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TBL002_ARTISTS")
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Artist extends EntityBase<ArtistDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String artisticName;
    private String realName;
    private LocalDate birthday;
    private CityEnum nationality;
    private int debutYear;
    @ManyToMany(mappedBy = "musicComposers")
    private List<Music> composedMusic;
    private boolean isActive;
    public Artist() {
        super(ArtistDTO.class);
    }
}
