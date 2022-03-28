import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit testing with full coverage of AssassinManager
 */

//Test constructor
public class AssassinManagerTest {

    // return an assassin manager with several players in kill ring
    private static AssassinManager generatePopulatedKillRing(){
        List<String> names = new ArrayList<>();
        names.add("Bob");
        names.add("Sam");
        names.add("Kyra");
        names.add("Julia");
        AssassinManager manager = new AssassinManager(names);
        return manager;
    }

    // return an assassin manager with one person in the kill ring
    private static AssassinManager generateEndKillRing(){
        List<String> names = new ArrayList<>();
        names.add("Bob");
        AssassinManager manager = new AssassinManager(names);
        return manager;
    }

    // return a manager with names in both the kill ring and graveyard
    private static AssassinManager generateGraveyardAndKillRing(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.kill("Bob");
        manager.kill("Kyra");
        return manager;
    }

    // Constructor
    /**
     * Test case 2 provided as an example
     * Test constructor with invalid argument
     * Should throw IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void constructorNegativeTest(){
        List<String> emptyList = new ArrayList<>();
        AssassinManager manager = new AssassinManager(emptyList);
        Assert.fail("AssassinManager should throw IllegalArgumentExeption for empty list");
    }

    // construct as expected
    @Test
    public void constructorPositiveTest(){
        List<String> filledList = new ArrayList<>();
        filledList.add("Bob");
        filledList.add("Sam");
        AssassinManager manager = new AssassinManager(filledList);
    }

    // Print Kill Ring

    // when manager has several names
    @Test
    public void printKillRingPositive(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.printKillRing();
    }

    // when manager has a single name; game is over
    @Test
    public void printKillRingNegative(){
        AssassinManager manager = generateEndKillRing();
        manager.printKillRing();
    }

    // Print Graveyard

    // when manager contains players in graveyard
    @Test
    public void printGraveyardPositive(){
        AssassinManager manager = generateGraveyardAndKillRing();
        manager.printGraveyard();

    }

    // when manager contains no players in graveyard
    @Test
    public void printGraveyardNegative(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.printGraveyard();
    }

    // Kill Ring Contains

    // when player exists in kill ring
    @Test
    public void killRingContainsPositive(){
        AssassinManager manager = generatePopulatedKillRing();
        boolean containsName = manager.killRingContains("Bob");
        Assert.assertEquals(true, containsName);
    }

    // when player does not exists in kill ring
    @Test
    public void killRingContainsNegative(){
        AssassinManager manager = generatePopulatedKillRing();
        boolean containsName = manager.killRingContains("Jack");
        Assert.assertEquals(false, containsName);
    }

    // Graveyard Contains

    // when graveyard contains name
    @Test
    public void graveyardContainsPositive(){
        AssassinManager manager = generateGraveyardAndKillRing();
        boolean containsName = manager.graveyardContains("Bob");
        Assert.assertEquals(true, containsName);
    }

    /**
     * Test case 1 provided as an example
     * Test graveyardContains should not have the person who wasn't killed
     */
    @Test
    public void graveyardContainsNegativeTest(){
        List<String> list1= new ArrayList<>();
        list1.add("Grayson");
        list1.add("Ocean");
        list1.add("Chris");
        list1.add("Dr.Han");

        AssassinManager manager = new AssassinManager(list1);
        manager.kill("Grayson");
        Assert.assertFalse(manager.graveyardContains("ocean"));
    }

    // Is Game Over

    // when game should be over
    @Test
    public void isGameOverPositive(){
        AssassinManager manager = generateEndKillRing();
        boolean isOver = manager.isGameOver();
        Assert.assertEquals(true, isOver);
    }

    // when game should not be over
    @Test
    public void isGameOverNegative(){
        AssassinManager manager = generatePopulatedKillRing();
        boolean isOver = manager.isGameOver();
        Assert.assertEquals(false, isOver);
    }

    // Winner

    // when there is a winner
    @Test
    public void winnerPositive(){
        AssassinManager manager = generateEndKillRing();
        String winner = manager.winner();
        Assert.assertEquals("Bob", winner);
    }

    // when winner has not been decided yet
    @Test
    public void winnerNegative(){
        AssassinManager manager = generatePopulatedKillRing();
        String winner = manager.winner();
        Assert.assertEquals(null, winner);
    }

    // Kill

    // kill first person in list
    @Test
    public void killFirst(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.kill("Bob");
        Assert.assertEquals(false, manager.killRingContains("Bob"));
        Assert.assertEquals(true, manager.graveyardContains("Bob"));
    }

    // kill from middle of list
    @Test
    public void killMiddle(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.kill("Sam");
        Assert.assertEquals(false, manager.killRingContains("Sam"));
        Assert.assertEquals(true, manager.graveyardContains("Sam"));
    }

    // kill from end of list
    @Test
    public void killLast(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.kill("Julia");
        Assert.assertEquals(false, manager.killRingContains("Julia"));
        Assert.assertEquals(true, manager.graveyardContains("Julia"));
    }

    // try to kill player after game is over
    @Test (expected = IllegalStateException.class)
    public void killGameOver(){
        AssassinManager manager = generateEndKillRing();
        manager.kill("Bob");
    }

    // try to kill player when name does not exist
    @Test (expected = IllegalArgumentException.class)
    public void killNameNonExistent(){
        AssassinManager manager = generatePopulatedKillRing();
        manager.kill("Jack");
    }
}