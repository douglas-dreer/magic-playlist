package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.exception.BusinessException;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.repository.ArtistRepository;
import br.com.ddreer.magicplaylistapi.utility.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class ArtistServiceImpl implements ArtistService {
    private final String NOT_FOUND = "Not found artist";
    private final String NOT_SAVED = "Unable to save artist";
    private ArtistRepository repository;

    @Override
    public List<ArtistDTO> list() {
        return Converter.mapList(repository.findAll(), ArtistDTO.class);
    }

    @Override
    public ArtistDTO findById(UUID id) {
        try {
            return repository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND)).toDTO();
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public List<ArtistDTO> findByRealName(String realName) {
        return repository.findAllByRealNameLikeIgnoreCase(realName);
    }

    @Override
    public List<ArtistDTO> findByArtisticName(String artistName) {
        return repository.findAllByArtisticNameLikeIgnoreCase(artistName);
    }

    @Override
    public ArtistDTO save(ArtistDTO dto) {
        try {
            return repository.save(dto.toEntity()).toDTO();
        } catch (BusinessException e) {
            log.error(NOT_SAVED);
            return null;
        }
    }

    @Override
    public ArtistDTO edit(ArtistDTO dto) {
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
        try {
            repository.findById(id).orElseThrow(() -> new BusinessException(NOT_FOUND));
            repository.deleteById(id);
            return true;
        } catch (BusinessException e) {
            log.info(e.getLocalizedMessage());
            return false;
        }
    }
}
