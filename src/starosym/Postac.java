/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Abstract class that is base for every moving object
 * @author Ślimak
 */
public abstract class Postac extends Byt implements Runnable {
    private boolean aktywny;
    private int predkosc;
    private int cel;
    private Droga szlak;
    private double predX;
    private double predY;
    private double tempX;
    private double tempY;
    private int wymiarX;
    private int wymiarY;
    /**
     *Integer that controls status of Postac
     */
    protected int status;
    private boolean widoczna;
    private int kierunek;
    private Postac poprzednik;
    private Thread watek;
    private Osada miastoPobytu;
    private Skrzyzowanie czekamNaSkrzyzowaniu;
    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param wymX width of sprite
     * @param wymY height of sprite
     * @param miasto Osada startpoint
     */
    public Postac(int x, int y,int wymX, int wymY, Osada miasto)
    {
        super(x,y);
        this.tempX=(double) x;
        this.tempY=(double) y;
        this.widoczna=false;
        if(miasto==null) this.status=1;
        else this.status=5;
        this.cel= 0;
        this.kierunek=0;
        this.miastoPobytu=miasto;
        if(x<0)
        {
            this.szlak=miasto.getWyjscie();
            this.miastoPobytu=null;
        }
        else
        {
            this.szlak= null;
        }
        this.watek= new Thread(this);
        this.wymiarX=wymX/2;
        this.wymiarY=wymY/2;
        Random los=new Random();
        this.predkosc=los.nextInt(30)+10;
        this.czekamNaSkrzyzowaniu=null;
        
        this.aktywny=true;
    }
    /**
     * Function makes Postac moving.
     * @return status of situation (1 - no problems, 2- come to Osada, 3- road is blocked, 4- can't go into Osada
     */
    public int idzDroga(){
        int jak;
        while((this.poprzednik!=null)&&(this.poprzednik.status==0)) 
            this.setPoprzednik(this.poprzednik.poprzednik);
        if((this.poprzednik!=null)&&(this.poprzednik.getSzlak()!=this.getSzlak())
                &&(this.poprzednik.status==1)) this.poprzednik=null;
        if((this.poprzednik!=null)&&(this.poprzednik.szlak!=this.szlak)
                &&(this.poprzednik.getCzekamNaSkrzyzowaniu()!=this.szlak.getKoniec()))
            this.setPoprzednik(null);
        if(((this.predX>0)&&(this.tempX+this.predX>this.szlak.getxKoniec()))
                ||((this.predX<0)&&(this.tempX+this.predX<this.szlak.getxKoniec()))
                ||((this.predY>0)&&(this.tempY+this.predY>this.szlak.getyKoniec()))
                ||((this.predY<0)&&(this.tempY+this.predY<this.szlak.getyKoniec())))
        {
           this.tempX=this.szlak.getxKoniec();
           this.tempY=this.szlak.getyKoniec();
           if(this.szlak.getKoniec() instanceof Skrzyzowanie)
           {
               Skrzyzowanie temp=(Skrzyzowanie) this.szlak.getKoniec();
               this.wyborSzlaku(temp);
               this.status=3;
               jak=1;
            }
            else
            {
                Osada temp=(Osada) this.szlak.getKoniec();
                jak=2;
                if(temp.getGosc()==null)
                {
                    this.status=4;
                    this.miastoPobytu=(Osada) this.szlak.getKoniec();
                    this.miastoPobytu.setGosc(this);
                    this.widoczna=false;
                    this.szlak=null;
                    jak=4;
                }
            } 
        }
        else
        {
            jak=1;
            if(this.getPoprzednik()!=null)      
            {
               if((this.predX!=0)
                    &&(Math.abs(this.tempX+this.predX-this.getPoprzednik().getX())<
                       this.getPoprzednik().getWymiarX()+this.getWymiarX()))
               {
                if(this.predX<0)
                   this.tempX=
                   this.getPoprzednik().getX()+this.getPoprzednik().getWymiarX()
                           +this.getWymiarX();
                else
                    this.tempX=this.getPoprzednik().getX()
                            -this.getPoprzednik().getWymiarX()-this.getWymiarX();
                            jak=3;
                 }
              else
                this.tempX+=this.predX;
             if((this.predY!=0)
                    &&(Math.abs(this.tempY+this.predY-this.getPoprzednik().getY())
                    <this.getPoprzednik().getWymiarY()+this.getWymiarY()))
             {
                if(this.predY<0)
                    this.tempY=this.getPoprzednik().getY()
                            +this.getPoprzednik().getWymiarY()+this.getWymiarY();
                else
                    this.tempY=this.getPoprzednik().getY()
                            -this.getPoprzednik().getWymiarY()-this.getWymiarY();
                jak=3;
             }
              else
                this.tempY+=this.predY;
            }
            else
            {
                this.tempX+=this.predX;
                this.tempY+=this.predY;
            } 
        }
        this.setX((int) this.tempX);
        this.setY((int) this.tempY);
        return jak;
    }
    
