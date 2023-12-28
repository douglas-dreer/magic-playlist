package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class RecordDTO extends ModelBase<Record> {
    private UUID id;
    private String name;
    private CityEnum city;
    private int foundingYear;
    private boolean isActive;

    public RecordDTO() {
        super(Record.class);
    }
}
