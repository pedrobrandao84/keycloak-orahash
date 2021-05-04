package com.avanadebrasil.keycloak.testehash;

import com.avanadebrasil.keycloak.orahash.OrahashPasswordProvider;
import org.junit.Assert;
import org.junit.Test;

public class TestHashQuery {

    private OrahashPasswordProvider orahashPasswordProvider;

    @Test
    public void testHashValid(){
        orahashPasswordProvider = new OrahashPasswordProvider("orahash", 10);

        String hashTest = orahashPasswordProvider.encode("teste", 10);

        Assert.assertEquals(hashTest, "801484143");

    }

}
