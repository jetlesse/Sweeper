package apps.sweeper;

import android.widget.Button;
import android.widget.TextView;
import android.graphics.Color;
import android.content.SharedPreferences;

import java.util.Locale;
import java.util.Random;
import java.util.HashMap;

/**
 * Created by Jordan on 2017-07-20.
 * Original version created by Jordan on 2015-12-06.
 * This version was made to improve efficiency, readability and extendability.
 */

public class Btn {

    //these values can be reset whenever the settings are changed.
    protected static int xSquares = getXSquares();
    protected static int ySquares = getYSquares();

    static HashMap<Integer, Btn> minePlacements = new HashMap<>(boardSize(), 1.0f);

    static HashMap<Integer, Button> buttonNums = new HashMap<>(boardSize(), 1.0f);

    static int gameState = 0;
    protected static TextView tv;
    public static SharedPreferences sp;

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

    public static Timer timer;

    public int getNumMines() { return numMines; }
    public boolean getIsMine() { return isMine; }
    public int getState() { return state; }
    public static int boardSize() { return xSquares * ySquares; }

    public static void emptyObjects() {
        minePlacements = new HashMap<>(boardSize(), 1.0f);
        buttonNums = new HashMap<>(boardSize(), 1.0f);
        timer = null;
        tv = null;
        sp = null;
    }


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

    // categorize the position of the current square
    // 1 5 2    top corners, top edge
    // 7 9 8    left/right edge, middle section
    // 3 6 4    bottom corners, bottom edge
    protected int searchAround() {
        if (place == 0) {
            return 1;
        } else if (place == xSquares - 1) {
            return 2;
        } else if (place == xSquares * (ySquares - 1)) {
            return 3;
        } else if (place == boardSize() - 1) {
            return 4;
        } else if (place > 0 && place < xSquares - 1) {
            return 5;
        } else if (place > xSquares * (ySquares - 1) && place < boardSize() - 1) {
            return 6;
        } else if (place % xSquares == 0) {
            return 7;
        } else if (place % xSquares == xSquares - 1) {
            return 8;
        } else {
            return 9;
        }
    }

