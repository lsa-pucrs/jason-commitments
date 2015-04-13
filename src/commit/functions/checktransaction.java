package commit.functions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.NumberTermImpl;
import jason.asSyntax.Term;

public class checktransaction extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMinArgs() {
		return 1;
	}

	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);
		NumberTerm nt = (NumberTermImpl) getFirst(args[0]);
		for (Term t : args) {
			if (!checkT(nt, t)) {
				return false;
			}
		}
		return true;
	}

	protected Term getFirst(Term t) {
		if (t instanceof LogExpr) {
			return getFirst(((LogExpr) t).getTerms().get(0));
		} else if (t.isLiteral()) {
			return ((Literal) t).getAnnot("t").getTerm(0);
		}
		return null;
	}

	protected boolean checkT(NumberTerm nt, Term t) {
		if (t instanceof LogExpr) {
			for (Term st : ((LogExpr) t).getTerms()) {
				boolean c = checkT(nt, st);
				if (!c)
					return false;
			}
			return true;
		} else if (t.isLiteral()) {
			Literal annot = ((Literal) t).getAnnot("t");
			if (annot == null)
				return false;
			return ((Literal) t).getAnnot("t").getTerm(0).equals(nt);
		}
		return true;
	}
}
