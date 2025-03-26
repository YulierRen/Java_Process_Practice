package YulierRen_Process_Practice.Counter;

class CounterTest_Nosync {
    int count = 0;

    public void count_add() {
        count++;
    }

    public CounterTest_Nosync() {
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
        //count的值由于add操作的非原子性导致操作丢失，导致结果不准确。
        //其实数据是对的，但这个应该有问题，所以我就这么写了
    }
}
