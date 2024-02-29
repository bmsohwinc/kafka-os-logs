from pyspark.sql import SparkSession
from pyspark.sql.protobuf.functions import from_protobuf, to_protobuf

def spark_simple():
    logFile = '/home/bms/projects/my-spark/spark-3.5.0-bin-hadoop3/README.md'
    spark = SparkSession.builder.appName("KOSLogAnalyzer").getOrCreate()
    logData = spark.read.text(logFile).cache()

    numAs = logData.filter(logData.value.contains('a')).count()
    numBs = logData.filter(logData.value.contains('b')).count()

    print(f"Lines with a: {numAs}, lines with b: {numBs}")

    spark.stop()
    return

def spark_main():
    spark = SparkSession.builder.appName("KOSLogAnalyzer").getOrCreate()
    df = spark\
        .readStream\
        .format("kafka")\
        .option("kafka.bootstrap.servers", "127.0.0.1:9092")\
        .option("subscribe", "streams-test-output-2")\
        .load()
    
    descriptorFilePath = 'KOSParsed.desc'
    output = df\
        .select(from_protobuf("value", "KOSLogEntry", descriptorFilePath).alias("kosLog"))

    writing_df = output.writeStream \
        .format("console") \
        .start()
    
    writing_df.awaitTermination()

spark_main()

