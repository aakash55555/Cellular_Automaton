import javax.swing.*;
import java.util.*;
import java.awt.*;

class Automaton_Logic extends JPanel {
    byte[][] cell_position;
    int rule_number  = 30;

    public Automaton_Logic()
    {
        //print Nothing
    }
    public Automaton_Logic(int ruleNumber)
    {
        rule_number = ruleNumber;
        Dimension dimension = new Dimension(1500, 1500);
        setPreferredSize(dimension);
        setBackground(Color.blue);

        cell_position = new byte[dimension.height][dimension.width];
        cell_position[0][dimension.width / 2] = 1;
    }

    public byte cell_position(int left_neighbour, int center, int right_neigbhour)
    {
        int position = (left_neighbour << 2 | center << 1 | right_neigbhour);
        return (byte) (rule_number >> position & 1);
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
                byte l_neighbour = cell_position[i][j - 1];
                byte middle = cell_position[i][j];
                byte r_neighbour = cell_position[i][j + 1];
                cell_position[i + 1][j] = cell_position(l_neighbour, middle, r_neighbour);
                if (cell_position[i][j] == 1)
                {
                    g.fillRect(j, i, 1, 1);
                }
                j++;
            }
            i++;
        }
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
}

class CellularAutomaton
{
    public static void main(String[] args)
    {
        while(true)
        {
            System.out.print("Enter the rule number for cellular automaton :");
            Scanner cells = new Scanner(System.in);
            int ruleNumber = cells.nextInt();
            if(ruleNumber < 255 && ruleNumber >=0)
            {
                SwingUtilities.invokeLater(() -> {
                    JFrame jframe = new JFrame();
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jframe.setResizable(false);
                    jframe.add(new Automaton_Logic(ruleNumber), BorderLayout.CENTER);
                    jframe.pack();
                    jframe.setLocationRelativeTo(null);
                    jframe.setVisible(true);
                });
            }
            else if(ruleNumber < 0 || ruleNumber > 255)
            {
                System.out.println("Please enter rule between 0 and 255");
            }
            System.out.print("Want to try another rule(Press y or yes to continue) :");
            String yes_or_no = cells.next();
            if (yes_or_no.equals("y") || yes_or_no.equals("yes")) {
                continue;
            }
            else
                break;
        }
    }
}