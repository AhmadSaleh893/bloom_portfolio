package com.portfolio.bloom.domain.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumTypeExistsValidator implements ConstraintValidator<EnumTypeExists, Object> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(EnumTypeExists constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // If value is already an enum of the correct type, it's valid
        if (enumClass != null && enumClass.isInstance(value)) {
            return true;
        }

        // Otherwise, check if the string representation matches any enum constant
        if (enumClass != null && enumClass.isEnum()) {
            Object[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants != null) {
                String valueStr = value.toString();
                for (Object constant : enumConstants) {
                    String enumName = ((Enum<?>) constant).name();
                    if (ignoreCase) {
                        if (enumName.equalsIgnoreCase(valueStr)) {
                            return true;
                        }
                    } else {
                        if (enumName.equals(valueStr)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}

