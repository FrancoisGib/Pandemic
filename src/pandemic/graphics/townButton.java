package pandemic.graphics;

import javax.swing.*;
import javax.swing.event.*;

import pandemic.Town;
import pandemic.player.Player;

import java.awt.event.*;

public class townButton implements ActionListener {
    private Main main;
    private Town town;
    private Player player;

    public townButton(Main main, Town town, Player player) {
        this.main = main;
        this.player = player;
        this.town = town;
    }

    public void actionPerformed(ActionEvent e){
        this.player.setTown(town);
        System.out.println(this.player.getTownName());
        JPanel panel = new JPanel();
        main.add(panel);
        main.revalidate();
        main.repaint();
        
    }
}
