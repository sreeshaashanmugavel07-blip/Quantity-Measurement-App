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

        // ✅ UC5 FEATURE
        public double convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Invalid target unit");
            }

            double base = unit.toBase(value);
            return targetUnit.fromBase(base);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof QuantityLength)) return false;

            QuantityLength other = (QuantityLength) obj;
            return Math.abs(this.unit.toBase(this.value) -
                            other.unit.toBase(other.value)) < 0.0001;
        }
    }

    public static void main(String[] args) {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);

        System.out.println("1 FEET → INCHES = " + q.convertTo(LengthUnit.INCHES));
        System.out.println("1 FEET → YARDS = " + q.convertTo(LengthUnit.YARDS));
    }
}
