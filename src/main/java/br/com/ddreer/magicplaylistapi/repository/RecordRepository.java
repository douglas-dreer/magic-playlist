package br.com.ddreer.magicplaylistapi.repository;

import br.com.ddreer.magicplaylistapi.entity.Record;
import br.com.ddreer.magicplaylistapi.model.RecordDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
    List<RecordDTO> findAllByNameLikeIgnoreCase(String name);
}
