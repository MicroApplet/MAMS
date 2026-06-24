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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.Objects;

/**
 * 微信 XML 解析
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
class XMLParse {
    static String[] extract(String xmltext) throws AesException {
        String[] result = new String[3];
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setXIncludeAware(false);
            dbf.setExpandEntityReferences(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new InputSource(new StringReader(xmltext)));

            Element root = document.getDocumentElement();
            NodeList encryptNode = root.getElementsByTagName("Encrypt");
            result[0] = String.valueOf(Objects.nonNull(encryptNode.item(0)));
            result[1] = Objects.isNull(encryptNode.item(0)) ? xmltext : encryptNode.item(0).getTextContent();

            NodeList toUserNode = root.getElementsByTagName("ToUserName");
            result[2] = Objects.nonNull(toUserNode.item(0)) ? toUserNode.item(0).getTextContent() : null;
            return result;
        } catch (Exception e) {
            throw new AesException(AesException.ParseXmlError);
        }
    }

    static String generate(String encrypt, String signature, String timestamp, String nonce) {
        String format = "<xml><Encrypt><![CDATA[%1$s]]></Encrypt><MsgSignature><![CDATA[%2$s]]></MsgSignature><TimeStamp>%3$s</TimeStamp><Nonce><![CDATA[%4$s]]></Nonce></xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);
    }
}