    /**
     *Method makes Postac moving through crossing
     * @return status of situation (1- no problems, 3- road is blocked
     */
    public int idzSkrzyzowanie(){
        int jak=3;
        double xMoreTemp = this.tempX+this.predX;
        double yMoreTemp = this.tempY+this.predY;
        if(this.getPoprzednik()!=null&&this.getPoprzednik().status==0)
            this.setPoprzednik(this.getPoprzednik().getPoprzednik());
        if(this.getPoprzednik()!=null
                &&this.getPoprzednik().getSzlak()!=this.getSzlak())
            this.setPoprzednik(null);
        if(((this.getPoprzednik()!=null)
            &&((Math.abs(xMoreTemp-this.getPoprzednik().getX())>this.getPoprzednik().wymiarX+this.getWymiarX())
            ||(Math.abs(yMoreTemp-this.getPoprzednik().getY())>this.getPoprzednik().wymiarY+this.getWymiarY())))
                ||(this.getPoprzednik()==null))
            {
                if((Math.signum(this.tempX-this.szlak.getxPoczatek())
                        !=Math.signum(xMoreTemp-this.szlak.getxPoczatek()))
                   ||(Math.signum(this.tempY-this.szlak.getyPoczatek())
                        !=Math.signum(yMoreTemp-this.szlak.getyPoczatek())))
                { 
                    this.getCzekamNaSkrzyzowaniu().zwolnij();
                    this.czekamNaSkrzyzowaniu=null;
                    this.setSzlak(this.szlak);
                    this.status=1;
                    jak=1;
                }
                else
                {
                    this.tempX=xMoreTemp;
                    this.tempY=yMoreTemp;
                    this.setX((int) this.tempX);
                    this.setY((int) this.tempY);
                    jak=1;
                }
       }  
        return jak;
  }
 
    /**
     *Method makes Postac waiting on the crossing and sets crossing for this Postac
     * when crossing is free
     */
    public void czekajSkrzyzowanie(){
        if(this.getCzekamNaSkrzyzowaniu().isZajete()==false){
            this.getCzekamNaSkrzyzowaniu().zajmij(this);
            this.setPoprzednik(this.szlak.getOstatni());
            if(this.getPoprzednik()==this) this.setPoprzednik(null);
            this.status=2;
        }
    }
    /**
     *Method starts Postac's thread
     */
    public void start(){
        this.aktywny=true;
        this.watek.start();
    }
    /**
     *Method is pausing Postac
     */
    public void stop(){
        this.aktywny=false;
    }
    /**
     *Mehtod is awaking Postac form pause
     */
    public void aktywuj()
    {
        this.aktywny=true;
    }
    /**
     *Method returns String describing status of Postac
     * @return describing of status
     */
    public abstract String opisStanu();
    
    /**
     *Method having logic for visiting city
     */
    public abstract void miasto1();
    /**
     *Method having logic for going away from city
     */
    public void miasto2()
    {
        Postac temp = this.miastoPobytu.getWyjscie().getOstatni();
        if(temp==this) 
        {
            temp=null;
            //this.miastoPobytu.getWyjscie().setOstatni(null);
        }
        if(temp!=null)
         {
         if(((Math.abs(temp.getX()-this.miastoPobytu.getWyjscie().getxPoczatek())
                >temp.getWymiarX()+this.getWymiarX())
          ||(Math.abs(temp.getY()-this.miastoPobytu.getWyjscie().getyPoczatek())
                >temp.getWymiarY()+this.getWymiarY()))&&(temp.getX()>0))
                {
                    this.setSzlak(this.miastoPobytu.getWyjscie());
                    this.miastoPobytu.setGosc(null);
                    this.miastoPobytu=null;
                    this.status=1;
                }   
          }
        else
        {
            this.setSzlak(this.miastoPobytu.getWyjscie());
            this.miastoPobytu.setGosc(null);
            this.miastoPobytu=null;
            this.status=1;
        }
          
    }
    
    /**
     * @return the predkosc
     */
    public int getPredkosc() {
        return predkosc;
    }

    /**
     * @return the cel
     */
    public int getCel() {
        return cel;
    }

    /**
     * @param cel the cel to set
     */
    public void setCel(int cel) {
        this.cel = cel;
    }

    /**
     * @return the szlak
     */
    public Droga getSzlak() {
        return szlak;
    }

