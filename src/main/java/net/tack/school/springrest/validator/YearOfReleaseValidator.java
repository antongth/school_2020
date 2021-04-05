package net.thumbtack.school.springrest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearOfReleaseValidator implements ConstraintValidator<YearOfRelease, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer > 1970;
    }
}