    // get the indices of all the sqaures within the 3x3 of the current square.
    protected int[] getAdjIndex() {
        int buttonPlace = searchAround();
        if (buttonPlace == 1) {
            int[] idArr = {place + 1, place + xSquares + 1, place + xSquares};
            return idArr;
        } else if (buttonPlace == 2) {
            int[] idArr = {place - 1, place + xSquares - 1, place + xSquares};
            return idArr;
        } else if (buttonPlace == 3) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place + 1};
            return idArr;
        } else if (buttonPlace == 4) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - 1};
            return idArr;
        } else if (buttonPlace == 5) {
            int[] idArr = {place - 1, place + 1, place + xSquares - 1, place + xSquares, place + xSquares + 1};
            return idArr;
        } else if (buttonPlace == 6) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place - xSquares - 1, place - 1, place + 1};
            return idArr;
        } else if (buttonPlace == 7) {
            int[] idArr = {place - xSquares + 1, place - xSquares, place + 1, place + xSquares, place + xSquares + 1};
            return idArr;
        } else if (buttonPlace == 8) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - 1, place + xSquares - 1, place + xSquares};
            return idArr;
        } else if (buttonPlace == 9) {
            int[] idArr = {place - xSquares - 1, place - xSquares, place - xSquares + 1,
                           place - 1, place + 1, place + xSquares - 1, place + xSquares, place + xSquares + 1};
            return idArr;
        } else {
            return null;
        }
    }


    private void btnReset (int a) {
        Button b = buttonNums.get(a);
        Btn btn = minePlacements.get(a);
        btnReset(btn, b);
    }

    public static void btnReset (Btn btn, Button b) {
        b.setBackgroundResource (R.drawable.square);
        b.setText ("");
        btn.numMines = 0;
        btn.setIsMine(false);
        btn.setState(0);
    }

    // reset the board (reset the values of all the buttons) and place the mines.
    // this should only be called when clicking to start a new game.
    // place is used as the index of the square the board is being reset from (the 3x3 around it may not have any mines).
    // totalMines: the number of mines to place, can be configurable in the future to be more (check settings).
    private void setBoard (int totalMines) {

        for (int i = 0 ; i < totalMines ; i++) {
            Random ranNum = new Random();
            while (true) {
                int point = (int) (ranNum.nextFloat() * boardSize());
                if (point == place) { continue; }
                if (minePlacements.get(point).getIsMine()) { continue; }
                boolean adj = false;
                for (int index: minePlacements.get(place).getAdjIndex()) {
                    if (point == index) { adj = true; }
                }
                if (adj) { continue; }

                minePlacements.get(point).setIsMine(true);
                break;
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
        // TODO get the total number of mines from the settings.
        setBoard(10);
        gameState = 1;
        for (int i = 0 ; i < boardSize() ; i++) {
            minePlacements.get(i).setMineNums();
        }
        timer.startTimer();
    }

    protected void endGame() {
        int time = timer.getTheTime();

        for (int i = 0 ; i < boardSize() ; i++) {
            btnReset(i);
        }
        timer.setTheTime (0);
        timer.tView.setText("0");
        tv.setText("");
        gameState = 0;
    }

    protected boolean checkW() {
        for (int i = 0 ; i < boardSize() ; i++) {
            if (!minePlacements.get(i).getIsMine()) {
                if (minePlacements.get(i).state == 0 || minePlacements.get(i).state == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // entrypoint from activity to the Btn class.
    public void clicked() {
        if (gameState == 0) {
            startGame();
        }

        if (gameState == 1) {
            chosen();

            if (checkW()) {
                tv.setText("You Won!");
                timer.stopTimer();
                gameState = 2;
                recordWin();
            }
        }
        else if (gameState == 2) {
            endGame();
        }
    }

    // entrypoint from activity to the Btn class.
    // only changes the background of the selected button between flagged and not.
    public void held() {
        if (gameState == 1) {
            if (state == 0) {
                state = 1;
//                buttonNums.get(place).setBackgroundResource(R.drawable.flag_background);
                buttonNums.get(place).setText ("flag");
            } else if (state == 1) {
                state = 0;
                buttonNums.get(place).setBackgroundResource(R.drawable.square);
            }
        }
    }

    // more in depth processing of the interactions between between buttons and the overall game.
    public void chosen() {
        if (state == 0) {
            // set background to clicked background.
            buttonNums.get(place).setBackgroundResource(R.drawable.pushed_square);
            if (isMine) {
                buttonNums.get(place).setTextColor (Color.parseColor ("#000000"));
                buttonNums.get(place).setText("lose");
                tv.setText("You lost. Play again?");
                timer.stopTimer();
                recordGame();
                state = 3;
                gameState = 2;
                return;
            }
            state = 2;

            if (numMines == 0) {
                // DFS. mark is state being 0, i.e. being blank
                for (int index: getAdjIndex()) {
                    if (minePlacements.get(index).getState() == 0) {
                        minePlacements.get(index).chosen();
                    }
                }
                return;
            }

            switch (numMines) {
                case 1:
                    buttonNums.get(place).setTextColor(Color.parseColor("#3170B7"));
                    break;
                case 2:
                    buttonNums.get(place).setTextColor(Color.parseColor("#1E631F"));
                    break;
                case 3:
                    buttonNums.get(place).setTextColor(Color.parseColor("#C10313"));
                    break;
                case 4:
                    buttonNums.get(place).setTextColor(Color.parseColor("#03077A"));
                    break;
                case 5:
                    buttonNums.get(place).setTextColor(Color.parseColor("#820000"));
                    break;
                case 6:
                    buttonNums.get(place).setTextColor(Color.parseColor("#0EBA65"));
                    break;
                case 7:
                    buttonNums.get(place).setTextColor(Color.parseColor("#FFFFFF"));
                    break;
                case 8:
                    buttonNums.get(place).setTextColor(Color.parseColor("#000000"));
                    break;
            }
            buttonNums.get(place).setText(String.format(Locale.getDefault(), "%d", numMines));
        }
    }

    // TODO check the settings to know which values to modify.
    // get games won and fastest game won, update with new statistics.
    protected void recordWin() {
        if (sp == null) {
            System.out.println("shared preferences is not set yet.");
            return;
        }
        int won = sp.getInt("Games_Won", 0);
        int fastest = sp.getInt("Fastest_Win", 99);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("Games_Won", won + 1);
        if (timer.getTheTime() < fastest) {
            edit.putInt("Fastest_Win", timer.getTheTime());
        }
        edit.apply();
        recordGame();
    }

    protected void recordGame() {
        if (sp == null) {
            System.out.println("shared preferences is not set yet.");
            return;
        }
        int gamesPlayed = sp.getInt("Games_Played", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("Games_Played", gamesPlayed + 1);
        edit.apply();
    }
}
