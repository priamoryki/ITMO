"use strict";

// :NOTE: вынесем общий код с variable
function Const(value) {
    this.getValue = function() {
        return value;
    }
}
Const.prototype.toString = function() { return this.getValue().toString() };
Const.prototype.evaluate = function() { return this.getValue() };
Const.prototype.diff = function() { return new Const(0) };

function Variable(variableName) {
    this.getName = function() {
        return variableName;
    }
}
Variable.prototype.toString = function() { return this.getName() };
Variable.prototype.evaluate = function() { return arguments[VARS.get(this.getName())] };
Variable.prototype.diff = function (name) {
    if (this.getName() === name) {
        return new Const(1);
    } else {
        return new Const(0);
    }
}

function Operation(f, sign, ...components) {
    this.getF = function() { return f };
    this.getSign = function() { return sign };
    this.getComponents = function() { return components };
}

Operation.prototype.toString = function() {
    return this.getComponents().join(' ') + ' ' + this.getSign();
}

Operation.prototype.evaluate = function (...variables) {
    return this.getF()(...this.getComponents().map(component => component.evaluate(...variables)));
}

Operation.prototype.diff = function (name) {
    return this.differential(name, ...this.getComponents());
}

function createOperation(f, sign, differential) {
    const operation = function (...components) {
        Operation.call(this, f, sign, ...components);
    }
    operation.prototype = Object.create(Operation.prototype);
    operation.prototype.differential = differential;
    return operation;
}

const Negate = createOperation(
    a => -a,
    'negate',
    (name, a) => new Negate(a.diff(name))
);

const Add = createOperation(
    (a, b) => a + b,
    '+',
    (name, a, b) => new Add(a.diff(name), b.diff(name))
);

const Subtract = createOperation(
    (a, b) => a - b,
    '-',
    (name, a, b) => new Subtract(a.diff(name), b.diff(name))
);

const Multiply = createOperation(
    (a, b) => a * b,
    '*',
    (name, a, b) => new Add(new Multiply(a.diff(name), b), new Multiply(a, b.diff(name)))
);

const Divide = createOperation(
    (a, b) => a / b,
    '/',
    (name, a, b) => new Divide(
        new Subtract(
            new Multiply(a.diff(name), b),
            new Multiply(a, b.diff(name))
        ),
        new Multiply(b, b)
    )
);

const Cube = createOperation(
    (a) => Math.pow(a, 3),
    'cube',
    (name, a) => new Multiply(new Multiply(new Const(3), new Multiply(a, a)), a.diff(name))
);

const Cbrt = createOperation(
    (a) => Math.cbrt(a),
    'cbrt',
    (name, a) => new Divide(a.diff(name), new Multiply(new Const(3), new Cbrt(new Multiply(a, a))))
);

function split(s) {
    let result = [];
    s += ' ';
    let start = 0;
    for (let i = 0; i < s.length; i++) {
        if (s[i] === ' ') {
            if (i > start) {
                result.push(s.substring(start, i));
            }
            start = i + 1;
        }
    }
    return result;
}

const VARS = new Map([
    ['x', 0],
    ['y', 1],
    ['z', 2]
])

// :NOTE: хотелось бы не объявлять арность тут
const OPERATIONS = new Map([
    ['negate', [Negate, 1]],
    ['+', [Add, 2]],
    ['-', [Subtract, 2]],
    ['*', [Multiply, 2]],
    ['/', [Divide, 2]],
    ['cube', [Cube, 1]],
    ['cbrt', [Cbrt, 1]]
])

const VARIABLES = new Map([
    ['x', new Variable('x')],
    ['y', new Variable('y')],
    ['z', new Variable('z')]
])

function parse(expression) {
    let opStack = [];
    for (const token of split(expression)) {
        if (OPERATIONS.has(token)) {
            const operation = OPERATIONS.get(token);
            opStack.push(new operation[0](...opStack.splice(-operation[1])));
        } else if (VARIABLES.has(token)) {
            opStack.push(VARIABLES.get(token));
        } else {
            opStack.push(new Const(parseInt(token)));
        }
    }
    return opStack.pop();
}