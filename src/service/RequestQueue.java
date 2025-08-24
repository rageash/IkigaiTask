package service;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This will loop through every process, and callback will call once execution is completed.
 */
public class RequestQueue {

    // Single instance for the application
    private static RequestQueue instance = null;

    // Handles processing the queue, if false will stops processing the queue
    private boolean isQueueRunning = false;
    // Async instance for executing the task
    private Thread requestQueue = null;
    // List of tasks
    private Queue<Executable> queue = new LinkedList<>();

    // Avoid creating the object instance
    private RequestQueue() {}

    /**
     * Return RequestQueue instance if already initialized
     * If not RequestQueue will be initialized, and returned
     * @return RequestQueue
     */
    public static RequestQueue getInstance() {
        return instance == null ? (instance = new RequestQueue()) : instance;
    }
    
    /**
     * Starts the thread
     */
    private void processQueue() {
        requestQueue.start();
    }

    /**
     * Initialize the requestQueue thread
     * Will be reinitialize of every time starting the request queue service
     */
    private void assignProcessingTask() {
        requestQueue = new Thread(() -> {
            while (isQueueRunning && !queue.isEmpty()) {
                Executable executable = queue.poll();
                executable.execute();
            }
            setQueueRunning(false);
        });
    }

    /**
     * Start the request queue service
     */
    public void start() {
        setQueueRunning(true);
        assignProcessingTask();
        processQueue();
    }

    /**
     * Stop the request queue service
     */
    public void stop() {
        setQueueRunning(false);
    }

    /**
     * Adds executable task in the queue
     * If queue is not start, the queue will will be started
     * @param executable
     */
    public void addTask(Executable executable) {
        queue.add(executable);
        if (!isQueueRunning) {
            start();
        }
    }

    /**
     * Clears all the task
     */
    public void clearAllTask() {
        queue.clear();
    }

    /**
     * Update the queue if its running or not
     * @param isQueueRunning
     */
    private synchronized void setQueueRunning(boolean isQueueRunning) {
        this.isQueueRunning = isQueueRunning;
    }
    
    public interface Executable {
        void execute();
    }
}