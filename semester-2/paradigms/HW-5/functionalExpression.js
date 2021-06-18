"use strict";

const abstractOperation = f => (...components) => (...vals) => {
    // :NOTE: Array.map
        // :NOTE: (...)
    return f(...(components.map(f => f(...vals))));
}

// :NOTE: Эффективность
const variable = name => (...values) => values[VARS.get(name)]

const cnst = value => () => value;
const negate = abstractOperation(a => -a);
const add = abstractOperation((a, b) => a + b);
const subtract = abstractOperation((a, b) => a - b);
const multiply = abstractOperation((a, b) => a * b);
const divide = abstractOperation((a, b) => a / b);
const one = cnst(1);
const two = cnst(2);
const min5 = abstractOperation((a, b, c, d, e) => Math.min(a, b, c, d, e));
const max3 = abstractOperation((a, b, c) => Math.max(a, b, c));

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

const OPERATIONS = new Map([
    ['negate', [negate, 1]],
    ['+', [add, 2]],
    ['-', [subtract, 2]],
    ['*', [multiply, 2]],
    ['/', [divide, 2]],
    ['min5', [min5, 5]],
    ['max3', [max3, 3]]
])

const VARIABLES = new Map([
    ['x', variable('x')],
    ['y', variable('y')],
    ['z', variable('z')]
])

const CONSTANTS = new Map([
    ['one', one],
    ['two', two]
])

function parse(expression) {
    let opStack = [];
    for (const token of split(expression)) {
        if (OPERATIONS.has(token)) {
            const operation = OPERATIONS.get(token);
            // :NOTE: Array.splice
            opStack.push(operation[0](...opStack.splice(-operation[1])));
        } else if (VARIABLES.has(token)) {
            opStack.push(VARIABLES.get(token));
        } else {
            const value = CONSTANTS.has(token) ? CONSTANTS.get(token) : cnst(parseInt(token));
            opStack.push(value);
        }
    }
    return opStack.pop();
}