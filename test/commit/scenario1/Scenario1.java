package commit.scenario1;

import jason.infra.centralised.RunCentralisedMAS;

import org.junit.Before;
import org.junit.Test;

public class Scenario1 {

	@Before
	public void setUp() throws Exception {

		String[] args = new String[] { "test/commit/scenario1/scenario1.jcm" };

		RunCentralisedMAS runner = new RunCentralisedMAS();
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
