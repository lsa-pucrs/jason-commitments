package commit.scenario1;

import jacamo.infra.JaCaMoLauncher;
import jason.infra.centralised.RunCentralisedMAS;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

public class Scenario1 extends RunCentralisedMAS {

	@Before
	public void setUp() throws Exception {
		
		String[] args = new String[] { "test/commit/scenario1/scenario1.jcm" };
		
        logger = Logger.getLogger(JaCaMoLauncher.class.getName());
        runner = new JaCaMoLauncher();
        runner.init(args);
        runner.getProject().addSourcePath("/media/Storage/Documents/Master/repos/jason-commitments/src/commit/agt");
        runner.create();
        runner.start();
        runner.waitEnd();
        runner.finish();
	}
	
	@Test
	public void run() {
	}

}
