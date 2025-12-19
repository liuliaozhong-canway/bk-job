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

package com.tencent.bk.job.common.cc.util;

import com.tencent.bk.job.common.cc.constants.ResourceWatchExceptionCodeEnum;
import com.tencent.bk.job.common.cc.exception.CmdbResourceWatchException;
import com.tencent.bk.job.common.constant.ErrorCode;
import com.tencent.bk.job.common.esb.model.EsbResp;
import com.tencent.bk.job.common.exception.InternalCmdbException;
import lombok.extern.slf4j.Slf4j;

/**
 * CMDB请求响应体检查工具类
 */
@Slf4j
public class CmdbResponseCheckUtil {

    /**
     * 检查CMDB资源监听响应码，如果不符合预期抛出异常
     */
    public static void checkResourceWatchRespCode(EsbResp<?> esbResp) {
        if (esbResp == null) {
            throw new InternalCmdbException("Watch response is null", ErrorCode.CMDB_API_DATA_ERROR);
        }
        if (esbResp.getCode() == 0) {
            return;
        }

        int code = esbResp.getCode();
        if (ResourceWatchExceptionCodeEnum.isValid(code)) {
            log.warn("CMDB watch response code not as expected, code={}, msg={}", code, esbResp.getMessage());
            throw new CmdbResourceWatchException("Response code not as expected", ErrorCode.CMDB_API_DATA_ERROR);
        }
    }
}
