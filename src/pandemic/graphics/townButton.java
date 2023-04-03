package pandemic.graphics;

import javax.swing.*;

import pandemic.Town;
import pandemic.player.Player;
import java.awt.event.*;

public class townButton implements ActionListener {
    private JPanel panel;
    private Town town;
    private Player player;
    private JLabel comp;

    public townButton(JPanel panel, Town town, Player player, JLabel comp) {
        this.panel = panel;
        this.player = player;
        this.town = town;
        this.comp = comp;
    }

    public void actionPerformed(ActionEvent e){
        this.player.setTown(town);
        System.out.println(this.player.getTownName());
        this.comp.setText(this.player.getTownName());
        panel.repaint();
    }
}
