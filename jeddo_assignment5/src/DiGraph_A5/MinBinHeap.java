package DiGraph_A5;

import DiGraph_A5.EntryPair;

public class MinBinHeap {
	private EntryPair[] array; 
	  private int size;
	  private static final int arraySize = 10000; 
	  
	public MinBinHeap() {
	
	this.array = new EntryPair[arraySize];
	
  array[0] = new EntryPair(null, -100000);
  
  }
	
	public EntryPair[] getHeap() { 
	    return this.array;
	}

	public void insert(EntryPair entry) {
		if (size == 0) {
			array[1] = entry;
			size++;
		} else { 
			for(int i = 1; i <= size + 1; i++) {
				if (array[i] == null) {
					array[i] = entry;
					bubbleUp(i);
					size++;
					return;
				}
			}
		}
		
		
		
	}

	public void delMin() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return;
		} else {
			for (int i = 1; i <= size + 1; i++) {
				if (array[i] == null) {
					
					array[1] = array[i - 1];
					array[i - 1] = null;
					
					size--;
					bubbleDown(1);
					
					return;
				}

			}
		}
		
	}

	
	public EntryPair getMin() {
		// TODO Auto-generated method stub
		if (this.size != 0) {
			
			return array[1];
			
		} else {
			return null;
		}
	}

	
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	
	public void build(EntryPair[] entries) {
		
		int index = 0;
		
		while (index < entries.length && entries[index] != null) {
			array[index + 1] = entries[index];
			size++;
			index++;
		}
		
		for (int i = size() / 2; i > 0; i--) {
			bubbleDown(i);
		}
	}
	
	public void bubbleUp(int i) {
		EntryPair current = array[i / 2];
		if (array[i].getPriority() > current.getPriority()) {
			return;
		} else {
			array[i / 2] = array[i];
			array[i] = current;
			bubbleUp(i /= 2);
		}
	}
	
	public void bubbleDown(int newRoot) {
		
		EntryPair current = array[newRoot];
		if (current == null || newRoot * 2 > size) {
			return;
		}
		
		EntryPair leftChild = array[newRoot * 2];
		if (leftChild == null) {
			leftChild = new EntryPair(null, 1000000);
		}
		
		EntryPair rightChild = array[newRoot * 2 + 1];
		if (rightChild == null) {
			rightChild = new EntryPair(null, 1000000);
		}
		
		if (leftChild.getPriority() > current.getPriority() && rightChild.getPriority() > current.getPriority()) {
			return;
		} else if (leftChild.getPriority() < rightChild.getPriority()) {
			array[newRoot] = array[newRoot * 2];
			array[newRoot * 2] = current;
			bubbleDown(newRoot * 2);
		} else {
			array[newRoot] = array[newRoot * 2 + 1];
			array[newRoot * 2 + 1] = current;
			bubbleDown(newRoot * 2 + 1);
		}
	}
	
	private int getParent(int i) {
		return i / 2;
	}

	private int getLeft(int i) {
		return 2 * i;
	}

	private int getRight(int i) {
		return 2 * i + 1;
	}

}
