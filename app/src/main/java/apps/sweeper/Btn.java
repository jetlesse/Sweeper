package apps.sweeper;

import android.widget.TextView;
import android.widget.TextClock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Jordan on 2017-07-20.
 */

public class Btn {

    //these values can be reset whenever the settings are changed.
    protected static int xSquares = getXSquares();
    protected static int ySquares = getYSquares();

    static ArrayList<Btn> minePlacements = new ArrayList<>(boardSize());
    static ArrayList<Boolean> visited = new ArrayList<>(boardSize());
    static ArrayList<Btn> currPath = new ArrayList<>(boardSize());
    static ArrayList<Integer> buttons = new ArrayList<>(boardSize());

    static int gameState = 0;
    static TextView t;

    public Btn(int a) {
        numMines = 0;
        state = 0;
        isMine = false;
        place = a;
    }

    protected int numMines;
    //which display is being used for the button.
    //0 means blank, 1 means flag, 2 means number, 3 means mine/lose.
    private int state;
    protected boolean isMine;
    protected int place;

    public int getNumMines() { return numMines; }
    public boolean getIsMine() { return isMine; }
    public int getState() { return state; }
    public static int boardSize() { return xSquares * ySquares; }


    protected void setIsMine(boolean bool) { isMine = bool; }
    protected void setState(int i) { state = i; }
    protected void setNumMines(int i) { numMines = i; }

    // TODO check the settings to find the number of columns the game board should have.
    protected static int getXSquares() {
        return 9;
    }

    // TODO check the settings to find the number of rows the game board should have.
    protected static int getYSquares() {
        return 9;
    }

    // categorize the position of the square
    // 5 1 6    top corners, top edge
    // 3 9 4    left/right edge, middle section
    // 7 2 8    bottom corners, bottom edge
    protected int searchAround() {
        if (place > 0 && place < xSquares - 1) {
            return 1;
        }
        else if (place > xSquares * (ySquares - 1) && place < boardSize() - 1) {
            return 2;
        }
        else if (place % xSquares == 0) {
            return 3;
        }
        else if (place % xSquares == xSquares - 1) {
            return 4;
        }
        else if (place == 0) {
            return 5;
        }
        else if (place == xSquares - 1) {
            return 6;
        }
        else if (place == xSquares * (ySquares - 1)) {
            return 7;
        }
        else if (place == boardSize() - 1) {
            return 8;
        }
        else {
            return 9;
        }
    }

    // get the indices of all the sqaures within the 3x3 of the current square.
    protected int[] getAdjIndex() {
        int buttonPlace = searchAround();
        if (buttonPlace == 1) {
            int[] idArr = {place - 1, place + 1, place + xSquares - 1, place + xSquares, place + xSquares + 1};
            return idArr;
        }
        else if (buttonPlace == 2) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place - xSquares - 1, place - 1, place + 1};
            return idArr;
        }
        else if (buttonPlace == 3) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place + 1, place + 9, place + 10};
            return idArr;
        }
        else if (buttonPlace == 4) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - 1, place + xSquares - 1, place + xSquares};
            return idArr;
        }
        else if (buttonPlace == 5) {
            int[] idArr = {place + 1, place + xSquares + 1, place + xSquares};
            return idArr;
        }
        else if (buttonPlace == 6) {
            int[] idArr = {place - 1, place + xSquares - 1, place + xSquares};
            return idArr;
        }
        else if (buttonPlace == 7) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place + 1};
            return idArr;
        }
        else if (buttonPlace == 8) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - 1};
            return idArr;
        }
        else if (buttonPlace == 9) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - xSquares + 1,
                           place - 1, place + 1, place + xSquares - 1, place + xSquares, place + xSquares + 1};
            return idArr;
        }
        else {
            return null;
        }
    }


    private void btnReset (int a) {
        minePlacements.get(a).numMines = 0;
        minePlacements.get(a).setIsMine(false);
        minePlacements.get(a).setState(0);
    }

    // reset the board (reset the values of all the buttons) and place the mines.
    // This should only be called when clicking to start a new game.
    // no: the index of the square the board is being reset from (the 3x3 around it may not have any mines).
    // totalMines: the number of mines to place, can be configurable in the future to be more (check settings).
    private static void setBoard (int no, int totalMines) {

        for (int i = 0 ; i < totalMines ; i++) {
            Random ranNum = new Random();
            boolean cont = true;
            while (cont) {
                int point = (int) (ranNum.nextFloat() * boardSize());
                if (point == no) { continue; }
                boolean adj = false;
                for (int index: minePlacements.get(no).getAdjIndex()) {
                    if (point == index) { adj = true; }
                }
                if (adj) { continue; }

                minePlacements.get(point).setIsMine(true);
                cont = false;
            }
        }
    }

    protected void setMineNums() {
        if(!getIsMine()) {
            int n = 0;
            for (int index: getAdjIndex()) {
                if (minePlacements.get(index).getIsMine()) {
                    n++;
                }
            }
            setNumMines(n);
        }
    }

    // set the board and states for the start of the game.
    protected void startGame() {
        setBoard(place, 10);
        gameState = 1;
        for (int i = 0 ; i < boardSize() ; i++) {
            minePlacements.get(i).setMineNums();
        }
    }
}
