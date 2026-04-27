package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    // ---------------- UC1 ----------------
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

    // ---------------- UC5 ----------------
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

    // ---------------- UC5 + UC6 ----------------
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

        // UC5: convert
        public double convertTo(LengthUnit targetUnit) {
            double base = unit.toBase(value);
            return targetUnit.fromBase(base);
        }

        public static double convert(double value, LengthUnit from, LengthUnit to) {
            if (from == null || to == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            double base = from.toBase(value);
            return to.fromBase(base);
        }

        // ---------------- UC6: ADDITION ----------------
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            // convert both to base (FEET)
            double base1 = this.unit.toBase(this.value);
            double base2 = other.unit.toBase(other.value);

            // add
            double sumBase = base1 + base2;

            // return result in THIS unit
            double result = this.unit.fromBase(sumBase);

            return new QuantityLength(result, this.unit);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        // UC1
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println("Feet Equal: " + f1.equals(f2));

        // UC5
        System.out.println("1 FEET → INCHES = " +
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        // ---------------- UC6 OUTPUT ----------------
        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        QuantityLength result1 = a.add(b);
        System.out.println("1 FEET + 12 INCHES = " + result1);

        QuantityLength c = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength d = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("3 FEET + 3 FEET = " + c.add(d));

        QuantityLength e = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength f = new QuantityLength(3.0, LengthUnit.FEET);

        System.out.println("1 YARD + 3 FEET = " + e.add(f));
    }
}