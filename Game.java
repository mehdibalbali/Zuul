/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {   Command c = new Command("go", "east");
        createRooms();
        goRoom(c);
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room piece1, piece2, piece3, piece4, piece5, piece6, piece7;
      
        // create the rooms
        piece1 = new Room("piéce 1");
        piece2 = new Room("piéce2");
        piece3 = new Room("piéce3");
        piece4 = new Room("piéce 4");
        piece5 = new Room("pièce 5");
        piece6 = new Room("pièce 6");
        piece7 = new Room("pièce 7");
        
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


        currentRoom = piece1;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    public void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

        printLocationInfo();

        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("quit"))
            wantToQuit = quit(command);
        else if(commandWord.equals("look"))
            look();
        else if (commandWord.equals("eat"))
            System.out.println("you are eating something");

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
            System.out.println("You are " + currentRoom.getLongDescription());
        }
    }

    private void printLocationInfo(){
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: "+ currentRoom.getExitString());


    }
    /**
     * "look" was entered.
     * Look the entire room. (now, this function is useless)
     */
    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
