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
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.commons.StreamDefinition;
import server.Wso2EventServer;

public class PerformanceWSO2Server {
    private static Log log = LogFactory.getLog(PerformanceWSO2Server.class);

    static DataPublisher dataPublisherH;
    static StreamDefinition streamDefinitionH;
    static int eventsH;
    static int delayH;

    static DataPublisher dataPublisherI;
    static StreamDefinition streamDefinitionI;
    static int eventsI;
    static int delayI;


    public static void main(String[] args) {

//        System.out.println(Arrays.deepToString(args));
        try {

            // The data-bridge receiver
            Wso2EventServer agentServerResultStream = new Wso2EventServer(8461, false);
            Thread agentServerThread = new Thread(agentServerResultStream);
            agentServerThread.start();




        } catch (Throwable e) {
            log.error(e);
        }
    }
}
