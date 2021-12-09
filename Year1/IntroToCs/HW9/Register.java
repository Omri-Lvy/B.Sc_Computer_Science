/**
 * Represents a register.
 * A register is the basic storage unit of the Vic computer.
 */

public class Register {

    private int value; // the current value of this register

    /** Constructs a register and sets its value to 0. */
    public Register() {
        this.setValue(0);
    }

    public Register(int val) {
        this.setValue(val);
    }

    /**
     * Sets the value of this register.
     * 
     * @param v the value to which the register will be set.
     */
    public void setValue(int val) {
        this.value = val;
    }

    /** Increments the value of this register by 1. */
    public void addOne() {
        this.value = this.value + 1;
    }

    /**
     * Returns the value of this register.
     * 
     * @return the current value of this register, as an int.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns a textual representation of the value of this register.
     * 
     * @return Returns the value of this register, as a String.
     */
    public String toString() {
        return ("" + this.value);
    }
}
