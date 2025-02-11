/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
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

package com.tencent.bk.job.common.k8s.config;

import io.kubernetes.client.openapi.ApiClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.springframework.cloud.kubernetes.commons.KubernetesClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

import static org.springframework.cloud.kubernetes.client.KubernetesClientUtils.kubernetesApiClient;

@Configuration
@Slf4j
public class CustomKubernetesClientAutoConfiguration {
    @Bean
    @Primary
    public ApiClient apiClient(KubernetesClientProperties properties) {
        ApiClient apiClient = kubernetesApiClient();
        OkHttpClient customClient = apiClient.getHttpClient()
                .newBuilder()
                // 默认的HTTP_2不兼容，使用HTTP_1.1
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();
        apiClient.setHttpClient(customClient);
        apiClient.setUserAgent(properties.getUserAgent());
        return apiClient;
    }
}
