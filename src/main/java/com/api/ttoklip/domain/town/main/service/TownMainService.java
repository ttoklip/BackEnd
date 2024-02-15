package com.api.ttoklip.domain.town.main.service;

import com.api.ttoklip.domain.town.cart.post.repository.CartRepository;
import com.api.ttoklip.domain.town.community.post.repository.CommunityRepository;
import com.api.ttoklip.domain.town.main.dto.response.TownMainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TownMainService {

    private final CartRepository cartRepository;
    private final CommunityRepository communityRepository;
    public TownMainResponse main() {
        return null;
    }

}
