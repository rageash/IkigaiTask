package ui.animation;

public interface AnimatorCallback {
    void callback(Object caller);
    boolean hasTerminated();
}
