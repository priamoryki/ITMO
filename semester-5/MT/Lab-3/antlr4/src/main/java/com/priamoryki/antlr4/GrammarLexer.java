// Generated from java-escape by ANTLR 4.11.1

package com.priamoryki.antlr4;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT=1, IF=2, ELSE=3, INPUT=4, TYPE=5, NEW_LINE=6, TAB=7, SPACE=8, COLON=9, 
		L_BRACKET=10, R_BRACKET=11, ASSIGNMENT=12, OPERATION=13, COMPARISON=14, 
		NUMBER=15, VARIABLE=16, STRING=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PRINT", "IF", "ELSE", "INPUT", "TYPE", "NEW_LINE", "TAB", "SPACE", "COLON", 
			"L_BRACKET", "R_BRACKET", "ASSIGNMENT", "OPERATION", "COMPARISON", "NUMBER", 
			"VARIABLE", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'print'", "'if'", "'else'", "'input()'", null, "'\\n'", null, 
			"' '", "':'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PRINT", "IF", "ELSE", "INPUT", "TYPE", "NEW_LINE", "TAB", "SPACE", 
			"COLON", "L_BRACKET", "R_BRACKET", "ASSIGNMENT", "OPERATION", "COMPARISON", 
			"NUMBER", "VARIABLE", "STRING"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0011\u0099\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004B\b\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006K\b\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0003\u000b^\b\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\rl\b\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000es\b\u000e"+
		"\n\u000e\f\u000ev\t\u000e\u0001\u000e\u0001\u000e\u0005\u000ez\b\u000e"+
		"\n\u000e\f\u000e}\t\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u0081\b"+
		"\u000e\n\u000e\f\u000e\u0084\t\u000e\u0003\u000e\u0086\b\u000e\u0003\u000e"+
		"\u0088\b\u000e\u0001\u000f\u0001\u000f\u0005\u000f\u008c\b\u000f\n\u000f"+
		"\f\u000f\u008f\t\u000f\u0001\u0010\u0001\u0010\u0005\u0010\u0093\b\u0010"+
		"\n\u0010\f\u0010\u0096\t\u0010\u0001\u0010\u0001\u0010\u0000\u0000\u0011"+
		"\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r"+
		"\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011\u0001\u0000\u0005\u0003\u0000*+--//\u0001"+
		"\u000019\u0001\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u00ab\u0000"+
		"\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000"+
		"\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000"+
		"\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r"+
		"\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0001#\u0001\u0000\u0000\u0000\u0003)\u0001\u0000\u0000"+
		"\u0000\u0005,\u0001\u0000\u0000\u0000\u00071\u0001\u0000\u0000\u0000\t"+
		"A\u0001\u0000\u0000\u0000\u000bC\u0001\u0000\u0000\u0000\rJ\u0001\u0000"+
		"\u0000\u0000\u000fL\u0001\u0000\u0000\u0000\u0011N\u0001\u0000\u0000\u0000"+
		"\u0013P\u0001\u0000\u0000\u0000\u0015R\u0001\u0000\u0000\u0000\u0017]"+
		"\u0001\u0000\u0000\u0000\u0019_\u0001\u0000\u0000\u0000\u001bk\u0001\u0000"+
		"\u0000\u0000\u001d\u0087\u0001\u0000\u0000\u0000\u001f\u0089\u0001\u0000"+
		"\u0000\u0000!\u0090\u0001\u0000\u0000\u0000#$\u0005p\u0000\u0000$%\u0005"+
		"r\u0000\u0000%&\u0005i\u0000\u0000&\'\u0005n\u0000\u0000\'(\u0005t\u0000"+
		"\u0000(\u0002\u0001\u0000\u0000\u0000)*\u0005i\u0000\u0000*+\u0005f\u0000"+
		"\u0000+\u0004\u0001\u0000\u0000\u0000,-\u0005e\u0000\u0000-.\u0005l\u0000"+
		"\u0000./\u0005s\u0000\u0000/0\u0005e\u0000\u00000\u0006\u0001\u0000\u0000"+
		"\u000012\u0005i\u0000\u000023\u0005n\u0000\u000034\u0005p\u0000\u0000"+
		"45\u0005u\u0000\u000056\u0005t\u0000\u000067\u0005(\u0000\u000078\u0005"+
		")\u0000\u00008\b\u0001\u0000\u0000\u00009:\u0005i\u0000\u0000:;\u0005"+
		"n\u0000\u0000;B\u0005t\u0000\u0000<=\u0005f\u0000\u0000=>\u0005l\u0000"+
		"\u0000>?\u0005o\u0000\u0000?@\u0005a\u0000\u0000@B\u0005t\u0000\u0000"+
		"A9\u0001\u0000\u0000\u0000A<\u0001\u0000\u0000\u0000B\n\u0001\u0000\u0000"+
		"\u0000CD\u0005\n\u0000\u0000D\f\u0001\u0000\u0000\u0000EK\u0005\t\u0000"+
		"\u0000FG\u0005 \u0000\u0000GH\u0005 \u0000\u0000HI\u0005 \u0000\u0000"+
		"IK\u0005 \u0000\u0000JE\u0001\u0000\u0000\u0000JF\u0001\u0000\u0000\u0000"+
		"K\u000e\u0001\u0000\u0000\u0000LM\u0005 \u0000\u0000M\u0010\u0001\u0000"+
		"\u0000\u0000NO\u0005:\u0000\u0000O\u0012\u0001\u0000\u0000\u0000PQ\u0005"+
		"(\u0000\u0000Q\u0014\u0001\u0000\u0000\u0000RS\u0005)\u0000\u0000S\u0016"+
		"\u0001\u0000\u0000\u0000T^\u0005=\u0000\u0000UV\u0005+\u0000\u0000V^\u0005"+
		"=\u0000\u0000WX\u0005-\u0000\u0000X^\u0005=\u0000\u0000YZ\u0005*\u0000"+
		"\u0000Z^\u0005=\u0000\u0000[\\\u0005/\u0000\u0000\\^\u0005=\u0000\u0000"+
		"]T\u0001\u0000\u0000\u0000]U\u0001\u0000\u0000\u0000]W\u0001\u0000\u0000"+
		"\u0000]Y\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^\u0018\u0001"+
		"\u0000\u0000\u0000_`\u0007\u0000\u0000\u0000`\u001a\u0001\u0000\u0000"+
		"\u0000ab\u0005!\u0000\u0000bl\u0005=\u0000\u0000cl\u0005>\u0000\u0000"+
		"de\u0005>\u0000\u0000el\u0005=\u0000\u0000fg\u0005=\u0000\u0000gl\u0005"+
		"=\u0000\u0000hi\u0005<\u0000\u0000il\u0005=\u0000\u0000jl\u0005<\u0000"+
		"\u0000ka\u0001\u0000\u0000\u0000kc\u0001\u0000\u0000\u0000kd\u0001\u0000"+
		"\u0000\u0000kf\u0001\u0000\u0000\u0000kh\u0001\u0000\u0000\u0000kj\u0001"+
		"\u0000\u0000\u0000l\u001c\u0001\u0000\u0000\u0000m\u0088\u00050\u0000"+
		"\u0000no\u00050\u0000\u0000op\u0005.\u0000\u0000pt\u0001\u0000\u0000\u0000"+
		"qs\u0007\u0001\u0000\u0000rq\u0001\u0000\u0000\u0000sv\u0001\u0000\u0000"+
		"\u0000tr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000u\u0088\u0001"+
		"\u0000\u0000\u0000vt\u0001\u0000\u0000\u0000w{\u0007\u0001\u0000\u0000"+
		"xz\u0007\u0002\u0000\u0000yx\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000"+
		"\u0000{y\u0001\u0000\u0000\u0000{|\u0001\u0000\u0000\u0000|\u0085\u0001"+
		"\u0000\u0000\u0000}{\u0001\u0000\u0000\u0000~\u0082\u0005.\u0000\u0000"+
		"\u007f\u0081\u0007\u0002\u0000\u0000\u0080\u007f\u0001\u0000\u0000\u0000"+
		"\u0081\u0084\u0001\u0000\u0000\u0000\u0082\u0080\u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0086\u0001\u0000\u0000\u0000"+
		"\u0084\u0082\u0001\u0000\u0000\u0000\u0085~\u0001\u0000\u0000\u0000\u0085"+
		"\u0086\u0001\u0000\u0000\u0000\u0086\u0088\u0001\u0000\u0000\u0000\u0087"+
		"m\u0001\u0000\u0000\u0000\u0087n\u0001\u0000\u0000\u0000\u0087w\u0001"+
		"\u0000\u0000\u0000\u0088\u001e\u0001\u0000\u0000\u0000\u0089\u008d\u0007"+
		"\u0003\u0000\u0000\u008a\u008c\u0007\u0004\u0000\u0000\u008b\u008a\u0001"+
		"\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000\u0000\u008d\u008b\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e \u0001\u0000"+
		"\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u0090\u0094\u0005\"\u0000"+
		"\u0000\u0091\u0093\u0007\u0004\u0000\u0000\u0092\u0091\u0001\u0000\u0000"+
		"\u0000\u0093\u0096\u0001\u0000\u0000\u0000\u0094\u0092\u0001\u0000\u0000"+
		"\u0000\u0094\u0095\u0001\u0000\u0000\u0000\u0095\u0097\u0001\u0000\u0000"+
		"\u0000\u0096\u0094\u0001\u0000\u0000\u0000\u0097\u0098\u0005\"\u0000\u0000"+
		"\u0098\"\u0001\u0000\u0000\u0000\f\u0000AJ]kt{\u0082\u0085\u0087\u008d"+
		"\u0094\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}