/* 
 * Copyright 2024 Hochschule Luzern - Informatik.
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
package ch.hslu.ad.n31.future.arraysort;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

/**
 * Testfälle für {@link ch.hslu.ad.n31.future.arraysort}.
 */
public class ArraySortTaskTest {

    private static final byte[] ARRAY_PRE_SORTED = {-2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final byte[] ARRAY_UN_SORTED = {7, 6, 2, 5, 10, 0, -2, 3, 1, 8, 9, 4, -1};

    public ArraySortTaskTest() {
    }

    /**
     * Test of call method, of class ArraySortTask.
     */
    @Test
    public void testCallSorted() throws InterruptedException, ExecutionException {
        final Callable<byte[]> task = new ArraySortTask(ARRAY_UN_SORTED);
        try (final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            final Future<byte[]> result = executor.submit(task);
            final byte[] sortedArray = result.get();
            assertArrayEquals(ARRAY_PRE_SORTED, sortedArray);
        } finally {
            // Executor shutdown
        }
    }

    /**
     * Test of multi callable, of class ArraySortTask.
     */
    @Test
    public void testCallMultiSorted() throws InterruptedException, ExecutionException {
        final Callable<byte[]> taskA = new ArraySortTask(ARRAY_UN_SORTED);
        final Callable<byte[]> taskB = new ArraySortTask(ARRAY_UN_SORTED);
        final Callable<byte[]> taskC = new ArraySortTask(ARRAY_UN_SORTED);
        try (final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            final Future<byte[]> resultA = executor.submit(taskA);
            final Future<byte[]> resultB = executor.submit(taskB);
            final Future<byte[]> resultC = executor.submit(taskC);
            byte[] sortedArray = resultA.get();
            assertArrayEquals(ARRAY_PRE_SORTED, sortedArray);
            sortedArray = resultB.get();
            assertArrayEquals(ARRAY_PRE_SORTED, sortedArray);
            sortedArray = resultC.get();
            assertArrayEquals(ARRAY_PRE_SORTED, sortedArray);
        } finally {
            // Executor shutdown
        }
    }
}
