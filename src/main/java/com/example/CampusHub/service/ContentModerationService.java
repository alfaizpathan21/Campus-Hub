package com.example.CampusHub.service;


import com.example.CampusHub.exception.ContentViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ContentModerationService {

    // ✅ easily extendable
    private static final List<String> BANNED_WORDS = List.of(
            "abuse",
            "hate",
            "spam",
            "badword"
    );

    // compiled patterns for performance
    private final List<Pattern> bannedPatterns =
            BANNED_WORDS.stream()
                    .map(word -> Pattern.compile("\\b" + word + "\\b",
                            Pattern.CASE_INSENSITIVE))
                    .toList();

    // 🔥 main validator
    public void validateText(String text) {

        if (text == null || text.isBlank()) {
            return;
        }

        for (Pattern pattern : bannedPatterns) {
            if (pattern.matcher(text).find()) {
                throw new ContentViolationException(
                        "Content violates community guidelines"
                );
            }
        }
    }
}
