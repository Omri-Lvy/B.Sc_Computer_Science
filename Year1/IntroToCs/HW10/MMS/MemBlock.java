package MMS;

/**
 * Represents a memory block.
 * A memory block has a base address, and a length in words. 
 * <br> (Part of Homework 10 in the Intro to CS course, Efi Arazi School of CS)
 */
public class MemBlock {

	// The address where this mempry block begins
	int baseAddress;
	// The length of this memory block, in words
	int length;

	/**
	 * Constructs a memory block with the given base address and length 
	 * 
	 * @param baseAddress The address of the first word in this memory block
	 * @param length The length of this memory block, in words
	 */
	public MemBlock(int baseAddress, int length) {
		this.baseAddress = baseAddress;
		this.length = length;
	}

	/**
	 * Checks if this memory block has the same base address and length as the other memory block
	 * 
	 * @param other The other memory block
	 * @return true if this memory block is the same as the other memory block, false otherwise
	 */
	public boolean equals(MemBlock other) {
		return baseAddress == other.baseAddress && length == other.length;
	}

	/**
	 * A textual representation of this memory block object, useful for debugging
	 * @return a string representing this memory block
	 */
	public String toString() {
		return "(" + baseAddress + " , " + length + ")";
	}
	
}