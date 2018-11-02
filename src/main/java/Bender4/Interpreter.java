package Bender4;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import view.FunctionView;

import java.util.Stack;

public class Interpreter {
    private String[] functions;
    public boolean hasCommandsLeft = true;
    Stack<FunctionExecution> calls = new Stack<>();
    private GraphicEntityModule graphics;
    private Group functionsGroup;

    public Interpreter(String s, Group functionsGroup, GraphicEntityModule graphics) {
        this.functionsGroup = functionsGroup;
        this.graphics = graphics;
        functions = s.split(";");
        calls.push(new FunctionExecution(functions[0], calls.size(), 0));
    }

    public void step(Robot robot) {
        if (calls.isEmpty()) {
            hasCommandsLeft = false;
            return;
        }
        FunctionExecution current = calls.peek();
        if (current.terminate()) {
            calls.pop();
            if (!calls.isEmpty()) calls.peek().resume();
            return;
        }
        char execute = current.step();
        robot.move(execute);
        if (execute >= '1' && execute <= '9') { // call a function
            calls.push(new FunctionExecution(functions[execute - '0'], calls.size(), execute - '0'));
        }
    }

    public class FunctionExecution {
        public String function;
        public int position;
        private FunctionView view;

        public FunctionExecution(String function, int depth, int name) {
            this.function = function;
            this.position = 0;
            view = new FunctionView(this, depth, name, functionsGroup, graphics);
        }

        public void delay() {
            position--;
            view.delay = true;
        }

        public void resume() {
            position++;
            view.step();
        }

        public boolean terminate() {
            if (position == function.length()) {
                view.terminate();
                return true;
            }
            return false;
        }

        public char step() {
            char result = function.charAt(position++);
            if (result >= '1' && result <= '9')
                delay();
            view.step();
            return result;
        }

        @Override
        public String toString() {
            return function.substring(0, position) + "|" + function.substring(position);
        }
    }
}
