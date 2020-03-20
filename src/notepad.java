import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileFilter;
import java.util.Scanner;

class Notepad extends JFrame implements ActionListener {
    private JTextArea txt = new JTextArea();

    private JMenuBar newMenubar() {
        JMenuBar menubar = new JMenuBar();
        String[] titles = {"File", "hemlig sak som ej får klickas"};
        String[][] elements = {{"New", "Open", "Save"}, {"Snälla ge mig A. GUI gör mig ledsen jag fattar inte :("}};
        for(int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String[] elems = elements[i];
            menubar.add(newMenu(title, elems));

        }
        return menubar;
    }

    private JMenu newMenu(String title, String[] elements) {
        JMenu menu = new JMenu(title);
        for(String element : elements) {
            JMenuItem menuitem = new JMenuItem(element);
            menu.add(menuitem);
            menuitem.addActionListener(this);
        }
        return menu;
    }

    private Notepad() {
        setTitle("untitled - Notepad");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setSize(800, 600);
        setJMenuBar(newMenubar());
        JScrollPane scroller = new JScrollPane(txt);
        add(scroller);
    }

    public static void main(String[] args) {
        new Notepad().setVisible(true);
    }


    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        if(cmd.equals("Save")) {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter buf = new BufferedWriter(new FileWriter(chooser.getSelectedFile().getAbsolutePath()));

                    buf.write(txt.getText());
                    setTitle(chooser.getSelectedFile().getName());
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if(cmd.equals("Open")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new Filter());
            int option = chooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                try {
                    Scanner scanner = new Scanner(chooser.getSelectedFile());
                    while(scanner.hasNext()) {
                        String data = scanner.nextLine();
                        txt.setText(data);
                    }
                    setTitle(chooser.getSelectedFile().getName());
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Filter extends javax.swing.filechooser.FileFilter implements FileFilter {

        public boolean accept(File file) {
            return file.getName().endsWith(".txt") || file.getName().endsWith(".java");
        }

        @Override
        public String getDescription() {
            return "Text File (.txt)";
        }
    }
}