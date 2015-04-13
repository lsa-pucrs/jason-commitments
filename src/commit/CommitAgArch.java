package commit;

import jacamo.infra.JaCaMoAgArch;
import jason.JasonException;
import jason.asSemantics.Agent;
import jason.asSyntax.ASSyntax;
import jason.asSyntax.Literal;
import jason.asSyntax.Rule;
import jason.asSyntax.parser.ParseException;
import jason.asSyntax.parser.as2j;

import java.io.InputStream;
import java.util.Map;

import commit.model.Commit;

public class CommitAgArch extends JaCaMoAgArch {

	Map<String, Commit> commitments;

	@Override
	public void init() throws Exception {
		
		super.init();
		
		loadCommitter();
		
		addRule("null(C)", "not var(C)");
		addRule("detached(C)", "active(C) & p(C)");
		addRule("conditional(C)", "active(C) & not p(C)");
		addRule("active(C)", "var(C) & not terminal(C) & not pending(C) & not satisfied(C)");
		addRule("terminated(C)", "released(C) | ( not p(C) & canceled(C) )");
		addRule("violated(C)", "p(C) & cancelled(C)");
		addRule("satisfied(C)", "not null(C) & not terminal(C) & commitment(C, _, _, _, Y) & Y");
		addRule("terminal(C)", "( cancelled(C) | released(C) | expired(C) )");

		addRule("null(Gt, Bag)", "not goal_var(_, Gt, Bag)");
		addRule("inactiveG(G)", "not null(G) & not suspendedG(G) & not activeG(G) & not terminalG(G)");
		addRule("activeG(G)", "activatedG(G) & not terminalG(G) & not suspendedG(G) & not satisfiedG(G)"); 
		addRule("satisfiedG(G)", "not null(G) & not terminalG(G)");
		addRule("failedG(G)", "not null(G)");
		addRule("terminatedG(G)", "not null(G) & ( dropped(G) | aborted(G) )");
		addRule("terminalG(G)", "( dropped(G) | aborted(G) )");

		addRule("eqGSCP(G, C)", "goal(G, _, X, _) & commitment(C, _, _, Y, _) & commit.functions.functor(X, XX) & commit.functions.functor(Y, YY) & XX == YY"); //( ( not X | Y ) & ( X | not Y ) )");
		addRule("eqGSCQ(G, C)", "goal(G, _, X, _) & commitment(C, _, _, _, Y) & commit.functions.functor(X, XX) & commit.functions.functor(Y, YY) & XX == YY"); //( ( not X | Y ) & ( X | not Y ) )");
		addRule("eqCPCQ(C1, C2)", "commitment(C1, _, _, X, _) & commitment(C2, _, _, Y, _) & ( ( not X | Y ) & ( X | not Y ) )");
	}
	
	public void loadCommitter() throws ParseException, JasonException{		
		InputStream committer = this.getClass().getClassLoader().getResourceAsStream("./commit/agt/committer.asl");
		Agent ag = new Agent();
		ag.initAg();
		as2j parser = new as2j(committer);
		parser.agent(ag);
		getTS().getAg().importComponents(ag);
	}

	public void addRule(String head, String body) {
		try {
			Rule belief = new Rule(Literal.parseLiteral(head), ASSyntax.parseFormula(body));
			addBelief(belief);
		} catch (ParseException e) {
		}
	}

	public void addBelief(Literal belief) {
		getTS().getAg().getBB().add(belief);
	}

}
