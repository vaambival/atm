package atm;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public final class AtmImpl implements Atm {
    private final EnumMap<Denomination, CashCell> cells = new EnumMap<>(Denomination.class);

    public AtmImpl() {
        for (Denomination d : Denomination.values()) {
            cells.put(d, new CashCell(d));
        }
    }

    @Override
    public void deposit(Map<Denomination, Integer> banknotes) {
        validate(banknotes);

        for (var denominationWithCount : banknotes.entrySet()) {
            cells.get(denominationWithCount.getKey()).add(denominationWithCount.getValue());
        }
    }

    private void validate(Map<Denomination, Integer> banknotes) {
        if (Objects.isNull(banknotes)) {
            throw new InvalidDepositException("Banknotes is null");
        }

        for (var entry : banknotes.entrySet()) {
            var denomination = entry.getKey();
            var count = entry.getValue();
            if (Objects.isNull(denomination)) {
                throw new InvalidDepositException("Denomination is null");
            }
            if (Objects.isNull(count)) {
                throw new InvalidDepositException("Count is null for " + denomination);
            }
            if (count < 0) {
                throw new InvalidDepositException("Count must be >= 0 for " + denomination);
            }
        }
    }

    @Override
    public Map<Denomination, Integer> withdraw(int amount) throws CannotDispenseException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        int remaining = amount;
        EnumMap<Denomination, Integer> toDispense = new EnumMap<>(Denomination.class);

        for (var denomination : Denomination.forDispense()) {
            if (remaining == 0) {
                break;
            }
            int denominationValue = denomination.value();
            int available = cells.get(denomination).count();

            int need = remaining / denominationValue;
            int take = Math.min(available, need);
            if (take > 0) {
                toDispense.put(denomination, take);
                remaining -= take * denominationValue;
            }
        }

        if (remaining != 0) {
            throw new CannotDispenseException("Cannot dispense amount=" + amount + ", remaining=" + remaining);
        }

        for (var countByDenomination : toDispense.entrySet()) {
            cells.get(countByDenomination.getKey()).remove(countByDenomination.getValue());
        }

        return toDispense;
    }

    @Override
    public long balance() {
        long sum = 0;
        for (CashCell cell : cells.values()) {
            sum += 1L * cell.denomination().value() * cell.count();
        }
        return sum;
    }
}
