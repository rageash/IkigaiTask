package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import config.AppConfig;

public class AppWindow {
    private AppConfig.BuildConfig buildConfig;

    // Master Frame
    private JFrame frame;
    
    // Landing Page
    private LandingPage landingPage;

    public AppWindow(AppConfig.BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
        initialize();
    }

    private void initialize() {
        // initialize master ui
        frame = new JFrame(buildConfig.getTitle());
        frame.setSize(buildConfig.getWidth(), buildConfig.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        LandingPage landingPage = new LandingPage(buildConfig);
        frame.add(landingPage);
        
        frame.setVisible(true);
    }
}
