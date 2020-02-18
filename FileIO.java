import java.io.*;
import java.util.*;
   
public class FileIO
{

    /*************************************************************************
    Purpose: read a file and import into a network
    **************************************************************************/ 
    public static void readNetwork(String fileName, Network net)
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;


        int lineNum;
        String line;

        try
        {
            fileStrm = new FileInputStream(fileName);
            rdr = new InputStreamReader(fileStrm);
            bufRdr = new BufferedReader(rdr);

            lineNum = 0;
            line = bufRdr.readLine();
            
            while (line != null)
            {
                lineNum++;
                
                processLine(net, line);
                System.out.println(line);
                line = bufRdr.readLine();
            }
            
            System.out.println();
            fileStrm.close();
        }
        catch (IOException e)
        {
            if (fileStrm != null)
            {
                try
                {
                    fileStrm.close();
                }
                catch (IOException ex2)
                {
                }
            }
            System.out.println("Error in file processing: " + e.getMessage());
            throw new IllegalArgumentException();
        }

        //return net;
    
    }

    /*************************************************************************
    Purpose: process each like split on :
    **************************************************************************/ 
    public static void processLine(Network net, String line)
    {
		String[] tokens = line.split(":");

        try
        {
            if(tokens.length == 1) //if Just a name - create a node
            {
                net.addPerson(tokens[0]);
            }
            else if(tokens.length == 2 && tokens[0].equals("A"))
            {
                net.addPerson(tokens[1]);
            }
            else if(tokens.length == 2 && tokens[0].equals("R"))
            {
                net.removePerson(tokens[1]);
            }
            else if(tokens.length == 2)// if name: name - create an edge
            {
                //System.out.println("test");
                if(!net.hasPerson(tokens[0]))
                {
                    net.addPerson(tokens[0]);
                }
                if(!net.hasPerson(tokens[0]))
                {
                    net.addPerson(tokens[1]);
                }

                net.follow(tokens[1], tokens[0], "1");
            }
            else if(tokens.length == 3 && tokens[0].equals("F"))
            {
                if(!net.hasPerson(tokens[0]))
                {
                    net.addPerson(tokens[0]);
                }
                if(!net.hasPerson(tokens[0]))
                {
                    net.addPerson(tokens[1]);
                }

                net.follow(tokens[2], tokens[1], "1");
            }

            else if(tokens.length == 3 && tokens[0].equals("U"))
            {
                net.unfollow(tokens[2], tokens[1]);
            }
            else if (tokens[0].equals("P"))
            {   
                if(tokens.length == 3)
                {
                    net.addPost(tokens[1], tokens[2]);
                }
                else if(tokens.length == 4)
                {
                    net.addPost(tokens[1], tokens[2]);
                    net.getPost(tokens[2]).setClickbait(Integer.parseInt(tokens[3]));
                }
            }
            
        }
        catch (IllegalStateException e)
        {
            throw new IllegalStateException("invalid format");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /*************************************************************************
    Purpose: output to a file and import into a network
    **************************************************************************/ 
    public static void fileOutput(Network net, String file)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        String out;

        try
        {
            fileStrm = new FileOutputStream(file);
            pw = new PrintWriter(fileStrm);

            if(file.equals("output.txt"))
            {
                out = net.toString();
            }
            else
            {
                out = net.simulate(10);
            }
            pw.println(out);

            pw.close();
        }
        catch (IOException e)
        {
            if (fileStrm != null)
            {
                try
                {
                    fileStrm.close();
                }
                    catch (IOException ex2)
                    {

                    }

            }
        }

    }

}
