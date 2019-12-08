package user;
import java.io.Serializable;
/**
 * @author joshherrera
 * @author parthpatel
 */
public class UserTags implements Serializable {

	private String tagName;
	private String tagValue;


	/**
	 * Initiates Photo Object
	 * @param Name Name of tag
	 * @param Value Value of tag
	 */
	public UserTags(String Name, String Value) {
		this.tagName = Name;
		this.tagValue = Value;
	}

	
	
	/**
	 * return tag name
	 * @return tag name
	 */
	public String getTag() {
		return this.tagName;
	}
	
	/**
	 * return tag value
	 * @return tag value
	 */
	public String getValue() {
		return this.tagValue;
	}
	
	
	/**
	 * toStirng method to print object
	 */
	public String toString() {
		return "#"+ tagName +" " + tagValue + "\n";
	}

}
