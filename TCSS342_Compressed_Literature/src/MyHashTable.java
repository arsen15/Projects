import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyHashTable<Key, Value> {
		
	private int capacity; // n
	private int size; //m
	private ArrayList<Node> buckets; // list of the key-value pair
	
	//for stats
	private int totalProbe;
	private int maxProbe;
	private int [] probes;
	
	private Set<Key> keySet;
	
	//Constructor
	public MyHashTable(int capacity) {
		this.capacity = capacity; //32767
		size = 0;
		buckets = new ArrayList<Node>(capacity);
		
		maxProbe = 0;
		totalProbe = 0;
		probes = new int [capacity];
		keySet = new HashSet<Key>();
		
		for (int i = 0; i < capacity; i++) {
			buckets.add(null);
		}
	}
	
	//put method
	public void put(Key key, Value value) {
		
		int index = hash(key);
		Node node = new Node(key, value);
		boolean replacing = false;
		int probe = 0;
		
		if(keySet.size() < capacity) {
			while (buckets.get(index) != null) {
				if (buckets.get(index).key.equals(key)) {
					replacing = true;
					totalProbe++;
					break;
				}
				totalProbe++;
				index = (index + 1) % capacity;
				probe = probe + 1;
				
			}
		}
		
		if (buckets.get(index) == null || replacing) {
			Node temp = buckets.get(index);
			temp = node;
			buckets.set(index, temp); //replacing the null node with new node that has key-value pair.
			keySet.add(key);
			size++;
			totalProbe++;
		}	
		
		if (probe > maxProbe) {
			maxProbe = probe;
		}
		probes[probe]++;
	}
	
	//get method
	public Value get(Key key) {
		
		int index = hash(key);
		Value value;
		if (key == null) {
			value = null;
		}
		if (buckets.get(index) == null) {
			System.out.println("this node is null.");
			return null;
		}
		while (!buckets.get(index).key.equals(key)) {
			index = (index + 1) % capacity;
		}
		
		value = buckets.get(index).value; 
		
		return value;
	}
	
	public boolean containsKey(Key key) {
		return keySet.contains(key);
	}
		
	//keySet method
	public Set<Key> keySet(){
		return keySet;
	}
	
	// hash method
		private int hash(Key key) {
			return Math.abs(key.hashCode() % capacity);
		}
	
	//stats() method
	public void stats() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nNumber of Entries (size): " + size);
		sb.append("\nNumber of Buckets (capacity): " + capacity);
		sb.append("\nTotal Probes: " + totalProbe);
		sb.append("\nMax Linear Probe: " + maxProbe);
		
		sb.append("\nHistogram of Probes: [");
		for (int i = 0; i < maxProbe + 1; i++) {
			sb.append(probes[i] + ", ");
			if (i == (maxProbe / 2)) {
				sb.append("\n");
			}
		}
		System.out.println(sb);
	}
	
	//toString() method
	@Override
	public String toString() {
		
		int max = capacity - 1;
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for(int i = 0; ; i++) {
			if (buckets.get(i) == null) {
				if (i == max) {
					return sb.append("null }").toString();
				}
				sb.append("null, ");
			} else {
				Node node = new Node(buckets.get(i).key, buckets.get(i).value);
				sb.append(node.key + " = ");
				sb.append(node.value);
			
				if (i == max) {
					return sb.append('}').toString();
				}
				sb.append(", ");
			}
		}
	}
	
	public int size() {
		return size;
	}
	
	
	//Private inner class
	private class Node{
		public Key key;
		public Value value;
		
		//Node constructor
		public Node(Key key, Value value) {
			this.key = key;
			this.value = value; 
		}
		
		public String toString() {
			return key + " = " + value;
		}
	}

}
