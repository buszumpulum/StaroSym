/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

/**
 *Osada that can send legions
 * @author Ślimak
 */
public class Stolica extends Osada{
    
    /**
     *Calls constructor from Osada
     * @param id id of Stolica
     * @param x x coordinate
     * @param y y coordinate
     * @param nazwa name of Stolica
     * @param lud citizens of Stolica
     * @param skarbiec money of Stolica
     * @param produkcja bitvector production
     * @param skup bitvector purchase
     */
    public Stolica(int id,int x, int y, String nazwa, int lud, int skarbiec, int produkcja, int skup)
    {
        super(id,x,y,nazwa,lud,skarbiec,produkcja,skup);
    }
    /**
     * Function creates Legion to destroy Barbarzyncy
     * @param wymX width of sprite
     * @param wymY height of sprite
     * @return created Legion
     */
    public Legion wyslijLegion(int wymX,int wymY){
        return new Legion(-1,this.getY(),wymX,wymY,this);
    }
}
