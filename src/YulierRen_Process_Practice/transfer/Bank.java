package YulierRen_Process_Practice.transfer;

import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    double []balance;
    int ac_num;
    ReentrantLock lock = new ReentrantLock();//尝试注释这一行，看看会不会影响结果

    public Bank(int ac_num,double init_balance) {
        this.ac_num = ac_num;
        balance = new double[ac_num];
        for(int i=0;i<ac_num;i++) {
            balance[i]=init_balance;
        }
    }

    public void transfer(int from,int to,double amount) {
        try{
            lock.lock();
            if(balance[from]<amount) {
                System.out.println("Insufficient balance");
                return;
            }
            balance[from]-=amount;//不是原子操作，需要加锁
            Thread.sleep((long) (Math.random()*1000));
            balance[to]+=amount;
            System.out.println(getTotalBalance());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

    public double getTotalBalance() {
        double total=0;
        for(int i=0;i<ac_num;i++) {
            total+=balance[i];
        }
        return total;
    }
}
