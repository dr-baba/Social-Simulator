/*
DSAHeapEntry.java – Derick Babu
Previously submitted in Practical 7

*/

public class DSAHeapEntry
{
    private int priority;
    private Object value;

    public DSAHeapEntry(int inPriority, Object inValue)
    {
        this.priority = inPriority;
        this.value = inValue;
    }

    public void setPriority(int inPriority)
    {
        this.priority = inPriority;
    }

    public void setValue(Object inValue)
    {
        this.value = inValue;
    }

    public int getPriority()
    {
        return priority;
    }

    public Object getValue()
    {
        return value;
    }
}