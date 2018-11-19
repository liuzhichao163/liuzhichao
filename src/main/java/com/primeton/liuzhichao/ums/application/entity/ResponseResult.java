package com.primeton.liuzhichao.ums.application.entity;

/**
 * 返回统一格式的json字符串对象
 * @author ASUS
 *
 * @param <T>
 */
public class ResponseResult<T> {
	
	private Integer state;
	private String message;
	private T data;

	public ResponseResult() {
		super();
	}

	public ResponseResult(Integer state) {
		super();
		setState(state);
	}
	
	public ResponseResult(ResultEnum resultEnum) {
        super();
        this.message = resultEnum.getMsg();
        this.state = resultEnum.getCode();
    }

	public ResponseResult(Integer state, String message) {
		super();
		setState(state);
		setMessage(message);
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	
}
