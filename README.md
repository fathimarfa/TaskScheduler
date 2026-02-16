Task Scheduling System (Java)

A console-based Task Scheduling System built in Java that schedules and executes tasks concurrently based on priority. The system uses a thread-safe priority queue and a fixed-size thread pool to efficiently manage task execution.

---

## Features

Priority-based task scheduling

Concurrent task execution using a thread pool

Thread-safe scheduling logic

In-memory task storage (no database)

Console logging for task lifecycle



---

## Tech Stack

Java

PriorityQueue

ExecutorService

Multithreading & Synchronization



---

## Core Components

1. Task

Represents a unit of work to be executed.

Attributes:

taskId – unique identifier

priority – determines execution order (higher value = higher priority)

Runnable action – logic to be executed


The Task class implements Comparable<Task> to support priority-based ordering.


---

2. TaskScheduler

Responsible for scheduling and executing tasks.

Responsibilities:

Accept new tasks

Maintain a priority queue

Ensure thread-safe access to the queue

Execute tasks using a fixed thread pool


Thread safety is ensured using synchronized blocks while accessing shared resources.


---

3. Main

Entry point of the application.

Creates tasks

Submits them to the scheduler

Demonstrates concurrent execution



---

## Thread Safety

All access to the shared PriorityQueue is synchronized

Task execution is handled by ExecutorService

Prevents race conditions during task scheduling



---

📂 Project Structure

task-scheduler/
 ├── src/
 │   ├── Task.java
 │   ├── TaskScheduler.java
 │   └── Main.java
 └── README.md

---

✅ Future Enhancements

Delayed task scheduling

Task cancellation

Dynamic thread pool sizing

Metrics & monitoring
