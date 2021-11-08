# CriptoPriceObtainer
Application that enables you to retrieve Bitcoin information from external service and get some analitics from the information retrieved.


## Run the APP
    The aplication can be run directly from IntelliJ.
    With maven installed, navigate to the root of the project and excecute: mvn spring-boot:run in the command line

## Database
    It has an H2 in memory database where every information retrieved from the external service is persisted

# SCHEDULED SERVICE
Every 10 seconds, the application retrieves the current Bitcoin information and persists it into the Database.
<br>`Timestamp added can be seen in the logs of the application`

# REST API
It has two functionalities available in two endpoints:

## Obtain Bitcoin price in a certain Timestamp provided by the User:

### Request
`GET /api/priceObtainer`

    RequestParam: timestamp
    ej: http://localhost:8080/api/priceObtainer?timestamp=2021-11-08T15:57:42.881

### Response

    {
      "price": 65888.60,
      "timestamp": "2021-11-08T15:57:42.881"
    }

## Obtain Bitcoin's average information between two Timestamp provided by the User:

### Request
`GET /api/priceObtainer/avg`

    RequestParam: fromTimestamp
    RequestParam: toTimestamp
    ej: http://localhost:8080/api/priceObtainer/avg?fromTimestamp=2021-11-08T13:06:16.275&toTimestamp=2021-11-08T16:09:03.117
    
### Response

    {
      "avg": 65913.29,
      "maxValue": 65980.50,
      "percentageDifference": 0.01
    }
