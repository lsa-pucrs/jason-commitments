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
	!create(bob, paid(12), delivered(bnw));
	.wait(2000);
	!create(bob, paid(12), delivered(bnw));
	
	!reboot;

	!create(bob, paid(12), delivered(bnw));
	.wait(1000);
	!create(bob, paid(12), delivered(bnw));
	.wait(1000);
	
	!reboot;
	
	!create(bob, paid(12), delivered(bnw));
	.wait(2000);
	!cancel(1);
	
	!reboot;
	
	.wait(1000);
	!create(bob, paid(12), delivered(bnw));
	
	.