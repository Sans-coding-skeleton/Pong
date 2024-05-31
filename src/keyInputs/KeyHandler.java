package keyInputs;

import gameGraphics.GamePanel;
import gameGraphics.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface and handles keyboard input for the game.
 * It updates the game state and player actions based on key events.
 */
public class KeyHandler implements KeyListener {
    private final GamePanel gp;
    private boolean leftPlayerUpPressed, leftPlayerDownPressed;
    private boolean rightPlayerUpPressed, rightPlayerDownPressed;
    private boolean checkDrawTime = false;

    /**
     * Constructs a KeyHandler with a reference to the GamePanel.
     *
     * @param gp the GamePanel instance
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Not used but required by the KeyListener interface.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    /**
     * Handles key press events and updates the game state and player actions accordingly.
     *
     * @param e the KeyEvent generated by the key press
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.getGameState().equals(GameState.TITLE_STATE)) {
            titleState(code);
        } else if (gp.getGameState().equals(GameState.SETTINGS_STATE)) {
            settingsState(code);
        } else if (gp.getGameState().equals(GameState.CONTROLS_STATE)) {
            controlsState(code);
        } else if (gp.getGameState().equals(GameState.CONFIRM_EXIT_STATE)) {
            confirmExitState(code);
        } else if (gp.getGameState().equals(GameState.PVP_PLAY_STATE) || gp.getGameState().equals(GameState.PVC_PLAY_STATE)) {
            playState(code);
        } else if (gp.getGameState().equals(GameState.PAUSE_STATE)) {
            pauseState(code);
        } else if (gp.getGameState().equals(GameState.MENU_STATE)) {
            menuState(code);
        }
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    /**
     * Handles key press events in the title state.
     *
     * @param code the key code of the pressed key
     */
    public void titleState(int code) {
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                gp.removeCommandNum();
                if (gp.getCommandNum() < 0) {
                    gp.setCommandNum(3);
                }
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                gp.addCommandNum();
                if (gp.getCommandNum() > 3) {
                    gp.setCommandNum(0);
                }
            }
            case KeyEvent.VK_ENTER -> {
                if (gp.getCommandNum() == 0) {
                    gp.playSE(1);
                    gp.setGameState(GameState.PVP_PLAY_STATE);
                }
                if (gp.getCommandNum() == 1) {
                    gp.playSE(1);
                    gp.setGameState(GameState.PVC_PLAY_STATE);
                }
                if (gp.getCommandNum() == 2) {
                    gp.setGameState(GameState.SETTINGS_STATE);
                }
                if (gp.getCommandNum() == 3) {
                    gp.playSE(2);
                    gp.setGameState(GameState.CONFIRM_EXIT_STATE);
                    gp.setCommandNum(2);
                }
            }
            case KeyEvent.VK_ESCAPE -> {
                gp.playSE(2);
                gp.setGameState(GameState.CONFIRM_EXIT_STATE);
                gp.setCommandNum(2);
            }
        }
    }

    /**
     * Handles key press events in the settings state.
     *
     * @param code the key code of the pressed key
     */
    public void settingsState(int code) {
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                gp.removeCommandNum();
                if (gp.getCommandNum() < 0) {
                    gp.setCommandNum(3);
                }
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                gp.addCommandNum();
                if (gp.getCommandNum() > 3) {
                    gp.setCommandNum(0);
                }
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                if (gp.getCommandNum() == 1) {
                    gp.removeVolume();
                }
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if (gp.getCommandNum() == 1) {
                    gp.addVolume();
                }
            }
            case KeyEvent.VK_ENTER -> {
                if (gp.getCommandNum() == 0) {
                    gp.switchFullScreen();
                }
                if (gp.getCommandNum() == 2) {
                    gp.setGameState(GameState.CONTROLS_STATE);
                    gp.setCommandNum(6);
                }
                if (gp.getCommandNum() == 3) {
                    gp.setGameState(GameState.TITLE_STATE);
                }
            }
            case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.TITLE_STATE);
        }
    }

    /**
     * Handles key press events in the controls state.
     *
     * @param code the key code of the pressed key
     */
    public void controlsState(int code) {
        switch (code) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_ENTER -> gp.setGameState(GameState.SETTINGS_STATE);
        }
    }

    /**
     * Handles key press events in the confirm exit state.
     *
     * @param code the key code of the pressed key
     */
    public void confirmExitState(int code) {
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                gp.removeCommandNum();
                if (gp.getCommandNum() < 1) {
                    gp.setCommandNum(2);
                }
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                gp.addCommandNum();
                if (gp.getCommandNum() > 2) {
                    gp.setCommandNum(1);
                }
            }
            case KeyEvent.VK_ENTER -> {
                if (gp.getCommandNum() == 1) {
                    System.exit(0);
                }
                if (gp.getCommandNum() == 2) {
                    gp.setGameState(GameState.TITLE_STATE);
                }
            }
            case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.TITLE_STATE);
        }
    }

    /**
     * Handles key press events in the play state.
     *
     * @param code the key code of the pressed key
     */
    public void playState(int code) {
        switch (code) {
            case KeyEvent.VK_W -> leftPlayerUpPressed = true;
            case KeyEvent.VK_S -> leftPlayerDownPressed = true;
            case KeyEvent.VK_UP -> rightPlayerUpPressed = true;
            case KeyEvent.VK_DOWN -> rightPlayerDownPressed = true;
            case KeyEvent.VK_P -> gp.setGameState(GameState.PAUSE_STATE);
            case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.MENU_STATE);
        }
    }

    /**
     * Handles key press events in the pause state.
     *
     * @param code the key code of the pressed key
     */
    public void pauseState(int code) {
        switch (code) {
            case KeyEvent.VK_P -> gp.setGameState(GameState.PVP_PLAY_STATE);
            case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.MENU_STATE);
        }
    }

    /**
     * Handles key press events in the menu state.
     *
     * @param code the key code of the pressed key
     */
    public void menuState(int code) {
        switch (code) {
            case KeyEvent.VK_P -> gp.setGameState(GameState.PAUSE_STATE);
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                gp.removeCommandNum();
                if (gp.getCommandNum() < 0) {
                    gp.setCommandNum(1);
                }
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                gp.addCommandNum();
                if (gp.getCommandNum() > 1) {
                    gp.setCommandNum(0);
                }
            }
            case KeyEvent.VK_ENTER -> {
                if (gp.getCommandNum() == 0) {
                    gp.setGameState(GameState.PVP_PLAY_STATE);
                }
                if (gp.getCommandNum() == 1) {
                    gp.playSE(2);
                    gp.setGameState(GameState.TITLE_STATE);
                }
            }
            case KeyEvent.VK_ESCAPE -> gp.setGameState(GameState.PVP_PLAY_STATE);
        }
    }

    /**
     * Handles key release events and updates player actions accordingly.
     *
     * @param e the KeyEvent generated by the key release
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (gp.getGameState().equals(GameState.PVP_PLAY_STATE) || gp.getGameState().equals(GameState.PVC_PLAY_STATE)) {
            int code = e.getKeyCode();
            switch (code) {
                case KeyEvent.VK_W -> leftPlayerUpPressed = false;
                case KeyEvent.VK_S -> leftPlayerDownPressed = false;
                case KeyEvent.VK_UP -> rightPlayerUpPressed = false;
                case KeyEvent.VK_DOWN -> rightPlayerDownPressed = false;
            }
        }
    }

    /**
     * Checks if the left player's up key is pressed.
     *
     * @return true if the left player's up key is pressed, false otherwise
     */
    public boolean isLeftPlayerUpPressed() {
        return leftPlayerUpPressed;
    }

    /**
     * Checks if the left player's down key is pressed.
     *
     * @return true if the left player's down key is pressed, false otherwise
     */
    public boolean isLeftPlayerDownPressed() {
        return leftPlayerDownPressed;
    }

    /**
     * Checks if the right player's up key is pressed.
     *
     * @return true if the right player's up key is pressed, false otherwise
     */
    public boolean isRightPlayerUpPressed() {
        return rightPlayerUpPressed;
    }

    /**
     * Checks if the right player's down key is pressed.
     *
     * @return true if the right player's down key is pressed, false otherwise
     */
    public boolean isRightPlayerDownPressed() {
        return rightPlayerDownPressed;
    }

    /**
     * Checks if the draw time display is enabled.
     *
     * @return true if the draw time display is enabled, false otherwise
     */
    public boolean isCheckDrawTime() {
        return checkDrawTime;
    }
}
