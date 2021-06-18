"use strict";

let MAXMEM = 1000, MAXOPS = 1000;
let MEMORY = new Array(MAXMEM);
MEMORY.fill(0,  0);
let OPERATIONSSTACK = new Array(); // :NOTE: []
let RESULT = '';
let SPLITEDINPUT = new Array();
let id = 0, inpId = 0, opNum = 0;

function runProgram(program, input) {
    parseProgram(program);
    split(input);
    cicle(0, OPERATIONSSTACK.length, 1);
    console.log(RESULT);
}

function cicle(startI, endI, iterNum) {
    for (let k = 0; k < iterNum; k++) {
        let i = startI;
        while (i < endI) {
            let op = OPERATIONSSTACK[i];
            if (op === cicleStart) {
                let balance = findBalance(i + 1);
                let start = balance[0], end = balance[1];
                cicle(start, end, get() - 1)
            } else {
                if (opNum < MAXOPS) {
                    opNum++;
                } else {
                    throw new Error('Stack Overflow');
                }
                op();
            }
            i++;
        }
    }
}

// :NOTE: * Лишняя работа
function findBalance(i) {
    let startI = i, balance = 1;
    while (balance != 0) {
        let op = OPERATIONSSTACK[i];
        if (op === cicleStart) {
            balance++;
        } else if (op === cicleEnd) {
            balance--;
        }
        i++;
    }
    let endI = i;
    return [startI, endI];
}

function set_() {
    MEMORY[id] = parseInt(SPLITEDINPUT[inpId++]);
}

function get() {
    return MEMORY[id];
}

function add() {
    MEMORY[id]++;
}

function reduce() {
    MEMORY[id]--;
}

function next() {
    id++;
    if (id > MAXMEM) {
        throw new Error('Memory Error');
    }
}

function prev() {
    id--;
}

function print() {
    RESULT += String.fromCharCode(get());
}

// :NOTE: - Заглушки
function cicleStart() {

}

function cicleEnd() {

}

const OPERATIONS = new Map([
    ['1', add],
    ['000', reduce],
    ['010', next],
    ['011', prev],
    ['00100', cicleStart],
    ['0011', cicleEnd],
    ['0010110', set_],
    ['001010', print]
])

function parseProgram(program) {
    let start = 0;
    for (let i = 0; i <= program.length; i++) {
        if (OPERATIONS.has(program.slice(start, i))) {
            OPERATIONSSTACK.push(OPERATIONS.get(program.slice(start, i)));
            start = i;
        }
    }
}

function split(s) {
    s += ' ';
    let start = 0;
    for (let i = 0; i < s.length; i++) {
        if (s[i] === ' ') {
            if (i > start) {
                SPLITEDINPUT.push(s.substring(start, i));
            }
            start = i + 1;
        }
    }
}

// Hello, World!
runProgram('11111111110010001011111110101111111111010111010101101101101100000110101100101001010010101111111001010001010111001010010110010100110111111111111111110010100100010101110010100000000000000000000010100000000000000000000000000010100101001010010001010', '');