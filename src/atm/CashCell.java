package atm;

public final class CashCell {
    private final Denomination denomination;
    private int count;

    public CashCell(Denomination denomination) {
        this(denomination, 0);
    }

    public CashCell(Denomination denomination, int initialCount) {
        if (denomination == null) {
            throw new IllegalArgumentException("Denomination is null");
        }
        if (initialCount < 0) {
            throw new IllegalArgumentException("InitialCount must be >= 0");
        }
        this.denomination = denomination;
        this.count = initialCount;
    }

    public Denomination denomination() {
        return denomination;
    }

    public int count() {
        return count;
    }

    public void add(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        count += n;
        if (count < 0) {
            throw new IllegalArgumentException("Overflow count");
        }
    }

    public void remove(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n > count) {
            throw new IllegalArgumentException("Not enough banknotes in cell");
        }
        count -= n;
    }
}
