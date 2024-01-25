package com.api.ttoklip.domain.town.main.service;

import com.api.ttoklip.domain.town.main.dto.request.TownSearchCondition;
import com.api.ttoklip.domain.town.main.dto.response.TownSearchResponse;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class TownMainService {
    public TownSearchResponse search(final TownSearchCondition condition, final Pageable pageable) {
        return null;
    }
}
