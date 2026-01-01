package com.portfolio.bloom.common.translation;

import com.portfolio.bloom.common.Translation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for language handling and translation support.
 */
public class LangList {
    
    private static final Set<String> SUPPORTED_LANGUAGES =
        Collections.unmodifiableSet(
            Arrays.stream(LANGUAGE_TYPE.values())
                  .map(t -> t.lang)
                  .collect(Collectors.toSet())
        );

    public static Set<String> getSupportedLanguages() {
        return SUPPORTED_LANGUAGES;
    }

    public static List<String> getSupportedLanguagesList() {
        return Arrays.asList(LANGUAGE_TYPE.values())
                     .stream()
                     .map(t -> t.lang)
                     .collect(Collectors.toList());
    }

    /**
     * Resolves and validates language code from request header.
     * Handles formats like "en-US", "ar-SA", "ar,en;q=0.9", etc.
     * 
     * @param lang Language code from request header
     * @return Validated language code, defaults to "en" if invalid
     */
    public static String resolveLanguage(String lang) {
        if (lang == null || lang.isBlank()) {
            return LANGUAGE_TYPE.EN.lang; // default
        }

        // Clean up: handle "en-US", "ar-SA", "ar,en;q=0.9", etc.
        String cleanLang = lang.split(",")[0].split("-")[0].trim().toLowerCase();

        // Validate
        if (!SUPPORTED_LANGUAGES.contains(cleanLang)) {
            // Fallback to default
            return LANGUAGE_TYPE.EN.lang;
        }
        return cleanLang;
    }

    /**
     * Checks if a translation exists for the given key and language.
     * 
     * @param translation Translation object containing translations map
     * @param key Translation key (field name)
     * @param lang Language code
     * @return true if valid translation exists, false otherwise
     */
    public static boolean isValid(Translation translation, String key, String lang) {
        if (translation == null || translation.getTranslations() == null) {
            return false;
        }
        if (translation.getTranslations().get(key) == null) {
            return false;
        }
        String translationValue = translation.getTranslations().get(key).get(lang);
        return translationValue != null && !translationValue.isBlank();
    }
}
