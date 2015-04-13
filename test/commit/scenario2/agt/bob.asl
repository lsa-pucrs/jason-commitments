/* Beliefs */

/* Initial goals */	
!start.
	
/* Plans */
+!start
<-
	.wait(1000);
	!release(1);
	.wait(1000);
		
	!reboot;
		
	.wait(2000);
	!release(1);
	
	!reboot;
	
	.wait(1000);
	!declare(paid(12)[t(1)]);
	.wait(1000);
	
	!reboot;
	
	!release(1);
	.wait(1000);
	
	.