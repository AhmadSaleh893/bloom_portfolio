package com.portfolio.bloom.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * Base class providing translation support for multi-language content.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "translationBuilder")
@Data
public class Translation {
    private Map<String, Map<String, String>> translations;
}
