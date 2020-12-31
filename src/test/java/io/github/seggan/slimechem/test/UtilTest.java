package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.StringUtil;
import io.github.mooy1.slimechem.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilTest {

    @BeforeAll
    public static void setUp() {
        Constants.isTestingEnvironment = true;
    }

    @Test
    public void testResourceLoading() throws IOException {
        Asserts.assertEquals(
            StringUtils.countMatches(StringUtil.getResourceAsString("isotopes.json"), "{"),
            3095
        );
    }

    @Test
    public void testStringSplitting() {
        Asserts.assertEquals(
            StringUtil.splitString("2n"),
            new StringUtil.NumberAndString(2, "n")
        );

        Asserts.assertEquals(
            StringUtil.splitString("4He"),
            new StringUtil.NumberAndString(4, "He")
        );
    }

    @Test
    public void testListTrimming() {
        List<Element> list = new ArrayList<>(Arrays.asList(Element.values()));

        Util.trimList(list, 4);
        Asserts.assertEquals(list.size(), 4);

        Util.trimList(list, 10);
        Asserts.assertEquals(list.size(), 4);
    }
}
