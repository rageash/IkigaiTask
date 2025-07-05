package ui.animation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Animator {

    public static int TIME_FRAMING = 60;

    private AnimatorCallback animatorCallback;
    private Timer timer = null;
    private int duration = 0;
    private List<AnimationStateListener> animationStateListeners = new ArrayList<>();

    public Animator(AnimatorCallback animatorCallback, int duration, boolean start) {
        this.animatorCallback = animatorCallback;
        setDuration(duration);
        if (start) {
            start();
        }
    }

    public Animator(AnimatorCallback animatorCallback, boolean start) {
        this(animatorCallback, TIME_FRAMING, start);
    }

    public Animator(AnimatorCallback animatorCallback) {
        this(animatorCallback, false);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void addAnimationStateListener(AnimationStateListener animationStateListener) {
        animationStateListeners.add(animationStateListener);
    }

    public void removeAnimationStateListener(AnimationStateListener animationStateListener) {
        animationStateListeners.remove(animationStateListener);
    }

    public void start() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(duration, new CallbackListener());
        timer.start();
        for(AnimationStateListener animationStateListener: animationStateListeners) {
            animationStateListener.animationStarted(this);
        }
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    public boolean isRunning() {
        if (timer != null) {
            return timer.isRunning();
        }
        return false;
    }

    private class CallbackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (animatorCallback.hasTerminated()) {
                if (timer == null) {
                    throw new IllegalStateException("Callback listener is not yet fired");
                }

                for (AnimationStateListener animationStateListener: animationStateListeners) {
                    animationStateListener.animationCompleted(Animator.this);
                }
                timer.stop();
            }
            animatorCallback.callback(Animator.this);
        }
    }
}