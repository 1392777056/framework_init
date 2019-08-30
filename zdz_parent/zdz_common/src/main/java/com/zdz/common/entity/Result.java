package com.zdz.common.entity;


import lombok.Data;

/**
 * 数据响应对象
 */
@Data
public class Result {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private Object data;

    public Result() {
    }

    /**
     * 增删改 -- 用
     * @param code  响应结果
     */
    public Result(ResultCode code){
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    /**
     * 查询 -- 用
     * @param code   响应结果
     * @param data   数据
     */
    public Result(ResultCode code,Object data){
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.data = data;
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static Result ERROR(){
        return new Result(ResultCode.SERVER_ERROR);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }
}
