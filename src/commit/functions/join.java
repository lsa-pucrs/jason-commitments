package commit.functions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

public class join extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	private static InternalAction singleton = null;

	public static InternalAction create() {
		if (singleton == null)
			singleton = new join();
		return singleton;
	}

	@Override
	public int getMinArgs() {
		return 3;
	}

	@Override
	public int getMaxArgs() {
		return 3;
	}

	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);
		
		ListTerm lit = new ListTermImpl();
		lit.append(args[0]);
		
		ListTerm terms = new ListTermImpl();
		for (Term term : (ListTerm) args[1]) {
			terms.append(term);
		}
		lit.append(terms);
		
		return un.unifies(args[2], Literal.newFromListOfTerms(lit));
	}
}
