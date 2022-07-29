/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package producers.wso2event.performance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.databridge.agent.AgentHolder;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.agent.exception.DataEndpointException;
import org.wso2.carbon.databridge.commons.Event;
import org.wso2.carbon.databridge.commons.StreamDefinition;
import producers.wso2event.DataPublisherUtil;

import java.text.DecimalFormat;
import java.util.Random;

public class RunnableImpression implements Runnable{
    private static Log log = LogFactory.getLog(RunnableImpression.class);

    private  DataPublisher dataPublisherI;
    private  StreamDefinition streamDefinitionI;
    private int eventsI;
    private int delayI;

    public RunnableImpression(DataPublisher dataPublisherI, StreamDefinition streamDefinitionI, int
            eventsI, int delayI) {

        try {
            System.out.println("Starting WSO2 Event ClientImpression");
            AgentHolder.setConfigPath("/Users/ramilu/wso2/git/sample/src/main/java/wso2event/files/configs/data-agent" +
                                      "-config.xml");
            DataPublisherUtil.setTrustStoreParams();

            this.dataPublisherI = dataPublisherI;
            this.streamDefinitionI = streamDefinitionI;
            this.eventsI = eventsI;
            this.delayI = delayI;


        } catch (Throwable e) {
            log.error(e);
        }
    }

    public void run() {
        try {
            Random r = new Random();
            Random r2 = new Random();
            Random r3 = new Random();
            Random r4 = new Random();
            int elapsedCount = 10000;
            long count = 0;
            long lastTime = System.currentTimeMillis();
            DecimalFormat decimalFormat = new DecimalFormat("#");
            while (count < eventsI) {
                Object[] meta  = new Object[4];
                String tenantId = "t"+(r.nextInt(10));
                String eventId = "e"+count;
                long timestamp = System.currentTimeMillis();
                int tz = 0;

                meta[0] = tenantId;
                meta[1] = eventId;
                meta[2] = timestamp;
                meta[3] = tz;

                Object[] payload = new Object[3];
                String userId = "user"+(r2.nextInt(10000));
                String domainId = "domain"+(r3.nextInt(10000));
                String creativeId = "creative"+(r4.nextInt(1000));

                payload[0] = userId;
                payload[1] = domainId;
                payload[2] = creativeId;

                Event event = new Event(streamDefinitionI.getStreamId(), System.currentTimeMillis(), meta, null,
                                        payload);

                dataPublisherI.publish(event);

                if (count % elapsedCount == 0) {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - lastTime;
                    double throughputPerSecond = (((double) elapsedCount) / elapsedTime) * 1000;
                    lastTime = currentTime;
                    log.info("Sent " + elapsedCount + " sensor events in " + elapsedTime +
                             " milliseconds with total throughput of " + decimalFormat.format(throughputPerSecond) +
                             " events per second.");
                }

                if (delayI > 0) {
                    Thread.sleep(delayI);
                }
                count++;
            }
            Thread.sleep(2000);
            dataPublisherI.shutdownWithAgent();
        } catch (InterruptedException e) {
            log.error("Thread interrupted while sleeping between eventsH", e);
        } catch (DataEndpointException e) {
            e.printStackTrace();
        }
    }
}
