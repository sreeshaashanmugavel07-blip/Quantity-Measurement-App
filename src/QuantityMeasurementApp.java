package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // Enum for units
    enum Unit {
        FEET(1.0),
        INCHES(1.0 / 12);

        private final double factor;

        Unit(double factor) {
            this.factor = factor;
        }

        public double toFeet(double value) {
            return value * factor;
        }
    }

    // Generic Quantity class
    public static class Quantity {
        private final double value;
        private final Unit unit;

        public Quantity(double value, Unit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Quantity)) return false;

            Quantity q = (Quantity) obj;
            return Double.compare(this.toFeet(), q.toFeet()) == 0;
        }
    }

    public static void main(String[] args) {
        Quantity q1 = new Quantity(1.0, Unit.FEET);
        Quantity q2 = new Quantity(12.0, Unit.INCHES);

        System.out.println("Equal: " + q1.equals(q2));
    }
}