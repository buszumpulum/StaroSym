/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.util.Random;

/**
 *Class with funcionality of stock
 * @author Ślimak
 */
public class Surowiec {
    private int identyfikator;
    private  String nazwa;
    private int objetosc;
    private int masa;
    private Osada producent;
    private int odbiorca;
    private int doPrzedawnienia;
    private boolean zywnosc;
    Surowiec(int typ,Osada produ){
        this.identyfikator=typ;
        this.producent= produ;
        Random los= new Random();
        if(typ==1)
        {
            this.nazwa="Filozofowie";
            this.objetosc= 5;
            this.masa= 2;
            int odbiorcy[]={1,16};
            this.odbiorca=odbiorcy[los.nextInt(2)];
            this.doPrzedawnienia=15;
            this.zywnosc= false;
        }
        else if(typ==2){
            this.nazwa="Tkaniny";
            this.objetosc= 3;
            this.masa= 1;
            int odbiorcy[]={16,64};
            this.odbiorca=odbiorcy[los.nextInt(2)];
            this.doPrzedawnienia=30;
            this.zywnosc= false;
        }
          else if(typ==4){
            this.nazwa="Chleb";
            this.objetosc= 6;
            this.masa= 5;
            int odbiorcy[]={8,128};
            this.odbiorca=odbiorcy[los.nextInt(2)];
            this.doPrzedawnienia=5;
            this.zywnosc= true;
        }
          else if(typ==8){
            this.nazwa="Wino";
            this.objetosc= 10;
            this.masa= 10;
            int odbiorcy[]={4,128,1024};
            this.odbiorca=odbiorcy[los.nextInt(3)];
            this.doPrzedawnienia=20;
            this.zywnosc= true;
        }
          else if(typ==16){
            this.nazwa="Złoto";
            this.objetosc= 2;
            this.masa= 50;
            this.odbiorca=1024;
            this.doPrzedawnienia=30;
            this.zywnosc= false;
        }
          else if(typ==32){
            this.nazwa="Serbro";
            this.objetosc= 4;
            this.masa= 40;
            this.odbiorca=256;
            this.doPrzedawnienia=30;
            this.zywnosc= false;
        }
          else if(typ==64){
            this.nazwa="Ruda";
            this.objetosc= 6;
            this.masa= 35;
            int odbiorcy[]={2,32};
            this.odbiorca=odbiorcy[los.nextInt(2)];
            this.doPrzedawnienia=25;
            this.zywnosc= false;
        }
          else{
              this.nazwa= "Drewno";
              this.objetosc= 20;
              this.masa= 25;
              int odbiorcy[]={1,5126};
              this.odbiorca=odbiorcy[los.nextInt(2)];
              this.doPrzedawnienia= 20;
              this.zywnosc= false;
          }
    }
    /**
     * Function subtracts 1 from doPrzedawnienia
     */
    public void dzienMniej(){
        if(this.doPrzedawnienia>0)
            this.doPrzedawnienia--;
    }

    /**
     * @return the identyfikator
     */
    public int getIdentyfikator() {
        return identyfikator;
    }

    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @return the objetosc
     */
    public int getObjetosc() {
        return objetosc;
    }

    /**
     * @return the masa
     */
    public int getMasa() {
        return masa;
    }

    /**
     * @return the producent
     */
    public Osada getProducent() {
        return producent;
    }

    /**
     * @return the odbiorca
     */
    public int getOdbiorca() {
        return odbiorca;
    }

    /**
     * @return the doPrzedawnienia
     */
    public int getDoPrzedawnienia() {
        return doPrzedawnienia;
    }

    /**
     * @return the zywnosc
     */
    public boolean isZywnosc() {
        return zywnosc;
    }
}
