/* Task Scheduler Application 
Manage Tasks
Stores tasks using Priority Queue
*/
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskScheduler {
    private final PriorityQueue<Task> taskQueue;
    private final ExecutorService executorService;
    private final int threadPoolSize;
    private volatile boolean isRunning;
    
    public TaskScheduler(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        this.taskQueue = new PriorityQueue<>();
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.isRunning = false;
    }
    
    /**
     * Submit a task to the scheduler
     */
    public void scheduleTask(Task task) {
        synchronized (taskQueue) {
            taskQueue.offer(task);
            System.out.println("[SCHEDULED] " + task);
            taskQueue.notify(); // Wake up the scheduler thread
        }
    }
    
    /**
     * Start the scheduler - processes tasks from the queue
     */
    public void start() {
        if (isRunning) {
            System.out.println("Scheduler is already running");
            return;
        }
        
        isRunning = true;
        System.out.println("Starting TaskScheduler with " + threadPoolSize + " threads");
        
        // Scheduler thread that dispatches tasks
        Thread schedulerThread = new Thread(() -> {
            while (isRunning) {
                Task task = null;
                
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && isRunning) {
                        try {
                            taskQueue.wait(); // Wait for tasks
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    if (!taskQueue.isEmpty()) {
                        task = taskQueue.poll();
                    }
                }
                
                if (task != null) {
                    final Task taskToExecute = task;
                    System.out.println("[DISPATCHING] " + taskToExecute);
                    
                    executorService.submit(() -> {
                        try {
                            System.out.println("[EXECUTING] " + taskToExecute + 
                                             " on thread: " + Thread.currentThread().getName());
                            taskToExecute.getAction().run();
                            System.out.println("[COMPLETED] " + taskToExecute);
                        } catch (Exception e) {
                            System.err.println("[FAILED] " + taskToExecute + 
                                             " - Error: " + e.getMessage());
                        }
                    });
                }
            }
        });
        
        schedulerThread.setName("Scheduler-Thread");
        schedulerThread.start();
    }
    
    /**
     * Shutdown the scheduler gracefully
     */
    public void shutdown() {
        System.out.println("Shutting down TaskScheduler...");
        isRunning = false;
        
        synchronized (taskQueue) {
            taskQueue.notify(); // Wake up waiting thread
        }
        
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("TaskScheduler stopped");
    }
    
    /**
     * Get current queue size
     */
    public int getQueueSize() {
        synchronized (taskQueue) {
            return taskQueue.size();
        }
    }
}