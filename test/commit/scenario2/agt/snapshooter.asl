/* Beliefs */

/* Initial goals */
!start.

/* Plans */
+!start:
	true
<-
	commit.functions.agents(Ags, "^bob[0-9]+");
	.wait(1000);
	!start;
	.