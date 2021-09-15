import java.util.ArrayList;
import java.util.List;

public class MyPriorityQueue<T extends Comparable<T>> {
	
	public List<T> minHeap;
	public int size;
	
	public MyPriorityQueue() {
		minHeap = new ArrayList<T>();
		size = 0;
	}
	
	public boolean offer(T item) {
		
		if (item == null) {
			throw new NullPointerException();
		}
		minHeap.add(item);
		size++;
		
		int index = minHeap.size()-1;
		
		while (index > 0 && minHeap.get(index).compareTo(minHeap.get(parent(index))) < 0) {
			swap(minHeap, index, parent(index));
			index = parent(index);
		}
		
		return true;
	}
	
	public T poll() {
		
		if (isEmpty()) {
			return null;
		}
		T node = minHeap.get(0);
		minHeap.set(0, minHeap.get(minHeap.size() - 1));
		minHeap.remove(minHeap.size() - 1);
		minHeap(minHeap, 0);
		size--;
		return node;
		
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//Helper method that performs the actual swapping when necessary.
	private void minHeap(List<T> list, int i) {
		
		int leftChild = 2*i + 1;   
		
		int rightChild = 2*i + 2; 
		int smallestValue;
		
		if (leftChild <= list.size()-1 && list.get(leftChild).compareTo(list.get(i)) < 0)
			smallestValue = leftChild;
		else
			smallestValue = i; 
		
		if (rightChild <= list.size()-1 && list.get(rightChild).compareTo(list.get(smallestValue)) < 0)
			smallestValue = rightChild; 

		if (smallestValue != i) {
			swap(list, i, smallestValue);
			minHeap(list, smallestValue);
		}
	}
	
	//Helper method to swap nodes in the priority queue. 
	private void swap(List<T> list, int i, int j) {
		T t = list.get(i);
		list.set(i, list.get(j));
		list.set(j, t);
	}
	
	private int parent (int i) {
		return (int)Math.floor((i - 1)/2);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		if (isEmpty()) {
			sb.append("]");
		} else {
			sb.append(minHeap.get(1));
			for (int i = 2; i < minHeap.size(); i++) {
				sb.append(", " + minHeap.get(i));
			}
			sb.append("]");
		}
		return sb.toString();
	}

}
