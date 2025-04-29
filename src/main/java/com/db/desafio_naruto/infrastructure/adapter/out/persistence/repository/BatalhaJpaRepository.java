package com.db.desafio_naruto.infrastructure.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.db.desafio_naruto.infrastructure.adapter.out.persistence.BatalhaEntity;

@Repository
public interface BatalhaJpaRepository extends JpaRepository<BatalhaEntity, Long> {
    @Query("SELECT b FROM BatalhaEntity b WHERE b.finalizada = false AND (b.ninja1.id = :ninjaId OR b.ninja2.id = :ninjaId)")
    Optional<BatalhaEntity> findBatalhaAtivaByNinja(@Param("ninjaId") Long ninjaId);
}
