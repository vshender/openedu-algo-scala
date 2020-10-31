# Анти-quick-sort

Для сортировки последовательности чисел широко используется быстрая сортировка -- QuickSort.  Далее приведена программа, которая сортирует массив `a`, используя этот алгоритм.

```
var a : array [1..N] of integer;

procedure QSort(left, right : integer);
var i, j, key, buf : integer;
begin
  key := a[(left + right) div 2];
  i := left;
  j := right;
  repeat
    while a[i] < key do
      inc(i);
    while key < a[j] do
      dec(j);
    if i <= j then begin
      buf := a[i];
      a[i] := a[j];
      a[j] := buf;
      inc(i);
      dec(j);
    end;
  until i > j;
  if left < j then QSort(left, j);
  if i < right then QSort(i, right);
end;
begin
  ...
  QSort(1, N);
end.
```

Хотя QuickSort является очень быстрой сортировкой в среднем, существуют тесты, на которых она работает очень долго.  Оценивать время работы алгоритма будем числом сравнений с элементами массива (то есть, суммарным числом сравнений в первом и втором `while`).  Требуется написать программу, генерирующую тест, на котором быстрая сортировка сделает наибольшее число таких сравнений.

### Формат входного файла

В первой строке находится единственное число ![n](https://latex.codecogs.com/svg.latex?n) (![1 \leqslant n \leqslant 10^6](https://latex.codecogs.com/svg.latex?1%20\leqslant%20n%20\leqslant%2010^6)).

### Формат выходного файла

Вывести перестановку чисел от 1 до ![n](https://latex.codecogs.com/svg.latex?n), на которой быстрая сортировка выполнит максимальное число сравнений.  Если таких перестановок несколько, вывести любую из них.

### Пример

`input.txt`
```
3
```

`output.txt`
```
1 3 2
```

### Примечание

На [этой странице](https://ctlab.itmo.ru/~mbuzdalov/antiqs.html) можно ввести ответ, выводимый Вашей программой, и страница посчитает число сравнений, выполняемых указанным выше алгоритмом Quicksort.  Вычисления будут производиться в Вашем браузере.  Очень большие массивы могут обрабатываться долго.

### Решение

[AntiQuickSort.scala](AntiQuickSort.scala)
