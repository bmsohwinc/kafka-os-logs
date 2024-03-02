from datetime import datetime, timedelta

from airflow.models.dag import DAG

from airflow.operators.bash import BashOperator

with DAG(
    "kos-producer-dag", # DAG Name
    default_args={
        "depends_on_past": False,
        "email": ["bmsb235@gmail.com"],
        "email_on_failure": False,
        "email_on_retry": False,
        "retries": 1,
        "retry_delay": timedelta(minutes=5),
    },
    description="DAG to trigger producer periodically",
    schedule="*/2 * * * *", # Run every 2 mins. to bring fresh OS logs into Kafka
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=["kos"], # To ease filtering on dashboard
) as dag:
    t1 = BashOperator(
        task_id="trigger_kos_producer", # Task Name
        bash_command="curl http://localhost:8081/kos/start",
    )

    t1
