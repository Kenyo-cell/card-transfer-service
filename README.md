# Card Transfer Service

----
## Realization note

Service has 2 realized containers: first for frontend service usage and second for REST requests

### Frontend Service

In this case you should run this one with `docker-compose -f front-docker-compose.yml up` command
and React App access with 8080 port.

Just enter in browser URL field `http://localhost:8080` and use it!

Enter all card data and just push the button in bottom of page.
You will see one of 2 messages: error modal or success transfer modal.
React App has it own internal validation so just do that it's saying to you.

### REST Service
So other case is using http requests to use the REST API. It can be started with
`docker-compose -f rest-docker-compose.yml up` command.

You can access the Application with port 5500. All of your requests should be sent to
`http://localhost:5050` address.

Application has 2 http API paths:
 * `http://localhost:5050/transfer`:
   * POST method with object:
 ```
 {
    cardFromNumber: string,
    cardValidTill: string,
    cardCVV: string,
    cardToNumber: string,
    amount: {
        value: int,
        currency: string
    }
 }
```
 * `http://localhost:5050/confirmOperaion`:
   * POST method with object:
 ```
 {
    operationId: int,
    code: string
 }
```

Every request returns one of 3 type of response:
 * Success response `{ "operationId" : string }` with 200 status code
 * Incorrect Input response `{ "operationId" : string, "message" : string }` with 400 status code
 * Internal Server Error response `{ "operationId" : string, "message" : string }` with 500 status code

----

## Developer Note

In the REST service implementation in command line you can see
output in csv view for every transfer transaction and verification code when transaction has written.
It was developed in that way due to my comfort and ability to find correct operation verification code
of each transaction. Main [task](https://github.com/netology-code/jd-homeworks/blob/master/diploma/moneytransferservice.md)
does not provide capability to know the verification code with other way.

Thank You!
