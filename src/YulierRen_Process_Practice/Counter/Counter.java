package YulierRen_Process_Practice.Counter;


/**
 * {@code @Auther:} YulierRen
 * 一个通过构造方法启动的sync测试类，对比两者的数据，体现sync的作用。
 */
public class Counter {
    public static void main(String[] args) {
//        new CounterTest_Sync();
        new CounterTest_Nosync();
    }
}