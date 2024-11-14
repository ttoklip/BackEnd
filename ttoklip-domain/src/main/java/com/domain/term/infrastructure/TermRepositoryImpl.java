package com.domain.term.infrastructure;

import com.domain.term.domain.Term;
import com.domain.term.domain.TermRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TermRepositoryImpl implements TermRepository {

    private final TermJpaRepository jpaRepository;
//    private final TermQueryRepository queryDSLRepository;

    @Override
    public Term getAgreeTermsOfService() {
//        return queryDSLRepository.getAgreeTermsOfService();
        return null;
    }

    @Override
    public Term getAgreePrivacyPolicy() {
//        return queryDSLRepository.getAgreePrivacyPolicy();
        return null;
    }

    @Override
    public Term getAgreeLocationService() {
//        return queryDSLRepository.getAgreeLocationService();
        return null;
    }

    @Override
    public Optional<Term> findById(final Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void saveAndFlush(final Term term) {
        jpaRepository.saveAndFlush(term);
    }

    @Override
    public List<Term> getAllTerms() {
        return jpaRepository.findAll();
    }


}
