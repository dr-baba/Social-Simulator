import java.util.*;

public class UserInterface
{
    public UserInterface()
    {

	}
	
	/*************************************************************************
    Main menu of user Interface
    **************************************************************************/
    public void mainMenu()
	{
		int option = 0;
		int nodeOption = 0;
		int edgeOption = 0;
		double probLike = 0;
		double probFollow = 0;
		String name1, name2, fileName, postName;
		Person person1, person2;
		Network net = new Network();
		Post post;

		do
		{
			try
			{
				option = menuValidation(); //option must be 1 - 11

				switch(option)
				{
					case 1: //Load Network
						fileName = stringInp("FILENAME");
						FileIO.readNetwork(fileName, net);
						System.out.println("Loaded");

					break;

					case 2: //Change probs
						probLike = probMenu("LIKING");
						probFollow = probMenu("FOLLOWING");
						net.setLikeProb(probLike);
						net.setFollowProb(probFollow);

					break;

					case 3: //Node operations
						try
						{
							nodeOption = nodeOp();
							switch(nodeOption)
							{
								case 1: // Find a person
									name1 = stringInp("PERSON");
									person1 = net.getPerson(name1);
									System.out.println("Followers of " + person1.getName() + ": " + person1.toString());

								break;

								case 2: // Add a person
									name1 = stringInp("PERSON");
									net.addPerson(name1);
								break;

								case 3: // Remove a person
									name1 = stringInp("PERSON");
									net.removePerson(name1);
								break;

								case 4:
								break;

							}
						}
						catch (IllegalArgumentException e)
						{
							System.out.println(e.getMessage());
						}
						
						 
					break;

					case 4: //Edge Operations

						try
						{
							edgeOption = edgeOp();
							switch(edgeOption)
							{
								case 1: // Follow a person
									name1 = stringInp("Person who wants to follow someone");
									name2 = stringInp("Person to be followed");
									net.follow(name1, name2,(name1 + " " + name2));

								break;

								case 2: // Unfollow a person
									name1 = stringInp("Person who wants to unfollow someone");
									name2 = stringInp("Person to be unfollowed from");
									net.unfollow(name1, name2);
								break;

								case 3:
								break;

							}
						}
						catch (IllegalArgumentException e)
						{
							System.out.println(e.getMessage());
						}

					break;

					case 5: //New Post
						name1 = stringInp("Poster name");
						postName = stringInp("Message for post");

						net.addPost(name1, postName);
					break;

					case 6: //Display network
						net.displayAsList();
						
					break;

					case 7: // Display posts
						net.displayPosts();
                    break;

					case 8:// Timestep
						System.out.println(net.timeStep());
                    break;

					case 9: //Display stats
						net.printPopularPosts();
						net.printPopularPeople();
						net.personRecord();
					break;

					case 10: // Save network
                		String file = "output.txt"; 
						FileIO.fileOutput(net, file);
						System.out.println("Saved");
					break;
					
					case 11: //Exit
						System.out.println("Exiting...");
                    break;
				}

			}
			catch (IllegalArgumentException e)
			{
				System.out.println(e.getMessage());
			}
			catch (ArrayIndexOutOfBoundsException e2)
			{
				System.out.println(e2.getMessage());
			}
			catch (IllegalStateException e3)
			{
				System.out.println(e3.getMessage());
			}
			catch (OutOfMemoryError e4)
			{
				System.out.println(e4.getMessage());
			}
		} while (option != 11); 

	}


	public String menuPrompt()
	{

		String menu = "(01) Load network\n" +
		"(02) Set probabilities\n" +
		"(03) Node operations (find, insert, delete)\n" +
		"(04) Edge operations (follow - add, remove)\n" +
		"(05) New post\n"+
		"(06) Display network\n"+
		"(07) Display Posts\n"+
		"(08) Update (run a timestep)\n"+
		"(09) Display statistics\n" +
		"(10) Save network\n"+
		"(11) Exit\n";

		return menu;
	}

