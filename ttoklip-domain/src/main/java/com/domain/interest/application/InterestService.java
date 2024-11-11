package com.domain.interest.application;

import com.domain.common.vo.Category;
import com.domain.interest.domain.Interest;
import com.domain.interest.domain.InterestRepository;
import com.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    public void registerInterest(final Member member, final List<Category> categories) {
        interestRepository.deleteAllByMemberId(member.getId());

        List<Interest> interests = categories
                .stream()
                .map(category -> Interest.of(member, category))
                .toList();

        interestRepository.saveAll(interests);
    }
}
