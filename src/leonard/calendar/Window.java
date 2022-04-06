package leonard.calendar;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Enumeration;

public class Window extends JFrame {

    JFrame jFrame;
    JPanel jPanel;
    String today = Calendar.today();

    public Window() {
        police();
        init();

        JLabel date = new JLabel(today);

        jPanel.add(date);

        showWindow();
    }

    private void init() {
        jFrame = new JFrame();
        BorderLayout layout = new BorderLayout();
        jFrame.setLayout(layout);
        jFrame.setTitle("Calendrier - " + today);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);

        jPanel = new JPanel();
        GridLayout layoutPanel = new GridLayout(4, 2);
        jPanel.setLayout(layoutPanel);

        Dimension minimumSize = new Dimension(400, 200);
        Dimension defaultSize = new Dimension(500, 300);
        jFrame.setMinimumSize(minimumSize);
        jFrame.setPreferredSize(defaultSize);
    }

    private void showWindow () {
        setContentPane(jPanel);
        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void police() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, new javax.swing.plaf.FontUIResource("Calibri", Font.PLAIN,16));
        }
    }

    public ImageIcon imageIcon (String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(jFrame, "Impossible de trouver le fichier " + path.substring(1));
            return null;
        }
    }
}
