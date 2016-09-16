package challenge.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashWindow extends JWindow {

	   public SplashWindow(/*String filename, Frame f*/)
	    {
		        //super(f);
		        JLabel l = new JLabel(new ImageIcon("/Users/thomasheritage/Documents/bah.jpg"));
		        getContentPane().add(l, BorderLayout.CENTER);
		        pack();
		        Dimension screenSize =
		          Toolkit.getDefaultToolkit().getScreenSize();
		        Dimension labelSize = l.getPreferredSize();
		        setLocation(screenSize.width/2 - (labelSize.width/2),
		                    screenSize.height/2 - (labelSize.height/2));
		        addMouseListener(new MouseAdapter()
		            {
		                public void mousePressed(MouseEvent e)
		                {
		                    setVisible(false);
		                    dispose();
		                }
		            });
		        setVisible(true);
	    }
	/*private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/

}
