package commit.functions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class gettransaction extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMinArgs() {
		return 1;
	}

	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);
		NumberTerm t = (NumberTermImpl) getFirst(args[1]);
		return un.unifies(args[0], t);
	}

	protected Term getFirst(Term t) {
		if (t instanceof LogExpr) {
			return getFirst(((LogExpr) t).getTerms().get(0));
		} else if (t.isLiteral()) {
			return ((Literal) t).getAnnot("t").getTerm(0);
		}
		return null;
	}
}
