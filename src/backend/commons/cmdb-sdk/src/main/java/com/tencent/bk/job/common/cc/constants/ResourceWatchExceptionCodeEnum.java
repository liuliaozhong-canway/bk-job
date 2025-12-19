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

package com.tencent.bk.job.common.cc.constants;

/**
 * 资源监听异常错误码枚举
 */
public enum ResourceWatchExceptionCodeEnum {
    // 事件节点不存在
    EVENT_NODE_NOT_EXIST(1103007),
    // 事件详情不存在
    EVENT_DETAIL_NOT_EXIST(1103008);

    private final Integer code;

    ResourceWatchExceptionCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static ResourceWatchExceptionCodeEnum valueOf(Integer code) {
        if (code == null) return null;
        for (ResourceWatchExceptionCodeEnum errorCodeEnum : values()) {
            if (errorCodeEnum.getCode().equals(code)) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    public static boolean isValid(Integer code) {
        if (code == null) {
            return false;
        }
        return valueOf(code) != null;
    }
}
