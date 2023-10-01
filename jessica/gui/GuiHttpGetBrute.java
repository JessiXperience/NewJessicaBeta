package jessica.gui;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

public class GuiHttpGetBrute extends JFrame {
  private List<String> words = new ArrayList<>();
  
  private List<String> urlList = new ArrayList<>();
  
  private JButton jButton1;
  
  private JButton jButton2;
  
  private JButton jButton3;
  
  private JComboBox<String> jComboBox1;
  
  private JLabel jLabel1;
  
  private JLabel jLabel2;
  
  private JLabel jLabel3;
  
  private JLabel jLabel4;
  
  private JLabel jLabel5;
  
  private JLabel jLabel6;
  
  private JTextField jTextField1;
  
  private JTextField jTextField2;
  
  private JTextField jTextField3;
  
  private JTextField jTextField4;
  
  private JTextField jTextField5;
  
  private JTextField jTextField6;
  
  private TextArea textArea1;
  
  public GuiHttpGetBrute() {
    initComponents();
  }
  
  private void initComponents() {
    this.jTextField1 = new JTextField();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jTextField2 = new JTextField();
    this.textArea1 = new TextArea();
    this.jLabel3 = new JLabel();
    this.jButton1 = new JButton();
    this.jTextField3 = new JTextField();
    this.jTextField4 = new JTextField();
    this.jLabel4 = new JLabel();
    this.jTextField5 = new JTextField();
    this.jLabel5 = new JLabel();
    this.jButton2 = new JButton();
    this.jButton3 = new JButton();
    this.jLabel6 = new JLabel();
    this.jTextField6 = new JTextField();
    this.jComboBox1 = new JComboBox<>();
    setTitle("HTTP/HTTPS GET Method Brute");
    this.jTextField1.setToolTipText("");
    this.jTextField1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jTextField1ActionPerformed(evt);
          }
        });
    this.jLabel1.setText("List of targets");
    this.jLabel2.setText("Path to list with words");
    this.jTextField2.setToolTipText("");
    this.jLabel3.setText("Log");
    this.jButton1.setText("Start");
    this.jButton1.setToolTipText("");
    this.jButton1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jButton1ActionPerformed(evt);
          }
        });
    this.jTextField3.setText("0/0");
    this.jTextField4.setText("200,403");
    this.jTextField4.setToolTipText("");
    this.jLabel4.setText("Threads");
    this.jTextField5.setText("1");
    this.jTextField5.setToolTipText("");
    this.jLabel5.setText("Allowed Responce Codes");
    this.jButton2.setText("...");
    this.jButton2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jButton2ActionPerformed(evt);
          }
        });
    this.jButton3.setText("...");
    this.jButton3.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jButton3ActionPerformed(evt);
          }
        });
    this.jLabel6.setText("Allowed context on page");
    this.jTextField6.setToolTipText("");
    this.jTextField6.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jTextField6ActionPerformed(evt);
          }
        });
    this.jComboBox1.setModel(new DefaultComboBoxModel<>(new String[] { "By responce codes", "By context on page" }));
    this.jComboBox1.setToolTipText("");
    this.jComboBox1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            GuiHttpGetBrute.this.jComboBox1ActionPerformed(evt);
          }
        });
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addContainerGap()
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.jTextField3)
              .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
              .addComponent(this.jButton1, -2, 382, -2))
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.textArea1, -2, 357, -2)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jTextField4)
                .addComponent(this.jTextField5, GroupLayout.Alignment.TRAILING)
                .addComponent(this.jTextField6)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.jLabel4)
                    .addComponent(this.jLabel5)
                    .addComponent(this.jLabel6))
                  .addGap(0, 0, 32767))
                .addComponent(this.jComboBox1, 0, -1, 32767)))
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                .addComponent(this.jTextField2, GroupLayout.Alignment.LEADING, -1, 464, 32767)
                .addComponent(this.jLabel1, GroupLayout.Alignment.LEADING)
                .addComponent(this.jLabel2, GroupLayout.Alignment.LEADING)
                .addComponent(this.jLabel3, GroupLayout.Alignment.LEADING)
                .addComponent(this.jTextField1, GroupLayout.Alignment.LEADING))
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(this.jButton2)
                .addComponent(this.jButton3))
              .addGap(0, 0, 32767)))
          .addContainerGap()));
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
          .addGap(7, 7, 7)
          .addComponent(this.jLabel1)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jTextField1, -2, -1, -2)
            .addComponent(this.jButton3))
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(this.jLabel2)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent(this.jTextField2, -2, -1, -2)
            .addComponent(this.jButton2))
          .addGap(18, 18, 18)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.jLabel3)
              .addGap(6, 6, 6)
              .addComponent(this.textArea1, -2, 192, -2))
            .addGroup(layout.createSequentialGroup()
              .addComponent(this.jComboBox1, -2, -1, -2)
              .addGap(18, 18, 18)
              .addComponent(this.jLabel6)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(this.jTextField6, -2, -1, -2)
              .addGap(18, 18, 18)
              .addComponent(this.jLabel5)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(this.jTextField4, -2, -1, -2)
              .addGap(17, 17, 17)
              .addComponent(this.jLabel4)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(this.jTextField5, -2, -1, -2)))
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(this.jButton1, -1, 54, 32767)
            .addComponent(this.jTextField3, -2, -1, -2))));
    pack();
  }
  
  private void jTextField1ActionPerformed(ActionEvent evt) {}
  
  private void jButton1ActionPerformed(ActionEvent evt) {
    this.words.clear();
    this.urlList.clear();
    (new Thread() {
        int count = 0;
        
        public void run() {
          try {
            File file = new File(GuiHttpGetBrute.this.jTextField2.getText());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
              GuiHttpGetBrute.this.words.add(line); 
            File file2 = new File(GuiHttpGetBrute.this.jTextField1.getText());
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            String line2;
            while ((line2 = br2.readLine()) != null)
              GuiHttpGetBrute.this.urlList.add(line2); 
            List<String> urls = new ArrayList<>();
            for (String s1 : GuiHttpGetBrute.this.urlList) {
              for (String s2 : GuiHttpGetBrute.this.words) {
                String url = s1.replace("{text}", s2);
                urls.add(url);
              } 
            } 
            GuiHttpGetBrute.this.jTextField3.setText("0/" + urls.size());
            ForkJoinPool forkJoinPool = new ForkJoinPool(Integer.parseInt(GuiHttpGetBrute.this.jTextField5.getText()));
            if ("By responce codes".equals(GuiHttpGetBrute.this.jComboBox1.getSelectedItem().toString())) {
              //forkJoinPool.submit(() -> Arrays.<Object>stream(param1List.toArray()).parallel().forEach(()));
            } else if ("By context on page".equals(GuiHttpGetBrute.this.jComboBox1.getSelectedItem().toString())) {
              //forkJoinPool.submit(() -> Arrays.<Object>stream(param1List.toArray()).parallel().forEach());
            	// Такая же история, тут ошибки
            } 
          } catch (Exception exception) {}
        }
        
        private void go(String url) {
          try {
            if (pageExist(url))
              GuiHttpGetBrute.this.textArea1.append(String.valueOf(url) + "\n"); 
          } catch (Exception exception) {}
          this.count++;
          GuiHttpGetBrute.this.jTextField3.setText(String.valueOf(this.count) + "/" + (GuiHttpGetBrute.this.words.size() * GuiHttpGetBrute.this.urlList.size()));
        }
        
        private void goByContext(String url) {
          try {
            if (pageContainsContext(url))
              GuiHttpGetBrute.this.textArea1.append(String.valueOf(url) + "\n"); 
          } catch (Exception exception) {}
          this.count++;
          GuiHttpGetBrute.this.jTextField3.setText(String.valueOf(this.count) + "/" + (GuiHttpGetBrute.this.words.size() * GuiHttpGetBrute.this.urlList.size()));
        }
        
        private boolean pageContainsContext(String url) throws Exception {
          StringBuffer response = new StringBuffer();
          URL obj = new URL(url);
          if (url.startsWith("https://")) {
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
              response.append(inputLine); 
          } else {
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
              response.append(inputLine); 
          } 
          return response.toString().contains(GuiHttpGetBrute.this.jTextField6.getText());
        }
        
        private boolean pageExist(String url) throws Exception {
          int responseCode = 0;
          URL obj = new URL(url);
          if (url.startsWith("https://")) {
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.connect();
            responseCode = con.getResponseCode();
          } else {
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.connect();
            responseCode = con.getResponseCode();
          } 
          String[] codes = GuiHttpGetBrute.this.jTextField4.getText().replace(" ", "").split(",");
          return Arrays.<String>asList(codes).contains(String.valueOf(responseCode));
        }
      }).start();
  }
  
  private void jButton3ActionPerformed(ActionEvent evt) {
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int returnValue = jfc.showOpenDialog(null);
    if (returnValue == 0) {
      File selectedFile = jfc.getSelectedFile();
      this.jTextField1.setText(selectedFile.getAbsolutePath());
    } 
  }
  
  private void jButton2ActionPerformed(ActionEvent evt) {
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int returnValue = jfc.showOpenDialog(null);
    if (returnValue == 0) {
      File selectedFile = jfc.getSelectedFile();
      this.jTextField2.setText(selectedFile.getAbsolutePath());
    } 
  }
  
  private void jTextField6ActionPerformed(ActionEvent evt) {}
  
  private void jComboBox1ActionPerformed(ActionEvent evt) {}
  
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
      Logger.getLogger(GuiHttpGetBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(GuiHttpGetBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(GuiHttpGetBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(GuiHttpGetBrute.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            (new GuiHttpGetBrute()).setVisible(true);
          }
        });
  }
}
