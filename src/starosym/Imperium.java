/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 *Main class of game
 * @author Ślimak
 */
public class Imperium extends JPanel implements AdjustmentListener,MouseListener,
        ActionListener{
    private ArrayList<Osada> osady;
    private Droga drogi[];
    private Skrzyzowanie skrzyzowania[];
    private ArrayList<Handlowiec> handlowcy;
    private ArrayList<Barbarzyncy> barbarzyncy;
    private ArrayList<Legion> legiony;
    private BufferedImage handlarz[];
    private BufferedImage legion[];
    private BufferedImage barbarzynca[];
    private BufferedImage mapa;
    private File handlarzPNG[];
    private File legionPNG[];
    private File barbarzyncaPNG[];
    private File mapaPNG;
    private int deltax, deltay;
    JScrollBar horyzontalnie, wertykalnie;
    private PanelUzytkownika panel;
    private Osada wybranaOsada;
    private Postac wybranaPostac;
    private Osada wybranaOsadaCel;
    private int zabiciBarbarzyncy;
    private int licznik;
    private int licznikBarbarzyncow;
    private int wektorOsad;
    private int alphaAwarii;
    private ArrayList<Barbarzyncy> wolniBarbarzyncy;
    private int licznikKomunikat;
    private String komunikat;
    /**
     *Creates new Imperium: loads all graphics files and creates map structures 
     * @throws IOException
     */
    public Imperium() throws IOException
    {
        super(new BorderLayout());
        this.wybranaOsada= null;
        this.wybranaOsadaCel= null;
        this.wybranaPostac= null;
        this.osady = new ArrayList<>();
        this.handlowcy = new ArrayList<>();
        this.barbarzyncy = new ArrayList<>();
        this.legiony = new ArrayList<>();
        this.wolniBarbarzyncy= new ArrayList<>();
        this.handlarzPNG= new File[4];
        this.handlarzPNG[0]=new File("grafika/handn.png");
        this.handlarzPNG[1]=new File("grafika/hande.png");
        this.handlarzPNG[2]=new File("grafika/hands.png");
        this.handlarzPNG[3]=new File("grafika/handw.png");
        this.legionPNG= new File[4];
        this.legionPNG[0]= new File("grafika/legionn.png");
        this.legionPNG[1]= new File("grafika/legione.png");
        this.legionPNG[2]= new File("grafika/legions.png");
        this.legionPNG[3]= new File("grafika/legionw.png");
        System.out.println(this.legionPNG[0].getCanonicalPath());
        this.barbarzyncaPNG= new File[4];
        this.barbarzyncaPNG[0]= new File("grafika/barbn.png");
        this.barbarzyncaPNG[1]= new File("grafika/barbe.png");
        this.barbarzyncaPNG[2]= new File("grafika/barbs.png");
        this.barbarzyncaPNG[3]= new File("grafika/barbw.png");
        this.mapaPNG= new File("grafika/mapa.png");
        this.barbarzynca= new BufferedImage[4];
        this.legion=new BufferedImage[4];
        this.handlarz= new BufferedImage[4];
        for(int i=0;i<4;i++)
        {
            this.handlarz[i]= ImageIO.read(handlarzPNG[i]);
            this.barbarzynca[i]= ImageIO.read(barbarzyncaPNG[i]);
            this.legion[i]= ImageIO.read(legionPNG[i]);
        }
        this.mapa= ImageIO.read(mapaPNG);
        this.osady.add(new Osada(1,151,110,"Rogopolis",1000,4000,64,24));
        this.osady.add(new Osada(2,657,73,"Napolis",3000,5000,2,64));
        this.osady.add(new Osada(4,1020,211,"Trzypolis",2500,2000,1,8));
        this.osady.add(new Osada(8,1282,48,"Apollis",6000,6000,16,4));
        this.osady.add(new Osada(16,1498,660,"Heropolis",6500,10000,8,2));
        this.osady.add(new Osada(32,1437,1065,"Polipolis",3000,2000,32,64));
        this.osady.add(new Osada(64,1035,1255,"Atenopolis",4500,4000,134,2));
        this.osady.add(new Osada(128,85,1272,"Spartopolis",300,9000,16,12));
        this.osady.add(new Osada(256,487,895,"Półpolis",3000,5500,4,32));
        this.osady.add(new Osada(512,52,530,"Grekopolis",7000,7000,3,128));
        this.osady.add(new Stolica(1024,727,648,"Megapolis",10000,12000,2,24));
        this.skrzyzowania= new Skrzyzowanie[21];
        for(int i=0;i<21;i++) this.skrzyzowania[i]= new Skrzyzowanie();
        this.drogi=new Droga[57];
        this.drogi[0]= new Droga(this.skrzyzowania[0],0,298,144,298,0);
        this.drogi[1]= new Droga(this.skrzyzowania[0],144,144,144,236,0);
        this.osady.get(0).setWyjscie(this.drogi[1]);
        this.drogi[2]= new Droga(this.osady.get(0),160,236,160,144,1);
        this.skrzyzowania[0].dodajDroge(this.drogi[2]);
        this.drogi[3]= new Droga(this.skrzyzowania[0],478,248,184,248,1);
        this.skrzyzowania[2].dodajDroge(this.drogi[3]);
        this.drogi[4]= new Droga(this.skrzyzowania[2],184,264,478,264,2046);
        this.skrzyzowania[0].dodajDroge(this.drogi[4]);
        this.drogi[5]= new Droga(this.skrzyzowania[1],624,101,498,101,0);
        this.osady.get(1).setWyjscie(this.drogi[5]);
        this.drogi[6]= new Droga(this.osady.get(1),516,112,650,112,2047);
        this.skrzyzowania[17].dodajDroge(this.drogi[6]);
        this.drogi[7]= new Droga(this.skrzyzowania[2],498,103,498,228,2047);
        this.skrzyzowania[1].dodajDroge(this.drogi[7]);
        this.drogi[8]= new Droga(this.skrzyzowania[17],516,228,516,115,2);
        this.skrzyzowania[2].dodajDroge(this.drogi[8]);
        this.drogi[9]= new Droga(this.skrzyzowania[9],498,280,498,526,2044);
        this.skrzyzowania[2].dodajDroge(this.drogi[9]);
        this.drogi[10]= new Droga(this.skrzyzowania[2],516,528,516,282,3);
        this.skrzyzowania[9].dodajDroge(this.drogi[10]);
        this.drogi[11]= new Droga(this.osady.get(9),486,558,90,558,512);
        this.skrzyzowania[9].dodajDroge(this.drogi[11]);
        this.drogi[12]= new Droga(this.skrzyzowania[9],66,572,482,572,0);
        this.osady.get(9).setWyjscie(this.drogi[12]);
        this.drogi[13]= new Droga(this.skrzyzowania[19],499,594,499,800,2047-1-2-512-4);
        this.skrzyzowania[9].dodajDroge(this.drogi[13]);
        this.drogi[14]= new Droga(this.skrzyzowania[9],515,784,515,594,2047);
        this.skrzyzowania[15].dodajDroge(this.drogi[14]);
        this.drogi[15]= new Droga(this.skrzyzowania[8],499,802,694,802,2047);
        this.skrzyzowania[19].dodajDroge(this.drogi[15]);
        this.drogi[16]= new Droga(this.skrzyzowania[15],696,786,515,786,1+2+4+512);
        this.skrzyzowania[8].dodajDroge(this.drogi[16]);
        this.drogi[17]= new Droga(this.skrzyzowania[10],494,938,686,938,0);
        this.osady.get(8).setWyjscie(this.drogi[17]);
        this.drogi[18]= new Droga(this.osady.get(8),680,924,510,924,256);
        this.skrzyzowania[10].dodajDroge(this.drogi[18]);
        this.drogi[19]= new Droga(this.osady.get(7),676,1304,114,1304,128);
        this.skrzyzowania[13].dodajDroge(this.drogi[19]);
        this.drogi[20]= new Droga(this.skrzyzowania[13],98,1314,682,1314,0);
        this.osady.get(7).setWyjscie(this.drogi[20]);
        this.drogi[21]= new Droga(this.skrzyzowania[13],724,1412,724,1320,0);
        this.drogi[22]= new Droga(this.skrzyzowania[13],708,958,708,1280,128+64);
        this.skrzyzowania[10].dodajDroge(this.drogi[22]);
        this.drogi[23]= new Droga(this.skrzyzowania[10],730,1278,730,960,2047-128-64);
        this.skrzyzowania[13].dodajDroge(this.drogi[23]);
        this.drogi[24]= new Droga(this.osady.get(6),752,1298,1024,1298,64);
        this.skrzyzowania[13].dodajDroge(this.drogi[24]);
        this.drogi[25]= new Droga(this.skrzyzowania[13],1000,1282,744,1282,0);
        this.osady.get(6).setWyjscie(this.drogi[25]);
        this.drogi[26]= new Droga(this.skrzyzowania[11],750,933,1230,933,32+16+8+4);
        this.skrzyzowania[10].dodajDroge(this.drogi[26]);
        this.drogi[27]= new Droga(this.skrzyzowania[10],1234,915,746,915,2047-32-16-8-4);
        this.skrzyzowania[11].dodajDroge(this.drogi[27]);
        this.drogi[28]= new Droga(this.skrzyzowania[8],718,690,718,778,0);
        this.osady.get(10).setWyjscie(this.drogi[28]);
        this.drogi[29]= new Droga(this.osady.get(10),738,782,738,686,1024);
        this.skrzyzowania[8].dodajDroge(this.drogi[29]);
        this.drogi[30]= new Droga(this.skrzyzowania[8],850,791,746,791,2047);
        this.skrzyzowania[7].dodajDroge(this.drogi[30]);
        this.drogi[31]= new Droga(this.skrzyzowania[7],850,655,850,789,2047);
        this.skrzyzowania[6].dodajDroge(this.drogi[31]);
        this.drogi[32]= new Droga(this.skrzyzowania[6],1240,653,850,653,2047-16-8-4);
        this.skrzyzowania[5].dodajDroge(this.drogi[32]);
        this.drogi[33]= new Droga(this.skrzyzowania[9],1004,556,536,556,2047);
        this.skrzyzowania[14].dodajDroge(this.drogi[33]);
        this.drogi[34]= new Droga(this.skrzyzowania[18],528,573,1023,573,4);
        this.skrzyzowania[9].dodajDroge(this.drogi[34]);
        this.drogi[35]= new Droga(this.skrzyzowania[14],1004,434,1004,554,2047-4);
        this.skrzyzowania[3].dodajDroge(this.drogi[35]);
        this.drogi[36]= new Droga(this.skrzyzowania[3],1023,570,1023,434,2047);
        this.skrzyzowania[18].dodajDroge(this.drogi[36]);
        this.drogi[37]= new Droga(this.skrzyzowania[3],1009,250,1009,394,0);
        this.osady.get(2).setWyjscie(this.drogi[37]);
        this.drogi[38]= new Droga(this.osady.get(2),1025,390,1025,250,4);
        this.skrzyzowania[3].dodajDroge(this.drogi[38]);
        this.drogi[39]= new Droga(this.skrzyzowania[3],1260,406,1036,406,2047-8);
        this.skrzyzowania[4].dodajDroge(this.drogi[39]);
        this.drogi[40]= new Droga(this.skrzyzowania[4],1271,84,1271,378,0);
        this.osady.get(3).setWyjscie(this.drogi[40]);
        this.drogi[41]= new Droga(this.osady.get(3),1286,380,1286,84,8);
        this.skrzyzowania[4].dodajDroge(this.drogi[41]);
        this.drogi[42]= new Droga(this.skrzyzowania[4],1551,403,1298,403,0);
        this.drogi[43]= new Droga(this.skrzyzowania[4],1276,638,1276,430,12);
        this.skrzyzowania[5].dodajDroge(this.drogi[43]);
        this.drogi[44]= new Droga(this.skrzyzowania[5],1460,679,1300,679,0);
        this.osady.get(4).setWyjscie(this.drogi[44]);
        this.drogi[45]= new Droga(this.osady.get(4),1296,696,1480,696,16);
        this.skrzyzowania[5].dodajDroge(this.drogi[45]);
        this.drogi[46]= new Droga(this.skrzyzowania[5],1264,906,1264,700,16+4+8);
        this.skrzyzowania[11].dodajDroge(this.drogi[46]);
        this.drogi[47]= new Droga(this.skrzyzowania[11],1484,922,1280,922,0);
        this.drogi[48]= new Droga(this.skrzyzowania[12],1262,958,1262,1134,32);
        this.skrzyzowania[11].dodajDroge(this.drogi[48]);
        this.drogi[49]= new Droga(this.skrzyzowania[11],1277,1126,1277,954,2047-32);
        this.skrzyzowania[12].dodajDroge(this.drogi[49]);
        this.drogi[50]= new Droga(this.skrzyzowania[12],1422,1143,1304,1143,2047);
        this.skrzyzowania[16].dodajDroge(this.drogi[50]);
        this.drogi[51]= new Droga(this.skrzyzowania[20],1304,1154,1444,1154,32);
        this.skrzyzowania[12].dodajDroge(this.drogi[51]);
        this.drogi[52]= new Droga(this.skrzyzowania[16],1422,1102,1422,1143,0);
        this.osady.get(5).setWyjscie(this.drogi[52]);
        this.drogi[53]= new Droga(this.osady.get(5),1444,1154,1444,1102,32);
        this.skrzyzowania[20].dodajDroge(this.drogi[53]);
        this.drogi[54]= new Droga(this.skrzyzowania[12],1280,1282,1280,1188,0);
        this.drogi[55]= new Droga(this.skrzyzowania[10],716,818,716,892,256+128+64+32+16+8);
        this.skrzyzowania[8].dodajDroge(this.drogi[55]);
        this.drogi[56]= new Droga(this.skrzyzowania[8],738,900,738,814,1024+1+512+2+4);
        this.skrzyzowania[10].dodajDroge(this.drogi[56]);
        this.horyzontalnie = new JScrollBar(JScrollBar.HORIZONTAL,0,100,0,1000);
        this.wertykalnie = new JScrollBar(JScrollBar.VERTICAL,0,100,0,1000);
        this.add(this.wertykalnie,BorderLayout.EAST);
        this.add(this.horyzontalnie,BorderLayout.SOUTH);
        this.deltax= 0;
        this.deltay= 0;
        this.licznik=0;
        this.zabiciBarbarzyncy=0;
        this.licznikBarbarzyncow=10;
        this.wektorOsad=2047;
        this.alphaAwarii=0;
        this.licznikKomunikat=0;
    }
    /**
     *Method is drawing every Postac on the screen and deletes every killed Postac
     * form control lists
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g){
        g.clearRect(0, 0, this.getWidth(),this.getHeight());
        g.drawImage(this.mapa, 0-this.deltax, 0-this.deltay, this);
        Iterator<Handlowiec> iteratorHandlowiec= this.handlowcy.iterator();
        while(iteratorHandlowiec.hasNext())
        {
            Handlowiec kupiec= iteratorHandlowiec.next();
            if(kupiec.status==0)
            {
                iteratorHandlowiec.remove();
                continue;
            }
            if(kupiec.isWidoczna()) 
            {
                g.setColor(new Color(255,0,0,this.alphaAwarii));
                if(kupiec.isAktywny()==false) 
                    g.fillOval(kupiec.getX()-kupiec.getWymiarX()-this.deltax, 
                        kupiec.getY()-kupiec.getWymiarY()-this.deltay,
                        kupiec.getWymiarX()*2, kupiec.getWymiarY()*2);
                g.drawImage(this.handlarz[kupiec.getKierunek()], 
                    kupiec.getX()-kupiec.getWymiarX()-this.deltax, 
                    kupiec.getY()-kupiec.getWymiarY()-this.deltay-5, this);
            }
        }
        Iterator<Legion> iteratorLegion=this.legiony.iterator();
        while(iteratorLegion.hasNext())
        {
            Legion zenek = iteratorLegion.next();
            if((((zenek.getBanda()!=null)&&(zenek.getBanda().status==0))
                    ||(zenek.getBanda()==null))
                    &&(this.wolniBarbarzyncy.isEmpty()==false))
            {
                Iterator<Barbarzyncy> iterator= this.wolniBarbarzyncy.iterator();
                if(iterator.hasNext()) zenek.okreslCel(iterator.next());
                iterator.remove();
            }
            if(zenek.status==0)
            {
                if(zenek.getBanda()!=null) this.wolniBarbarzyncy.add(zenek.getBanda());
                iteratorLegion.remove();
                continue;
            }
            if(zenek.isWidoczna())
            {
                g.drawImage(this.legion[zenek.getKierunek()],
                        zenek.getX()-zenek.getWymiarX()-this.deltax,
                        zenek.getY()-zenek.getWymiarY()-this.deltay-5, this);
            }
        }
        Iterator<Barbarzyncy> iteratorBarbarzyncy=this.barbarzyncy.iterator();
        while(iteratorBarbarzyncy.hasNext())
        {
            Barbarzyncy darki= iteratorBarbarzyncy.next();
            if(darki.status==0)
            {
                iteratorBarbarzyncy.remove();
                if(this.wolniBarbarzyncy.contains(darki)) 
                    this.wolniBarbarzyncy.remove(darki);
                if(darki.getLiczebnosc()>0) this.zabiciBarbarzyncy++;
                continue;
            }
            if((darki.status==5)&&(darki.getCel()==0)) darki.nowyCel(this.wektorOsad);
            if(darki.isWidoczna())
            {
                g.drawImage(this.barbarzynca[darki.getKierunek()],
                        darki.getX()-darki.getWymiarX()-this.deltax,
                        darki.getY()-darki.getWymiarY()-this.deltay-5, this);
            }
        }
        if(this.barbarzyncy.size()<this.legiony.size()) this.licznikBarbarzyncow=1;
        g.setColor(new Color(128,128,255,100));
        if(this.wybranaOsada!=null) g.fillRect(this.wybranaOsada.getX()-40-this.deltax, 
                this.wybranaOsada.getY()-40-this.deltay, 80, 80);
        g.setColor(new Color(255,128,0,100));
        if(this.wybranaPostac!=null) g.fillRect(this.wybranaPostac.getX()-this.wybranaPostac.getWymiarX()-this.deltax, 
                this.wybranaPostac.getY()-this.wybranaPostac.getWymiarY()-this.deltay,
                this.wybranaPostac.getWymiarX()*2, this.wybranaPostac.getWymiarY()*2);
        g.setColor(new Color(255,255,0,100));
        if(this.wybranaOsadaCel!=null) g.fillRect(this.wybranaOsadaCel.getX()-40-this.deltax, 
                this.wybranaOsadaCel.getY()-40-this.deltay, 80, 80);
        if(this.licznikKomunikat>0)
        {
            g.setColor(new Color(0,0,0));
            g.setFont(new Font(this.getFont().getName(),Font.BOLD,20));
            g.drawString(komunikat, 40, this.getHeight()-80);
            this.licznikKomunikat--;
        }
    }
    /**
     * Function is controling the game
     * @return killed Barbarzyncy
     * @throws InterruptedException  
     */
    public int graj() throws InterruptedException{
        this.addMouseListener(this);
        this.horyzontalnie.addAdjustmentListener(this);
        this.wertykalnie.addAdjustmentListener(this);
        while(this.wektorOsad>0){
            Thread.sleep(40);
            if(licznik==25)
            {
                Iterator<Osada> iterator= this.osady.iterator();
                while(iterator.hasNext())
                {
                    Osada temp= iterator.next();
                    if((this.wektorOsad&temp.getId())==0) continue;
                    if(temp.isZniszczona()) 
                    {
                        this.wektorOsad=this.wektorOsad&(~temp.getId());
                        continue;
                    }
                    if(!(temp.getGosc() instanceof Handlowiec)) 
                        temp.sprawdzWaznosc();
                }
                this.licznikBarbarzyncow--;
                if(this.licznikBarbarzyncow==0)
                {
                    if(this.barbarzyncy.size()<20) this.nowyBarbarzynca();
                    Random los= new Random();
                    this.licznikBarbarzyncow=los.nextInt(20)+5;
                }
                licznik=0;
                if(this.alphaAwarii==0) this.alphaAwarii=150;
                else this.alphaAwarii=0;
            }
            if(this.wybranaPostac!=null&&this.wybranaPostac.status==0)
            {
                this.wybranaPostac=null;
                this.wybranaOsadaCel=null;
                this.panel.wyswietlPusty();
            }
            if((this.wybranaPostac!=null)&&(this.wybranaPostac.status==4||this.wybranaPostac.status==5))
            {
                this.wybranaPostac=null;
                this.wybranaOsada=this.wybranaOsadaCel;
                this.wybranaOsadaCel=null;
                this.okreslProdISkup(this.wybranaOsada);
                this.panel.wyswietlOsada(this.wybranaOsada);
            }
            
            this.repaint();
            if(this.wybranaOsada!=null) this.panel.wyswietlOsada(this.wybranaOsada);
            if(this.wybranaPostac!=null) this.panel.wyswietlPostac(this.wybranaPostac);
            this.horyzontalnie.setMaximum(this.mapa.getWidth()-this.getWidth()+120);
            this.wertykalnie.setMaximum(this.mapa.getHeight()-this.getHeight()+120);
            licznik++;
        }
        return this.zabiciBarbarzyncy;
    }

    /**
     *Method checks possibility of creating new Legion
     */
    public void mozliwoscUtworzeniaLegionu()
    {
        if((this.wolniBarbarzyncy.isEmpty())||(this.osady.get(10).isZniszczona())) 
            this.panel.getWysLegionButton().setEnabled(false);
        else this.panel.getWysLegionButton().setEnabled(true);
    }
    /**
     *Method is changing deltax and deltay when scrolls are moved
     * @param ae AdjustmentEvent
     */
    @Override
    public void adjustmentValueChanged(AdjustmentEvent ae) {
        this.deltax=this.horyzontalnie.getValue();
        this.deltay=this.wertykalnie.getValue();
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(PanelUzytkownika panel) {
        this.panel = panel;
    }

    /**
     *Creates String describing what osada sells and buys
     * @param osada Osada to describe
     */
    public void okreslProdISkup(Osada osada)
    {
       String temp= "<html>Produkcja: ";
       for(int i=0;i<8;i++)
       {
        int warunek=osada.getProdukcja()&(1<<i);
        switch(warunek){
            case 1:
                temp+="filozofowie ";
                break;
            case 2:
                temp+= "tkaniny ";
                break;
            case 4:
                temp+= "chleb ";
                break;
            case 8:
                temp+= "wino ";
                break;
            case 16:
                temp+= "złoto ";
                break;
            case 32:
                temp+= "serbro ";
                break;
            case 64:
                temp+= "ruda ";
                break;
            case 128:
                temp+= "drewno ";
                break;
            }
        }
      temp+="<br>Skup: ";
      for(int i=0;i<8;i++)
      {
        int warunek=osada.getSkup()&(1<<i);
        switch(warunek){
            case 1:
                temp+="filozofowie ";
                break;
            case 2:
                temp+= "tkaniny ";
                break;
            case 4:
                temp+= "chleb ";
                break;
            case 8:
                temp+= "wino ";
                break;
            case 16:
                temp+= "złoto ";
                break;
            case 32:
                temp+= "serbro ";
                break;
            case 64:
                temp+= "ruda ";
                break;
            case 128:
                temp+= "drewno ";
                break;
        }
        }
       temp+="</html>";
       this.panel.getSurowceOsady().setText(temp);
    }
    /**
     *Method is checking where user clicked on map
     * @param me Mouse Event
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        Iterator<Osada> iterator = osady.iterator();
        this.wybranaOsada=null;
        while(iterator.hasNext())
        {
            Osada osada=iterator.next();
            if(osada.getX()-this.deltax<me.getX()+40
                    &&osada.getX()-this.deltax>me.getX()-40)
                if(osada.getY()-this.deltay<me.getY()+40
                        &&osada.getY()-this.deltay>me.getY()-40){
                    this.okreslProdISkup(osada);
                    this.panel.wyswietlOsada(osada);
                    this.wybranaOsada=osada;
                    break;
                }
        }
        this.wybranaOsadaCel= null;
        this.wybranaPostac= null;
        if(this.wybranaOsada==null) 
        {
            Iterator<Handlowiec> iteratorHandlowiec= this.handlowcy.iterator();
            while(iteratorHandlowiec.hasNext())
            {
                Handlowiec gostek= iteratorHandlowiec.next();
                if(gostek.isWidoczna()==false) continue;
                if(gostek.getX()-this.deltax<me.getX()+gostek.getWymiarX()
                    &&gostek.getX()-this.deltax>me.getX()-gostek.getWymiarX())
                if(gostek.getY()-this.deltay<me.getY()+gostek.getWymiarY()
                        &&gostek.getY()-this.deltay>me.getY()-gostek.getWymiarY()){
                    this.panel.wyswietlPostac(gostek);
                    this.wybranaPostac=gostek;
                    this.wybranaOsadaCel=this.osadaPoID(gostek.getCel());
                    break;
                }
            }
            if(this.wybranaPostac==null){
            Iterator<Legion> iteratorLegion= this.legiony.iterator();
            while(iteratorLegion.hasNext())
            {
                Legion gostek= iteratorLegion.next();
                if(gostek.isWidoczna()==false) continue;
                if(gostek.getX()-this.deltax<me.getX()+gostek.getWymiarX()
                    &&gostek.getX()-this.deltax>me.getX()-gostek.getWymiarX())
                if(gostek.getY()-this.deltay<me.getY()+gostek.getWymiarY()
                        &&gostek.getY()-this.deltay>me.getY()-gostek.getWymiarY()){
                    this.panel.wyswietlPostac(gostek);
                    this.wybranaPostac=gostek;
                    this.wybranaOsadaCel=this.osadaPoID(gostek.getCel());
                    break;
                }
            }   
            }
            if(this.wybranaPostac==null)
            {
            Iterator<Barbarzyncy> iteratorBarbarzyncy= this.barbarzyncy.iterator();
            while(iteratorBarbarzyncy.hasNext())
            {
                Barbarzyncy gostek= iteratorBarbarzyncy.next();
                if(gostek.isWidoczna()==false) continue;
                if(gostek.getX()-this.deltax<me.getX()+gostek.getWymiarX()
                    &&gostek.getX()-this.deltax>me.getX()-gostek.getWymiarX())
                if(gostek.getY()-this.deltay<me.getY()+gostek.getWymiarY()
                        &&gostek.getY()-this.deltay>me.getY()-gostek.getWymiarY()){
                    this.panel.wyswietlPostac(gostek);
                    this.wybranaPostac=gostek;
                    this.wybranaOsadaCel=this.osadaPoID(gostek.getCel());
                    break;
                }
            }
            }
        }
        if((this.wybranaOsada==null)&&(this.wybranaPostac==null)) 
        {
            this.panel.wyswietlPusty();
        }
    }
    
    /**
     *Method lets to show message in the left corner of map
     * @param tresc Message to show
     */
    public void wpiszKomunikat(String tresc)
    {
        this.komunikat=tresc;
        this.licznikKomunikat=100;
    }
    
    /**
     *
     * @param id This is the id we're looking for
     * @return Osada which has ID id
     */
    public Osada osadaPoID(int id){
        Iterator<Osada> iterator= this.osady.iterator();
        while(iterator.hasNext())
        {
            Osada miastko = iterator.next();
            if(miastko.getId()==id) return miastko;
        }
        return null;
    }
    /**
     *
     * @return true when Legion was created
     */
    public boolean nowyLegion(){
        Stolica stoleczne=(Stolica) this.osady.get(10);
        if(stoleczne.isGnebiona())
        {
            stoleczne.getGosc().gin();
            Random los= new Random();
            stoleczne.uciskaj(los.nextInt(3000)+1);
            return true;
        }
        else
        {
            Legion wojownicy= stoleczne.wyslijLegion(this.legion[0].getWidth(),
                    this.legion[0].getHeight());
            if(this.legiony.add(wojownicy))
            {
                if(this.wolniBarbarzyncy.isEmpty()==false)
                {
                    wojownicy.okreslCel(this.wolniBarbarzyncy.get(0));
                    this.wolniBarbarzyncy.remove(0);
                }
                else
                    wojownicy.okreslCel(this.barbarzyncy.get(0));
                wojownicy.start();
                this.mozliwoscUtworzeniaLegionu();
                return true;
            }
            else
                return false;
        }
    }
    /**
     *
     * @return true if Barbarzynca was created
     */
    public boolean nowyBarbarzynca()
    {
        Random los=new Random();
        int start[]={0,21,54,47};
        int wylosowanyStart=los.nextInt(4);
        Iterator<Osada> iterator= this.osady.iterator();
        Osada najblizsza=null;
        double odleglosc=10000;
        while(iterator.hasNext())
        {
            Osada temp=iterator.next();
            int warunek= temp.getId()&this.wektorOsad;
            if(warunek>0)
            {
                double a1=temp.getX()-this.drogi[start[wylosowanyStart]].getxPoczatek();
                double a2=temp.getY()-this.drogi[start[wylosowanyStart]].getyPoczatek();
                double tempOdleglosc= Math.sqrt(a1*a1+a2*a2);
                if(tempOdleglosc<odleglosc)
                {
                    odleglosc=tempOdleglosc;
                    najblizsza=temp;
                }
            }
        }
        if(najblizsza!=null)
        {
            Barbarzyncy tomek= new Barbarzyncy(0,0,this.barbarzynca[0].getWidth(),
                this.barbarzynca[0].getHeight(),0);
            tomek.setSzlak(this.drogi[start[wylosowanyStart]]);
            tomek.setCel(najblizsza.getId());
            if((this.barbarzyncy.add(tomek))&&(this.wolniBarbarzyncy.add(tomek)))
            {
                this.mozliwoscUtworzeniaLegionu();
                tomek.start();
                return true;
            }
            else return false;
        }
        else return false;
    }
    /**
     *
     * @return true when Handlowiec was created
     */
    public boolean nowyHandlowiec()
    {
        if(this.wybranaOsada!=null)
        {
            Handlowiec adam = new Handlowiec(this.wybranaOsada.getX(),
                    this.wybranaOsada.getY(),this.handlarz[0].getWidth(),
                    this.handlarz[0].getHeight(),this.wybranaOsada);
            if(this.handlowcy.add(adam))
            {
                this.wybranaOsada.setGosc(adam);
                adam.start();
                this.wpiszKomunikat("Utworzono handlowca w osadzie: " + 
                              this.wybranaOsada.getNazwa());
                return true;
            }
            else return false;
        }
        else
        {
            Osada najwiecej=null;
            Iterator<Osada> iterator= this.osady.iterator();
            while(iterator.hasNext())
            {
                Osada temp=iterator.next();
                if(temp.isZniszczona()||temp.isGnebiona()
                        ||temp.getGosc()!=null) continue;
                if(najwiecej==null) najwiecej=temp;
                else
                {
                    if(temp.getStanMagazynu().getObciazenie()>
                            najwiecej.getStanMagazynu().getObciazenie())
                        najwiecej=temp;
                }
            }
            if(najwiecej!=null)
            {
                 Handlowiec adam = new Handlowiec(najwiecej.getX(),
                    najwiecej.getY(),this.handlarz[0].getWidth(),
                    this.handlarz[0].getHeight(),najwiecej);
                 if(this.handlowcy.add(adam))
                    {
                      najwiecej.setGosc(adam);
                      adam.start();
                      this.wpiszKomunikat("Utworzono handlowca w osadzie: " + 
                              najwiecej.getNazwa());
                       return true;
                 }
                    else return false;
                 }
            else 
            {
                this.wpiszKomunikat("Wszystkie miasta zajęte. Spróbuj później.");
                return false;
            }
        }
    }
    /**
     *Changes destination of Handlowiec
     */
    public void zmienCelHandlowiec()
    {
        int temp=this.wybranaPostac.getCel()*2;
        if(temp>1024) temp=1;
        this.wybranaPostac.setCel(temp);
        this.wybranaOsadaCel=this.osadaPoID(this.wybranaPostac.getCel());
    }
    /**
     *Nothing is happening
     * @param me MouseEvent
     */
    @Override
    public void mousePressed(MouseEvent me) {
    }

    /**
     *Nothing is happening
     * @param me MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent me) {
    }

    /**
     *Nothing is happening
     * @param me MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    /**
     *Nothing is happening
     * @param me MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     *Logic of buttons in panel
     * @param ae ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "prod":
                this.wybranaOsada.wyprodukuj();
                break;
            case "handl":
                this.nowyHandlowiec();
                break;
            case "awaria":
                if(this.wybranaPostac.isAktywny()) this.wybranaPostac.stop();
                else this.wybranaPostac.aktywuj();
                break;
            case "usun":
                this.wybranaPostac.gin();
                this.wybranaPostac=null;
                this.wybranaOsadaCel=null;
                this.panel.wyswietlPusty();
                break;
            case "cel":
                this.zmienCelHandlowiec();
                break;
            case "legion":
                this.nowyLegion();
                break;
        }
    }
}
