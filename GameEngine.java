
public class GameEngine {
	private Parser parser;
    private Room currentRoom;
    private UserInterface gui;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        parser = new Parser();
        createRooms();
    }

    public void setGUI(UserInterface userInterface)
    {
        gui = userInterface;
        printWelcome();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gui.print("\n");
        gui.println("Welcome to the Hardes Game!");
        gui.println("Hardes is a new, incredibly boring adventure game.");
        gui.println("Type 'help' if you need help.");
        gui.print("\n");
        gui.println(currentRoom.getLongDescription());
        gui.showImage(currentRoom.getImageName());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room piece1, piece2, piece3, piece4, piece5, piece6, piece7;

        // create the rooms
        piece1 = new Room("Dongeon Cell", "dongeonCell.jpg");
        piece2 = new Room("Couslang Tour", "cell.jpg");
        piece3 = new Room("Ostagar", "piece4.jpg");
        piece4 = new Room("Royal room", "shot0025.jpg");
        piece5 = new Room("The Ishal Tower", "pont.jfif");
        piece6 = new Room("the computing admin office", "royal.jfif");
        piece7 = new Room("the computing admin office", "cell2.gif");
        
        piece1.setExits("east", piece2);
        piece1.setExits("south", piece3);
        piece1.setExits("north", piece4);

        piece2.setExits("west", piece1);
        piece2.setExits("east", piece3);

        piece3.setExits("north", piece1);
        piece3.setExits("west", piece2);

        piece4.setExits("south", piece1);
        piece4.setExits("north", piece7);
        piece4.setExits("west", piece5);
        piece4.setExits("east", piece6);

        piece5.setExits("east", piece4);

        piece6.setExits("west", piece4);

        piece7.setExits("south", piece4);


        currentRoom = piece1;
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand(String commandLine) 
    {
        gui.println(commandLine);
        Command command = parser.getCommand(commandLine);

        if(command.isUnknown()) {
            gui.println("I don't know what you mean...");
            return;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit")) {
            if(command.hasSecondWord())
                gui.println("Quit what?");
            else
                endGame();
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        gui.println("You are lost. You are alone. You wander");
        gui.println("around at Monash Uni, Peninsula Campus." + "\n");
        gui.print("Your command words are: " + parser.showCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
            gui.println("There is no door!");
        else {
            currentRoom = nextRoom;
            gui.println(currentRoom.getLongDescription());
            if(currentRoom.getImageName() != null)
                gui.showImage(currentRoom.getImageName());
        }
    }

    private void endGame()
    {
        gui.println("Thank you for playing.  Good bye.");
        gui.enable(false);
    }
}
