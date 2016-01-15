package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.LevelResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.*;

public class GameMasterAPITest {
    private GameMasterAPI gameMasterAPI;

    @Before
    public void setUp() throws Exception {
        gameMasterAPI = new GameMasterAPI();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStartLevel() throws Exception {
        LevelResponse level = gameMasterAPI.startLevel("first_steps").get();
        assertTrue(level.isOk());
    }
}