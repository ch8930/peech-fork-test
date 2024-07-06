package com.twentythree.peech.script.respository;

import com.twentythree.peech.script.domain.SentenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceRepository extends JpaRepository<SentenceEntity, Long> {
}
