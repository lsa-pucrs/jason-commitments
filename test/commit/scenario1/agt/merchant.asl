/* Beliefs */
goal( g1 , true    , paid(_)  , deadline(_) ).
goal( g3 , paid(_) , goods(_) , deadline(_) ).

commitment( c1 , debtor   , [customer] , paid(_)     , goods(_)  ).
commitment( c2 , debtor   , [customer] , accepted(_) , goods(_)  ).
commitment( c3 , debtor   , [customer] , returned(_) , goods(_)  ).
commitment( c4 , debtor   , [customer] , returned(_) , refund(_) ).
commitment( c5 , creditor , [customer] , goods(_)    , paid(_)   ).
commitment( c6 , creditor , [customer] , accepted(_) , goods(_)  ).

/* Initial goals */	
!start.
	
/* Plans */
+!start
<-
	!achieveGoals;
	.
	
+!detach(C, Dbag)
	: commit_var(C, Ct, _, Cr, _) & Ct == c1
<-
	.print("Send ", Dbag, " goods");
	!deliver(Cr, Dbag)
	.

+!deliver(Ag, What)
	: true
<-
	.send(Ag, tell, What);
	.
    
+!sendRefund
	: true
<-
	.send(customer, tell, goods(10));
	.
    
+!manufactureGoods
	: true
<-
	.send(customer, tell, goods(10));
	.