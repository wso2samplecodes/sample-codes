package producers.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by ramindu on 1/9/17.
 */
public class KafkaProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        String FILENAME = "/Users/ramindu/Desktop/sampledata.txt";
        byte[] encoded = new byte[0];
        try {
//            encoded = Files.readAllBytes(Paths.get(FILENAME));
            File file = new File(FILENAME);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            Producer<String, Object> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, Object>(props);
            while ((line = br.readLine()) != null) {
                producer.send(new ProducerRecord<String, Object>("account_topic", line));
            }
            fr.close();    //closes the stream and release the resources
            System.out.println("Published all events in the file via Kafka.");
            producer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
