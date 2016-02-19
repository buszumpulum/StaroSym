/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Iterator;

/**
 *City with storage and production ability
 * @author Ślimak
 */
public class Osada extends Byt{
    private boolean zniszczona;
    private boolean gnebiona;
    private String nazwa;
    private int id;
    private int ludnosc;
    private int stanSkarbca;
    private int produkcja;
    private int skup;
    private Magazyn stanMagazynu;
    private Droga wyjscie;
    private Postac gosc;
    
    /**
     *
     * @param id id of Osada
     * @param x x coordinate
     * @param y y coordinate
     * @param nazwa name of Osada
     * @param lud citizens of Osada
     * @param skarbiec money of Osada
     * @param produkcja bitvector production
     * @param skup bitvector purchase
     */
    public Osada(int id,int x, int y, String nazwa, int lud, int skarbiec, int produkcja, int skup)
    {
        super(x,y);
        this.nazwa=nazwa;
        this.ludnosc=lud;
        this.stanSkarbca=skarbiec;
        this.produkcja=produkcja;
        this.skup=skup;
        this.stanMagazynu= new Magazyn(200,100);
        this.id=id;
        this.zniszczona=false;
        this.gnebiona=false;
        this.gosc=null;
    }
    /**
     * Produces new Surowiec if there is a space inn stanMagazynu
     */
    public void wyprodukuj(){
        for(int i=128;i>0;i=i/2){
            int warunek=this.produkcja&i;
            if(warunek>0)
                this.stanMagazynu.dodaj(new Surowiec(i,this));
        }
    }
    /**
     * Function makes sure that every Surowiec is fresh and moisty
     */
    public void sprawdzWaznosc(){
        Iterator<Surowiec> iterator = this.stanMagazynu.getStan().iterator();
        while(iterator.hasNext())
        {
            Surowiec temp  = iterator.next();
            if(temp.getProducent()==this) continue;
            temp.dzienMniej();
            if(temp.isZywnosc()==false) 
                this.stanSkarbca+= 100*temp.getMasa();
                iterator.remove();
                this.getStanMagazynu().usun(temp);
        }
    }

    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @return the ludnosc
     */
    public int getLudnosc() {
        return ludnosc;
    }

    /**
     * @param ludnosc the ludnosc to set
     */
    public void setLudnosc(int ludnosc) {
        this.ludnosc = ludnosc;
    }

    /**
     * @return the stanSkarbca
     */
    public int getStanSkarbca() {
        return stanSkarbca;
    }

    /**
     * @param stanSkarbca the stanSkarbca to set
     */
    public void setStanSkarbca(int stanSkarbca) {
        this.stanSkarbca = stanSkarbca;
    }

    /**
     * @return the produkcja
     */
    public int getProdukcja() {
        return produkcja;
    }

    /**
     * @return the skup
     */
    public int getSkup() {
        return skup;
    }

    /**
     * @return the stanMagazynu
     */
    public Magazyn getStanMagazynu() {
        return stanMagazynu;
    }

    /**
     * @return the wyjscie
     */
    public Droga getWyjscie() {
        return wyjscie;
    }

    /**
     * @param wyjscie the wyjscie to set
     */
    public void setWyjscie(Droga wyjscie) {
        this.wyjscie = wyjscie;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     *
     * @return the zniszczona
     */
    public boolean isZniszczona(){
        return this.zniszczona;
    }
    
    /**
     *
     * @return the gnebiona
     */
    public boolean isGnebiona(){
        return this.gnebiona;
    }
    
    /**
     *If gneb is true sets stanSkarbca to 0 and calls stanMagazynu.oproznij()
     * @param gneb true if start to killing ludnosc, false if end
     */
    public void setGneb(boolean gneb)
    {
        this.gnebiona=gneb;
        if(gneb) 
        {
            this.stanMagazynu.oproznij();
            this.stanSkarbca=0;
        }
    }
    
    /**
     *Subtracts deltaLudnosci from ludnosc. If ludnosc is less then 0 sets ludnosc
     * to 0 and sets zniszczona to true
     * @param deltaLudnosci to substract
     */
    public void uciskaj(int deltaLudnosci)
    {
        this.ludnosc-=deltaLudnosci;
        if(this.ludnosc<=0)
        {
            this.zniszczona=true;
            this.ludnosc=0;
        }
    }

    /**
     * @return the gosc
     */
    public Postac getGosc() {
        return gosc;
    }

    /**
     * @param gosc the gosc to set
     */
    public void setGosc(Postac gosc) {
        this.gosc = gosc;
    }
}
