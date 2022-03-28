import java.util.Iterator;
import java.util.List;

/*
* This class manages the state of the assassin game.
* It is used to keep track of those in the game and those killed,
* while giving the ability to move victims from the kill ring to the graveyard.
* */

public class AssassinManager {
    // YOUR CODE GOES HERE
    private AssassinNode killRing;
    private AssassinNode graveyard;

    // Construct an assassin game state given a list of names
    public AssassinManager(List<String> names){
        // Handle exceptions
        if(names == null || names.isEmpty())
            throw new IllegalArgumentException("List<String> names is null or empty.");

        // Initialize kill ring
        Iterator<String> iter = names.iterator();
        killRing = new AssassinNode(iter.next(), null);
        AssassinNode currNode = killRing;
        while(iter.hasNext()){
            currNode.next = new AssassinNode(iter.next(), null);
            currNode = currNode.next;
        }

    }

    // Print all players still alive
    public void printKillRing(){
        if(isGameOver()){
            System.out.println("    " + killRing.name + " won the game!");
            return;
        }

        AssassinNode currNode = killRing;
        while(currNode.next != null){
            String assassin = currNode.name;
            String victim = currNode.next.name;
            printKillMessage(assassin, victim);
            currNode = currNode.next;
        }
        String assassin = currNode.name;
        String victim = killRing.name;
        printKillMessage(assassin, victim);
    }

    // Helper method to print a single kill message
    private void printKillMessage(String assassin, String victim){
        System.out.println("    " + assassin + " is stalking " + victim);
    }

    // Print the players that have been killed
    public void printGraveyard(){
        if(graveyard == null){
            return;
        }
        AssassinNode currNode = graveyard;
        while(currNode != null){
            String killer = currNode.killer;
            String victim = currNode.name;
            System.out.println("    " + victim + " was killed by " + killer);
            currNode = currNode.next;
        }
    }

    // Check if a player is still alive
    public boolean killRingContains(String name){
        AssassinNode currNode = killRing;
        while(currNode != null){
            if(currNode.name.equalsIgnoreCase(name))
                return true;
            currNode = currNode.next;
        }
        return false;
    }

    // Check if a player has been killed
    public boolean graveyardContains(String name) {
        AssassinNode currNode = graveyard;
        while(currNode != null){
            if(currNode.name.equalsIgnoreCase(name))
                return true;
            currNode = currNode.next;
        }
        return false;
    }

    // Check if the game is over
    public boolean isGameOver(){
        return killRing.next == null;
    }

    // Check who the winner is
    public String winner(){
        return isGameOver() ? killRing.name : null;
    }

    // Try to kill a player
    public void kill(String name){
        if(isGameOver())
            throw new IllegalStateException();

        AssassinNode assassinNode = killRing;
        AssassinNode victimNode;
        while(assassinNode.next != null){
            victimNode = assassinNode.next;
            if(victimNode.name.equalsIgnoreCase(name)){
                assassinNode.next = victimNode.next;
                victimNode.killer = assassinNode.name;
                addToGraveyard(victimNode);
                return;
            }
            assassinNode = assassinNode.next;
        }
        if(killRing.name.equalsIgnoreCase(name)){
            victimNode = killRing;
            killRing = killRing.next;
            victimNode.killer = assassinNode.name;
            addToGraveyard(victimNode);
            return;
        }

        throw new IllegalArgumentException();
    }

    // Helper method to add a node to the graveyard
    private void addToGraveyard(AssassinNode node){
        node.next = graveyard;
        graveyard = node;
    }


    //////// DO NOT MODIFY AssassinNode.  You will lose points if you do. ////////
    /**
     * Each AssassinNode object represents a single node in a linked list
     * for a game of Assassin.
     */
    private static class AssassinNode {
        public final String name;  // this person's name
        public String killer;      // name of who killed this person (null if alive)
        public AssassinNode next;  // next node in the list (null if none)
        
        /**
         * Constructs a new node to store the given name and no next node.
         */
        public AssassinNode(String name) {
            this(name, null);
        }

        /**
         * Constructs a new node to store the given name and a reference
         * to the given next node.
         */
        public AssassinNode(String name, AssassinNode next) {
            this.name = name;
            this.killer = null;
            this.next = next;
        }
    }
}
