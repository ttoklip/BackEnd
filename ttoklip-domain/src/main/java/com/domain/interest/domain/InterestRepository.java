package com.domain.interest.domain;

import java.util.List;

public interface InterestRepository {
    void deleteAllByMemberId(Long memberId);

    void saveAll(List<Interest> interests);
}
