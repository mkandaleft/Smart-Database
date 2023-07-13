Smart Database
 
Written by Mark Kandaleft
  
This smart database will choose between a Sequence ADT implemented with ArrayList
and a LinkedHashMap ADT depending on the size of the input. 
For inputs below 50 000, the database will use a sequence where each node is an ArrayList for less
space usage with slower methods and a linked hash map for larger inputs which
uses more space but has faster methods.
  
The project is composed of the following files:
 - Main which also containes a testing class
 - Tools contains method performed on the database
 - smartDB is the class that will assign either structure
 - Hashmap and Sequence classes which contain their respective methods
 - LinkedNode which is used by the sequence class
 - 3 test files (2 small, 1 large) found under TestFiles
