package pandemic.graphics;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;

import pandemic.Map;
import pandemic.NoSuchTownException;
import pandemic.Town;
import pandemic.player.Player;
import pandemic.player.Role;

public class Main extends JFrame {
    public Main() {
        super("Test jFrame");
        Map map = new Map();
        try {
			map.setMapWithJSON("./json/villes48.json");
		}
		catch(NoSuchTownException e) {
			System.out.println("Error with the towns");
		}
        catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
        ArrayList<Town> towns = map.getTowns();
        Player p = new Player("Player", Role.EXPERT);
        p.setTown(towns.get(0));
        JPanel pan = new JPanel();
        JPanel totalGui = new JPanel();
        pan.setSize(300, 720);
        pan.setLocation(0, 0);
        pan.setBackground(Color.BLACK);
        totalGui.setLayout(null);

        JLabel playerTown = new JLabel("<html><p>"+ p.getTownName() + "</p></html>", SwingConstants.CENTER);
        playerTown.setBounds(1000, 20, 200, 100);
        totalGui.add(playerTown);

        ArrayList<Town> movableTowns = new ArrayList<Town>();
        if (p.getRole() == Role.GLOBETROTTER) {
            movableTowns = map.getTowns();
        }
        else {
            movableTowns = p.getTown().getNeighbors();
        }
        
        new MovableTownsComp(pan, movableTowns, playerTown, p);
        totalGui.add(pan);
        
        Graphs graph = new Graphs("dsqdsqddfsdq \n fdnds", this.getGraphics());
        graph.setBounds(700, 500, 500, 500);
        JLabel l = new JLabel("<html><p>"+ map.toString() + "</p></html>", SwingConstants.CENTER);
        l.setBounds(320, 20, 700, 700);
        totalGui.add(l);
        totalGui.add(graph);
        this.add(totalGui);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1280, 720);
        System.out.println(p.getTownName());
    }
    
    
    public static void main(String []args) {
        Main main = new Main();
    }
}


