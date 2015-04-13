package commit.model;

import cartago.AgentId;
import cartago.Artifact;
import cartago.OPERATION;

public class Commit extends Artifact {

	protected CommitState state;
	protected String uid;

	protected AgentId creditor;
	protected AgentId debtor;

	protected void init() {
	}

	protected void init(String uid, String commitType, String debtor, String creditor) {
		this.uid = uid;
		this.creditor = getCreatorId();
	}

	@OPERATION
	void accept(String uid) {
		debtor = getOpUserId();
		signal(creditor, "commit_accepted", uid);
	}

	@OPERATION
	void satisfy(String uid) {
		AgentId ag = getOpUserId();
		if (debtor.equals(ag))
			signal(creditor, "commit_satisfied", uid);
		else
			signal(debtor, "commit_satisfied", uid);
	}

}