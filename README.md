# Java 8 API for the [StockFighter](https://www.stockfighter.io) Game
It includes both the [documented API](https://starfighter.readme.io/v1.0/docs) and the game master API. All the REST calls are wrapped into a CompletableFuture or in a [RxJava](https://github.com/ReactiveX/RxJava) Observable.

## Installation

Fetch and install into your local Maven repository

    git clone https://github.com/adrianbumbas/stockfighter-api
    cd stockfighter-api
    mvn install
Then add it to your pom.xml

      <dependency>
        <groupId>com.amonsoftware.stockfighter</groupId>
        <artifactId>stockfighter-api</artifactId>
        <version>1.0-SNAPSHOT</version>
      </dependency>

## Usage

In order to create a new Stockfighter API object do

    StockfighterAPI stockfighterAPI = new StockfighterAPI("your api key");

Example code to get the heartbeat (blocking)...

    stockfighterAPI.getHeartbeat().get();

...and nonblocking

    stockfighterAPI.getHeartbeat().thenAccept(heartbeat -> //consume the response);
    
Create a new game master API

    GameMasterAPI gameMasterAPI = new GameMasterAPI("your api key");

Usage of the Ticker Tape Websocket API

    StockfighterWSApi stockfighterWSApi = new StockfighterWSApi();
    stockfighterWSApi.getQuoteSubscription("YOUR_VENUE", "YOUR_ACCOUNT").subscribe(quoteSubscription -> {
                //quoteSubscription contains the quote information
            });
       
