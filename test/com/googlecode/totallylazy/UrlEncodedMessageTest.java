package com.googlecode.totallylazy;

import com.googlecode.totallylazy.matchers.NumberMatcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class UrlEncodedMessageTest {
    @Test
    public void canParseToPairs() throws Exception {
        List<Pair<String, String>> pairs = UrlEncodedMessage.parse("The+string=%C3%BC%40foo-bar");
        assertThat(pairs.size(), NumberMatcher.is(1));
        assertThat(pairs.get(0).first(), Matchers.is("The string"));
        if (!runningOnAMac()) {
            assertThat(pairs.get(0).second(), Matchers.is("ü@foo-bar"));
        }
    }

    @Test
    public void canParseToPairsEvenWhenNoValueIsPresent() throws Exception {
        List<Pair<String, String>> pairs = UrlEncodedMessage.parse("The+string=");
        assertThat(pairs.size(), NumberMatcher.is(1));
        assertThat(pairs.get(0).first(), Matchers.is("The string"));
        assertThat(pairs.get(0).second(), Matchers.is(""));
    }

    @Test
    public void canConvertToString() throws Exception {
        if (runningOnAMac()) {
            return;
        }

        List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>() {{
            add(Pair.pair("The string", "ü@foo-bar"));
        }};
        String result = UrlEncodedMessage.toString(pairs);
        assertThat(result, Matchers.is("The+string=%C3%BC%40foo-bar"));
    }

    private static boolean runningOnAMac() {
        return System.getProperty("os.name").contains("Mac OS");
    }
}