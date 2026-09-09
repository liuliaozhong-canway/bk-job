/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 Tencent.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.tencent.bk.job.common.cc.sdk;

import com.tencent.bk.job.common.cc.config.CmdbConfig;
import com.tencent.bk.job.common.esb.config.AppProperties;
import com.tencent.bk.job.common.esb.config.BkApiGatewayProperties;
import com.tencent.bk.job.common.esb.config.EsbProperties;
import com.tencent.bk.job.common.util.http.JobHttpSslVerifyConfig;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BaseCmdbApiClientTest {

    @AfterEach
    void resetGlobalSslVerifyConfig() {
        JobHttpSslVerifyConfig.setGlobalVerifyEnabled(true);
    }

    @Test
    void explicitSslVerifyConfigDoesNotDependOnStaticConfigInitializationOrder() {
        JobHttpSslVerifyConfig.setGlobalVerifyEnabled(true);
        AppProperties appProperties = mock(AppProperties.class);
        EsbProperties esbProperties = mock(EsbProperties.class, RETURNS_DEEP_STUBS);
        BkApiGatewayProperties apiGatewayProperties = mock(BkApiGatewayProperties.class, RETURNS_DEEP_STUBS);
        CmdbConfig cmdbConfig = mock(CmdbConfig.class);
        when(esbProperties.getService().getUrl()).thenReturn("https://esb.example.com");
        when(apiGatewayProperties.getCmdb().getUrl()).thenReturn("https://cmdb.example.com");
        when(cmdbConfig.getDefaultSupplierAccount()).thenReturn("0");

        BizSetCmdbClient client = new BizSetCmdbClient(
            appProperties,
            esbProperties,
            apiGatewayProperties,
            cmdbConfig,
            null,
            new SimpleMeterRegistry(),
            false
        );

        assertFalse(client.isSslVerifyEnabled());
    }
}
