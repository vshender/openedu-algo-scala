# Наивный поиск подстроки в строке

Даны строки ![p](https://latex.codecogs.com/svg.latex?p) и ![t](https://latex.codecogs.com/svg.latex?t).  Требуется найти все вхождения строки ![p](https://latex.codecogs.com/svg.latex?p) в строку ![t](https://latex.codecogs.com/svg.latex?t) в качестве подстроки.

### Формат входного файла

Первая строка входного файла содержит ![p](https://latex.codecogs.com/svg.latex?p), вторая -- ![t](https://latex.codecogs.com/svg.latex?t) (![1 \leqslant |p|, |t| \leqslant 10^4](https://latex.codecogs.com/svg.latex?1%20\leqslant%20|p|,%20|t|%20\leqslant%2010^4)).  Строки состоят из букв латинского алфавита.

### Формат выходного файла

В первой строке выведите число вхождений строки ![p](https://latex.codecogs.com/svg.latex?p) в строку ![t](https://latex.codecogs.com/svg.latex?t).  Во второй строке выведите в возрастающем порядке номера символов строки ![t](https://latex.codecogs.com/svg.latex?t), с которых начинаются вхождения ![p](https://latex.codecogs.com/svg.latex?p).  Символы нумеруются с единицы.

### Примеры

`input.txt`
```
aba
abaCaba
```

`output.txt`
```
2
1 5
```

### Решение

[NaiveSearch.scala](NaiveSearch.scala)
