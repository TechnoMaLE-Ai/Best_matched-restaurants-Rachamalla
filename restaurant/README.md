# Fullstack Technical Assessment
A take home assessment designed for Full-stack or Backend developers
## Best matched restaurants
### Introduction
Code is written java using spring boot and the api call is happening to search the best matched restaurant.
Response will be generated in the form of a JSON payload.

Technology used
1. Java 17
2. Spring boot 3.3
3. Postname(Tool required)


Steps :
1. Code needs to be imported into the editor(IntelliJ, Eclipse). Once the importing is done, the server can be started.
2. Configure jdk 17 for the code.
3. Maven build will start automatically. If not, then start maven build.
4. Start the server.
5. When you open the code, update it with the location of your CSV file

When the server gets started, you will find the port in the logs and start postman tool for service hit.
Below is a sample example for request and response received.
URL: localhost:8080/search
Request 1:
{
    "cuisine":"Amer",
    "name":"cho",
    "customerRating":3,
    "distance":5,
    "price":40
}
Response 1:
{
    "searchList": [
        {
            "name": "Wish Chow",
            "rating": 3,
            "distance": 1,
            "price": 40,
            "cuisine": "American"
        },
        {
            "name": "Bazaar Chow",
            "rating": 4,
            "distance": 4,
            "price": 40,
            "cuisine": "American"
        }
    ],
    "errorCode": null,
    "errorDescription": null
}

Request 2 :
{
    "cuisine":"Amer",
    "name":"cho",
    "customerRating":3,
    "distance":null,
    "price":null
}

Response 2:
{
    "searchList": [
        {
            "name": "Wish Chow",
            "rating": 3,
            "distance": 1,
            "price": 40,
            "cuisine": "American"
        },
        {
            "name": "Bazaar Chow",
            "rating": 4,
            "distance": 4,
            "price": 40,
            "cuisine": "American"
        },
        {
            "name": "Wagon Chow",
            "rating": 3,
            "distance": 9,
            "price": 10,
            "cuisine": "American"
        },
        {
            "name": "Central Chow",
            "rating": 3,
            "distance": 7,
            "price": 45,
            "cuisine": "American"
        }
    ],
    "errorCode": null,
    "errorDescription": null
}

Request 3:
{
    "cuisine":"Turkish",
    "name":"chomn",
    "customerRating":3,
    "distance":null,
    "price":null
}
Response 3:
{
    "searchList": null,
    "errorCode": "104",
    "errorDescription": "No Match found"
}