/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

/**
 *Class of Handlowiec's cart
 * @author Ślimak
 */
public class Woz extends Magazyn{
    private int masa;
    private float wspolczynnik;
    Woz()
    { 
        super(20,10);
        this.masa=0;
        this.wspolczynnik=1;
    }
    @Override
    public boolean dodaj(Surowiec towar){
        if(super.dodaj(towar))
        {
            this.masa+= towar.getMasa();
            this.wspolczynnik= this.getMasa()/50;
            return true;
        }
        else return false;
    }
    
    @Override
    public void usun(Surowiec towar){
        this.masa-=towar.getMasa();
        this.wspolczynnik= this.getMasa()/50;
    }

    /**
     * @return the wspolczynnik
     */
    public float getWspolczynnik() {
        return wspolczynnik;
    }

    /**
     * @return the masa
     */
    public int getMasa() {
        return masa;
    }
}
