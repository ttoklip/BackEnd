package com.api.ttoklip.domain.term.repository;

import com.api.ttoklip.domain.term.domain.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long>, TermRepositoryCustom {
}
