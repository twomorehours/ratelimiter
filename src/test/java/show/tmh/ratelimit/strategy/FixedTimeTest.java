package show.tmh.ratelimit.strategy;

import org.junit.jupiter.api.Test;
import show.tmh.ratelimit.strategy.memory.FixedTimeWindowStrategy;

/**
 * TODO:DOCUMENT ME!
 *
 * @author yuhao
 * @date 2020/7/27 12:08 下午
 */
public class FixedTimeTest {

    @Test
    public void testFixedTime() throws InterruptedException {
        FixedTimeWindowStrategy strategy = new FixedTimeWindowStrategy(1, 1);
        for (int i = 0; i < 100000; i++) {
            if(strategy.tryAcquire()){
                System.out.println("pass");
                System.out.println(System.currentTimeMillis());
            }
            Thread.sleep(100);
        }
    }
}
