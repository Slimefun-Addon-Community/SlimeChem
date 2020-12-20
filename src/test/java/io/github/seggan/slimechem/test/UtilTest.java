package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.lists.Constants;

import io.github.mooy1.slimechem.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class UtilTest {

    @BeforeClass
    public static void setUp() {
        Constants.isTestingEnvironment = true;
    }

    @Test
    public void testResourceLoading() throws IOException {
        Assertions.assertEquals(
            StringUtils.countMatches(StringUtil.getResourceAsString("isotopes.json"), "{"),
            3087
        );
    }

    @Test
    public void testStringSplitting() {
        Assertions.assertEquals(
            StringUtil.splitString("2n"),
            new StringUtil.NumberAndString(2, "n")
        );

        Assertions.assertEquals(
            StringUtil.splitString("4He"),
            new StringUtil.NumberAndString(4, "He")
        );
    }
}
