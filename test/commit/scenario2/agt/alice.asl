// The formula C(x, y, r,u) represents a commitment,
// x (the debtor) is committed
// y (the creditor) that
// if r (the antecedent) holds,
// then u (the consequent) will hold

// C(Alice, Bob, paid($12), delivered(bnw))

/* Beliefs */

/* Initial goals */
!start.

/* Plans */
+!start:
	true
<-	
	commit.functions.agents(Ags, "^bob[0-9]+");
	for(.member(Ag, Ags)){
		!create(Ag, paid(12), delivered(bnw));
	}
	.