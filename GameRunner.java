
/**
* The GameRunner class represents a graphical user interface that
* adds a SpaceWorld panel to the extended JFrame so that
* the created space world can be displayed in a window.
* @author Teddy Hsieh, Sharon Jiang, Charissa Zou
* Teacher Name: Mrs. Ishman
* Period: 3
* Due Date: 05-18-18
*/
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
 
public class GameRunner extends JFrame{
 
    public GameRunner() {
        initUI();
    }
     
    /** 
     * Initiate the UI
     */
    private void initUI() {
        add(new SpaceWorld());
         
        //setResizable(false); //locks window size
        pack(); //cause window size to fit layout of children
         
        setTitle("Galactic Adventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EventQueue.invokeLater(() -> {
            GameRunner ex = new GameRunner();
            ex.setExtendedState(JFrame.MAXIMIZED_BOTH);
            ex.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
            ex.setVisible(true);
        });
    }
    /*
    public void nextWorld() {
        remove(currentWorld);
        currentWorld = new SpaceWorld(2);
        add(currentWorld);
    }
    */
 
}