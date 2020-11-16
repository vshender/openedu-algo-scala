# Почти интерактивная хеш-таблица

В данной задаче у Вас не будет проблем ни с вводом, ни с выводом.  Просто реализуйте быструю хеш-таблицу.

В этой хеш-таблице будут храниться целые числа из диапазона ![[0; 10^15 - 1]](https://latex.codecogs.com/svg.latex?[0;10^5-1]).  Требуется поддерживать добавление числа ![x](https://latex.codecogs.com/svg.latex?x) и проверку того, есть ли в таблице число ![x](https://latex.codecogs.com/svg.latex?x).  Числа, с которыми будет работать таблица, генерируются следующим образом.  Пусть имеется четыре целых числа ![N](https://latex.codecogs.com/svg.latex?N), ![X](https://latex.codecogs.com/svg.latex?X), ![A](https://latex.codecogs.com/svg.latex?A), ![B](https://latex.codecogs.com/svg.latex?B), такие что:

- ![1 \leqslant N \leqslant 10^7](https://latex.codecogs.com/svg.latex?1%20\leqslant%20N%20\leqslant%2010^7);
- ![0 \leqslant X < 10^{15}](https://latex.codecogs.com/svg.latex?1%20\leqslant%20X%20<%2010^{15});
- ![0 \leqslant A < 10^3](https://latex.codecogs.com/svg.latex?0%20\leqslant%20A%20<%2010^3);
- ![0 \leqslant B < 10^{15}](https://latex.codecogs.com/svg.latex?0%20\leqslant%20B%20<%2010^{15}).

Требуется ![N](https://latex.codecogs.com/svg.latex?N) раз выполнить следующую последовательность операций:

- Если ![X](https://latex.codecogs.com/svg.latex?X) содержится в таблице, то установить ![A \leftarrow (A + A_C) \mod 10^3](https://latex.codecogs.com/svg.latex?A\leftarrow(A+A_C)\mod10^3), ![B \leftarrow (B + B_C) \mod 10^{15}](https://latex.codecogs.com/svg.latex?B\leftarrow(B+B_C)\mod10^{15}).
- Если ![X](https://latex.codecogs.com/svg.latex?X) не содержится в таблице, то добавить ![X](https://latex.codecogs.com/svg.latex?X) в таблицу и установить ![A \leftarrow (A + A_D) \mod 10^3](https://latex.codecogs.com/svg.latex?A\leftarrow(A+A_D)\mod10^3), ![B \leftarrow (B + B_D) \mod 10^{15}](https://latex.codecogs.com/svg.latex?B\leftarrow(B+B_D)\mod10^{15}).
- Установить ![X \leftarrow (X \cdot A + B) mod 10^{15}](https://latex.codecogs.com/svg.latex?X\leftarrow(X%20\cdot%20A+B)\mod10^{15}).

Начальные значения ![X](https://latex.codecogs.com/svg.latex?X), ![A](https://latex.codecogs.com/svg.latex?A) и ![B](https://latex.codecogs.com/svg.latex?B), а также ![N](https://latex.codecogs.com/svg.latex?N), ![A_C](https://latex.codecogs.com/svg.latex?A_C), ![B_C](https://latex.codecogs.com/svg.latex?B_C), ![A_D](https://latex.codecogs.com/svg.latex?A_D) и ![B_D](https://latex.codecogs.com/svg.latex?B_D) даны во входном файле.  Выведите значения ![X](https://latex.codecogs.com/svg.latex?X), ![A](https://latex.codecogs.com/svg.latex?A) и ![B](https://latex.codecogs.com/svg.latex?B) после окончания работы.

### Формат входного файла
В первой строке входного файла содержится четыре целых числа ![N](https://latex.codecogs.com/svg.latex?N), ![X](https://latex.codecogs.com/svg.latex?X), ![A](https://latex.codecogs.com/svg.latex?A), ![B](https://latex.codecogs.com/svg.latex?B).  Во второй строке содержится еще четыре целых числа ![A_C](https://latex.codecogs.com/svg.latex?A_C), ![B_C](https://latex.codecogs.com/svg.latex?B_C), ![A_D](https://latex.codecogs.com/svg.latex?A_D) и ![B_D](https://latex.codecogs.com/svg.latex?B_D), такие что ![0 \leqslant A_C, A_D < 10^3](https://latex.codecogs.com/svg.latex?0%20\leqslant%20A_C,A_D%20<%2010^3), ![0 \leqslant B_C, B_D < 10^{15}](https://latex.codecogs.com/svg.latex?0%20\leqslant%20B_C,B_D%20<%2010^{15}).

### Формат выходного файла

Выведите значения ![X](https://latex.codecogs.com/svg.latex?X), ![A](https://latex.codecogs.com/svg.latex?A) и ![B](https://latex.codecogs.com/svg.latex?B) после окончания работы.

### Пример

`input.txt`
```
4 0 0 0
1 1 0 0
```

`output.txt`
```
3 1 1
```

### Решение

[FastHashTable.scala](FastHashTable.scala)
