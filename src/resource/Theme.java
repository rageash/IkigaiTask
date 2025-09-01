package resource;

import java.awt.Color;

public class Theme {
    public static ApplicationTheme castOut() {
        return new CastOut();
    }

    static class CastOut implements ApplicationTheme {
        @Override
        public Color getLayerZeroColor() {
            return Colors.WHITE;
        }

        @Override
        public Color getLayerOneColor() {
            return Colors.LIGHT_GRAY;
        }

        @Override
        public Color getLayerTwoColor() {
            return Colors.WHITE;
        }

        @Override
        public Color getLayerThreeColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerThreeColor'");
        }

        @Override
        public Color getLayerFourColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerFourColor'");
        }

        @Override
        public Color getLayerFiveColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerFiveColor'");
        }

        @Override
        public Color getLayerSixColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerSixColor'");
        }

        @Override
        public Color getLayerSevenColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerSevenColor'");
        }

        @Override
        public Color getLayerEightColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerEightColor'");
        }

        @Override
        public Color getLayerNineColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getLayerNineColor'");
        }

        @Override
        public Color getPrimaryButtonColor() {
            return Colors.LIGHT_BLUE;
        }

        @Override
        public Color getSecondaryButtonColor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getSecondaryButtonColor'");
        }

    }

    public interface ApplicationTheme {
        Color getLayerZeroColor();
        Color getLayerOneColor();
        Color getLayerTwoColor();
        Color getLayerThreeColor();
        Color getLayerFourColor();
        Color getLayerFiveColor();
        Color getLayerSixColor();
        Color getLayerSevenColor();
        Color getLayerEightColor();
        Color getLayerNineColor();

        Color getPrimaryButtonColor();
        Color getSecondaryButtonColor();
    }
}