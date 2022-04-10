package leonard.calendar;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Enumeration;

public class Window extends JFrame {

    JPanel jPanel;
    JPanel grid;
    String today;
    JLabel monthLabel;
    public int month;
    public int year;

    public Window() {
        super();
        Calendar calendar = new Calendar();
        today = calendar.today();
        month = calendar.getMonthNbr();
        year = calendar.getYear();

        BorderLayout layout = new BorderLayout();
        this.setTitle("Calendrier - " + today);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        jPanel = new JPanel();
        jPanel.setLayout(layout);

        Dimension minimumSize = new Dimension(500, 300);
        Dimension defaultSize = new Dimension(650, 400);
        this.setMinimumSize(minimumSize);
        this.setPreferredSize(defaultSize);

        //HAUT
        JPanel up = new JPanel();
        up.setLayout(new BorderLayout());
        //up.setPreferredSize(new Dimension(100, 50));

        JLabel date = new JLabel(today);
        monthLabel = new JLabel(calendar.MonthToTxt(month) + " " + year);
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        up.add(date, BorderLayout.WEST);
        up.add(monthLabel, BorderLayout.CENTER);

        //CENTRE
        grid = new JPanel();
        GridLayout layoutPanel = new GridLayout(6, 7);
        grid.setLayout(layoutPanel);

        createGrid(calendar);

        //BAS
        JPanel down = new JPanel();
        down.setLayout(new BorderLayout());

        JButton left = new JButton("Flèche gauche");
        JButton right = new JButton("Flèche droite");
        JButton todayButton = new JButton("Aujourd'hui");

        left.addActionListener(e -> {
            if (month > 1) {
                month--;
            } else {
                month = 12;
                year--;
            }
            changeMonth(calendar);
        });
        right.addActionListener(e -> {
            if (month < 12) {
                month ++;
            } else {
                month = 1;
                year++;
            }
            changeMonth(calendar);
        });
        todayButton.addActionListener( e -> {
            month = calendar.getMonthNbr();
            year = calendar.getYear();
            changeMonth(calendar);
        });

        down.add(left, BorderLayout.WEST);
        down.add(right, BorderLayout.EAST);
        down.add(todayButton, BorderLayout.CENTER);

        jPanel.add(up, BorderLayout.NORTH);
        jPanel.add(grid, BorderLayout.CENTER);
        jPanel.add(down, BorderLayout.SOUTH);

        police();
        showWindow();
    }

    private void changeMonth(Calendar calendar) {
        monthLabel.setText(calendar.MonthToTxt(month) + " " + year);
        if (month == calendar.getMonthNbr() && year == calendar.getYear()) {
            this.setTitle("Calendrier - " + today);
        } else {
            this.setTitle("Calendrier - " + calendar.MonthToTxt(month) + " " + year);
        }
        createGrid(calendar);
    }

    private void createGrid(Calendar calendar) {
        grid.removeAll();
        //Cases
        int[] boxes = calendar.array(month, year);
        for (int i = 0; i < 42; i++) {
            JLabel day = new JLabel(String.valueOf(boxes[i]));
            day.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //Colorer mois d'avant et d'après
            if (i < 7) { //Première ligne
                if (boxes[i] > 7) {
                    day.setForeground(Color.GRAY);
                }
            }
            if (i > 30) { //Dernière ligne
                if (boxes[i] < 21) {
                    day.setForeground(Color.GRAY);
                }
            }
            //Couleur d'aujourd'hui
            if (month == calendar.getMonthNbr() && boxes[i] == calendar.getDayNbr() && year == calendar.getYear()) {
                day.setForeground(Color.RED);
            }
            grid.add(day);
        }
        this.pack();
    }

    private void showWindow () {
        setContentPane(jPanel);
        this.pack();
        this.setVisible(true);
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
                UIManager.put(key, new javax.swing.plaf.FontUIResource("Calibri", Font.PLAIN,18));
        }
    }

    public ImageIcon imageIcon (String path) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            General.errorMessageText("Impossible de trouver le fichier " + path.substring(1));
            return null;
        }
    }
}
