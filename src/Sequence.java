import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

// This class is the small database
class Sequence {
  
  private LinkedNode head = null;

  // Return key O(n)
  SimpleEntry[] get(String key) {
    
    if (this.head == null)                          // Return null if sequence empty
      return null;
      
    LinkedNode position = this.head;                // Else, create new node and set it as head. Then
    while (!position.getKey().equals(key)) {        // iterate through sequence until key is found
      if (position.isLast())                        // 
        return null;
      position = position.getNext();
    }
    return position.getValues();
  }

  // Verifies if item is contained in sequence O(n)
  boolean contains(String key) {
    
    if (this.head == null)
      return false;
      
    LinkedNode position = this.head;
    while (!position.getKey().equals(key)) {
      if (position.isLast())
        return false;
      position = position.getNext();
    }
    return position.isEmpty();
  }

  // Returns sorted keys O(n)
  String[] allKeys() {
    
    if (this.head == null)
      return null;
      
    LinkedNode position = this.head;
    ArrayList<String> keys = new ArrayList<>();
    while (!position.isLast()) {
      keys.add(position.getKey());
      position = position.getNext();
    }
    keys.add(position.getKey());
    return sortKeys(keys);
  }

  // Add key item pair O(n)
  void add(String key, SimpleEntry<String, Object> value) {
    // Reserve the "History" keyword for the program
    if (value.getKey().equals("History"))
      throw new RuntimeException("\"History\" cannot be used as key."); // "History" is a keyword
      
    if (key == null || key.isEmpty())
      return;
      
    if (this.head == null) {                 // Create head initially
      this.head = new LinkedNode(key);
      this.head.addValue(value);
      return;
    }
    
    LinkedNode position = this.head;
    while (!position.isLast()) {
      if (position.getKey().equals(key)) {   // If the key exists the simply add the data
        position.addValue(value);
        return;
      }
      position = position.getNext();
    }
    
    LinkedNode newNode = new LinkedNode(key); // Adjust the pointers, now the new node is the new tail
    newNode.addValue(value);
    position.setNext(newNode);
  }

  // Removes a key O(n)
  void remove(String key) {
    
    if (this.head == null)
      return;
      
    LinkedNode position = this.head;
    while (!position.getKey().equals(key)) {        // Set new node as head and iterate through
      if (position.isLast())                        // the sequence.
        return;
      position = position.getNext();
    }
    position.remove();
  }
 
  // Returns history O(n)
  SimpleEntry[] history(String key) {
    
    if (this.head == null)
      return null;
      
    LinkedNode position = this.head;
    while (!position.getKey().equals(key)) {
      if (position.isLast())
        return null;
      position = position.getNext();
    }
    
    for (SimpleEntry pair : position.getValues()) {     // Get the history for that key                  
      if (pair.getKey().equals("History"))
        return ((ArrayList<SimpleEntry>) pair.getValue()).toArray(new SimpleEntry[0]);
    }
    return null;
  }

  // Return first key O(1)
  String firstKey() {
    return this.head.getKey();
  }

  // Return next key O(n)
  String nextKey(String key) {
    
    if (this.head == null)
      return null;
      
    LinkedNode position = this.head;
    while (!position.getKey().equals(key)) {  
      if (position.isLast())
        return null;
      position = position.getNext();
    }
    return position.getNext().getKey();
  }

  // Return previous key O(n)
  String prevKey(String key) {
    
    if (this.head == null)
      return null;
      
    LinkedNode position = this.head;
    LinkedNode lastIteration = null;
    while (!position.getKey().equals(key)) {
      if (position.isLast())
          return null;
      lastIteration = position;
      position = position.getNext();
    }
    
    if (lastIteration == null)
      return null;
    return lastIteration.getKey();
  }

  // Check if empty 
  boolean isEmpty() {
    return this.head.isEmpty() && this.head.isLast();
  }

  // The next few methods are used for heap Sort which order all elements into a tree with right
  // left pointers. The algorithm sorts by keeping the parent larger than the child, switching positions
  // otherwies. This has a time complexity of O(nlogn) whic is much faster than 
  // the conventional sorting O(n^2).
  private String[] sortKeys(ArrayList<String> keys) {
    
    String[] sorted = keys.toArray(new String[0]);              
    int size = sorted.length;
    
    for (int i = size / 2 - 1; i >= 0; i--)                    // call heapify to sort the array
      heapify(sorted, size, i);
    for (int i = size - 1; i >= 0; i--) {
      String x = sorted[0];
      sorted[0] = sorted[i];
      sorted[i] = x;
      heapify(sorted, i, 0);
    }
    return sorted;
  }

  // Heapify sorts the tree largest up recursively O(1)
  private void heapify(String[] array, int heapSize, int i) {

    int largest = i;                                                                    // Initialize largest as root
    int left  = 2 * i + 1;                                                              // Point to the left child
    int right  = 2 * i + 2;                                                             // Point to the right child
                                                               
    if (left < heapSize && compareStrings(array[left], array[largest]))                 // Check if left child is larger than root or
      largest = left ;                                                                  // if right child is larger than largest.
    if (right < heapSize && compareStrings(array[right], array[largest]))               // If la
      largest = right ;
    
    if (largest != i) {
      String swap = array[i];
      array[i] = array[largest];                                                        // Recursive call to  heapify the smaller tree
      array[largest] = swap;    
      heapify(array, heapSize, largest);
    }
  }

  // Returns true if string a is larger than string b O(n)
  private boolean compareStrings(String a, String b) {
    
    for (int i = 0; i < a.length(); i++) {
      if (i >= b.length())
        return true;
      if (a.charAt(i) > b.charAt(i))
        return true;
      else if (a.charAt(i) < b.charAt(i))
        return false;
    }
    return a.length() < b.length();
  }

  // Optional method moves data from map to sequence
  // used in setThreshold O(n)
  void move(HashMap map) {
    
    LinkedNode position = null;
    String[] keys = map.allKeys();
    for (int i = 0; i < keys.length; i++) {
      if (i == 0) {
        position = new LinkedNode(keys[i]);
        position.setValues(map.get(keys[i]));
        this.head = position;
      } else {
        position.setNext(new LinkedNode(keys[i]));
        position = position.getNext();
        position.setValues(map.get(keys[i]));
      }
    }
  }
}