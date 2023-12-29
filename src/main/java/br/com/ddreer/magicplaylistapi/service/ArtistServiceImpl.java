package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.repository.ArtistRepository;
import br.com.ddreer.magicplaylistapi.service.common.ArtistService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.ddreer.magicplaylistapi.utility.Converter.mapList;

@Service
@AllArgsConstructor
@Log4j2
public class ArtistServiceImpl implements ArtistService {
    private static final String ARTIST_NOT_FOUND = "Not found artist";
    private static final String ARTIST_NOT_SAVED = "Unable to save artist";
    private ArtistRepository repository;

    @Override
    public List<ArtistDTO> list() {
        return mapList(repository.findAll(), ArtistDTO.class);
    }

    @Override
    public ArtistDTO findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new BusinessException(ARTIST_NOT_FOUND)).toDTO();
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<ArtistDTO> findByRealName(String realName) {
        return mapList(repository.findAllByRealNameLikeIgnoreCase(realName), ArtistDTO.class);
    }

    @Override
    public List<ArtistDTO> findByArtisticName(String artistName) {
        return mapList(repository.findAllByArtisticNameLikeIgnoreCase(artistName), ArtistDTO.class);
    }

    @Override
    public ArtistDTO save(ArtistDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(ARTIST_NOT_SAVED);
            return null;
        }
    }

    @Override
    public ArtistDTO edit(ArtistDTO dto) {
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
        try {
            if (!repository.existsById(id)) {
                throw new BusinessException(ARTIST_NOT_FOUND);
            }
            repository.deleteById(id);
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
