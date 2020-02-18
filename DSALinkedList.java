/*
DSALinkedList.java – Derick Babu
Previously submitted in Practical 3
*/

import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable, Serializable
{
    /*************************************************************************
    Node Class
    **************************************************************************/
    private class DSAListNode
    {
        //List node class fields
        private Object value;
        private DSAListNode next;
        private DSAListNode prev;


        public DSAListNode(Object inValue)
        {
            this.value = inValue;
            this.next = null;
            this.prev = null;
        }    

        public Object getValue()
        {
            return value;
        }

        public void setValue(Object inValue)
        {
            this.value = inValue;
        }

        public DSAListNode getNext()
        {
            return next;
        }

        public DSAListNode getPrev()
        {
            return prev;
        }

        public void setNext(DSAListNode newNext)
        {
            this.next = newNext;
        }

        public void setPrev(DSAListNode newPrev)
        {
            this.prev = newPrev;
        }
    }

    /*************************************************************************
    ITERATOR
    **************************************************************************/
    private class DSALinkedListIterator implements Iterator
    {
        private DSAListNode next;
        private DSAListNode prev;
        private DSAListNode prev2;
        private boolean canRemove;

        public DSALinkedListIterator (DSALinkedList theList)
        {
            //next is first set to the head
            next = theList.head;
            prev = null;
            prev2 = null;
            canRemove = false;
        }

        public boolean hasNext()
        {
            return (next != null);
        }

        /* Algorithm based on Iterators, The Collection Hierarchy,
            Carnegie Mellon University, Page 10 */ 
        public Object next()
        {
            Object value;
            if (next == null)
            {
                value = null;
            }

                value = next.getValue();
                prev2 = prev;
                prev = next;
                next = next.getNext();
                canRemove = true;

            return value;
        }

        /* Algorithm based on Iterators, The Collection Hierarchy,
            Carnegie Mellon University, Page 10 */ 
        public void remove()
        {
            if(!canRemove)
            {
                throw new IllegalStateException("Can't remove");

            }
            if (prev2 == null)
            {
                head = next;
            }
            else
            {
                prev2.setNext(next);
                prev = prev2;
                canRemove = false;
            }
        }
    }

    /*************************************************************************
    Linked List Class
    **************************************************************************/
    //DSA Linked List classfields
    private DSAListNode head;
    private DSAListNode tail;

    public DSALinkedList()
    {
        head = null;
        tail = null;
    }    

    /*************************************************************************
    Insert First element
    **************************************************************************/
    public void insertFirst(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);

        if (isEmpty()) //empty list
        {
            head = newNd;
            tail = newNd;
        }
        else //one item or multi item list
        {
            //original head is set as the next node to new node
            head.setPrev(newNd);
            //new node is set to current head
            newNd.setNext(head);
            //new node is now head
            head = newNd;
        }
        
    }

    /*************************************************************************
    Insert last element
    **************************************************************************/
    public void insertLast(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);

        if (isEmpty()) //empty list
        {
            head = newNd;
            tail = newNd;
        }
        else //one item list or multi item
        {
            //the one after original tail is pointed to new node 
            tail.setNext(newNd);  
            //set the prev link of the new tail to the one before
            newNd.setPrev(tail);
            //the new node is now the tail
            tail = newNd;
        }
        
        
    }

    /*************************************************************************
    Check if empty
    **************************************************************************/
    public boolean isEmpty()
    {
        return ((head == null) && (tail == null));
    }

    /*************************************************************************
    Find first value
    **************************************************************************/
    public Object peekFirst()
    {
        Object nodeValue;
        nodeValue = head.getValue();

        if (isEmpty())
        {
            throw new IllegalStateException("Empty list");
        }
        else
        {
            nodeValue = head.getValue();
        }
        return nodeValue;
    }

    /*************************************************************************
    Find last value
    **************************************************************************/
    public Object peekLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalStateException("Empty list");
        }
        else
        {
           nodeValue = tail.getValue();
        }

        return nodeValue;
    }
 
    /*************************************************************************
    Remove first node
    **************************************************************************/
    public Object removeFirst()
    {
        Object nodeValue;

        if (isEmpty()) //empty list 
        {
            throw new IllegalStateException("Empty list");
        }
        else if(head.getNext() == null) // one node list 
        {
            nodeValue = head.getValue();
            head = head.getNext(); //sets to null
            tail = tail.getPrev();
        }
        else //multi node list 
        {
            nodeValue = head.getValue();
            //head is set to the new head
            head = head.getNext();
            //when removing the first set the prev pointer of the new head to null
            head.setPrev(null);
        } 

        return nodeValue;
    }

    /*************************************************************************
    Remove last element
    **************************************************************************/
    public Object removeLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalStateException("Empty list");
        }
        else if(head.getNext() == null) // one node list 
        {
            nodeValue = head.getValue();
            tail = tail.getPrev(); //sets to null
            head = head.getNext();
        }
        else
        {
            nodeValue = tail.getValue();
            //when removing the last one the second last one is set to the tail
            tail = tail.getPrev();
            //the tail now points to null
            tail.setNext(null);
        }
        return nodeValue;
    }

    /*************************************************************************
    Remove value with given string
    **************************************************************************/
    public Object remove(Object value)
    {
        DSAListNode currNode, prevNode;
        Object removed = null;

        if (isEmpty())
        {
            throw new IllegalStateException("Empty List");
        }
        else if (head.getValue().equals(value))
        {
            removeFirst();
        }
        else if (tail.getValue().equals(value))
        {
            removeLast();
        }
        else
        {
            currNode = head;
            while (currNode != null)
            {
                prevNode = currNode.getPrev();

                if(currNode.getValue().equals(value))
                {
                    removed = currNode.getValue();
                    prevNode.setNext(currNode.getNext());
                    currNode.getNext().setPrev(currNode.getPrev());
                    
                }

                currNode = currNode.getNext();
            }
        }

        return removed;
    }

    /*************************************************************************
    Iterator method
    **************************************************************************/
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }
}










