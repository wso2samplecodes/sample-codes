package java_codes;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class JavaInitials {
    private static final Logger log = LoggerFactory.getLogger(JavaInitials.class);
    public static void main(String[] args) {
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
//                new ThreadFactoryBuilder().setPriority(7).setNameFormat("SchedulePersistence-%d").build());
//        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new PersistenceManager(),
//                5, 5, TimeUnit.SECONDS);


        log.info((new Date(System.currentTimeMillis())).toString());
    }

}
