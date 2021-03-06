/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.underlying.rewrite.parameter.builder.impl;

import com.google.common.base.Optional;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.rewrite.parameter.builder.ParameterBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Grouped parameter builder.
 *
 * @author panjuan
 * @author zhangliang
 */
public final class GroupedParameterBuilder implements ParameterBuilder {
    
    @Getter
    private final List<StandardParameterBuilder> parameterBuilders;
    
    @Setter
    private String derivedColumnName;
    
    public GroupedParameterBuilder(final List<List<Object>> groupedParameters) {
        parameterBuilders = new ArrayList<>(groupedParameters.size());
        for (List<Object> each : groupedParameters) {
            parameterBuilders.add(new StandardParameterBuilder(each));
        }
    }
    
    @Override
    public List<Object> getParameters() {
        List<Object> result = new LinkedList<>();
        for (int i = 0; i < parameterBuilders.size(); i++) {
            result.addAll(getParameters(i));
        }
        return result;
    }
    
    /**
     * Get parameters.
     * 
     * @param count parameters group count
     * @return parameters
     */
    public List<Object> getParameters(final int count) {
        return parameterBuilders.get(count).getParameters();
    }
    
    /**
     * Get derived column name.
     * 
     * @return derived column name
     */
    public Optional<String> getDerivedColumnName() {
        return Optional.fromNullable(derivedColumnName);
    }
}
