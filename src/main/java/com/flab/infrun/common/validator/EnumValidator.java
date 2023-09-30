package com.flab.infrun.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private EnumValid enumValid;

    @Override
    public void initialize(final EnumValid constraintAnnotation) {
        this.enumValid = constraintAnnotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        Enum<?>[] enumValues = this.enumValid.type().getEnumConstants();

        for (Enum<?> enumValue : enumValues) {
            if (value.equals(enumValue.toString())
                || this.enumValid.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())) {
                return true;
            }
        }

        return false;
    }
}
