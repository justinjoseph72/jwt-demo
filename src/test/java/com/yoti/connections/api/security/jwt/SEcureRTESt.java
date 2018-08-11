package com.yoti.connections.api.security.jwt;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SEcureRTESt {


    @Test
    public void sdfs() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        byte[] seed = secureRandom.generateSeed(5);
        byte[] randomBytes = new byte[128];
        secureRandom.nextBytes(randomBytes);
        String ress = new String(randomBytes);
        System.out.println(ress);
        secureRandom.setSeed(seed);

        byte[] gsdg = secureRandom.generateSeed(128);
        String s = new String(gsdg);
        System.out.println(s);

    }
}
