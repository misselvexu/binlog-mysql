/*
 * Copyright 2016 VOPEN.CN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.vopen.mysql.binlog.event.deserialization;

import xyz.vopen.mysql.binlog.event.EventData;
import xyz.vopen.mysql.binlog.nio.ByteArrayInputStream;

import java.io.IOException;

/**
 * 空事件异常
 *
 * @author <a href="mailto:cinc.jan@gmail.com">CiNC</a>
 */
@SuppressWarnings("rawtypes")
public class NullEventDataDeserializer implements EventDataDeserializer {

    @Override
    public EventData deserialize (ByteArrayInputStream inputStream) throws IOException {
        return null;
    }
}
