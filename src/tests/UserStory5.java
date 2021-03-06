package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class UserStory5 {
    private final String PLAYER_NAME = "test";

    private Game game;
    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp(){
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        String SOLUTION = "This is a test sentence that needs to be solved";
        sentences.add(SOLUTION);
    }

    @Test
    public void testLoadexists() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {

        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();
        game.savegame();
        game.loadGame(PLAYER_NAME);

        File test=new File("test.txt");

        Assert.assertTrue(test.delete());

        Assert.assertNotNull(game.getPlayerGameMapping().get(game.getCurrentPlayer()));


        File players = new File("players.txt");
        players.delete();

    }

    @Test(expected = NoSaveGameFound.class)
    public void testNotLoadExists() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();

        game.loadGame(PLAYER_NAME);



    }
    @Test (expected = InvalidGameCreation.class)
    public void testCorrupted() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);



        game = new Game(player, sentences,false);

        game.playGame();

        game.savegame();

        try
        {
            FileWriter playersWrite = new FileWriter("test.txt");
            playersWrite.write("test\n");
            playersWrite.write("very corrupt file\n");
            playersWrite.write("very corrupt file\n");
            playersWrite.write("very corrupt file\n");
            playersWrite.write("very corrupt file\n");
            playersWrite.close();
        }
        catch(Exception ignored)
        {

        }

        game.loadGame(PLAYER_NAME);




    }
    @After
    public void deleteTXT()
    {
        File players = new File("players.txt");
        players.delete();



        File test=new File("test.txt");
        test.delete();
    }
}
