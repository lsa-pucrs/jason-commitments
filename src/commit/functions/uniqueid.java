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
		return 2;
	}
	
	@Override
	public Object execute(TransitionSystem ts, final Unifier un, final Term[] args) throws Exception {
		checkArguments(args);
		
		String hash = "";
		for (int i = 1; i < args.length - 1; i++) {
			if(args[i].getClass().isAssignableFrom(ListTerm.class)){
				ListTerm bag = (ListTerm) args[i];
				for (Term object : bag) {
					hash += object.toString();
				}
			} else {
				hash += args[i].toString();
			}
		}
		
		hash += (new Date()).getTime();
		
		String prefix = args[0].toString();
		String name = prefix + "_" + Math.abs(hash.hashCode());
		
		return un.unifies(args[args.length - 1], Literal.parseLiteral(name));
	}

}
