package com.api.ttoklip.domain.town.main.dto.response;


import java.util.List;

public record TownSearchResponse(List<TownTitleResponse> searchData, Integer totalPage,
                                 Long totalElements, Boolean isFirst, Boolean isLast) {
}
