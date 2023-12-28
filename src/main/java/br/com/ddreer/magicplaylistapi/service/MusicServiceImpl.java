package br.com.ddreer.magicplaylistapi.service;

import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import br.com.ddreer.magicplaylistapi.model.MusicDTO;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MusicServiceImpl implements MusicService {
    @Override
    public MusicDTO save(MusicDTO dto) {
        return null;
    }

    @Override
    public List<MusicDTO> list() {
        return null;
    }

    @Override
    public List<MusicDTO> findByArtist(ArtistDTO artist) {
        return null;
    }

    @Override
    public List<MusicDTO> findByReacord(RecordDTO recordDTO) {
        return null;
    }
}
