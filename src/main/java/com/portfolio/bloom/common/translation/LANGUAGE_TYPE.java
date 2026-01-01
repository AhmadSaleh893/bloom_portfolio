package com.portfolio.bloom.common.translation;

import com.portfolio.bloom.common.Constants;

/**
 * Supported language types enum.
 */
public enum LANGUAGE_TYPE {
    EN(Constants.DEFAULT_LANGUAGE),
    AR("ar"),
    HE("he");

    public final String lang;
    
    LANGUAGE_TYPE(String lang) {
        this.lang = lang;
    }
}
