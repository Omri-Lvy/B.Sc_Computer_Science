
/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class TaskElement,
 * and the heap is ordered according to the Task instances wrapped by those
 * objects.
 * 
 * IMPORTANT: Except the percolation (private) functions and the constructors,
 * no single function may loop/recurse through all elements in the heap.
 * 
 * 
 *
 */
public class TaskHeap {

	public static int capacity = 200; // the maximum number of elements in the heap
	/*
	 * The array in which the elements are kept according to the heap order.
	 * The following must always hold true:
	 * if i < size then heap[i].heapIndex == i
	 */
	TaskElement[] heap;
	int size; // the number of elements in the heap, it is required that size <= heap.length

	/**
	 * Creates an empty heap which can contain 'capacity' elements.
	 */
	public TaskHeap() {
		heap = new TaskElement[capacity];
		size = 0;
	}

	/**
	 * Constructs a heap that may contain 'capacity' many elements, from a given
	 * array of TaskElements, of size at most 'capacity'.
	 * This should be done according to the "build-heap" function studied in class.
	 * NOTE: the heapIndex field of each TaskElement might be -1 (or incorrect).
	 * You may NOT use the insert function of heap.
	 * In this function you may use loops.
	 * 
	 */
	public TaskHeap(TaskElement[] arr) {
		heap = new TaskElement[capacity + 1];
		for (int i = 1; i <= arr.length; i++) {
			heap[i] = arr[i - 1];
			heap[i].heapIndex = i;
			size++;
			percUp(i, heap[i]);
		}
	}

	/**
	 * Returns the size of the heap.
	 *
	 * @return the size of the heap
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts a given element into the heap.
	 *
	 * @param e - the element to be inserted.
	 */
	public void insert(TaskElement e) {
		e.heapIndex = size + 1;
		heap[size + 1] = e;
		size++;
		percUp(size, e);
	}

	/**
	 * Returns and does not remove the element which wraps the task with maximal
	 * priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
	public TaskElement findMax() {
		if (size == 0) {
			throw new RuntimeException("The heap is empty");
		} else {

			return heap[1];
		}
	}

	/**
	 * Returns and removes the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
	public TaskElement extractMax() {
		if (size == 0) {
			throw new RuntimeException("The heap is empty");
		}
		TaskElement max = heap[1];
		heap[1] = heap[size];
		heap[size] = null;
		size--;
		percDown(1, heap[1]);
		return max;
	}

	/**
	 * Removes the element located at the given index.
	 * 
	 * Note: this function is not part of the standard heap API.
	 * Make sure you understand how to implement it, and why it is required.
	 * There are several ways this function could be implemented.
	 * No matter how you choose to implement it, you need to consider the different
	 * possible edge cases.
	 * 
	 * @param index
	 */
	public void remove(int index) {
		heap[index] = heap[size];
		percDown(index, heap[index]);
		size--;
	}

	private void percUp(int i, TaskElement e) {
		while (i > 1) {
			if (heap[(int) (Math.floor(i / 2))].t.compareTo(e.t) < 0) {
				TaskElement temp = heap[(int) (Math.floor(i / 2))];
				int tempIndex = temp.heapIndex;
				temp.heapIndex = e.heapIndex;
				e.heapIndex = tempIndex;
				heap[e.heapIndex] = e;
				heap[temp.heapIndex] = temp;
				i = (int) (Math.floor(i / 2));
			} else
				i = -1;
		}
	}

	private void percDown(int i, TaskElement e) {
		while (i < size) {
			int tempIndex = 0;
			if (i * 2 + 1 <= size && heap[i * 2 + 1].t.compareTo(e.t) > 0) {
				tempIndex = i * 2 + 1;
			} else if (i * 2 <= size && heap[i * 2].t.compareTo(e.t) > 0) {
				tempIndex = i * 2;
			}
			TaskElement temp = heap[tempIndex];
			e.heapIndex = tempIndex;
			temp.heapIndex = i;
			heap[tempIndex] = e;
			heap[i] = temp;
			i = tempIndex;
			if (2 * i > size) {
				i = size * 2;
			}
		}
	}

	public static void main(String[] args) {

		/*
		 * A basic test for the heap.
		 * You should be able to run this before implementing the queue.
		 * 
		 * Expected outcome:
		 * task: Add a new feature, priority: 10
		 * task: Solve a problem in production, priority: 100
		 * task: Solve a problem in production, priority: 100
		 * task: Develop a new feature, priority: 10
		 * task: Code Review, priority: 3
		 * task: Move to the new Kafka server, priority: 2
		 * 
		 */

		Task a = new Task(10, "Add a new feature");
		Task b = new Task(3, "Code Review");
		Task c = new Task(2, "Move to the new Kafka server");
		TaskElement[] arr = { new TaskElement(a), new TaskElement(b), new TaskElement(c) };
		TaskHeap heap = new TaskHeap(arr);
		System.out.println(heap.findMax());

		Task d = new Task(100, "Solve a problem in production");
		heap.insert(new TaskElement(d));
		System.out.println(heap.findMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());

	}
}
