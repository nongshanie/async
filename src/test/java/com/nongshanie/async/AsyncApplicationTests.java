package com.nongshanie.async;

import com.nongshanie.async.service.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncApplicationTests {

    @Autowired
    private AsyncService asyncService;

    @Test
    public void testAsync() throws InterruptedException{
        asyncService.doBatch();
        /*try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
