package main.view;

import main.exceptions.*;
import main.game.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is for managing the buttons that the player see
 * at the bottom of the window.
 */
public class ButtonHolder {

    private JPanel holder;        // Holds the buttons together
    private JPanel nameHolder;
    private JLabel playerName;    // Label for the player's username
    private Game gameController;  // This creates a aggregation between ButtonsHolder and Game

    private JButton newGame;
    private JButton reset;
    private JButton saveGame;
    private JButton loadGame;
    private JButton getHint;
    private JButton showSolution;


    public ButtonHolder(String name, Game gameController) {

        this.gameController = gameController;

        initHolder();
        initButtons();
        initPlayerStatistics(name);

    }

    /**
     * This is the actual panel that holds every button.
     * Here we initialize it.
     */
    private void initHolder() {
        holder = new JPanel();
        holder.setBackground(Frame.GUNMETAL);

    }

    /**
     * This is where we initialize buttons,
     * by adding text and action listeners to them.
     */
    private void initButtons() {
        newGame = new JButton("New Game");

        // Clicking on the "New Game" button, it generates a new sentence
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameController.playGame();
                } catch (NoSentencesToGenerateFrom noSentencesToGenerateFrom) {
                    noSentencesToGenerateFrom.printStackTrace();
                } catch (NoSuchGameType noSuchGameType) {
                    noSuchGameType.printStackTrace();
                } catch (NoGameBeingPlayed noGameBeingPlayed) {
                    noGameBeingPlayed.printStackTrace();
                }
            }
        });


        // Clicking on the "Reset" button clears every input field
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resetMappings();
            }
        });

        saveGame = new JButton("Save");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.savegame();
            }
        });
        loadGame = new JButton("Load");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameController.loadGame(gameController.getCurrentPlayer().getUsername());
                } catch (NoSaveGameFound noSaveGameFound) {
                    noSaveGameFound.printStackTrace();
                } catch (InvalidPlayerCreation invalidPlayerCreation) {
                    invalidPlayerCreation.printStackTrace();
                } catch (InvalidGameCreation invalidGameCreation) {
                    invalidGameCreation.printStackTrace();
                }
            }
        });

        getHint = new JButton("Hint");
        getHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.getHint();
            }
        });

        //Clicking the "show solution" button reveals the solution to the current cryptogram in play
        showSolution = new JButton("Solution");
        showSolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gameController.showSolution();

                } catch (NoSuchGameType noSuchGameType) {
                    noSuchGameType.printStackTrace();
                }
            }
        });


        holder.add(newGame);
        holder.add(reset);
        holder.add(saveGame);
        holder.add(loadGame);
        holder.add(getHint);
        holder.add(showSolution);

    }

    /**
     * Here we create the player username display,
     * which is interactive, so the user can click on it and can check their
     * statistics.
     *
     * @param name the players username
     */
    private void initPlayerStatistics(String name) {
        nameHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));

        nameHolder.setPreferredSize(new Dimension(name.length()*10, 25));

        nameHolder.setBackground(Frame.GUNMETAL);

        nameHolder.setToolTipText("Check your statistics!");

        nameHolder.addMouseListener(createMouseListener());

        playerName = new JLabel(name);
        playerName.setForeground(Color.WHITE);
        playerName.setVerticalAlignment(JLabel.CENTER);
        playerName.setHorizontalAlignment(JLabel.CENTER);

        nameHolder.add(playerName);

        holder.add(nameHolder);
    }

    private MouseListener createMouseListener() {
        MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                CurrentPlayerStatistics statistics = new CurrentPlayerStatistics(gameController.getCurrentPlayer());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playerName.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                nameHolder.setBackground(Frame.QUEENBLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playerName.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                nameHolder.setBackground(Frame.GUNMETAL);
            }
        };

        return mouseListener;
    }

    public void enableSolutionButton(){
        showSolution.setEnabled(true);
    }

    public void disableSolutionButton(){
        showSolution.setEnabled(false);
    }

    public void enableHintButton(){
        getHint.setEnabled(true);
    }

    public void disableHintButton(){
        getHint.setEnabled(false);
    }

    public void enableSaveGameButton(){
        saveGame.setEnabled(true);
    }

    public void disableSaveGameButton(){
        saveGame.setEnabled(false);
    }

    public void enableLoadGameButton(){
        loadGame.setEnabled(true);
    }

    public void disableLoadGameButton(){
        loadGame.setEnabled(false);
    }

    public void enableResetButton(){
        reset.setEnabled(true);
    }

    public void disableResetButton(){
        reset.setEnabled(false);
    }

    public JPanel getHolder() {
        return holder;
    }

}
