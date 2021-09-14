package com.yanxin.store.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.yanxin.store.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsSubstanceBean extends BaseBean {

    /**
     * code : 0000
     * data : [{"groupName":"string","groupNameEn":"string","orderNum":0,"parentId":"string","sysSubsidy":0,"uuid":"string"}]
     * msg : 操作成功
     * success : true
     */

    private String code;
    private ArrayList<DataBean> data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * groupName (string, optional): 商品分组名称 ,
         * groupNameEn (string, optional): 商品分组英文名称 ,
         * orderNum (integer, optional): 排序 ,
         * parentId (string, optional): 父组id ,
         * sysSubsidy (number, optional): 平台补贴 ,
         * uuid (string, optional): 主键ID
         */

        private String groupName;
        private String groupNameEn;
        private int orderNum;
        private String parentId;
        private float sysSubsidy;
        private String uuid;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupNameEn() {
            return groupNameEn;
        }

        public void setGroupNameEn(String groupNameEn) {
            this.groupNameEn = groupNameEn;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public float getSysSubsidy() {
            return sysSubsidy;
        }

        public void setSysSubsidy(float sysSubsidy) {
            this.sysSubsidy = sysSubsidy;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        @Override
        public String getPickerViewText() {
            return groupName;
        }
    }
}
