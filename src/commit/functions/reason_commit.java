package commit.functions;

import jason.JasonException;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.LogExpr.LogicalOp;
import jason.asSyntax.Term;

import java.util.Iterator;

public class reason_commit extends DefaultInternalAction {

	private static final long serialVersionUID = 1L;

	protected void checkArguments(Term[] args) throws JasonException {
		int l = args.length;
		if (l == 1 && args[0].isLiteral()) {
			Literal lit = (Literal) args[0];
			boolean checkFunctor = lit.getFunctor().equals("commit") && lit.getArity() == 4;
			boolean checkAnnot = lit.getAnnot("t") != null && lit.getAnnot("t").getArity() == 1;
			if (checkFunctor && checkAnnot)
				return;
		}
		if (l == 5 || l == 6)
			return;
		throw JasonException.createWrongArgument(this, "Wrong arguments");
	}

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		checkArguments(args);

		ListTerm newCommits = new ListTermImpl();

		Literal finderAnt = generateCommitLiteral(args[0], args[1], Literal.parseLiteral("_"), args[3], args[4]);
		Iterator<Literal> findBelAnt = ts.getAg().getBB().getCandidateBeliefs(finderAnt, un);
		if (findBelAnt != null) {
			while (findBelAnt.hasNext()) {
				Literal lit = findBelAnt.next();
				if (!args[3].equals(lit.getTerm(3)))
					continue;
				Literal ant = (Literal) lit.getTerm(2);
				LogExpr logExpr = new LogExpr((Literal) args[2], LogicalOp.or, ant);
				Literal newCommit = generateCommitLiteral(args[0], args[1], logExpr, args[3], args[4]);
				ts.getAg().getBB().remove(lit);
				ts.getAg().getBB().add(newCommit);
				newCommits.add(newCommit);
			}
		}

		Literal finderCon = generateCommitLiteral(args[0], args[1], args[2], Literal.parseLiteral("_"), args[4]);
		Iterator<Literal> findBelCon = ts.getAg().getBB().getCandidateBeliefs(finderCon, un);
		if (findBelCon != null) {
			while (findBelCon.hasNext()) {
				Literal lit = findBelCon.next();
				if (!args[2].equals(lit.getTerm(2)))
					continue;
				Literal ant = (Literal) lit.getTerm(3);
				LogExpr logExpr = new LogExpr((Literal) args[2], LogicalOp.or, ant);
				Literal newCommit = generateCommitLiteral(args[0], args[1], logExpr, args[3], args[4]);
				ts.getAg().getBB().remove(lit);
				ts.getAg().getBB().add(newCommit);
				newCommits.add(newCommit);
			}
		}

		if (args.length == 6)
			un.unifies(newCommits, args[5]);

		return true;
	}

	protected Literal generateCommitLiteral(Term debtor, Term creditor, Term antecedent, Term consequent, Term id) {
		
		// commit(Debtor, Creditor, Antecedent, Consequent)[t(Id)];
		
		ListTerm ca = new ListTermImpl();
		
		ca.add(Literal.parseLiteral("commit"));
		
		ListTerm cat = new ListTermImpl();
		
		cat.add(debtor);
		cat.add(creditor);
		cat.add(antecedent);
		cat.add(consequent);
		
		ca.add(cat);
				
		Literal annot = Literal.parseLiteral("t");
		annot.addTerm(id);
		
		ListTerm caa = new ListTermImpl();
		caa.add(annot);
		
		ca.add(caa);
		
		try {
			Literal complete = Literal.newFromListOfTerms(ca);
			return complete;
		} catch (JasonException e) {
		}
		
		return null;
	}
}
