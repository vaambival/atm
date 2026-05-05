package atm;

import java.util.Map;

public interface Atm {
    void deposit(Map<Denomination, Integer> banknotes);

    Map<Denomination, Integer> withdraw(int amount) throws CannotDispenseException;

    long balance();
}
