package com.api.ttoklip.domain.member.editor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class MemberEditor {
    private final String nickname;
    private final String street;
    private final int independentYear;
    private final int independentMonth;
    private final Integer locationX;
    private final Integer locationY;

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String nickname;
        private String street;
        private int independentYear;
        private int independentMonth;
        private Integer locationX;
        private Integer locationY;

        MemberEditorBuilder(){
        }

        public MemberEditorBuilder nickname(final String nickname) {
            if (StringUtils.hasText(nickname)) {
                this.nickname = nickname;
            }
            return this;
        }

        public MemberEditorBuilder street(final String street) {
            if (StringUtils.hasText(street)) {
                this.street = street;
            }
            return this;
        }

        public MemberEditorBuilder independentYear(final int independentYear) {
            this.independentYear = independentYear;
            return this;
        }

        public MemberEditorBuilder independentMonth(final int independentMonth) {
            this.independentMonth = independentMonth;
            return this;
        }

        public MemberEditorBuilder locationX(final Integer locationX) {
            if (locationX != null) {
                this.locationX = locationX;
            }
            return this;
        }

        public MemberEditorBuilder locationY(final Integer locationY) {
            if (locationY != null) {
                this.locationY = locationY;
            }
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(nickname, street, independentYear, independentMonth, locationX, locationY);
        }
    }
}
