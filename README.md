# Java API for the [StockFighter](https://www.stockfighter.io) Game
It includes both the [documented API](https://starfighter.readme.io/v1.0/docs) and the game master API. All the calls are wrapped into a CompletableFuture.

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

Example code to get the heartbeat

    stockfighterAPI.getHeartbeat().get();
    
Create a new game master API

    GameMasterAPI gameMasterAPI = new GameMasterAPI("your api key");
       
