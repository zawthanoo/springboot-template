#docker run -p 8089:8089 -v <<LOAD_TEST_DIRECTORY_PATH>>:/mnt/locust locustio/locust -f /mnt/locust/load_test.py

from locust import HttpUser, task, constant
import json
import uuid
import random

class cacheService(HttpUser):

    wait_time = constant(1)
    ids = []

    @task
    def getEmpList(self):
        response = self.client.get(f"/demo/api/emplist")
        if response.status_code == 200:
            out = response.json()
            empId = out[0]["id"]
            self.ids.append(empId)

    @task
    def get(self):
        if len(self.ids) == 0:
            self.getEmpList()
        else:
            randomEmpId = random.choice(self.ids)
            self.client.get(f"/demo/api/{randomEmpId}")
    
    @task
    def daoExceptionTest(self):
        with self.client.get(f"/demo/api/daoExceptionTest", catch_response=True) as response:
            if response.status_code == 500:
                out = response.json()
                status = out["status"]
                if status == "FAILED":
                    response.success()

    @task
    def addNewEmp(self):
        payload = {
            "name": "ZTO",
            "age": 19,
            "department": "HR"
        }
        headers = {'content-type': 'application/json'}
        response = self.client.post("/demo/api/add", data=json.dumps(payload),headers=headers)
        if response.status_code == 200:
            out = response.json()
            message = out["message"]
            # self.message.append(message)
                



