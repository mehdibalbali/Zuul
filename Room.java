/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

import java.util.HashMap;
import java.util.Set;

public class Room
{
    public String description;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<String, Room>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direction, Room neighbor)
    {
        this.exits.put(direction, neighbor);
    }
    
    /**
     * Return the room that is reached if we go from this
     * room in direction "direction". If there is no room in
     * that direction, return null.
     */
    public Room getExit(String direction) {
        return this.exits.get(direction);
    }


    /**
     * Return a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString(){
        String returnString = "";
        Set<String> keys = this.exits.keySet();
        for (String exit : keys)
            returnString += " " + exit;
        return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * Return a long description of this room, of the form:
     * Room description.
     * Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        return this.description + ".\n" + this.getExitString();
    }


}
