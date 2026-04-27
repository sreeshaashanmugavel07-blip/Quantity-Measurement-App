package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // ---------------- UC1: Feet Class ----------------
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet feet = (Feet) obj;
            return Double.compare(feet.value, value) == 0;
        }
    }

    // ---------------- UC5: LengthUnit Enum ----------------
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        // Convert to base unit (FEET)
        public double toBase(double value) {
            return value * factor;
        }

        // Convert from base unit (FEET)
        public double fromBase(double baseValue) {
            return baseValue / factor;
        }
    }

    // ---------------- UC5: QuantityLength Class ----------------
    public static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        // Instance method
        public double convertTo(LengthUnit targetUnit) {
            double baseValue = unit.toBase(value);
            return targetUnit.fromBase(baseValue);
        }

        // Static method (as per UC5 requirement)
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == null || target == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            double baseValue = source.toBase(value);
            return target.fromBase(baseValue);
        }
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        // UC1 check
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("Feet Equal: " + f1.equals(f2));

        // ---------------- UC5 TEST OUTPUTS ----------------
        System.out.println("1 FEET → INCHES = " +
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("3 YARDS → FEET = " +
                QuantityLength.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("36 INCHES → YARDS = " +
                QuantityLength.convert(36.0, LengthUnit.INCHES, LengthUnit.YARDS));

        System.out.println("2.54 CM → INCHES = " +
                QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES));

        System.out.println("0 FEET → INCHES = " +
                QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES));
    }
}
