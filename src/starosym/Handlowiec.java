/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Iterator;
import java.util.Random;

/**
 *Men that is selling and buying stocks in Osada's
 * @author Ślimak
 */
public class Handlowiec extends Postac{
    private String imie;
    private String nazwisko;
    private Woz bryka;
    
    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param wymX width of sprite
     * @param wymY height of sprite
     * @param miejsce Osada startpoint
     */
    public Handlowiec(int x, int y,int wymX,int wymY,Osada miejsce)
    {
        super(x,y,wymX,wymY,miejsce);
        
        this.bryka= new Woz();
        Random los=new Random();
        char samogloski[]={'a','e','i','o','u','y'};
        char spolgloski[]={'b','c','d','f','g','h','j','k','l','m','n','p',
            'q','r','s','t','v','w','x','z'};
        this.imie= Character.toString(Character.toUpperCase(spolgloski[los.nextInt(spolgloski.length)]));
        for(int i=0;i<los.nextInt(5)+1;i++) 
        {
            if(i%2==0) this.imie=this.imie+Character.toString(samogloski[los.nextInt(samogloski.length)]);
            else this.imie=this.imie+Character.toString(spolgloski[los.nextInt(spolgloski.length)]);
        }
        this.nazwisko= Character.toString(Character.toUpperCase(samogloski[los.nextInt(samogloski.length)]));
        for(int i=0;i<los.nextInt(5)+2;i++) 
        {
            if(i%2==0) this.nazwisko=this.nazwisko+Character.toString(spolgloski[los.nextInt(spolgloski.length)]);
            else this.nazwisko=this.nazwisko+Character.toString(samogloski[los.nextInt(samogloski.length)]);
        }
    }
    
    @Override
    public String opisStanu()
    {
        if(this.isAktywny()==false)
        {
            return "Kółko odpadło, naprawić trza...</html>";
        }
        switch(status){
            case 1:
                return "Jadę sobie z towarami";
            case 2:
                return "Przez skrzyżowanie <br>jadę sobie z towarami</html>";
            case 3:
                return "Czekam na skrzyżowaniu</html>";
            case 4:
                return "Towary sprzedaję w mieście</html>";
            case 5:
                return "Towary skupuję z miasta</html>";
            default:
                return "Ups!</html>";
        }
    }
    /**
     * Function adds Surowiec to Woz
     * @param towar 
     * @return true if done, false if was impossible
     */
    public boolean zaladujTowar(Surowiec towar){return bryka.dodaj(towar);}
    
    /**
     * Function puts coresponding Surowiec to miejscePobytu
     * @return true if for this miastoPobytu was selled
     */
    public boolean sprzedajTowary(){
        boolean sukces= true;
        Iterator<Surowiec> iterator= this.bryka.getStan().iterator();
        while(iterator.hasNext())
        {
            Surowiec temp=iterator.next();
            if(temp.getOdbiorca()!=this.getMiastoPobytu().getId()) continue;
            sukces=this.getMiastoPobytu().getStanMagazynu().dodaj(temp);
            if(sukces) iterator.remove();
            this.getBryka().usun(temp);
        }
        return sukces;
    }
  
    /**
     *Method deletes every Surowiec that has miastoPobytu as Odbiorca, when
     * miastoPobytu is destroyed
     */
    public void zrzucBalast()
    {
        Iterator<Surowiec> towar=this.getBryka().getStan().iterator();
        while(towar.hasNext())
        {
            Surowiec balast=towar.next();
            if(balast.getOdbiorca()==this.getMiastoPobytu().getId())
            {
                towar.remove();
                this.bryka.usun(balast);
            }
        }
    }
    /**
     * Function destroys Handlowiec in a nonspectacular way
     */
    @Override
    public void gin()
    {
        super.gin();
        this.bryka.oproznij();
    }

    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @return the nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * @return the bryka
     */
    public Woz getBryka() {
        return bryka;
    }

    @Override
    public void miasto1() {
        if(this.getMiastoPobytu().isZniszczona())
        {
            this.zrzucBalast();
            this.status=5;
        }
        else if(this.sprzedajTowary()) this.status=5;
    }

    @Override
    public void miasto2() {
        if(this.getMiastoPobytu().isZniszczona()==false)
        {
            Iterator<Surowiec> iterator= 
                this.getMiastoPobytu().getStanMagazynu().getStan().iterator();
            while(iterator.hasNext())
            {
                Surowiec temp= iterator.next();
                int warunek= temp.getIdentyfikator()&
                        this.getMiastoPobytu().getProdukcja();
                if(warunek==0) continue;
                if(this.bryka.dodaj(temp))
                {
                    iterator.remove();
                    this.getMiastoPobytu().getStanMagazynu().usun(temp);
                }
            }
        }
        if(this.bryka.getStan().isEmpty()==false)
        {
            Surowiec temp=(Surowiec) this.getBryka().getStan().get(0);
            this.setCel(temp.getOdbiorca());
            super.miasto2();
        }
        else if(this.getMiastoPobytu().isZniszczona()) this.gin();
     }
    
    @Override
    public int getPredkosc()
    {
        int temp=(int)(super.getPredkosc()*(1-this.getBryka().getWspolczynnik()));
        if(temp<5)
            return 5;
        else
            return temp;
    }
}
