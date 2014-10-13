package commit.functions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.Iterator;

public class terms extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMinArgs() {
		return 2;
	}

	@Override
	public int getMaxArgs() {
		return 2;
	}

	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);
		Literal lookFor = (Literal) args[0];
		ListTerm all = new ListTermImpl();
		Iterator<Term> terms = lookFor.getTerms().iterator();
		while (terms.hasNext()) {
			Term term = (Term) terms.next();
			all.append(term);
		}
		return un.unifies(args[1], all);
	}
}
