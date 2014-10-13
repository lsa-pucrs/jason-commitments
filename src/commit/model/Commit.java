package commit.model;

import cartago.AgentId;
import cartago.Artifact;
import cartago.GUARD;
import cartago.OPERATION;

public class Commit extends Artifact {

	protected CommitState state;
	protected String uid;

	protected AgentId creditor;
	protected AgentId debtor;

	protected void init() {
	}

	protected void init(String uid, String commitType, String debtor, String creditor, Object... params) {
		this.uid = uid;
		this.creditor = getCreatorId();
	}

	@OPERATION
	void accept() {
		debtor = getOpUserId();
	}

	@OPERATION(guard = "isDebtor")
	void detach() {
		signal(creditor, "satisfied");
	}

	@GUARD
	boolean isDebtor() {
		return getOpUserId().equals(debtor);
	}

}