/* Beliefs */

/* Initial goals */	
!start.
	
/* Plans */
+!start
<-
	.wait(2000);
	.random(Wait);
	.wait(Wait * 5000);
	!declare(paid(12)[t(1)]);
	.