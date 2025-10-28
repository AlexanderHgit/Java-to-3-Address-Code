#Converts Java code to a 3 Address Code equivalent. 

##Example input:
{
    int i ; int j ; float v ; float x ; float[100] a ;
    while( true ) {
        do i = i+1 ; while( a[i] < v) ;
        do j = j-1 ; while( a[j] > v) ;
        if( i >= j ) break;
        x = a[i] ; a[i] = a[j] ; a[j] = x ;
    }
}

##Example output:
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
