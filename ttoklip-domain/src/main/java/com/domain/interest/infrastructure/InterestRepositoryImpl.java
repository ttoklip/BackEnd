package com.domain.interest.infrastructure;

import com.domain.interest.domain.Interest;
import com.domain.interest.domain.InterestRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InterestRepositoryImpl implements InterestRepository {

    private final InterestJpaRepository interestJpaRepository;

    @Override
    public void deleteAllByMemberId(final Long memberId) {
        interestJpaRepository.deleteAllByMemberId(memberId);
    }

    @Override
    public void saveAll(final List<Interest> interests) {
        interestJpaRepository.saveAll(interests);
    }
}
