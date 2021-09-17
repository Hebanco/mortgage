package testTask.ulytichev.mortgage.utils;

import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnValidator implements ConstraintValidator<InnValid, Seller> {

    @Override
    public boolean isValid(Seller seller, ConstraintValidatorContext constraintValidatorContext) {
        if (seller.getSellerType().equals(SellerType.COMPANY)) {
            long longInn = Long.parseLong(seller.getPersonalData());
            long resNumber = 0;
            int lastSymbol = (int) (longInn % 10);
            int tempInn = (int) (longInn / 10);
            resNumber += tempInn % 10 * 8;
            tempInn /= 10;
            resNumber += tempInn % 10 * 6;
            tempInn /= 10;
            resNumber += tempInn % 10 * 4;
            tempInn /= 10;
            resNumber += tempInn % 10 * 9;
            tempInn /= 10;
            resNumber += tempInn % 10 * 5;
            tempInn /= 10;
            resNumber += tempInn % 10 * 3;
            tempInn /= 10;
            resNumber += tempInn % 10 * 10;
            tempInn /= 10;
            resNumber += tempInn % 10 * 4;
            tempInn /= 10;
            resNumber += tempInn * 2;
            resNumber = resNumber % 11;
            if (resNumber % 10 == lastSymbol)
                return true;
            else
                return false;
        }
        else return true;
    }
}
