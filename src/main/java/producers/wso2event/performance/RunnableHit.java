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

import java.util.Random;

public class RunnableHit implements Runnable{
    private static Log log = LogFactory.getLog(RunnableHit.class);

    private DataPublisher dataPublisherH;
    private StreamDefinition streamDefinitionH;
    private int eventsH;
    private int delayH;

    public RunnableHit(DataPublisher dataPublisherH, StreamDefinition streamDefinitionH, int eventsH, int delayH) {

        try {
            System.out.println("Starting WSO2 Event ClientImpression");
            AgentHolder.setConfigPath("/Users/ramilu/wso2/git/sample/src/main/java/wso2event/files/configs/data-agent" +
                                      "-config.xml");
            DataPublisherUtil.setTrustStoreParams();
            this.dataPublisherH = dataPublisherH;
            this.streamDefinitionH = streamDefinitionH;
            this.eventsH = eventsH;
            this.delayH = delayH;

        } catch (Throwable e) {
            log.error(e);
        }
    }

    public void run() {
        try {
            Random r = new Random();
            Random r2 = new Random();

            while (eventsH != 0) {
                Object[] meta  = new Object[4];
                String tenantId = "t"+(r.nextInt(10));
                String eventId = "e"+eventsH;
                long timestamp = System.currentTimeMillis();
                int tz = 0;

                meta[0] = tenantId;
                meta[1] = eventId;
                meta[2] = timestamp;
                meta[3] = tz;

                Object[] payload = new Object[3];
                String userId = "user"+(r2.nextInt(10000));

                payload[0] = userId;

                Event event = new Event(streamDefinitionH.getStreamId(), System.currentTimeMillis(), meta, null,
                                        payload);

                dataPublisherH.publish(event);

                if (delayH > 0) {
                    Thread.sleep(delayH);
                }
                eventsH--;
            }
            dataPublisherH.shutdownWithAgent();

        } catch (InterruptedException e) {
            log.error("Thread interrupted while sleeping between eventsH", e);
        } catch (DataEndpointException e) {
            e.printStackTrace();
        }
    }
}
