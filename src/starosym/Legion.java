/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Legion proud of beeing member of this society
 * @author Ślimak
 */
public class Legion extends Postac {
    private Barbarzyncy banda;
    
    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param wymX width of sprite
     * @param wymY height of sprite
     * @param miasto Osada startpoint
     */
    public Legion(int x, int y,int wymX,int wymY,Osada miasto){
        super(x,y,wymX,wymY,miasto);
        this.setPoprzednik(this.getSzlak().getOstatni());
        if(this.getPoprzednik()!=null
                &&this.getPoprzednik().getSzlak()!= this.getSzlak()) 
            this.setPoprzednik(null);
        this.getSzlak().setOstatni(this);
        this.banda=null;
    }
    /**
     * Function locates banda and makes Legion following banda.
     * @param banda Barbarzyncy to follow
     */
    public void okreslCel(Barbarzyncy banda){
        this.banda=banda;
        if(this.banda!=null) this.setCel(this.banda.getCel());
    }

    @Override
    public void miasto1() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Legion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(this.getMiastoPobytu().isZniszczona()) 
            this.setCel(this.banda.getCel());
        this.status=5;
    }

    @Override
    public void miasto2()
    {
        if(this.getMiastoPobytu()==null)
        {
            if((this.getPoprzednik()!=null)
                    &&(this.getSzlak()!=this.getPoprzednik().getSzlak()))
                this.setPoprzednik(null);
            if((this.getPoprzednik()!=null)
                    &&(((Math.abs(this.getPoprzednik().getX()
                    -this.getSzlak().getxPoczatek())
                    <this.getWymiarX()+this.getPoprzednik().getWymiarX())
                    &&(Math.abs(this.getPoprzednik().getY()
                    -this.getSzlak().getyPoczatek())
                    <this.getWymiarY()+this.getPoprzednik().getWymiarY()))
                    ||(this.getPoprzednik().getX()<0)))
                try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Legion.class.getName()).log(Level.SEVERE, null, ex);
            }
            else
            {
                this.setSzlak(this.getSzlak());
                this.status=1;
            }
        }
        else
            super.miasto2();
    }
    @Override
    public int idzDroga(){
        int warunek=super.idzDroga();
        if(warunek==2)
        {
            Osada miasto=(Osada) this.getSzlak().getKoniec();
            if(miasto.getGosc() instanceof Barbarzyncy)
            {
                if(miasto.getGosc()==this.banda) this.nullBanda();
                miasto.getGosc().gin();
                Random los= new Random();
                miasto.uciskaj(los.nextInt(3000)+1);
                this.gin();
            }
        }
        else if(warunek==3)
        {
            if(this.getPoprzednik() instanceof Barbarzyncy)
            {
                if(this.getBanda()==this.getPoprzednik()) this.nullBanda();
                this.getPoprzednik().gin();
                this.setPoprzednik(this.getPoprzednik().getPoprzednik());
                this.gin();
            }
        }
        return 0;
    }
    
    @Override
    public int idzSkrzyzowanie(){
        int warunek=super.idzSkrzyzowanie();
        if(warunek==3)
        {
            if(this.getPoprzednik() instanceof Barbarzyncy)
            {
                if(this.banda==this.getPoprzednik()) this.nullBanda();
                this.getPoprzednik().gin();
                this.setPoprzednik(this.getPoprzednik().getPoprzednik());
                this.gin();
            }
        }
        return 0;
    }
    
    @Override
    public void czekajSkrzyzowanie()
    {
        super.czekajSkrzyzowanie();
        if(this.status==3)
        {
            if(this.getCzekamNaSkrzyzowaniu().getUzytkownik() 
                    instanceof Barbarzyncy)
            {
                this.getCzekamNaSkrzyzowaniu().getUzytkownik().gin();
                this.gin();
            }
        }
    }
    
    @Override
    public String opisStanu()
    {
        switch(status){
            case 1:
                return "Podążam za barbarzyńcami</html>";
            case 2:
                return "Na skrzyżowaniu podążam <br>za barbarzyńcami</html>";
            case 3:
                return "Czekam na skrzyżowaniu, ale podążam</html>";
            case 4:
                return "Pilnuję miasta</html>";
            case 5:
                return "Szykuję się <br>do patrolu</html>";
            default:
                return "Ups!</html>";
        }
    }
    
    /**
     * @return the banda
     */
    public Barbarzyncy getBanda() {
        return banda;
    }

    /**
     *Sets null in field banda
     */
    public void nullBanda() {
        this.banda = null;
    }
}
