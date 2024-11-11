package com.domain.term.infrastructure;

import com.domain.term.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermJpaRepository extends JpaRepository<Term, Long> {
}
