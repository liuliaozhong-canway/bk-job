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

package com.tencent.bk.job.manage.api.web.impl;

import com.tencent.bk.job.common.iam.exception.PermissionDeniedException;
import com.tencent.bk.job.common.iam.model.AuthResult;
import com.tencent.bk.job.common.model.BaseSearchCondition;
import com.tencent.bk.job.common.model.PageData;
import com.tencent.bk.job.common.model.PageDataWithManagePermission;
import com.tencent.bk.job.common.model.Response;
import com.tencent.bk.job.common.model.dto.ApplicationHostDTO;
import com.tencent.bk.job.common.model.vo.CloudAreaInfoVO;
import com.tencent.bk.job.manage.api.web.WebTestResource;
import com.tencent.bk.job.manage.api.web.WebWhiteIPResource;
import com.tencent.bk.job.manage.auth.NoResourceScopeAuthService;
import com.tencent.bk.job.manage.model.web.request.whiteip.WhiteIPRecordCreateUpdateReq;
import com.tencent.bk.job.manage.model.web.vo.whiteip.ActionScopeVO;
import com.tencent.bk.job.manage.model.web.vo.whiteip.WhiteIPRecordVO;
import com.tencent.bk.job.manage.service.WhiteIPService;
import com.tencent.bk.job.manage.service.host.HostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class WebTestResourceImpl implements WebTestResource {

    private final HostService hostService;

    @Autowired
    public WebTestResourceImpl(HostService hostService) {
        this.hostService = hostService;
    }

    @Override
    public Response<Object> createLog(String username, Long bizId, Integer count) {
        ApplicationHostDTO applicationHostInfoCondition = new ApplicationHostDTO();
        if (bizId == null) {
            bizId = 2L;
        }
        if (count == null) {
            count = 200;
        }
        applicationHostInfoCondition.setBizId(bizId);

        BaseSearchCondition baseSearchCondition = new BaseSearchCondition();
        baseSearchCondition.setStart(0);
        baseSearchCondition.setLength(1000);

        PageData<ApplicationHostDTO> appHostInfoPageData =
            hostService.listAppHost(applicationHostInfoCondition, baseSearchCondition);
        log.info("get host list size: {}", appHostInfoPageData.getTotal());
        List<ApplicationHostDTO> data = appHostInfoPageData.getData();
        for (int i = 0; i < count; i++) {
            log.info("begin print data, current index = {}, end index={}", i, count);
            log.info(data.toString());
        }
        return Response.buildSuccessResp(true);
    }

    @Override
    public Response<Object> createLog1(String username, Integer count) {
        int threadPoolSize = 10;
        int totalLogCount = 100000;
        if (count != null) {
            totalLogCount = count;
        }
        int logsPerThread = totalLogCount / threadPoolSize;

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        for (int i = 0; i < threadPoolSize; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                for (int j = 0; j < logsPerThread; j++) {
                    log.info("Thread {} - This is log message number: {}", threadId, j + (threadId * logsPerThread));
                }
            });
        }

        // 关闭线程池（在示例中等待一段时间以便日志可以输出，实际应用中可能需要根据情况决定关闭时机）
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executorService.shutdown();
        }

        return Response.buildSuccessResp(true);
    }


}
