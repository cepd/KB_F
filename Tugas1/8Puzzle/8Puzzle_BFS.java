import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
public class BFS{
    Queue<String> frontier = new LinkedList<String>();
    Map<String,Integer> explored = new HashMap<String,Integer>();
    Map<String,String> memo = new HashMap<String,String>();
    Stack<String> result = new Stack();
    
    public void compute(String newState, String oldState){
        if(!explored.containsKey(newState)){
            int val;
            if(oldState==null){
                val = 0;
            }
            else{
                val = explored.get(oldState)+1;
            }
            explored.put(newState,val);
            frontier.add(newState);
            memo.put(newState, oldState);
        }
    }
    
    public void moveUp(String state){
        int a = state.indexOf("0");
        if(a>2){
            String nextState = state.substring(0,a-3)+"0"+state.substring(a-2,a)+state.charAt(a-3)+state.substring(a+1);
            isGoal(state,nextState);
        }
    }
    
    public void moveDown(String state){
        int a = state.indexOf("0");
        if(a<6){
            String nextState = state.substring(0,a)+state.substring(a+3,a+4)+state.substring(a+1,a+3)+"0"+state.substring(a+4);
            isGoal(state, nextState);
        }
    }
    
    public void moveLeft(String state){
        int a = state.indexOf("0");
        if(a!=0 && a!=3 && a!=6){
            String nextState = state.substring(0,a-1)+"0"+state.charAt(a-1)+state.substring(a+1);
            isGoal(state, nextState);
        }
    }
    
    public void moveRight(String state){
        int a = state.indexOf("0");
        if(a!=2 && a!=5 && a!=8){
            String nextState = state.substring(0,a)+state.charAt(a+1)+"0"+state.substring(a+2);
            isGoal(state, nextState);
        }
    }
    
    //check if the state is the goal state
    private void isGoal(String oldState, String newState){
        compute(newState,oldState);
        if(newState.equals("123456780")){
            int total = explored.get(newState);
            System.out.println("Solution found after "+total+" steps");
            String trace = newState;
            while(trace!=null){
                result.add(trace);
                trace = memo.get(trace);
            }
            backtrack(total);
            System.exit(0);
        }
    }
    
    private void backtrack(int steps){
        int i=0;
        while(!result.isEmpty()){
            System.out.print("Step: "+i);
            if(i==0){
                System.out.println(" (Initial State)");
            }
            else if(i==steps){
                System.out.println(" (Goal State)");
            }
            else{
                System.out.println();
            }
            printState(result.pop());
            i++;
        }
    }
    
    private void printState(String s){
        int i=0;
        while(i!=s.length()){
            if(i==3||i==6)System.out.println();
            System.out.print(s.charAt(i)+" ");
            i++;
        }
        System.out.println();
        System.out.println();
    }
}
