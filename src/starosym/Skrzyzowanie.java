/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.ArrayList;

/**
 *Implementation of crossroads
 * @author Ślimak
 */
public class Skrzyzowanie extends Byt{
    private ArrayList<Droga> drogi;
    private boolean zajete;
    private Postac uzytkownik;
    /**
     *Creates new Skrzyzowanie with empty list of roads
     */
    public Skrzyzowanie()
    {
        super(0,0);
        this.zajete=false;
        this.drogi=new ArrayList<>();
        this.uzytkownik=null;
    }
    /**
     *Adds droga to drogi
     * @param droga Droga to add
     */
    public void dodajDroge(Droga droga)
    {
        this.drogi.add(droga);
    }
    /**
     * Function lockes the crossing
     * @param osoba 
     * @return true if was free, false if was locked
     */
    public synchronized boolean zajmij(Postac osoba){
        if(this.zajete)
        {
           return false;
        }
        else
        {
            this.zajete=true;
            this.uzytkownik=osoba;
            return true;
        }
    }
    /**
     * Function unlockes the crossing
     */
    public void zwolnij(){
        this.uzytkownik=null;
        this.zajete=false;
    }

    /**
     * @return the drogi
     */
    public ArrayList getDrogi() {
        return drogi;
    }

    /**
     * @return the zajete
     */
    public boolean isZajete() {
        return zajete;
    }

    /**
     * @return the uzytkownik
     */
    public Postac getUzytkownik() {
        return uzytkownik;
    }
}
