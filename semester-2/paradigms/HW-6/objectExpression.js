"use strict";

const OPERATIONS = {};
function UnaryOperation(value) {
    this.getValue = value;
}
UnaryOperation.prototype.toString = function() { return this.getValue.toString() };

function crateUnaryOperation(f, differential) {
    const operation = function (value) {
        UnaryOperation.call(this, value);
    };
    operation.prototype = Object.create(UnaryOperation.prototype);
    operation.prototype.evaluate = f;
    operation.prototype.diff = differential;
    return operation;
}

const Const = crateUnaryOperation(
    function() { return this.getValue },
    function() { return Zero }
);

const Zero = new Const(0);
const One = new Const(1);

const Variable = crateUnaryOperation(
    function() { return arguments[VARS.get(this.getValue)] },
    function (name) {
        if (this.getValue === name) {
            return One;
        } else {
            return Zero;
        }
    }
);

function AbstractOperation(f, sign, ...components) {
    this.getF = function() { return f };
    this.getSign = function() { return sign };
    this.getComponents = function() { return components };
}

AbstractOperation.prototype.toString = function() {
    return this.getComponents().join(' ') + ' ' + this.getSign();
};

AbstractOperation.prototype.evaluate = function (...variables) {
    return this.getF()(...this.getComponents().map(component => component.evaluate(...variables)));
};

AbstractOperation.prototype.diff = function (name) {
    return this.differential(name, ...this.getComponents());
};

function createOperation(f, sign, differential) {
    const Operation = function(...components) {
        AbstractOperation.call(this, f, sign, ...components);
    };
    Operation.arity = f.length;
    Operation.prototype = Object.create(AbstractOperation.prototype);
    Operation.prototype.differential = differential;
    OPERATIONS[sign] = Operation;
    return Operation;
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
]);

const VARIABLES = new Map([
    ['x', new Variable('x')],
    ['y', new Variable('y')],
    ['z', new Variable('z')]
]);

function parse(expression) {
    let opStack = [];
    for (const token of split(expression)) {
        if (token in OPERATIONS) {
            const Operation = OPERATIONS[token];
            opStack.push(new Operation(...opStack.splice(-Operation.arity)));
        } else if (VARIABLES.has(token)) {
            opStack.push(VARIABLES.get(token));
        } else {
            opStack.push(new Const(parseInt(token)));
        }
    }
    return opStack.pop();
}
