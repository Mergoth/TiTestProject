package org.interview;

import org.interview.exceptions.TooManyURLsException;
import org.interview.impl.LBRegistryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;


public class LBRegistryTest {

    @Test
    void urlRegistersWithNoExceptions() throws MalformedURLException, TooManyURLsException {

        LBRegistry registry = new LBRegistryImpl();
        URL testURL = new URL("https://my-test-component");
        registry.registerURL(testURL);
    }

    @Test
    void registerOneURL() throws MalformedURLException, TooManyURLsException {

        //given
        LBRegistry registry = new LBRegistryImpl();
        URL testURL = new URL("https://my-test-component");
        Set<URL> expectedResult = new HashSet<>();
        expectedResult.add(testURL);

        // when
        registry.registerURL(testURL);
        Set<URL> actualResult = registry.getURLs();

        // then
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldRegisterUniqueURL() throws MalformedURLException, TooManyURLsException {

        //given
        LBRegistry registry = new LBRegistryImpl();
        URL testURL = new URL("https://my-test-component");
        Set<URL> expectedResult = new HashSet<>();
        expectedResult.add(testURL);

        // when registering twice
        registry.registerURL(testURL);
        registry.registerURL(testURL);
        Set<URL> actualResult = registry.getURLs();

        // then
        Assertions.assertEquals(1, expectedResult.size());
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldThrowExceptionOnExcessiveURLs() throws MalformedURLException, TooManyURLsException {

        //given
        LBRegistry registry = new LBRegistryImpl();
        URL excessiveURL = new URL("http://error");

        //when
        for (int i = 1; i < 11; i++) {
            URL testURL = new URL("https://my-test-component/" + i);
            registry.registerURL(testURL);
        }
        Set<URL> actualResult = registry.getURLs();

        // then
        Assertions.assertThrows(TooManyURLsException.class, () -> registry.registerURL(excessiveURL));
        Assertions.assertEquals(10, actualResult.size());
    }

    @Test
    void shouldReturnRandomResult() throws MalformedURLException, TooManyURLsException {
        // given
        LBRegistry registry = new LBRegistryImpl();
        Set<URL> testData = new HashSet<>();
        Set<URL> resultSet = new HashSet<>();

        for (int i = 1; i < 11; i++) {
            URL testURL = new URL("https://my-test-component/" + i);
            registry.registerURL(testURL);
            testData.add(testURL);
        }

        // when
        for (int i = 0; i < 100; i++) {
            resultSet.add(registry.get());
        }

        // then
        Assertions.assertTrue(resultSet.size() > 1);
        Assertions.assertTrue(testData.containsAll(resultSet));

    }

    @Test
    void shouldReturnImmutableSet() throws MalformedURLException, TooManyURLsException {
        // given
        LBRegistry registry = new LBRegistryImpl();
        Set<URL> resultSet;

        // when
        URL testURL = new URL("https://my-test-component/");
        registry.registerURL(testURL);
        resultSet = registry.getURLs();

        // then
        Assertions.assertThrows(Exception.class, resultSet::clear);
    }
}
