/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starosym;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *Control and information panel for user
 * @author Ślimak
 */
public class PanelUzytkownika extends JPanel {
   private Imperium gra;
   private JPanel panelKontrolny;
   private JPanel panelInformacyjny;
   private JPanel pustyPanel;
   private JPanel panelOsada;
   private JPanel panelPostac;
   private JButton wysLegionButton;
   private JButton nowyHandlowiecButton;
   private JButton wyprodukujButton;
   private JButton zmienCelButton;
   private JButton awariaButton;
   private JLabel nazwaOsady;
   private JLabel nazwaPostaci;
   private JLabel bronPostaci;
   private JLabel magazynOsady;
   private JLabel parametryWozu;
   private JLabel celPodrozy;
   private JLabel predkoscPostaci;
   private JLabel skarbiecOsady;
   private JLabel ludnoscOsady;
   private JButton usunPostac;
   private JLabel surowceOsady;
   private JLabel goscOsady;
   private JLabel statusPostaci;
   /**
    * Creates panel and every thing that is on it
    * @param zarzadca Imperium used as a main game 
    */
   PanelUzytkownika(Imperium zarzadca){
       super(new BorderLayout());
       this.gra=zarzadca;
       this.panelKontrolny= new JPanel();
       this.wysLegionButton= new JButton("Wyślij legion");
       this.wysLegionButton.setActionCommand("legion");
       this.wysLegionButton.setEnabled(false);
       this.wysLegionButton.setToolTipText("Wyślij legion ze stolicy, "
               + "aby ubić nędznych barbarzyńców.");
       this.nowyHandlowiecButton= new JButton("Nowy handlowiec");
       this.nowyHandlowiecButton.setActionCommand("handl");
       this.nowyHandlowiecButton.setToolTipText("Utwórz handlowca");
       this.panelKontrolny.add(this.wysLegionButton);
       this.panelKontrolny.add(this.nowyHandlowiecButton);
       this.add(this.panelKontrolny,BorderLayout.SOUTH);
       this.awariaButton= new JButton("Awaria wozu");
       this.awariaButton.setActionCommand("awaria");
       this.awariaButton.setToolTipText("Napraw/popsuj wóż handlowca");
       this.zmienCelButton= new JButton("Zmień cel");
       this.zmienCelButton.setActionCommand("cel");
       this.zmienCelButton.setToolTipText("Zmienia cel podróży handlowca"
               + " na kolejną osadę.");
       this.wyprodukujButton= new JButton("Wyprodukuj");
       this.wyprodukujButton.setActionCommand("prod");
       this.wyprodukujButton.setToolTipText("Wyprodukuj surowce");
       this.bronPostaci= new JLabel("Bumerang");
       this.celPodrozy= new JLabel("Megapolis");
       this.magazynOsady= new JLabel("Magazyn: 200/3000");
       this.nazwaOsady= new JLabel("Rogopolis");
       this.parametryWozu= new JLabel("Wóz: 46/200");
       this.predkoscPostaci= new JLabel("Prędkość: 20");
       this.ludnoscOsady= new JLabel("Ludność: 1000");
       this.skarbiecOsady= new JLabel("Stan skarbca: 10000");
       this.usunPostac= new JButton("Usuń handlowca");
       this.usunPostac.setActionCommand("usun");
       this.usunPostac.setToolTipText("Usuwa handlowca z mapy raz na zawsze.");
       this.nazwaPostaci= new JLabel("Handlowiec: Mietek");
       this.surowceOsady= new JLabel("Surowce");
       this.goscOsady= new JLabel("Gość");
       this.statusPostaci= new JLabel("Status:");
       this.panelInformacyjny= new JPanel(new CardLayout());
       this.panelOsada= new JPanel(new GridLayout(0,1,0,10));
       this.panelOsada.add(this.nazwaOsady);
       this.panelOsada.add(this.goscOsady);
       this.panelOsada.add(this.ludnoscOsady);
       this.panelOsada.add(this.skarbiecOsady);
       this.panelOsada.add(this.surowceOsady);
       this.panelOsada.add(this.magazynOsady);
       this.panelOsada.add(this.wyprodukujButton);
       this.panelPostac= new JPanel(new GridLayout(0,1,10,10));
       this.panelPostac.add(this.nazwaPostaci);
       this.panelPostac.add(this.statusPostaci);
       this.panelPostac.add(this.bronPostaci);
       this.panelPostac.add(this.celPodrozy);
       this.panelPostac.add(this.predkoscPostaci);
       this.panelPostac.add(this.parametryWozu);
       this.panelPostac.add(this.usunPostac);
       this.panelPostac.add(this.zmienCelButton);
       this.panelPostac.add(this.awariaButton);
       this.pustyPanel= new JPanel(new BorderLayout());
       JLabel starosym = new JLabel("StaroSym",JLabel.CENTER);
       starosym.setFont(new Font(starosym.getName(),Font.PLAIN,32));
       this.pustyPanel.add(starosym,BorderLayout.CENTER);
       this.panelInformacyjny.add(this.pustyPanel,"Pusty");
       this.panelInformacyjny.add(this.panelPostac,"Postac");
       this.panelInformacyjny.add(this.panelOsada,"Osada");
       this.add(this.panelInformacyjny,BorderLayout.CENTER);
       this.wyprodukujButton.addActionListener(this.gra);
       this.awariaButton.addActionListener(this.gra);
       this.nowyHandlowiecButton.addActionListener(this.gra);
       this.usunPostac.addActionListener(this.gra);
       this.wysLegionButton.addActionListener(this.gra);
       this.zmienCelButton.addActionListener(this.gra);
   }
    /**
     *Method shows informations about miasto in panelInformacyjny
     * @param miasto Osada to show
     */
    public void wyswietlOsada(Osada miasto){
       this.nazwaOsady.setText("Osada: " + miasto.getNazwa());
       if(miasto.getGosc()!=null)
       {
           if(miasto.getGosc() instanceof Handlowiec)
               this.goscOsady.setText("<html>Handlowiec: " 
                       + miasto.getGosc().opisStanu());
           else if(miasto.getGosc() instanceof Legion)
               this.goscOsady.setText("<html>Legion: " 
                       + miasto.getGosc().opisStanu());
           else
               this.goscOsady.setText("<html>Barbarzyńca: " 
                       + miasto.getGosc().opisStanu());
       }
       else this.goscOsady.setText("Nikt nas nie odwiedził");
       this.ludnoscOsady.setText("Ludność: " + miasto.getLudnosc());
       this.skarbiecOsady.setText("Stan skarbca: " + miasto.getStanSkarbca());
       this.magazynOsady.setText("Stan magazynu: " + 
               miasto.getStanMagazynu().getObciazenie() + "/" + 
               miasto.getStanMagazynu().getPojemnosc());
       if(miasto.isZniszczona())
           this.ludnoscOsady.setText("OSADA ZNISZCZONA");
       if((miasto.isZniszczona())||(miasto.isGnebiona()))
       {
           this.nowyHandlowiecButton.setEnabled(false);
           this.wyprodukujButton.setEnabled(false);
       }
       else
       {
           this.wyprodukujButton.setEnabled(true);
           if(miasto.getGosc()==null) 
               this.nowyHandlowiecButton.setEnabled(true);
           else
               this.nowyHandlowiecButton.setEnabled(false);
       }
       CardLayout cl = (CardLayout)this.panelInformacyjny.getLayout();
       cl.show(this.panelInformacyjny, "Osada");
   }
   
