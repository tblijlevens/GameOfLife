package lifegui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import life.Life;

/**
 * Grafische weergave van een spel Game of Life.
 * Het veld is verdeeld in cellen. Een cel kan door een muisklik
 * van status wisselen tussen bevolkt en onbevolkt.
 * Voor het tekenen wordt gebruik gemaakt van dubbele buffering.
 * @author Open Universiteit Nederland
 */
public class LifePanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Afmeting van een cel op het scherm.
   */
  public static final int CELMAAT = 12;

  //attributen
  private int aantalRijen = Life.HOOGTE;  // aantal rijen van het veld
  private int aantalKolommen = Life.BREEDTE;    // aantal kolommen van het veld
  private Life life = new Life();  // spel Game of Life

  /**
   * Initialiseert een spel.
   */
  public LifePanel() {
    // bereken size
    int breedte = 1 + (CELMAAT + 1) * Life.BREEDTE;
    int hoogte = 1 + (CELMAAT + 1) * Life.HOOGTE;
    this.setSize(breedte, hoogte);
    this.addMouseListener(new MuisLuisteraar());
    this.setDoubleBuffered(true);
  }

  /**
   * Het veld en de cellen worden getekend.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.gray);
    g.fillRect(0, 0, getWidth(), getHeight());
    // teken cellen
    for (int i = 0; i < aantalRijen; i++) {
      for (int j = 0; j < aantalKolommen; j++) {
        if(!life.isBevolkt(i, j)) {
          g.setColor(Color.white);
        }
        else {
          g.setColor(Color.green);
        }
        g.fillRect(1 + (CELMAAT + 1) * j, 1 +(CELMAAT + 1) * i, CELMAAT, CELMAAT);
      }
    }
  }

  /**
   * Tekent de volgende generatie cellen.
   */
  public void volgende() {
    life.nieuweGeneratie();
    repaint();
  }

  /**
   * Wis het scherm en tekent een nieuw spel.
   */
  public void wis() {
    life = new Life();
    repaint();
  }

  /**
   * Event handling klasse voor muisklik op cel.
   */
  public class MuisLuisteraar extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      // wissel selectie van cel
      int j = e.getX()/(CELMAAT + 1);
      int i = e.getY()/(CELMAAT + 1);
      life.wisselBevolking(i, j);
      repaint();
    }
  }
}
