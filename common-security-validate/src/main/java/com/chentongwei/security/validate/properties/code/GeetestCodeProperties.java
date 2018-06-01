package com.chentongwei.security.validate.properties.code;

/**
 * 极验验证配置
 *
 * @author TongWei.Chen 2017-6-4 11:37:56
 */
public class GeetestCodeProperties extends CommonCodeProperties {
	/**
	 * id
	 */
	private String id = "id";
	/**
	 * key
	 */
	private String key = "key";
	/**
	 * 是否开启新的failback
	 */
	private Boolean newfailback = Boolean.TRUE;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getNewfailback() {
		return newfailback;
	}

	public void setNewfailback(Boolean newfailback) {
		this.newfailback = newfailback;
	}

	@Override
	public String toString() {
		return "GeetestConstant [id=" + id + ", key=" + key + ", newfailback=" + newfailback + "]";
	}
}
