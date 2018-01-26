package kinesis;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.ShutdownReason;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.model.Record;
import kinesis.other.SampleProducer;

import java.util.ArrayList;
import java.util.List;

public class SimpleConsumer implements IRecordProcessorFactory {

    private class RecordProcessor implements IRecordProcessor {
        @Override
        public void initialize(String shardId) {}

        @Override
        public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {

            for (Record r : records) {
                // Get the timestamp of this run from the partition key.
                System.out.println( "### PartitionKey: " + r.getPartitionKey());

                // Extract the sequence number. It's encoded as a decimal
                // string and placed at the beginning of the record data,
                // followed by a space. The rest of the record data is padding
                // that we will simply discard.
                try {
                    byte[] b = new byte[r.getData().remaining()];
                    r.getData().get(b);
                    System.out.println(new String(b, "UTF-8"));
                } catch (Exception e) {
                    System.out.println("Error parsing record" + e);
                    System.exit(1);
                }
            }

            try {
                checkpointer.checkpoint();
            } catch (Exception e) {
                System.out.println("Error while trying to checkpoint during ProcessRecords" + e);
            }
        }

        @Override
        public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
            System.out.println("Shutting down, reason: " + reason);
            try {
                checkpointer.checkpoint();
            } catch (Exception e) {
                System.out.println("Error while trying to checkpoint during Shutdown" +  e);
            }
        }
    }

    @Override
    public IRecordProcessor createProcessor() {
        return this.new RecordProcessor();
    }

    public static void main(String[] args) throws Exception {
        KinesisClientLibConfiguration config =
                new KinesisClientLibConfiguration(
                        "KinesisProducerLibSampleConsumer",
                        SampleProducer.STREAM_NAME,
                        new ProfileCredentialsProvider("eis-deliverydevqa"),
                        "KinesisProducerLibSampleConsumer")
                        .withRegionName(SampleProducer.REGION)
                        .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

        final SimpleConsumer consumer = new SimpleConsumer();

        new Worker.Builder()
                .recordProcessorFactory(consumer)
                .config(config)
                .build()
                .run();

        System.out.println("Finished.");

    }
}
