# lexical-analyzer
CS5900 module 1a, design and implement a lexical analyzer

## Requirements
Design and implement a lexical analyzer from scratch whose specification are given below, by
using your favorite programming language (preferably C). The lexical analyzer identifies and
outputs tokens (valid words and punctuations) in the source program.

```
id = letter alphanum*
alphanum = letter | digit | _
num = integer | float
integer = nonzero digit* | 0
float = integer fraction
fraction = .digit* nonzero | .0
letter = a..z |A..Z
digit = 0..9
nonzero = 1..9
Operators, punctuation and reserved words
==      +       (       if      void
<>      -       )       then    main
<       *       {       else
>       /       }       for
<=      =       [       class
>=      and     ]       int
;       not     /*      float
,       or      */      get
.       //      put     return
```

If a token belongs to both category, the code should output the one that is written higher (5
should be identified as alphanum, not integer; a should be identified as id, not alphanum)
Your code should ignore whatever is written inside the comment block (whatever is written
inside the /* */ block)
If no match is found, report it as an error token.
If you need to assume something, assume it but with correct justification. Any assumption
should not result in diminishing the expressive power of the language.

## Sample input
```
void main( )
         {
          int a=15, b_, c=5;
          float d=2.2;
          if (c%2==0) /* checking if c is even */
                     {
                       printf(“a”);
                     }
         }
```

## Sample Output
```
Operators, punctuations, and reserved words:
void main ( ) { int , , = ; float = ;
if ( == ) { ( ) ; } }

id:
a b_ c d

alphanum:
5 2 0

num:
15 2.2

error token:
% printf “ ”
```

## Actual output
```
Operators, punctuations, and reserved words:
void main ( ) { int = , , = ; float = ; if ( == ) / { ( ) ; } } 

id:
a b_ c d c printf a 

num:
15 5 2.2 2 0 

error token:
% “ ”
```

Assumptions:
1) In the sample output alphanum is really a list of integers. Since an alphanum *must* start with a letter, the sample output cannot be correct. Therefore, I omit alphanum from my output and include the requested values as 'num'. Additionally, since all tokens starting with a letter are either id's or defined as a keyword, then if an encountered token is *not* explicitly defined as a keyword, it's included as an id. It's assumed this would be caught later during semantic analysis
2) Although in the sample output id's aren't listed more than once, I assume that this scanner makes no determination on whether values can be repeated; it simply tokenizes and leaves additional logic such as placing the tokens into a symbol table for another module. Therefore in my output tokens are repeated for all token types when applicable, including id's
