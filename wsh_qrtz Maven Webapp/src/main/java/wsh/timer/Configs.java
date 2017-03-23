package wsh.timer;

public class Configs {
	private String url="http://w.lxy716.com/home/";
	private String port="";
	private String controller_name="Test";
	private String method_name="test";
	private Boolean is_modify_url=false; 
	public String getUrl() { 
		return url;
	}
	public void setUrl(String url) {
		this.is_modify_url=true;
		this.url = url;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getController_name() {
		return controller_name;
	}
	public void setController_name(String controller_name) { 
		this.controller_name = controller_name;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) { 
		this.method_name = method_name;
	}
	
}
