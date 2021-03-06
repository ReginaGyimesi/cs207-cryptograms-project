package tests;

import main.cryptogram.LetterCryptogram;
import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

/* As a player I want to be able to undo a letter so I can play the cryptogram */
public class UserStory10 {

    private final String PLAYER_NAME = "test";
    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String SOLUTION2 = "This is another test sentence that needs to be solved";
    private final String SOLUTION3 = "This is the last sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
        sentences.add(SOLUTION2);
        sentences.add(SOLUTION3);

        game = new Game(player, sentences, false);
    }

    /*
    Scenario – new cryptogram played
       - Given the player has opened the program
       - When the requests a new cryptogram
       - Then the number of cryptograms played is increased by one
     */
    @Test
    public void numberOfCryptosPlayed() throws Exception {
        game = new Game(player, sentences, false);

        Assert.assertEquals(0, player.getNumCryptogramsPlayed());

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(1, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(1, player.getNumCryptogramsPlayed());

        LetterCryptogram letter2 =new LetterCryptogram(SOLUTION2);
        Assert.assertNotSame(letter2.getSolution(), SOLUTION3.toLowerCase());
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(1, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(2, player.getNumCryptogramsPlayed());

        LetterCryptogram letter3 = new LetterCryptogram(SOLUTION3);
        Assert.assertNotSame(letter3.getSolution(), SOLUTION3.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(2, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(3, player.getNumCryptogramsPlayed());

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();
    }

    /*
    Scenario – cryptogram loaded
        - Given the player has opened the program
        - When they request to load their saved game
        - Then the game is loaded and no change is made to the cryptograms played
     */

    @Test
    public void numberOfCryptosAfterLoad() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        player.incrementCryptogramsPlayed();


        in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.savegame();
        game.loadGame(PLAYER_NAME);

        Assert.assertNotNull(game.getPlayerGameMapping().get(game.getCurrentPlayer()));

        File test =new File("test.txt");
        Assert.assertTrue(test.delete());
        File players = new File("players.txt");
        Assert.assertTrue(players.delete());

    }

    @After
    public void tearDown(){

    }
}
