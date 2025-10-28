# Converts Java code to a 3 Address Code equivalent. 
Uses input.txt as an input and outputs various information about the structure of the inputted code along with a 3 Address Code conversion at the end.

## Example input:
```
{
    int i ; int j ; float v ; float x ; float[100] a ;
    while( true ) {
        do i = i+1 ; while( a[i] < v) ;
        do j = j-1 ; while( a[j] > v) ;
        if( i >= j ) break;
        x = a[i] ; a[i] = a[j] ; a[j] = x ;
    }
}
```
## Example 3 Address Code output:
```
L1:
 ifFalse true  goto L2
L3:
 i = i + 1 ;
 T1 = i * 8 ;
 T2 = a[T1] ;
 if T2 < v  goto L3
L5:
 j = j - 1 ;
 T3 = j * 8 ;
 T4 = a[T3] ;
 if T4 > v  goto L5
 ifFalse i >= j  goto L7
 goto L2
L7:
 T5 = i * 8 ;
 x = a[T5] ;
 T6 = i * 8 ;
 T7 = j * 8 ;
 T8 = a[T7] ;
 a[T6] = T8 ;
 T9 = j * 8 ;
 a[T9] = x ;
 goto L1
L2:
```

## Entire Example output:
```
Compilation Unit
...BlockStatementNode
......DeclarationNode
.........TypeNode: int
.........IdentifierNode: i
&&&&&&& tmp.type: int
&&&&&&& tmp.w: i
......DeclarationNode
.........TypeNode: int
.........IdentifierNode: j
&&&&&&& tmp.type: int
&&&&&&& tmp.w: j
......DeclarationNode
.........TypeNode: float
.........IdentifierNode: v
&&&&&&& tmp.type: float
&&&&&&& tmp.w: v
......DeclarationNode
.........TypeNode: float
.........IdentifierNode: x
&&&&&&& tmp.type: float
&&&&&&& tmp.w: x
......DeclarationNode
.........TypeNode: float
............ArrayTypeNode
............Array Dimension: 100
.........IdentifierNode: a
&&&&&&& tmp.type: float
&&&&&&& tmp.w: a
......**** parseStatementNode 275
......WhileStatementNode
......operator: while
.........ParenthesesNode
............TrueNode
.........BlockStatementNode
............**** parseStatementNode 259
............DoWhileStatementNode
............operator: do
............**** parseStatementNode 264
............AssignmentNode
...............IdentifierNode: i
In Parser, Assignment's left type: int
............operator: =
...............IdentifierNode: i
............operator: +
.................................NumNode: 1
............operator: while
...............ParenthesesNode
..................IdentifierNode: a
...............parseArrayAccessNode
..................ArrayDimsNode
.....................IdentifierNode: i
.......................................IdentifierNode: v
............**** parseStatementNode 259
............DoWhileStatementNode
............operator: do
............**** parseStatementNode 264
............AssignmentNode
...............IdentifierNode: j
In Parser, Assignment's left type: int
............operator: =
...............IdentifierNode: j
............operator: -
.................................NumNode: 1
............operator: while
...............ParenthesesNode
..................IdentifierNode: a
...............parseArrayAccessNode
..................ArrayDimsNode
.....................IdentifierNode: j
.......................................IdentifierNode: v
............**** parseStatementNode 265
............IfStatementNode
[intercode.ast.DoWhileStatementNode@4eec7777, intercode.ast.DoWhileStatementNode@3b07d329]
intercode.ast.DoWhileStatementNode@4eec7777
intercode.ast.DoWhileStatementNode@3b07d329
###### enclosing block doesnt have IfStatementNode
............operator: if
...............ParenthesesNode
..................IdentifierNode: i
.......................................IdentifierNode: j
............**** parseStatementNode 258
............BreakStatementNode: break
............**** parseStatementNode 264
............AssignmentNode
...............IdentifierNode: x
In Parser, Assignment's left type: float
............operator: =
...............IdentifierNode: a
............parseArrayAccessNode
...............ArrayDimsNode
..................IdentifierNode: i
............**** parseStatementNode 264
............AssignmentNode
...............IdentifierNode: a
In Parser, Assignment's left type: float
............parseArrayAccessNode
...............ArrayDimsNode
..................IdentifierNode: i
............operator: =
...............IdentifierNode: a
............parseArrayAccessNode
...............ArrayDimsNode
..................IdentifierNode: j
............**** parseStatementNode 264
............AssignmentNode
...............IdentifierNode: a
In Parser, Assignment's left type: float
............parseArrayAccessNode
...............ArrayDimsNode
..................IdentifierNode: j
............operator: =
...............IdentifierNode: x
@@@@@@@@@@ EOF reached...
***************************
*   Tree printer starts   *
***************************

CompilationUnit
...BlockStatementNode
......DeclarationNode
.........TypeNode: int
.........IdentifierNode: i
......DeclarationNode
.........TypeNode: int
.........IdentifierNode: j
......DeclarationNode
.........TypeNode: float
.........IdentifierNode: v
......DeclarationNode
.........TypeNode: float
.........IdentifierNode: x
......DeclarationNode
.........TypeNode: float
............ArrayTypeNode: 100
.........IdentifierNode: a
......WhileStatementNode
.........ParenthesesNode
............TrueNode
.........BlockStatementNode
............DoWhileStatementNode
...............AssignmentNode
..................IdentifierNode: i
...............operator: =
..................BinExprNode: +
.....................IdentifierNode: i
.....................NumNode: 1
...............ParenthesesNode
..................BinExprNode: <
.....................ArrayAccessNode
........................IdentifierNode: a
........................ArrayDimsNode
...........................IdentifierNode: i
.....................IdentifierNode: v
............DoWhileStatementNode
...............AssignmentNode
..................IdentifierNode: j
...............operator: =
..................BinExprNode: -
.....................IdentifierNode: j
.....................NumNode: 1
...............ParenthesesNode
..................BinExprNode: >
.....................ArrayAccessNode
........................IdentifierNode: a
........................ArrayDimsNode
...........................IdentifierNode: j
.....................IdentifierNode: v
............IfStatementNode
...............ParenthesesNode
..................BinExprNode: >=
.....................IdentifierNode: i
.....................IdentifierNode: j
...............BreakStatementNode
............AssignmentNode
...............IdentifierNode: x
............operator: =
...............ArrayAccessNode
..................IdentifierNode: a
..................ArrayDimsNode
.....................IdentifierNode: i
............AssignmentNode
...............ArrayAccessNode
..................IdentifierNode: a
..................ArrayDimsNode
.....................IdentifierNode: i
............operator: =
...............ArrayAccessNode
..................IdentifierNode: a
..................ArrayDimsNode
.....................IdentifierNode: j
............AssignmentNode
...............ArrayAccessNode
..................IdentifierNode: a
..................ArrayDimsNode
.....................IdentifierNode: j
............operator: =
...............IdentifierNode: x
***************************
*   TypeChecker starts    *
***************************

CompilationUnit
BlockStatementNode
DeclarationNode
TypeNode: int
IdentifierNode: i
In TypeChecker ID: i, Declaration type: int
DeclarationNode
TypeNode: int
IdentifierNode: j
In TypeChecker ID: j, Declaration type: int
DeclarationNode
TypeNode: float
IdentifierNode: v
In TypeChecker ID: v, Declaration type: float
DeclarationNode
TypeNode: float
IdentifierNode: x
In TypeChecker ID: x, Declaration type: float
DeclarationNode
TypeNode: float
ArrayTypeNode: 100
IdentifierNode: a
In TypeChecker ID: a, Declaration type: float
WhileStatementNode
ParenthesesNode
TrueNode
BlockStatementNode
DoWhileStatementNode
AssignmentNode
IdentifierNode: i
i
{}
intercode.ast.IdentifierNode@5fd0d5ae
In TypeChecker, AssignmentNode's left type: int
BinExprNode: +
IdentifierNode: i
NumNode: 1
*********** leftType is Type.Int
AssignmentNode
IdentifierNode: i
i
{}
intercode.ast.IdentifierNode@5fd0d5ae
In TypeChecker, AssignmentNode's left type: int
BinExprNode: +
IdentifierNode: i
NumNode: 1
*********** leftType is Type.Int
true
ParenthesesNode
BinExprNode: <
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: i
IdentifierNode: v
DoWhileStatementNode
AssignmentNode
IdentifierNode: j
j
{}
intercode.ast.IdentifierNode@2d98a335
In TypeChecker, AssignmentNode's left type: int
BinExprNode: -
IdentifierNode: j
NumNode: 1
*********** leftType is Type.Int
AssignmentNode
IdentifierNode: j
j
{}
intercode.ast.IdentifierNode@2d98a335
In TypeChecker, AssignmentNode's left type: int
BinExprNode: -
IdentifierNode: j
NumNode: 1
*********** leftType is Type.Int
true
ParenthesesNode
BinExprNode: >
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: j
IdentifierNode: v
IfStatementNode
ParenthesesNode
BinExprNode: >=
IdentifierNode: i
IdentifierNode: j
BreakStatementNode
AssignmentNode
IdentifierNode: x
x
{}
intercode.ast.IdentifierNode@16b98e56
In TypeChecker, AssignmentNode's left type: float
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: i
AssignmentNode
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: i
In TypeChecker, AssignmentNode's left type: float
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: j
AssignmentNode
ArrayAccessNode
IdentifierNode: a
ArrayDimsNode
IdentifierNode: j
In TypeChecker, AssignmentNode's left type: float
IdentifierNode: x
INTERCODE GENERATION
{
 int i;
 int j;
 float v;
 float x;
 float[100] a;
 while (true)
 {
  do
   i = i + 1;
  while(a[i] < v);
  do
   j = j - 1;
  while(a[j] > v);
  if (i >= j)
   break ;
  x = a[i];
  a[i] = a[j];
  a[j] = x;
 }
}
[intercode.intercodeast.LoopNode@b4c966a, intercode.intercodeast.IfFalseNode@2f4d3709, intercode.intercodeast.LoopNode@4e50df2e, intercode.ast.AssignmentNode@1d81eb93, intercode.ast.AssignmentNode@7291c18f, intercode.ast.AssignmentNode@34a245ab, intercode.intercodeast.IfNode@7cc355be, intercode.intercodeast.LoopNode@6e8cf4c6, intercode.ast.AssignmentNode@12edcd21, intercode.ast.AssignmentNode@34c45dca, intercode.ast.AssignmentNode@52cc8049, intercode.intercodeast.IfNode@5b6f7412, intercode.intercodeast.IfFalseNode@27973e9b, intercode.intercodeast.GotoNode@312b1dae, intercode.intercodeast.LoopNode@7530d0a, intercode.ast.AssignmentNode@27bc2616, intercode.ast.AssignmentNode@3941a79c, intercode.ast.AssignmentNode@506e1b77, intercode.ast.AssignmentNode@4fca772d, intercode.ast.AssignmentNode@9807454, intercode.ast.AssignmentNode@3d494fbf, intercode.ast.AssignmentNode@1ddc4ec2, intercode.ast.AssignmentNode@133314b, intercode.intercodeast.GotoNode@b1bc7ed, intercode.intercodeast.LoopNode@7cd84586]
UNPARSER
[intercode.intercodeast.LoopNode@b4c966a, intercode.intercodeast.IfFalseNode@2f4d3709, intercode.intercodeast.LoopNode@4e50df2e, intercode.ast.AssignmentNode@1d81eb93, intercode.ast.AssignmentNode@7291c18f, intercode.ast.AssignmentNode@34a245ab, intercode.intercodeast.IfNode@7cc355be, intercode.intercodeast.LoopNode@6e8cf4c6, intercode.ast.AssignmentNode@12edcd21, intercode.ast.AssignmentNode@34c45dca, intercode.ast.AssignmentNode@52cc8049, intercode.intercodeast.IfNode@5b6f7412, intercode.intercodeast.IfFalseNode@27973e9b, intercode.intercodeast.GotoNode@312b1dae, intercode.intercodeast.LoopNode@7530d0a, intercode.ast.AssignmentNode@27bc2616, intercode.ast.AssignmentNode@3941a79c, intercode.ast.AssignmentNode@506e1b77, intercode.ast.AssignmentNode@4fca772d, intercode.ast.AssignmentNode@9807454, intercode.ast.AssignmentNode@3d494fbf, intercode.ast.AssignmentNode@1ddc4ec2, intercode.ast.AssignmentNode@133314b, intercode.intercodeast.GotoNode@b1bc7ed, intercode.intercodeast.LoopNode@7cd84586]
L1:
 ifFalse true  goto L2
L3:
 i = i + 1 ;
 T1 = i * 8 ;
 T2 = a[T1] ;
 if T2 < v  goto L3
L5:
 j = j - 1 ;
 T3 = j * 8 ;
 T4 = a[T3] ;
 if T4 > v  goto L5
 ifFalse i >= j  goto L7
 goto L2
L7:
 T5 = i * 8 ;
 x = a[T5] ;
 T6 = i * 8 ;
 T7 = j * 8 ;
 T8 = a[T7] ;
 a[T6] = T8 ;
 T9 = j * 8 ;
 a[T9] = x ;
 goto L1
L2:
```
