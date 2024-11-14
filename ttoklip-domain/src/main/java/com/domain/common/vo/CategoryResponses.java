package com.domain.common.vo;

import java.util.List;
import lombok.Builder;

@Builder
public record CategoryResponses(
        List<TitleResponse> housework,
        List<TitleResponse> cooking,
        List<TitleResponse> safeLiving,
        List<TitleResponse> welfarePolicy
) {

}