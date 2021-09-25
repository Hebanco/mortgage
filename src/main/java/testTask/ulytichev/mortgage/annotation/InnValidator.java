package testTask.ulytichev.mortgage.annotation;

import testTask.ulytichev.mortgage.domain.Seller;
import testTask.ulytichev.mortgage.domain.SellerType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InnValidator implements ConstraintValidator<InnValid, Seller> {
    @Override
    public boolean isValid(Seller seller, ConstraintValidatorContext constraintValidatorContext) {
        if (seller.getSellerType()!=null&&seller.getSellerType().equals(SellerType.COMPANY)) {
            long longInn = Long.parseLong(seller.getPersonalData());
            long resNumber = 0;
            int lastSymbol = (int) (longInn % 10);
            resNumber += countParameter(longInn /= 10, 8);
            resNumber += countParameter(longInn /= 10, 6);
            resNumber += countParameter(longInn /= 10, 4);
            resNumber += countParameter(longInn /= 10, 9);
            resNumber += countParameter(longInn /= 10, 5);
            resNumber += countParameter(longInn /= 10, 3);
            resNumber += countParameter(longInn /= 10, 10);
            resNumber += countParameter(longInn /= 10, 4);
            resNumber += countParameter(longInn, 2);
            resNumber %= 11;
            if (resNumber % 10 == lastSymbol)
                return true;
            else return false;
        }
        return true;
    }

    private long countParameter(long tempInn, int param){
        return tempInn % 10 * param;
    }
}
