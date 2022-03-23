
/**
 * A queue class, implemented as a linked list.
 * The nodes of the linked list are implemented as the TaskElement class.
 * 
 * IMPORTANT: you may not use any loops/recursions in this class.
 */
public class TaskQueue {

	TaskElement first;
	TaskElement last;

	/**
	 * Constructs an empty queue
	 */
	public TaskQueue() {
		first = null;
		last = null;
	}

	/**
	 * Removes and returns the first element in the queue
	 * 
	 * @return the first element in the queue
	 */
	public TaskElement dequeue() {
		TaskElement temp = first;
		first = first.next;
		return temp;
	}

	/**
	 * Returns and does not remove the first element in the queue
	 * 
	 * @return the first element in the queue
	 */
	public TaskElement peek() {
		return first;
	}

	/**
	 * Adds a new element to the back of the queue
	 * 
	 * @param node
	 */
	public void enqueue(TaskElement node) {
		if (first == null) {
			first = node;
			last = first;
		} else {
			node.prev = last;
			last.next = node;
			last = node;

		}
	}

	public void remove(TaskElement node) {
		if (node == first) {
			first = node.next;
		} else if (node == last) {
			node.prev.next = null;
			last = node.prev;
		} else {
			node.prev.next = node.next;
		}
	}

	/**
	 * 
	 * @return true iff the queue is Empty
	 */
	public boolean isEmpty() {
		return (first == null);
	}
}
