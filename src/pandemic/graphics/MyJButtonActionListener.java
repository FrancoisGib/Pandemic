package pandemic.graphics;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
//1ère étape
public class MyJButtonActionListener implements ActionListener
{
  private static JTextField text;
  
  public static void main(String[] args) 
  {
    JFrame frame = new JFrame("Exemple ActionListener");
    text = new JTextField();
    text.setBounds(45,50,150,20);
    JButton btn = new JButton("Cliquez ici");
    btn.setBounds(70,100,100,30);
    MyJButtonActionListener instance = new MyJButtonActionListener();
    //2ème étape
    btn.addActionListener(instance);
    frame.add(btn);
    frame.add(text);
    frame.setSize(250,250);
    frame.setLayout(null);
    frame.setVisible(true);  
  }
  //3ème étape
  public void actionPerformed(ActionEvent e){
    text.setText("Welcome to WayToLearnX");
  }
}