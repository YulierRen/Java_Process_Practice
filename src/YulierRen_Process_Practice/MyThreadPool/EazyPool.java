package YulierRen_Process_Practice.MyThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EazyPool implements ThreadPool {

    int InitThreadNum;
    BlockingQueue<Runnable> Eazyqueue;
    List<WorkThread> workList;
    Boolean isShutdown;

    public EazyPool(int InitThreadNum) {
        this.InitThreadNum = InitThreadNum;
        this.Eazyqueue = new LinkedBlockingQueue<>();
        this.workList = new ArrayList<>(InitThreadNum);
        this.isShutdown = false;

        for(int i = 0; i < InitThreadNum; i++) {
            WorkThread workThread = new WorkThread(Eazyqueue);
            workThread.start();
            workList.add(workThread);
        }
    }

    @Override
    public void execute(Runnable task) {
        if(isShutdown) {
            throw new IllegalStateException("ThreadPool has been shutdown");
        }
        Eazyqueue.offer(task);
    }

    @Override
    public void shutdown() {
        isShutdown = true;
        for(WorkThread workThread : workList) {
            workThread.interrupt();
        }
    }

    @Override
    public List<Runnable> NBshutdown() {
        isShutdown = true;
        List<Runnable> taskList = new ArrayList<>();
        Eazyqueue.drainTo(taskList);
        for(WorkThread workThread : workList) {
            workThread.interrupt();
        }
        return taskList;
    }
}
