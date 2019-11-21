package user;
import java.io.Serializable;

public class UserTags implements Serializable {

	private String tagName;
	private String tagValue;


	public UserTags(String Name, String Value) {
		this.tagName = Name;
		this.tagValue = Value;
	}

	
	
	public String getTag() {
		return this.tagName;
	}
	
	public String getValue() {
		return this.tagValue;
	}
	
	
	public String toString() {
		return "#"+ tagName +" " + tagValue + "\n";
	}

}
