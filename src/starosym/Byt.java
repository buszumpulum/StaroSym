/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

/**
 *Abstract class that is base for every object on map
 * @author Ślimak
 */
public abstract class Byt {
    private int x;
    private int y;

    /**
     * X and Y coordinate of Byt
     * @param x x coordinate
     * @param y y coordinate
     */
    public Byt(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
} 
