import java.util.ArrayList;
import java.util.EmptyStackException;

public class MyStack<AnyType> {

	private ArrayList<AnyType> stack = new ArrayList<>();

	public boolean isEmpty() {
		return stack.isEmpty();
	}

	public void push(AnyType x) {
		stack.add(x);
	}

	public AnyType pop() {
		if (stack.isEmpty())
			throw new EmptyStackException();
		return stack.remove(stack.size() - 1);
	}

	public AnyType peek() {
		if (stack.isEmpty())
			throw new EmptyStackException();
		return stack.get(stack.size() - 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (AnyType x : stack)
			sb.append(x + " ");
		return new String(sb);
	}
}

class CheckBalance {

	public static boolean isBalanced(String str) {
		MyStack<Character> stack = new MyStack<>();
		char[] ch = str.toCharArray();

		for (int i = 0; i < str.length(); i++) {
			if (ch[i] == '[' || ch[i] == '{' || ch[i] == '(')
				stack.push(ch[i]);
			if (ch[i] == ']' || ch[i] == '}' || ch[i] == ')') {
				if (stack.isEmpty())
					return false;
				if (isPaired(stack.peek(), ch[i]) == false)
					return false;
				stack.pop();
			}
		}
		return stack.isEmpty();
	}

	public static boolean isPaired(char open, char close) {
		if (open == '[' && close == ']' ||
			open == '{' && close == '}' ||
			open == '(' && close == ')')
			return true;
		return false;
	}

	public static void main(String[] args) {

		String str = "[(])"; // not balanced
		String str2 = "()(){}{}{()}"; // balanced
		String str3 = ")("; // not balanced
		String str4 = "[{{[(){}]}}[]{}{({())}}]"; // not balanced
		String str5 = "[{{[(){}]}}[]{}{{(())}}]"; // balanced

		System.out.println(isBalanced(str));
		System.out.println(isBalanced(str2));
		System.out.println(isBalanced(str3));
		System.out.println(isBalanced(str4));
		System.out.println(isBalanced(str5));
	}
}
