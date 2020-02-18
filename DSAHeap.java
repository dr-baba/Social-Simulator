/*
DSAHeap.java – Derick Babu
Previously submitted in Practical 7

*/

import java.util.*;
import java.lang.Math;

public class DSAHeap
{
    private DSAHeapEntry[] heap;
    private int count;

    public DSAHeap(int maxSize)
    {
        heap = new DSAHeapEntry[maxSize];
    }

    public int getCount()
    {
        return count;
    }

    public void add(int priority, Object value)
    {
        heap[count] = new DSAHeapEntry(priority, value);

        trickleUp(count);

        count++;
    }

    public DSAHeapEntry remove()
    {
        DSAHeapEntry root = heap[0];

        heap[0] = heap[count - 1];
        heap[count - 1] = null;
        int curIdx = 0;

        count--;

        trickleDown(curIdx);



        //System.out.println(getCount() + "\n");

        return root;
    }

    public void display()
    {
        int rowStrt = 0;
        int rowEnd = 0;
        int row = 0;

        int totalRows = (int) (Math.log(getCount()) / Math.log(2)) + 1;
        //System.out.println(totalRows);

        while(row < totalRows)
        {
            for (int ii = rowStrt; ii <= rowEnd; ii++)
            {
                if(ii < getCount())
                {
                    System.out.print(heap[ii].getPriority() + "\t");
                }
               
            }

            rowStrt = rowEnd + 1;
            rowEnd = rowStrt + (int) Math.pow(2, row + 1) - 1;
            row++;
            System.out.println();
        }
        System.out.println();

    }

    public DSAHeapEntry[] heapSort(DSAHeapEntry[] values)
    {
        heap = new DSAHeapEntry[values.length];
        DSAHeapEntry[] result = new DSAHeapEntry[values.length];

        count = values.length;

        for (int ii = 0; ii < values.length; ii++)
        {
            heap[ii] = values[ii];
        }

        //System.out.println("asdasd");

        heapify();

        for (int ii = getCount()- 1; ii >= 1; ii--)
        {
            swap(0, ii);
            trickleDown(ii);
        }

       //System.out.println("test");

        for (int ii = heap.length - 1; ii >= 0; ii--)
        {
            result[ii] = remove();
            //display();
            //System.out.println(result[ii].getPriority());
        }

        return result;
    }

    private void heapify()
    {
        //System.out.println("count " + getCount());
        
        for (int ii = (getCount()/2) - 1; ii > 0; ii--)
        {
            trickleDown(ii);
        }
    }

    private void trickleUp(int curIdx)
    {
        int parentIdx = (curIdx - 1)/2;
        DSAHeapEntry temp;

        if (curIdx > 0)
        {
            if (heap[curIdx].getPriority() > heap[parentIdx].getPriority())
            {
                temp = heap[parentIdx];
                heap[parentIdx] = heap[curIdx];
                heap[curIdx] = temp;
                trickleUp(parentIdx);
            }
        }
    }

    private void trickleDown(int curIdx)
    {
        int lChildIdx = curIdx * 2 + 1;
        int rChildIdx = curIdx * 2 + 2;
        int largeIdx;

        //System.out.println("index " + curIdx);

        if (lChildIdx < getCount())
        {
            largeIdx = lChildIdx;
            if (rChildIdx < getCount())
            {
                if(heap[lChildIdx].getPriority() < heap[rChildIdx].getPriority())
                {
                    largeIdx = rChildIdx;
                }
                
            }
            if (heap[largeIdx].getPriority() > heap[curIdx].getPriority())
            {
                swap(largeIdx, curIdx);
                trickleDown(largeIdx);
            }
        }
    }

    private void swap(int firstIdx, int secIdx)
    {
        DSAHeapEntry firstVal = heap[firstIdx];
        DSAHeapEntry secVal = heap[secIdx];
        DSAHeapEntry temp;

        temp = firstVal;
        heap[firstIdx] = secVal;
        heap[secIdx] = temp; 
    }
}
