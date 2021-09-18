package testTask.ulytichev.mortgage.annotation;

import testTask.ulytichev.mortgage.domain.Credit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreditValidator implements ConstraintValidator<CreditAmountValid, Credit> {
    @Override
    public boolean isValid(Credit credit, ConstraintValidatorContext constraintValidatorContext) {
        return credit.getTotalAmount() - credit.getCreditAmount() > 0;
    }
}
