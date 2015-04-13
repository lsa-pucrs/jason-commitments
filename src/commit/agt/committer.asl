+!achieve_goals
	: true
<-
	.findall(X, goal(X, _, _, _), Gts);
	for( .member(Gt, Gts) ) {
		!consider_goal(Gt);
		!activate_goal(Gt);
	}
	.wait(100);
	!achieve_goals;
	.

+!consider_goal(Gt)
	: goal(Gt, _, Var, _)
<-
	.findall(X, goal(Gt, X, _, _), PreConds);
	for( .member(PreCond, PreConds) ) {
		.eval(PreCondResult, PreCond);
		if(PreCondResult){
			!create_goal(Gt, Var, G);
		}
	}
	.

+!create_goal(Gt, Var, G)
	: not goal_var(_, Gt, Var)
<-
	commit.functions.uniqueid(Gt, Var, G);
	.print("Activating goal ", Gt, " to reach ", Var);
	makeArtifact(G, "commit.model.Goal", [G, Gt], Art);
	focus(Art);
	+goal_var(G, Gt, Var);
	.

+!create_goal(Gt, Var, G) : true <- true; .

+!activate_goal(Gt)
	: goal_var(G, Gt, Bag) & commitment(Ct, _, _, _, _) & eqGSCQ(Gt, Ct) & commit_var(C, Ct, De, _, Bag) & .my_name(Ag) & Ag == De
<-
	!satisfy(C, G);
	.

+!activate_goal(Gt)
	: goal_var(G, Gt, Bag) & commitment(Ct, _, _, _, _) & eqGSCQ(Gt, Ct)
<-
	!entice(G, Ct, C);
	.

+!activate_goal(Gt) : true <- true; .
	
//+!detach(G, Gt, C, Ct, De, Cr, Bag)
//<-
//	.send(Cr, tell, Bag);
//.
//	
//+!detach(G, Gt, C, Ct, De, Cr, Bag)
//<-
//	.send(Cr, tell, Bag);
//.

//
// Creditor
//
+!entice(G, Ct, C)
    : goal_var(G, Gt, Bag) & commitment(Ct, _, De, _, _) & .my_name(Cr) & not commit_var(_, Ct, De, Cr, Bag)
<- 
	.print("Creating commitment ", Ct, " with bag ", Bag);
	commit.functions.uniqueid(Ct, De, Cr, Bag, C);
	+commit_var(C, Ct, De, Cr, Bag);
	.send( De, tell, commit_var(C, Ct, De, Cr, Bag) );
    .

+!entice(G, Ct, C) : true <- true; .

+commit_accepted(Uid)
	: commit_var(C, Ct, De, Cr, Bag) & .term2string(C, Uid) & .my_name(Me) & Me == Cr
<-
	.print("Received ", C, "!");
	.

+commit_satisfied(Uid)
	: commit_var(C, Ct, De, Cr, Bag) & .term2string(C, Uid) & goal_var(G, Gt, Bag) & eqGSCQ(Gt, Ct) & .my_name(Me) & Me == Cr
<-
	.print("Satisfied ", C, "!");
	!satisfy(C, G);
	.

//
// Debtor
//
+commit_var(C, Ct, De, Cr, Bag)[source(self)]
	: .my_name(Me) & Me == Cr
<- 
	makeArtifact(C, "commit.model.Commit", [C, Ct, De, Cr], Art);
	focus(Art);
	.

+commit_var(C, Ct, De, Cr, Bag)[source(A)]
	: commitment(Ct, _, _, _, _) & goal( Gt, _, _, _ ) & eqGSCQ(Gt, Ct)
<- 
	.print("Accepting commit ", Ct," from ", A, " using goal ", Gt);
	lookupArtifact(C, Art);
	focus(Art);
	accept(C) [artifact_id(Art)];
	!create_goal(Gt, Bag, G);
	.

+!satisfy(C, G)
	: goal_var(G, Gt, Bag) & commit_var(C, Ct, De, Cr, Bag) & eqGSCQ(Gt, Ct)
<-	
	.print("Will do ", Gt, " to satisfy ", Ct);
	
	!detach(Gt, Ct, Cr, Bag);
	
	lookupArtifact(C, Art);
	satisfy(C) [artifact_id(Art)];
	
	// Change status?
	-goal_var(G, Gt, Bag);
	-commit_var(C, Ct, _, _, Bag);
	.