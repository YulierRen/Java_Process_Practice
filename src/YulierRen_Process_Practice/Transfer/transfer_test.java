package YulierRen_Process_Practice.Transfer;

public class transfer_test {
    private static final int MAX_VALUE = 10000;
    private static final double init_value = 100000;
    private static final int balance_num = 10;
    private static final int step = 100;

    public static void main(String[]args) throws InterruptedException {
        var bank = new Bank(balance_num,init_value);
        Runnable r = ()->{
            for(int i=0;i<step;i++) {
                bank.transfer(0,i%10,MAX_VALUE);
                try {
                    Thread.sleep((long) (100*Math.random()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Runnable r2 = ()->{
            for(int i=0;i<step;i++) {
                bank.transfer(i%10,0,MAX_VALUE);
                try {
                    Thread.sleep((long) (100*Math.random()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        new Thread(r).start();
        new Thread(r2).start();
    }
}
