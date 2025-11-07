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

package com.tencent.bk.job.common.service.feign.client;

import feign.Client;
import feign.Request;
import feign.Response;
import feign.httpclient.ApacheHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * FeignClient抛出的原始异常信息中仅包含负载均衡解析前的服务名称信息，
 * 当前类用于包装ApacheHttpClient以便观察FeignClient调用异常时的真实请求信息（URL等）
 */
@Slf4j
public class WatchableFeignHttpClient implements Client {
    private final ApacheHttpClient apacheHttpClient;

    public WatchableFeignHttpClient(ApacheHttpClient  apacheHttpClient) {
        this.apacheHttpClient = apacheHttpClient;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        long start = System.currentTimeMillis();
        try {
            Response response = apacheHttpClient.execute(request, options);
            long cost = System.currentTimeMillis() - start;
            log.debug(
                "SucceedToExecFeignRequest, cost={}ms, method={}, url={}",
                cost,
                request.httpMethod().name(),
                request.url()
            );
            return response;
        } catch (Exception e) {
            long cost = System.currentTimeMillis() - start;
            log.warn(
                "FailToExecFeignRequest, cost={}ms, method={}, url={}",
                cost,
                request.httpMethod().name(),
                request.url()
            );
            throw e;
        }
    }
}
