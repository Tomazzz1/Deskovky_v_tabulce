import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Form extends JFrame{
    private JButton buttonPredchozi;
    private JButton buttonDalsi;
    private JTextArea textArea1;
    private JCheckBox checkBoxZ;
    private JSlider slider1;
    private JCheckBox checkBoxN;
    private JPanel main;
    private ButtonGroup skupina;
    private DefaultTableModel model;
    private static final String ODDELOVAC = ";";
    private static final String NAZEV_SOUBORU = "deskovky.txt";
    private int indexSeznamu = 0;
    private static final String SOUBOR = "deskovky.txt";

    public Form() {
        nacistD();
        skupina = new ButtonGroup();
        skupina.add(checkBoxN);
        skupina.add(checkBoxZ);

        try{nactiData(indexSeznamu);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(main,"Zapište do souboru data" + JOptionPane.ERROR_MESSAGE);
        }

        buttonPredchozi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexSeznamu--;
                nactiData(indexSeznamu);

            }
        });
        buttonDalsi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexSeznamu++;
                nactiData(indexSeznamu);

                }
        });
    }

    public void nactiData(int index){

        List<Deskovka> seznam = getSeznamDeskovek();

        if(indexSeznamu == 0){
            buttonPredchozi.setEnabled(false);
            buttonDalsi.setEnabled(true);
        }
        else if(indexSeznamu == seznam.size() - 1){
            buttonPredchozi.setEnabled(true);
            buttonDalsi.setEnabled(false);
        }
        else {
            buttonPredchozi.setEnabled(true);
            buttonDalsi.setEnabled(true);
        }
        Deskovka aktualniDeskovka = seznam.get(index);
        textArea1.setText(aktualniDeskovka.getNazev());
        if(aktualniDeskovka.isZakoupeno()){
            checkBoxZ.setSelected(true);
        }
        else {
            checkBoxN.setSelected(true);
        }

        slider1.setValue(aktualniDeskovka.getOblibenost());
    }
    public void nacistD() {

        List<Deskovka> hry = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(SOUBOR))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String nazev = parts[0];
                boolean zakoupeno = Boolean.parseBoolean(parts[1]);
                int oblibenost = Integer.parseInt(parts[2]);
                hry.add(new Deskovka(nazev, zakoupeno, oblibenost));
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání souboru");
            e.printStackTrace();
        }

        // Vytvoření tabulky a zobrazení dat v ní
        JFrame frame = new JFrame("Deskové hry");
        JPanel panel = new JPanel();
        String[] columnNames = {"Název", "Zakoupeno", "Oblíbenost"};
        Object[][] data = new Object[hry.size()][3];
        for (int i = 0; i < hry.size(); i++) {
            Deskovka hra = hry.get(i);
            data[i][0] = hra.getNazev();
            data[i][1] = hra.isZakoupeno();
            data[i][2] = hra.getOblibenost();
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }


    List<Deskovka> getSeznamDeskovek() {
        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(NAZEV_SOUBORU)));
            List<Deskovka> seznamDeskovek = new ArrayList<>();
            while (scanner.hasNext()) {
                String radek = scanner.nextLine();
                String[] castiRadku = radek.split(ODDELOVAC);

                for (int i = 0; i < castiRadku.length; i++) {
                    castiRadku[i] = castiRadku[i].trim();
                }
                String nazev = castiRadku[0];
                boolean zakoupeno = Boolean.parseBoolean(castiRadku[1]);
                int oblibenost = Integer.parseInt(castiRadku[2]);
                seznamDeskovek.add(new Deskovka(nazev, zakoupeno, oblibenost));



            }
            scanner.close();
            return seznamDeskovek;


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) {
        Form form = new Form();
        form.setVisible(true);
        form.pack();
        form.setContentPane(form.main);
        form.setDefaultCloseOperation(EXIT_ON_CLOSE);
        form.setSize(800,400);


    }
}

