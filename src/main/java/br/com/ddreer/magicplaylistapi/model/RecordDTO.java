package br.com.ddreer.magicplaylistapi.model;

import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.enums.CityEnum;
import br.com.ddreer.magicplaylistapi.model.common.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RecordDTO extends BaseModel<Record> {
    private UUID id;
    private String name;
    private CityEnum city;
    private int foundingYear;
    private boolean isActive;

    public RecordDTO() {
        super(Record.class);
    }
}
