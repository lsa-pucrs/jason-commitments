/* Beliefs */
goods(20).
money(0).

goal( makeoffer   , money(Q) & Q < 20              , answer(_) , deadline(_) ).
goal( givegoods   , goods(20) & accepted           , goods(0)  , deadline(_) ).
goal( wantmoney   , goods(0) & money(0) & accepted , money(20) , deadline(_) ).
goal( returngoods , goods(0) & money(0) & rejected , goods(20) , deadline(_) ).

commitment( makeoffer , debtor , customer , offer(_) , answer(_) ). // merchant give offer if fuck yeah
commitment( givegood  , debtor , customer , goods(_) , money(_) ). // merchant give goods if accepted

/* Initial goals */	
!start.
	
/* Plans */
+!start
<-
	!achieve_goals;
	.
	
+!detach(Gt, Ct, Cr, Bag):
	Gt == offer
<-
	print(Gt, " ", Ct, " ", Cr, " ", Bag);
	.

+money(HowMuch)[source(A)]:
	A \== self & money(Current)
<-
	-money(_);
	+money(Current + HowMuch);
	.print("Hooray! ", HowMuch, " money!");
	.

+goods(HowMuch)[source(A)]:
	A \== self & goods(Current)
<-
	-goods(_);
	+goods(Current + HowMuch);
	.print("Return of ", HowMuch, " goods!");
	.