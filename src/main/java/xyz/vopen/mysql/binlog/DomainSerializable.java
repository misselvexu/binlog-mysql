/**
 * Copyright (C) 2010-2013 Xilamuren Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.vopen.mysql.binlog;

import com.alibaba.fastjson.JSON;
import xyz.vopen.mysql.binlog.utils.JPretty;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Serializable + JSON base class
 */
public abstract class DomainSerializable implements Serializable {

    private static final long serialVersionUID = 702262309802799359L;

    /**
     * 转换成JSON
     *
     * @param obj          实体
     * @param prettyFormat 是否格式化
     * @return json数据
     */
    public static String toJson (final Object obj, boolean prettyFormat) {
        return JSON.toJSONString(obj, prettyFormat);
    }

    /**
     * 将JSON 数据转化成实体
     *
     * @param json     json 数据
     * @param classOfT 实体 bean
     * @param <T>      泛型
     * @return 实体类型
     */
    public static <T> T fromJson (String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }

    public static byte[] encode (final Object obj) {
        final String json = toJson(obj, false);
        if (json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    /**
     * 将JSON 数据转化成实体
     *
     * @param data     json 数据
     * @param classOfT 实体 bean
     * @param <T>      泛型
     * @return 实体类型
     */
    public static <T> T decode (final byte[] data, Class<T> classOfT) {
        final String json = new String(data, Charset.forName("UTF-8"));
        return fromJson(json, classOfT);
    }

    /**
     * TO JSON
     */
    public String toJson () {
        return toJson(false);
    }

    /**
     * print
     */
    public void print () {
        JPretty.printJson(toJson());
    }

    /**
     * print format
     */
    public String toJson (final boolean prettyFormat) {
        return toJson(this, prettyFormat);
    }

    /**
     * encode
     */
    public byte[] encode () {
        final String json = this.toJson();
        if (json != null) {
            return json.getBytes();
        }
        return null;
    }

    @SuppressWarnings("unused")
    public List<Object> parseSerializable (Serializable[] serializables) {
        List<Object> result = new ArrayList<>();
        int index = 0;
        if (serializables != null && serializables.length > 0) {
            for (Serializable serializable : serializables) {
                int currentIndex = index++;
                // 判断字符串类型
                if (serializables[currentIndex] instanceof byte[]) {
                    result.add(new String((byte[]) serializables[currentIndex]));
                } else {
                    result.add(serializables[currentIndex]);
                }
            }
        }
        return result;
    }

}
