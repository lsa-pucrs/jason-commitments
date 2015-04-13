package commit.scenario2;

import jacamo.infra.JaCaMoLauncher;

import org.junit.Before;
import org.junit.Test;

public class Scenario2 {

	@Before
	public void setUp() throws Exception {

		String[] args = new String[] { "test/commit/scenario2/scenario2.jcm" };

		JaCaMoLauncher runner = new JaCaMoLauncher();
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
