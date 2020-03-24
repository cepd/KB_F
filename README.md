# KB_F
## Penugasan Kelas

### Tugas 1

**8 Puzzle DFS**

8 Puzzle adalah sebuah permainan sliding puzzle yang terdiri dari sebuah bingkai
yang berisi kotak kotak angka dalam keadaan bergerak dimana ada sebuah kotak
yang kosong. Tujuan dari game ini adalah dengan memakai gerakan geser yang 
menggunakan kotak kosong untuk mencapai goal state. Dalam hal ini, algoritma
yang digunakan adalah DFS. Pencarian dilakukan pada satu node dalam setiap level
yang paling kiri. Jika pada level yang paling dalam, solusi belum ditemukan,
maka pencarian dilanjutkan pada node sebelah kanan, dan node kiri dapat 
dihapus. Demikian seterusnya sampai menemukan solusi

source code:

```c++
#include <bits/stdc++.h>
using namespace std;
#define N 3

```
Disini saya menggunakan bahasa c++ dan mendefine N=3 diawal
yaitu jumlah matriks yang akan digunakan (3x3).

```c++
struct Node
{
    // menyimpan parent node ke current node
    // membantu mengontrol jejak jalannya ketika jawaban ditemukan
    Node* parent;
    
    // menyimpan matriks
    int mat[N][N];
    
    // menyimpan kotak yang kosong
    int x, y;
    
    // menyimpan angka yang berada di kotak yang salah
    int cost;
    
    // menyimpan angka yang bergerak
    int level;
};
```
Program diatas adalah untuk membuat nodes tree

```c++
int printMatrix(int mat[N][N])
{
    for (int i = 0; i < N; i++)
    {
        for (int j = 0; j < N; j++)
            printf("%d ", mat[i][j]);
        printf("\n");
    }
    return 0;
}
```
Fungsi diatas adalah untuk mencetak matriks NxN

```c++
Node* newNode(int mat[N][N], int x, int y, int newX,
              int newY, int level, Node* parent)
{
    Node* node = new Node;
    
    // set pointer untuk path ke root
    node->parent = parent;
    
    // copy data dari parent node ke current node
    memcpy(node->mat, mat, sizeof node->mat);
    
    // pindahkan kotak
    swap(node->mat[x][y], node->mat[newX][newY]);
    
    // set angka untuk kotak yang salah posisi
    node->cost = INT_MAX;
    
    // set angka perpindahan
    node->level = level;
    
    // update posisi kotak kosong
    node->x = newX;
    node->y = newY;
    
    return node;
}
```

Fungsi diatas untuk menempatkan node baru


```c++
int calculateCost(int initial[N][N], int final[N][N])
{
    int count = 0;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            if (initial[i][j] && initial[i][j] != final[i][j])
                count++;
    return count;
}
```
Fungsi diatas adalah untuk menghitung angka yang berada
di kotak yang salah


```c++
int isSafe(int x, int y)
{
    return (x >= 0 && x < N && y >= 0 && y < N);
}
```
Fungsi diatas adalah untuk mengecek jika x dan y adalah kordinat yang valid


```c++
void printPath(Node* root)
{
    if (root == NULL)
        return;
    printPath(root->parent);
    printMatrix(root->mat);
    
    printf("\n");
}
```
Mencetak Path dari root node ke node tujuan

```c++
void solve(int initial[N][N], int x, int y,
           int final[N][N])
{
    priority_queue<Node*, std::vector<Node*>, comp> pq;
    Node* root = newNode(initial, x, y, x, y, 0, NULL);
    root->cost = calculateCost(initial, final);
    pq.push(root);
    
    // Finds a live node with least cost,
    // add its childrens to list of live nodes and
    // finally deletes it from the list.
    while (!pq.empty())
    {
        Node* min = pq.top();
        pq.pop();
        
        // if min is an answer node
        if (min->cost == 0)
        {
            // print the path from root to destination;
            printPath(min);
            return;
        }
        
        // do for each child of min
        // max 4 children for a node
        for (int i = 0; i < 4; i++)
        {
            if (isSafe(min->x + row[i], min->y + col[i]))
            {
                // create a child node and calculate
                // its cost
                Node* child = newNode(min->mat, min->x,
                                      min->y, min->x + row[i],
                                      min->y + col[i],
                                      min->level + 1, min);
                child->cost = calculateCost(child->mat, final);
                
                // Add child to list of live nodes
                pq.push(child);
            }
        }
    }
}
```
Solving Function

```c++
int main()
{
    // Initial configuration
    // Value 0 is used for empty space
    int initial[N][N] =
    {
        {2, 8, 3},
        {1, 6, 4},
        {7, 0, 5}
    };
    
    // Solvable Final configuration
    // Value 0 is used for empty space
    int final[N][N] =
    {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };
    
    // Blank tile coordinates in initial
    // configuration
    int x = 2, y = 1;
    
    solve(initial, x, y, final);
    
    return 0;
}
```

Output
