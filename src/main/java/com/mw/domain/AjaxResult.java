package com.mw.domain;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";


    public AjaxResult() {
    }

    /** 静态创建对象 -- 成功操作 */
    public static AjaxResult success() {
        return new AjaxResult().code(200).msg("操作成功");
    }

    /** 静态创建对象 -- 失败操作 */
    public static AjaxResult error() {
        return new AjaxResult().code(500).msg("操作失败");
    }

    /** 静态创建对象 -- 请求存在问题 */
    public static AjaxResult bad() {
        return new AjaxResult().code(400).msg("请求错误");
    }

    public AjaxResult code(int code) {
        super.put(CODE_TAG, code);
        return this;
    }

    public AjaxResult msg(String msg) {
        super.put(MSG_TAG, msg);
        return this;
    }

    /** 自定义data属性值，如果下面四个预定义的不符合要求的话 */
    public AjaxResult data(Object data) {
        super.put(DATA_TAG, data);
        return this;
    }

    /** 查询结果 */
    public AjaxResult selectResult(int total, Object rows) {
        super.put(DATA_TAG, new Rows(total, rows));
        return this;
    }

    /** 更新结果 */
    public AjaxResult updateResult(int affectedRows) {
        super.put(DATA_TAG, new Result(affectedRows, "update"));
        return this;
    }

    /** 插入结果 */
    public AjaxResult insertResult(int affectedRows) {
        super.put(DATA_TAG, new Result(affectedRows, "insert"));
        return this;
    }

    /** 删除结果 */
    public AjaxResult deleteResult(int affectedRows) {
        super.put(DATA_TAG, new Result(affectedRows, "delete"));
        return this;
    }

    /** 除了code、msg、data，还可以有其它名值对 */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    private static class Rows {
        private int total;
        private Object rows;
        private static final String type = "select";

        Rows(int total, Object rows) {
            this.total = total;
            this.rows = rows;
        }

        public int getTotal() {
            return total;
        }
        public Object getRows() {
            return rows;
        }
        public String getType() {
            return type;
        }
    }

    static class Result{
        private int affectedRows;
        private String type = "update";     // or delete, insert

        Result(int affectedRows, String type) {
            this.affectedRows = affectedRows;
            this.type = type;
        }

        public int getAffectedRows() {
            return affectedRows;
        }
        public String getType() {
            return type;
        }
    }
}
