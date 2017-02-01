import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class PostFix {
	private String x;
	private PostFix filsgauche, filsdroit;
	
	public PostFix(){	 }
	public PostFix(String val){
		this.x = val;
	}
	 
	// ACCESSEURS
	public String getValeur() {
	return(x);
    }
	 
	public PostFix getSousArbreGauche() {
		return(filsgauche);
	}   

    public PostFix getSousArbreDroit() {
        return(filsdroit);
	}
	 
	public void setfilsgauche(PostFix arbr){
		this.filsgauche = arbr;
	}
	 
	public void setfilsdroit(PostFix arbr){
		this.filsdroit = arbr;
	}
	// AFFICHAGE
	    public String toString() {
	        return toString("\t");
	    }

	    public String toString(String s) {
			if (filsgauche!=null) {
				if (filsdroit!=null) 
					return(s+"__("+x+")__\n"+filsgauche.toString(s+"\t")+filsdroit.toString(s+"\t"));
				else
					return(s+x+"\n"+filsgauche.toString(s+"\t")+"\n");
			}
			else 
				if (filsdroit!=null) 
					return(s+x+"\n\n"+filsdroit.toString(s+"\t"));
				else
					return(s+x+" \n");
	    }

	private static Scanner sc;
	public static void main(String[] args){
		PostFix aux,courant, racine;
		Stack<String> tempo = new Stack<String>();
		
		sc = new Scanner(System.in);
		System.out.print("Entrez une expression postfixée: ");
		System.out.println();
		ArrayList<String> list ;
		String PostEntre = sc.nextLine();
		System.out.println();
		  
		list = new ArrayList<>(Arrays.asList(PostEntre.split(" ")));
		  
		ArrayDeque<PostFix> pil = new ArrayDeque<PostFix>();
		for (String s : list){
			courant = new PostFix(s);
			if (isOperator(s)){
				aux =  pil.pop();
				courant.setfilsgauche(pil.pop());
				courant.setfilsdroit(aux);
			}
			pil.push(courant);
			
		}
		racine = pil.pop();
		System.out.println("L'arbre est : " );
		System.out.println(racine + "\n" );
		System.out.println();
		
		//-----------------------------to infix------------------------
		System.out.println("L'expression infixee donne:");
		System.out.println(PostfixToInfix(tempo, PostEntre));
		System.out.println();
		  
		//----------------------------to prefix------------------------
		String s1,s2,sor;
		  
		ArrayDeque<String> operat = new ArrayDeque<String>();
		for (String n : list){
			if (!isOperator(n))
				operat.push(n);
			else{
				s2  = operat.pop();
			  	s1  = operat.pop();
			  	sor = n +" "+ s1 +" "+ s2;
				operat.push(sor);
			}
			            			  
		}
		System.out.println("L'expression prefixee donne: ");
		System.out.println(operat.pop()+"\n");
		//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		  
		  
		//-----------------------------evaluation-----------------------
		Double oper1, oper2,result;
		ArrayDeque<Double> abc = new ArrayDeque<Double>();
		for (String n : list){
			if (!isOperator(n))
				abc.push(Double.valueOf(n) - 0);
			else{
				oper2 = abc.pop();
				oper1 = abc.pop();
			  	result = operate(oper1,oper2,n);			           
				abc.push(result);
			}
			            			  
		}
		System.out.println("l'evaluation donne: ");
		System.out.println(abc.pop());
		//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	}
	
	public void Afficher(PostFix a){
		System.out.println("\t\t\t\t"+a.getValeur());
		try{
		PostFix b = a.getSousArbreGauche();
		PostFix c = a.getSousArbreDroit();
		System.out.println("\t\t\t"+b.getValeur()+" \t\t "+c.getValeur());
		PostFix d = b.getSousArbreGauche();	PostFix f,g;
		PostFix e = b.getSousArbreDroit();
		f= c.getSousArbreGauche(); g = c.getSousArbreDroit();
		System.out.println("\t\t     "+d.getValeur()+" \t   "+e.getValeur()+"\t \t "+f.getValeur()+"\t"+g.getValeur());}
		finally{}
	}
	
	private static double operate(Double a, Double b, String op){
	    //Log.d("Calc", "w " +"operrrrr");
	    switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                 e.getMessage();
            }
            default: return -1;
        }
	}
	
	//method
	private static boolean isOperator(String op){
        switch (op){
            case "+":
            case "-":
            case "*":
            case "/":return true;
            default: return false;
        }
    }
	
	public static String PostfixToInfix(Stack<String> temp, String a){
		Stack<String> op1 = new Stack<String>();
		Stack<String> op2 = new Stack<String>();
		
		StringBuffer abuffer = new StringBuffer(a);
		
		for(int i=0; i<abuffer.length(); i++){
			if(abuffer.charAt(i) == ' '){
				abuffer.deleteCharAt(i);
				i=0;
			}
		}
		//a = abuffer.toString();
		
		for(int i=0; i<abuffer.length(); i++){
			if(!isOperator(String.valueOf(abuffer.charAt(i)))){
				temp.push(String.valueOf(abuffer.charAt(i)));
			}
			else if(isOperator(String.valueOf(abuffer.charAt(i)))){
				op1.push(temp.pop());
				op2.push(temp.pop());
				temp.push(op2.pop() + abuffer.charAt(i) + op1.pop());
			}
		}
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<temp.size(); i++){
			buffer.append(temp.pop());
		}
		return buffer.toString();
	}
}
