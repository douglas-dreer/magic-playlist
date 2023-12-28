package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "TBL001_RECORDS")
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Record extends EntityBase<RecordDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private CityEnum city;
    private int foundingYear;
    private boolean isActive;
    public Record() {
        super(RecordDTO.class);
    }
}
