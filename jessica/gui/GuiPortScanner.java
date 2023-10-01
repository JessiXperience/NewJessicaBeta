package jessica.gui;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GuiPortScanner extends JFrame {
  private JButton a;
  
  private JLabel b;
  
  private JLabel c;
  
  private JLabel d;
  
  private JLabel e;
  
  private JTextField f;
  
  private JTextField g;
  
  private JTextField h;
  
  private TextArea i;
  
  private TextField j;
  
  public GuiPortScanner() {
    a();
  }
  
  private void a() {
    this.f = new JTextField();
    this.g = new JTextField();
    this.b = new JLabel();
    this.h = new JTextField();
    this.c = new JLabel();
    this.d = new JLabel();
    this.e = new JLabel();
    this.a = new JButton();
    this.i = new TextArea();
    this.j = new TextField();
    this.b.setText(":");
    this.c.setText("-");
    this.d.setText("IP");
    this.e.setText("Port");
    this.a.setText("Start");
    this.a.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiPortScanner.this.a(evt);
          }
        });
    this.j.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiPortScanner.this.b(evt);
          }
        });
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.f, -2, 140, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.b).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.g, -2, 65, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.c).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.h, -2, 65, -2)).addGroup(layout.createSequentialGroup().addGap(73, 73, 73).addComponent(this.d).addGap(140, 140, 140).addComponent(this.e))).addGap(0, 86, 32767)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.i, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.a, -2, 122, -2).addGap(99, 99, 99).addComponent(this.j, -1, -1, 32767))))).addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.d).addComponent(this.e)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.f, -2, -1, -2).addComponent(this.g, -2, -1, -2).addComponent(this.b).addComponent(this.h, -2, -1, -2).addComponent(this.c)).addGap(18, 18, 18).addComponent(this.a)).addComponent(this.j, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.i, -1, 182, 32767).addContainerGap()));
    pack();
  }
  
  private void a(ActionEvent actionEvent) {
    this.i.setText("");
    for (int i = Integer.parseInt(this.g.getText()); i <= Integer.parseInt(this.h.getText()); i++) {
      if (a(this.f.getText(), i, 150))
        this.i.append("Open port:" + i + "\n"); 
      this.j.setText("Port:" + i);
    } 
  }
  
  public boolean a(String s, int n, int n2) {
    try {
      Socket socket = new Socket();
      socket.connect(new InetSocketAddress(s, n), n2);
      socket.close();
      return true;
    } catch (Exception ex) {
      return false;
    } 
  }
  
  private void b(ActionEvent actionEvent) {}
  
  public static void main(String[] array) {
    try {
      UIManager.LookAndFeelInfo[] installedLookAndFeels;
      for (int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length, i = 0; i < length; i++) {
        UIManager.LookAndFeelInfo lookAndFeelInfo = installedLookAndFeels[i];
        if ("Nimbus".equals(lookAndFeelInfo.getName())) {
          UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex2) {
      Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, (String)null, ex2);
    } catch (IllegalAccessException ex3) {
      Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, (String)null, ex3);
    } catch (UnsupportedLookAndFeelException ex4) {
      Logger.getLogger(GuiPortScanner.class.getName()).log(Level.SEVERE, (String)null, ex4);
    } 
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            (new GuiPortScanner()).setVisible(true);
          }
        });
  }
}
