package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.model.ArtistDTO;

import java.util.List;
import java.util.UUID;

public interface ArtistService {
    List<ArtistDTO> list();

    ArtistDTO findById(UUID id);

    List<ArtistDTO> findByRealName(String realName);

    List<ArtistDTO> findByArtisticName(String artistName);

    ArtistDTO save(ArtistDTO artist);

    ArtistDTO edit(ArtistDTO artist);

    boolean delete(UUID id);
}
