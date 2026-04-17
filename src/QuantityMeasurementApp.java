package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    enum Unit {
        FEET(1.0),
        INCHES(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        Unit(double factor) {
            this.factor = factor;
        }

        public double toFeet(double value) {
            return value * factor;
        }
    }

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

        Quantity q1 = new Quantity(1.0, Unit.YARDS);
        Quantity q2 = new Quantity(3.0, Unit.FEET);

        Quantity q3 = new Quantity(1.0, Unit.CENTIMETERS);
        Quantity q4 = new Quantity(0.393701, Unit.INCHES);

        System.out.println("Yard to Feet Equal: " + q1.equals(q2));
        System.out.println("CM to Inch Equal: " + q3.equals(q4));
    }
}