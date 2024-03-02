from datetime import datetime, timedelta

from airflow.models.dag import DAG

from airflow.operators.bash import BashOperator

with DAG(
    "kos-streams-dag", # DAG Name
    default_args={
        "depends_on_past": False,
        "email": ["bmsb235@gmail.com"],
        "email_on_failure": False,
        "email_on_retry": False,
        "retries": 1,
        "retry_delay": timedelta(minutes=5),
    },
    description="DAG to trigger kafka streams once a day",
    schedule="0 0 * * *", # Run once a day, at 00:00
    start_date=datetime(2021, 1, 1),
    catchup=False,
    tags=["kos"], # To ease filtering on dashboard
) as dag:
    t1 = BashOperator(
        task_id="trigger_kos_streams", # Task Name
        bash_command="curl http://localhost:8081/kos/streams/start",
    )

    t1
