package br.com.ddreer.magicplaylistapi.service;


import br.com.ddreer.magicplaylistapi.model.RecordDTO;

import java.util.List;
import java.util.UUID;

public interface RecordService {
    List<RecordDTO> list();

    RecordDTO findById(UUID id);

    List<RecordDTO> findByName(String name);

    RecordDTO save(RecordDTO dto);

    RecordDTO edit(RecordDTO dto);

    boolean delete(UUID id);
}
