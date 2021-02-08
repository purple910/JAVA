package com.example.demo.excel;

/**
 * @author: 杨登柳
 * @description: 数据库变更记录
 * @date: 2021/2/5  10:34
 **/
public class RecordWorld {

    /**
     * 文件路径 20190726
     */
    private String filepath;

    /**
     * 操作信息 :
     * 新增表 AJ_YX_AJSLBZ 案件受理标准表 1
     * 表T_TYYW_XZ_XLJ_AJ添加字段JTJDCS(具体监督措施) 2
     * 表T_TYYW_AG_RJ_AJ 修改字段 GKCBRGH为 CHAR(10) 3
     * 新增索引IDX_AJ_YX_GLAJJL_GLAJ 4
     */
    private String msg;

    /**
     * 表名
     */
    private String table;

    /**
     * 字段
     */
    private String column;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 操作分类
     */
    private Integer state;

    /**
     * 表的模型
     */
    private String tableModel;


    @Override
    public String toString() {
        return "[ " + filepath + ": " + msg + ": " + table + ": " + column + "--" + type + " ]";
    }

    public String getTableModel() {
        return tableModel;
    }

    public void setTableModel(String tableModel) {
        this.tableModel = tableModel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
