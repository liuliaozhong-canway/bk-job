package com.tencent.bk.job.api.v3.testcase;

import com.tencent.bk.job.api.constant.ErrorCode;
import com.tencent.bk.job.api.constant.ResourceScopeTypeEnum;
import com.tencent.bk.job.api.props.TestProps;
import com.tencent.bk.job.api.util.ApiUtil;
import com.tencent.bk.job.api.util.JsonUtil;
import com.tencent.bk.job.api.util.TestValueGenerator;
import com.tencent.bk.job.api.v3.model.request.EsbTestV3Req;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * i18n API 测试
 */
@DisplayName("v3.I18nTestResourceAPITest")
class I18nTestResourceAPITest extends BaseTest {

    @Nested
    class I18nTest {
        @Test
        @DisplayName("测试国际化id异常")
        void testI18nWithWrongId() {
            EsbTestV3Req req = new EsbTestV3Req();
            req.setScopeId(String.valueOf(TestProps.DEFAULT_BIZ));
            req.setScopeType(ResourceScopeTypeEnum.BIZ.getValue());
            req.setTestName(TestValueGenerator.generateUniqueStrValue("i18n_", 50));
            req.setTestId(0L);

            given()
                    .spec(ApiUtil.requestSpec(TestProps.DEFAULT_TEST_USER))
                    .body(JsonUtil.toJson(req))
                    .post("/api/job/v3/job-manage/test_i18n")
                    .then()
                    .spec(ApiUtil.failResponseSpec(ErrorCode.RESULT_OK));
        }

        @Test
        @DisplayName("测试国际化Name异常")
        void testI18nWithWrongName() {
            EsbTestV3Req req = new EsbTestV3Req();
            req.setScopeId(String.valueOf(TestProps.DEFAULT_BIZ));
            req.setScopeType(ResourceScopeTypeEnum.BIZ.getValue());
            req.setTestName(null);
            req.setTestId(1L);

            given()
                .spec(ApiUtil.requestSpec(TestProps.DEFAULT_TEST_USER))
                .body(JsonUtil.toJson(req))
                .post("/api/job/v3/job-manage/test_i18n")
                .then()
                .spec(ApiUtil.failResponseSpec(ErrorCode.RESULT_OK));
        }
    }
}
