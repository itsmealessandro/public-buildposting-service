from locust import HttpUser, task, between
import random
import uuid

CITIES = ["Rome", "Milan", "Naples", "Florence"]
USERNAMES = ["mariorossi", "sarabianchi", "johndoe"]
FORMATS = ["A3", "A4", "Poster", "Billboard"]

def generate_payload(username):
    selected_cities = random.sample(CITIES, k=random.randint(1, len(CITIES)))
    max_prices = {city: random.randint(100, 500) for city in selected_cities}
    return {
        "username": username,
        "cities": selected_cities,
        "max_prices": max_prices,
        "poster_format": random.choice(FORMATS)
    }

class BaseUser(HttpUser):
    wait_time = between(1, 3)
    host = "http://localhost:8080"
    abstract = True
    path = "/poster-request"

    def post_and_print(self, username):
        print(self.username)
        payload = generate_payload(username)
        with self.client.post(self.path, json=payload, catch_response=True) as response:
            if response.status_code == 200:
                try:
                    data = response.json()
                    selected_zones = data.get("selected_zones")
                    total_price = data.get("total_price")
                    request_id = data.get("request_id")
                    print(f"[{username}] Zones: {selected_zones}, Total: {total_price}, ID: {request_id}")
                    response.success()
                except Exception as e:
                    print(f"[{username}] Failed to parse response: {e}")
                    response.failure("Invalid JSON")
            else:
                print(f"[{username}] Request failed with status code {response.status_code}")
                response.failure("Bad status code")

class UserTypeA(BaseUser):
    username=USERNAMES[0]
    @task
    def send_request(self):
        self.post_and_print(USERNAMES[0] + "_" + uuid.uuid4().hex[:8])

class UserTypeB(BaseUser):
    username=USERNAMES[1]
    @task
    def send_request(self):
        self.post_and_print(USERNAMES[1] + "_" + uuid.uuid4().hex[:8])

class UserTypeC(BaseUser):
    username=USERNAMES[2]
    @task
    def send_request(self):
        self.post_and_print(USERNAMES[2] + "_" + uuid.uuid4().hex[:8])