    /**
     *Method puts Postac at the begining of szlak, calculates velocity
     * and sets Poprzednik
     * @param szlak the szlak to set
     */
    public void setSzlak(Droga szlak) {
        this.szlak = szlak;
        if(this.getX()>-1)
        {
            if(szlak.getOstatni()!=this) this.setPoprzednik(szlak.getOstatni());
            else this.setPoprzednik(null);
        }
        if(this.getX()>0) szlak.setOstatni(this);
        this.tempX=szlak.getxPoczatek();
        this.tempY=szlak.getyPoczatek();
        this.setX(szlak.getxPoczatek());
        this.setY(szlak.getyPoczatek());
        int deltax=szlak.getxKoniec()-szlak.getxPoczatek();
        int deltay=szlak.getyKoniec()-szlak.getyPoczatek();
        if(deltax>0) 
        {
            this.predX=this.getPredkosc()/25.0;
            this.kierunek=1;
        }
        else if(deltax==0) this.predX=0;
            else 
                {
                    this.predX=-this.getPredkosc()/25.0;
                    this.kierunek=3;
                }
        if(deltay>0) {
            this.predY=this.getPredkosc()/25.0;
            this.kierunek=2;
        }
        else if(deltay==0) this.predY=0;
             else 
                {
                    this.predY=-this.getPredkosc()/25.0;
                    this.kierunek=0;
                }
        this.widoczna=true;
    }
    
    /**
     *Method with logic to decide which way to use on crossing
     * @param rozwidlenie Skrzyzowanie to pass
     */
    public void wyborSzlaku(Skrzyzowanie rozwidlenie){
        Iterator<Droga> iterator = rozwidlenie.getDrogi().iterator();
        while(iterator.hasNext())
        {
            Droga sciezka= iterator.next();
            int traf = sciezka.getProwadziDo()&this.cel;
            if(traf>0)
            {
                this.szlak=sciezka;
                break;
            }
        }
        this.poprzednik=this.szlak.getOstatni();
        if(this.poprzednik==this) this.poprzednik=null;
        while(this.poprzednik!=null&&this.poprzednik.status==0)
            this.poprzednik=this.poprzednik.poprzednik;
        if((this.poprzednik!=null)&&(this.poprzednik.getSzlak()!=this.szlak)) 
            this.poprzednik=null;
        int xTemp = this.szlak.getxPoczatek()-this.getX();
        int yTemp = this.szlak.getyPoczatek()-this.getY();
        double rTemp;
        rTemp = Math.sqrt(xTemp*xTemp+yTemp*yTemp);
        this.predX=xTemp*this.predkosc/rTemp;
        this.predX/=25;
        this.predY=yTemp*this.predkosc/rTemp;
        this.predY/=25;
        this.czekamNaSkrzyzowaniu=rozwidlenie;
    }

    /**
     *Loop that makes Postac alive in independent thread
     */
    @Override
    public void run() {
        int warunekLos=25;
        while(true)
        {
            if(this.status==0) break;
            if(this instanceof Handlowiec)
            {
                warunekLos--;
                if(warunekLos==0)
                {
                    Random los= new Random();
                    if((los.nextInt(100)==status)&&(this.status<4)) this.stop();
                    warunekLos=25;
                }
            }
            if(this.isAktywny()==false) 
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Postac.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }
        try {
            Thread.sleep(40);
        } catch (InterruptedException ex) {
            Logger.getLogger(Postac.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(this.status) {
            case 1: this.idzDroga();
                break;
            case 2: this.idzSkrzyzowanie();
                break;
            case 3: this.czekajSkrzyzowanie();
                break;
            case 4: this.miasto1();
                break;
            case 5: this.miasto2();
                break;
            default: break;
        }
        }
    }

    /**
     * @return the wymiarX
     */
    public int getWymiarX() {
        return wymiarX;
    }

    /**
     * @return the wymiarY
     */
    public int getWymiarY() {
        return wymiarY;
    }

    /**
     * @return the widoczna
     */
    public boolean isWidoczna() {
        return widoczna;
    }

    /**
     * @return the kierunek
     */
    public int getKierunek() {
        return kierunek;
    }

    /**
     * @return the miastoPobytu
     */
    public Osada getMiastoPobytu() {
        return miastoPobytu;
    }

    /**
     * @param miastoPobytu the miastoPobytu to set
     */
    public void setMiastoPobytu(Osada miastoPobytu) {
        this.miastoPobytu = miastoPobytu;
    }

    /**
     * @return the aktywny
     */
    public boolean isAktywny() {
        return aktywny;
    }
    
    /**
     *Kills Postac
     */
    public void gin()
    {
        if(this.getCzekamNaSkrzyzowaniu()!=null) 
        {
            this.getCzekamNaSkrzyzowaniu().zwolnij();
            this.czekamNaSkrzyzowaniu=null;
        }
        if(this.miastoPobytu!=null) this.miastoPobytu.setGosc(null);
        this.szlak=null;
        this.status=0;
    }

    /**
     * @return the poprzednik
     */
    public Postac getPoprzednik() {
        return poprzednik;
    }

    /**
     * @param poprzednik the poprzednik to set
     */
    public void setPoprzednik(Postac poprzednik) {
        this.poprzednik = poprzednik;
    }

    /**
     * @return the czekamNaSkrzyzowaniu
     */
    public Skrzyzowanie getCzekamNaSkrzyzowaniu() {
        return czekamNaSkrzyzowaniu;
    }
}
