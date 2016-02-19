/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *Main class of project
 * @author Ślimak
 */
public class StaroSym{

    /**
     * Loads best scores form wyniki.xml, creates new window, starts the game
     * , shows messagebox to type name, adds result to best scores list, shows
     * best scores list in messagebox, saves list to wyniki.xml and ends game
     * @param args the command line arguments
     * @throws InterruptedException  
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ArrayList<Wynik> wyniki= new ArrayList<>();
        boolean listaWynikow=true;
        try{
            XMLDecoder d = new XMLDecoder(
                     new BufferedInputStream(
                       new FileInputStream("wyniki.xml")));
            Wynik temp=(Wynik) d.readObject();
            while(temp!=null)
            {
                wyniki.add(temp);
                temp=(Wynik) d.readObject();
            }
        }
        catch(Exception e)
        {
            listaWynikow=false;
        }
        JFrame window = new JFrame("StaroSym");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1024, 600);
        window.setMinimumSize(new Dimension(1024,600));
        long start= 0;
        Imperium m;
        int pokonano=0;
        try {
            m = new Imperium();
            window.add(m,BorderLayout.CENTER);
            PanelUzytkownika prawyPanel = new PanelUzytkownika(m);
            m.setPanel(prawyPanel);
            window.add(prawyPanel,BorderLayout.EAST);
            window.setVisible(true);
            start= System.currentTimeMillis();
            pokonano=m.graj();
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(window, "Brakuje "
                   + "plików graficznych niezbędnych do działania gry.",
                   "Brak plików grafiki", JOptionPane.ERROR_MESSAGE);
           JOptionPane.showMessageDialog(window, ex.getMessage(),
                   "Brak plików grafiki", JOptionPane.ERROR_MESSAGE);
                   
            System.exit(1);
        }
        long stop= System.currentTimeMillis();
        int minuty=(int)((stop-start)/1000);
        int sekundy= minuty%60;
        minuty/=60;
        String imie=JOptionPane.showInputDialog(window, "Twój czas gry to: "
                + minuty + " minut i " 
                + sekundy + " sekund. W tym czasie pokonałeś "
                + pokonano + " plemion barbarzyńców. "
                + "\r\nPodaj swoje imię do listy "
                + "najlepszych wyników (zostaw puste "
                + "by wpisać się anonimowo)",
                "Podaj imię", JOptionPane.QUESTION_MESSAGE);
        if(imie.isEmpty()) imie= "Anonim";
        Wynik nowyWynik= new Wynik(imie,pokonano,minuty,sekundy);
        wyniki.add(nowyWynik);
        Collections.sort(wyniki);
        if(wyniki.size()>5) wyniki.remove(5);
        String temp= "<html><table><tr><td>Numer</td><td>Imię</td>"
                + "<td>Czas gry</td>"
                + "<td>Pokonane plemiona</td></tr>";
        for(int i=0;i<wyniki.size();i++)
        {
            temp+="<tr>" + "<td>"+(i+1)+"</td>" +wyniki.get(i) + "</tr>";
        }
        temp+= "</table></html>";
        JOptionPane.showMessageDialog(window, temp, "Lista najlepszych"
                + " wyników", JOptionPane.PLAIN_MESSAGE);
        XMLEncoder e;
        try {
            e = new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream("wyniki.xml")));
            for(int i=0;i<wyniki.size();i++)
            {
                e.writeObject(wyniki.get(i));
            }
            e.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StaroSym.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }     
}
