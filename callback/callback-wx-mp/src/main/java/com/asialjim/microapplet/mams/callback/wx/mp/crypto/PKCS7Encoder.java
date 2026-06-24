/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.callback.wx.mp.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * PKCS7 编码
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
class PKCS7Encoder {
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final int BLOCK_SIZE = 32;

    static byte[] encode(int count) {
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) amountToPad = BLOCK_SIZE;
        char padChr = chr(amountToPad);
        return String.valueOf(padChr).repeat(amountToPad).getBytes(CHARSET);
    }

    static byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) pad = 0;
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }
}
