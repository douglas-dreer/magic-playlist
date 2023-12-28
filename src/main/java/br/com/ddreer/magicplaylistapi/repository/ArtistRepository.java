package br.com.ddreer.magicplaylistapi.repository;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import br.com.ddreer.magicplaylistapi.model.ArtistDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    List<ArtistDTO> findAllByRealNameLikeIgnoreCase(String realName);

    List<ArtistDTO> findAllByArtisticNameLikeIgnoreCase(String artistName);
}
