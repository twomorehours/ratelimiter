package show.tmh.ratelimit.strategy.memory;

import com.google.common.base.Stopwatch;
import show.tmh.ratelimit.strategy.RateLimitStrategy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 滑动窗口限流策略
 *
 * @author yuhao
 * @date 2020/7/27 11:52 上午
 */
public class FixedTimeWindowStrategy implements RateLimitStrategy {

    private int unit;
    private int limit;
    private Lock lock = new ReentrantLock();
    private AtomicInteger counter = new AtomicInteger();
    private Stopwatch stopwatch = Stopwatch.createStarted();

    /**
     * 限流
     *
     * @param unit  几秒
     * @param limit 几次
     */
    public FixedTimeWindowStrategy(int unit, int limit) {
        this.unit = unit;
        this.limit = limit;
    }

    @Override
    public boolean tryAcquire() {
        if (counter.incrementAndGet() <= limit) {
            return true;
        }
        lock.lock();
        try {
            if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > unit * 1000) {
                stopwatch.reset();
                stopwatch.start();
                counter.set(0);
            }
            return counter.incrementAndGet() <= limit;
        } finally {
            lock.unlock();
        }
    }
}
