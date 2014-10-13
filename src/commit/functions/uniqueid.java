package commit.functions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.Date;

public class uniqueid extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	@Override
	public int getMinArgs() {
		return 5;
	}

	@Override
	public int getMaxArgs() {
		return 5;
	}

	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);

		String type = args[0].toString();
		String debtor = args[1].toString();
		String creditor = args[2].toString();
		ListTerm bag = (ListTerm) args[3];

		String hash = debtor + creditor;
		for (Term object : bag) {
			hash += object.toString();
		}
		hash += (new Date()).getTime();
		String name = type + "_" + Math.abs(hash.hashCode());
		return un.unifies(args[4], Literal.parseLiteral(name));
	}
}
