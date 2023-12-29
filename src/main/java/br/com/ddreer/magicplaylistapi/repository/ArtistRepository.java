package br.com.ddreer.magicplaylistapi.repository;

import br.com.ddreer.magicplaylistapi.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    List<Artist> findAllByRealNameLikeIgnoreCase(String realName);

    List<Artist> findAllByArtisticNameLikeIgnoreCase(String artistName);
}
