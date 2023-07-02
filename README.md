# Fetch-Rewards-Challenge


## To Test using the Google Cloud Run Deployment - 

```
curl --location --request POST 'https://fetch-app-klcmltmwma-ue.a.run.app/receipts/process' \
--header 'Content-Type: application/json' \
--data-raw '{
  "retailer": "M&M Corner Market",
  "purchaseDate": "2022-03-20",
  "purchaseTime": "14:33",
  "items": [
    {
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    }
  ],
  "total": "9.00"
}'
```

Please Replace the id with the id received from the above response!

```
 curl --location --request GET 'https://fetch-app-klcmltmwma-ue.a.run.app/receipts/{id}/points'
```

## Steps to Install and Run the Project 

## Install Java JDK

JDK 17 from - https://jdk.java.net/archive/

https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_macos-aarch64_bin.tar.gz

## Clone the repository to your local machine using the following command:
```
git clone https://github.com/SakshiDG/Fetch-Rewards-Challenge.git
```

## to the project directory:
```
cd Fetch-Rewards-Challenge
```

## Build the Docker image using the provided Dockerfile:
```
docker build -t fetch-app .
```

## Run the Docker container:
```
docker run -p 8080:8080 fetch-app
```
## Curl Commands to Test Examples

### Example 1
```
curl --location --request POST 'http://localhost:8080/receipts/process' \
--header 'Content-Type: application/json' \
--data-raw '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}'
```
Please Replace the id with the id received from the above response!

```
curl --location --request GET 'http://localhost:8081/receipts/{id}/points'
```

### Example 2

```
curl --location --request POST 'http://localhost:8080/receipts/process' \
--header 'Content-Type: application/json' \
--data-raw '{
  "retailer": "M&M Corner Market",
  "purchaseDate": "2022-03-20",
  "purchaseTime": "14:33",
  "items": [
    {
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    },{
      "shortDescription": "Gatorade",
      "price": "2.25"
    }
  ],
  "total": "9.00"
}'
```

Please Replace the id with the id received from the above response!

```
curl --location --request GET 'http://localhost:8081/receipts/{id}/points'
```

## Key Features

* **Print Info Logging:** A custom logging function called printInfo has been implemented to enable/disable detailed logging of the code execution. This function prints relevant information at various stages of the code, providing insights into the processing flow and helping with debugging.

* **Flexibility of Logging:** The detailView variable in the ReceiptService class can be toggled to enable/disable the detailed logging. This allows users to control the printing of logs and conserve memory when detailed logging is not required.

* **Deployment on Google Cloud:** The code has been deployed on Google Cloud to test the solution without the need for installation. This allows easy access and evaluation of the receipt processing service.

* **Unit Tests:** The code includes unit tests to ensure the correctness of the implemented logic and functionality. These tests cover various scenarios and validate the expected behavior of the service.

* **Dockerfile:** A Dockerfile is provided to containerize the application. It allows easy deployment and scaling of the receipt processing service in different environments.
