# simple-kinesis-sample
A simple application which sends (Producer) and receives (Consumer) data from Amazon Kinesis Stream. It uses/depends Kinesis Producer Library (KPL) and Kinesis Client Library (KCL).



###### Important Concepts: (https://docs.aws.amazon.com/streams/latest/dev/key-concepts.html)
* **Record**: is the data stored in a Kinesis stream. Data records are composed of a sequence number, partition key, and data blob, which is an immutable sequence of bytes. A data blob can be up to 1 MB.
* **Producer**: put records into Kinesis.
* **Consumer**: get records from Kinesis.
* **Shards**: is a uniquely identified group of data records in a stream. A stream is composed of one or more shards. Each shard can support:
  * Read rate: 5 transactions per second, up to 2 MB.
  * Write rate: 1,000 records per second, up to 1 MB.

Develop a sample client Kinesis application: https://docs.aws.amazon.com/streams/latest/dev/learning-kinesis.html
https://github.com/awslabs/amazon-kinesis-producer/tree/master/java/amazon-kinesis-producer-sample
https://github.com/awslabs/amazon-kinesis-learning

###### Kinesis Producer Library (KPL): https://docs.aws.amazon.com/streams/latest/dev/developing-producers-with-kpl.html
* It needs to configure the settings: Region, AWS Credentials, Kinesis Stream Name, and Partition Key (Shard name).

###### Kinesis Client Library (KCL): https://docs.aws.amazon.com/streams/latest/dev/kinesis-record-processor-implementation-app-java.html
* It needs to set the settings equal to the producer. In addition to defining: the consumer application name, and Initial Position in Stream.
* It is necessary to create a factory class to handle any existing shards (IRecordProcessorFactory).
* It is necessary to create a processor class to process the recordings (RecordProcessor).
* Checkpoint is used to mark what has already been read: https://docs.aws.amazon.com/streams/latest/dev/kinesis-record-processor-ddb.html.




Other interesting links:
https://docs.aws.amazon.com/streams/latest/dev/kinesis-kpl-writing.html
https://docs.aws.amazon.com/streams/latest/dev/learning-kinesis-module-one-consumer.html
https://docs.aws.amazon.com/streams/latest/dev/learning-kinesis-module-one.html
https://docs.aws.amazon.com/streams/latest/dev/kinesis-kpl-writing.html
https://docs.aws.amazon.com/streams/latest/dev/kinesis-kpl-config.html
https://docs.aws.amazon.com/streams/latest/dev/kinesis-record-processor-implementation-app-java.html
https://github.com/awslabs/amazon-kinesis-learning/blob/master/src/com/amazonaws/services/kinesis/samples/stocktrades/writer/StockTradesWriter.java
