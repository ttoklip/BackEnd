package com.api.ttoklip.domain.main.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponses {

    private List<TitleResponse> housework;
    private List<TitleResponse> cooking;
    private List<TitleResponse> safeLiving;
    private List<TitleResponse> welfarePolicy;

}