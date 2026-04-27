public class QuantityMeasurementApp {

    // ===== ENUM: WeightUnit =====
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

        public double fromBase(double baseValue) {
            return baseValue / factor;
        }
    }

    // ===== CLASS: QuantityWeight =====
    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityWeight(double value, WeightUnit unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (Double.isNaN(value) || Double.isInfinite(value))
                throw new IllegalArgumentException("Invalid value");

            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toBase(value);
        }

        // ===== EQUALS =====
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;
            return Math.abs(this.toBase() - other.toBase()) < EPSILON;
        }

        // ===== CONVERT =====
        public QuantityWeight convertTo(WeightUnit targetUnit) {
            if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");

            double base = this.toBase();
            double converted = targetUnit.fromBase(base);
            return new QuantityWeight(converted, targetUnit);
        }

        // ===== ADD (default unit = this.unit) =====
        public QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        // ===== ADD (explicit unit) =====
        public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
            if (other == null || targetUnit == null)
                throw new IllegalArgumentException("Invalid input");

            double sumBase = this.toBase() + other.toBase();
            double result = targetUnit.fromBase(sumBase);

            return new QuantityWeight(result, targetUnit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ===== MAIN METHOD (TESTING) =====
    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        // Equality
        System.out.println("Equal: " + w1.equals(w2)); // true

        // Conversion
        System.out.println("Convert: " + w1.convertTo(WeightUnit.POUND));

        // Addition (default unit)
        System.out.println("Add: " + w1.add(w2));

        // Addition (explicit unit)
        System.out.println("Add in GRAM: " + w1.add(w2, WeightUnit.GRAM));
    }
}