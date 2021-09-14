package com.yanxin.store.commont;

import com.blankj.utilcode.util.SPUtils;

public class Constant {
    /**
     * 服务器状态码，保存常见的几个即可
     */
    public static class HttpRequestStatus {


    }

    /**
     * 权限状态码统一保存
     */
    public static class PermissionStatus {
        /**
         * 相机权限
         */
        public static final int PER_CAMERA = 10001;
        /**
         * 写入文件权限
         */
        public static final int PER_WRITE_EXTERNAL_STORAGE = 10002;

        /**
         * 读取文件权限 tips:写权限权重更大，一般同时申请，状态码一样就好
         */
        public static final int PER_READ_EXTERNAL_STORAGE = PER_WRITE_EXTERNAL_STORAGE;

        /**
         * Q 读取文件权限 tips:写权限权重更大，一般同时申请，状态码一样就好
         */
        public static final int PER_ACCESS_MEDIA_LOCATION = PER_READ_EXTERNAL_STORAGE;


        public static final int PER_DOWN_READ_EXTERNAL_STORAGE = 10003;

        /**
         * 打电话权限
         */
        public static final int PER_CALL_PHONE = 10004;
        /**
         * 定位
         */
        public static final int PER_FIND_LOCATION = 10005;
    }


    /**
     * 用于逻辑不复杂不存在多线程的不同页面之间的状态传递，防止回调太多
     */
    public static class AsynchronousStatus {
        /**
         * 待审核
         */
        public static int REGISTER_WAIT = 0;
        /**
         * 审核成功
         */
        public static int REGISTER_SUCCESS = 1;
        /**
         * 审核驳回、失败
         */
        public static int REGISTER_FAILED = 1;

        /**
         * 车主账户
         */
        public static int USER_TYPE_CAR_OWNER = 1;
        /**
         * 技师账户
         */
        public static int USER_TYPE_TECHNICIAN = 2;
        /**
         * 店铺商户账户
         */
        public static int USER_TYPE_STORE = 3;

        /**
         * 店铺维修品牌跳转页面传递的Code
         */
        public static int REGISTER_BRAND_CODE = 2000;
        /**
         * 筛选条件类型 品牌
         */
        public static int FILTRATE_TYPE_BRAND = 1;

        /**
         * 筛选条件类型 车型
         */
        public static int FILTRATE_TYPE_MODEL = 2;

        /**
         * 筛选条件类型 系统
         */
        public static int FILTRATE_TYPE_SYS = 3;

        /**
         * DTC 类型
         */
        public static int FILTRATE_TYPE_DTC = 4;

        /**
         * 筛选条件类型 技师类型
         */
        public static int FILTRATE_TYPE_TECHNICIAN = 5;

        /**
         * 服务页面的在线状态
         */
        public static boolean SERVICE_ONLINE = SPUtils.getInstance().getBoolean("is_online", true);
    }

    /**
     * 隐私政策等匹配码
     */
    public static class WebDocumentTag {
        /**
         * 隐私政策
         */
        public static final int TAG_PRIVATE_AGREE = 1000;
        /**
         * 服务协议
         */
        public static final int TAG_SERVICE_AGREE = TAG_PRIVATE_AGREE + 1;
        /**
         * 版权声明
         */
        public static final int TAG_COPYRIGHT_AGREE = TAG_SERVICE_AGREE + 1;
    }

    /**
     * 传递给服务器的固定参数   以下字段全部为字典表查询Type
     */
    public static class AppConfig {
        /**
         * 查询所有车辆品牌
         */
        public static final String BRAND_PARENT = "0001";

        /**
         * 查询基础上门费
         */
        public static final String BASE_FEE = "6206";

        /**
         * 平台上门服务费
         */
        public static final String BASE_SERVING_FEE = "6207";

        /**
         * 查询案例 /筛选系统栏 attach_sys:所属系统
         */
        public static final String ATTACH_SYS = "attach_sys";

        /**
         * 查询案例  technician_type:  技术类型
         */
        public static final String TECHNICIAN_TYPE = "technician_type";

        /**
         * dtc类型  dtc_type:  dtc类型
         */
        public static final String DTC_TYPE = "dtc_type";

        /**
         * 发动机排量获取
         */
        public static final String ENGINE_DISPLACEMENT = "engine_displacement";


        /**
         * 驱动方式获取
         */
        public static final String DRIVING_MODE = "driving_mode";

        /**
         * 增压系统
         */
        public static final String SUPERCHARGING_SYSTEM = "supercharging_system";

        /**
         * 变速器类型
         */
        public static final String TRANSMISSION_TYPE = "transmission_type";

        /**
         * 相关线路检查费用
         */
        public static final String LINE_INSPECT_COST = "line_inspect_cost";

        /**
         * 车辆钣金修复费用
         */
        public static final String SHEET_METAL_REPAIR_COST = "sheet_metal_repair_cost";

        /**
         * 基本检查费
         */
        public static final String BASIC_INSPECT_COST = "basic_inspect_cost";

        /**
         * 诊断仪使用费
         */
        public static final String DIAGNOSIS_INSTRUMENT_USE_COST = "diagnosis_instrument_use_cost";

        /**
         * 车辆油漆修复费用
         */
        public static final String PAINT_REPAIR_COST = "paint_repair_cost";

        /**
         * 其他费用
         */
        public static final String OTHER_COST = "other_cost";

        /**
         * 平台订单服务费
         */
        public static final String ORDER_SERVICE_COST = "order_service_cost";

        /**
         * 诊断过程PDF
         */
        public static final String CASE_DOCUMENT = "https://dl-car.oss-cn-beijing.aliyuncs.com/other/2021-08-11/0b094a44c67a40228475e586c08c10ce.pdf";
    }
}
