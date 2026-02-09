/*Entry point
Adds sample task
Triggers execution */
public class Main {
    public static void main(String[] args) {
        // Create scheduler with 3 worker threads
        TaskScheduler scheduler = new TaskScheduler(3);
        
        // Start the scheduler
        scheduler.start();
        
        // Create and schedule tasks with different priorities
        System.out.println("\n=== Submitting Tasks ===\n");
        
        // Low priority task
        scheduler.scheduleTask(new Task("Task-1", 1, () -> {
            simulateWork("Task-1", 2000);
        }));
        
        // High priority task
        scheduler.scheduleTask(new Task("Task-2", 10, () -> {
            simulateWork("Task-2", 1500);
        }));
        
        // Medium priority task
        scheduler.scheduleTask(new Task("Task-3", 5, () -> {
            simulateWork("Task-3", 1000);
        }));
        
        // Another high priority task
        scheduler.scheduleTask(new Task("Task-4", 9, () -> {
            simulateWork("Task-4", 2500);
        }));
        
        // Low priority task
        scheduler.scheduleTask(new Task("Task-5", 2, () -> {
            simulateWork("Task-5", 1000);
        }));
        
        // Medium priority task
        scheduler.scheduleTask(new Task("Task-6", 6, () -> {
            simulateWork("Task-6", 1500);
        }));
        
        // High priority task
        scheduler.scheduleTask(new Task("Task-7", 8, () -> {
            simulateWork("Task-7", 1000);
        }));
        
        // Wait for all tasks to complete
        try {
            Thread.sleep(10000); // Give time for tasks to execute
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Shutdown gracefully
        System.out.println("\n=== Shutting Down ===\n");
        scheduler.shutdown();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Simulates task work by sleeping
     */
    private static void simulateWork(String taskName, long duration) {
        try {
            System.out.println("  → " + taskName + " is working for " + duration + "ms");
            Thread.sleep(duration);
            System.out.println("  ✓ " + taskName + " finished work");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("  ✗ " + taskName + " was interrupted");
        }
    }
}
