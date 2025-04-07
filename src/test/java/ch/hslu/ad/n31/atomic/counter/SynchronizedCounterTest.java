/*
 * Copyright 2025 Hochschule Luzern - Informatik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.ad.n31.atomic.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testfälle für {@link ch.hslu.ad.n31.atomic.counter.SynchronizedCounter}.
 */
public class SynchronizedCounterTest {
    
    public SynchronizedCounterTest() {
    }

    /**
     * Test of increment method, of class SynchronizedCounter.
     */
    @Test
    public void testIncrement() {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = 1;
        int result = instance.increment();
        assertEquals(expResult, result);
    }

    /**
     * Test of decrement method, of class SynchronizedCounter.
     */
    @Test
    public void testDecrement() {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = -1;
        int result = instance.decrement();
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class SynchronizedCounter.
     */
    @Test
    public void testGet() {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = 0;
        int result = instance.get();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of concurrent increment method, of class SynchronizedCounter.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIncrementConc() throws InterruptedException {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = 5555;
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1234; i++) {
                instance.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 4321; i++) {
                instance.increment();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        int result = instance.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of concurrent decrement method, of class SynchronizedCounter.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testDecrementConc() throws InterruptedException {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = -5555;
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1234; i++) {
                instance.decrement();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 4321; i++) {
                instance.decrement();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        int result = instance.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of concurrent increment and decrement method, of class SynchronizedCounter.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testIncDecConc() throws InterruptedException {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = 0;
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 6127; i++) {
                instance.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 6127; i++) {
                instance.decrement();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        int result = instance.get();
        assertEquals(expResult, result);
    }

    /**
     * Test of concurrent increment and decrement method, of class SynchronizedCounter.
     * @throws java.lang.InterruptedException
     */
    @Test
    public void testInc2Dec1Conc() throws InterruptedException {
        SynchronizedCounter instance = new SynchronizedCounter();
        int expResult = 0;
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 8400; i++) {
                instance.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5200; i++) {
                instance.decrement();
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 3200; i++) {
                instance.decrement();
            }
        });
        t1.start();
        t3.start();
        t2.start();
        t1.join();
        t2.join();
        t3.join();
        int result = instance.get();
        assertEquals(expResult, result);
    }
}
