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
import org.wso2.carbon.databridge.commons.StreamDefinition;
import producers.wso2event.DataPublisherUtil;

import java.util.Map;

public class PerformanceImpression {
    private static Log log = LogFactory.getLog(PerformanceImpression.class);

    static DataPublisher dataPublisherI;
    static StreamDefinition streamDefinitionI;
    static int eventsI;
    static int delayI;


    public static void main(String[] args) {

//        System.out.println(Arrays.deepToString(args));
        try {

            System.out.println("Starting WSO2 Event ClientImpression");
            AgentHolder.setConfigPath("/Users/ramilu/wso2/git/sample/src/main/java/files/configs/data-agent" +
                                      "-config.xml");
            DataPublisherUtil.setTrustStoreParams();
            String protocol = "thrift";
            String host = "localhost";
            String port = "7611";
            String username = "admin";
            String password = "admin";
            //String sampleNumber = args[6];
            String filePath;
            //filePath = DataPublisherUtil.getEventFilePath(sampleNumber, streamId, filePath);
            //create data publisher
            dataPublisherI = new DataPublisher(protocol, "tcp://" + host + ":" + port, null, username, password);

            String streamIdI = "impressionStream:1.0.0";
            String streamIdH = "hitStream:1.0.0";

            Map<String, StreamDefinition> streamDefinitions = DataPublisherUtil.loadStreamDefinitions();
            streamDefinitionI = streamDefinitions.get(streamIdI);

            eventsI = 1000000;
            delayI = 1;

            if (streamDefinitionI == null) {
                throw new Exception("StreamDefinition not available for stream " + streamIdI);
            } else {
                log.info("StreamDefinition used :" + streamDefinitionI);
            }

            Runnable impressionPublisher = new RunnableImpression(dataPublisherI, streamDefinitionI, eventsI, delayI);
            Thread t2 = new Thread(impressionPublisher);
            t2.start();

        } catch (Throwable e) {
            log.error(e);
        }
    }
}
