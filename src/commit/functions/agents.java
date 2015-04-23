package commit.functions;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.util.Set;
import java.util.regex.Pattern;

public class agents extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	protected void checkArguments(Term[] args) throws JasonException {
		int l = args.length;
		if (l == 1 || l == 2)
			return;
		throw JasonException.createWrongArgument(this, "Wrong arguments");
	}

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		checkArguments(args);
		ListTerm matches = new ListTermImpl();
		Set<String> names = ts.getUserAgArch().getRuntimeServices().getAgentsNames();
		if (args.length == 2) {
			Pattern pattern = Pattern.compile(((StringTerm) args[1]).getString());
			for (String name : names) {
				if (pattern.matcher(name).find())
					matches.add(Literal.parseLiteral(name));
			}
		} else {
			for (String name : names) {
				matches.add(Literal.parseLiteral(name));
			}
		}
		return un.unifies(matches, args[0]);
	}
}
