package com.wallimn.iteye.sp.asset.bus;

import com.wallimn.iteye.sp.asset.AssetApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Captain J
 * @date 2018/12/21 9:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AssetApplication.class)
public class ServiceTest {

    @Test
    public void test(){
        System.out.println("hello word !!!");
    }

}
