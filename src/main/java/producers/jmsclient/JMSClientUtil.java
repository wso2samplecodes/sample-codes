/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package producers.jmsclient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JMSClientUtil {
    private static Log log = LogFactory.getLog(JMSClientUtil.class);
    static String sampleDirectoryPath = ".." + File.separator + ".." + File.separator + ".." + File.separator +
                                        "samples" + File.separator + "artifacts" + File.separator + "sampleNumber" + File.separator;

    /**
     * This method will construct the directory path of the data file
     *
     * @param sampleNumber Number of the sample which is running currently
     * @param format       Format of the file (ex: csv, txt)
     * @param topic        topic of the message to be sent (the data file should be named with the topic)
     * @param filePath     file path if a sample if not running
     */
    public static String getEventFilePath(String sampleNumber, String format, String topic, String filePath)
            throws Exception {
        if (sampleNumber != null && sampleNumber.length() == 0) {
            sampleNumber = null;
        }

        if (filePath != null && filePath.length() == 0) {
            filePath = null;
        }

        String resultingFilePath;
        if (filePath != null && sampleNumber == null) {
            resultingFilePath = filePath;
        } else if (filePath == null && sampleNumber != null) {
            if (format.equalsIgnoreCase("csv")) {
                resultingFilePath = sampleDirectoryPath.replace("sampleNumber", sampleNumber) + topic + ".csv";
            } else {
                resultingFilePath = sampleDirectoryPath.replace("sampleNumber", sampleNumber) + topic + ".txt";
            }
        } else {
            throw new Exception("In sampleNumber:'" + sampleNumber + "' and filePath:'" + filePath
                                + "' one must be null and other not null");
        }
        File file = new File(resultingFilePath);
        if (!file.isFile()) {
            throw new Exception("'" + resultingFilePath + "' is not a file");
        }
        if (!file.exists()) {
            throw new Exception("file '" + resultingFilePath + "' does not exist");
        }
        return resultingFilePath;
    }

    /**
     * Messages will be read from the given filepath and stored in the array list (messagesList)
     *
     * @param filePath Text file to be read
     */
    public static List<String> readFile(String filePath) {
        BufferedReader bufferedReader = null;
        StringBuffer message = new StringBuffer("");
        final String asterixLine = "*****";
        List<String> messagesList = new ArrayList<String>();
        try {

            String line;
            bufferedReader = new BufferedReader(new FileReader(filePath));
            while ((line = bufferedReader.readLine()) != null) {
                if ((line.equals(asterixLine.trim()) && !"".equals(message.toString().trim()))) {
                    messagesList.add(message.toString());
                    message = new StringBuffer("");
                } else {
                    message = message.append(String.format("\n%s", line));
                }
            }
            if (!"".equals(message.toString().trim())) {
                messagesList.add(message.toString());
            }
        } catch (FileNotFoundException e) {
            log.error("Error in reading file " + filePath, e);
        } catch (IOException e) {
            log.error("Error in reading file " + filePath, e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                log.error("Error occurred when closing the file : " + e.getMessage(), e);
            }
        }
        return messagesList;
    }

    /**
     * Each message will be divided into groups and create the map message
     *
     * @param producer     Used for sending messages to a destination
     * @param session      Used to produce the messages to be sent
     * @param messagesList List of messages to be sent
     *                     individual message event data should be in
     *                     "attributeName(attributeType):attributeValue" format
     */
    public static void publishMapMessage(MessageProducer producer, Session session, List<String> messagesList)
            throws IOException, JMSException {
        String regexPattern = "(.*)\\((.*)\\):(.*)";
        Pattern pattern = Pattern.compile(regexPattern);
        for (String message : messagesList) {
            MapMessage mapMessage = session.createMapMessage();
            for (String line : message.split("\\n")) {
                if (line != null && !line.equalsIgnoreCase("")) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        mapMessage.setObject(matcher.group(1), parseAttributeValue(matcher.group(2), matcher.group(3)));
                    }
                }
            }
            producer.send(mapMessage);
        }
    }

    /**
     * Each message will be divided into groups and create the map message
     *
     * @param producer     Used for sending messages to a destination
     * @param session      Used to produce the messages to be sent
     * @param messagesList List of messages to be sent
     */
    public static void publishTextMessage(MessageProducer producer, Session session, List<String> messagesList)
            throws JMSException {
        for (String message : messagesList) {
            TextMessage jmsMessage = session.createTextMessage();
            jmsMessage.setText(message);
            producer.send(jmsMessage);
        }
    }

    public static void publishTextMessage(MessageProducer producer, Session session)
            throws JMSException {
        String message = "<ns0:Envelope xmlns:ns0=\"http://www.wso2.com/cep/env/component\">\n" + "    <ns0:Header>\n"
                + "        <ns1:eventHeader xmlns:ns1=\"http://www.wso2.com/cep/env/header\" eventID=\"ID1\" eventName=\"wso2Event\">10</ns1:eventHeader>\n"
                + "    </ns0:Header>\n" + "    <ns0:Body>\n" + "        <something>\n"
                + "            <ns1:Schedule xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso2\" located=\"sri lanka\">\n"
                + "                <ns1:Equip carrIataCd=\"CO\"/>\n" + "            </ns1:Schedule>\n"
                + "            <ns1:Operate xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso22\" located=\"sri lankaa\">\n"
                + "                <ns1:Equip carrIataCd=\"CO\"/>\n" + "            </ns1:Operate>\n"
                + "        </something>\n" + "        <something>\n"
                + "            <ns1:Schedule xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso23\" located=\"sri lanka\">\n"
                + "                <ns1:Equip carrIataCd=\"CO\"/>\n" + "            </ns1:Schedule>\n"
                + "            <ns1:Operate xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso223\" located=\"sri lankaa\">\n"
                + "                <ns1:Equip carrIataCd=\"CO\"/>\n" + "            </ns1:Operate>\n"
                + "        </something>\n" + "    </ns0:Body>\n" + "</ns0:Envelope>";

//        message = "<Keys xmlns=\"http://ws.wso2.org/dataservice\">\n" + "\n" + "  <Key>\n" + "     <P_Id>1</P_Id>\n"
//                + "     <LastName>Soysa</LastName>\n" + "     <FirstName>Amani</FirstName>\n"
//                + "     <Address>361 Kotte Road Nugegoda</Address>\n" + "     <City>Colombo</City>\n" + "  </Key>\n"
//                + "  <Key>\n" + "     <P_Id>2</P_Id>\n" + "     <LastName>Bishop</LastName>\n"
//                + "     <FirstName>Peter</FirstName>\n" + "     <Address>300 Technology BuildingHouston</Address>\n"
//                + "     <City>London</City>\n" + "  </Key>\n" + "  <Key>\n" + "     <P_Id>3</P_Id>\n"
//                + "     <LastName>Clark</LastName>\n" + "     <FirstName>James</FirstName>\n"
//                + "     <Address>Southampton</Address>\n" + "     <City>London</City>\n" + "  </Key>\n" + "  <Key>\n"
//                + "     <P_Id>4</P_Id>\n" + "     <LastName>Carol</LastName>\n" + "     <FirstName>Dilan</FirstName>\n"
//                + "     <Address>A221 LSRC Box 90328 </Address>\n" + "     <City>Durham</City>\n" + "  </Key>\n"
//                + "</Keys>";

        TextMessage jmsMessage = session.createTextMessage();
        jmsMessage.setText(message);
        producer.send(jmsMessage);
    }

    public static void publishFLIFOTextMessage(MessageProducer producer, Session session)
            throws JMSException {

//        String fileDirectory = "/Users/ramindu/Desktop/UnitedWork/data/FLIFO/ems/testORDOn.xml";
                    String fileDirectory = "/Users/ramindu/Desktop/UnitedWork/data/FLIFO/Flight1163/4.xml";

        File dir = new File(fileDirectory);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dir);
            byte[] data = new byte[(int) dir.length()];
            fis.read(data);
            fis.close();
            String message = new String(data, "UTF-8");

            TextMessage jmsMessage = session.createTextMessage();
            jmsMessage.setText(message);
            producer.send(jmsMessage);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private static Object parseAttributeValue(String type, String value) {
        if (type.equals("bool")) {
            return Boolean.parseBoolean(value);
        } else if (type.equals("int")) {
            return Integer.parseInt(value);
        } else if (type.equals("long")) {
            return Long.parseLong(value);
        } else if (type.equals("float")) {
            return Float.parseFloat(value);
        } else if (type.equals("double")) {
            return Double.parseDouble(value);
        }
        return value;
    }

}
