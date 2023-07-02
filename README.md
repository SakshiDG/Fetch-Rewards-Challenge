# Fetch-Rewards-Challenge

## To test using the Google Cloud Run Deployment - 

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