	/*************************************************************************
    Validate menu
    **************************************************************************/
	private int menuValidation()
	{
		Scanner sc = new Scanner(System.in);
        int min = 0;
        int max = 11;
        String prompt = "Please select an option: \n" + menuPrompt();
        String error = "ERROR value must be between " + min + " and " + max + ".";
        String outp = prompt;
        int value;

		// Looped until valid integer entered
        do
        {
            try
            {
                System.out.println(outp);
                value = sc.nextInt();
                outp = error + "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                sc.nextLine();
                value  = min - 1;
                outp = "ERROR input must be an integer." + "\n" + prompt;

            }
        } while (value <= min || value > max);

        return value;
	}
	
	public String nodePrompt()
	{

		String menu = "(1) Find a person\n" +
		"(2) Add a person\n" +
		"(3) Delete a person\n" +
		"(4) Return to menu";

		return menu;
	}

	/*************************************************************************
    Node options menu
    **************************************************************************/
	private int nodeOp()
	{
		Scanner sc = new Scanner(System.in);
        int min = 1;
        int max = 4;
        String prompt = "Please select an option: \n" + nodePrompt();
        String error = "ERROR value must be between " + min + " and " + max + ".";
        String outp = prompt;
        int value;

		// Looped until valid integer entered
        do
        {
            try
            {
                System.out.println(outp);
                value = sc.nextInt();
                outp = error + "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                sc.nextLine();
                value  = min - 1;
                outp = "ERROR input must be an integer." + "\n" + prompt;

            }
        } while (value < min || value > max);

        return value;
	}

	private String edgePrompt()
	{

		String menu = "(1) Add a follower to a person\n" +
		"(2) Remove a follower from a person\n" +
		"(3) Return to menu";

		return menu;
	}

	/*************************************************************************
    Edge options menu
    **************************************************************************/
	private int edgeOp()
	{
		Scanner sc = new Scanner(System.in);
        int min = 1;
        int max = 3;
        String prompt = "Please select an option: \n" + edgePrompt();
        String error = "ERROR value must be between " + min + " and " + max + ".";
        String outp = prompt;
        int value;

		// Looped until valid integer entered
        do
        {
            try
            {
                System.out.println(outp);
                value = sc.nextInt();
                outp = error + "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                sc.nextLine();
                value  = min - 1;
                outp = "ERROR input must be an integer." + "\n" + prompt;

            }
        } while (value < min || value > max);

        return value;
	}

	/*************************************************************************
   	Probability menu
    **************************************************************************/
	private double probMenu(String type)
	{
		Scanner sc = new Scanner(System.in);

		double prob = 0.0;
		String prompt = "Enter the probabily for " + type + ": ";
		String outp = prompt;
		String error = "Invalid probability. Try again!";

		do
        {
            try
            {
                System.out.println(outp);
                prob = sc.nextFloat();
                outp = error + "\n" + prompt;
            }
            catch (InputMismatchException e)
            {
                sc.nextLine();
                outp = "ERROR input must be a real number." + "\n" + prompt;

            }
        } while (!validateProb(prob));

        return prob;
	}

	/*************************************************************************
    validate probability
    **************************************************************************/
	public boolean validateProb(double prob)
	{
		return prob > 0.0 && prob <= 1.0;
	}


	/*************************************************************************
    String validation
    **************************************************************************/
	public String stringInp(String type)
	{
		Scanner sc = new Scanner(System.in);

		String inString = " ";
		String prompt = "Enter the " + type + ": ";
		String outp = prompt;
		String error = "Invalid Name. Try again!";

		do
		{
			try
			{
				System.out.println(outp);
				inString = sc.nextLine();
				outp = error + "\n" + prompt;
			}
			catch (NumberFormatException e)
			{
				outp = error + "\n" + prompt;
			}
		} while (validateString(inString));

		return inString;
	}

	private boolean validateString(String input)
	{
		return (input.equals(""));
	}

}