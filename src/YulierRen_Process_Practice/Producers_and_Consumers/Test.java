package YulierRen_Process_Practice.Producers_and_Consumers;


import java.util.Queue;

/**
 * @Author: YulierRen
 * 这是一个基于对象的关于锁的方法实现生产者消费者模型的例子
 * 可以调整生产者和消费者的run方法制造供大于求或者供不应求的情况
 * 通过一个定时标志控制线程的启动和停止，可以模拟生产者和消费者的交替生产和消费
 */
public class Test {
    private final static ProductRepository repo = new ProductRepository(600);
    private final static Producer producer = new Producer(repo, 200);
    private final static Consumer consumer = new Consumer(repo, 200);

    static volatile boolean flag = false;

    public static void main(String[]args) throws InterruptedException {
        Runnable r = ()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true;
        };
        new Thread(r).start();
        while(!flag) {
            Thread.sleep(100);
            Runnable pr = ()->{
                producer.run();
            };
            new Thread(pr).start();
            Runnable cr = ()->{
                consumer.run();
            };
            new Thread(cr).start();
        }
    }
}

//产品仓库
class ProductRepository {
    private final static int MAX_NUM=2000;
    private final Object lock = new Object();
    private int current_num;

    public ProductRepository(int num) {
        current_num=num;
    }

    public int getCurrent_num() {
        return current_num;
    }

    public void setCurrent_num(int current_num) {
        this.current_num = current_num;
    }

    public void entry(int num){
        synchronized (lock) {
            try{
                while(current_num+num>MAX_NUM){
                    System.out.println(Thread.currentThread().getName()+"由于仓满，暂时不能入账，进入等待池");
                    lock.wait();
                }
                current_num+=num;
                System.out.println("仓库中增加了"+num+"件商品，当前库存为"+current_num);
                lock.notifyAll();
                System.out.println("等待池中线程释放进入锁池");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disburse(int num){
        synchronized (lock) {
            try{
                while(current_num<num){
                    System.out.println(Thread.currentThread().getName()+"由于缺货，暂时不能出账，进入等待池");
                    lock.wait();
                }
                current_num-=num;
                System.out.println("仓库中减少了"+num+"件商品，当前库存为"+current_num);
                lock.notifyAll();
                System.out.println("等待池中线程释放进入锁池");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
//生产者
class Producer implements Runnable{
    private final ProductRepository repo;
    private final int num;

    public Producer(ProductRepository repo, int num) {
        this.repo = repo;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Math.round(Math.random()*1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repo.entry(num);
    }
}
//消费者
class Consumer implements Runnable{
    private final ProductRepository repo;
    private final int num;

    public Consumer(ProductRepository repo, int num) {
        this.repo = repo;
        this.num = num;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Math.round(Math.random()*100));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repo.disburse(num);

    }
}







