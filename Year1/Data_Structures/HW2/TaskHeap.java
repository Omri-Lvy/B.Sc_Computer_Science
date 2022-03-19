
/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class TaskElement,
 * and the heap is ordered according to the Task instances wrapped by those objects.
 * 
 * IMPORTANT: Except the percolation (private) functions and the constructors, no single function may loop/recurse through all elements in the heap.
 * 		
 * 
 *
 */
public class TaskHeap{

	public static int capacity=200; // the maximum number of elements in the heap
	/*
	 * The array in which the elements are kept according to the heap order.
	 * The following must always hold true:
	 * 			if i < size then heap[i].heapIndex == i
	 */
	TaskElement[] heap;
	int size; // the number of elements in the heap, it is required that size <= heap.length
	
	/**
	 * Creates an empty heap which can contain 'capacity' elements.
	 */
	public TaskHeap(){
		//Your code comes here

	}
	
	/**
	 * Constructs a heap that may contain 'capacity' many elements, from a given array of TaskElements, of size at most 'capacity'. 
	 * This should be done according to the "build-heap" function studied in class.
	 * NOTE: the heapIndex field of each TaskElement might be -1 (or incorrect).
	 * You may NOT use the insert function of heap.
	 * In this function you may use loops.
	 * 
	 */
	public TaskHeap(TaskElement[] arr) {
		//Your code comes here

	}
	
    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size(){
		//Your code comes here
		return 0;
    }
    
    /**
     * Inserts a given element into the heap.
     *
     * @param e - the element to be inserted.
     */
    public void insert(TaskElement e){
        //Your code comes here
    }
    
    
	
	/**
	 * Returns and does not remove the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement findMax(){
		//Your code comes here
		return null;
    }
    
	/**
	 * Returns and removes the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
    public TaskElement extractMax() {
		//Your code comes here
		return null;
    }
    
    /**
     * Removes the element located at the given index.
     * 
	 * Note: this function is not part of the standard heap API.
	 *		 Make sure you understand how to implement it, and why it is required.
	 *       There are several ways this function could be implemented. 
	 * 		 No matter how you choose to implement it, you need to consider the different possible edge cases.
     * @param index
     */
    public void remove(int index){
        //Your code comes here
    }
    
    
    public static void main (String[] args){

        	/*
        	 * A basic test for the heap.
        	 * You should be able to run this before implementing the queue.
        	 * 
        	 * Expected outcome: 
        	 * 	task: Add a new feature, priority: 10
    		 *	task: Solve a problem in production, priority: 100
    		 *	task: Solve a problem in production, priority: 100
    		 *	task: Develop a new feature, priority: 10
    		 *	task: Code Review, priority: 3
    		 *	task: Move to the new Kafka server, priority: 2
        	 * 
        	 */
        	
        	Task a = new Task(10, "Add a new feature");
        	Task b = new Task(3, "Code Review");
        	Task c = new Task(2, "Move to the new Kafka server");
        	TaskElement [] arr = {new TaskElement(a), new TaskElement(b), new TaskElement(c)};
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
