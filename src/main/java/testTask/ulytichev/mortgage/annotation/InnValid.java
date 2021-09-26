package testTask.ulytichev.mortgage.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InnValidator.class)
public @interface InnValid {
    String message() default "Не правильно введен ИНН";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}