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
 *
 */

package org.apache.skywalking.oap.server.core.analysis.manual.endpointrelation;

import org.apache.skywalking.oap.server.core.analysis.SourceDispatcher;
import org.apache.skywalking.oap.server.core.analysis.worker.IndicatorProcess;
import org.apache.skywalking.oap.server.core.source.EndpointRelation;

/**
 * @author wusheng
 */
public class EndpointCallRelationDispatcher implements SourceDispatcher<EndpointRelation> {
    @Override
    public void dispatch(EndpointRelation source) {
        switch (source.getDetectPoint()) {
            case CLIENT:
                clientSide(source);
                break;
            case SERVER:
                serverSide(source);
                break;
        }
    }

    private void serverSide(EndpointRelation source) {
        EndpointRelationServerSideIndicator indicator = new EndpointRelationServerSideIndicator();
        indicator.setTimeBucket(source.getTimeBucket());
        indicator.setSourceEndpointId(source.getEndpointId());
        indicator.setDestEndpointId(source.getChildEndpointId());
        IndicatorProcess.INSTANCE.in(indicator);
    }

    private void clientSide(EndpointRelation source) {
        EndpointRelationClientSideIndicator indicator = new EndpointRelationClientSideIndicator();
        indicator.setTimeBucket(source.getTimeBucket());
        indicator.setSourceEndpointId(source.getEndpointId());
        indicator.setDestEndpointId(source.getChildEndpointId());
        IndicatorProcess.INSTANCE.in(indicator);
    }
}
