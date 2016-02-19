/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.io.Serializable;

/**
 *Class to keep score
 * @author Ślimak
 */
public class Wynik implements Comparable<Wynik> {
    private String imie;
    private int pokonane;
    private int minuty;
    private int sekundy;
    /**
     *Creates new Wynik with empty fields
     */
    public Wynik(){}
    /**
     *Creates new Wynik with imie as a name, pokonane is count of killed 
     * Barbarzyncy, minuty are minutes and sekundy are seconds of playing
     * @param imie
     * @param pokonane
     * @param minuty
     * @param sekundy
     */
    public Wynik(String imie,int pokonane, int minuty, int sekundy)
    {
        this.imie=imie;
        this.pokonane=pokonane;
        this.minuty=minuty;
        this.sekundy=sekundy;
    }
    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * @return the pokonane
     */
    public int getPokonane() {
        return pokonane;
    }

    /**
     * @param pokonane the pokonane to set
     */
    public void setPokonane(int pokonane) {
        this.pokonane = pokonane;
    }

    /**
     * @return the minuty
     */
    public int getMinuty() {
        return minuty;
    }

    /**
     * @param minuty the minuty to set
     */
    public void setMinuty(int minuty) {
        this.minuty = minuty;
    }

    /**
     * @return the sekundy
     */
    public int getSekundy() {
        return sekundy;
    }

    /**
     * @param sekundy the sekundy to set
     */
    public void setSekundy(int sekundy) {
        this.sekundy = sekundy;
    }

    /**
     *
     * @param t Wynik to comapre
     * @return less then 0 if bigger
     */
    @Override
    public int compareTo(Wynik t) {
        return -(this.minuty*60)-this.sekundy+(t.minuty*60)+t.sekundy;
    }
    
    /**
     *Returns String prepared with HTML to be shown in messagebox 
     * @return Wynik converted to String
     */
    @Override
    public String toString()
    {
        String temp;
        String sek;
        if(this.getSekundy()<10) sek="0"+this.getSekundy();
        else sek="" + this.getSekundy();
        temp="<td>" +  this.getImie() + "</td><td>" + this.getMinuty() +
                ":" + sek
                + "</td> <td>" + this.pokonane + "</td>";
        return temp;
    }
}
