package com.timeless.utils;

/**
 * twitter 的分布式环境全局唯一id算法
 */
public class IdGenerateUtil {
    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long twepoch            = 1288834974657L;
    private long workerIdBits       = 5L;
    private long datacenterIdBits   = 5L;
    private long maxWorkerId        = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId    = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits       = 12L;
    private long workerIdShift      = sequenceBits;
    private long datacenterIdShift  = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask       = -1L ^ (-1L << sequenceBits); //4095
    private long lastTimestamp      = -1L;

    private static class IdGenHolder {
        private static final IdGenerateUtil instance = new IdGenerateUtil();
    }

    public static IdGenerateUtil get() {
        return IdGenHolder.instance;
    }

    public IdGenerateUtil() {
        this(0L, 0L);
    }

    public IdGenerateUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果上次生成时间和当前时间相同,在同一毫秒内
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }


}