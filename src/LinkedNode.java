
import java.util.Arrays;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

// This class is a ArrayList Node of the Sequence class
class LinkedNode {

  private ArrayList<SimpleEntry<String, Object>> values;
  private String key;
  private LinkedNode next = null;
 
  // Initialize the Linked list named values
  LinkedNode(String key) {
    this.key = key;
    values = new ArrayList<>();
  }

  //Return the key of the current node
  String getKey() {
    return this.key;
  }

  // Get the values for the current node
  SimpleEntry[] getValues() {
    return this.values.toArray(new SimpleEntry[0]);
  }

  // Add a value to the values ArrayList
  void addValue(SimpleEntry<String, Object> newValue) {
    this.values.add(newValue);
  }

  // "Remove the node by setting the History and clearing the rest of the values
  void remove() {
  
    var prevValues = this.values;
    this.values = new ArrayList<>();
    this.values.add(new SimpleEntry<>("History", prevValues));
  }

  // Return the next node
  LinkedNode getNext() {
    return this.next;
  }

  // Set the next element
  void setNext(LinkedNode next) {
    this.next = next;
  }

  // Add a value to the ArrayList
  void setValues(SimpleEntry<String, Object>[] newValues) {
    this.values = new ArrayList<>(Arrays.asList(newValues));
  }

  // if there is no next pointer, return true
  boolean isLast() {
    return this.next == null;
  }

  // Checks if the there is values or history
  boolean isEmpty() {
    
    boolean noValues = (this.values == null);
    boolean historyOnly = (this.values != null
                    && this.values.size() == 1
                    && this.values.get(0).getKey().equals("History"));
    return (noValues || historyOnly);
  }
}