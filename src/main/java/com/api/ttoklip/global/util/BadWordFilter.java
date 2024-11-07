package com.api.ttoklip.global.util;

import static com.api.ttoklip.global.exception.ErrorType.BAD_WORDS_ERROR;

import com.api.ttoklip.global.exception.BadWordException;
import com.vane.badwordfiltering.BadWordFiltering;
import java.util.Arrays;
import java.util.List;
public class BadWordFilter {

    public static void isBadWord(String... words) {
        BadWordFiltering filtering = new BadWordFiltering();

        List<String> badWords = Arrays.stream(words)
                .map(word -> word.replaceAll("[^\\p{IsAlphabetic}\\s]", ""))
                .filter(filtering::blankCheck)
                .toList();

        if (!badWords.isEmpty()) {
            String badWordsString = String.join(", ", badWords);
            String customMessage = BAD_WORDS_ERROR.getMessage() + " (욕설 단어들: " + badWordsString + ")";
            throw new BadWordException(BAD_WORDS_ERROR.getStatus(), BAD_WORDS_ERROR.getErrorCode(), customMessage);
        }
    }
}
