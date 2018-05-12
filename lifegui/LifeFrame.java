package lifegui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LifeFrame extends JFrame implements Runnable {

  // constante
  private static final int SLAAPTIJD = 100;

  // eigen attributen
  private Thread spel = null;
  private LifePanel lifePanel = null; // spel Game of Life met grafische weergave
  private int generatie = 0;  // teller voor het aantal generaties

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JButton volgendeKnop;
  private JButton startStopKnop;
  private JLabel genLabel;
  private JButton nieuwKnop;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          LifeFrame frame = new LifeFrame();
          frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public LifeFrame() {
    initialize();
    mijnInit();
  }
  private void initialize() {
    setTitle("Game of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 557, 503);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    contentPane.add(getVolgendeKnop());
    contentPane.add(getStartStopKnop());
    contentPane.add(getGenLabel());
    contentPane.add(getNieuwKnop());
  }

  private JButton getVolgendeKnop() {
    if (volgendeKnop == null) {
    	volgendeKnop = new JButton("Ga naar volgende generatie");
    	volgendeKnop.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    	    volgendeKnopAction();
    	  }
    	});
    	volgendeKnop.setBounds(15, 347, 278, 31);
    }
    return volgendeKnop;
  }
  private JButton getStartStopKnop() {
    if (startStopKnop == null) {
    	startStopKnop = new JButton("Laat tijd lopen");
    	startStopKnop.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    	    startStopKnopAction();
    	  }
    	});
    	startStopKnop.setBounds(15, 388, 278, 31);
    }
    return startStopKnop;
  }
  private JLabel getGenLabel() {
    if (genLabel == null) {
    	genLabel = new JLabel("Generatie: ");
    	genLabel.setBounds(332, 343, 171, 31);
    }
    return genLabel;
  }
  private JButton getNieuwKnop() {
    if (nieuwKnop == null) {
    	nieuwKnop = new JButton("Nieuw spel");
    	nieuwKnop.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent e) {
    	    nieuwKnopAction();
    	  }
    	});
    	nieuwKnop.setBounds(15, 431, 278, 31);
    }
    return nieuwKnop;
  }

  private void mijnInit() {
    lifePanel = new LifePanel();
    getContentPane().add(lifePanel);
    lifePanel.setLocation(5,5);
  }

  /**
   * Toont de volgende generatie
   */
  private void volgendeKnopAction() {
    lifePanel.volgende();
    lifePanel.repaint();
    generatie++;
    genLabel.setText("Generatie: " + generatie);
  }

  /**
   * Start of stop de animatie.
   */
  private void startStopKnopAction() {
    if(startStopKnop.getText().equals("Laat tijd lopen")) {
      startStopKnop.setText("Stop");
      start();
    }
    else {
      startStopKnop.setText("Laat tijd lopen");
      stop();
    }
  }

  /**
   * Stopt zonodig de animatie en vervangt dan het huidige
   * panel met cellen door een nieuw, leeg panel
   */
  private void nieuwKnopAction() {
    stop();
    startStopKnop.setText("Laat tijd lopen");
    lifePanel.wis();
    lifePanel.repaint();
    generatie = 0;
    genLabel.setText("Generatie: " + generatie);
  }

  /**
   * Start de animatie.
   */
  public void start() {
    if(spel == null) {
      spel = new Thread(this);
      spel.start();
    }
  }

  /**
   * Stopt de animatie.
   */
  public void stop() {
    if(spel != null) {
      spel = null;
    }
  }

  /**
   * Animatie van Game of Life.
   * Op het scherm wordt op gezette tijden een nieuwe generatie
   * cellen getoond.
   */
  public void run() {
    while(spel != null) {
      lifePanel.volgende();
      lifePanel.repaint();
      generatie++;
      genLabel.setText("Generatie: " + generatie);
      try {
        Thread.sleep(SLAAPTIJD);
      }
      catch (InterruptedException ex) {}
    }
  }
}
