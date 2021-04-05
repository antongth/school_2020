package net.thumbtack.school.springrest.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearOfReleaseValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface YearOfRelease {
    String message() default "Invalid year of release";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
