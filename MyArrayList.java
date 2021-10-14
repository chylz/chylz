import java.util.Iterator;
import java.util.ListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * The MyArrayList class is a simplified implementation of ArrayList with the capability of Iterator and ListIterator, the ability to add, remove, get, and set elements as well as be printed with 
 * the toString method. 
 * 
 * @author Luke Zeng
 * 
 * @version 10/13/2021
 */
public class MyArrayList<E>
{
    private int size;//the current size of the MyArrayList
    private Object[] values; //the array to implement the list. 
    
    /**
     * Constructs a new MyArrayList object with a default size of 0. 
     */
    
    public MyArrayList()
    {
        size = 0;
        values = new Object[1];
    }
    
    /**
     * Converts the current MyArrayList to a string and returns it. 
     * 
     * @return the MyArrayList in string form.
     * 
     * @postcondition returns the MyArrayList as a string. 
     */
    
    public String toString()
    {
        if (size == 0)
            return "[]";

        String s = "[";
        for (int i = 0; i < size - 1; i++)
            s += values[i] + ", ";
        return s + values[size - 1] + "]";
    }

    /**
     * Replaces the current values array with an array that is twice as long with the same values. 
     * 
    * @postcondition replaces the array with one that is
    *               twice as long, and copies all of the
    *               old elements into it
    */
    private void doubleCapacity()
    {
        Object[] temp = new Object[values.length*2];
        for(int i = 0; i<values.length; i++)
        {
            temp[i] = values[i];
        }
        values = temp;
    }

    /**
     * Gives the maximum amount of objects the array can store before needing to be doubled in capacity.
     * 
     * @return returns the length of the array. 
     * 
    * @postcondition returns the length of the array
    */
   
    public int getCapacity()
    {
        return values.length;
    }
    
    /**
     * Gives the current size of the MyArrayList. 
     * 
     * @return returns the size of the MyArrayList. 
     * 
     * @postcondition returns the current size of the MyArrayList. 
     */
    
    public int size()
    {
        return size;
    }
    
    /**
     * Accesses the MyArrayList and retrieves the object at that index. 
     * 
     * @param index the index of the object that will be returned. 
     * 
     * @return the object at the given index. 
     * 
     * @precondition 0<=index<size
     * @postcondition returns the object at the given index. 
     */
    
    public E get(int index)
    {
        
        if(index <0 || index>=size)
        {
            throw new IndexOutOfBoundsException();
        }
        else
        {
        
            return (E)values[index];
        }
    }

    /** 
     * Replaces an element at a specified position with a specified object and returns the original object. 
     * 
     * @param index the index at which the element will be replaced. 
     * 
     * @param obj the object which will replace the current object at the specified index. 
     * 
     * @return the original object at the given index. 
     *
     *@precondition 0<=index<size
    * @postcondition replaces the element at position index with obj
    *               returns the element formerly at the specified position
    */
    public E set(int index, E obj)
    {
        if(index<0 || index>= size)
        {
            throw new IndexOutOfBoundsException();
        }


        E temp = (E)values[index];//temporary variable to store return value.
        values[index] = obj;
        return temp;
    }

    /**
     * Adds a new object to the end of the MyArrayList. 
     * 
     * @param obj the object of type E which will be added to the end of the list. 
     * 
     * @return true(always)
     * 
    * @postcondition appends obj to end of list; returns true
    */
    public boolean add(E obj)
    {
        if(values.length==size)
        {
            this.doubleCapacity();
        }
        size++;
        values[size-1] = obj;
        return true;
    }

    /**
     * removes an element from the MyArrayList from a given index. 
     * 
     * @param index the index at which the element will be removed. 
     * 
     * @return returns the removed element. 
     * 
     * @precondition 0<=index<size
    * @postcondition removes element from position index, moving elements
    *               at position index + 1 and higher to the left
    *               (subtracts 1 from their indices) and adjusts size
    *               returns the element formerly at the specified position
    */
    public E remove(int index)
    {
        if(index<0 || index>=size)
        {
            throw new IndexOutOfBoundsException();
        }
        E temp = (E)values[index];
        for(int i = index+1; i<size; i++)
        {
            values[i-1] = values[i];
        }
        size--;
        return temp;
    }

    /**
     * Allows access for the Iterator for MyArrayList. 
     * 
     * @return returns a copy of the reference for the MyArrayListIterator. 
     */
    public Iterator<E> iterator()
    {
        return new MyArrayListIterator();
    }
    
    /**
     * Allows access for the ListIterator for MyArrayList. 
     * 
     * @return returns a copy of the reference for the MyArrayListListIterator. 
     */
    public ListIterator<E> listIterator()
    {
        return new MyArrayListListIterator();
    }

    /**
     * adds an element between the element at a given index and the next index after it. 
     * 
     * @param index the index after which the object will be added. 
     * 
     * @param obj the object to be added. 
     * 
    * @precondition  0 <= index <= size
    * @postcondition inserts obj at position index,
    *               moving elements at position index and higher
    *               to the right (adds 1 to their indices) and adjusts size
    */
    public void add(int index, E obj)
    {
        
        if(index<0 || index>=size)
        {
            throw new IndexOutOfBoundsException();
        }
        E temp = (E)values[size-1];
        for(int i = index+1; i<size; i++)
        {
            values[i] = values[i-1];
        }
        if(values.length == size)
        {
            this.doubleCapacity();
            values[size] = temp;
        }
        else
        {
            values[size] = temp;
        }
    }
    
    
    /**
     * The MyArrayListIterator class will iterate through a MyArrayList and be able to remove and get the elements inside of it. 
     * 
     * @author Luke Zeng
     * 
     * @version 10/13/2021
     */
    private class MyArrayListIterator implements Iterator<E>
    {
        //the index of the value that will be returned by next()
        private int nextIndex;
        //the size of the MyArrayList when the iterator was created.
        private int currentSize;
        
