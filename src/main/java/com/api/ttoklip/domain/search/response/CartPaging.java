package com.api.ttoklip.domain.search.response;

import com.api.ttoklip.domain.mypage.dto.response.UserCartSingleResponse;
import java.util.List;
import lombok.Builder;
@Builder
public record CartPaging(List<UserCartSingleResponse> carts, Integer totalPage,
                         Long totalElements, Boolean isFirst, Boolean isLast) {
}
