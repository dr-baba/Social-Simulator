/*************************************************************************
Author: Derick Babu
Purpose: Network Class
**************************************************************************/

import java.util.*;
import java.lang.Math;

public class Network
{
    private DSALinkedList people, follows;
    private DSALinkedList allPosts;
    private int peopleCount, followCount, postsCount, steps;
    public double likeProb;
    public double followProb;
    boolean timeStop;

    /*************************************************************************
    Default Constructor
    **************************************************************************/
    public Network()
    {
        people = new DSALinkedList();
        follows = new DSALinkedList();
        allPosts = new DSALinkedList();
        peopleCount = 0;
        followCount = 0;
        steps = 0;
        likeProb = 0.4; //Default values
        followProb = 0.7; //Default values
        timeStop = false;
    }

    /*************************************************************************
    Alternate Constructor
    **************************************************************************/
    public void addPerson(String name)
    {
        Person person = new Person(name);

        //if doesnt have vertex already add the vertex
        if(!hasPerson(name))
        {
            //System.out.println("Added " + name);
            people.insertLast(person);
        }
        else
        {
            throw new IllegalArgumentException("That vertex already exists");
        }
        
        peopleCount++;
    }

    /*************************************************************************
    Purpose: Remove a person from the network
    **************************************************************************/
    public void removePerson(String name1)
    {
        Person person1, personInList;
        Iterator iter = people.iterator();
        boolean removed = false;

        //removes the person from everyone else's follower list
        removeFromFollowed(name1);

        if (hasPerson(name1))
        {
            person1 = getPerson(name1);
            
            while(iter.hasNext() && !removed)
            {
                personInList = (Person) iter.next();
                if(name1.equals(personInList.getName()))
                {
                    //iter.remove();
                    people.remove(personInList);
                    removed = true;
                    //iter = people.iterator();
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("That node does not exist");
        }

        peopleCount--;
    }

    /*************************************************************************
    Purpose: Remove a person from all the people they followed
             Private field only used by removePerson()
    **************************************************************************/
    private void removeFromFollowed(String name1)
    {
        Person personInList, personInList2;
        Iterator iter = people.iterator();

        if (hasPerson(name1))
        {
            
            Person person1 = getPerson(name1);

            while(iter.hasNext())
            {
                personInList = (Person) iter.next();
                Iterator iter2 = personInList.getFollowers().iterator();
                while(iter2.hasNext())
                {
                    personInList2 = (Person) iter2.next();
                    if(personInList2.getName().equals(name1))
                    {
                        //iter2.remove();
                        //iter = people.iterator();
                        personInList.getFollowers().remove(personInList2);
                    }
                    
                }
                    
            }
        }
        else
        {
            throw new IllegalArgumentException("That node does not exist");
        }
    }

    /*************************************************************************
    Purpose: Add person1 who wants to follow to person2's list (followed)
    **************************************************************************/
    public void follow(String follower, String followed, String edgeLabel)
    {
        Person person1, person2, personInList;
        Follow edge;
        boolean alreadyExists;
        Iterator iter;

        person2 = new Person();

        //check if the people already exist
        if(!hasPerson(follower) && !hasPerson(followed))
        {
            throw new IllegalArgumentException("Person doesn't exist");
        }
        
        //create people from labels
        person1 = getPerson(follower);
        person2 = getPerson(followed);

        //check if the edge already exists by iterating through the links of a vertex
        alreadyExists = false;
        iter = person2.getFollowers().iterator();
        while(iter.hasNext())
        {
            personInList = (Person) iter.next();
            if(person1.getName() == personInList.getName())
            {
                alreadyExists = true;
            }   
        }

        if(!alreadyExists)
        {
            person2.addFollower(person1);
            person1.addFollowed(person2);
            edge = new Follow(person2, person1, edgeLabel);
            follows.insertLast(edge);
            followCount++;
        }
    }

    /*************************************************************************
    Purpose: Remove person1 who wants to follow to person2's list (followed)
    **************************************************************************/
    public void unfollow(String follower, String followed)
    {
        Person person1, person2, personInList;
        Follow edge;
        Iterator iter;

        person1 = new Person();
        person2 = new Person();

        //check if the people already exist
        if(!hasPerson(follower) && !hasPerson(followed))
        {
            throw new IllegalArgumentException("One or more people don't exist");
        }
        
        //create people from labels
        person1 = getPerson(follower);
        person2 = getPerson(followed);


        //remove follower from followed
        person2.removeFollower(person1);
        person1.removeFollowed(person2);
        followCount--;
    }

    /*************************************************************************
    Purpose: Check to see if a person exists in the network
    **************************************************************************/
    public boolean hasPerson(String name)
    {
        Iterator iter = people.iterator();
        Person personInList;
        boolean duplicate = false;

        //get out of the loop if no duplicate
        while((iter.hasNext()) && (!duplicate))
        {
            personInList = (Person) iter.next();
            
            if(name.equals(personInList.getName()))
            {
                duplicate = true;
            }
        }

        return duplicate;
    }

    public int getPeopleCount()
    {
        return peopleCount;
    }

    public int getFollowCount()
    {
        return followCount;
    }

    public int getPostsCount()
    {
        return postsCount;
    }

    public int getSteps()
    {
        return steps;
    }

    public DSALinkedList getAllPosts()
    {
        return allPosts;
    }

    public boolean getTimeStop()
    {
        return timeStop;
    }

    /*************************************************************************
    Purpose: Get a person object from a give name
    **************************************************************************/
    public Person getPerson(String name)
    {
        Iterator iter = people.iterator();
        Person person = new Person();
        Person personInList;
        boolean exists = false;

        //to get vertex check through the people already and find it 
        while(iter.hasNext())
        {
            personInList = (Person) iter.next();
            
            if(name.equals(personInList.getName()))
            {
                person = personInList;
                exists = true;
            }
        }
        if(!exists)
        {
            throw new IllegalArgumentException("The Person doesn't exist");
        }

        return person;
    }


    public DSALinkedList getFollowers(String name)
    {
        //return the links of a vertex
        DSALinkedList followers = new DSALinkedList();
        
        Person person = getPerson(name);

        followers = person.getFollowers();

        return followers;
    }

    /*************************************************************************
    Purpose: Get a post object from a give message
    **************************************************************************/
    public Post getPost(String message)
    {
        Iterator iter = allPosts.iterator();
        Post post = new Post();
        Post postInList;
        boolean exists = false;

        //to get vertex check through the people already and find it 
        while(iter.hasNext())
        {
            postInList = (Post) iter.next();
            
            if(message.equals(postInList.getMessage()))
            {
                post = postInList;
                exists = true;
            }
        }
        if(!exists)
        {
            throw new IllegalArgumentException("The Person doesn't exist");
        } 

        return post;
    }

    public void setLikeProb(double likeProb)
    {
        this.likeProb = likeProb;
    }

    public void setFollowProb(double followProb)
    {
        this.followProb = followProb;
    }

    /*************************************************************************
    Purpose: Adds a post to the network
    **************************************************************************/
    public void addPost(String owner, String message)
    {
        Person person = getPerson(owner);
        Post post = new Post(person, message);
        
        person.makePost(post);
        allPosts.insertLast(post);
        postsCount++;
    }

    /*************************************************************************
    Purpose: simulation
    **************************************************************************/
    public String simulate(int n)
    {
        String output = "";

        for(int i = 0; i < n; i++)
        {
            
            output += timeStep() + "\n" + toString() + "\n";
        }

        return output;
    }

    /*************************************************************************
    Purpose: iterate through all posts
    a time step is the post propagating one level down the poster's follow list 
    and the follow list having a chance to like it
    **************************************************************************/
    public String timeStep()
    {
        String outp;
        Iterator iter = allPosts.iterator();
        Person personInList;
        Post postInList;
        long startTime, endTime;
        double diff = 0;
        //find the start time
        startTime = System.nanoTime();

        //iterate through each post
        while(iter.hasNext())
        {
            
            postInList = (Post) iter.next();
            //for a post get all the people who received it
            DSALinkedList received = postInList.getReceived();
            //add all the followers of the owner to the received list
            receivePost(postInList, postInList.getOwner());
        
            Iterator iter2 = received.iterator();
            boolean liked = false;
            boolean alreadylik = false;

            //iterate through people who received the post
            while(iter2.hasNext())
            {
                personInList = (Person) iter2.next();
                
                Iterator iter3 = postInList.getLikers().iterator();
                Person personInLike;
                while(iter3.hasNext())
                {
                    personInLike = (Person) iter3.next();
                    if (personInList.getName().equals(personInLike.getName()))
                    {
                        alreadylik = true;
                    }
                }

                //The person has a chance of liking the post;
                if(!alreadylik)
                {
                    liked = likePost(postInList, personInList, likeProb);

                    //if they like the post then add the followers of that person to the recieved list
                    //System.out.println("\t received " + personInList.getName());
                    if(liked)
                    {
                        //System.out.println("liked by: " + personInList.getName());
                        receivePost(postInList, personInList);
                        followPoster(postInList, personInList, followProb);
                        //personInList.Like();

                        //received.remove(personInList.getName());
                        iter2.remove();
                        

                        //iter2 = received.iterator();
                    }
                    //iter2 = received.iterator();
                }
            }
        }
        steps++;

        endTime = System.nanoTime();
        diff = (int)((double) (endTime - startTime))/1000.0;
        outp = "Time step " + steps + ": " + diff + " microseconds.\n";

        return outp;
    }

    /*************************************************************************
    Purpose: add a post to the "inbox"
    **************************************************************************/  
    public void receivePost(Post post, Person person)
    {
        DSALinkedList followersOfPerson = person.getFollowers();
        Iterator iter = followersOfPerson.iterator();
        Person personInList;

        while(iter.hasNext())
        {
            personInList = (Person) iter.next();
            post.receive(personInList);
        }
    }

    /*************************************************************************
    Purpose: add a person to the like list of a post
    **************************************************************************/  
    public boolean likePost(Post post, Person liker, double likeProb)
    {
        boolean liked = false;
        double prob;

        prob = likeProb*post.getClickbait();

        //if invalid probability make it 1.
        if(prob < 0.0 || prob > 1.0)
        {
            prob = 1.0;
        }

        //generate a random number in 0,1
        if(Math.random() < prob)
        {
            post.like(liker);
            liked = true;
        }
        
        return liked;
    }


    /*************************************************************************
    Purpose: if a person isnt a follower, there is a chance they can follow
             the original poster when they post and a direct follower likes
    **************************************************************************/  
    public void followPoster(Post post, Person liker, double followProb)
    {
        DSALinkedList followers = post.getOwner().getFollowers();
        Iterator iter = followers.iterator();
        Person personInList;
        boolean exists = false;

        if(Math.random() < followProb)
        {
            while (iter.hasNext() && !exists)
            {
                personInList = (Person) iter.next();
                if(personInList.getName().equals(liker.getName()))
                {
                    exists = true;
                }
            } 
            // if the liker isnt already a follower of the poster
            if(!exists)
            {
                follow(liker.getName(), post.getOwner().getName(), post.getMessage());
            }
        }
    }

    /*************************************************************************
    Purpose: Display people and their followers
    **************************************************************************/  
    public String displayAsList()
    {
        Person p;
        Iterator iter = people.iterator();
        String outp = "";

        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("PERSON (" + getPeopleCount() + ")          | " + "FOLLOWERS");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        while (iter.hasNext())
        {
            p = (Person) iter.next();
            System.out.println(String.format("%20s", p.getName()) + " | " + p.toString());
            outp += String.format("%20s", p.getName()) + " | " + p.toString();
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println();
        
        return outp;
    }


    /*************************************************************************
    Purpose: String of data
    **************************************************************************/  
    public String toString()
    {

        Person p;
        Post post;
        Iterator iter = people.iterator();
        String string = "";

        while (iter.hasNext())
        {
            p = (Person) iter.next();
            string = string + (p.getName() + " = " + p.toString() + "\n");
        }
        iter = people.iterator();
        while (iter.hasNext())
        {
            p = (Person) iter.next();
            Iterator iter2 = p.getPosts().iterator();
            string = string + "(F " + String.format("%02d", p.getFollowerCount()) + ") " + p.getName() + ": \n";
            while (iter2.hasNext())
            {
                post = (Post) iter2.next();
                string = string + "\t---{" + String.format("%2s", post.getLikeCount()) + " likes" + "}---\t---> "+post.getMessage()+"\n";

            }

        }

        return string;
    }


    /*************************************************************************
    Purpose: Display People and their posts
    **************************************************************************/ 
    public void displayPosts()
    {
        Person p;
        Post post = new Post();
        
        Iterator iter = allPosts.iterator();
        
        iter = people.iterator();
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("PERSON                      | POST");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        while (iter.hasNext())
        {
            p = (Person) iter.next();
            Iterator iter2 = p.getPosts().iterator();
            System.out.println("(F " + String.format("%02d", p.getFollowerCount()) + ") " + p.getName() + ": ");
            //System.out.println("\t\t ___"+ p.getPostCount() + " posts.___");
            while (iter2.hasNext())
            {
                post = (Post) iter2.next();
                System.out.println("\t---{" + String.format("%2s", post.getLikeCount()) + " likes" + "}---\t---> "+post.getMessage());

            }

        }
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println();
        
    }

    /*************************************************************************
    Purpose: Display Posts according to the amount of likes
    **************************************************************************/ 
    public void printPopularPosts()
    {
        
        Iterator iter = allPosts.iterator();
        Post postInList;
        DSAHeap heap = new DSAHeap(getPostsCount());
        //Create a heap array
        DSAHeapEntry[] array = new DSAHeapEntry[getPostsCount()];
        Post[] posts = new Post[getPostsCount()];
        int i = 0;

        //iterate through the post ad add it to the heap array as heap objects with priority on likes
        while(iter.hasNext())
        {
            postInList = (Post) iter.next();
            array[i] = new DSAHeapEntry(postInList.getLikeCount(), postInList);
            i++;
        }

        array = heap.heapSort(array);

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Likes| Post (" + getPostsCount() + ")");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        for(int j = 0; j < getPostsCount(); j++)
        {
            posts[j] = (Post) array[getPostsCount()-1-j].getValue();
            System.out.println(String.format("%5s", posts[j].getLikeCount()) + "| " + posts[j].getMessage());
        }
    }

    /*************************************************************************
    Purpose: Display People according to the amount of followers
    **************************************************************************/ 
    public void printPopularPeople()
    {
        
        Iterator iter = people.iterator();
        Person personInList;
        DSAHeap heap = new DSAHeap(getPeopleCount());
        //Create a heap array
        DSAHeapEntry[] array = new DSAHeapEntry[getPeopleCount()];
        Person[] popularPeople = new Person[getPeopleCount()];
        int i = 0;

        //iterate through the people ad add it to the heap array as heap objects with priority on likes
        while(iter.hasNext())
        {
            personInList = (Person) iter.next();
            array[i] = new DSAHeapEntry(personInList.getFollowerCount(), personInList);
            i++;
        }

        array = heap.heapSort(array);

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Followers| Person (" + getPeopleCount() + ")");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        for(int j = 0; j < getPeopleCount(); j++)
        {
            popularPeople[j] = (Person) array[getPeopleCount()-1-j].getValue();
            System.out.println(String.format("%9s", popularPeople[j].getFollowerCount()) + "| " + popularPeople[j].getName());
        }
        
        
    }

    /*************************************************************************
    Purpose: Display each person's record
    **************************************************************************/ 
    public void personRecord()
    {
        Person p;
        Iterator iter = people.iterator();

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Person              " + "| Followers " + "| Following " + "| PostCount |");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

        while (iter.hasNext())
        {
            p = (Person) iter.next();
            System.out.println(String.format("%20s", p.getName()) + "| " + String.format("%10s", p.getFollowerCount()) + "| " + String.format("%10s", p.getFollowedCount()) + "| " + String.format("%10s",p.getPostCount()) + "|");
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}