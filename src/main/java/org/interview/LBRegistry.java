package org.interview;

import org.interview.exceptions.TooManyURLsException;

import java.net.URL;
import java.util.Set;

public interface LBRegistry {

    void registerURL(URL urlToAdd) throws TooManyURLsException;

    Set<URL> getURLs();

    URL get();
}
