package commit.functions;

import java.util.Iterator;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

public class findallbeliefs extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	private static InternalAction singleton = null;

	public static InternalAction create() {
		if (singleton == null)
			singleton = new findallbeliefs();
		return singleton;
	}

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
		Iterator<Literal> relB = ts.getAg().getBB().getCandidateBeliefs(lookFor, un);
		if (relB != null) {
			while (relB.hasNext()) {
				Literal literal = (Literal) relB.next();
				all.append(literal);
			}
		} else {
			System.out.println("NOT FOUND FOR " + lookFor.toString());
		}
		return un.unifies(args[1], all);
	}
}
