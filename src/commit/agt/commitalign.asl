+!create(Creditor, Antecedent, Consequent)
	: true
<-
	?id(Id);
	!create(Id, Creditor, Antecedent, Consequent);
	-+id(Id+1);
	.

+!create(Id, Creditor, Antecedent, Consequent)
	: true
<-	
	commit.functions.add_annot(Antecedent, t(Id), A);
	commit.functions.add_annot(Consequent, t(Id), C);
	.my_name(Debtor);

	.send(Creditor, tell , create(A, C));
	+commit(Debtor, Creditor, Antecedent, Consequent)[t(Id)];
	commit.functions.reason_commit(Debtor, Creditor, A, C, Id, Commit);
	.print("Commitment created");
	.

+create(Antecedent, Consequent)[source(Debtor)]:
	commit.functions.checktransaction(Antecedent, Consequent)
<-
	-create(Antecedent, Consequent)[source(Debtor)];
	.my_name(Creditor);
	commit.functions.gettransaction(EId, Antecedent, Consequent);
	?id(Id);
	+extid(Debtor, EId, Id);
	+commit(Debtor, Creditor, Antecedent, Consequent)[t(Id)];
	-+id(Id+1);
	.print("Commitment create received");
	.
	
+!declare(Statement[t(Transaction)])
	: true
<-
	.my_name(Me);
	?commit(Debtor, Creditor, _, _)[t(Transaction)];
	if(Me == Debtor){
		Id = Transaction;
		Agent = Creditor;
	}
	if(Me == Creditor){
		Agent = Debtor;
		?extid(Debtor, Id, Transaction);
	}
	+Statement;
	.send(Agent, tell , statement(Statement)[t(Id)]);
	.print("Declarate sended ", Transaction);
	.
	
-!declare(Statement[t(Transaction)])
	: true
<-
	.print("Inexistant transaction ", Transaction);
	.
	
+statement(Statement)[t(Transaction),source(Agent)]:
	true
<-
	+Statement[t(Transaction)];
	.print("Declarate received");
	
	?commit(Debtor1, Creditor1, Antecedent1, Consequent1)[t(Transaction)];
	if(Antecedent1){
		-commit(_, _, _, _)[t(Transaction)];
		+commit(Debtor1, Creditor1, true, Consequent1)[t(Transaction)];
		.print("Detached ", Transaction);
	}
	
	?commit(Debtor2, Creditor2, Antecedent2, Consequent2)[t(Transaction)];
	if(Consequent2){
		-commit(_, _, _, _)[t(Transaction)];
		+commit(Debtor2, Creditor2, Antecedent2, true)[t(Transaction)];
		.print("Discharged ", Transaction);
	}
	-statement(_);
	.
	
+!release(Transaction)
	: true
<-
	?commit(Debtor, _, _, _)[t(Transaction)];
	?extid(Debtor, Id, Transaction);
	.send(Debtor, tell, release(Id));
	-commit(Debtor, _, _, _)[t(Transaction)];
	-extid(Debtor, _, Transaction);
	.print("Commit released");
	.
	
-!release(Transaction)
	: true
<-
	.print("No commit to release");
	.
	
+release(Transaction)[source(Debtor)]:
	true
<-
	-release(Transaction)[source(Debtor)];
	-release(Transaction);
	-commit(Debtor, _, _, _)[t(Transaction)];
	.print("Commit release received");
	.
	
// Who creates, cancels
+!cancel(Transaction)
	: true
<-
	?commit(_, Creditor, _, _)[t(Transaction)];
	.send(Creditor, tell, cancel(Transaction));
	-commit(_, Creditor, _, _)[t(Transaction)];
	.print("Commit cancelled");
	.
	
-!cancel(Transaction)
	: true
<-
	.print("No commit to cancel");
	.
	
+cancel(Transaction)[source(Debtor)]:
	true
<-
	?extid(Debtor, Id, Transaction);
	-cancel(Transaction)[source(Debtor)];
	-commit(Debtor, _, _, _)[t(Id)];
	-extid(Debtor, _, Transaction);
	.print("Commit cancel received");
	.
	
+!reboot:
	true
<-
	.print("Rebooting...");
	.wait(4000);
	-commit(_, _, _, _);
	-+id(1);
	-statement(_);
	.wait(4000);
	.print("Online!");
	.