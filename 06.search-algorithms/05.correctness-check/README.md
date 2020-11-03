# Проверка корректности

Свойство двоичного дерева поиска можно сформулировать следующим образом: для каждой вершины дерева ![V](https://latex.codecogs.com/svg.latex?V) выполняется следующее условие:
- все ключи вершин из левого поддерева меньше ключа вершины ![V](https://latex.codecogs.com/svg.latex?V);
- все ключи вершин из правого поддерева больше ключа вершины ![V](https://latex.codecogs.com/svg.latex?V).

Дано двоичное дерево.  Проверьте, выполняется ли для него свойство двоичного дерева поиска.

### Формат входного файла

Входной файл содержит описание двоичного дерева.  В первой строке файла находится число ![N](https://latex.codecogs.com/svg.latex?N) (![0 \leqslant N \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?0%20\leqslant%20N%20\leqslant%202\cdot10^5)) -- число вершин в дереве.  В последующих ![N](https://latex.codecogs.com/svg.latex?N) строках файла находятся описания вершин дерева.  В ![(i + 1)](https://latex.codecogs.com/svg.latex?(i+1))-ой строке файла (![1 \leqslant i \leqslant N](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20N)) находится описание ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины, состоящее из трех чисел ![K_i](https://latex.codecogs.com/svg.latex?K_i), ![L_i](https://latex.codecogs.com/svg.latex?L_i), ![R_i](https://latex.codecogs.com/svg.latex?R_i), разделенных пробелами -- ключа в ![i](https://latex.codecogs.com/svg.latex?i)-ой вершине (![|K_i| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|K_i|\leqslant10^9)), номера левого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < L_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20L_i%20\leqslant%20N) или ![L_i = 0](https://latex.codecogs.com/svg.latex?L_i=0), если левого ребенка нет) и номера правого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < R_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20R_i%20\leqslant%20N) или ![R_i = 0](https://latex.codecogs.com/svg.latex?R_i=0), если правого ребенка нет).

### Формат выходного файла

Выведите "YES", если данное во входном файле дерево является двоичным деревом поиска, и "NO", если не является.

### Примеры

`input.txt`
```
6
-2 0 2
8 4 3
9 0 0
3 6 5
6 0 0
0 0 0
```

`output.txt`
```
YES
```

`input.txt`
```
0
```

`output.txt`
```
YES
```

`input.txt`
```
3
5 2 3
6 0 0
4 0 0
```

`output.txt`
```
NO
```

### Решение

[CorrectnessCheck.scala](CorrectnessCheck.scala)
