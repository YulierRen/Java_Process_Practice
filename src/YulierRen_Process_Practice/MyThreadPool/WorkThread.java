package YulierRen_Process_Practice.MyThreadPool;

import java.util.concurrent.BlockingQueue;

public class WorkThread extends Thread {
    BlockingQueue<Runnable> queue;

    public WorkThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        while (!Thread.interrupted() && !queue.isEmpty()) {
            try {
                Runnable task = queue.take();
                task.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
