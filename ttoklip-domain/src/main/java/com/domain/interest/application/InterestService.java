package com.domain.interest.application;

import com.domain.common.vo.Category;
import com.domain.interest.domain.Interest;
import com.domain.interest.domain.InterestRepository;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    // ToDo 아래 2개로 트랜잭션 분리할 것
    @Transactional
    public void registerInterest(final Member member, final List<Category> categories) {
        interestRepository.deleteAllByMemberId(member.getId());

        List<Interest> interests = categories
                .stream()
                .map(category -> Interest.of(member, category))
                .toList();

        interestRepository.saveAll(interests);
    }

    @Transactional
    public void deleteInterestsByMember(final Member member) {
        interestRepository.deleteAllByMemberId(member.getId());
    }

    @Transactional
    public void saveInterests(final Member member, final List<Category> categories) {
        List<Interest> interests = categories
                .stream()
                .map(category -> Interest.of(member, category))
                .toList();

        interestRepository.saveAll(interests);
    }

}
