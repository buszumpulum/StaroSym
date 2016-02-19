/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Random;

/**
 *Group of barberians with killing ability
 * @author Ślimak
 */
public class Barbarzyncy extends Postac{
    private String nazwa;
    private int liczebnosc;
    private String bron;
    
    /**
     * Creates new Barbarzyncy in points x,y, wymX and wymY are sizes of sprite,
     * and miasto is not used
     * @param x x coordinate
     * @param y y coordinate
     * @param wymX width of sprite
     * @param wymY height of sprite
     * @param miasto Osada startpoint
     */
    public Barbarzyncy(int x, int y,int wymX,int wymY,int miasto){
        super(x,y,wymX,wymY,null);
        Random los= new Random();
        int rand=los.nextInt(4);
        switch(rand){
            case 0:
                this.bron="Bumerang";
                break;
            case 1: 
                this.bron="Tulipan";
                break;
            case 2:
                this.bron="Sztućce";
                break;
            case 3:
                this.bron="Maczeta";
                break;
        }
        this.liczebnosc=los.nextInt(200)+100;
        char samogloski[]={'a','e','i','o','u','y'};
        char spolgloski[]={'b','c','d','f','g','h','j','k','l','m','n','p',
            'q','r','s','t','v','w','x','z'};
        this.nazwa= Character.toString(Character.toUpperCase(spolgloski[los.nextInt(spolgloski.length)]));
        int dlugosc=los.nextInt(6)+4;
        for(int i=0;i<dlugosc;i++)
        {
            if(i%2==0) this.nazwa=this.nazwa+Character.toString(samogloski[los.nextInt(samogloski.length)]);
            else this.nazwa=this.nazwa+Character.toString(spolgloski[los.nextInt(spolgloski.length)]);
            if((dlugosc%4==0)&&(i==7)) this.nazwa=this.nazwa + "'r";
            if((dlugosc%3==0)&&(i==9)) this.nazwa=this.nazwa + "'n";
        }
    }
    /**
     * Funtions sets new random Osada as a cel
     * @param wektorOsad bitvector with still existing Osada's
     */
    public void nowyCel(int wektorOsad){
        if(wektorOsad>0)
        {
            Random los=new Random();
            int krok=los.nextInt(10)+1;
            int warunek=wektorOsad&(1<<krok);
            if(warunek>0) this.setCel(1<<krok);
            else
                for(int i=(krok*2)%11;i!=krok;i=(i+krok)%11)
                {
                    warunek=wektorOsad&(1<<i);
                    if(warunek>0) 
                    {
                        this.setCel(warunek);
                        break;
                    }
                }
            if(warunek==0) this.gin();
        }
    }
    /**
     * Subtracts liczebnosc/100 from miastoPobytu
     */
    public void terroryzuj(){
        this.getMiastoPobytu().uciskaj((int)this.liczebnosc/100);
    }

    @Override
    public String opisStanu()
    {
        switch(status){
            case 1:
                return "Biegnę grabić</html>";
            case 2:
                return "Przez skrzyżowanie <br>biegnę grabić</html>";
            case 3:
                return "Czekam na skrzyżowaniu, <br>by biec grabić</html>";
            case 4:
                return "Grabię</html>";
            case 5:
                return "Zbieram sie grabić <br>inne osady</html>";
            default:
                return "Ups!</html>";
        }
    }
    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @return the liczebnosc
     */
    public int getLiczebnosc() {
        return liczebnosc;
    }

    /**
     * @return the bron
     */
    public String getBron() {
        return bron;
    }
    
    @Override
    public int idzDroga()
    {
        int warunek=super.idzDroga();
        if(warunek==2)
        {
            Osada miasto=(Osada) this.getSzlak().getKoniec();
            if(miasto.getGosc() instanceof Handlowiec)
                miasto.getGosc().gin();
            else if(miasto.getGosc() instanceof Legion)
            {
                Legion zbyszek =(Legion) miasto.getGosc();
                if(zbyszek.getBanda()==this) zbyszek.nullBanda();
                zbyszek.gin();
                this.gin();
            }
            else
            {
                Barbarzyncy wojtki= (Barbarzyncy) miasto.getGosc();
                wojtki.dolacz(this.liczebnosc);
                this.liczebnosc=0;
                this.gin();
            }
        }
        else if(warunek==3)
        {
            if(this.getPoprzednik() instanceof Handlowiec)
            {
                this.getPoprzednik().gin();
                this.setPoprzednik(this.getPoprzednik().getPoprzednik());
            }
            else if(this.getPoprzednik() instanceof Legion)
            {
                this.getPoprzednik().gin();
                this.gin();
            }
        }   
            else if(warunek==4)
            {
                this.getMiastoPobytu().setGneb(true);
            }
        return 0;
    }

    @Override
    public int idzSkrzyzowanie()
    {
        int warunek=super.idzSkrzyzowanie();
        if(warunek==3)
        {
            if(this.getPoprzednik() instanceof Handlowiec)
            {
                this.getPoprzednik().gin();
                this.setPoprzednik(this.getPoprzednik().getPoprzednik());
            }
            else if(this.getPoprzednik() instanceof Legion)
            {
                Legion zbyszek=(Legion) this.getPoprzednik();
                if(zbyszek.getBanda()==this) zbyszek.nullBanda();
                zbyszek.gin();
                this.gin();
            }
        }
        return 0;
    }
    
    @Override
    public void miasto1() {
        if(this.getMiastoPobytu().isZniszczona()==false) 
            this.terroryzuj();
        else {
            this.setCel(0);
            this.status=5;
        }
    }

    @Override
    public void miasto2() {
        if(this.getCel()!=0)
        {
            super.miasto2();
            if(this.status==5)
            {
                if(this.getMiastoPobytu().getWyjscie().getOstatni() instanceof Handlowiec)
                {
                    Handlowiec wojtek=(Handlowiec) 
                            this.getMiastoPobytu().getWyjscie().getOstatni();
                    this.setPoprzednik(wojtek.getPoprzednik());
                    wojtek.gin();
                }
            }
        }
    }
    
    @Override
    public void czekajSkrzyzowanie()
    {
        super.czekajSkrzyzowanie();
        if(this.status==3)
        {
            if(this.getCzekamNaSkrzyzowaniu().getUzytkownik() 
                    instanceof Handlowiec)
            {
                this.getCzekamNaSkrzyzowaniu().getUzytkownik().gin();
            }
        }
    }
    
    @Override
    public void gin()
    {
        super.gin();
        if(this.getMiastoPobytu()!=null) this.getMiastoPobytu().setGneb(false);
    }
    
    /**
     *Adds oddzial to liczebnosc
     * @param oddzial amount to add
     */
    public void dolacz(int oddzial)
    {
        this.liczebnosc+=oddzial;
    }
}
