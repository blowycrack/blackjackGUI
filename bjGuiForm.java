import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyListener;

public class bjGuiForm {

    private JPanel mainPanel; // app
    private JTextField tetrako_ertek;

    private JLabel coin_egyenleg;
    private JLabel playerlapok_erteke;
    private JLabel dealerlapok_erteke;

    private JButton tetrako_gomb;
    private JButton Hitbutton;
    private JButton Tartbutton;
    private JLabel jatekos_kartyai;
    private JLabel dealer_kartyai;
    //----------------------------------
    private static double tet = 0.0;
    private static double coin = 100.0;
    private static boolean endround = false;
    private static boolean ingame = false;

    // Asztal létrehozása
    Asztal asztal = new Asztal();
    Asztal playerLapok = new Asztal();
    Asztal dealerLapok = new Asztal();
    ///
    // --------------------------------------- [ TÉT RAKÁS GOMB ABLAK ] ---------------------------------------
    public bjGuiForm() {
        //------------------------------------------------------------------------------------------------------
        // Beállítások
        //------------------------------------------------------------------------------------------------------
        tetrako_gomb.setEnabled(true);
        Hitbutton.setEnabled(false);
        Tartbutton.setEnabled(false);
        coin_egyenleg.setText("Pénzed: " + coin);
        //----------
        //------------------------------------------------------------------------------------------------------
        // Gombok (tétrakás, hit, stand)
        //------------------------------------------------------------------------------------------------------
        tetrako_gomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String stet;
                try {
                    stet = tetrako_ertek.getText(); // beolvasás
                    tet = Double.parseDouble(stet); // konvertálás
                    if (tet <= coin) {

                        coin_egyenleg.setText("Pénzed: " + coin);

                        start();
                        ingame = true;

                        tetrako_gomb.setEnabled(false);
                        Hitbutton.setEnabled(true);
                        Tartbutton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Nincs nálad elég pénz!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mainPanel, "A beírt érték, nem egy egész szám!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        //------------------------------------------------------------------------------------------------------
        Hitbutton.addActionListener(new ActionListener() { // HIT
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(playerLapok.kartyakErteke() < 21) {
                    playerLapok.draw(asztal);
                    jatekos_kartyai.setText(playerLapok.toString());
                    // jatek();
                    kartyaertekek();
                    atlepte();
                } else { // ha a player kártyái átlépik a 21-es összértéket nem kérhet többé// .
                    eredmenyhirdetes();
                }

            }
        });
        Tartbutton.addActionListener(new ActionListener() { // STAND
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                while ((dealerLapok.kartyakErteke() < 17) && !endround) {
                    dealerLapok.draw(asztal);

                    dealer_kartyai.setText(dealerLapok.toString());
                }

                kartyaertekek();
                eredmenyhirdetes();
            }
        });
    }

    // --------------------------------------- [ MAIN ABLAK ] ---------------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("BlackJack");
        frame.setContentPane(new bjGuiForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setSize(800, 600);
        // frame.pack();
        frame.setVisible(true);
        //----------------------------------------------------
    }
    // START
    public void start() {

        asztal.ujAsztal();
        asztal.kartyaKever();
        // 2 kártya játékosnak
        playerLapok.draw(asztal);
        playerLapok.draw(asztal);
        // 2 kártya dealernek
        dealerLapok.draw(asztal);
        dealerLapok.draw(asztal);


        jatekos_kartyai.setText(playerLapok.toString());
        dealer_kartyai.setText(dealerLapok.getKartya(0).toString());

        kartyaertekek();

        // szinezés

        jatekos_kartyai.setForeground(Color.GREEN);
        dealer_kartyai.setForeground(Color.RED);

        coin_egyenleg.setForeground(Color.WHITE);
        dealerlapok_erteke .setForeground(Color.CYAN);
        playerlapok_erteke.setForeground(Color.CYAN);

    }
    // összérték
    public void kartyaertekek() { // milye volt a játékosnak
        playerlapok_erteke.setText("Játékos kártyáinak összértéke: " + playerLapok.kartyakErteke());
        dealerlapok_erteke.setText("Dealer kártyáinak összértéke: ?");
    }
    // ellenörző
    public void atlepte() { // átlépte a 21-et a HIT gombbal :(.
        if(playerLapok.kartyakErteke() > 21) {
        eredmenyhirdetes(); }
    }
    public void mittudadealer() { // milye volt a Dealernek
        dealer_kartyai.setText(dealerLapok.toString());
        dealerlapok_erteke.setText("Dealer kártyáinak összértéke: " + dealerLapok.kartyakErteke());
    }
    // END
    public void eredmenyhirdetes() {

        // LEHETŐSÉGEK
        if (dealerLapok.kartyakErteke() == 21 || playerLapok.kartyakErteke() > 21
                || (dealerLapok.kartyakErteke() < 21 && dealerLapok.kartyakErteke() > playerLapok.kartyakErteke())) {
            coin = coin-tet;
            mittudadealer();
            JOptionPane.showMessageDialog(mainPanel, "Sajnálom! Vesztettél! Dealer kártyáinak összértéke: " + dealerLapok.kartyakErteke() + " / Te kártyáid összértéke: "
            + playerLapok.kartyakErteke() + " / Jelenlegi egyenleged: " + coin, "KIKAPTÁL!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (playerLapok.kartyakErteke() == 21 || dealerLapok.kartyakErteke() > 21 || playerLapok.kartyakErteke() > dealerLapok.kartyakErteke()) {
            coin = coin+tet;
            mittudadealer();
            JOptionPane.showMessageDialog(mainPanel, "Siker! Nyertél! Dealer kártyáinak összértéke: " + dealerLapok.kartyakErteke() + " / Te kártyáid összértéke: "
                    + playerLapok.kartyakErteke() + " / Jelenlegi egyenleged: " + coin, "NYERTÉL!", JOptionPane.INFORMATION_MESSAGE);

        }
        else if(playerLapok.kartyakErteke() == dealerLapok.kartyakErteke()) {
            mittudadealer();
            JOptionPane.showMessageDialog(mainPanel, "A kör egyenlő! Dealer lapjainak értéke: " + dealerLapok.kartyakErteke() + " / Te kártyáid összértéke: "
                    + playerLapok.kartyakErteke() + " / Jelenlegi egyenleged: " + coin, "Egyenlő!", JOptionPane.INFORMATION_MESSAGE);
        }


        playerLapok.kartyaLerak(asztal);
        dealerLapok.kartyaLerak(asztal);
        coin_egyenleg.setText("Pénzed: " + coin);
        ingame = false;
        tetrako_gomb.setEnabled(true);
        Hitbutton.setEnabled(false);
        Tartbutton.setEnabled(false);
        // System.out.println("-- [ Kör vége ] --");
    }
}
