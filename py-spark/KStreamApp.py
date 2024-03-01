from pyspark.sql import SparkSession
from pyspark.sql import Row
from pyspark.sql.protobuf.functions import from_protobuf

from datetime import datetime, date


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
    
    # output.show()
    # output.printSchema()

    output_cnt = output.count()
    writing_df = output_cnt.writeStream \
        .format("console") \
        .outputMode('complete')\
        .option("truncate", "False") \
        .start()
    
    writing_df.awaitTermination()
    spark.stop()
    return

def spark_test():
    spark = SparkSession.builder.appName("KOSLogAnalyzer").getOrCreate()
    df = spark.createDataFrame([
        Row(a=1, b=4., c='GFG1', d=date(2000, 8, 1),
            e=datetime(2000, 8, 1, 12, 0)),
    
        Row(a=2, b=8., c='GFG2', d=date(2000, 6, 2), 
            e=datetime(2000, 6, 2, 12, 0)),
    
        Row(a=4, b=5., c='GFG3', d=date(2000, 5, 3),
            e=datetime(2000, 5, 3, 12, 0))
    ])
    
    df.show()
    spark.close()
    return

spark_main()

# spark_test()
