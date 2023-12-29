package br.com.ddreer.magicplaylistapi.repository;

import br.com.ddreer.magicplaylistapi.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {
}
