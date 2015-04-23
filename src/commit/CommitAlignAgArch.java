package commit;

import jacamo.infra.JaCaMoAgArch;
import jason.JasonException;
import jason.asSemantics.Agent;
import jason.asSyntax.Literal;
import jason.asSyntax.parser.ParseException;
import jason.asSyntax.parser.as2j;

import java.io.InputStream;

public class CommitAlignAgArch extends JaCaMoAgArch {

	@Override
	public void init() throws Exception {
		super.init();
		loadCommitter();
	}

	public void loadCommitter() throws ParseException, JasonException {
		InputStream committer = this.getClass().getClassLoader().getResourceAsStream("./commit/agt/commitalign.asl");
		Agent ag = new Agent();
		ag.initAg();
		as2j parser = new as2j(committer);
		parser.agent(ag);
		getTS().getAg().addBel(Literal.parseLiteral("id(1)"));
		getTS().getAg().importComponents(ag);
	}

}
