/* Beliefs */
goods(0).
money(20).
commit(money(20)[transaction(5)]).

goal( wantfood   , goods(Q) & Q < 20    , offer(_)  , deadline(_) ).
goal( willpay    , offer(Q) & Q <= 1.50 , goods(20) , deadline(_) ).
goal( willnotpay , offer(Q) & Q > 1.50  , true      , deadline(_) ).
goal( payforfood , goods(20) & offer(_) , money(0)  , deadline(_) ).

commitment( makeoffer , creditor , merchant , answer(_) , offer(_) ). // merchant give offer if fuck yeah
commitment( givegood  , creditor , merchant , money(_)  , goods(_) ). // merchant give goods if accepted

/* Initial goals */
!start.

/* Plans */
+!start
<-
	!achieve_goals;
	.

+goods(HowMuch)[source(A)]:
	A \== self & goods(Current)
<-
	-goods(_);
	+goods(Current + HowMuch);
	.print("Hooray! ", HowMuch, " goods!");
	.