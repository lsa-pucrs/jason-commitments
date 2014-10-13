/* Beliefs */
needgoods(123).
needgoods(345).

goal( g2 , needgoods(_) , goods(_) , deadline(_) ).
goal( g4 , goods(_)     , paid(_)  , deadline(_) ).

commitment(c1, creditor , [merchant] , paid(_)     , goods(_)  ).
commitment(c2, creditor , [merchant] , accepted    , goods     ).
commitment(c3, creditor , [merchant] , returned(_) , goods(_)  ).
commitment(c4, creditor , [merchant] , returned(_) , refund(_) ).
commitment(c5, debtor   , [merchant] , goods(_)    , paid(_)   ).
commitment(c6, debtor   , [merchant] , accepted(_) , goods(_)  ).

/* Initial goals */
!start.

/* Plans */
+!start
<-
	!achieveGoals;
	.
	
+goods(HowMuch) <-
	.print("Hooray! ", HowMuch, " goods!");
	-needgoods(HowMuch).