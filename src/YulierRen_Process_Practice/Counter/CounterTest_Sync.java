package YulierRen_Process_Practice.Counter;

class CounterTest_Sync {

    static int count = 0;

    public static synchronized void count_add() {
        count++;
    }

    public CounterTest_Sync() {
        Runnable runnable = () -> {
            count_add();
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(count);
        };

        for (int i = 0; i < 10000; i++) {
            new Thread(runnable).start();
        }
        //看结果，数据不是规律增加的，说明在一个线程的add操作过后，
        //在它睡眠的这段时间内，有其他线程对count进行add操作，导致数据呈现跳跃式增加，说明运行方式为并行，
        //并且最终结果是10000,说明count_add的原子性得到保证。
    }
}
