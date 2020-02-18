import java.util.*;

public class Person
{
    private String name;
    private DSALinkedList followers;
    private DSALinkedList followed;
    private DSALinkedList posts;
    private int followerCount;
    private int followedCount;
    private int postCount;

    public Person()
    {
        this.name = null;
        followers = new DSALinkedList();
        followed = new DSALinkedList();
        posts = new DSALinkedList();
        followerCount = 0;
        followedCount = 0;
        postCount = 0;
    }

    public Person(String name)
    {
        this.name = new String(name);
        followers = new DSALinkedList();
        followed = new DSALinkedList();
        posts = new DSALinkedList();
        followerCount = 0;
        followedCount = 0;
        postCount = 0;
    }

    public String getName()
    {
        return name;
    }

    public DSALinkedList getFollowers()
    {
        return followers;
    }

    public DSALinkedList getFollowed()
    {
        return followed;
    }

    /*************************************************************************
    public void Like()
    {
        this.like = true;;
    }
    */

    /*************************************************************************
    Insert to the followers list
    **************************************************************************/
    public void addFollower(Person person)
    {
        //if its not empty, then check if the vertex already exists
        if (!followers.isEmpty())
        {
            Iterator iter = followers.iterator();
            Person personInList;
            boolean exists = false;
    
            while(iter.hasNext())
            {
                personInList = (Person) iter.next();
    
                if(person.getName().equals(personInList.getName()))
                {
                    exists = true;
                }   
            }
            //add vertex if it doesnt exist
            if (!exists)
            {
                followers.insertLast(person);
                followerCount++;
            }
            else
            {
                throw new IllegalArgumentException("Already followed");
            }
        }
        //if empty just add it 
        else
        {
            followers.insertLast(person);
            followerCount++;
        }
        
    }

    /*************************************************************************
    Insert to the followed list
    **************************************************************************/
    public void addFollowed(Person person)
    {
        //if its not empty, then check if the vertex already exists
        if (!followed.isEmpty())
        {
            Iterator iter = followers.iterator();
            Person personInList;
            boolean exists = false;
    
            while(iter.hasNext())
            {
                personInList = (Person) iter.next();
    
                if(person.getName().equals(personInList.getName()))
                {
                    exists = true;
                }   
            }
            //add vertex if it doesnt exist
            if (!exists)
            {
                followed.insertLast(person);
                followedCount++;
            }
            else
            {
                throw new IllegalArgumentException("Already a follower");
            }
        }
        //if empty just add it 
        else
        {
            followed.insertLast(person);
            followedCount++;
        }
        
    }
    /*************************************************************************
    Remove from follower list
    **************************************************************************/
    public void removeFollower(Person person)
    {
        if (followers.isEmpty())
        {

            throw new IllegalArgumentException("No followers");

        }
        else
        {
            
            Iterator iter = followers.iterator();
            Person personInList;
            boolean exists = false;
    
            while(iter.hasNext() && !exists)
            {
                personInList = (Person) iter.next();
    
                if(person.getName().equals(personInList.getName()))
                {
                    exists = true;
                    followers.remove(personInList);
                    //iter.remove();
                    iter = followers.iterator();
                }   
            }
        }
        followerCount--;
    }
    /*************************************************************************
    Remove from followed list
    **************************************************************************/
    public void removeFollowed(Person person)
    {
        if (followed.isEmpty())
        {

            throw new IllegalArgumentException("Followed no one");

        }
        else
        {
            
            Iterator iter = followed.iterator();
            Person personInList;
            boolean exists = false;
    
            while(iter.hasNext() && !exists)
            {
                personInList = (Person) iter.next();
    
                if(person.getName().equals(personInList.getName()))
                {
                    exists = true;
                    //iter.remove();
                    followed.remove(personInList);
                    iter = followed.iterator();
                }   
            }
        }
        followedCount--;
    }

    /*************************************************************************
    Make a post by inserting to post
    **************************************************************************/
    public void makePost(Post post)
    {
        posts.insertLast(post);
        postCount++;

    }

    public DSALinkedList getPosts()
    {
        return posts;
    }

    public int getFollowerCount()
    {
        return followerCount;
    }

    public int getFollowedCount()
    {
        return followedCount;
    }

    public int getPostCount()
    {
        return postCount;
    }

    public String toString()
    {
        Person v;
        String c;
        String output = "";
        Iterator iter = followers.iterator();

        while(iter.hasNext())
        {
            v = (Person) iter.next();
            c = v.getName();
            if(iter.hasNext())
            {
                output += c + ", ";
            }
            else
            {
                output += c + ".";
            }
        }
        return output;
    }
}