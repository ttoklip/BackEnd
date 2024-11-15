package com.infrastructure.util;

import static com.common.exception.ErrorType.BAD_WORDS_ERROR;

import com.common.exception.BadWordException;
import com.vane.badwordfiltering.BadWordFiltering;
import java.util.Arrays;
import java.util.List;


public class BadWordUtil {

    public static void isBadWord(String... words) {
        BadWordFiltering filtering = new BadWordFiltering();

        List<String> badWords = Arrays.stream(words)
                .map(word -> word.replaceAll("[^\\p{IsAlphabetic}\\p{IsWhite_Space}]", ""))
                .filter(filtering::blankCheck)
                .toList();

        if (!badWords.isEmpty()) {
            String badWordsString = String.join(", ", badWords);
            String customMessage = BAD_WORDS_ERROR.getMessage() + " (욕설 단어들: " + badWordsString + ")";
            throw new BadWordException(BAD_WORDS_ERROR.getStatus(), BAD_WORDS_ERROR.getErrorCode(), customMessage);
        }
    }
}
