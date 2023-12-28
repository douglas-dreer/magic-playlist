package br.com.ddreer.magicplaylistapi.entity;

import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "TBL001_RECORDS")
@AllArgsConstructor
@Data
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
