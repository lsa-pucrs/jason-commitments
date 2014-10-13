package commit.functions;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class functor extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	private static InternalAction singleton = null;

	public static InternalAction create() {
		if (singleton == null)
			singleton = new functor();
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
		if (!args[0].isVar() && args[1].isVar()) {
			Literal lit = (Literal) args[0];
			return un.unifies(new StringTermImpl(lit.getFunctor()), args[1]);
		}
		throw new JasonException("invalid case of factor");
	}
}
