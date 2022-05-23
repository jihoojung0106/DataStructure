import java.io.*;
import java.util.Stack;
import java.lang.Long.*;

public class CalculatorTest {
	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String input = br.readLine();
				if (input == null || input.compareTo("q") == 0) { return; }
				command(input);
			} catch (Exception e) {
				//System.err.println("입력이 잘못되었습니다. 오류 : " + e.toString());
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input) {
		/*for(int i=0;i<token(input).size();i++){
			System.out.println(stackmodify(token(input)).get(i));
		}*/
		check(input);
		String postfix=infixToPostFix(token(input));
		Long answer=evaluate(postfix);
		System.out.println(postfix);
		System.out.println(answer);
	}
	public static void check(String input){
		input=input.replaceAll("\t"," ");
		for(int i=0;i<input.length()-1;i++){
			if(input.charAt(i)<='9'&& input.charAt(i)>='0' && input.charAt(i+1)==' '){
				String a=input.substring(i+2).trim();
				if(a.charAt(0)>='0' && a.charAt(0)<='9'){
					throw new IllegalArgumentException();
				}
				}
			}
		}

	private static Stack<Object> token(String input) {
		input = input.trim();
		input = input.replaceAll(" ", "");
		int len = input.length();
		Stack<Object> s = new Stack<>();
		boolean digitPreviously = false;

		for (int i = 0; i < len; i++) {
			char ch = input.charAt(i);
			if (ch >= '0' && ch <= '9') {
				if (digitPreviously) {
					int tmp = (int) s.pop();
					tmp = tmp * 10 + (ch - '0');
					s.push(tmp);

				} else {
					s.push(ch - '0');
				}
				digitPreviously = true;
			} else if (isOperator(ch)) {
				s.push(ch);
				digitPreviously = false;
			} else {
				digitPreviously = false;
			}
		}
		//System.out.println(stackmodify(s));
		return stackmodify(s);
	}

	public static Stack<Object> stackmodify(Stack<Object> s){
		int i=1;
		while(i<s.size()) {
			if (s.get(i) instanceof Character && s.get(i-1) instanceof Character) {
				if ((char) s.get(i) == '-' && isOperator((char) s.get(i - 1)) && (char) s.get(i - 1)!='(' &&(char) s.get(i - 1)!=')') {
					s.set(i, '~');
				}
			}i++;
		}
		if(s.get(0) instanceof Character && (char)s.get(0)=='-'){s.set(0,'~');}
		return s;
	}


	private static boolean isOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '(' || ch == ')' || ch == '(' || ch == '%' || ch == '^' ||ch=='~';
	}
	public static int precedence(char c){
		switch (c){
			case '+':
			case '-':
				return 1;
			case '*':
			case '/':
			case '%':
				return 2;
			case '~':
				return 3;
			case '^':
				return 4;
		}
		return -1;
	}

static String infixToPostFix(Stack<Object> input){
		String result = "";
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i <input.size() ; i++) {
			//System.out.println(input.get(i));

			if(input.get(i) instanceof Integer){
				int c=(int)input.get(i);
				result+=" "+c;
			}
			if(input.get(i) instanceof Character){
				char c=(char)input.get(i);
				if(precedence(c)==4 || precedence(c)==3){
					while(stack.isEmpty()==false && precedence(stack.peek())>precedence(c)){
						result += " "+stack.pop();
					}
					stack.push(c);
				}

				else if(precedence(c)>0){
					while(stack.isEmpty()==false && precedence(stack.peek())>=precedence(c)){
						result += " "+stack.pop();
					}
					stack.push(c);
				}else if(c==')'){
					char x = stack.pop();
					while(x!='('){
						result += " "+x;
						x = stack.pop();
					}
				}else if(c=='('){
					stack.push(c);
				}else{}
			}
		}
		while(!stack.isEmpty()){
			result += " "+stack.pop();
		}
		return result.trim();
	}


	public static Long evaluate(String postfix){
		Long A,B;
		Stack<Long> s=new Stack<>();
		boolean digitpre=false;
		for(int i=0;i<postfix.length();i++){
			char ch=postfix.charAt(i);
			if(Character.isDigit(ch)){
				if(digitpre==true){
					long tmp=s.pop();
					tmp=10*tmp+(ch-'0');
					s.push(tmp);
				}
				else s.push((long)ch-'0');
				digitpre=true;
			}
			else if(ch=='~'){
				long tmp_=s.pop();
				tmp_=-1*tmp_;
				s.push(tmp_);
			}
			else if(ch!='~' && isOperator(ch)){
				A=s.pop();
				B=s.pop();
				long val=operation(A,B,ch);
				s.push(val);
				digitpre=false;
			}else digitpre=false;
		}
		return s.pop();
	}
	private static long operation(Long a,Long b,char ch){
		long val=0;
		switch (ch){
			case '*':
				val=b*a;
				break;
			case '/':
				if(a==0){throw new IllegalArgumentException();}
				val=b/a;
				break;
			case '+':
				val=b+a;
				break;
			case '-':
				val=b-a;
				break;
			case '^':
				if(a<0){if(b==0){throw new IllegalArgumentException();}}
				val=(long)Math.pow(b,a);
				break;
			case '%':
				if(a==0){throw new IllegalArgumentException();}
				val=b%a;
				break;
			default: throw new IllegalArgumentException();
		}
		return val;
	}
}