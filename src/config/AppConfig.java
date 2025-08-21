package config;
import java.awt.Color;

public class AppConfig {
    
    private static class Debug implements BuildConfig {
        
        // Title of the application
        public static String TITLE = "Ikigai Task[DEBUG]";

        // Height of the application
        public static int HEIGHT = 800;
        
        // Width of the application
        public static int WIDTH = 800;

        // Background color of the Main window
        public static Color BACKGROUND_COLOR = new Color(255, 255, 255, 255);

        // Data file location
        public static String DATA_FILE_LOCATION = ".\\build\\.ikigai_task";

        // Data file name
        public static String DATA_FILE = "data.iki";

        @Override
        public String getTitle() {
            return TITLE;
        }

        @Override
        public int getHeight() {
            return HEIGHT;
        }

        @Override
        public int getWidth() {
            return WIDTH;
        }

        @Override
        public Color getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        @Override
        public String getDataFileLocation() {
            return DATA_FILE_LOCATION;
        }

        @Override
        public String getDataFile() {
            return DATA_FILE;
        }
    }
    
    private static class DebugRelease implements BuildConfig {
        
        // Title of the application
        public static String TITLE = "Ikigai Task[DEBUG_RELEASE]";

        // Default height of the application
        public static int HEIGHT = 800;
        
        // Default width of the application
        public static int WIDTH = 800;
        
        // Background color of the Main window
        public static Color BACKGROUND_COLOR = new Color(105, 130, 90, 255);

        // Data file location
        public static String DATA_FILE_LOCATION = ".\\build\\.ikigai_task";

        // Data file name
        public static String DATA_FILE = "data.iki";

        @Override
        public String getTitle() {
            return TITLE;
        }

        @Override
        public int getHeight() {
            return HEIGHT;
        }

        @Override
        public int getWidth() {
            return WIDTH;
        }

        @Override
        public Color getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        @Override
        public String getDataFileLocation() {
            return DATA_FILE_LOCATION;
        }

        @Override
        public String getDataFile() {
            return DATA_FILE;
        }
    }
    
    private static class Release implements BuildConfig {
        
        // Title of the application
        public String TITLE = "Ikigai Task";

        // Default height of the application
        public int HEIGHT = 800;
        
        // Default width of the application
        public int WIDTH = 800;
        
        // Background color of the Main window
        public static Color BACKGROUND_COLOR = new Color(255, 205, 155, 255);

        // Data file location
        public static String DATA_FILE_LOCATION = System.getenv("USERPROFILE") + "\\AppData\\Roaming\\.ikigai_task";

        // Data file name
        public static String DATA_FILE = "data.iki";

        @Override
        public String getTitle() {
            return TITLE;
        }

        @Override
        public int getHeight() {
            return HEIGHT;
        }

        @Override
        public int getWidth() {
            return WIDTH;
        }

        @Override
        public Color getBackgroundColor() {
            return BACKGROUND_COLOR;
        }

        @Override
        public String getDataFileLocation() {
            return DATA_FILE_LOCATION;
        }

        @Override
        public String getDataFile() {
            return DATA_FILE;
        }
    }
    
    public interface BuildConfig {

        public final static String DATA_FILE_FORMAT = ".iki";

        String getTitle();
        int getHeight();
        int getWidth();
        Color getBackgroundColor();
        String getDataFileLocation();
        String getDataFile();
    }

    public static BuildConfig getDebugConstants() {
        return new Debug();
    }

    public static BuildConfig getDebugReleaseConstants() {
        return new DebugRelease();
    }

    public static BuildConfig getReleaseConstants() {
        return new Release();
    }
}