        /**
         * creates a new MyArrayListIterator object and sets nextIndex to null and sets current size to the current list size. 
         */
        public MyArrayListIterator()
        {
            nextIndex = 0;
            currentSize = MyArrayList.this.size();
        }
        
        /**
         * Checks if there is a next value in the MyArrayList. 
         * 
         * @return true if there is, false if not. 
         */
        public boolean hasNext()
        {
            if(nextIndex < MyArrayList.this.size)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        /**
         * Gives the next value in the MyArrayList then increments. 
         * 
         * @return returns the next value in the MyArrayList. 
         * 
         * @precondition the list has not been modified since the Iterator's creation and has a valid next value. 
         * @postcondition the nextIndex variable has been incremented by 1, the method returns the element at the index of the previous index of nextIndex. 
         */
        public E next()
        {
            Object[] array = MyArrayList.this.values;//a copy of the values array.
            if(nextIndex == MyArrayList.this.size())
            {
                throw new NoSuchElementException("finally I figured it out");
            }
            if(currentSize != MyArrayList.this.size)
            {
                throw new ConcurrentModificationException("no modificastion allowed here");
            }
            E temp = (E)(array[nextIndex]);
            nextIndex++;
            return temp;

        }

        /**
         * removes the last returned object by the next() method. 
         * 
         * @precondition the next() method has been called at least once. 
         * @postcondition the last returnedf object by the next() method has been removed from the MyArrayList. 
         */
        public void remove()
        {
            if(nextIndex == 0)
            {
                throw new IllegalStateException("you will now be incarcerated for 0 days");
            }
            nextIndex--;
            currentSize--;
            MyArrayList.this.remove(nextIndex);

        }
    }
    
    /**
     * The MyArrayListListIterator class is a sub-class of MyArrayListIterator and adds additional functionality such as iterating backwards, adding elements, and setting elements.
     * 
     * @author Luke Zeng, some documentation and skeleton by Anu Datar. 
     * 
     * @version 10/13/2021
     */
    private class MyArrayListListIterator extends MyArrayListIterator implements ListIterator<E>
    {
        // note the extends MyArrayListIterator 
        // Remember this class thus inherits the methods from the parent class.
        private int nextIndex;
        private int previousIndex;// Index of element that will be returned by the next call of next()
        private boolean forward; //direction of traversal

        /**
         * Constructs a new MyArrayListListIterator
         */
        public MyArrayListListIterator()
        {
            nextIndex = 0;
            previousIndex = -1;
            forward = true;
        }
        
        /**
         * Checks if there is a next value in the MyArrayList. 
         * 
         * @return true if there is, false if not. 
         */
        public boolean hasNext()
        {
            if(nextIndex < MyArrayList.this.size)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        /**
         * Returns next element and moves pointer forward
         * @return next Object in MyArrayList
         */
        public E next()
        {
            forward = true;
            if(!this.hasNext())
            {
                throw new NoSuchElementException("no element here!");
            }
            nextIndex++;
            previousIndex++;
            return MyArrayList.this.get(previousIndex);
        }

        /**
         * Adds element before element that would be returned by next
         * @param element to add to the end of the list. 
         */
        public void add(E obj)
        {
            MyArrayList.this.add(previousIndex, obj);
            nextIndex++;
            previousIndex++;
        }

        /**
         * Determines whether there is another element in MyArrayList
         * while traversing in the backward direction
         * @return true if there is previous element, false otherwise
         * @postcondition returns true if there is a previous element, false if otherwise. 
         */
        public boolean hasPrevious()
        {
            if(previousIndex<=0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /**
         * returns the object in previous to the cursor. 
         * 
         * @return the previous object to the cursor. 
         * 
         * @precondition 0<nextIndex
         * @postcondition increments the previous and next indexes down by 1, returns the previous object to the cursor, sets forward to false.  
         */
        public E previous()
        {
            forward = false;
            if(!this.hasPrevious())
            {
                throw new NoSuchElementException("no element here!");
            }
            nextIndex--;
            previousIndex--;
            return MyArrayList.this.get(previousIndex);
        }

        /**
         * Returns index of the next element 
         * @return index of element that would be 
         *         returned by a call to next()
         */
        public int nextIndex()
        {
            return nextIndex;
        }

        /**
         * Returns the index of the previous element
         * @return index of element that would be
         *         returned by a call to previous()
         */
        public int previousIndex()
        {
            return previousIndex;
        }

        /**
         * Removes element that was returned by next() or previous()
         * 
         * @precondition previous() or next() must have been called at least once. 
         * @postcondition the element that was last returned by next() or previous() is now removed. 
         */
        public void remove()
        {
            if(previousIndex == -1)
            {
                throw new IllegalStateException("that's illegal!");
            }
            else if(forward)
            {
                MyArrayList.this.remove(previousIndex);
                nextIndex--;
                previousIndex--;
            }
            else
            {
                MyArrayList.this.remove(nextIndex);
            }
        }

        /**
         * replaces the last object returned by next() or previous() with a given object. 
         * 
         * @param obj the object reference which will replace the old one. 
         * 
         * @precondition next() or previous() have been called at least once. 
         * @postcondition sets the last index returned by next() or previous() in MyArrayList to the given object. 
         */
        public void set(E obj)
        {
            if(previousIndex == -1)
            {
                throw new IllegalStateException("this is illegal in many states");
            }
            else if(forward)
            {
                MyArrayList.this.set(previousIndex, obj);
            }
            else
            {
                MyArrayList.this.set(nextIndex, obj);
            }
        }
    }
    
}