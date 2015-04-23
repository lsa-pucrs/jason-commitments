package commit.functions;

import java.util.ArrayList;
import java.util.List;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.Term;

public class snapshot extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
//		ts.getAg().getQueryProfiling()
//		Term result = addAnnotToList(un, args[0], args[1]);
		return true;
	}
}
