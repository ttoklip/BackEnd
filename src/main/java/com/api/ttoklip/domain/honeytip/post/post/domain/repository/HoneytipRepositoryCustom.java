package com.api.ttoklip.domain.honeytip.post.post.domain.repository;

import com.api.ttoklip.domain.honeytip.post.post.domain.Honeytip;

public interface HoneytipRepositoryCustom {
    Honeytip findByIdActivated(final Long honneytipId);
}
