package ui;

import javax.swing.JFrame;

import config.AppConfig;

public class AppWindow {
    private AppConfig.BuildConfig buildConfig;

    // Master Frame
    private JFrame frame;

    public AppWindow(AppConfig.BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
        initialize();
    }

    private void initialize() {
        // initialize master ui
        frame = new JFrame(buildConfig.getTitle());
        frame.setSize(buildConfig.getWidth(), buildConfig.getHeight());
        frame.getContentPane().setBackground(buildConfig.getBackgroundColor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
