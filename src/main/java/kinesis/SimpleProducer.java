package kinesis;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import kinesis.other.SampleProducer;

import java.nio.ByteBuffer;

public class SimpleProducer {

    public static void main(String[] args) throws Exception {

        KinesisProducerConfiguration config = new KinesisProducerConfiguration();
        config.setRegion(SampleProducer.REGION);
        config.setCredentialsProvider(new ProfileCredentialsProvider("eis-deliverydevqa"));
        config.setMaxConnections(1);
        config.setRequestTimeout(90000);
        config.setRecordMaxBufferedTime(15000);

        KinesisProducer kinesis = new KinesisProducer(config);

        // Put some records
        for (int i = 0; i < 100; ++i) {
            String myData = "[DEMO]startMyData" + i + "endMyData";
            ByteBuffer data = ByteBuffer.wrap(myData.getBytes("UTF-8"));
            // doesn't block
            kinesis.addUserRecord(SampleProducer.STREAM_NAME, "myPartitionKey", data);
            System.out.println(myData);
        }

        System.out.println("Waiting for remaining puts to finish...");
        kinesis.flushSync();
        System.out.println("All records complete.");

        // This kills the child process and shuts down the threads managing it.
        kinesis.destroy();
        System.out.println("Finished.");
    }

}
