package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.repository.RecordRepository;
import br.com.ddreer.magicplaylistapi.service.common.RecordService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class RecordServiceImpl implements RecordService {
    private static final String ARTIST_NOT_FOUND = "Not found record";
    private static final String ARTIST_NOT_SAVED = "Unable to save record";
    private RecordRepository repository;

    @Override
    public List<RecordDTO> list() {
        return mapList(repository.findAll(), RecordDTO.class);
    }

    @Override
    public RecordDTO findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new BusinessException(ARTIST_NOT_FOUND)).toDTO();
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<RecordDTO> findByName(String name) {
        return mapList(repository.findAllByNameLikeIgnoreCase(name), RecordDTO.class);
    }

    @Override
    public RecordDTO save(RecordDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(ARTIST_NOT_SAVED);
        }
        return null;
    }

    @Override
    public RecordDTO edit(RecordDTO dto) {
        try {
            if (!repository.existsById(dto.getId())) {
                throw new BusinessException(ARTIST_NOT_FOUND);
            }

            return repository.saveAndFlush(dto.toEntity()).toDTO();

        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public boolean delete(UUID id) {
        boolean status = false;
        try {
            if (!repository.existsById(id)) {
                throw new BusinessException(ARTIST_NOT_FOUND);
            }
            repository.deleteById(id);
            status = true;
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
        }
        return status;
    }
}