    /**
     *Method shows informations about osoba in panelInformacyjny
     * @param osoba Postac to show
     */
    public void wyswietlPostac(Postac osoba){
       if(osoba instanceof Handlowiec){
           Handlowiec gostek=(Handlowiec)osoba;
           this.nazwaPostaci.setText("Handlowiec: " + gostek.getImie() + " " + gostek.getNazwisko());
           this.bronPostaci.setVisible(false);
           this.celPodrozy.setText("Cel podróży: " + this.gra.osadaPoID(gostek.getCel()).getNazwa());
           this.predkoscPostaci.setText("Prędkość: " + gostek.getPredkosc());
           this.parametryWozu.setVisible(true);
           this.parametryWozu.setText("Obiążenie wozu: " + gostek.getBryka().getObciazenie() + "/" + gostek.getBryka().getPojemnosc());
           this.awariaButton.setEnabled(true);
           if(osoba.isAktywny()) this.awariaButton.setText("Awaria");
           else this.awariaButton.setText("Naprawa");
           this.awariaButton.setVisible(true);
           this.usunPostac.setEnabled(true);
           this.usunPostac.setVisible(true);
           this.zmienCelButton.setEnabled(true);
           this.zmienCelButton.setVisible(true);
           CardLayout cl = (CardLayout)this.panelInformacyjny.getLayout();
           cl.show(this.panelInformacyjny, "Postac");
       }
       else if(osoba instanceof Legion){
           Legion gostek=(Legion)osoba;
           this.nazwaPostaci.setText("Legion");
           this.bronPostaci.setVisible(true);
           this.bronPostaci.setText("Broń: miecz");
           this.celPodrozy.setText("Cel podróży: " + this.gra.osadaPoID(gostek.getCel()).getNazwa());
           this.predkoscPostaci.setText("Prędkość: " + gostek.getPredkosc());
           this.parametryWozu.setVisible(false);
           this.awariaButton.setEnabled(false);
           this.awariaButton.setVisible(false);
           this.usunPostac.setEnabled(false);
           this.usunPostac.setVisible(false);
           this.zmienCelButton.setEnabled(false);
           this.zmienCelButton.setVisible(false);
           CardLayout cl = (CardLayout)this.panelInformacyjny.getLayout();
           cl.show(this.panelInformacyjny, "Postac");
       }
       else if(osoba instanceof Barbarzyncy){
           Barbarzyncy gostek=(Barbarzyncy)osoba;
           this.nazwaPostaci.setText("Plemię barbarzynców: " + gostek.getNazwa());
           this.bronPostaci.setVisible(true);
           this.bronPostaci.setText("Broń: " + gostek.getBron());
           this.celPodrozy.setText("Cel podróży: " + this.gra.osadaPoID(gostek.getCel()).getNazwa());
           this.predkoscPostaci.setText("Prędkość: " + gostek.getPredkosc());
           this.parametryWozu.setVisible(true);
           this.parametryWozu.setText("Liczebność: " + gostek.getLiczebnosc());
           this.awariaButton.setEnabled(false);
           this.awariaButton.setVisible(false);
           this.usunPostac.setEnabled(false);
           this.usunPostac.setVisible(false);
           this.zmienCelButton.setEnabled(false);
           this.zmienCelButton.setVisible(false);
           CardLayout cl = (CardLayout)this.panelInformacyjny.getLayout();
           cl.show(this.panelInformacyjny, "Postac");
       }
       this.statusPostaci.setText("<html>Status: " + osoba.opisStanu());
   }
   
    /**
     *Method shows empty panel in panelInformacyjny
     */
    public void wyswietlPusty()
   {
       CardLayout cl= (CardLayout)this.panelInformacyjny.getLayout();
       cl.show(this.panelInformacyjny, "Pusty");
       this.nowyHandlowiecButton.setEnabled(true);
   }

    /**
     * @return the wysLegionButton
     */
    public JButton getWysLegionButton() {
        return wysLegionButton;
    }

    /**
     * @return the nowyHandlowiecButton
     */
    public JButton getNowyHandlowiecButton() {
        return nowyHandlowiecButton;
    }

    /**
     * @return the surowceOsady
     */
    public JLabel getSurowceOsady() {
        return surowceOsady;
    }
}
