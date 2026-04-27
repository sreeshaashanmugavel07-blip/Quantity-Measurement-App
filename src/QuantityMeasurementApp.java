public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBase(double value) {
            return value * factor;
        }

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

        public double convertTo(LengthUnit targetUnit) {
            double base = unit.toBase(value);
            return targetUnit.fromBase(base);
        }

        // ✅ UC6 FEATURE
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            double sumBase = base1 + base2;

            double result = this.unit.fromBase(sumBase);

            return new QuantityLength(result, this.unit);
        }
    }

    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result = q1.add(q2);

        System.out.println("Result: " + result.value + " " + result.unit);
    }
}
