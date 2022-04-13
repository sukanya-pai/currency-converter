## Currency Converter App

Scope Covered:
- Retrieve ECB reference rate for currency pair eg: USD/EUR, HUF/EUR
  ```
  curl --location --request GET 'localhost:8080/currency/exchangerate?currency=HUF&baseCurrency=EUR'
  ```
- Retrieve Exchange rates for other pairs HUF/USD
  ```
  curl --location --request GET 'localhost:8080/currency/exchangerate?currency=HUF&baseCurrency=USD'
  ```
- Convert amount of given currency to another, for example: 15 EUR = ? GBP
  ```
  curl --location --request POST 'localhost:8080/currency/convert' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "targetCurrency":{
    "unit":"EUR",
    "value":15
    },
    "baseCurrencyUnit":"GBP"
    }'
  ```
- Retrieve List of supported Currencies.
    ```
    curl --location --request GET 'localhost:8080/currency/supported'
    ```