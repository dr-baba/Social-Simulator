import java.util.*;

public class Follow
{
    private String label;
    private Person fromFollower, toFollowed;
    private boolean isDirected;

    public Follow()
    {
        fromFollower = new Person();
        toFollowed = new Person();
        label = null;
        this.isDirected = true;
    }

    public Follow(Person fromFollower, Person toFollowed, String label)
    {
        this.fromFollower = fromFollower;
        this.toFollowed = toFollowed;
        this.label = label;
        this.isDirected = true;
    }

    public String getLabel()
    {
        return label;
    }

    public Person getFromFollower()
    {
        return fromFollower;
    }

    public Person getToFollowed()
    {
        return toFollowed;
    }

    public boolean isDirected()
    {
        return isDirected;
    }

    public void setFromFollower(Person fromFollower)
    {
        this.fromFollower = fromFollower;
    }

    public void setToFollowed(Person toFollowed)
    {
        this.toFollowed = toFollowed;
    }

    public String toString()
    {
        String output;

        output = fromFollower.getName() + "-" + toFollowed.getName();

        return output;
    }
}