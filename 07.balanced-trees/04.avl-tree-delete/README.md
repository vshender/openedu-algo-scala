# Удаление из АВЛ-дерева

Удаление из АВЛ-дерева вершины с ключом ![X](https://latex.codecogs.com/svg.latex?X), при условии ее наличия, осуществляется следующим образом:

- путем спуска от корня и проверки ключей находится ![V](https://latex.codecogs.com/svg.latex?V) -- удаляемая вершина;
- если вершина ![V](https://latex.codecogs.com/svg.latex?V) -- лист (то есть, у нее нет детей):
  - удаляем вершину;
  - поднимаемся к корню, начиная с бывшего родителя вершины ![V](https://latex.codecogs.com/svg.latex?V), при этом если
    встречается несбалансированная вершина, то производим поворот.
- если у вершины ![V](https://latex.codecogs.com/svg.latex?V) не существует левого ребенка:
  - следовательно, баланс вершины равен единице и ее правый ребенок -- лист;
  - заменяем вершину ![V](https://latex.codecogs.com/svg.latex?V) ее правым ребенком;
  - поднимаемся к корню, производя, где необходимо, балансировку.
- иначе:
  - находим ![R](https://latex.codecogs.com/svg.latex?R) -- самую правую вершину в левом поддереве;
  - переносим ключ вершины ![R](https://latex.codecogs.com/svg.latex?R) в вершину ![V](https://latex.codecogs.com/svg.latex?V);
  - удаляем вершину ![R](https://latex.codecogs.com/svg.latex?R) (у нее нет правого ребенка, поэтому она либо лист, либо имеет левого ребенка, являющегося листом);
  - поднимаемся к корню, начиная с бывшего родителя вершины ![R](https://latex.codecogs.com/svg.latex?R), производя балансировку.

Исключением является случай, когда производится удаление из дерева, состоящего из одной вершины -- корня.  Результатом удаления в этом случае будет пустое дерево.

Указанный алгоритм не является единственно возможным, но мы просим Вас реализовать именно его, так как тестирующая система проверяет точное равенство получающихся деревьев.

### Формат входного файла

Входной файл содержит описание двоичного дерева, а также ключ вершины, которую требуется удалить из дерева.

В первой строке файла находится число ![N](https://latex.codecogs.com/svg.latex?N) (![1 \leqslant N \leqslant 2 \cdot 10^5](https://latex.codecogs.com/svg.latex?1%20\leqslant%20N%20\leqslant%202\cdot10^5)) -- число вершин в дереве.  В последующих ![N](https://latex.codecogs.com/svg.latex?N) строках файла находятся описания вершин дерева.  В ![(i + 1)](https://latex.codecogs.com/svg.latex?(i+1))-ой строке файла (![1 \leqslant i leqslant N](https://latex.codecogs.com/svg.latex?1%20\leqslant%20i%20\leqslant%20N)) находится описание ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины, состоящее из трех чисел ![K_i](https://latex.codecogs.com/svg.latex?K_i), ![L_i](https://latex.codecogs.com/svg.latex?L_i), ![R_i](https://latex.codecogs.com/svg.latex?R_i), разделенных пробелами -- ключа в ![i](https://latex.codecogs.com/svg.latex?i)-ой вершине (![|K_i| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|K_i|%20\leqslant%2010^9)), номера левого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < L_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20L_i%20\leqslant%20N) или ![L_i = 0](https://latex.codecogs.com/svg.latex?L_i=0), если левого ребенка нет) и номера правого ребенка ![i](https://latex.codecogs.com/svg.latex?i)-ой вершины (![i < R_i \leqslant N](https://latex.codecogs.com/svg.latex?i%20<%20R_i%20\leqslant%20N) или ![R_i = 0](https://latex.codecogs.com/svg.latex?R_i=0), если правого ребенка нет).

Все ключи различны.  Гарантируется, что данное дерево является корректным АВЛ-деревом.

В последней строке содержится число ![X](https://latex.codecogs.com/svg.latex?X) (![|X| \leqslant 10^9](https://latex.codecogs.com/svg.latex?|X|%20\leqslant%2010^9)) -- ключ вершины, которую требуется вставить в дерево.  Гарантируется, что такая вершина в дереве существует.

### Формат выходного файла

Выведите в том же формате дерево после осуществления операции удаления.  Нумерация вершин может быть произвольной при условии соблюдения формата.

### Пример

`input.txt`
```
3
4 2 3
3 0 0
5 0 0
4
```

`output.txt`
```
2
3 0 2
5 0 0
```

### Решение

[AVLTreeDelete.scala](AVLTreeDelete.scala)
