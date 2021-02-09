import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Collections;
import java.util.ArrayList;


/**
 * This is Earth. Or at least some remote, uninhabited part of Earth. Here, Greeps can
 * land and look for piles of tomatoes...
 * 
 * @author Michael Kolling
 * @version 1.0
 */
public class Earth extends World
{
    public static final int RESOLUTION = 1;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int SCORE_DISPLAY_TIME = 240;

    // stats across multiple games
    private static boolean TRACK_MULTIPLE_SCORES = false;
    private static int totalOfAllScores = 0;
    private static int numTotalScores = 0;
    private static int overAllHighScore = 0;
    private static int overAllLowScore = Integer.MAX_VALUE;
    private static boolean STOP_BETWEEN_GAMES = true;
    private static boolean SAVE_SCORES_TO_FILE = true;

    private GreenfootImage map;
    private Ship ship;
    private int currentMap;
    private AnonymousWarning anonymousWarning;
    
    private int[][][] mapData = {
        { {480, 100, 0}, {40, 721, 532}, {12, 400, 560}, {40, 615, 400},    // map 1
          {40, 642, 192}, {16, 128, 113}, {30, 400, 40} },
 
        { {496, 709, 0}, {10, 322, 422}, {40, 700, 241}, {40, 681, 49},     // map 2
          {10, 317, 54}, {50, 90, 174}, {40, 60, 339} },
          
        { {272, 394, 0}, {10, 39, 30}, {30, 71, 476}, {50, 398, 520},       // map 3
          {40, 655, 492} },          
    };

    private int[] scores;
    
    /**
     * Create a new world. 
     */
    public Earth()
    {
        super(WIDTH / RESOLUTION, HEIGHT / RESOLUTION, RESOLUTION);
        setPaintOrder(AnonymousWarning.class);
        anonymousWarning = new AnonymousWarning();
        currentMap = 0;
        scores = new int[mapData.length];    // one score for each map
        showMap(currentMap);
    }
    
    /**
     * Return true, if the specified coordinate shows water.
     * (Water is defined as a predominantly blueish color.)
     */
    public boolean isWater(int x, int y)
    {
        Color col = map.getColorAt(x, y);
        return col.getBlue() > (col.getRed() * 2);
    }
    
    /**
     * Jump to the given map number (1..n).
     */
    public void jumpToMap(int map)
    {
        clearWorld();
        currentMap = map-1;
        showMap(currentMap);
    }
    
    /**
     * Set up the start scene.
     */
    private void showMap(int mapNo)
    {
        if (Greep.getAuthorName().equalsIgnoreCase("Anonymous")) {
            addObject(anonymousWarning, getWidth() / 2, getHeight() / 2);
        } else {
            if (anonymousWarning.getWorld() == this) {
                removeObject(anonymousWarning);
            }
        }
        map = new GreenfootImage("map" + mapNo + ".jpg");
        setBackground(map);
        Counter mapTitle = new Counter(Greep.getAuthorName() + " - Map ", mapNo+1);
        addObject(mapTitle, 200, 20);
        int[][] thisMap = mapData[mapNo];
        for(int i = 1; i < thisMap.length; i++) {
            int[] data = thisMap[i];
            addObject(new TomatoPile(data[0]), data[1], data[2]);
        }
        int[] shipData = thisMap[0];
        ship = new Ship(shipData[0]);
        addObject(ship, shipData[1], shipData[2]);
    }
    
    /**
     * Game is over. Stop running, display score.
     */
    public void mapFinished(int time)
    {
        displayScore(time);
        wait(SCORE_DISPLAY_TIME);
        clearWorld();
        currentMap++;
        if(currentMap < mapData.length) {
            showMap(currentMap);
        }
        else {
            displayFinalScore();
            Greenfoot.stop();
            if (!STOP_BETWEEN_GAMES) {
                Greenfoot.setWorld(new Earth());
                Greenfoot.start();
            }
        }
    }

    private void displayScore(int time)
    {
        int points = ship.getTomatoCount() + time;
        scores[currentMap] = points;
        ScoreBoard board = new ScoreBoard(Greep.getAuthorName(), "Map " + (currentMap+1), "Score: ", currentMap, scores);
        addObject(board, getWidth() / 2, getHeight() / 2);
    }
    
    private void displayFinalScore()
    {
        clearWorld();
        ScoreBoard board = new ScoreBoard(Greep.getAuthorName(), "Final score", "Total: ", scores);
        addObject(board, getWidth() / 2, getHeight() / 2);
        
        int fullScore = 0;
        ArrayList<Integer> scoresList = new ArrayList<Integer>();
        for (int score : scores) {
            fullScore += score;
            scoresList.add(score);
        }

        if (SAVE_SCORES_TO_FILE) {
            ArrayList<StudScore> studScores = readScores(new File("LatestScores.txt"));

            studScores.add(new StudScore(Greep.getAuthorName(), scoresList));
            Collections.sort(studScores);
            Collections.reverse(studScores);
            FileWriter fw = null;
            try {
                fw = new FileWriter(new File("LatestScores.txt"));
                for (StudScore studS : studScores) {
                    fw.write(studS + System.lineSeparator());
                }
                fw.close();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }

        if (TRACK_MULTIPLE_SCORES) {
            totalOfAllScores += fullScore;
            numTotalScores++;
            if (fullScore > overAllHighScore) {
                overAllHighScore = fullScore;
            }
            if (fullScore < overAllLowScore) {
                overAllLowScore = fullScore;
            }
    
            double avg = (double)totalOfAllScores / numTotalScores;
            System.out.println(totalOfAllScores + " tomatoes in " + numTotalScores + " games => Average Score: " + avg + " high score: " + overAllHighScore + " low Score: " + overAllLowScore);
        }
    }

    private ArrayList<StudScore> readScores(File file) {
        ArrayList<StudScore> studScores = new ArrayList<StudScore>();
        Scanner in = null;
        try {
            in = new Scanner(file);
            while (in.hasNextLine()) {
                String line = in.nextLine();
                int indexOfScores = line.indexOf("[");
                int idxOfEndScores = line.indexOf("]");
                String name = line.substring(0, indexOfScores).trim();
                Scanner scoreScan = new Scanner(line.substring(indexOfScores + 1, idxOfEndScores).replaceAll(",", ""));
                ArrayList<Integer> mapScores = new ArrayList<Integer>();
                while (scoreScan.hasNextInt()) {
                    mapScores.add(scoreScan.nextInt());
                }
                scoreScan.close();
                studScores.add(new StudScore(name, mapScores));
            }
            if (in != null) in.close();
        } catch (FileNotFoundException fnfe) {
            try {
                FileWriter fw = new FileWriter(file);
                fw.write("");
                fw.close();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studScores;
    }

    private void printArray(ArrayList<StudScore> arr) {
        for (StudScore s : arr) {
            System.out.println(s);
        }
    }
    
    private void clearWorld()
    {
        removeObjects(getObjects(null));
    }
    
    private void wait(int time)
    {
        for (int i = 0; i < time; i++) {
            Greenfoot.delay(1);
        }
    }
}

class StudScore implements Comparable<StudScore> {
    private String name;
    private ArrayList<Integer> mapScores;

    public StudScore(String name, ArrayList<Integer> mapScores) {
        this.name = name;
        this.mapScores = mapScores;
    }

    public int compareTo(StudScore other) {
        return this.getScore() - other.getScore();
    }

    public int getScore() {
        int total = 0;
        for (int score : mapScores) {
            total += score;
        }
        return total;
    }

    public String toString() {
        return name + " " + mapScores + " " + getScore();
    }
}