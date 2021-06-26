import subprocess
from os import listdir
from math import isclose
from shutil import copyfile


def update_input(directory):
    copyfile('tests/' + directory + '/in', 'input.txt')


def run_tests():
    for i in listdir('tests'):
        update_input(i)
        output = float(subprocess.check_output('script.bat').split()[11].decode('utf-8'))
        with open('tests/' + i + '/expected') as file:
            expected = float(file.readline())
            if not isclose(output, expected, rel_tol=0.000002):
                print('--------------------------------------------------')
                print(f"Wrong answer on test {i}\nCorrect answer: {expected}\nYour answer: {output}")
                print('--------------------------------------------------')
                return
            else:
                print(f"Test {i} result: OK")
    print('\n--------------------------------------------------\n                 ALL TESTS PASSED'
          '\n--------------------------------------------------\n')


if __name__ == "__main__":
    print('--------------------------------------------------\n                   TESTS START'
          '\n--------------------------------------------------\n')
    run_tests()
