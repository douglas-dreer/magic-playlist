package br.com.ddreer.magicplaylistapi.service.common;


import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;

import java.util.List;
import java.util.UUID;

public interface RecordService extends BaseService<Record, RecordDTO> {
    List<RecordDTO> list();

    RecordDTO findById(UUID id);

    List<RecordDTO> findByName(String name);

    RecordDTO save(RecordDTO dto);

    RecordDTO edit(RecordDTO dto);

    boolean delete(UUID id);
}
