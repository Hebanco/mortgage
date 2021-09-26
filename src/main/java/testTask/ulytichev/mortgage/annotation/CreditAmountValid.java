package testTask.ulytichev.mortgage.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CreditValidator.class)
public @interface CreditAmountValid {
    String message() default "Сумма кредита превышает общую стоимость";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
