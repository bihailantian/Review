//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.xxm.review.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 雪花id生成器
 */
public class SnowflakeIdGenerator {
    //private static final Logger log = LoggerFactory.getLogger(SnowflakeIdGenerator.class);
    private final long workerId;
    private final long dataCenterId;
    private long sequence = 0L;
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = 31L;
    private final long maxDataCenterId = 31L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = 12L;
    private final long datacenterIdShift = 17L;
    private final long timestampLeftShift = 22L;
    private final long sequenceMask = 4095L;
    private long lastTimestamp = -1L;
    private static final SnowflakeIdGenerator generator;

    public static SnowflakeIdGenerator getInstance() {
        return generator;
    }

    public static SnowflakeIdGenerator create(int workerId, int dataCenterId) {
        return new SnowflakeIdGenerator((long) workerId, (long) dataCenterId);
    }

    public static SnowflakeIdGenerator create() {
        return create(ThreadLocalRandom.current().nextInt(31), ThreadLocalRandom.current().nextInt(31));
    }

    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        if (workerId <= 31L && workerId >= 0L) {
            if (dataCenterId <= 31L && dataCenterId >= 0L) {
                this.workerId = workerId;
                this.dataCenterId = dataCenterId;
                //log.info("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}", new Object[]{22L, 5L, 5L, 12L, workerId});
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            //log.warn("clock is moving backwards {}.", this.lastTimestamp);
        }

        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 4095L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTimestamp = timestamp;
        return timestamp - 1288834974657L << 22 | this.dataCenterId << 17 | this.workerId << 12 | this.sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for (timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    static {
        Random random = new SecureRandom();
        long workerId = Long.getLong("id-worker", (long) random.nextInt(31));
        long dataCenterId = Long.getLong("id-datacenter", (long) random.nextInt(31));
        generator = new SnowflakeIdGenerator(workerId, dataCenterId);
    }
}
