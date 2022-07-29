package java_codes;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class PersistenceManager implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(java_codes.PersistenceManager.class);
    private AtomicLong sequenceIDGenerator;
    private GenericKeyedObjectPool tcpConnectionPool;
    private static int count = 0;

    public PersistenceManager() {
    }

    @Override
    public void run() {
        count++;
        if (count == 3) {
            throw new RuntimeException("Runtime Exception !!");
        } else {
            log.info("PersistenceManager ran !!");
        }
    }
}
