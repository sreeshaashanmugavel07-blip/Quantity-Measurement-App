package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // Enum for units
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

    // Quantity class
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

        // UC7: Addition with target unit
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

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Main method
    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Result in FEET: " +
                a.add(b, LengthUnit.FEET));

        System.out.println("Result in INCHES: " +
                a.add(b, LengthUnit.INCHES));

        System.out.println("Result in YARDS: " +
                a.add(b, LengthUnit.YARDS));
    }
}