/**
 * Copyright 2016 Yahoo Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yahoo.pulsar.checksum.utils;

import java.nio.ByteBuffer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.yahoo.pulsar.checksum.utils.Crc32cChecksum;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Crc32cChecksumTest {

    private static final byte[] inputBytes = "data".getBytes();
    private static final int expectedChecksum = 0xaed87dd1;

    @Test
    public void testCrc32c() {

        ByteBuf payload = Unpooled.wrappedBuffer(ByteBuffer.wrap(inputBytes, 0, inputBytes.length));
        int checksum = Crc32cChecksum.computeChecksum(payload);
        payload.release();
        Assert.assertEquals(expectedChecksum, checksum);
    }

    @Test
    public void testCrc32cDirectMemory() {
        ByteBuffer bytes = ByteBuffer.allocateDirect(4);
        bytes.put(inputBytes);
        bytes.flip();
        ByteBuf payload = Unpooled.wrappedBuffer(bytes);
        int checksum = Crc32cChecksum.computeChecksum(payload);
        payload.release();
        Assert.assertEquals(expectedChecksum, checksum);
    }

}