package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.model.MusicDTO;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicService {
    MusicDTO save(MusicDTO dto);

    List<MusicDTO> list();

    List<MusicDTO> findByArtist(ArtistDTO artist);

    List<MusicDTO> findByReacord(RecordDTO recordDTO);
}
