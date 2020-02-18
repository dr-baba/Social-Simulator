import java.util.*;

public class Post
{
    private Person owner;
    private String message;
    private int likeCount;
    private int receiveCount;
    public int clickbait;
    private DSALinkedList received; 
    private DSALinkedList likers;

    public Post()
    {
        this.owner = null;
        this.message = null;
        this.likeCount = 0;
        this.receiveCount = 0;
        clickbait = 1;
        likers = new DSALinkedList();
        received = new DSALinkedList();
    }

    public Post(Person owner, String message)
    {
        this.owner = owner;
        this.message = message;
        this.likeCount = 0;
        this.receiveCount = 0;
        clickbait = 1;
        likers = new DSALinkedList();
        received = new DSALinkedList();
    }

    public Person getOwner()
    {
        return owner;
    }

    /*************************************************************************
    Add a like to their post if not the poster
    **************************************************************************/
    public void like(Person liker)
    {
        /*Iterator iter = likers.iterator();
        Person personInList;
        boolean alreadyLiked = false;

        while(iter.hasNext())
        {
            personInList = (Person) iter.next();
            if(personInList.getName().equals(liker.getName()))
            {
                alreadyLiked = true;
            }
        }
        */

        if(liker != getOwner())
        {
            likers.insertLast(liker);
            likeCount++;
        }
    }

    public void unlike(Person person)
    {
        Iterator iter = likers.iterator();
        Person personInList;
        boolean unliked = false;

        while(iter.hasNext() && !unliked)
        {
            personInList = (Person) iter.next();
            if(personInList.getName().equals(person.getName()))
            {
                unliked = true;
            }
        }

        if(unliked)
        {
            likers.remove(person);
            likeCount--;
        }
    }

    /*************************************************************************
    Add to the received list of the post
    **************************************************************************/
    public void receive(Person person)
    {
        received.insertLast(person);
    }

    public int getLikeCount()
    {
        return likeCount;
    }

    public int getReceiveCount()
    {
        return receiveCount;
    }

    public DSALinkedList getLikers()
    {
        return likers;
    }

    public DSALinkedList getReceived()
    {
        return received;
    }

    public String getMessage()
    {
        return message;
    }

    public int getClickbait()
    {
        return clickbait;
    }
   
    public void setClickbait(int clickbait)
    {
        this.clickbait = clickbait;
    }

    public String displayPost()
    {
        String output;

        output = "Poster: " + owner.getName() + "\n" + "Message: " + getMessage();

        return output;
    }

    public String toString()
    {
        Person v;
        String c;
        String output = "";
        Iterator iter = likers.iterator();

        while(iter.hasNext())
        {
            v = (Person) iter.next();
            c = v.getName();
            output += c + " ";
        }
        return output;
    }

}