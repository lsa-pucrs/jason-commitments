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

public class add_annot extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMinArgs() {
		return 3;
	}

	@Override
	public int getMaxArgs() {
		return 3;
	}

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		checkArguments(args);
		Term result = addAnnotToList(un, args[0], args[1]);
		return un.unifies(result, args[2]);
	}

	protected Term addAnnotToList(Unifier unif, Term l, Term annot) throws JasonException {
		if (l instanceof LogExpr) {
			LogExpr c = (LogExpr) l.clone();
			List<Term> nterms = new ArrayList<Term>();
			for (Term t : c.getTerms()) {
				nterms.add(addAnnotToList(unif, t, annot));
			}
			c.setTerms(nterms);
			return c;
		} else if (l.isList()) {
			ListTerm result = new ListTermImpl();
			for (Term lTerm : (ListTerm) l) {
				Term t = addAnnotToList(unif, lTerm, annot);
				if (t != null) {
					result.add(t);
				}
			}
			return result;
		} else if (l.isLiteral()) {
			return ((Literal) l).forceFullLiteralImpl().copy().addAnnots(annot);
		}
		return l;
	}
}
