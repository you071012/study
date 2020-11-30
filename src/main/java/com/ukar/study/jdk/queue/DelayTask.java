package com.ukar.study.jdk.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayTask implements Delayed {

    /**
     * 任务名称
     */
    private String name;

    /**
     * 执行时间
     */
    private long time;

    public DelayTask(String name, long time) {
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0 ? time : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long now = System.currentTimeMillis();
        return unit.convert(time - now, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DelayTask{" +
                "name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
