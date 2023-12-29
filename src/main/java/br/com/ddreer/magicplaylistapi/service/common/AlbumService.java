package br.com.ddreer.magicplaylistapi.service.common;

import br.com.ddreer.magicplaylistapi.entity.Album;
import br.com.ddreer.magicplaylistapi.model.AlbumDTO;

import java.util.List;
import java.util.UUID;


public interface AlbumService extends BaseService<Album, AlbumDTO> {
    List<AlbumDTO> list();

    AlbumDTO findById(UUID id);

    List<AlbumDTO> findByArtisticName(String artistName);

    AlbumDTO save(AlbumDTO artist);

    boolean delete(UUID id);
}
