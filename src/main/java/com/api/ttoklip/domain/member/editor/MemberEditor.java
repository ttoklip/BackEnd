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

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String nickname;
        private String street;
        private int independentYear;
        private int independentMonth;

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

        public MemberEditor build() {
            return new MemberEditor(nickname, street, independentYear, independentMonth);
        }
    }
}
