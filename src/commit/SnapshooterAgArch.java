package commit;

import jason.infra.centralised.CentralisedAgArch;

import java.util.Timer;
import java.util.TimerTask;

public class SnapshooterAgArch extends CentralisedAgArch {

	@Override
	public void init() throws Exception {
		super.init();
		this.getTS().getSettings().setQueryProfiling(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				try {
					getTS().getAg().getQueryProfiling().show();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				receiveSyncSignal();
			}
		};
		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 1 * 1000;
		timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	}
	
	@Override
	public void informCycleFinished(boolean breakpoint, int cycle) {
		System.out.println("HERE");
		super.informCycleFinished(breakpoint, cycle);
	}

}
