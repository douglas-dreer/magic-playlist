package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.AlbumDTO;
import br.com.ddreer.magicplaylistapi.repository.AlbumRepository;
import br.com.ddreer.magicplaylistapi.service.common.AlbumService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class AlbumServiceImpl implements AlbumService {
    private static final String ALBUM_NOT_FOUND = "Not found album";
    private static final String ALBUM_NOT_SAVED = "Unable to save album";
    private AlbumRepository repository;

    @Override
    public List<AlbumDTO> list() {
        return mapList(repository.findAll(), AlbumDTO.class);
    }

    @Override
    public AlbumDTO findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new BusinessException(ALBUM_NOT_FOUND)).toDTO();
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<AlbumDTO> findByArtisticName(String artistName) {
        return null;
    }


    @Override
    public AlbumDTO save(AlbumDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(ALBUM_NOT_SAVED);
            return null;
        }
    }

    @Override
    public AlbumDTO edit(AlbumDTO dto) {
        try {
            if (!repository.existsById(dto.getId())) {
                throw new BusinessException(ALBUM_NOT_FOUND);
            }
            return repository.saveAndFlush(dto.toEntity()).toDTO();

        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public boolean delete(UUID id) {
        try {
            if (!repository.existsById(id)) {
                throw new BusinessException(ALBUM_NOT_FOUND);
            }
            repository.deleteById(id);
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
