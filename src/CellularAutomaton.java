

import javax.swing.*;
import java.util.*;
import java.awt.*;
class Automaton_Logic extends JPanel {
    int rule_number;
    byte[][] cell_position;
    public Automaton_Logic()
    {
        //print Nothing
    }
    public Automaton_Logic(int ruleNumber)
    {
        rule_number = ruleNumber;
        Dimension dimension = new Dimension(1500, 1500);
        setPreferredSize(dimension);
        cell_position = new byte[dimension.height][dimension.width];
        setBackground(Color.blue);
        cell_position[0][dimension.width / 2] = 1;
    }
    @Override
    public void paintComponent(Graphics gg)
    {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ca_Logic(g);
    }

    void ca_Logic(Graphics2D g)
    {
        int i = 0;
        g.setColor(Color.red);
        while(i < cell_position.length - 1)
        {
            int j = 1;
            while(j < cell_position[i].length - 1)
            {
                byte r_neighbour = cell_position[i][j + 1];
                byte middle = cell_position[i][j];
                byte l_neighbour = cell_position[i][j - 1];
                CellularAutomaton ca = new CellularAutomaton();
                cell_position[i + 1][j] = ca.cell_position(l_neighbour, middle, r_neighbour, rule_number);
                if (cell_position[i][j] == 1)
                {
                    g.fillRect(j, i, 1, 1);
                }
                j++;
            }
            i++;
        }
    }
}
/*Enter the rule number less than 256 and more than 0
The Cellular Automaton for that rule will be printed
If wanna see the cellular automaton of some other rule
then press y or yes and then the rule number
Cellular Automaton for next rule will appear.
 */
class CellularAutomaton
{
    public static void main(String[] args) {
        while (true) {
            System.out.print("Enter the rule number for cellular automaton :");
            Scanner cells = new Scanner(System.in);
            int ruleNumber = cells.nextInt();
            if (ruleNumber < 255 && ruleNumber >= 0) {
                SwingUtilities.invokeLater(() -> {
                    JFrame jframe = new JFrame();
                    jframe.setVisible(true);
                    jframe.add(new Automaton_Logic(ruleNumber), BorderLayout.CENTER);
                    jframe.pack();
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jframe.setLocationRelativeTo(null);
                    jframe.setResizable(false);
                });
            } else if (ruleNumber < 0 || ruleNumber > 255) {
                System.out.println("Please enter rule between 0 and 255");
            }
            System.out.print("Want to try another rule(Press y or yes to continue) :");
            String yes_or_no = cells.next();
            if (yes_or_no.equals("y") || yes_or_no.equals("yes")) {
                continue;
            } else
                break;
        }
    }
    public byte cell_position(byte left_neighbour, byte center, byte right_neigbhour, int rule_number)
    {
        int l_neighbour, centr, r_neighbour, r_number, value;
        l_neighbour = left_neighbour << 2;
        centr = center << 1;
        r_neighbour = right_neigbhour;
        int position = (l_neighbour | centr | r_neighbour);
        r_number = rule_number >> position;
        value = r_number & 1;
        return (byte) (value);
    }
}
