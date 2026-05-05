import atm.AtmImpl;
import atm.CannotDispenseException;
import atm.Denomination;

import java.util.EnumMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        var atm = new AtmImpl();

        Map<Denomination, Integer> deposit = new EnumMap<>(Denomination.class);
        deposit.put(Denomination.D1000, 2);
        deposit.put(Denomination.D500, 1);
        deposit.put(Denomination.D200, 3);
        deposit.put(Denomination.D50, 10);
        atm.deposit(deposit);

        long expectedBalance = 2 * 1000 + 1 * 500 + 3 * 200 + 10 * 50;
        assert atm.balance() == expectedBalance : "Balance mismatch after deposit";

        var w1 = atm.withdraw(1250);
        assert w1.get(Denomination.D1000) == 1;
        assert w1.get(Denomination.D200) == 1;
        assert w1.get(Denomination.D50) == 1;
        assert atm.balance() == expectedBalance - 1250 : "Balance mismatch after withdraw";

        var balanceBeforeFail = atm.balance();
        try {
            atm.withdraw(30);
            throw new AssertionError("Expected CannotDispenseException");
        } catch (CannotDispenseException expected) {
            assert atm.balance() == balanceBeforeFail : "Balance must not change on failed withdraw";
        }

        System.out.println("OK. Balance=" + atm.balance());
        System.out.println("Dispensed 1250: " + w1);
    }
}
