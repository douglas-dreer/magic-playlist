package br.com.ddreer.magicplaylistapi.service.common;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;

import java.util.List;
import java.util.UUID;


public interface ArtistService extends BaseService<Artist, ArtistDTO> {
    List<ArtistDTO> list();

    ArtistDTO findById(UUID id);

    List<ArtistDTO> findByRealName(String realName);

    List<ArtistDTO> findByArtisticName(String artistName);

    ArtistDTO save(ArtistDTO artist);

    boolean delete(UUID id);
}
