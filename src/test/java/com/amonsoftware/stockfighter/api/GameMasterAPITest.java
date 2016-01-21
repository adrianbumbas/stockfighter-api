package com.amonsoftware.stockfighter.api;

import com.amonsoftware.stockfighter.model.Instance;
import com.amonsoftware.stockfighter.model.LevelResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GameMasterAPITest {
    private static final String API_KEY = "6c0f27076afa7582a81541544105c0f6b4b055a5";
    private GameMasterAPI gameMasterAPI;

    @Before
    public void setUp() throws Exception {
        gameMasterAPI = new GameMasterAPI(API_KEY);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testStartLevel() throws Exception {
        LevelResponse level = gameMasterAPI.startLevel("first_steps").get();
        assertTrue(level.isOk());
    }

    @Test
    public void testGetInstanceDetails() throws Exception {
        LevelResponse level = gameMasterAPI.startLevel("chock_a_block").get();
        assertTrue(level.isOk());
        Instance instance = gameMasterAPI.getInstanceDetails(level.getInstanceId()).get();
        assertTrue(instance.isOk());
        //do not expect a flashMessage message yet
        assertNull(instance.getFlashMessage());
    }
}