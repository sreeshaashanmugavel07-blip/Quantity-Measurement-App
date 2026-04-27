public class QuantityMeasurementApp {

    // ✅ UC8: LengthUnit handles conversion (refactored responsibility)
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        // Convert THIS unit → base (feet)
        public double toBase(double value) {
            return value * factor;
        }

        // Convert base (feet) → THIS unit
        public double fromBase(double baseValue) {
            return baseValue / factor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        // Existing feature (UC5) — now uses delegation
        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Invalid target unit");
            }

            double base = unit.toBase(value);
            double result = targetUnit.fromBase(base);

            return new QuantityLength(result, targetUnit);
        }

        // Existing feature (UC6)
        public QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        // Existing feature (UC7)
        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            double sumBase = base1 + base2;

            double result = targetUnit.fromBase(sumBase);

            return new QuantityLength(result, targetUnit);
        }

        // Equality (UC4)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            return Math.abs(base1 - base2) < 0.0001;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        // Demonstration (all previous UC still working)
        System.out.println("Convert: " + q1.convertTo(LengthUnit.INCHES));
        System.out.println("Add: " + q1.add(q2));
        System.out.println("Add with target YARDS: " + q1.add(q2, LengthUnit.YARDS));
        System.out.println("Equal? " + q1.equals(q2));
    }
}