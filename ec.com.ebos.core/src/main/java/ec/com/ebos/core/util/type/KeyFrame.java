package ec.com.ebos.core.util.type;

import lombok.Data;

@Data
public class KeyFrame {	
	
	private String key;
	private boolean state;
	
	public KeyFrame(String key, boolean state) {
		this.key = key;
		this.state = state;
	}

}
