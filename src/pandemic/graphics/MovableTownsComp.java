package pandemic.graphics;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pandemic.Town;
import pandemic.player.Player;

public class MovableTownsComp {
    private JPanel panel;
    private ArrayList<Town> towns;
    private JLabel displayer;
    private Player player;

    public MovableTownsComp(JPanel panel, ArrayList<Town> towns, JLabel displayer, Player player) {
        this.panel = panel;
        this.towns = towns;
        this.displayer = displayer;
        this.player = player;

        for (Town t : this.towns) {
            JButton btn = new JButton(t.getName());
            townButton instance = new townButton(this.panel, t, this.player, this.displayer);
            btn.addActionListener(instance);
            this.panel.add(btn);
        }
    }
}
