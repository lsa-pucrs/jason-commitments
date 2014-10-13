+!achieveGoals
<-
	.findall(X, goal(X, _, _, _), G);
	for( .member(X, G) ) {
		!achieveGoal(X);
	}
	!achieveGoals;
	.
		
+!achieveGoal(G)
	: goal(G, _, _, _) & not activeG(G)
<-
	.findall(X, goal(G, X, _, _), PC);
	for( .member(X, PC) ) {
		if(not X){
			.fail;
		}
	}
	.print("Activating goal ", G);
	!activate(G);
	.
	
+!achieveGoal(G)
	: goal(G, _, _, _) & activeG(G) & commitment(Ct, W, _, _, _) & eqGSCP(G, Ct)
<-	
	.print("Enticing a commitment ", Ct, " to satisfy ", G);
	!entice(G, Ct);
	.
	
+!achieveGoalForCommit(C)
	: commit_var(C, Ct, _, _, _) & not detached(C) & goal(G, _, _, _) & eqGSCQ(G, Ct)
<-
	.print("Activating ", G, " to satisfy ", Ct);
	!activate(G);
	!satisfy(C, G);
	.
	
+!achieveGoalForCommit(C)
	: true
<-
	.print("There is no goal to satisfy commitment ", C);
	.
	
-!achieveGoal(G) : true <- true.
	
+!activate(G)
	: true
<-
	+activeG(G);
	.
	
+!satisfy(C, G)
	: goal(G, _, _, _) & activeG(G) & commit_var(C, Ct, De, Cr, Bag) & commitment(Ct, _, _, _, Con) & .my_name(Ag)
<-
	lookupArtifact(C, Art);
	commit.functions.join(Con, Bag, DBag);
	!detach(C, DBag);
	detach [artifact_id(Id)];
	.
	
+satisfied
	: commit_var(C, Ct, De, Cr, Bag) & commitment(Ct, _, _, _, Con) & commit.functions.has(Con, Bag)
	  & goal(G, _, _, _) & activeG(G) & eqGSCQ(G, Ct)
<- 
	-activeG(G);
	.print("Received!"); 
	.
	
+!entice(G, C)
    : goal(G, X, _, _) & activeG(G) & commitment(Ct, _, Des, _, _) & .my_name(Cr)
<- 
	.print("Looking for ", X, " to create a bag");
	.wait(1000);
	commit.functions.findallbeliefs(X, PC);
	for( .member(B, PC) ) {
		.print("Found ", B);
		commit.functions.terms(B, Var);
		for( .member(De, Des) ) {
    		!create(Ct, De, Cr, Var);
    	}
	}
    .
    
+!join(C, Ct, De, Cr, Bag)
	: not commit_var(C, Ct, De, Cr, Bag)
<-
	.print("Joining commitment ", Ct, " ", Bag);
	lookupArtifact(C, Art);
	focus(Art);
	accept [artifact_id(Id)];
	+commit_var(C, Ct, De, Cr, Bag);
	!achieveGoalForCommit(C);
	.
	
+!create(Ct, De, Cr, Bag)
	: not commit_var(_, Ct, De, Cr, Bag)
<-	
	.print("Creating commitment ", Ct, " ", Bag);
	commit.functions.uniqueid(Ct, De, Cr, Bag, C);
	makeArtifact(C, "commit.model.Commit", [C, Ct, De, Cr, Bag], Art);
	focus(Art);
	+commit_var(C, Ct, De, Cr, Bag);
	.send( De, achieve, join(C, Ct, De, Cr, Bag) );
	.