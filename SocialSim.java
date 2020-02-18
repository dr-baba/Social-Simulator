import java.lang.Math;
public class SocialSim
{
    public static void main(String [] args)
    {
        try
        {
            String mode;
            UserInterface ui = new UserInterface();

            mode = args[0];
            if(!validateArgs(mode))
            {
                System.out.println("ERROR: Invalid Commandline inputs");
            }
            else if (mode.equals("-i"))
            {
                ui = new UserInterface();
                ui.mainMenu();
            } 
            else if (mode.equals("-s"))
            {
                String netfile, eventfile;
                double prob_like, prob_foll;
                Network net = new Network();

                int fileD= (int) ((1000000.0) *(Math.random() + Math.random()));
                System.out.println(fileD);
                String file = String.valueOf(fileD) + ".txt"; 

                netfile = args[1];
                eventfile = args[2];

                if(ui.validateProb(Double.parseDouble(args[3])))
                {
                    prob_like = Double.parseDouble(args[3]);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid commandline");
                }
                if(ui.validateProb(Double.parseDouble(args[4])))
                {
                    prob_foll = Double.parseDouble(args[4]);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid commandline");
                }
                //do simulation
                try
                {
                    FileIO.readNetwork(netfile, net);
                }
                catch (IllegalArgumentException e)
                {
                }
                try
                {
                    FileIO.readNetwork(eventfile, net);                
                }
                catch (IllegalArgumentException e)
                {
                }
                
                net.setFollowProb(prob_foll);
                net.setLikeProb(prob_like);

                //net.simulate(10);

                FileIO.fileOutput(net, file);

            }
            else
            {
                throw new IllegalArgumentException("Invalid commandline");
            }
        }
        catch (IllegalArgumentException e2)
        {
            System.out.println(e2.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e3)
        {
            System.out.println("Usage Info:");
                System.out.println("\tSocialSim -i: Interactive Mode");
                System.out.println("\tSocialSim -s: Simulation Mode");
                System.out.println("\tFor Simulation Mode Use the format:");
                System.out.println("\t\tSocialSim -s netfile eventfile prob_like prob_fail");
        }
    }

    /*************************************************************************
    Validates string
    **************************************************************************/
    private static boolean validateArgs(String mode)
    {
        return mode.equals("-i") || mode.equals("-s") || mode.equals("");
    }


}