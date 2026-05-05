package atm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Denomination {
    D5000(5000),
    D2000(2000),
    D1000(1000),
    D500(500),
    D200(200),
    D100(100),
    D50(50);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    /**
     * Номиналы в порядке, подходящем для выдачи (от большего к меньшему).
     */
    public static List<Denomination> forDispense() {
        return FOR_DISPENSE;
    }

    private static final List<Denomination> FOR_DISPENSE = Arrays.stream(values())
            .sorted(Comparator.comparingInt(Denomination::value).reversed()).toList();
}
