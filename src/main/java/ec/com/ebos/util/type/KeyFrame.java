package ec.com.ebos.util.type;

import lombok.Data;

@Data
public class KeyFrame {	
	
	private String key;
	private Boolean state;
	
	public KeyFrame(String key, boolean state) {
		this.key = key;
		this.state = state;
	}

}
