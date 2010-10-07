package com.googlecode.totallylazy.predicates;

import com.googlecode.totallylazy.Sequence;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;

import static com.googlecode.totallylazy.Sequences.sequence;

public class IterableMatcher<T> extends TypeSafeMatcher<Iterable<? super T>> {
    private final Sequence<? super T> expected;
    private boolean shouldBeSameSize;

    private IterableMatcher(Sequence<? super T> expected, boolean shouldBeSameSize) {
        this.expected = expected;
        this.shouldBeSameSize = shouldBeSameSize;
    }

    public static <T> Matcher<Iterable<? super T>> hasExactly(T... items) {
        return hasExactly(sequence(items));
    }

    public static <T> Matcher<Iterable<? super T>> hasExactly(Sequence<T> expected) {
        return new IterableMatcher<T>(expected, true);
    }

    public static <T> Matcher<Iterable<? super T>> startsWith(T... items) {
        return startsWith(sequence(items));
    }

    public static <T> Matcher<Iterable<? super T>> startsWith(Sequence<T> expected) {
        return new IterableMatcher<T>(expected, false);
    }

    @Override
    public boolean matchesSafely(Iterable<? super T> actual) {
        Iterator<? super T> e = this.expected.iterator();
        Iterator<? super T> a = actual.iterator();
        while(e.hasNext()){
            if(!a.hasNext()){
                return false;
            }

            if(!e.next().equals(a.next())){
                return false;
            }
        }
        if(shouldBeSameSize && a.hasNext()){
            return false;
        }
        return true;
    }

    public void describeTo(Description description) {
        description.appendValue(expected);
    }
}
