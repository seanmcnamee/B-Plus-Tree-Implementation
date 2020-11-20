package app.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * GUI
 */
public class GUI implements ComponentListener, ActionListener {
    public JFrame frame;
    private GUIPage currentPage;
    private GUIPage[] pages;

    private final String NAME = "B+ Tree";
    private final int WIDTH_AT_SCALE_1 = 790;
    private final int HEIGHT_AT_SCALE_1 = 590;
    private double gameScale = 1;

    public GUI(GUIPage... pages) {
        this.pages = pages;
    }

    public void start() {
        setupPages();
        setupGUI();

        frame.setVisible(true);
    }

    /**
     * Sets up GUI stuff (JFrame)
     */
    private void setupGUI() {
        // Most of this stuff does what it says... If you want to see what it does,
        // mess around with it a bit (but put it back when you're done)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        System.out.println(screenSize.getWidth() + " x " + screenSize.getHeight() + " : Monitor Size");

        Dimension defaultDimension = new Dimension(WIDTH_AT_SCALE_1, HEIGHT_AT_SCALE_1);
        Dimension fullWindowDimention = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());

        // Create the GUI itself
        frame = new JFrame(NAME);

        // Its bounds
        frame.setMinimumSize(defaultDimension);
        frame.setMaximumSize(fullWindowDimention);
        frame.setPreferredSize(defaultDimension);

        // Allow for trapclose (X button)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // frame.add(NAME, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.setLocation((int) (screenSize.getWidth() - frame.getWidth()) / 2,
                (int) (screenSize.getHeight() - frame.getHeight()) / 2);

        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        System.out.println(
                "frame size: " + frame.getContentPane().getWidth() + ", " + frame.getContentPane().getHeight());

        addListeners();
        // frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        switchToAndReturnPage(0);
    }

    private void addListeners() {

        frame.addComponentListener(this);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {

            }

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }

        });
    }

    // May have a problem with having too much of a panel on something
    public GUIPage switchToAndReturnPage(int pageNumber) {
        currentPage = this.pages[pageNumber];
        frame.add(currentPage.getPanel());

        currentPage.getPanel().setVisible(true);
        return currentPage;
    }

    private void setupPages() {
        for (GUIPage page : this.pages) {
            page.setButtonListeners(this);
            page.panel.setVisible(false);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("Resizing...");
        double scaleX = 1.0 * frame.getContentPane().getWidth() / WIDTH_AT_SCALE_1;
        double scaleY = 1.0 * frame.getContentPane().getHeight() / HEIGHT_AT_SCALE_1;
        this.gameScale = Math.min(scaleX, scaleY);
        setComponentSizeAndLocation();
    }

    /**
     * Set position and sizes of buttons based on percentages Use
     * gameScale*WIDTH_AT_SCALE_1*(xSizePercent or xPositionPercent) I created an
     * example
     */
    public void setComponentSizeAndLocation() {
        System.out.println("Setting size/location");
        // Use for all components
        double pixelWidth = gameScale * WIDTH_AT_SCALE_1;
        double pixelHeight = gameScale * HEIGHT_AT_SCALE_1;
        // System.out.println(pixelWidth);

        // This will force everything to be in scale with the window. (unproportional
        // width or height will result in the margins)
        double excessWidth = frame.getContentPane().getWidth() - pixelWidth;
        double excessHeight = frame.getContentPane().getHeight() - pixelHeight;
        double xStart = excessWidth / 2.0;
        double yStart = excessHeight / 2.0;
        // YO
        // Update each component in the page
        this.currentPage.setComponentSizeAndLocation(pixelWidth, pixelHeight, xStart, yStart);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // System.out.println("Moved");
    }

    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println("Shown");

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        System.out.println("Hidden");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.currentPage.actionPerformed(e.getSource(), this);
    }
}