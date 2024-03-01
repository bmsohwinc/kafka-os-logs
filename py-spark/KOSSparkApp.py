from pyspark.sql import SparkSession
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd


def get_metrics():
    spark = SparkSession.builder\
        .appName('KOSSparkApp')\
        .getOrCreate()

    df = spark.read \
        .format("jdbc") \
        .option("driver","com.mysql.jdbc.Driver") \
        .option("url", "jdbc:mysql://localhost:3306/kos") \
        .option("dbtable", "parsedoslog_entry") \
        .option("user", "root") \
        .option("password", "your_mysql_password") \
        .load()

    # df.filter('process_name="kernel:"').show()

    df.groupBy('process_name').count().orderBy('count', ascending=False).show()
    df.groupBy('service_name').count().orderBy('count', ascending=False).show()

    # df.select(['log_date_time', 'service_name', 'process_name']).orderBy('log_date_time').show()

    df.groupBy('log_date_time', 'process_name').count().orderBy('log_date_time').show()

    df_process_cnt = df.groupBy('process_name').count().orderBy('count', ascending=False).toPandas()
    df_service_cnt = df.groupBy('service_name').count().orderBy('count', ascending=False).toPandas()

    # df.select(['log_date_time', 'service_name', 'process_name']).orderBy('log_date_time').show()

    df_timeseries_cnt = df.groupBy('log_date_time', 'process_name').count().orderBy('log_date_time').toPandas()

    return [df_process_cnt, df_service_cnt, df_timeseries_cnt]


df_process_cnt, df_service_cnt, df_timeseries_cnt = get_metrics()

# preprocess
df_process_cnt['process_name'] = df_process_cnt['process_name'].str[:12]
df_timeseries_cnt['process_name'] = df_timeseries_cnt['process_name'].str[:12]

plt.figure(figsize=(15,8))
proc_bar_plot = sns.barplot(data=df_process_cnt, x='process_name', y='count')
proc_bar_plot.set_xticklabels(proc_bar_plot.get_xticklabels(), rotation=15)
fig = proc_bar_plot.get_figure()
fig.savefig("proc_bar_plot.png") 


plt.figure(figsize=(15,8))
proc_timeseries_plot = sns.scatterplot(data=df_timeseries_cnt, x='log_date_time', y='count', hue='process_name')
proc_timeseries_plot.set_xticklabels(proc_timeseries_plot.get_xticklabels(), rotation=15)
fig = proc_timeseries_plot.get_figure()
fig.savefig("proc_timeseries_plot.png") 


plt.figure(figsize=(15,8))
proc_timeseries_plot_2 = sns.lineplot(data=df_timeseries_cnt, x='log_date_time', y='count', hue='process_name')
proc_timeseries_plot_2.set_xticklabels(proc_timeseries_plot_2.get_xticklabels(), rotation=15)
fig = proc_timeseries_plot_2.get_figure()
fig.savefig("proc_timeseries_plot_2.png") 
