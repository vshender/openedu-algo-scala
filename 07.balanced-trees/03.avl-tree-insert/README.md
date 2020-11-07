# Вставка в АВЛ-дерево

Вставка в АВЛ-дерево вершины ![V](https://latex.codecogs.com/svg.latex?V) с ключом ![X](https://latex.codecogs.com/svg.latex?X) при условии, что такой вершины в этом дереве нет, осуществляется следующим образом:

- находится вершина ![W](https://latex.codecogs.com/svg.latex?W), ребенком которой должна стать вершина ![V](https://latex.codecogs.com/svg.latex?V);
- вершина ![V](https://latex.codecogs.com/svg.latex?V) делается ребенком вершины ![W](https://latex.codecogs.com/svg.latex?W);
- производится подъем от вершины ![W](https://latex.codecogs.com/svg.latex?W) к корню, при этом, если какая-то из вершин несбалансирована, производится, в зависимости от значения баланса, левый или правый поворот.

Первый этап нуждается в пояснении.  Спуск до будущего родителя вершины ![V](https://latex.codecogs.com/svg.latex?V) осуществляется, начиная от корня, следующим образом:

- Пусть ключ текущей вершины равен ![Y](https://latex.codecogs.com/svg.latex?Y).
- Если ![X < Y](https://latex.codecogs.com/svg.latex?X<Y) и у текущей вершины есть левый ребенок, переходим к левому ребенку.
- Если ![X < Y](https://latex.codecogs.com/svg.latex?X<Y) и у текущей вершины нет левого ребенка, то останавливаемся, текущая вершина будет родителем новой вершины.
- Если ![X > Y](https://latex.codecogs.com/svg.latex?X>Y) и у текущей вершины есть правый ребенок, переходим к правому ребенку.
- Если ![X > Y](https://latex.codecogs.com/svg.latex?X>Y) и у текущей вершины нет правого ребенка, то останавливаемся, текущая вершина будет родителем новой вершины.

Отдельно рассматривается следующий крайний случай -- если до вставки дерево было пустым, то вставка новой вершины осуществляется проще: новая вершина становится корнем дерева.

### Формат входного файла

Входной файл содержит описание двоичного дерева, а также ключ вершины, которую требуется вставить в дерево.

В первой строке файла находится число ![N](https://latex.codecogs.com/svg.latex?N) (![0 \leqslant N \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?0%20\leqslant%20N%20\leqslant%202\cdot10^5)) -- число вершин в дереве.  В последующих ![N](https://latex.codecogs.com/svg.latex?N) строках файла находятся описания вершин дерева.  В ![(i + 1)](https://latex.codecogs.com/svg.latex?(i+1))-ой строке файла (![1 \leqslant i leqslant N](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20N)) находится описание ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины, состоящее из трех чисел ![K_i](https://latex.codecogs.com/svg.latex?K_i), ![L_i](https://latex.codecogs.com/svg.latex?L_i), ![R_i](https://latex.codecogs.com/svg.latex?R_i), разделенных пробелами -- ключа в ![i](https://latex.codecogs.com/svg.latex?i)-ой вершине (![|K_i| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|K_i|%20\leqslant%2010^9)), номера левого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < L_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20L_i%20\leqslant%20N) или ![L_i = 0](https://latex.codecogs.com/svg.latex?L_i=0), если левого ребенка нет) и номера правого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < R_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20R_i%20\leqslant%20N) или ![R_i = 0](https://latex.codecogs.com/svg.latex?R_i=0), если правого ребенка нет).

Все ключи различны.  Гарантируется, что данное дерево является корректным АВЛ-деревом.

В последней строке содержится число ![X](https://latex.codecogs.com/svg.latex?X) (![|X| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|X|%20\leqslant%2010^9)) -- ключ вершины, которую требуется вставить в дерево.  Гарантируется, что такой вершины в дереве нет.

### Формат выходного файла

Выведите в том же формате дерево после осуществления операции вставки.  Нумерация вершин может быть произвольной при условии соблюдения формата.

### Пример

`input.txt`
```
2
3 0 2
4 0 0
5
```

`output.txt`
```
3
4 2 3
3 0 0
5 0 0
```

### Решение

[AVLTreeInsert.scala](AVLTreeInsert.scala)
