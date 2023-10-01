package jessica.gui;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GuiSubdomainBrute extends JFrame {
  private List<String> subs = new ArrayList<>();
  
  private int count = 0;
  
  private JButton jButton1;
  
  private JCheckBox jCheckBox1;
  
  private JLabel jLabel1;
  
  private JLabel jLabel2;
  
  private JLabel jLabel3;
  
  private JTextField jTextField1;
  
  private JTextField jTextField2;
  
  private JTextField jTextField3;
  
  private JTextField jTextField4;
  
  private TextArea textArea1;
  
  public GuiSubdomainBrute() {
    initComponents();
  }
  
  private void initComponents() {
    this.jTextField1 = new JTextField();
    this.jLabel1 = new JLabel();
    this.textArea1 = new TextArea();
    this.jButton1 = new JButton();
    this.jLabel2 = new JLabel();
    this.jTextField2 = new JTextField();
    this.jCheckBox1 = new JCheckBox();
    this.jTextField3 = new JTextField();
    this.jLabel3 = new JLabel();
    this.jTextField4 = new JTextField();
    setTitle("Subdomains Brute");
    this.jTextField1.setToolTipText("");
    this.jLabel1.setText("Path to list with Subdomains");
    this.jButton1.setText("Start");
    this.jButton1.setToolTipText("");
    this.jButton1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiSubdomainBrute.this.jButton1ActionPerformed(evt);
          }
        });
    this.jLabel2.setText("Target");
    this.jTextField2.setToolTipText("");
    this.jTextField2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiSubdomainBrute.this.jTextField2ActionPerformed(evt);
          }
        });
    this.jCheckBox1.setText("HTTP Responce code");
    this.jCheckBox1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiSubdomainBrute.this.jCheckBox1ActionPerformed(evt);
          }
        });
    this.jTextField3.setToolTipText("");
    this.jLabel3.setText("Threads");
    this.jTextField4.setText("0/0");
    this.jTextField4.setToolTipText("");
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.jTextField2)
            .addComponent(this.jButton1, -1, -1, 32767)
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.textArea1, -2, 341, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(this.jCheckBox1, -1, -1, 32767)
                .addComponent(this.jTextField3)
                .addComponent(this.jLabel3, -2, 55, -2)
                .addComponent(this.jTextField4)))
            .addComponent(this.jTextField1)
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jLabel2)
                .addComponent(this.jLabel1))
              .addGap(0, 0, 32767)))
          .addContainerGap()));
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap(13, 32767)
          .addComponent(this.jLabel2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(this.jTextField2, -2, -1, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
          .addComponent(this.jLabel1)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(this.jTextField1, -2, -1, -2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(this.jButton1)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this.textArea1, -2, 205, -2)
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.jCheckBox1)
              .addGap(18, 18, 18)
              .addComponent(this.jLabel3)
              .addGap(3, 3, 3)
              .addComponent(this.jTextField3, -2, -1, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
              .addComponent(this.jTextField4, -2, -1, -2)))
          .addContainerGap()));
    pack();
  }
  
  private boolean isSubExist(String sub) {
    try {
      InetAddress inetAddress = InetAddress.getByName(sub);
    } catch (UnknownHostException ex) {
      return false;
    } 
    return true;
  }
  
  private void jButton1ActionPerformed(ActionEvent evt) {
    this.subs.clear();
    this.count = 0;
    (new Thread() {
        public void run() {
          GuiSubdomainBrute.this.textArea1.append("Start!\n");
          try {
            Scanner txtscan = new Scanner(new File(GuiSubdomainBrute.this.jTextField1.getText()));
            while (txtscan.hasNextLine())
              GuiSubdomainBrute.this.subs.add(txtscan.nextLine()); 
            ForkJoinPool forkJoinPool = new ForkJoinPool(Integer.parseInt(GuiSubdomainBrute.this.jTextField3.getText()));
            //forkJoinPool.submit(() -> Arrays.<Object>stream(GuiSubdomainBrute.this.subs.toArray()).parallel().forEach(()));
            /* Тут была ошибка */
          } catch (Exception exception) {}
        }
      }).start();
  }
  
  private void go(String str) {
    if (isSubExist(str))
      if (this.jCheckBox1.isSelected()) {
        try {
          this.textArea1.append(String.valueOf(str) + " : " + sendGET(str) + "\n");
        } catch (Exception ex) {
          ex.printStackTrace();
        } 
      } else {
        this.textArea1.append(String.valueOf(str) + "\n");
      }  
    this.count++;
    this.jTextField4.setText(String.valueOf(this.count) + "/" + this.subs.size());
  }
  
  private String sendGET(String dom) throws Exception {
    String url = "http://" + dom;
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("User-Agent", "Mozilla/5.0");
    con.connect();
    int responseCode = con.getResponseCode();
    return "Code - " + responseCode;
  }
  
  private void jTextField2ActionPerformed(ActionEvent evt) {}
  
  private void jCheckBox1ActionPerformed(ActionEvent evt) {}
  
  public static void main(String[] args) {
    try {
      byte b;
      int i;
      UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo;
      for (i = (arrayOfLookAndFeelInfo = UIManager.getInstalledLookAndFeels()).length, b = 0; b < i; ) {
        UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[b];
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
        b++;
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(GuiSubdomainBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(GuiSubdomainBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(GuiSubdomainBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(GuiSubdomainBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            (new GuiSubdomainBrute()).setVisible(true);
          }
        });
  }
}
