package org.interview.impl;

import org.interview.LBRegistry;
import org.interview.exceptions.TooManyURLsException;

import java.net.URL;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LBRegistryImpl implements LBRegistry {

    private final Set<URL> registrySet = ConcurrentHashMap.newKeySet();

    @Override
    public void registerURL(URL urlToAdd) throws TooManyURLsException {
        if (registrySet.size() >= 10) throw new TooManyURLsException("Not able to add more URLs into registry");
        registrySet.add(urlToAdd);
    }

    @Override
    public Set<URL> getURLs() {
        return Collections.unmodifiableSet(registrySet);
    }

    @Override
    public URL get() {
        int size = registrySet.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (URL url : registrySet) {
            if (i == item) return url;
            i++;
        }
        throw new RuntimeException("Couldn't get random element");
    }
}
