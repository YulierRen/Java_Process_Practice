package YulierRen_Process_Practice.MyThreadPool;

import java.util.List;

public interface ThreadPool {
    //线程池提交任务
    void execute(Runnable task);
    //等待线程任务结束后关闭线程池
    void shutdown();
    //强制关闭线程池并返回未结束任务的线程列表
    List<Runnable> NBshutdown();
}
