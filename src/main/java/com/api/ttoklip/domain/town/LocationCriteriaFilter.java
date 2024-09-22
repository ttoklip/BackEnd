package com.api.ttoklip.domain.town;

import static com.api.ttoklip.domain.town.community.post.entity.QCommunity.community;

import com.api.ttoklip.global.exception.ApiException;
import com.api.ttoklip.global.exception.ErrorType;
import com.querydsl.core.types.dsl.BooleanExpression;

public class LocationCriteriaFilter {

    private static final String SPLIT_CRITERIA = " ";
    
    public static BooleanExpression getLocationFilterByTownCriteria(final TownCriteria townCriteria,
                                                                    final String street) {
        String[] streetParts = splitStreet(street);  // 공통 메서드로 분리

        if (townCriteria.equals(TownCriteria.CITY)) {
            return filterByCity(streetParts);
        }

        if (townCriteria.equals(TownCriteria.DISTRICT)) {
            return filterByDistrict(streetParts);
        }

        if (townCriteria.equals(TownCriteria.TOWN)) {
            return filterByTown(streetParts);
        }

        throw new ApiException(ErrorType.INTERNAL_STREET_TYPE);
    }

    // 주소를 공백으로 분리하는 로직을 하나의 메서드로 추출
    private static String[] splitStreet(final String street) {
        return street.split(SPLIT_CRITERIA);
    }

    // '시' 부분만 추출해서 필터링 (예: '서울특별시'로 시작하는 모든 주소)
    private static BooleanExpression filterByCity(final String[] streetParts) {
        if (streetParts.length > 0) {
            String city = streetParts[0];
            return community.member.street.startsWith(city);
        }
        return null;
    }

    // '시'와 '구'가 모두 일치해야 함 (예: '서울특별시 서대문구')
    private static BooleanExpression filterByDistrict(final String[] streetParts) {
        if (streetParts.length > 1) {
            String city = streetParts[0];
            String district = streetParts[1];
            return community.member.street.startsWith(city + SPLIT_CRITERIA + district);
        }
        return null;
    }

    // '시', '구', '동'이 모두 일치해야 함 (예: '서울특별시 서대문구 북가좌동')
    private static BooleanExpression filterByTown(final String[] streetParts) {
        if (streetParts.length > 2) {
            String city = streetParts[0];
            String district = streetParts[1];
            String town = streetParts[2];
            return community.member.street.startsWith(city + SPLIT_CRITERIA + district + SPLIT_CRITERIA + town);
        }
        return null;
    }
}
