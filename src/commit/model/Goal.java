package commit.model;

import cartago.Artifact;
import cartago.OPERATION;

public class Goal extends Artifact {

	protected String uid;

	protected void init() {
	}

	protected void init(String uid, String goalType) {
		this.uid = uid;
	}
	
	@OPERATION
	void accept() {
	}

}