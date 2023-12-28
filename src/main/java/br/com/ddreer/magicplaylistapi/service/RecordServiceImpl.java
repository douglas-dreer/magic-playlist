package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import br.com.ddreer.magicplaylistapi.repository.RecordRepository;
import br.com.ddreer.magicplaylistapi.utility.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class RecordServiceImpl implements RecordService {
    private final String NOT_FOUND = "Not found record";
    private final String NOT_SAVED = "Unable to save record";
    private RecordRepository repository;

    @Override
    public List<RecordDTO> list() {
        return Converter.mapList(repository.findAll(), RecordDTO.class);
    }

    @Override
    public RecordDTO findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND)).toDTO();
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<RecordDTO> findByName(String name) {
        return repository.findAllByNameLikeIgnoreCase(name);
    }

    @Override
    public RecordDTO save(RecordDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(NOT_SAVED);
        }
        return null;
    }

    @Override
    public RecordDTO edit(RecordDTO dto) {
        try {
            if (!repository.existsById(dto.getId())) {
                throw new BusinessException(NOT_FOUND);
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
            repository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND));
            repository.deleteById(id);
            status = true;
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
        }
        return status;
    }
}